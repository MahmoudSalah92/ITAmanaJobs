package sa.gov.amana.alriyadh.job.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import sa.gov.amana.alriyadh.job.entity.cmn.CmnServiceDetails;
import sa.gov.amana.alriyadh.job.repository.cmn.CmnServiceDetailsRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CmnService {

    private final CmnServiceDetailsRepository cmnServiceDetailsRepository;


    @Transactional
    public CmnServiceDetails getServiceDetails(Long serviceId, String serviceName) {
        Optional<CmnServiceDetails> detailsRow = cmnServiceDetailsRepository.findByIdAndServName(serviceId, serviceName);
        if (detailsRow.isPresent()) {
            return detailsRow.get();
        } else {
            throw new RuntimeException("Service not found in CmnServiceDetails with ID: " + serviceId);
        }
    }

}
