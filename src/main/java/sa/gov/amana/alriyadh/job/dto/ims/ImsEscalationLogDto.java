package sa.gov.amana.alriyadh.job.dto.ims;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;

import java.sql.Clob;
import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImsEscalationLogDto {

    private Long serial;
    private String campaignId;

    private Long contractId;
    private Long payment_id;
    private Long escalationLevel;
    private Long setting_escalationPeriod;
    private Long actual_escalationPeriod;
    private String mobileNo;
    private Clob msgBody;
    private Long sentStatus;
    private Long logDate;
    private String investorName;
    private String billAccount;
    private String toEmail;
    private String ccEmail;
    private String bccEmail;
    private Date sendDate;
    private Long idTypeCode;
    private Long identificationNo;

}
