package sa.gov.amana.alriyadh.job.dao;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@AllArgsConstructor
public class AuditDao {

    private final JdbcTemplate jdbcTemplate;

    @Async
    public Object addApiAudit(Map<String, Object> datafields) {
        Object result = null;
        Map<String, Object> outParams = new HashMap<String, Object>();
        try {
            MapSqlParameterSource params = new MapSqlParameterSource(datafields);
            //jdbcTemplate.setResultsMapCaseInsensitive(true);
            SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withSchemaName("CMNV3")
                    .withFunctionName("ADD_SERVICE_AUD_ALL_F");
            params.addValue("p_service_name", datafields.get("p_service_name"));
            params.addValue("p_app_code", datafields.get("p_APP_CODE"));
            params.addValue("p_client_ip", datafields.get("p_client_ip"));
            params.addValue("p_pinput", datafields.get("p_pInput"));
            params.addValue("p_poutput", datafields.get("p_poutput"));
            params.addValue("p_status_code", datafields.get("p_status_code"));
            params.addValue("p_status_desc", datafields.get("p_status_desc"));
            params.addValue("p_full_status_desc", datafields.get("p_full_status_desc"));
            params.addValue("p_note", datafields.get("p_note"));
            params.addValue("p_service_id", datafields.get("p_service_id"));
            params.addValue("p_server_ip", datafields.get("P_SERVER_IP"));
            params.addValue("p_f5", datafields.get("P_F5_IP"));
            params.addValue("p_source_type", datafields.get("P_SOURCE_TYPE"));
            params.addValue("p_call_date", new Date());
            params.addValue("p_before_call_date", datafields.get("p_before_call_date"));
            params.addValue("p_after_call_date", datafields.get("p_after_call_date"));
            params.addValue("P_USER_CODE", "");
            params.addValue("P_DIR_CODE", null);
            outParams.putAll(simpleJdbcCall.execute(params));
            if (outParams != null && outParams.size() > 0) {
                result = outParams.get("return");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

}
