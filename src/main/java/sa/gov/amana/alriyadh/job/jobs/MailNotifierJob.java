package sa.gov.amana.alriyadh.job.jobs;


import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sa.gov.amana.alriyadh.job.services.MailNotifierService;

@RestController
@RequestMapping("/serv/api/job/mailNotifier")
@Validated
@AllArgsConstructor
public class MailNotifierJob {

    private final MailNotifierService mailNotifierService;


    // @Scheduled(fixedDelay = 1, initialDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void sendRmsScheduleEmails() {

        mailNotifierService.sendRmsScheduleEmails();

    }

}
