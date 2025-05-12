package sa.gov.amana.alriyadh.job.dto.apigateway;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiGatewayResponse<T> {

    @JsonProperty("httpCode")
    private String httpCode;

    @JsonProperty("httpMessage")
    private String httpMessage;

    @JsonProperty("data")
    private T data;

}
