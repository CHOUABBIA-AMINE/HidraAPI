/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityCaseJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for IntegrityCase.
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
     * Database-backed JPA entity for IntegrityCase.
     */
    @Entity
    @Table(name = "hidra_integrity_case")
    public class IntegrityCaseJpaEntity {

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

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private IntegrityCaseStatus status;

    @Column(name = "severity_id", nullable = true, length = 80)
    private String severityId;

    @Column(name = "topology_asset_type_code", nullable = false, length = 80)
    private String topologyAssetTypeCode;

    @Column(name = "topology_asset_id", nullable = false, length = 80)
    private String topologyAssetId;

    @Column(name = "topology_asset_code_snapshot", nullable = true, length = 160)
    private String topologyAssetCodeSnapshot;

    @Column(name = "primary_defect_id", nullable = true, length = 80)
    private String primaryDefectId;

    @Column(name = "source_incident_id", nullable = true, length = 80)
    private String sourceIncidentId;

    @Column(name = "source_hse_case_id", nullable = true, length = 80)
    private String sourceHseCaseId;

    @Column(name = "responsible_organization_unit_id", nullable = true, length = 80)
    private String responsibleOrganizationUnitId;

    @Column(name = "workflow_instance_id", nullable = true, length = 80)
    private String workflowInstanceId;

    @Column(name = "opened_at", nullable = false)
    private Instant openedAt;

    @Column(name = "closed_at", nullable = true)
    private Instant closedAt;

    @Column(name = "opened_by_actor_id", nullable = true, length = 80)
    private String openedByActorId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected IntegrityCaseJpaEntity() {
            // Required by JPA.
        }

        public IntegrityCaseJpaEntity(
                String id,
            String caseNumber,
            String title,
            String description,
            String caseTypeId,
            IntegrityCaseStatus status,
            String severityId,
            String topologyAssetTypeCode,
            String topologyAssetId,
            String topologyAssetCodeSnapshot,
            String primaryDefectId,
            String sourceIncidentId,
            String sourceHseCaseId,
            String responsibleOrganizationUnitId,
            String workflowInstanceId,
            Instant openedAt,
            Instant closedAt,
            String openedByActorId,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.caseNumber = caseNumber;
        this.title = title;
        this.description = description;
        this.caseTypeId = caseTypeId;
        this.status = status;
        this.severityId = severityId;
        this.topologyAssetTypeCode = topologyAssetTypeCode;
        this.topologyAssetId = topologyAssetId;
        this.topologyAssetCodeSnapshot = topologyAssetCodeSnapshot;
        this.primaryDefectId = primaryDefectId;
        this.sourceIncidentId = sourceIncidentId;
        this.sourceHseCaseId = sourceHseCaseId;
        this.responsibleOrganizationUnitId = responsibleOrganizationUnitId;
        this.workflowInstanceId = workflowInstanceId;
        this.openedAt = openedAt;
        this.closedAt = closedAt;
        this.openedByActorId = openedByActorId;
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


    public IntegrityCaseStatus status() {
        return status;
    }


    public String severityId() {
        return severityId;
    }


    public String topologyAssetTypeCode() {
        return topologyAssetTypeCode;
    }


    public String topologyAssetId() {
        return topologyAssetId;
    }


    public String topologyAssetCodeSnapshot() {
        return topologyAssetCodeSnapshot;
    }


    public String primaryDefectId() {
        return primaryDefectId;
    }


    public String sourceIncidentId() {
        return sourceIncidentId;
    }


    public String sourceHseCaseId() {
        return sourceHseCaseId;
    }


    public String responsibleOrganizationUnitId() {
        return responsibleOrganizationUnitId;
    }


    public String workflowInstanceId() {
        return workflowInstanceId;
    }


    public Instant openedAt() {
        return openedAt;
    }


    public Instant closedAt() {
        return closedAt;
    }


    public String openedByActorId() {
        return openedByActorId;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
