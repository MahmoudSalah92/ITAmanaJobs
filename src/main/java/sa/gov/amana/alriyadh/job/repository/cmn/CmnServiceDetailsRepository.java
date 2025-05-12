package sa.gov.amana.alriyadh.job.repository.cmn;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import sa.gov.amana.alriyadh.job.entity.cmn.CmnServiceDetails;

public interface CmnServiceDetailsRepository extends JpaRepository<CmnServiceDetails, Long> {

    // HQL
    Optional<CmnServiceDetails> findByIdAndServName(Long serviceId, String serviceName);

    //	// Native Query
    //	@Query(nativeQuery = true, value = "SELECT * FROM CMN_SERVICE_DETAILS u WHERE u.SERV_NAME = :servName")
    //	Optional<ServiceDetails> findByIdAndServNameNativeQuery(@Param("id") Long id, @Param("servName") String servName);

    //	// JPQL
    //	@Query("SELECT u FROM ServiceDetails u WHERE u.servName = :servName")
    //	Optional<ServiceDetails> findByIdAndServNameQuery(Long id, String servName);

}
