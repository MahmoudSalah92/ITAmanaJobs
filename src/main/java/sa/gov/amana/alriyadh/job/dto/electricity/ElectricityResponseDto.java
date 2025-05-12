package sa.gov.amana.alriyadh.job.dto.electricity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ElectricityResponseDto {

    @JsonProperty("msgCode")
    private String msgCode;

    @JsonProperty("msg")
    private String msg;

    @Override
    public String toString() {
        return "msgCode: " + msgCode + " | msg: " + msg ;
    }

}
