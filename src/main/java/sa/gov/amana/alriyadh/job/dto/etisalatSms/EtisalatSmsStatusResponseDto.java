package sa.gov.amana.alriyadh.job.dto.etisalatsms;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EtisalatSmsStatusResponseDto {

	private EtisalatSmsStatusResultDto result;

	private String responseCode;
	private String responseMessage;

	private String createdDate;
	private String receivedDate;

}
