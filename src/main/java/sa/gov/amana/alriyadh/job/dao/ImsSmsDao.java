package sa.gov.amana.alriyadh.job.dao;

import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import sa.gov.amana.alriyadh.job.dto.ims.*;

import java.sql.Clob;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;
import java.util.List;

@Component
@AllArgsConstructor
public class ImsSmsDao {

    private final JdbcTemplate jdbcTemplate;

    private static final String GET_CONTRACT_PAYMENTS_DATA = "SELECT CP.ID,\n" + "                   CP.ICON_ID,\n" + "                   PF.FORM_DATE,\n" + "                   CP.BILL_ACCOUNT,\n" + "                   CP.SERIAL,\n" + "                   PF.FORM_AMOUNT\n" + "              FROM IMS.IMS_CONTRACT_PAYMENTS CP, RVU.RVN_PAYMENT_FORMS PF\n" + "             WHERE CP.BILL_ACCOUNT = PF.BILL_ACCOUNT\n" + "                AND PF.PAYMENT_TYPE <> 1\n" + "                AND CP.STATUS = 2\n" + "                AND ((NVL (PF.CANCLED, 0) = 0) OR (NVL (PF.REFUND, 0) = 0))\n" + "                AND TRUNC (PF.FORM_DATE) >=\n" + "                    TO_DATE (NVL (GET_SETTING_VALUE ('ESCALATION_START_DATE', NULL),'29-05-2024'), 'DD-MM-YYYY')\n" + "                AND NOT EXISTS\n" + "                           (SELECT 1 FROM IMS.IMS_ESCALATION_LOG EL\n" + "                             WHERE EL.CONTRACT_ID = CP.ICON_ID\n" + "                               AND EL.PAYMENT_ID = CP.ID\n" + "                               AND NVL (ESCALATION_LEVEL, 0) = 2\n" + "                               AND NVL (SENT_STATUS, 0) = 1)";

    private static final String GET_ESCALATION_LEVEL = "SELECT EL.ESCALATION_LEVEL, EL.SENT_STATUS, EL.SEND_DATE\n" + "                      FROM (SELECT EL_1.*, MAX (EL_1.ESCALATION_LEVEL)\n" + "                        OVER (PARTITION BY EL_1.CONTRACT_ID,\n" + "                           EL_1.PAYMENT_ID)    MAX_ESCALATION_LEVEL\n" + "                                FROM IMS.IMS_ESCALATION_LOG EL_1\n" + "                               WHERE EL_1.PAYMENT_ID = ?\n" + "                                 AND EL_1.CONTRACT_ID = ?\n" + "                            ORDER BY SERIAL DESC) EL\n" + "                     WHERE ROWNUM = 1";

    private static final String GET_IMS_ESCALATION_LOG_SERIAL = "SELECT NVL (MAX (SERIAL), 0) + 1 FROM IMS.IMS_ESCALATION_LOG";

    /*private static final String GET_INVESTOR_INFO = " SELECT PERSON_NAME, ID_TYPE_CODE, IDENTIFICATION_NO, CONTRACT_NO, DIR_CODE "
            + "FROM IMS.IMS_CONTRACTS CON "
            + "WHERE TRX_SERIAL = 0 AND CON.ID =?";*/

    private static final String GET_MOBILE_NO = "SELECT CP.MOBILE_NO FROM CMNV3.CMN_PERSONS CP " + "               WHERE (CP.ID_TYPE_CODE, CP.IDENTIFICATION_NO) IN\n" + "                       (SELECT ID_TYPE_CODE, IDENTIFICATION_NO\n" + "                          FROM IMS.IMS_CONTRACTS CON\n" + "                         WHERE TRX_SERIAL = 0 AND CON.ID = ?)"; // P_CONTRACT_ID

    private static final String GET_CURR_HIJRI_DATE = "SELECT TO_DATE(TO_CHAR(CMNV3.HEJRY_DATE.SYSTEM_DATE, 'mi-mm-yyyy'), 'mi-mm-yyyy') FROM DUAL";

    private static final String GET_ICON_P_ID = "SELECT id FROM ims_contract_payments cp WHERE cp.id IN " + "(SELECT c.iconp_id FROM ims_contracts c WHERE c.contract_no =?  AND c.dir_code = NVL(?,0))";

