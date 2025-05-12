package sa.gov.amana.alriyadh.job.dao;


import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import sa.gov.amana.alriyadh.job.dto.electricity.CertificateOccupancyDto;

import java.sql.Types;
import java.util.Date;
import java.util.List;


@Component
@AllArgsConstructor
public class NotifyElectricityDao {

    private final JdbcTemplate jdbcTemplate;

    private static final String BLS_SELECT_CERTIFICATE_OCCUPANCY =
            "  SELECT LBL.LICENSE_YEAR || LPAD(LBL.LICENSE_NUMBER, 14 - LENGTH(LBL.LICENSE_YEAR), '0') AS electricityLicenseNumber , 11 AS notificationTypeId, \n" +
                    "               OSB_ACL.OSB_ACL_SERVICE_PKG.GET_REP_ONETIME_URL ('BMS3380W_BALADY','P$REQ_NO,P$REQ_YR', \n" +
                    "                       (SELECT MAX(REQUEST_NUMBER) FROM BLSPC.BLS_COMPLETIONS LIC WHERE LIC.LICENSE_BUILDING_NUMBER = LBL.LICENSE_NUMBER AND LIC.LICENSE_BUILDING_YEAR = LBL.LICENSE_YEAR) \n" +
                    "                        || ',' || (SELECT MAX(REQUEST_YEAR) FROM BLSPC.BLS_COMPLETIONS LIC WHERE LIC.LICENSE_BUILDING_NUMBER = LBL.LICENSE_NUMBER AND LIC.LICENSE_BUILDING_YEAR = LBL.LICENSE_YEAR)) AS attachmentFilePath, \n" +
                    "            LBL.LICENSE_NUMBER AS amanaLicenseNumber, \n" +
                    "               LBL.LICENSE_YEAR AS licenseYear \n" +
                    "   FROM BLSPC.BLS_LICENSE_BUILDINGS LBL,\n" +
                    "          (SELECT MAX ( LICENSE_DATE) AS LICENSE_DATE,LICENSE_BUILDING_NUMBER , LICENSE_BUILDING_YEAR \n" +
                    "           FROM   BLSPC.BLS_COMPLETIONS \n" +
                    "            WHERE  STATUS_NUMBER IS NULL \n" +
                    "             AND    OBJECTIVE_NUMBER = 1 \n" +
                    "             AND    SEND_FLAG is null \n" +
                    "             AND    LICENSE_DATE >= TO_DATE ('06-05-1441', 'MI-MM-YYYY') \n" +
                    "             GROUP BY LICENSE_BUILDING_NUMBER , LICENSE_BUILDING_YEAR)COMP \n" +
                    "    WHERE COMP.LICENSE_BUILDING_NUMBER = LBL.LICENSE_NUMBER \n" +
                    "     AND   COMP.LICENSE_BUILDING_YEAR = LBL.LICENSE_YEAR \n" +
                    "     AND   LBL.STATUS_NUMBER IS NULL \n" +
                    "     AND   ELECTRICITY_SEND_FLAG = 1 \n" +
                    "     AND ROWNUM <= 1";

    private static final String BLS_UPDATE_CERTIFICATE_OCCUPANCY = "UPDATE BLSPC.BLS_COMPLETIONS c  SET SEND_FLAG = ?, SEND_STATUS = ?, SEND_DATE = ?\n" +
            " WHERE c.LICENSE_BUILDING_NUMBER = ? AND c.LICENSE_BUILDING_YEAR = ?";


    public List<CertificateOccupancyDto> getCertificateOccupancyReport() {

        return jdbcTemplate.query(BLS_SELECT_CERTIFICATE_OCCUPANCY, (rs, rowNum) ->
                new CertificateOccupancyDto(
                        rs.getString("electricityLicenseNumber"),
                        rs.getString("notificationTypeId"),
                        rs.getString("attachmentFilePath"),
                        rs.getString("amanaLicenseNumber"),
                        rs.getString("licenseYear")
                ));
    }

    public void updateCertificateOccupancyReport(Integer flag, String sendStatus, Date sendDate, String amanaLicenseNumber, String licenseYear) {

        int result = 0;
        Object[] params = {flag, sendStatus, sendDate, amanaLicenseNumber, licenseYear};
        int[] types = {Types.NUMERIC, Types.VARCHAR, Types.DATE, Types.VARCHAR, Types.VARCHAR};
        result = jdbcTemplate.update(BLS_UPDATE_CERTIFICATE_OCCUPANCY, params, types);

    }

}
