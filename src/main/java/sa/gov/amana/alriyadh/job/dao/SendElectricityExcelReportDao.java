package sa.gov.amana.alriyadh.job.dao;


import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class SendElectricityExcelReportDao {

    private final JdbcTemplate jdbcTemplate;

    private static final String BLS_SELECT_ELECTRICITY_REPORT =
            " SELECT LIC.LICENSE_YEAR || LPAD (LIC.LICENSE_NUMBER, 14 - LENGTH (LIC.LICENSE_YEAR), '0') AS ELECTRICITY_LICENSE_NUMBER, \n" +
            "       LIC.LICENSE_NUMBER, LIC.LICENSE_YEAR,  LIC.SEND_FLAG, LIC.SEND_DATE \n" +
            "   FROM BLSPC.BLS_COMPLETIONS LIC \n" +
            "  WHERE LIC.SEND_FLAG IS NOT NULL AND TRUNC(LIC.SEND_DATE) = TRUNC(SYSDATE -1)";


    private static final String BLS_SELECT_ELECTRICITY_REPORT_SUCCESS =
            " SELECT LIC.LICENSE_YEAR || LPAD (LIC.LICENSE_NUMBER, 14 - LENGTH (LIC.LICENSE_YEAR), '0') AS ELECTRICITY_LICENSE_NUMBER, \n" +
                    "       LIC.LICENSE_NUMBER, LIC.LICENSE_YEAR,  'نجح' AS SEND_FLAG, LIC.SEND_DATE \n" +
                    "   FROM BLSPC.BLS_COMPLETIONS LIC \n" +
                    "  WHERE LIC.SEND_FLAG = 1 AND TRUNC(LIC.SEND_DATE) = TRUNC(SYSDATE -1)";


    public List<Map<String, Object>> getElectricityReport() {
        List<Map<String, Object>> resultSet = new ArrayList<Map<String, Object>>();
        resultSet = jdbcTemplate.queryForList(BLS_SELECT_ELECTRICITY_REPORT);
        return resultSet;
    }

}
