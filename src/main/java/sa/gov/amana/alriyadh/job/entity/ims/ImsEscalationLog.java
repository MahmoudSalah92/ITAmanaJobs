package sa.gov.amana.alriyadh.job.entity.ims;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@ToString
@Entity
@Table(name = "IMS_ESCALATION_LOG", schema = "IMS")
public class ImsEscalationLog {

    @Id
    @Column(name = "SERIAL", nullable = false)
    private Long serial;

    @Column(name = "CONTRACT_ID")
    private Long contractId;

    @Column(name = "PAYMENT_ID")
    private Long paymentId;

    @Column(name = "ESCALATION_LEVEL")
    private Long escalationLevel;

    @Column(name = "SETTING_ESCALATION_PERIOD")
    private Long settingEscalationPeriod;

    @Column(name = "ACTUAL_ESCALATION_PERIOD")
    private Long actualEscalationPeriod;

    @Size(max = 12)
    @Column(name = "MOBILE_NO", length = 12)
    private String mobileNo;

    @Lob
    @Column(name = "MSG_BODY")
    private String msgBody;

    @Column(name = "SENT_STATUS")
    private Long sentStatus;

    @Column(name = "LOG_DATE")
    private LocalDate logDate;

    @Size(max = 1000)
    @Column(name = "INVESTOR_NAME", length = 1000)
    private String investorName;

    @Size(max = 2000)
    @Column(name = "BILL_ACCOUNT", length = 2000)
    private String billAccount;

    @Size(max = 500)
    @Column(name = "TO_EMAIL", length = 500)
    private String toEmail;

    @Size(max = 500)
    @Column(name = "CC_EMAIL", length = 500)
    private String ccEmail;

    @Size(max = 500)
    @Column(name = "BCC_EMAIL", length = 500)
    private String bccEmail;

    @Column(name = "SEND_DATE")
    private Date sendDate;

    @Column(name = "ID_TYPE_CODE")
    private Long idTypeCode;

    @Size(max = 20)
    @Column(name = "IDENTIFICATION_NO", length = 20)
    private String identificationNo;

}