package sa.gov.amana.alriyadh.job.entity.ims;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@Entity
@Table(name = "IMS_CONTRACT_PAYMENTS")
public class ImsContractPayments {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ICON_ID", nullable = false)
    private ImsContracts icon;

    @NotNull
    @Column(name = "SERIAL", nullable = false)
    private Short serial;

    @NotNull
    @Column(name = "PAYMENT_AMOUNT", nullable = false, precision = 15, scale = 2)
    private BigDecimal paymentAmount;

    @NotNull
    @Column(name = "PAID_AMOUNT", nullable = false, precision = 15, scale = 2)
    private BigDecimal paidAmount;

    @NotNull
    @Column(name = "REMAINING_AMOUNT", nullable = false, precision = 15, scale = 2)
    private BigDecimal remainingAmount;

    @Size(max = 25)
    @Column(name = "BILL_ACCOUNT", length = 25)
    private String billAccount;

    @Column(name = "DUE_DATE_HJ")
    private LocalDate dueDateHj;

    @Column(name = "DUE_DATE")
    private LocalDate dueDate;

    @NotNull
    @Column(name = "STATUS", nullable = false)
    private Boolean status = false;

    @Column(name = "COLLECT_DATE")
    private LocalDate collectDate;

    @Column(name = "COLLECT_DATE_HJ")
    private LocalDate collectDateHj;

    @Size(max = 50)
    @Column(name = "COLLECTION_GID", length = 50)
    private String collectionGid;

    @Size(max = 2000)
    @Column(name = "REMARKS", length = 2000)
    private String remarks;

    @Size(max = 30)
    @NotNull
    @Column(name = "CREATED_BY", nullable = false, length = 30)
    private String createdBy;

    @NotNull
    @Column(name = "CREATED_DATE", nullable = false)
    private LocalDate createdDate;

    @Size(max = 30)
    @Column(name = "MODIFIED_BY", length = 30)
    private String modifiedBy;

    @Column(name = "MODIFIED_DATE")
    private LocalDate modifiedDate;

    @Column(name = "BILL_DATE")
    private LocalDate billDate;

    @Column(name = "BILL_DATE_HJ")
    private LocalDate billDateHj;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ICONP_ID")
    private ImsContractPayments iconp;

    @Column(name = "PAYMENT_TYPE")
    private Short paymentType;

    @Column(name = "SUB_SERIAL")
    private Short subSerial;

    @Column(name = "ICON_ID1")
    private Long iconId1;

    @Column(name = "PAYMENT_METHOD")
    private Boolean paymentMethod;

    @Column(name = "DUE_TO_DATE_HJ")
    private LocalDate dueToDateHj;

    @Column(name = "DUE_TO_DATE")
    private LocalDate dueToDate;

    @Column(name = "TOTAL_REMOVALS", precision = 15, scale = 2)
    private BigDecimal totalRemovals;

    @Column(name = "TOTAL_ADDITIONS", precision = 15, scale = 2)
    private BigDecimal totalAdditions;

    @Column(name = "RECYCLED_BALANCE", precision = 15, scale = 2)
    private BigDecimal recycledBalance;

    @Column(name = "FIRST_ESCALATION_ID")
    private Long firstEscalationId;

    @Column(name = "SECOND_ESCALATION_ID")
    private Long secondEscalationId;

    @Column(name = "IMS_MAIN_SERVICE")
    private Long imsMainService;

    @Column(name = "IMS_SUB_SERVICE")
    private Long imsSubService;

}