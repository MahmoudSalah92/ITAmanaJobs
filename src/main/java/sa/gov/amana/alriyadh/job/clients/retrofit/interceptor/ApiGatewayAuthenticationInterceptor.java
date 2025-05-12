package sa.gov.amana.alriyadh.job.clients.retrofit.interceptor;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import sa.gov.amana.alriyadh.job.clients.retrofit.ApiGatewayAuthorizeClient;
import sa.gov.amana.alriyadh.job.clients.retrofit.ClientUtils;
import sa.gov.amana.alriyadh.job.constants.JobsConstants;
import sa.gov.amana.alriyadh.job.dto.apigateway.AuthorizeResponse;

import java.io.IOException;

@Component
public class ApiGatewayAuthenticationInterceptor implements Interceptor, InitializingBean {

    @Autowired
    private ClientUtils clientUtils;

    private ApiGatewayAuthorizeClient apiGatewayAuthorizeClient;
    private String username;
    private String password;

    @Override
    public Response intercept(Chain chain) throws IOException {

        String tokenValue = null;
        try {
            AuthorizeResponse authenticate = authenticate();
            tokenValue = String.format("Bearer %s", authenticate.getAccessToken());
        } catch (Exception e) {
            throw new IOException(e);
        }

        Builder builder = chain.request().newBuilder();
        builder.addHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        builder.addHeader("Authorization", tokenValue);
        builder.addHeader("Accept", "*/*");
        builder.addHeader("Accept-Encoding", "identity");

        Request request = builder.build();

        Response res = chain.proceed(request);
//        System.out.println("Response Body: " + res.body().string());

        return res;
    }

    private AuthorizeResponse authenticate() throws Exception {

        String credentials = Credentials.basic(username, password);
        Call<AuthorizeResponse> call = apiGatewayAuthorizeClient.authenticate("client_credentials", "token",
                MediaType.APPLICATION_FORM_URLENCODED_VALUE, credentials);

        retrofit2.Response<AuthorizeResponse> execute = call.execute();
        if (execute.isSuccessful()) {
            return execute.body();
        }
        throw new Exception("unable to get apiGateway token ");
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        username = JobsConstants.API_GATEWAY_USERNAME_KEY;
        password = JobsConstants.API_GATEWAY_PASSWORD_KEY;

        apiGatewayAuthorizeClient = clientUtils.createApiGetwayClient(JobsConstants.API_GATEWAY_BASE_URL_KEY, ApiGatewayAuthorizeClient.class);
    }

}
