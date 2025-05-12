package sa.gov.amana.alriyadh.job.services;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sa.gov.amana.alriyadh.job.dao.AuditDao;

import java.net.InetAddress;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
public class AuditService {

    private final AuditDao auditDao;


    public void addAudit(String serviceName, String serviceUrl, String payload, String response, Date beforeDate, Long statusCode, String statusMessage) {
        try {
            Map<String, Object> inputParams = new HashMap<String, Object>();

            //String clientIp = request.getHeader("X-FORWARDED-FOR") == null ? request.getRemoteAddr() : request.getHeader("X-FORWARDED-FOR");  //Client IP Address
            //InetAddress inetAddress = InetAddress.getByName(request.getRemoteAddr());
            //String f5Ip = inetAddress.getHostAddress(); //F5Ip IP Address

            String serverIp = InetAddress.getLoopbackAddress().getHostAddress(); //Server IP Address
            String notes = "Status is " + statusCode + " : " + statusMessage + " for Call Service " + serviceName + " - Service URL: " + serviceUrl;

            inputParams.put("p_service_name", "notifyElectricityService");
            inputParams.put("p_APP_CODE", "BLS");
            inputParams.put("p_client_ip", serverIp);
            inputParams.put("p_pInput", payload);
            inputParams.put("p_poutput", response);
            inputParams.put("p_status_code", statusCode);
            inputParams.put("p_status_desc", statusMessage);
            inputParams.put("p_full_status_desc", " ");
            inputParams.put("p_note", notes);
            inputParams.put("p_service_id", 24);
            inputParams.put("P_SERVER_IP", serverIp);
            inputParams.put("P_F5_IP", serverIp);
            inputParams.put("P_F5", serverIp);
            inputParams.put("P_SOURCE_TYPE", "Schedule");
            inputParams.put("p_call_date", new Date());
            inputParams.put("p_before_call_date", beforeDate);
            inputParams.put("p_after_call_date", new Date());
            inputParams.put("P_USER_CODE", "");
            inputParams.put("P_DIR_CODE", null);

            Object auditSerial = auditDao.addApiAudit(inputParams);

            //log.info("Service Call Audit Serial is: "+auditSerial);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
