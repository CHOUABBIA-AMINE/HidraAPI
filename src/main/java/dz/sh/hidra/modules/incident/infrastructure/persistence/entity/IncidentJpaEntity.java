/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for Incident.
 *
 */
package dz.sh.hidra.modules.incident.infrastructure.persistence.entity;

import dz.sh.hidra.modules.incident.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;

    /**
     * Database-backed JPA entity for Incident.
     */
    @Entity
    @Table(name = "hidra_incident")
    public class IncidentJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "incident_number", nullable = false, length = 80)
    private String incidentNumber;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "description", nullable = true, columnDefinition = "text")
    private String description;

    @Column(name = "classification_id", nullable = false, length = 80)
    private String classificationId;

    @Column(name = "severity_id", nullable = false, length = 80)
    private String severityId;

    @Column(name = "priority_id", nullable = true, length = 80)
    private String priorityId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private IncidentStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "source_type", nullable = false, length = 80)
    private IncidentSourceType sourceType;

    @Column(name = "source_reference_id", nullable = true, length = 80)
    private String sourceReferenceId;

    @Column(name = "source_reference_code", nullable = true, length = 160)
    private String sourceReferenceCode;

    @Column(name = "detected_at", nullable = false)
    private Instant detectedAt;

    @Column(name = "reported_at", nullable = false)
    private Instant reportedAt;

    @Column(name = "occurred_at", nullable = true)
    private Instant occurredAt;

    @Column(name = "topology_asset_type_code", nullable = true, length = 80)
    private String topologyAssetTypeCode;

    @Column(name = "topology_asset_id", nullable = true, length = 80)
    private String topologyAssetId;

    @Column(name = "topology_asset_code", nullable = true, length = 160)
    private String topologyAssetCode;

    @Column(name = "topology_asset_name_snapshot", nullable = true, length = 500)
    private String topologyAssetNameSnapshot;

    @Column(name = "location_description_ar", nullable = true, length = 500)
    private String locationDescriptionAr;

    @Column(name = "location_description_lt", nullable = true, length = 500)
    private String locationDescriptionLt;

    @Column(name = "latitude", nullable = true, precision = 10, scale = 7)
    private BigDecimal latitude;

    @Column(name = "longitude", nullable = true, precision = 10, scale = 7)
    private BigDecimal longitude;

    @Column(name = "responsible_organization_unit_id", nullable = true, length = 80)
    private String responsibleOrganizationUnitId;

    @Column(name = "responsible_organization_unit_code", nullable = true, length = 160)
    private String responsibleOrganizationUnitCode;

    @Column(name = "responsible_organization_unit_name_snapshot", nullable = true, length = 500)
    private String responsibleOrganizationUnitNameSnapshot;

    @Column(name = "responsible_actor_id", nullable = true, length = 80)
    private String responsibleActorId;

    @Column(name = "responsible_actor_name_snapshot", nullable = true, length = 255)
    private String responsibleActorNameSnapshot;

    @Column(name = "workflow_instance_id", nullable = true, length = 80)
    private String workflowInstanceId;

    @Column(name = "current_escalation_level", nullable = false)
    private int currentEscalationLevel;

    @Column(name = "contained_at", nullable = true)
    private Instant containedAt;

    @Column(name = "resolved_at", nullable = true)
    private Instant resolvedAt;

    @Column(name = "closed_at", nullable = true)
    private Instant closedAt;

    @Column(name = "cancelled_at", nullable = true)
    private Instant cancelledAt;

    @Column(name = "created_by_actor_id", nullable = false, length = 80)
    private String createdByActorId;

    @Column(name = "created_by_actor_name_snapshot", nullable = true, length = 255)
    private String createdByActorNameSnapshot;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected IncidentJpaEntity() {
            // Required by JPA.
        }

        public IncidentJpaEntity(
                String id,
            String incidentNumber,
            String title,
            String description,
            String classificationId,
            String severityId,
            String priorityId,
            IncidentStatus status,
            IncidentSourceType sourceType,
            String sourceReferenceId,
            String sourceReferenceCode,
            Instant detectedAt,
            Instant reportedAt,
            Instant occurredAt,
            String topologyAssetTypeCode,
            String topologyAssetId,
            String topologyAssetCode,
            String topologyAssetNameSnapshot,
            String locationDescriptionAr,
            String locationDescriptionLt,
            BigDecimal latitude,
            BigDecimal longitude,
            String responsibleOrganizationUnitId,
            String responsibleOrganizationUnitCode,
            String responsibleOrganizationUnitNameSnapshot,
            String responsibleActorId,
            String responsibleActorNameSnapshot,
            String workflowInstanceId,
            int currentEscalationLevel,
            Instant containedAt,
            Instant resolvedAt,
            Instant closedAt,
            Instant cancelledAt,
            String createdByActorId,
            String createdByActorNameSnapshot,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.incidentNumber = incidentNumber;
        this.title = title;
        this.description = description;
        this.classificationId = classificationId;
        this.severityId = severityId;
        this.priorityId = priorityId;
        this.status = status;
        this.sourceType = sourceType;
        this.sourceReferenceId = sourceReferenceId;
        this.sourceReferenceCode = sourceReferenceCode;
        this.detectedAt = detectedAt;
        this.reportedAt = reportedAt;
        this.occurredAt = occurredAt;
        this.topologyAssetTypeCode = topologyAssetTypeCode;
        this.topologyAssetId = topologyAssetId;
        this.topologyAssetCode = topologyAssetCode;
        this.topologyAssetNameSnapshot = topologyAssetNameSnapshot;
        this.locationDescriptionAr = locationDescriptionAr;
        this.locationDescriptionLt = locationDescriptionLt;
        this.latitude = latitude;
        this.longitude = longitude;
        this.responsibleOrganizationUnitId = responsibleOrganizationUnitId;
        this.responsibleOrganizationUnitCode = responsibleOrganizationUnitCode;
        this.responsibleOrganizationUnitNameSnapshot = responsibleOrganizationUnitNameSnapshot;
        this.responsibleActorId = responsibleActorId;
        this.responsibleActorNameSnapshot = responsibleActorNameSnapshot;
        this.workflowInstanceId = workflowInstanceId;
        this.currentEscalationLevel = currentEscalationLevel;
        this.containedAt = containedAt;
        this.resolvedAt = resolvedAt;
        this.closedAt = closedAt;
        this.cancelledAt = cancelledAt;
        this.createdByActorId = createdByActorId;
        this.createdByActorNameSnapshot = createdByActorNameSnapshot;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String incidentNumber() {
        return incidentNumber;
    }


    public String title() {
        return title;
    }


    public String description() {
        return description;
    }


    public String classificationId() {
        return classificationId;
    }


    public String severityId() {
        return severityId;
    }


    public String priorityId() {
        return priorityId;
    }


    public IncidentStatus status() {
        return status;
    }


    public IncidentSourceType sourceType() {
        return sourceType;
    }


    public String sourceReferenceId() {
        return sourceReferenceId;
    }


    public String sourceReferenceCode() {
        return sourceReferenceCode;
    }


    public Instant detectedAt() {
        return detectedAt;
    }


    public Instant reportedAt() {
        return reportedAt;
    }


    public Instant occurredAt() {
        return occurredAt;
    }


    public String topologyAssetTypeCode() {
        return topologyAssetTypeCode;
    }


    public String topologyAssetId() {
        return topologyAssetId;
    }


    public String topologyAssetCode() {
        return topologyAssetCode;
    }


    public String topologyAssetNameSnapshot() {
        return topologyAssetNameSnapshot;
    }


    public String locationDescriptionAr() {
        return locationDescriptionAr;
    }


    public String locationDescriptionLt() {
        return locationDescriptionLt;
    }


    public BigDecimal latitude() {
        return latitude;
    }


    public BigDecimal longitude() {
        return longitude;
    }


    public String responsibleOrganizationUnitId() {
        return responsibleOrganizationUnitId;
    }


    public String responsibleOrganizationUnitCode() {
        return responsibleOrganizationUnitCode;
    }


    public String responsibleOrganizationUnitNameSnapshot() {
        return responsibleOrganizationUnitNameSnapshot;
    }


    public String responsibleActorId() {
        return responsibleActorId;
    }


    public String responsibleActorNameSnapshot() {
        return responsibleActorNameSnapshot;
    }


    public String workflowInstanceId() {
        return workflowInstanceId;
    }


    public int currentEscalationLevel() {
        return currentEscalationLevel;
    }


    public Instant containedAt() {
        return containedAt;
    }


    public Instant resolvedAt() {
        return resolvedAt;
    }


    public Instant closedAt() {
        return closedAt;
    }


    public Instant cancelledAt() {
        return cancelledAt;
    }


    public String createdByActorId() {
        return createdByActorId;
    }


    public String createdByActorNameSnapshot() {
        return createdByActorNameSnapshot;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
