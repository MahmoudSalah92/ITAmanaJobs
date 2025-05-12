package sa.gov.amana.alriyadh.job.dto.electricity;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ElectricityUpdateResponse {

    private Integer notifySuccessful;

    private String statusMessage;

    private Date sendDate;

}
