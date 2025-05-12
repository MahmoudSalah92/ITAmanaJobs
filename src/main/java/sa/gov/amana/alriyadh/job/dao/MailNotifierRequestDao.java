package sa.gov.amana.alriyadh.job.dao;


import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Component
@AllArgsConstructor
public class MailNotifierRequestDao {

    private final JdbcTemplate jdbcTemplate;

    private static final String RMS_SCHEDULE_RECIPIENTS = "SELECT * FROM TABLE (RMS.RMS_PKG_N.get_email_data ())";
    private static final String RMS_GL_MESSAGE_RECIPIENTS = "UPDATE RMS.RMS_GL_MESSAGE_RECIPIENTS mr SET mr.DELIVERY_DATE = SYSDATE, mr.EMAIL_STATUS = 2 WHERE mr.MESSAGE_ID = ? AND mr.RECIPIENT_NUMBER = ?";
    private static final String RMS_GL_MESSAGES = "UPDATE RMS.RMS_GL_MESSAGES R SET R.EMAIL_STATUS = 2 WHERE MESSAGE_ID = ?";


    public List<Map<String, Object>> getRmsScheduleRecipients() {
        List<Map<String, Object>> resultSet = new ArrayList<Map<String, Object>>();
        resultSet = jdbcTemplate.queryForList(RMS_SCHEDULE_RECIPIENTS);
        return resultSet;
    }

    public void updateRmsMsgRecipient(Integer msgId, Integer recipientNum) {
        int result = 0;
        Object[] params = {msgId, recipientNum};
        int[] types = {Types.INTEGER, Types.INTEGER};
        result = jdbcTemplate.update(RMS_GL_MESSAGE_RECIPIENTS, params, types);
    }

    public void updateRmsGlMessages(Integer msgId) {
        int result = 0;
        Object[] params = {msgId};
        int[] types = {Types.INTEGER};
        result = jdbcTemplate.update(RMS_GL_MESSAGES, params, types);
    }

}
