package sa.gov.amana.alriyadh.job.entity.cmn;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "CMN_PERSONS_MESSAGES")
public class CmnPersonsMessage {

    @EmbeddedId
    private CmnPersonsMessageId personsMessageId;

    @NotNull
    @Column(name = "SEND_METHOD_CODE", nullable = false)
    private Long sendMethodCode;

    @NotNull
    @Column(name = "ISSUED_FROM", nullable = false)
    private Long issuedFrom;

    @Size(max = 3)
    @NotNull
    @Column(name = "APP_CODE", nullable = false, length = 3)
    private String appCode;

    @Size(max = 4)
    @NotNull
    @Column(name = "FUNCTION_CODE", nullable = false, length = 4)
    private String functionCode;

    @NotNull
    @Column(name = "SMS_MSG_LANGUAGE", nullable = false)
    private Long smsMsgLanguage;

    @NotNull
    @Column(name = "MSG_TYPE_CODE", nullable = false)
    private Long msgTypeCode;

    @Size(max = 4000)
    @NotNull
    @Column(name = "MSG_BODY", nullable = false, length = 4000)
    private String msgBody;

    @Size(max = 15)
    @Column(name = "MOBILE_NO", length = 15)
    private String mobileNo;

    @Column(name = "ID_TYPE_CODE")
    private Boolean idTypeCode;

    @Size(max = 240)
    @Column(name = "IDENTIFICATION_NO", length = 240)
    private String identificationNo;

    @Column(name = "DIR_CODE")
    private Integer dirCode;

    @Column(name = "SECTION_CODE")
    private Long sectionCode;

    @Size(max = 100)
    @Column(name = "TARGET", length = 100)
    private String target;

    @Column(name = "RESEND_COUNT")
    private Long resendCount;

    @Column(name = "SEND_DATE")
    private Date sendDate;

    @Column(name = "DELIVERY_DATE")
    private Date deliveryDate;

    @Size(max = 300)
    @Column(name = "MSG_KEY", length = 300)
    private String msgKey;

    @Size(max = 1000)
    @Column(name = "MSG_SUBJECT", length = 1000)
    private String msgSubject;

    @NotNull
    @Column(name = "MSG_STATUS", nullable = false)
    private Long msgStatus;

    @Size(max = 1000)
    @Column(name = "FAIL_REASON", length = 1000)
    private String failReason;

    @NotNull
    @Column(name = "VALID_HOURS", nullable = false)
    private Long validHours;

    @NotNull
    @Column(name = "PRIORITY", nullable = false)
    private Long priority;

    @NotNull
    @Column(name = "TIME_TO_SEND", nullable = false)
    private Date timeToSend;

    @Column(name = "ISSUE_DATE")
    private Date issueDate;

    @Size(max = 12)
    @Column(name = "EMPLOYEE_NO", length = 12)
    private String employeeNo;

    @Column(name = "EMPLOYEE_TYPE")
    private Long employeeType;

}