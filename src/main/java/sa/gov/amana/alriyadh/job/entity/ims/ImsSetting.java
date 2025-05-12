package sa.gov.amana.alriyadh.job.entity.ims;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@Entity
@Table(name = "IMS_SETTINGS", schema = "IMS")
public class ImsSetting {

    @Id
    @Size(max = 100)
    @NotNull
    @Column(name = "PARAM_NAME", nullable = false, length = 100)
    private String paramName;

    @Column(name = "DIR_CODE")
    private Integer dirCode;

    @Size(max = 1)
    @Column(name = "PARAM_TYPE", length = 1)
    private String paramType;

    @Size(max = 1000)
    @Column(name = "PARAM_VALUE", length = 1000)
    private String paramValue;

    @Size(max = 500)
    @Column(name = "PARAM_DESC", length = 500)
    private String paramDesc;

    @Column(name = "STATUS")
    private Boolean status;

    @Size(max = 1000)
    @Column(name = "REMARKS", length = 1000)
    private String remarks;

}