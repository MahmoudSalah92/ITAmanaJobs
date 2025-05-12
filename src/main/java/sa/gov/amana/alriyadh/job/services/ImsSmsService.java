package sa.gov.amana.alriyadh.job.services;


import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sa.gov.amana.alriyadh.job.constants.Constants;
import sa.gov.amana.alriyadh.job.dao.ImsSmsDao;
import sa.gov.amana.alriyadh.job.dao.ImsUserLogDao;
import sa.gov.amana.alriyadh.job.dto.ims.ContractPaymentsDto;
import sa.gov.amana.alriyadh.job.dto.ims.EscalationLevelDto;
import sa.gov.amana.alriyadh.job.entity.cmn.CmnPersonsMessage;
import sa.gov.amana.alriyadh.job.entity.cmn.CmnPersonsMessageId;
import sa.gov.amana.alriyadh.job.entity.ims.ImsContracts;
import sa.gov.amana.alriyadh.job.entity.ims.ImsEscalationLog;
import sa.gov.amana.alriyadh.job.entity.ims.ImsSetting;
import sa.gov.amana.alriyadh.job.repository.cmn.CmnPersonsMessageRepository;
import sa.gov.amana.alriyadh.job.repository.ims.ImsContractRepository;
import sa.gov.amana.alriyadh.job.repository.ims.ImsEscalationLogRepository;
import sa.gov.amana.alriyadh.job.repository.ims.ImsSettingRepository;
import sa.gov.amana.alriyadh.job.utils.Utils;

