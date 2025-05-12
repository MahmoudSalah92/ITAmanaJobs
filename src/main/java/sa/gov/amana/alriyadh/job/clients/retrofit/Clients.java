package sa.gov.amana.alriyadh.job.clients.retrofit;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sa.gov.amana.alriyadh.job.clients.retrofit.interceptor.ApiGatewayAuthenticationInterceptor;
import sa.gov.amana.alriyadh.job.constants.JobsConstants;


@Getter
@Setter
@Component
public class Clients implements InitializingBean {

    @Autowired
    private ClientUtils clientUtils;

    @Autowired
    private ApiGatewayAuthenticationInterceptor apiGatewayAuthenticationInterceptor;

    private ApiGatewayAuthorizeClient apiGatewayAuthorizeClient;
    private ElectricityClient electricityClient;


    @Override
    public void afterPropertiesSet() throws Exception {

        apiGatewayAuthorizeClient = clientUtils.createApiGetwayClient(JobsConstants.API_GATEWAY_BASE_URL_KEY, ApiGatewayAuthorizeClient.class);
        electricityClient = clientUtils.createApiGetwayClient(JobsConstants.API_GATEWAY_BASE_URL_KEY + JobsConstants.API_GATEWAY_ENV, ElectricityClient.class, apiGatewayAuthenticationInterceptor);

    }

}

