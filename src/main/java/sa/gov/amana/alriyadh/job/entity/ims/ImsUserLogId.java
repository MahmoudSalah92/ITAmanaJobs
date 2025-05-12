package sa.gov.amana.alriyadh.job.entity.ims;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;

@ToString
@Getter
@Setter
@Embeddable
public class ImsUserLogId implements java.io.Serializable {

    private static final long serialVersionUID = -5517898085938670366L;

    @Column(name = "GUID")
    private String guid;

    @Column(name = "GUID_SEQ")
    private Long guidSeq;

}
