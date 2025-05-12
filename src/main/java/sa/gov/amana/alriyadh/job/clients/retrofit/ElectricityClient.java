package sa.gov.amana.alriyadh.job.clients.retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import sa.gov.amana.alriyadh.job.dto.apigateway.ApiGatewayResponse;
import sa.gov.amana.alriyadh.job.dto.electricity.ElectricityRequestDto;
import sa.gov.amana.alriyadh.job.dto.electricity.ElectricityResponseDto;

public interface ElectricityClient {


    @POST(value = "v1/BLSSEC/notification")
    Call<ApiGatewayResponse<ElectricityResponseDto>> pushCertificateOccupancyToElectricity(@Header("AppCode") String appCode,
                                                                                           @Body ElectricityRequestDto electricityRequestDto);

    /*@POST(value = "v1/nic-sms/send")
    Call<ApiGatewayResponse<SendNicSmsResponseDto>> sendNicSms(@Header("AppCode") String appCode,
                                                               @Body SendNicSmsDto sendNicSmsDto);*/


}
