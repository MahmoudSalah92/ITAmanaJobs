package sa.gov.amana.alriyadh.job.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class RequestExceptionErrors {

    @JsonProperty(value = "StatusCode")
    int statusCode;

    @JsonProperty(value = "StatusMessage")
    String statusMessage;

    Date time;

    public RequestExceptionErrors() {
    }

    public RequestExceptionErrors(int statusCode, String statusMessage, Date time) {
        super();
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.time = time;
    }

}
