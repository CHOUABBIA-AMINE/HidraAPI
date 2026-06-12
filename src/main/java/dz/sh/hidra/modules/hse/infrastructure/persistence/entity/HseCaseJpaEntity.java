/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseCaseJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for HseCase.
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
     * Database-backed JPA entity for HseCase.
     */
    @Entity
    @Table(name = "hidra_hse_case")
    public class HseCaseJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "case_number", nullable = false, length = 80)
    private String caseNumber;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "description", nullable = true, columnDefinition = "text")
    private String description;

    @Column(name = "case_type_id", nullable = false, length = 80)
    private String caseTypeId;

    @Column(name = "severity_id", nullable = false, length = 80)
    private String severityId;

    @Column(name = "priority_id", nullable = true, length = 80)
    private String priorityId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private HseCaseStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "source_type", nullable = false, length = 80)
    private HseCaseSourceType sourceType;

    @Column(name = "incident_reference_id", nullable = true, length = 80)
    private String incidentReferenceId;

    @Column(name = "incident_code_snapshot", nullable = true, length = 160)
    private String incidentCodeSnapshot;

    @Column(name = "incident_title_snapshot", nullable = true, length = 255)
    private String incidentTitleSnapshot;

    @Column(name = "target_module", nullable = true, length = 80)
    private String targetModule;

    @Column(name = "target_type_code", nullable = true, length = 80)
    private String targetTypeCode;

    @Column(name = "target_id", nullable = true, length = 80)
    private String targetId;

    @Column(name = "target_code_snapshot", nullable = true, length = 160)
    private String targetCodeSnapshot;

    @Column(name = "target_label_snapshot", nullable = true, length = 500)
    private String targetLabelSnapshot;

    @Column(name = "occurred_at", nullable = true)
    private Instant occurredAt;

    @Column(name = "reported_at", nullable = false)
    private Instant reportedAt;

    @Column(name = "reported_by_actor_id", nullable = true, length = 80)
    private String reportedByActorId;

    @Column(name = "reported_by_display_name_snapshot", nullable = true, length = 255)
    private String reportedByDisplayNameSnapshot;

    @Column(name = "responsible_organization_unit_id", nullable = true, length = 80)
    private String responsibleOrganizationUnitId;

    @Column(name = "responsible_organization_unit_name_snapshot", nullable = true, length = 500)
    private String responsibleOrganizationUnitNameSnapshot;

    @Column(name = "workflow_instance_id", nullable = true, length = 80)
    private String workflowInstanceId;

    @Column(name = "audit_reference_id", nullable = true, length = 80)
    private String auditReferenceId;

    @Column(name = "controlled_at", nullable = true)
    private Instant controlledAt;

    @Column(name = "resolved_at", nullable = true)
    private Instant resolvedAt;

    @Column(name = "closed_at", nullable = true)
    private Instant closedAt;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected HseCaseJpaEntity() {
            // Required by JPA.
        }

        public HseCaseJpaEntity(
                String id,
            String caseNumber,
            String title,
            String description,
            String caseTypeId,
            String severityId,
            String priorityId,
            HseCaseStatus status,
            HseCaseSourceType sourceType,
            String incidentReferenceId,
            String incidentCodeSnapshot,
            String incidentTitleSnapshot,
            String targetModule,
            String targetTypeCode,
            String targetId,
            String targetCodeSnapshot,
            String targetLabelSnapshot,
            Instant occurredAt,
            Instant reportedAt,
            String reportedByActorId,
            String reportedByDisplayNameSnapshot,
            String responsibleOrganizationUnitId,
            String responsibleOrganizationUnitNameSnapshot,
            String workflowInstanceId,
            String auditReferenceId,
            Instant controlledAt,
            Instant resolvedAt,
            Instant closedAt,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.caseNumber = caseNumber;
        this.title = title;
        this.description = description;
        this.caseTypeId = caseTypeId;
        this.severityId = severityId;
        this.priorityId = priorityId;
        this.status = status;
        this.sourceType = sourceType;
        this.incidentReferenceId = incidentReferenceId;
        this.incidentCodeSnapshot = incidentCodeSnapshot;
        this.incidentTitleSnapshot = incidentTitleSnapshot;
        this.targetModule = targetModule;
        this.targetTypeCode = targetTypeCode;
        this.targetId = targetId;
        this.targetCodeSnapshot = targetCodeSnapshot;
        this.targetLabelSnapshot = targetLabelSnapshot;
        this.occurredAt = occurredAt;
        this.reportedAt = reportedAt;
        this.reportedByActorId = reportedByActorId;
        this.reportedByDisplayNameSnapshot = reportedByDisplayNameSnapshot;
        this.responsibleOrganizationUnitId = responsibleOrganizationUnitId;
        this.responsibleOrganizationUnitNameSnapshot = responsibleOrganizationUnitNameSnapshot;
        this.workflowInstanceId = workflowInstanceId;
        this.auditReferenceId = auditReferenceId;
        this.controlledAt = controlledAt;
        this.resolvedAt = resolvedAt;
        this.closedAt = closedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String caseNumber() {
        return caseNumber;
    }


    public String title() {
        return title;
    }


    public String description() {
        return description;
    }


    public String caseTypeId() {
        return caseTypeId;
    }


    public String severityId() {
        return severityId;
    }


    public String priorityId() {
        return priorityId;
    }


    public HseCaseStatus status() {
        return status;
    }


    public HseCaseSourceType sourceType() {
        return sourceType;
    }


    public String incidentReferenceId() {
        return incidentReferenceId;
    }


    public String incidentCodeSnapshot() {
        return incidentCodeSnapshot;
    }


    public String incidentTitleSnapshot() {
        return incidentTitleSnapshot;
    }


    public String targetModule() {
        return targetModule;
    }


    public String targetTypeCode() {
        return targetTypeCode;
    }


    public String targetId() {
        return targetId;
    }


    public String targetCodeSnapshot() {
        return targetCodeSnapshot;
    }


    public String targetLabelSnapshot() {
        return targetLabelSnapshot;
    }


    public Instant occurredAt() {
        return occurredAt;
    }


    public Instant reportedAt() {
        return reportedAt;
    }


    public String reportedByActorId() {
        return reportedByActorId;
    }


    public String reportedByDisplayNameSnapshot() {
        return reportedByDisplayNameSnapshot;
    }


    public String responsibleOrganizationUnitId() {
        return responsibleOrganizationUnitId;
    }


    public String responsibleOrganizationUnitNameSnapshot() {
        return responsibleOrganizationUnitNameSnapshot;
    }


    public String workflowInstanceId() {
        return workflowInstanceId;
    }


    public String auditReferenceId() {
        return auditReferenceId;
    }


    public Instant controlledAt() {
        return controlledAt;
    }


    public Instant resolvedAt() {
        return resolvedAt;
    }


    public Instant closedAt() {
        return closedAt;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
