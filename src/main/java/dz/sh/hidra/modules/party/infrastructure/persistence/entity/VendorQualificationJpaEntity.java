/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : VendorQualificationJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for VendorQualification.
 *
 */
package dz.sh.hidra.modules.party.infrastructure.persistence.entity;

import dz.sh.hidra.modules.party.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for VendorQualification.
     */
    @Entity
    @Table(name = "hidra_party_vendor_qualification")
    public class VendorQualificationJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "party_id", nullable = false, length = 80)
    private String partyId;

    @Column(name = "vendor_category_code", nullable = false, length = 120)
    private String vendorCategoryCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "qualification_status", nullable = false, length = 80)
    private QualificationStatus qualificationStatus;

    @Column(name = "approved_from", nullable = true)
    private Instant approvedFrom;

    @Column(name = "approved_to", nullable = true)
    private Instant approvedTo;

    @Column(name = "approval_reference_id", nullable = true, length = 120)
    private String approvalReferenceId;

    @Enumerated(EnumType.STRING)
    @Column(name = "risk_level_snapshot", nullable = true, length = 80)
    private RiskLevel riskLevelSnapshot;

    @Column(name = "last_review_date", nullable = true)
    private Instant lastReviewDate;

    @Column(name = "next_review_date", nullable = true)
    private Instant nextReviewDate;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected VendorQualificationJpaEntity() {
            // Required by JPA.
        }

        public VendorQualificationJpaEntity(
                String id,
            String partyId,
            String vendorCategoryCode,
            QualificationStatus qualificationStatus,
            Instant approvedFrom,
            Instant approvedTo,
            String approvalReferenceId,
            RiskLevel riskLevelSnapshot,
            Instant lastReviewDate,
            Instant nextReviewDate,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.partyId = partyId;
        this.vendorCategoryCode = vendorCategoryCode;
        this.qualificationStatus = qualificationStatus;
        this.approvedFrom = approvedFrom;
        this.approvedTo = approvedTo;
        this.approvalReferenceId = approvalReferenceId;
        this.riskLevelSnapshot = riskLevelSnapshot;
        this.lastReviewDate = lastReviewDate;
        this.nextReviewDate = nextReviewDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String partyId() {
        return partyId;
    }


    public String vendorCategoryCode() {
        return vendorCategoryCode;
    }


    public QualificationStatus qualificationStatus() {
        return qualificationStatus;
    }


    public Instant approvedFrom() {
        return approvedFrom;
    }


    public Instant approvedTo() {
        return approvedTo;
    }


    public String approvalReferenceId() {
        return approvalReferenceId;
    }


    public RiskLevel riskLevelSnapshot() {
        return riskLevelSnapshot;
    }


    public Instant lastReviewDate() {
        return lastReviewDate;
    }


    public Instant nextReviewDate() {
        return nextReviewDate;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
