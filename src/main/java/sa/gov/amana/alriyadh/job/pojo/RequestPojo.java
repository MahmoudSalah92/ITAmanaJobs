package sa.gov.amana.alriyadh.job.pojo;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@Data // Generates getters, setters, toString, equals, and hashCode
@NoArgsConstructor // Generates a no-arguments constructor
@AllArgsConstructor
public class RequestPojo {

	@Hidden // @Hidden mean that this field will not seen in the json
	private Integer issuedFrom;
	@NotBlank(message = "Application code is required.")
	private String appCode;
	@Hidden
	private String functionCode;
	@NotBlank(message = "Mobile Number is required")
	private String mobileNumber;
	@NotBlank(message = "Message body is required")
	private String messageBody;
	@NotBlank(message = "SMS TimeToSend is required (yyyy-MM-dd HH:mm:ss).")
	private String timeToSend;
	@Hidden
	private Integer validFor;
	@Hidden
	private Integer language;
	@Hidden
	private boolean urgent;
	@Hidden
	Map<String, Object> datafields = new HashMap<String, Object>();

	public Integer getIssuedFrom() {
		if (issuedFrom == null) {
			// DEFAULT_FROM
			return 1;
		}
		if (isUrgent()) {
			// URGENT_FROM = 3
			return 3;
		}
		return issuedFrom;
	}

	public void setIssuedFrom(Integer issuedFrom) {
		this.issuedFrom = issuedFrom;
	}

	public String getAppCode() {
		if (appCode == null || appCode.isEmpty()) {
			// DEFAULT_APP
			return "OTP";
		} else {
			return appCode;
		}
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getFunctionCode() {
		if (functionCode == null || functionCode.isEmpty()) {
			// DEFAULT_FUNCTION
			// return "110";
			return "0000";
		} else {
			return functionCode;
		}
	}

	public Integer getValidFor() {
		if (validFor == null) {
			// DEFAULT_VALID_FOR
			return 72;
		} else {
			return validFor;
		}
	}

	public void setValidFor(Integer validFor) {
		this.validFor = validFor;
	}

	public Integer getLanguage() {
		if (language == null) {
			// DEFAULT_LANG
			return 1;
		} else {
			return language;
		}

	}

	public Map<String, Object> getDatafields() {
		try {
			datafields.put("P$ISSUED_FROM", getIssuedFrom());
			datafields.put("P$APP_CODE", getAppCode());
			datafields.put("P$FUNCTION_CODE", getFunctionCode());
			datafields.put("P$SMS_MSG_LANGUAGE", getLanguage());
			datafields.put("P$MSG_BODY", getMessageBody());
			datafields.put("P$VALID_HOURS", getValidFor());
			if (timeToSend != null && !timeToSend.isEmpty()) {
				java.util.Date temp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timeToSend);

				// java.util.Date temp = new Date();
				// java.util.Date temp = new localdate.p
				// java.util.Date temp =Date.from( LocalDateTime.parse(timeToSend,
				// DateTimeFormatter.ofPattern("yyyy-MM-dd
				// HH:mm:ss")).toInstant(ZoneOffset.UTC));

				datafields.put("P$TIME_TO_SEND", temp);
			}
			datafields.put("P$MOBILE_NO", getMobileNumber());
			datafields.put("P$ID_TYPE_CODE", null);
			datafields.put("P$IDENTIFICATION_NO", null);
			datafields.put("P$DIR_CODE", null);
			datafields.put("P$SECTION_CODE", null);
			datafields.put("P$TARGET", null);
			datafields.put("P$RESEND_COUNT", null);
			datafields.put("P$ISSUE_DATE", null);
			datafields.put("P$DELIVERY_DATE", null);
			datafields.put("P$MSG_KEY", null);
			datafields.put("P$MSG_SUBJECT", null);
			datafields.put("P$FAIL_REASON", null);
			datafields.put("P$EMPLOYEE_NO", null);

			System.out.println(datafields.toString());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return datafields;
	}

	public void setDatafields(Map<String, Object> datafields) {
		this.datafields = datafields;
	}

}
