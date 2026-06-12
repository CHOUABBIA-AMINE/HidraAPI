/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityAssessmentJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for IntegrityAssessment.
 *
 */
package dz.sh.hidra.modules.integrity.infrastructure.persistence.entity;

import dz.sh.hidra.modules.integrity.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for IntegrityAssessment.
     */
    @Entity
    @Table(name = "hidra_integrity_assessment")
    public class IntegrityAssessmentJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "program_id", nullable = true, length = 80)
    private String programId;

    @Column(name = "assessment_number", nullable = false, length = 80)
    private String assessmentNumber;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "description", nullable = true, columnDefinition = "text")
    private String description;

    @Column(name = "assessment_type_id", nullable = false, length = 80)
    private String assessmentTypeId;

    @Column(name = "methodology_id", nullable = true, length = 80)
    private String methodologyId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private IntegrityAssessmentStatus status;

    @Column(name = "assessment_date", nullable = false)
    private Instant assessmentDate;

    @Column(name = "assessed_by_actor_id", nullable = true, length = 80)
    private String assessedByActorId;

    @Column(name = "reviewed_by_actor_id", nullable = true, length = 80)
    private String reviewedByActorId;

    @Column(name = "approved_by_actor_id", nullable = true, length = 80)
    private String approvedByActorId;

    @Column(name = "approved_at", nullable = true)
    private Instant approvedAt;

    @Column(name = "workflow_instance_id", nullable = true, length = 80)
    private String workflowInstanceId;

    @Column(name = "audit_reference_id", nullable = true, length = 80)
    private String auditReferenceId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected IntegrityAssessmentJpaEntity() {
            // Required by JPA.
        }

        public IntegrityAssessmentJpaEntity(
                String id,
            String programId,
            String assessmentNumber,
            String title,
            String description,
            String assessmentTypeId,
            String methodologyId,
            IntegrityAssessmentStatus status,
            Instant assessmentDate,
            String assessedByActorId,
            String reviewedByActorId,
            String approvedByActorId,
            Instant approvedAt,
            String workflowInstanceId,
            String auditReferenceId,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.programId = programId;
        this.assessmentNumber = assessmentNumber;
        this.title = title;
        this.description = description;
        this.assessmentTypeId = assessmentTypeId;
        this.methodologyId = methodologyId;
        this.status = status;
        this.assessmentDate = assessmentDate;
        this.assessedByActorId = assessedByActorId;
        this.reviewedByActorId = reviewedByActorId;
        this.approvedByActorId = approvedByActorId;
        this.approvedAt = approvedAt;
        this.workflowInstanceId = workflowInstanceId;
        this.auditReferenceId = auditReferenceId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String programId() {
        return programId;
    }


    public String assessmentNumber() {
        return assessmentNumber;
    }


    public String title() {
        return title;
    }


    public String description() {
        return description;
    }


    public String assessmentTypeId() {
        return assessmentTypeId;
    }


    public String methodologyId() {
        return methodologyId;
    }


    public IntegrityAssessmentStatus status() {
        return status;
    }


    public Instant assessmentDate() {
        return assessmentDate;
    }


    public String assessedByActorId() {
        return assessedByActorId;
    }


    public String reviewedByActorId() {
        return reviewedByActorId;
    }


    public String approvedByActorId() {
        return approvedByActorId;
    }


    public Instant approvedAt() {
        return approvedAt;
    }


    public String workflowInstanceId() {
        return workflowInstanceId;
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
