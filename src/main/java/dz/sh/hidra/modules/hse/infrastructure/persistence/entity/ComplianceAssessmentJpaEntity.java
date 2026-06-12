/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ComplianceAssessmentJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for ComplianceAssessment.
 *
 */
package dz.sh.hidra.modules.hse.infrastructure.persistence.entity;

import dz.sh.hidra.modules.hse.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for ComplianceAssessment.
     */
    @Entity
    @Table(name = "hidra_hse_compliance_assessment")
    public class ComplianceAssessmentJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "obligation_id", nullable = false, length = 80)
    private String obligationId;

    @Column(name = "assessment_number", nullable = false, length = 80)
    private String assessmentNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "compliance_status", nullable = false, length = 40)
    private ComplianceStatus complianceStatus;

    @Column(name = "assessment_summary", nullable = true, columnDefinition = "text")
    private String assessmentSummary;

    @Column(name = "assessed_by_actor_id", nullable = true, length = 80)
    private String assessedByActorId;

    @Column(name = "assessed_at", nullable = false)
    private Instant assessedAt;

    @Column(name = "evidence_reference_id", nullable = true, length = 80)
    private String evidenceReferenceId;

    @Column(name = "linked_hse_case_id", nullable = true, length = 80)
    private String linkedHseCaseId;

    @Column(name = "next_assessment_due_at", nullable = true)
    private Instant nextAssessmentDueAt;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected ComplianceAssessmentJpaEntity() {
            // Required by JPA.
        }

        public ComplianceAssessmentJpaEntity(
                String id,
            String obligationId,
            String assessmentNumber,
            ComplianceStatus complianceStatus,
            String assessmentSummary,
            String assessedByActorId,
            Instant assessedAt,
            String evidenceReferenceId,
            String linkedHseCaseId,
            Instant nextAssessmentDueAt,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.obligationId = obligationId;
        this.assessmentNumber = assessmentNumber;
        this.complianceStatus = complianceStatus;
        this.assessmentSummary = assessmentSummary;
        this.assessedByActorId = assessedByActorId;
        this.assessedAt = assessedAt;
        this.evidenceReferenceId = evidenceReferenceId;
        this.linkedHseCaseId = linkedHseCaseId;
        this.nextAssessmentDueAt = nextAssessmentDueAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String obligationId() {
        return obligationId;
    }


    public String assessmentNumber() {
        return assessmentNumber;
    }


    public ComplianceStatus complianceStatus() {
        return complianceStatus;
    }


    public String assessmentSummary() {
        return assessmentSummary;
    }


    public String assessedByActorId() {
        return assessedByActorId;
    }


    public Instant assessedAt() {
        return assessedAt;
    }


    public String evidenceReferenceId() {
        return evidenceReferenceId;
    }


    public String linkedHseCaseId() {
        return linkedHseCaseId;
    }


    public Instant nextAssessmentDueAt() {
        return nextAssessmentDueAt;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
