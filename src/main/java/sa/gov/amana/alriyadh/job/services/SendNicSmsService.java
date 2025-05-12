package sa.gov.amana.alriyadh.job.services;

/*@Service
@Slf4j
@AllArgsConstructor*/
public class SendNicSmsService  {

    /*private final Clients client;


    public void mapSendNicSmsDto() {

        SendNicSmsDto sendNicSmsDto = new SendNicSmsDto();
        sendNicSmsDto.setId(7608);
        sendNicSmsDto.setViolationType("1");
        sendNicSmsDto.setViolationNumber("1");
        sendNicSmsDto.setIdNumber("1");
        sendNicSmsDto.setDate("1");
        sendNicSmsDto.setTime("1");
        sendNicSmsDto.setCity("1");
        sendNicSmsDto.setNeighborhood("132");
        sendNicSmsDto.setPlateNumber("1");
        sendNicSmsDto.setAmount("1");
        sendNicSmsDto.setSamisId("2356762324");
        sendNicSmsDto.setApplicationCode("BIS");
        sendNicSmsDto.setIdTypeCode("3");


        String notifySuccessful = sendNicSms(sendNicSmsDto);
//        if (notifySuccessful) {
//
//            System.out.println("sss");
//
//        }

    }

    public String sendNicSms(SendNicSmsDto sendNicSmsDto) {
        try {
            Call<ApiGatewayResponse<SendNicSmsResponseDto>> call = client.getElectricityClient().sendNicSms("BSI", sendNicSmsDto);
            Response<ApiGatewayResponse<SendNicSmsResponseDto>> executor = call.execute();

            if (executor.isSuccessful()) {
                ApiGatewayResponse<SendNicSmsResponseDto> br = executor.body();

                if (Long.valueOf(br.getHttpCode()) == HttpStatus.OK.value() && br.getData() != null) {
                    return br.getData().getResult().getMessageId();

                } else {
                    System.out.println("NotifyElectricityService Body N: " + executor.body().toString());
                    return "Boolean.FALSE";
                }

            } else {
                System.out.println("NotifyElectricityService ErrorBody: " + executor.errorBody().string());
//                return Boolean.FALSE;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
//            return Boolean.FALSE;
        }

        return null;

    }*/

}
