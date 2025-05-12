package sa.gov.amana.alriyadh.job.services.ims;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import sa.gov.amana.alriyadh.job.clients.webclient.WebClientService;
import sa.gov.amana.alriyadh.job.constants.JobsConstants;
import sa.gov.amana.alriyadh.job.dao.ims.UpdateImsSmsStatusDao;
import sa.gov.amana.alriyadh.job.dto.etisalatsms.EtisalatSmsStatusResponseDto;
import sa.gov.amana.alriyadh.job.dto.ims.ImsEscalationLogDto;
import sa.gov.amana.alriyadh.job.entity.cmn.CmnServiceDetails;
import sa.gov.amana.alriyadh.job.services.AuditService;
import sa.gov.amana.alriyadh.job.services.CmnService;
import sa.gov.amana.alriyadh.job.utils.Utils;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
//import sun.misc.BASE64Encoder;

@Service
@Slf4j
@AllArgsConstructor
public class UpdateImsSmsStatusService {

    private final AuditService auditService;
    private final WebClientService webClientService;
    private final CmnService cmnService;
    private final UpdateImsSmsStatusDao updateImsSmsStatusDao;


    public void updateImsSmsStatus() {
        EtisalatSmsStatusResponseDto etisalatSmsStatusResponseDto = new EtisalatSmsStatusResponseDto();
        List<ImsEscalationLogDto> imsEscalationLogDtoRowSet = updateImsSmsStatusDao.getImsEscalationLog();
        if (imsEscalationLogDtoRowSet != null && imsEscalationLogDtoRowSet.size() > 0) {
            System.out.println("Start Loop imsEscalationLog " + imsEscalationLogDtoRowSet.size());
            for (ImsEscalationLogDto row : imsEscalationLogDtoRowSet) {
                etisalatSmsStatusResponseDto = getEtisalatSmsStatus("940535c1-599f-455b-88bc-5c8781772b9b"); // row.getCampaignId()
                if (etisalatSmsStatusResponseDto != null) {
                    if (Integer.parseInt(etisalatSmsStatusResponseDto.getResponseCode()) == 200) {
                        int rowsAffected = updateImsSmsStatusDao.updateImsEscalationLog(
                                Utils.unixTimeConverter(etisalatSmsStatusResponseDto.getCreatedDate()),
                                Utils.unixTimeConverter(etisalatSmsStatusResponseDto.getReceivedDate()),
                                etisalatSmsStatusResponseDto.getResponseCode(),
                                etisalatSmsStatusResponseDto.getResponseMessage(), row.getSerial());
                        if (rowsAffected > 0) {
                            System.out.println(rowsAffected + " rows updated successfully.");
                        } else {
                            System.out.println("No rows affected.");
                        }
                    } else {
                        int rowsAffected = updateImsSmsStatusDao.updateImsEscalationLog(null, null,
                                etisalatSmsStatusResponseDto.getResponseCode(),
                                etisalatSmsStatusResponseDto.getResponseMessage(), row.getSerial());
                        if (rowsAffected > 0) {
                            System.out.println(rowsAffected + " rows updated successfully.");
                        } else {
                            System.out.println("No rows affected.");
                        }
                    }
                } else {
                    System.out.println("Client is not available.");
                }
            }
        }
    }

    @Transactional
    public EtisalatSmsStatusResponseDto getEtisalatSmsStatus(String campaignId) {
        String authString;
        String authStringEnc;
        String url;

        CmnServiceDetails cmnServiceDetails = cmnService.getServiceDetails(99L, "EtisalatSmsStatus");

        String serviceUrl = cmnServiceDetails.getServUrl();
        String serviceName = cmnServiceDetails.getServName();
        String requestPayload = "";
        Date beforeDate = new Date();
        EtisalatSmsStatusResponseDto etisalatSmsStatusResponseDto = new EtisalatSmsStatusResponseDto();
        url = JobsConstants.ETISALAT_SMS_STATUS_URL + campaignId;
        authString = JobsConstants.API_OSB_USERNAME_KEY + ":" + JobsConstants.API_OSB_PASSWORD_KEY;
//		authStringEnc = new BASE64Encoder().encode(authString.getBytes());
        authStringEnc = java.util.Base64.getEncoder().encodeToString(authString.getBytes());
        Mono<String> responseStr = webClientService.createGet(url, "Basic", authStringEnc,
                new HashMap<String, String>());
        String responseObject = responseStr.toString();
        JSONObject responseJson = Utils.stringToJsonJackson(responseObject);
        String statusCode = responseJson.getString("httpCode");
        String statusMessage = responseJson.getString("httpMessage");
        if (statusCode != null && Integer.parseInt(statusCode) == 200) {
            JSONObject data = responseJson.getJSONObject("data");
            JSONObject result = data.getJSONObject("result");
            String smsCreatedDate = null;
            String smsReceivedDate = null;
            if (result != null && result.has("s_tm")) {
                smsCreatedDate = result.getString("s_tm");
            }
            if (result != null && result.has("c_tm")) {
                smsReceivedDate = result.getString("c_tm");
            }
            etisalatSmsStatusResponseDto.setCreatedDate(smsCreatedDate);
            etisalatSmsStatusResponseDto.setReceivedDate(smsReceivedDate);
            etisalatSmsStatusResponseDto.setResponseCode(statusCode);
            etisalatSmsStatusResponseDto.setResponseMessage(statusMessage);
            auditService.addAudit(serviceName, serviceUrl, requestPayload,
                    etisalatSmsStatusResponseDto.getResponseMessage(), beforeDate, Long.valueOf(statusCode),
                    statusMessage);
            return etisalatSmsStatusResponseDto;
        } else {
            etisalatSmsStatusResponseDto.setResponseCode(statusCode);
            etisalatSmsStatusResponseDto.setResponseMessage(statusMessage);
            auditService.addAudit(serviceName, serviceUrl, requestPayload,
                    etisalatSmsStatusResponseDto.getResponseMessage(), beforeDate, Long.valueOf(statusCode),
                    statusMessage);
            return etisalatSmsStatusResponseDto;
        }

    }

}
