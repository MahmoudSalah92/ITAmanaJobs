package sa.gov.amana.alriyadh.job.repository.ims;

import org.springframework.data.jpa.repository.JpaRepository;
import sa.gov.amana.alriyadh.job.entity.ims.ImsUserLog;
import sa.gov.amana.alriyadh.job.entity.ims.ImsUserLogId;

public interface ImsUserLogRepository extends JpaRepository<ImsUserLog, ImsUserLogId> {


}
