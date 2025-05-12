package sa.gov.amana.alriyadh.job.dto.ims;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContractPaymentsDto {

    // IMS
    private Long paymentId;
    private Long contractNo;
    private Long serial;
    private String billAccount;

    // RVU
    private Date formDate;
    private Long formAmount;

}