    private static final String GET_MESSAGE_INFO = "SELECT CODE, DESCRIPTION, SMS_TEXT FROM IMS.IMS_SMS_MESSAGES where ID = ?";

    private static final String GET_ACTUAL_ESCALATION_PERIOD = "SELECT TRUNC (TRUNC (SYSDATE) - TRUNC (TO_DATE (?, 'yyyy-mm-dd'))) FROM DUAL";

    private static final String GET_PERSONS_MESSAGE_ID = "SELECT CMNV3.MSG_NUMBER.NEXTVAL FROM DUAL";

    private static final String UPDATE_FIRST_ESCALATION = "UPDATE IMS.IMS_CONTRACT_PAYMENTS SET FIRST_ESCALATION_ID = ? WHERE ID = ?";

    private static final String UPDATE_SECOND_ESCALATION = "UPDATE IMS.IMS_CONTRACT_PAYMENTS SET SECOND_ESCALATION_ID = ? WHERE ID = ?";


    public List<ContractPaymentsDto> getContractPaymentsData() {
        return jdbcTemplate.query(GET_CONTRACT_PAYMENTS_DATA, (rs, rowNum) -> new ContractPaymentsDto(rs.getLong("ID"), rs.getLong("ICON_ID"), rs.getLong("SERIAL"), rs.getString("BILL_ACCOUNT"), rs.getDate("FORM_DATE"), rs.getLong("FORM_AMOUNT")));
    }

    public List<EscalationLevelDto> getEscalationLevel(Long paymentId, Long contractId) {

        Object[] params = {paymentId, contractId};
        int[] types = {Types.NUMERIC, Types.NUMERIC};

        return jdbcTemplate.query(GET_ESCALATION_LEVEL, params, types, (rs, rowNum) -> new EscalationLevelDto(rs.getLong("ESCALATION_LEVEL"), rs.getLong("SENT_STATUS"), rs.getDate("SEND_DATE")));
    }

    public Long getImsEscalationLogSerial() {
        return jdbcTemplate.queryForObject(GET_IMS_ESCALATION_LOG_SERIAL, new Object[]{}, Long.class);
    }

    public Long getPersonsMessageId() {
        return jdbcTemplate.queryForObject(GET_PERSONS_MESSAGE_ID, new Object[]{}, Long.class);
    }

    public Long getMobileNo(Long contractId) {
        return jdbcTemplate.queryForObject(GET_MOBILE_NO, new Long[]{contractId}, Long.class);
    }

    public Timestamp getGetCurrHijriDate() {
        return jdbcTemplate.queryForObject(GET_CURR_HIJRI_DATE, new Object[]{}, Timestamp.class);
    }

    public Long getActualEscalationPeriod(String formDate) {
        return jdbcTemplate.queryForObject(GET_ACTUAL_ESCALATION_PERIOD, new Object[]{formDate}, Long.class);
    }

    public Long getIconPId(String contractNo, Long dirCode) {
        try {
            Object[] params = {contractNo, dirCode};
            int[] types = {Types.VARCHAR, Types.NUMERIC};

            return jdbcTemplate.queryForObject(GET_ICON_P_ID, params, types, Long.class);

        } catch (EmptyResultDataAccessException e) {
            // No rows returned, handle appropriately (return null or a default value)
            return null;
        }
    }

    public List<MessageInfoDto> getMessageInfo(Integer messageId) {

        Object[] params = {messageId};
        int[] types = {Types.INTEGER};

        return jdbcTemplate.query(GET_MESSAGE_INFO, params, types, (rs, rowNum) -> new MessageInfoDto(rs.getLong("CODE"), rs.getString("DESCRIPTION"), rs.getString("SMS_TEXT")));
    }

    public int updateImsContractPaymentsFirstEscalation(Long serial, Long paymentId) {
        int result = 0;
        Object[] params = {serial, paymentId};
        int[] types = {Types.NUMERIC, Types.NUMERIC};
        result = jdbcTemplate.update(UPDATE_FIRST_ESCALATION, params, types);
        return result;
    }

    public int updateImsContractPaymentsSecondEscalation(Long serial, Long paymentId) {
        int result = 0;
        Object[] params = {serial, paymentId};
        int[] types = {Types.NUMERIC, Types.NUMERIC};
        result = jdbcTemplate.update(UPDATE_SECOND_ESCALATION, params, types);
        return result;
    }

}
