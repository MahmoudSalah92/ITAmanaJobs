package sa.gov.amana.alriyadh.job.entity.ims;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;


@Getter
@Setter
@ToString
@Entity
@Table(name = "IMS_CONTRACTS", schema = "IMS")
public class ImsContracts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "IOF_ID")
    private Long iof;

    @Column(name = "DIR_CODE")
    private Long dirCode;

    @Size(max = 20)
    @NotNull
    @Column(name = "CONTRACT_NO", nullable = false, length = 20)
    private String contractNo;

    @Size(max = 500)
    @NotNull
    @Column(name = "CONTRACT_DESCRIPTION", nullable = false, length = 500)
    private String contractDescription;

    @Column(name = "SIGN_DATE")
    private Date signDate;

    @Column(name = "SIGN_DATE_HJ")
    private Date signDateHj;

    @NotNull
    @Column(name = "CONT_PERIOD", nullable = false)
    private Long contPeriod;

    @NotNull
    @Column(name = "PERIOD_UNIT", nullable = false)
    private Long periodUnit;

    @Column(name = "START_DATE")
    private Date startDate;

    @Column(name = "START_DATE_HJ")
    private Date startDateHj;

    @Column(name = "END_DATE")
    private Date endDate;

    @Column(name = "END_DATE_HJ")
    private Date endDateHj;

    @Column(name = "ISITE_ID")
    private Long isite;

    @Column(name = "ISD_ID")
    private Long isd;

    @Column(name = "INVESTOR_CALLING_DATE")
    private Date investorCallingDate;

    @Column(name = "INVESTOR_CALLING_DATE_HJ")
    private Date investorCallingDateHj;

    @Column(name = "SITE_RECEIVE_MOM_DATE")
    private Date siteReceiveMomDate;

    @Column(name = "SITE_RECEIVE_MOM_DATE_HJ")
    private Date siteReceiveMomDateHj;

    @Column(name = "CONTRACT_CANCEL_DATE")
    private Date contractCancelDate;

    @Column(name = "CONTRACT_CANCEL_DATE_HJ")
    private Date contractCancelDateHj;

    @NotNull
    @Column(name = "RENT_AMOUNT", nullable = false, precision = 15, scale = 2)
    private BigDecimal rentAmount;

    @NotNull
    @Column(name = "ACTIVITY_TYPE", nullable = false)
    private Boolean activityType = false;

    @Column(name = "CONSTRUCTION_PERIOD_PCT", precision = 6, scale = 2)
    private BigDecimal constructionPeriodPct;

    @Column(name = "ISR_ID")
    private Long isrId;

    @Size(max = 2000)
    @Column(name = "REMARKS", length = 2000)
    private String remarks;

    @Column(name = "ID_TYPE_CODE")
    private Long idTypeCode;

    @Size(max = 20)
    @Column(name = "IDENTIFICATION_NO", length = 20)
    private String identificationNo;

    @Size(max = 100)
    @Column(name = "PERSON_NAME", length = 100)
    private String personName;

    @Size(max = 20)
    @Column(name = "CONTRACT_NO_OLD", length = 20)
    private String contractNoOld;

    @Column(name = "STATUS")
    private Long status;

    @Column(name = "ICON_ID")
    private Long iconId;

    @Column(name = "CTT_ID")
    private Long cttId;

    @Column(name = "TRX_SERIAL")
    private Long trxSerial;

    @Column(name = "WARNING_DATE")
    private Date warningDate;

    @Column(name = "WARNING_DATE_HJ")
    private Date warningDateHj;

    @Column(name = "WARNING_SENT")
    private Boolean warningSent;

    @Column(name = "IIA_ID")
    private Long iia;

    @Column(name = "IIOP_ID")
    private Long iiop;

    @Size(max = 50)
    @Column(name = "FUNCTION_GID", length = 50)
    private String functionGid;

    @Size(max = 30)
    @NotNull
    @Column(name = "CREATED_BY", nullable = false, length = 30)
    private String createdBy;

    @NotNull
    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Size(max = 30)
    @Column(name = "MODIFIED_BY", length = 30)
    private String modifiedBy;

    @Column(name = "MODIFIED_DATE")
    private Date modifiedDate;

    @Column(name = "IMS_SERVICES")
    private Integer imsServices;

    @Column(name = "RENT_AMOUNT_EST", precision = 15, scale = 2)
    private BigDecimal rentAmountEst;

    @Size(max = 10)
    @Column(name = "PREV_OUTGOING_NO", length = 10)
    private String prevOutgoingNo;

    @Column(name = "PREV_OUTGOING_DATE")
    private Date prevOutgoingDate;

    @Column(name = "PREV_OUTGOING_DATE_HJ")
    private Date prevOutgoingDateHj;

    @Column(name = "ICONP_ID")
    private Long iconpId;

    @Column(name = "DIRECT_RENT_IND")
    private Boolean directRentInd;

    @Column(name = "SITES_COUNT")
    private Long sitesCount;

    @Column(name = "ELECTRICITY_FEE", precision = 12, scale = 2)
    private BigDecimal electricityFee;

    @Column(name = "ELECTRICITY_FEES", precision = 12, scale = 2)
    private BigDecimal electricityFees;

    @Column(name = "ADVERTISING_FEE", precision = 12, scale = 2)
    private BigDecimal advertisingFee;

    @Column(name = "ADVERTISING_FEES", precision = 12, scale = 2)
    private BigDecimal advertisingFees;

    @NotNull
    @Column(name = "CONSTRUCTION_PERIOD_IND", nullable = false)
    private Boolean constructionPeriodInd = false;

    @Column(name = "REFERENCE_NUMBER")
    private Long referenceNumber;

    @Column(name = "DOCUMENT_DATE")
    private Date documentDate;

    @Column(name = "DOCUMENT_DATEH")
    private Date documentDateh;

    @Column(name = "OLD_STATUS")
    private Long oldStatus;

    @Column(name = "CONTRACT_REACTIVATE_DATE_HJ")
    private Date contractReactivateDateHj;

    @Column(name = "CONTRACT_REACTIVATE_DATE")
    private Date contractReactivateDate;

    @Column(name = "MWF_REQUEST_NUMBER")
    private Long mwfRequestNumber;

    @Column(name = "MWF_REQUEST_YEAR")
    private Long mwfRequestYear;

    @Size(max = 20)
    @Column(name = "IDENTIFICATION_NO_OLD", length = 20)
    private String identificationNoOld;

    @NotNull
    @Column(name = "PREMIUM_CONTRACT", nullable = false)
    private Boolean premiumContract = false;

    @Column(name = "IMS_MAIN_SERVICE")
    private Long imsMainService;

    @Column(name = "PAYMENT_CODE")
    private Long paymentCode;

}