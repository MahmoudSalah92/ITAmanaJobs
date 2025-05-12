package sa.gov.amana.alriyadh.job.jobs;

import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sa.gov.amana.alriyadh.job.services.ImsSmsService;

@RestController
@RequestMapping("/serv/api/job/sendImsSmsJob")
@Validated
@AllArgsConstructor
public class ImsSmsJob {

    private final ImsSmsService imsSmsService;

    @GetMapping(value = "/test")
    // @Scheduled(fixedDelay = 1, initialDelay = 1, timeUnit = TimeUnit.DAYS)
    public void sendImsSms() {

        imsSmsService.sendImsSms();

    }

}
