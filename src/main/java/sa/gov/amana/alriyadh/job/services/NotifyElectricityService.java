package sa.gov.amana.alriyadh.job.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import sa.gov.amana.alriyadh.job.clients.retrofit.Clients;
import sa.gov.amana.alriyadh.job.dao.NotifyElectricityDao;
import sa.gov.amana.alriyadh.job.dto.apigateway.ApiGatewayResponse;
import sa.gov.amana.alriyadh.job.dto.electricity.CertificateOccupancyDto;
import sa.gov.amana.alriyadh.job.dto.electricity.ElectricityRequestDto;
import sa.gov.amana.alriyadh.job.dto.electricity.ElectricityResponseDto;
import sa.gov.amana.alriyadh.job.dto.electricity.ElectricityUpdateResponse;
import sa.gov.amana.alriyadh.job.utils.FileUtil;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class NotifyElectricityService {

    private final Clients client;
    private final NotifyElectricityDao notifyElectricityDao;
    private final AuditService auditService;

    private final String serviceName = "NotifyElectricityService";

    public void sendCertificateOccupancyToElectricity() {

        Date beforeDate = new Date();

        List<CertificateOccupancyDto> certificateOccupancyRowSet = notifyElectricityDao.getCertificateOccupancyReport();

        if (certificateOccupancyRowSet != null && certificateOccupancyRowSet.size() > 0) {

            System.out.println("Start Loop notifyElectricityService " + certificateOccupancyRowSet.size());
            for (CertificateOccupancyDto row : certificateOccupancyRowSet) {

                ElectricityRequestDto electricityRequestDto = new ElectricityRequestDto();
                electricityRequestDto.setLicenseNumber(row.getElectricityLicenseNumber());
                electricityRequestDto.setNotificationTypeId(row.getNotificationTypeId());
                electricityRequestDto.setAttachmentFile(FileUtil.convertPdfUrlToBase64(row.getAttachmentFilePath()));

                ElectricityUpdateResponse updateResponse = NotifyElectricity(electricityRequestDto, beforeDate);
                if (updateResponse != null) {
                    notifyElectricityDao.updateCertificateOccupancyReport(updateResponse.getNotifySuccessful(), updateResponse.getStatusMessage(), updateResponse.getSendDate(), row.getAmanaLicenseNumber(), row.getLicenseYear());
                }

            }
        }
    }

    public ElectricityUpdateResponse NotifyElectricity(ElectricityRequestDto electricityRequestDto, Date beforeDate) {
        ElectricityUpdateResponse electricityUpdateResponse = new ElectricityUpdateResponse();
        electricityUpdateResponse.setSendDate(beforeDate);

        String serviceUrl = null;
        String requestPayload = null;
        try {
            Call<ApiGatewayResponse<ElectricityResponseDto>> call = client.getElectricityClient().pushCertificateOccupancyToElectricity("BLS", electricityRequestDto);
            Response<ApiGatewayResponse<ElectricityResponseDto>> executor = call.execute();

            serviceUrl = executor.raw().request().url().toString();
            requestPayload = executor.raw().request().toString();

            if (executor.isSuccessful()) {
                ApiGatewayResponse<ElectricityResponseDto> br = executor.body();

                if (Long.valueOf(br.getHttpCode()) == HttpStatus.OK.value() && br.getData() != null) {
                    electricityUpdateResponse.setNotifySuccessful(1);
                    electricityUpdateResponse.setStatusMessage(br.getData().toString());

                    auditService.addAudit(serviceName, serviceUrl, requestPayload, electricityUpdateResponse.getStatusMessage(), beforeDate, Long.valueOf(br.getHttpCode()), br.getHttpMessage());
                    return electricityUpdateResponse;

                } else {
                    electricityUpdateResponse.setNotifySuccessful(0);
                    electricityUpdateResponse.setStatusMessage(executor.body().toString());

                    auditService.addAudit(serviceName, serviceUrl, requestPayload, electricityUpdateResponse.getStatusMessage(), beforeDate, Long.valueOf(br.getHttpCode()), br.getHttpMessage());
                    return electricityUpdateResponse;
                }

            } else {
                electricityUpdateResponse.setNotifySuccessful(0);
                electricityUpdateResponse.setStatusMessage(executor.errorBody().string());

                auditService.addAudit(serviceName, serviceUrl, requestPayload, electricityUpdateResponse.getStatusMessage(), beforeDate, Long.valueOf(executor.code()), executor.message());
                return electricityUpdateResponse;

            }

        } catch (Exception ex) {
            electricityUpdateResponse.setNotifySuccessful(0);
            electricityUpdateResponse.setStatusMessage(ex.getMessage());

            auditService.addAudit(serviceName, serviceUrl, StringUtils.isNotEmpty(requestPayload) ? requestPayload : electricityRequestDto.toString(), ExceptionUtils.getStackTrace(ex), beforeDate, Long.valueOf(500), ex.getMessage());
            return electricityUpdateResponse;
        }

    }


}
