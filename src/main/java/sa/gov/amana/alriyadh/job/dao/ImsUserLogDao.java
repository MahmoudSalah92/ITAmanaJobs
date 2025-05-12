package sa.gov.amana.alriyadh.job.dao;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;

@Component
@AllArgsConstructor
public class ImsUserLogDao {

    private final JdbcTemplate jdbcTemplate;
    private final ImsSmsDao imsSmsDao;

    private static final String INSERT_IMS_USER_LOG = "INSERT INTO IMS.IMS_USER_LOGS " + " (GUID, GUID_SEQ, MODULE_ID, USER_CODE, TRANS_TYPE, MODULE_KEY, REMARKS, TRANS_TIME, TRANS_HEJRY_DATE, IP_ADDRESS, OS_USER_NAME, LOGS) " + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    @Async
    public Object addImsUserLog(String moduleId, String userCode, String transType, String moduleKey, String remarks, String guid, Long guidSeq, String logs) {

        String serverIp = null;     // Server IP Address
        try {
            serverIp = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            serverIp = InetAddress.getLoopbackAddress().getHostAddress();
        }

        String hostName = null;     // OS_USER_NAME
        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (Exception e) {
            hostName = InetAddress.getLoopbackAddress().getHostName();
        }

        Timestamp transDateH = imsSmsDao.getGetCurrHijriDate(); // Get Hijri Date

        Object[] params = {guid, guidSeq, moduleId, userCode, transType, moduleKey, remarks, new Date(), transDateH, serverIp, hostName, logs};
        int[] types = {Types.VARCHAR, Types.NUMERIC, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.DATE, Types.DATE, Types.VARCHAR, Types.VARCHAR, Types.CLOB};

        Object result = 0;
        result = jdbcTemplate.update(INSERT_IMS_USER_LOG, params, types);
        return result;
    }

    /*public ImsUserLog toImsUserLog(String moduleId, String userCode, String transType, String moduleKey, String remarks, String guid, Long guid_seq) {
        String serverIp = InetAddress.getLoopbackAddress().getHostAddress(); // Server IP Address

        ImsUserLogId imsUserLogId = new ImsUserLogId();
        imsUserLogId.setGuid(guid);
        imsUserLogId.setGuidSeq(guid_seq);

        ImsUserLog imsUserLog = new ImsUserLog();
        imsUserLog.setImsUserLogId(imsUserLogId);

        imsUserLog.setModuleId(moduleId);
        imsUserLog.setUserCode(userCode != null ? userCode : "web_user");
        imsUserLog.setTransType(transType);
        imsUserLog.setModuleKey(moduleKey);
        imsUserLog.setRemarks(remarks);
        imsUserLog.setTransTime(new Date());
        imsUserLog.setTransHejryDate(imsSmsDao.getGetCurrHijriDate());

        // missing values
        imsUserLog.setLogs(null);
        imsUserLog.setDbmsOutputLines(null);
        imsUserLog.setIpAddress(serverIp);
        imsUserLog.setMachineName(null);
        imsUserLog.setOsUserName(null);

        return imsUserLog;
    }*/

}
