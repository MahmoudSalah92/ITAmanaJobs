package sa.gov.amana.alriyadh.job.dto.nabaaSms;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SendNicSmsDto {

    private Integer id;
    private String violationType;
    private String violationNumber;
    private String idNumber;
    private String date;
    private String time;
    private String city;
    private String neighborhood;
    private String plateNumber;
    private String amount;
    private String samisId;
    private String ApplicationCode;
    private String IdTypeCode;

}
