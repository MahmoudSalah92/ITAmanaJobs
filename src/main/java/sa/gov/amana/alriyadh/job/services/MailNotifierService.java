package sa.gov.amana.alriyadh.job.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sa.gov.amana.alriyadh.job.dao.AuditDao;
import sa.gov.amana.alriyadh.job.dao.MailNotifierRequestDao;
import sa.gov.amana.alriyadh.job.utils.MailUtil;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.util.*;

import static sa.gov.amana.alriyadh.job.utils.MailUtil.validEmailAddress;

@Service
@Slf4j
@AllArgsConstructor
public class MailNotifierService {

    private final MailNotifierRequestDao mailNotifierRequestDao;
    private final AuditDao auditDao;


    // Send internal mail using RMS RecibientNum & MessageId.
    public Map<String, Object> sendRmsScheduleEmails() {

        Date beforeDate = new Date();
        Map<String, Object> resultSet = new HashMap<String, Object>();
        Map<String, Object> outputData = new HashMap<String, Object>();
        List<Map<String, Object>> sendEmailOut = new ArrayList<Map<String, Object>>();

        List<Map<String, Object>> rmsRowSet = mailNotifierRequestDao.getRmsScheduleRecipients();
        if (rmsRowSet != null && rmsRowSet.size() > 0) {
            for (Map<String, Object> row : rmsRowSet) {
                //log.info("##### sendRmsScheduleEmails Mail rmsRowSet row number: " + rmsRowSet.indexOf(row) + " #RECIPIENT_NUMBER: " + row.get("RECIPIENT_NUMBER"));
                String mailTemplate = (String) row.get("TEMPLATE_TXT");
                String mailBody = mailTemplate.replace("{##BODY}", (String) row.get("EMAIL_BODY"));
                String mailHeader = (String) row.get("EMAIL_HEADER");
                String mailSender = validEmailAddress((String) row.get("SENDER_MAIL"));
                String senderTitle = (String) row.get("SENDER_TITLE");
                String mailReciever = validEmailAddress((String) row.get("EMAIL_ADDRESS"));
                String mailCC = validEmailAddress((String) row.get("CC_ADDRESS"));
                String mailBCC = validEmailAddress((String) row.get("BCC_ADDRESS"));
                String emailUser = (String) row.get("EMAIL_USER");
                String emailPassword = (String) row.get("EMAIL_PASSWORD");
                BigDecimal messageId = (BigDecimal) row.get("MESSAGE_ID");
                BigDecimal recipientNum = (BigDecimal) row.get("RECIPIENT_NUMBER");
                boolean mailSent = false;
                //log.info("##### sendRmsScheduleEmails Mail isValidEmailAddress starttttttttttt....");
                mailSent = MailUtil.sendEmail(mailSender, senderTitle, mailReciever, mailCC, mailBCC,
                        mailHeader, mailBody, 1, emailUser, emailPassword, null, null);
                //log.info("##### sendRmsScheduleEmails Mail isValidEmailAddress Endddddddddddddd mailSent flag: "+ mailSent);

                Map<String, Object> recipientObj = new LinkedHashMap<>();
                recipientObj.put("RECIPIENT_NUMBER", row.get("RECIPIENT_NUMBER"));
                recipientObj.put("MESSAGE_ID", row.get("MESSAGE_ID"));
                recipientObj.put("USER_CODE", row.get("USER_CODE"));
                recipientObj.put("TEMPLATE_NO", row.get("TEMPLATE_NO"));
                if (mailSent) {
                    recipientObj.put("EMAIL_STATUS", "SENT");
                    mailNotifierRequestDao.updateRmsMsgRecipient(messageId.intValue(), recipientNum.intValue());
                    mailNotifierRequestDao.updateRmsGlMessages(messageId.intValue());
                } else {
                    recipientObj.put("EMAIL_STATUS", "FAIL");
                }
                sendEmailOut.add(recipientObj);
            }
        } else {
            Map<String, Object> recipientObj = new LinkedHashMap<>();
            recipientObj.put("OUTPUT_MESSAGE", "No messages found (empty).");
            sendEmailOut.add(recipientObj);
        }

        outputData.put("ResultSet", sendEmailOut);
        resultSet = returnJsonDataTemp(1, outputData, null, null);


        if (!sendEmailOut.isEmpty()) {
            addMailAudit(sendEmailOut.toString(), resultSet.toString(), beforeDate);
        }
        return resultSet;
    }

    // returnJsonDataTemp --> (main template of return json data success of fail)
    public Map<String, Object> returnJsonDataTemp(int flage, Map<String, Object> outputData, String errorCode,
                                                  String errorMsg) {
        Map<String, Object> tempJsonData = new HashMap<String, Object>();
        Map<String, Object> statusDetails = new HashMap<String, Object>();
        if (flage == 1) { // success Process
            statusDetails.put("StatusCode", "200");
            statusDetails.put("StatusMessage", "Success Process");
            tempJsonData.put("OutputData", outputData);
        } else { // 2 fail
            statusDetails.put("StatusCode", errorCode);
            statusDetails.put("StatusMessage", errorMsg);
        }
        tempJsonData.put("StatusDetails", statusDetails);
        return tempJsonData;
    }

    public void addMailAudit(String payload, String response, Date beforeDate) {
        try {
            Map<String, Object> inputParams = new HashMap<String, Object>();
            //String clientIp = request.getHeader("X-FORWARDED-FOR") == null ? request.getRemoteAddr() : request.getHeader("X-FORWARDED-FOR");  //Client IP Address
            //InetAddress inetAddress = InetAddress.getByName(request.getRemoteAddr());
            //String f5Ip = inetAddress.getHostAddress(); //F5Ip IP Address
            String serverIp = InetAddress.getLoopbackAddress().getHostAddress(); //Server IP Address
            String notes = "Status is " + 200 + ", "
                    + "OK" + " for call service " + "sendRmsScheduleEmails"
                    + " - Service URL: " + "/serv/api/job";
            inputParams.put("p_service_name", "sendRmsScheduleEmails");
            inputParams.put("p_APP_CODE", "RMS");
            inputParams.put("p_client_ip", serverIp);
            inputParams.put("p_pInput", payload);
            inputParams.put("p_poutput", response);
            inputParams.put("p_status_code", 200);
            inputParams.put("p_status_desc", "OK");
            inputParams.put("p_full_status_desc", " ");
            inputParams.put("p_note", notes);
            inputParams.put("p_service_id", 24);
            inputParams.put("P_SERVER_IP", serverIp);
            inputParams.put("P_F5_IP", serverIp);
            inputParams.put("P_F5", serverIp);
            inputParams.put("P_SOURCE_TYPE", "Schedule");
            inputParams.put("p_call_date", new Date());
            inputParams.put("p_before_call_date", beforeDate);
            Date afterDate = new Date();
            inputParams.put("p_after_call_date", afterDate);
            inputParams.put("P_USER_CODE", "");
            inputParams.put("P_DIR_CODE", null);
            Object auditSerial = auditDao.addApiAudit(inputParams);
            //log.info("Service Call Audit Serial is: "+auditSerial);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
