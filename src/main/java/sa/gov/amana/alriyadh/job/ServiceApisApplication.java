package sa.gov.amana.alriyadh.job;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
//@EnableTransactionManagement
//@EnableResourceServer
@EnableScheduling
@EnableAsync
@OpenAPIDefinition(info = @Info(title = "Schedule App Services Definition"))
public class ServiceApisApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceApisApplication.class, args);
    }

}
