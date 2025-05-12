package sa.gov.amana.alriyadh.job.dto.electricity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CertificateOccupancyDto {

    private String electricityLicenseNumber;
    private String notificationTypeId;
    private String attachmentFilePath;

    private String amanaLicenseNumber;
    private String licenseYear;

}