import java.time.temporal.ChronoField;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ImsSmsService {

    private final ImsSmsDao imsSmsDao;
    private final ImsUserLogDao imsUserLogDao;
    private final ImsContractRepository imsContractRepository;
    private final ImsEscalationLogRepository imsEscalationLogRepository;
    private final ImsSettingRepository imsSettingRepository;
    private final CmnPersonsMessageRepository cmnPersonsMessageRepository;

    private final String USER_CODE = "API-JOB";


    @Transactional
    public void sendImsSms() {

        String guid = Utils.getGUID();
        Long guidSeq = Constants.ZERO;
        String logs = "";
        try {
            Long serial = imsSmsDao.getImsEscalationLogSerial();
            System.out.println(serial);
            Long checkError = Constants.ZERO;
            String msgError = "";
            String msgBody = "";

            // start log
            imsUserLogDao.addImsUserLog("IMS_API_JOB_SEND_ESCALATION", USER_CODE, "S", "START", "START#CALL#API_JOB", guid, guidSeq, "START");

            List<ContractPaymentsDto> contractPaymentsDataList = imsSmsDao.getContractPaymentsData();
            if (CollectionUtils.isNotEmpty(contractPaymentsDataList)) {

                for (ContractPaymentsDto contractPaymentsDto : contractPaymentsDataList) {
                    System.out.println(contractPaymentsDto.toString());

                    Long mobileNo = imsSmsDao.getMobileNo(contractPaymentsDto.getContractNo());
                    System.out.println(mobileNo);

                    logs += "V_MOBILE_NO = " + mobileNo;

                    Long sentStatus = mobileNo != null ? Constants.ONE : Constants.ZERO;

                    // get Contract Data
                    ImsContracts oldContract = getContractData(contractPaymentsDto.getContractNo());

                    // get Escalation Level
                    Long escalationLevelId = Constants.ZERO;
                    List<EscalationLevelDto> escalationLevel = imsSmsDao.getEscalationLevel(contractPaymentsDto.getPaymentId(), contractPaymentsDto.getContractNo());
                    if (CollectionUtils.isNotEmpty(escalationLevel)) {
                        System.out.println(escalationLevel.get(0).toString());
                        escalationLevelId = escalationLevel.get(0).getEscalationLevel();

                        logs += "; T_ESCALATION_LEVEL = " + escalationLevel.get(0).getEscalationLevel() + "; T_SENT_STATUS = " + escalationLevel.get(0).getSentStatus() + "; T_SEND_DATE = " + escalationLevel.get(0).getSendDate() + "; V_ACTUAL_ESCALATION_PERIOD = " + imsSmsDao.getActualEscalationPeriod(contractPaymentsDto.getFormDate().toString());
                    }

                    try {

                        if (mobileNo != null && mobileNo > Constants.mobileNoMin && mobileNo < Constants.mobileNoMax) {
                            if (escalationLevelId != null && escalationLevelId.equals(Constants.ONE)) {
                                //msgBody = firstTime(oldContract, contractPaymentsDto, serial, sentStatus, newEscalationLog);
                                msgBody = getMsgBody("FIRST_NOTIFICATION_JOB", oldContract, contractPaymentsDto);
                            } else if (escalationLevelId != null && escalationLevelId.equals(Constants.TWO)) {
                                //msgBody = secondTime(oldContract, contractPaymentsDto, serial, newEscalationLog);
                                msgBody = getMsgBody("SECOND_NOTIFICATION_JOB", oldContract, contractPaymentsDto);
                            }

                            logs += "; V_SERIAL = " + serial + "; V_INVESTOR_NAME = " + oldContract.getPersonName() + "; V_ID_TYPE_CODE = " + oldContract.getIdTypeCode() + "; V_IDENTIFICATION_NO = " + oldContract.getIdentificationNo() + "; v_CONTRACT_NO = " + oldContract.getContractNo() + "; FORM_AMOUNT = " + contractPaymentsDto.getFormAmount();

                            // Save Persons Message
                            savePersonsMessage(msgBody, mobileNo.toString());

                            sentStatus = Constants.ONE;
                        } else {
                            msgBody = "لم يتم ارسال الرسالة لعدم توفر رقم الجوال ";
                            sentStatus = Constants.ZERO;
                        }

                        logs += "; V$MSG = " + msgBody + "; V_SENT_STATUS = " + sentStatus;

                        // Create Escalation Log
                        ImsEscalationLog newEscalationLog = toImsEscalationLog(oldContract, contractPaymentsDto, serial, mobileNo, escalationLevelId, sentStatus, msgBody);

                        logs += "; V_SETTING_ESCALATION_PERIOD = " + newEscalationLog.getSettingEscalationPeriod() + "; V_ESCALATION_LEVEL = " + newEscalationLog.getEscalationLevel();

                        if (escalationLevelId != null && escalationLevelId.equals(Constants.ONE)) {
                            firstTime(oldContract, contractPaymentsDto, serial, sentStatus, newEscalationLog);
                        } else if (escalationLevelId != null && escalationLevelId.equals(Constants.TWO)) {
                            secondTime(oldContract, contractPaymentsDto, serial, newEscalationLog);
                        }

                    } catch (Exception e) {
                        // not sent log for each bill
                        checkError = checkError + 1;
                        guidSeq = ++guidSeq;
                        imsUserLogDao.addImsUserLog("IMS_API_JOB_SEND_ESCALATION", USER_CODE, "EXP-R", "BILL_ACCOUNT = " + contractPaymentsDto.getBillAccount(), "EXCEPTION#REC#LOOP", guid, guidSeq, logs + "\n ========> \n" + ExceptionUtils.getStackTrace(e));
                    }
                }

                // in case of sent errors, send message to admin
                if (checkError > 0) {
                    msgError = "حدث خطأ اثناء ارسال رسائل التصعيد في نظام الاستثمار لعدد " + checkError + " سجلات";
                    // get adminMobileNo
                    ImsSetting msgFromSetting = getImsSetting("ERROR_REPORTING");
                    String adminMobileNo = msgFromSetting.getParamValue();
                    // sendMsgToAdmin
                    if (msgError != null && adminMobileNo != null) {
                        savePersonsMessage(msgError, adminMobileNo);
                    }
                }
            }

            // finish log
            guidSeq = ++guidSeq;
            imsUserLogDao.addImsUserLog("IMS_API_JOB_SEND_ESCALATION", USER_CODE, "F", "FINISH", "END#CALL#API_JOB", guid, guidSeq, "DONE END API JOB");

        } catch (Exception e) {
            // not sent log for all bills
            guidSeq = ++guidSeq;
            imsUserLogDao.addImsUserLog("IMS_API_JOB_SEND_ESCALATION", USER_CODE, "E", "EXP-G", "EXCEPTION#GENERAL#ERROR", guid, guidSeq, logs + "\n ========> \n" + ExceptionUtils.getStackTrace(e));
        }

    }

    private ImsEscalationLog toImsEscalationLog(ImsContracts oldContract, ContractPaymentsDto contractPaymentsDto, Long serial, Long mobileNo, Long escalationLevelId, Long sentStatus, String msgBody) {

        ImsEscalationLog imsEscalationLog = new ImsEscalationLog(); //getContractData(contractPaymentsDto.getContractId());
        imsEscalationLog.setSerial(serial);
        imsEscalationLog.setMobileNo(mobileNo.toString());
        imsEscalationLog.setContractId(contractPaymentsDto.getContractNo());
        imsEscalationLog.setPaymentId(contractPaymentsDto.getPaymentId());
        imsEscalationLog.setBillAccount(contractPaymentsDto.getBillAccount());
        imsEscalationLog.setSendDate(new Date());
        imsEscalationLog.setIdTypeCode(oldContract.getIdTypeCode());
        imsEscalationLog.setIdentificationNo(oldContract.getIdentificationNo());
        imsEscalationLog.setInvestorName(oldContract.getPersonName());
        imsEscalationLog.setSentStatus(sentStatus);

        imsEscalationLog.setMsgBody(msgBody);

        imsEscalationLog.setEscalationLevel(escalationLevelId);
        imsEscalationLog.setActualEscalationPeriod(imsSmsDao.getActualEscalationPeriod(contractPaymentsDto.getFormDate().toString()));

        return imsEscalationLog;
    }

    @Transactional(rollbackFor = Exception.class)
    public ImsContracts getContractData(Long id) {

        Optional<ImsContracts> contractOp = imsContractRepository.findById(id);
        if (contractOp.isPresent()) {
            return contractOp.get();
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    public ImsSetting getImsSetting(String paramName) {

        Optional<ImsSetting> settingOp = imsSettingRepository.findById(paramName);
        if (settingOp.isPresent()) {
            return settingOp.get();
        }
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveNewContractData(ImsContracts oldContract) {
        ImsContracts newContract = new ImsContracts();
        BeanUtils.copyProperties(oldContract, newContract, "id", "cttId", "trxSerial", "warningDate", "warningDateHj", "warningSent", "iconpId");

        newContract.setCttId(Constants.TWO);  // cttId = 2
        newContract.setTrxSerial(oldContract.getTrxSerial() + Constants.ONE); // +1
        newContract.setWarningDate(new Date());
        newContract.setWarningDateHj(imsSmsDao.getGetCurrHijriDate());
        newContract.setWarningSent(Boolean.TRUE);  // warningSent = 1
        newContract.setIconpId(imsSmsDao.getIconPId(oldContract.getContractNo(), oldContract.getDirCode()));

        newContract = imsContractRepository.save(newContract);
        newContract.toString();
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveNewEscalationLogData(ImsEscalationLog newEscalationLog) {
        newEscalationLog = imsEscalationLogRepository.save(newEscalationLog);
        newEscalationLog.toString();
    }

    @Transactional(rollbackFor = Exception.class)
    public void savePersonsMessage(String msgBody, String mobileNo) {

        CmnPersonsMessageId personsMessageId = new CmnPersonsMessageId();
        personsMessageId.setMsgNumber(imsSmsDao.getPersonsMessageId());
        personsMessageId.setSendYear(Utils.getCurrentHijriDate().getLong(ChronoField.YEAR)); // TO_NUMBER(TO_CHAR(HEJRY_DATE.SYSTEM_DATE,'YYYY')

        CmnPersonsMessage personsMessage = new CmnPersonsMessage();
        personsMessage.setPersonsMessageId(personsMessageId);
        personsMessage.setIssuedFrom(Constants.THREE);
        personsMessage.setAppCode("IMS");
        personsMessage.setFunctionCode("0000");
        personsMessage.setSmsMsgLanguage(Constants.ONE);
        personsMessage.setMsgBody(msgBody);
        personsMessage.setValidHours(72L);
        personsMessage.setTimeToSend(new Date());
        personsMessage.setMobileNo(mobileNo);
        personsMessage.setMsgStatus(Constants.ZERO);
        personsMessage.setMsgTypeCode(Constants.ONE);
        personsMessage.setPriority(Constants.ONE);
        personsMessage.setSendMethodCode(Constants.ONE);

        cmnPersonsMessageRepository.save(personsMessage);
        personsMessage.toString();
    }

    @Transactional(rollbackFor = Exception.class)
    public void firstTime(ImsContracts oldContract, ContractPaymentsDto contractPaymentsDto, Long serial, Long sentStatus, ImsEscalationLog newEscalationLog) {

        // get msgBody
        /*ImsSetting msgFromSetting = getImsSetting("FIRST_NOTIFICATION_JOB");
        String msgBody = msgFromSetting.getParamValue();
        msgBody = msgBody.replace(Constants.HASH_1, oldContract.getPersonName());
        msgBody = msgBody.replace(Constants.HASH_2, oldContract.getIdentificationNo());
        msgBody = msgBody.replace(Constants.HASH_3, oldContract.getContractNo());
        msgBody = msgBody.replace(Constants.HASH_4, contractPaymentsDto.getBillAccount());
        msgBody = msgBody.replace(Constants.HASH_5, contractPaymentsDto.getFormAmount().toString());
        newEscalationLog.setMsgBody(msgBody);*/

        // get Escalation Period
        ImsSetting periodFromSetting = getImsSetting("FIRST_ESCALATION_PERIOD");
        newEscalationLog.setSettingEscalationPeriod(Long.valueOf(periodFromSetting.getParamValue()));

        // save New Escalation Log
        saveNewEscalationLogData(newEscalationLog);

        // save New Contract Data
        if (sentStatus.equals(Constants.ONE)) {
            saveNewContractData(oldContract);
        }

        imsSmsDao.updateImsContractPaymentsFirstEscalation(serial, contractPaymentsDto.getPaymentId());

        //return msgBody;

    }

    @Transactional(rollbackFor = Exception.class)
    public void secondTime(ImsContracts oldContract, ContractPaymentsDto contractPaymentsDto, Long serial, ImsEscalationLog newEscalationLog) {

        // get msgBody
        /*ImsSetting msgFromSetting = getImsSetting("SECOND_NOTIFICATION_JOB");
        String msgBody = msgFromSetting.getParamValue();
        msgBody = msgBody.replace(Constants.HASH_1, oldContract.getPersonName());
        msgBody = msgBody.replace(Constants.HASH_2, oldContract.getIdentificationNo());
        msgBody = msgBody.replace(Constants.HASH_3, oldContract.getContractNo());
        msgBody = msgBody.replace(Constants.HASH_4, contractPaymentsDto.getBillAccount());
        msgBody = msgBody.replace(Constants.HASH_5, contractPaymentsDto.getFormAmount().toString());
        newEscalationLog.setMsgBody(msgBody);*/

        // get Escalation Period
        ImsSetting periodFromSetting = getImsSetting("SECOND_ESCALATION_PERIOD");
        newEscalationLog.setSettingEscalationPeriod(Long.valueOf(periodFromSetting.getParamValue()));

        // save New Escalation Log
        saveNewEscalationLogData(newEscalationLog);

        imsSmsDao.updateImsContractPaymentsSecondEscalation(serial, contractPaymentsDto.getPaymentId());

        //return msgBody;
    }

    public String getMsgBody(String escalationLevelDesc, ImsContracts oldContract, ContractPaymentsDto contractPaymentsDto) {
        // get msgBody
        ImsSetting msgFromSetting = getImsSetting(escalationLevelDesc);
        String msgBody = msgFromSetting.getParamValue();
        msgBody = msgBody.replace(Constants.HASH_1, oldContract.getPersonName());
        msgBody = msgBody.replace(Constants.HASH_2, oldContract.getIdentificationNo());
        msgBody = msgBody.replace(Constants.HASH_3, oldContract.getContractNo());
        msgBody = msgBody.replace(Constants.HASH_4, contractPaymentsDto.getBillAccount());
        msgBody = msgBody.replace(Constants.HASH_5, contractPaymentsDto.getFormAmount().toString());
        return msgBody;
    }

}