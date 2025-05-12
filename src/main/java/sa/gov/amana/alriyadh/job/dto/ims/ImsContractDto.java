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
public class ImsContractDto {

    private Long id;
    private Long iofId;
    private Long dirCode;
    private String contractNo;
    private String contractDescription;
    private Date signDate;
    private Date signDateHj;
    private Long contPeriod;
    private Long periodUnit;
    private Date startDate;
    private Date startDateHj;
    private Date endDate;
    private Date endDateHj;
    private Long isiteId;
    private Long rentAmount;
    private Long activityType;
    private Long isrId;
    private Long idTypeCode;
    private String identificationNo;
    private String personName;
    private Long status;
    private Long iconId;
    private Long cttId;
    private Long trxSerial;
    private Date warningDate;
    private Date warningDateHj;
    private Long warningSent;
    private Long iiaId;
    private Long imsServices;
    private Long rentAmountEst;
    private Long iconpId;
    private Long constructionPeriodInd;
    private Long premiumContract;

}
