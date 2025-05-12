package sa.gov.amana.alriyadh.job.clients.retrofit;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import sa.gov.amana.alriyadh.job.dto.apigateway.AuthorizeResponse;

public interface ApiGatewayAuthorizeClient {

    @FormUrlEncoded
    @POST("auth/oauth2/token")
    Call<AuthorizeResponse> authenticate(@Field("grant_type") String grantType,
                                         @Field("scope") String scope,
                                         @Header("Content-Type") String contentType,
                                         @Header("Authorization") String authorization);

}
