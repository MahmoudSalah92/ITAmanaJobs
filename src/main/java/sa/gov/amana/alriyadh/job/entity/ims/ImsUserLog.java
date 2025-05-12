package sa.gov.amana.alriyadh.job.entity.ims;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import sa.gov.amana.alriyadh.job.entity.cmn.CmnPersonsMessageId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@ToString
@Getter
@Setter
@Entity
@Table(name = "IMS_USER_LOGS", schema = "IMS")
public class ImsUserLog {

    @EmbeddedId
    private ImsUserLogId imsUserLogId;

    @Size(max = 500)
    @NotNull
    @Column(name = "MODULE_ID", nullable = false, length = 500)
    private String moduleId;

    @Size(max = 100)
    @NotNull
    @Column(name = "USER_CODE", nullable = false, length = 100)
    private String userCode;

    @Size(max = 500)
    @NotNull
    @Column(name = "TRANS_TYPE", nullable = false, length = 500)
    private String transType;

    @Size(max = 2000)
    @Column(name = "MODULE_KEY", length = 2000)
    private String moduleKey;

    @Lob
    @Column(name = "LOGS")
    private String logs;

    @Size(max = 4000)
    @Column(name = "REMARKS", length = 4000)
    private String remarks;

    @Lob
    @Column(name = "DBMS_OUTPUT_LINES")
    private String dbmsOutputLines;

    @NotNull
    @Column(name = "TRANS_HEJRY_DATE", nullable = false)
    private Date transHejryDate;

    @NotNull
    @Column(name = "TRANS_TIME", nullable = false)
    private Date transTime;

    @Size(max = 100)
    @Column(name = "IP_ADDRESS", length = 100)
    private String ipAddress;

    @Size(max = 200)
    @Column(name = "MACHINE_NAME", length = 200)
    private String machineName;

    @Size(max = 200)
    @Column(name = "OS_USER_NAME", length = 200)
    private String osUserName;


}