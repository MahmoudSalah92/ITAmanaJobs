package sa.gov.amana.alriyadh.job.entity.cmn;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "CMN_SERVICE_DETAILS", schema = "CMNV3")
public class CmnServiceDetails {

	@Id
	@Column(name = "SERV_ID", nullable = false)
	private Long id;

	@Size(max = 1000)
	@Column(name = "APP_CODE", length = 1000)
	private String appCode;

	@Size(max = 1000)
	@Column(name = "SERV_NAME", length = 1000)
	private String servName;

	@Size(max = 1000)
	@Column(name = "SERV_DESC", length = 1000)
	private String servDesc;

	@Column(name = "STATUS")
	private Long status;

	@Size(max = 4000)
	@Column(name = "SERV_URL", length = 4000)
	private String servUrl;

	@Size(max = 4000)
	@Column(name = "SERV_USERNAME", length = 4000)
	private String servUsername;

	@Size(max = 4000)
	@Column(name = "SERV_PASS", length = 4000)
	private String servPass;

	@Lob
	@Column(name = "SERV_SAMPLE_REQ")
	private String servSampleReq;

	@Lob
	@Column(name = "SERV_SAMPLE_RES")
	private String servSampleRes;

	@Column(name = "LAST_SUCCESS_DATE")
	private LocalDate lastSuccessDate;

	@Column(name = "LAST_FAIL_DATE")
	private LocalDate lastFailDate;

	@Size(max = 4000)
	@Column(name = "LAST_APP_CALLED", length = 4000)
	private String lastAppCalled;

	@Size(max = 4000)
	@Column(name = "SOURCE_DEV", length = 4000)
	private String sourceDev;

	@Size(max = 1000)
	@Column(name = "METHOD_TYPE", length = 1000)
	private String methodType;

	@Size(max = 1000)
	@Column(name = "REQUEST_TYPE", length = 1000)
	private String requestType;

	@Size(max = 1000)
	@Column(name = "SERVICE_TYPE", length = 1000)
	private String serviceType;

	@Size(max = 1000)
	@Column(name = "RES_TYPE", length = 1000)
	private String resType;

	@Column(name = "SERVICE_TIMEOUT")
	private Long serviceTimeout;

	@Size(max = 2000)
	@Column(name = "API_KEY", length = 2000)
	private String apiKey;

	@Column(name = "PARENT_SERV_ID")
	private Long parentServId;

	@Size(max = 2000)
	@Column(name = "SECURITY_TYPE", length = 2000)
	private String securityType;

	@Size(max = 2000)
	@Column(name = "QUERY_PARAMS", length = 2000)
	private String queryParams;

	@Column(name = "CLIENT_SERIAL")
	private Long clientSerial;

	@Size(max = 100)
	@Column(name = "CONSUME_EXPOSE", length = 100)
	private String consumeExpose;

}
