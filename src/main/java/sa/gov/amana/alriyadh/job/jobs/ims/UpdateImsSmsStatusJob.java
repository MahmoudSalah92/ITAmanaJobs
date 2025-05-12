package sa.gov.amana.alriyadh.job.jobs.ims;

import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import sa.gov.amana.alriyadh.job.services.ims.UpdateImsSmsStatusService;

@RestController
@RequestMapping("/serv/api/job/updateImsSmsStatusJob")
@Validated
@AllArgsConstructor
public class UpdateImsSmsStatusJob {

	private final UpdateImsSmsStatusService updateImsSmsStatusService;

//	@Scheduled(fixedDelay = 1, initialDelay = 1, timeUnit = TimeUnit.MINUTES)
	public void sendRmsScheduleEmails() {

		updateImsSmsStatusService.updateImsSmsStatus();

	}

}
