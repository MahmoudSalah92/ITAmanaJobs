package sa.gov.amana.alriyadh.job.dao.ims;

import java.sql.Types;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import sa.gov.amana.alriyadh.job.dto.ims.ImsEscalationLogDto;

@Component
@RequiredArgsConstructor
public class UpdateImsSmsStatusDao {

    private final JdbcTemplate jdbcTemplate;

    private static final String SELECT_IMS_ESCALATION_LOG = "SELECT * FROM IMS.IMS_ESCALATION_LOG log " +
            "WHERE log.SENT_STATUS = 1 " +
            "AND log.SMS_CREATED_DATE IS NULL " +
            "AND log.SMS_RECEIVED_DATE IS NULL " +
            "AND ROWNUM <= 1";

    private static final String UPDATE_IMS_ESCALATION_LOG = "UPDATE IMS.IMS_ESCALATION_LOG log SET " +
            " log.SMS_CREATED_DATE = ?, " +
            " log.SMS_RECEIVED_DATE = ?, " +
            " log.SMS_STATUS_CODE = ?, " +
            " log.SMS_STATUS_MSG = ? " +
            " WHERE log.SERIAL = ?";

    // public List<Map<String, Object>> getImsEscalationLog() {
    // List<Map<String, Object>> resultSet = new ArrayList<Map<String, Object>>();
    // resultSet = jdbcTemplate.queryForList(IMS_SELECT_IMS_ESCALATION_LOG);
    // return resultSet;
    // }

    public List<ImsEscalationLogDto> getImsEscalationLog() {
//		return jdbcTemplate.query(SELECT_IMS_ESCALATION_LOG,
//				(rs, rowNum) -> new ImsEscalationLogDto(rs.getLong("serial"), rs.getString("campaignId")));

        // TODO
        return null;
    }

    public int updateImsEscalationLog(Date smsCreatedDate, Date smsReceivedDate, String providerStatusCode,
                                      String providerStatusMsg, Long escalationLogSerial) {
        Object[] params = {smsCreatedDate, smsReceivedDate, providerStatusCode, providerStatusMsg,
                escalationLogSerial};
        int[] types = {Types.DATE, Types.DATE, Types.VARCHAR, Types.VARCHAR, Types.NUMERIC};
        int rowsAffected = jdbcTemplate.update(UPDATE_IMS_ESCALATION_LOG, params, types);
        System.out.println("Update-Result::: " + rowsAffected);
        return rowsAffected;
    }

}
