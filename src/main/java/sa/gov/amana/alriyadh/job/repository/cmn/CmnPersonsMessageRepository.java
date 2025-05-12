package sa.gov.amana.alriyadh.job.repository.cmn;

import org.springframework.data.jpa.repository.JpaRepository;
import sa.gov.amana.alriyadh.job.entity.cmn.CmnPersonsMessage;
import sa.gov.amana.alriyadh.job.entity.cmn.CmnPersonsMessageId;

public interface CmnPersonsMessageRepository extends JpaRepository<CmnPersonsMessage, CmnPersonsMessageId> {


}
