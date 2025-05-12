package sa.gov.amana.alriyadh.job.dto.electricity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ElectricityRequestDto {

    private String licenseNumber;
    private String notificationTypeId;
    private String attachmentFile;


    @Override
    public String toString() {
        return "licenseNumber: " + licenseNumber + " | notificationTypeId: " + notificationTypeId + " | attachmentFile: " + attachmentFile;
    }

}
