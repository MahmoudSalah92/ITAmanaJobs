package sa.gov.amana.alriyadh.job.dto.ims;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class InvestorInfoDto {

    private String personName;
    private Long idTypeCode;
    private String identificationNo;
    private Long contractNo;
    private Long dirCode;

}
