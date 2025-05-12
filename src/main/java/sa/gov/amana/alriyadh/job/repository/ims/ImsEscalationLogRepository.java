package sa.gov.amana.alriyadh.job.repository.ims;

import org.springframework.data.jpa.repository.JpaRepository;
import sa.gov.amana.alriyadh.job.entity.ims.ImsEscalationLog;

public interface ImsEscalationLogRepository extends JpaRepository<ImsEscalationLog, Long> {
}
