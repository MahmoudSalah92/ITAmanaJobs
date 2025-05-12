package sa.gov.amana.alriyadh.job.jobs;


import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sa.gov.amana.alriyadh.job.services.NotifyElectricityService;

@RestController
@RequestMapping("/serv/api/job/notifyElectricity")
@Validated
@AllArgsConstructor
public class NotifyElectricityJob {

    private final NotifyElectricityService notifyElectricityService;


    // @Scheduled(fixedDelay = 1, initialDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void sendRmsScheduleEmails() {

        notifyElectricityService.sendCertificateOccupancyToElectricity();

    }

}
