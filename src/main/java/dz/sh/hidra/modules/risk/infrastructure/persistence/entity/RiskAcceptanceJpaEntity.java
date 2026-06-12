/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskAcceptanceJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for RiskAcceptance.
 *
 */
package dz.sh.hidra.modules.risk.infrastructure.persistence.entity;

import dz.sh.hidra.modules.risk.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;

    /**
     * Database-backed JPA entity for RiskAcceptance.
     */
    @Entity
    @Table(name = "hidra_risk_acceptance")
    public class RiskAcceptanceJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "risk_assessment_id", nullable = false, length = 80)
    private String riskAssessmentId;

    @Column(name = "acceptance_number", nullable = false, length = 80)
    private String acceptanceNumber;

    @Column(name = "accepted_rating_id", nullable = false, length = 80)
    private String acceptedRatingId;

    @Column(name = "accepted_score", nullable = true, precision = 18, scale = 6)
    private BigDecimal acceptedScore;

    @Column(name = "acceptance_reason_id", nullable = false, length = 80)
    private String acceptanceReasonId;

    @Column(name = "acceptance_justification", nullable = false, columnDefinition = "text")
    private String acceptanceJustification;

    @Column(name = "accepted_by_actor_id", nullable = false, length = 80)
    private String acceptedByActorId;

    @Column(name = "accepted_by_display_name_snapshot", nullable = true, length = 255)
    private String acceptedByDisplayNameSnapshot;

    @Column(name = "accepted_by_organization_unit_id", nullable = true, length = 80)
    private String acceptedByOrganizationUnitId;

    @Column(name = "accepted_by_organization_unit_name_snapshot", nullable = true, length = 500)
    private String acceptedByOrganizationUnitNameSnapshot;

    @Column(name = "accepted_at", nullable = false)
    private Instant acceptedAt;

    @Column(name = "valid_until", nullable = true)
    private Instant validUntil;

    @Column(name = "review_required", nullable = false)
    private boolean reviewRequired;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private RiskAcceptanceStatus status;

    @Column(name = "workflow_reference_id", nullable = true, length = 80)
    private String workflowReferenceId;

    @Column(name = "audit_reference_id", nullable = true, length = 80)
    private String auditReferenceId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected RiskAcceptanceJpaEntity() {
            // Required by JPA.
        }

        public RiskAcceptanceJpaEntity(
                String id,
            String riskAssessmentId,
            String acceptanceNumber,
            String acceptedRatingId,
            BigDecimal acceptedScore,
            String acceptanceReasonId,
            String acceptanceJustification,
            String acceptedByActorId,
            String acceptedByDisplayNameSnapshot,
            String acceptedByOrganizationUnitId,
            String acceptedByOrganizationUnitNameSnapshot,
            Instant acceptedAt,
            Instant validUntil,
            boolean reviewRequired,
            RiskAcceptanceStatus status,
            String workflowReferenceId,
            String auditReferenceId,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.riskAssessmentId = riskAssessmentId;
        this.acceptanceNumber = acceptanceNumber;
        this.acceptedRatingId = acceptedRatingId;
        this.acceptedScore = acceptedScore;
        this.acceptanceReasonId = acceptanceReasonId;
        this.acceptanceJustification = acceptanceJustification;
        this.acceptedByActorId = acceptedByActorId;
        this.acceptedByDisplayNameSnapshot = acceptedByDisplayNameSnapshot;
        this.acceptedByOrganizationUnitId = acceptedByOrganizationUnitId;
        this.acceptedByOrganizationUnitNameSnapshot = acceptedByOrganizationUnitNameSnapshot;
        this.acceptedAt = acceptedAt;
        this.validUntil = validUntil;
        this.reviewRequired = reviewRequired;
        this.status = status;
        this.workflowReferenceId = workflowReferenceId;
        this.auditReferenceId = auditReferenceId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String riskAssessmentId() {
        return riskAssessmentId;
    }


    public String acceptanceNumber() {
        return acceptanceNumber;
    }


    public String acceptedRatingId() {
        return acceptedRatingId;
    }


    public BigDecimal acceptedScore() {
        return acceptedScore;
    }


    public String acceptanceReasonId() {
        return acceptanceReasonId;
    }


    public String acceptanceJustification() {
        return acceptanceJustification;
    }


    public String acceptedByActorId() {
        return acceptedByActorId;
    }


    public String acceptedByDisplayNameSnapshot() {
        return acceptedByDisplayNameSnapshot;
    }


    public String acceptedByOrganizationUnitId() {
        return acceptedByOrganizationUnitId;
    }


    public String acceptedByOrganizationUnitNameSnapshot() {
        return acceptedByOrganizationUnitNameSnapshot;
    }


    public Instant acceptedAt() {
        return acceptedAt;
    }


    public Instant validUntil() {
        return validUntil;
    }


    public boolean reviewRequired() {
        return reviewRequired;
    }


    public RiskAcceptanceStatus status() {
        return status;
    }


    public String workflowReferenceId() {
        return workflowReferenceId;
    }


    public String auditReferenceId() {
        return auditReferenceId;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
