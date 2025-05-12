package sa.gov.amana.alriyadh.job.entity.cmn;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class CmnPersonsMessageId implements java.io.Serializable {

    private static final long serialVersionUID = -5517898085938670355L;

    @NotNull
    @Column(name = "SEND_YEAR", nullable = false)
    private Long sendYear;

    @NotNull
    @Column(name = "MSG_NUMBER", nullable = false)
    private Long msgNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CmnPersonsMessageId entity = (CmnPersonsMessageId) o;
        return Objects.equals(this.msgNumber, entity.msgNumber) &&
                Objects.equals(this.sendYear, entity.sendYear);
    }

    @Override
    public int hashCode() {
        return Objects.hash(msgNumber, sendYear);
    }

}