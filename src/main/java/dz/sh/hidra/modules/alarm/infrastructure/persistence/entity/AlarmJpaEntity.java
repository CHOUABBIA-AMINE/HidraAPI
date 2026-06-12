/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for Alarm.
 *
 */
package dz.sh.hidra.modules.alarm.infrastructure.persistence.entity;

import dz.sh.hidra.modules.alarm.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for Alarm.
     */
    @Entity
    @Table(name = "hidra_alarm")
    public class AlarmJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "alarm_number", nullable = false, length = 80)
    private String alarmNumber;

    @Column(name = "alarm_type_id", nullable = false, length = 80)
    private String alarmTypeId;

    @Column(name = "severity_id", nullable = false, length = 80)
    private String severityId;

    @Column(name = "priority_id", nullable = true, length = 80)
    private String priorityId;

    @Column(name = "title_ar", nullable = true, length = 255)
    private String titleAr;

    @Column(name = "title_fr", nullable = false, length = 255)
    private String titleFr;

    @Column(name = "title_en", nullable = true, length = 255)
    private String titleEn;

    @Column(name = "description_ar", nullable = true, columnDefinition = "text")
    private String descriptionAr;

    @Column(name = "description_fr", nullable = true, columnDefinition = "text")
    private String descriptionFr;

    @Column(name = "description_en", nullable = true, columnDefinition = "text")
    private String descriptionEn;

    @Enumerated(EnumType.STRING)
    @Column(name = "source_type", nullable = false, length = 80)
    private AlarmSourceType sourceType;

    @Column(name = "source_reference_id", nullable = true, length = 80)
    private String sourceReferenceId;

    @Column(name = "monitoring_alert_candidate_id", nullable = true, length = 80)
    private String monitoringAlertCandidateId;

    @Column(name = "monitoring_evaluation_id", nullable = true, length = 80)
    private String monitoringEvaluationId;

    @Column(name = "telemetry_reading_id", nullable = true, length = 80)
    private String telemetryReadingId;

    @Column(name = "planning_target_id", nullable = true, length = 80)
    private String planningTargetId;

    @Column(name = "topology_asset_type_code", nullable = false, length = 80)
    private String topologyAssetTypeCode;

    @Column(name = "topology_asset_id", nullable = false, length = 80)
    private String topologyAssetId;

    @Column(name = "topology_asset_code", nullable = false, length = 160)
    private String topologyAssetCode;

    @Column(name = "topology_asset_name_snapshot", nullable = true, length = 500)
    private String topologyAssetNameSnapshot;

    @Enumerated(EnumType.STRING)
    @Column(name = "current_state", nullable = false, length = 40)
    private AlarmState currentState;

    @Column(name = "raised_at", nullable = false)
    private Instant raisedAt;

    @Column(name = "first_detected_at", nullable = true)
    private Instant firstDetectedAt;

    @Column(name = "last_updated_at", nullable = false)
    private Instant lastUpdatedAt;

    @Column(name = "cleared_at", nullable = true)
    private Instant clearedAt;

    @Column(name = "closed_at", nullable = true)
    private Instant closedAt;

    @Column(name = "acknowledged_at", nullable = true)
    private Instant acknowledgedAt;

    @Column(name = "acknowledged_by_actor_id", nullable = true, length = 80)
    private String acknowledgedByActorId;

    @Column(name = "owning_organization_unit_id", nullable = true, length = 80)
    private String owningOrganizationUnitId;

    @Column(name = "owning_organization_unit_code", nullable = true, length = 160)
    private String owningOrganizationUnitCode;

    @Column(name = "owning_organization_unit_name_snapshot", nullable = true, length = 500)
    private String owningOrganizationUnitNameSnapshot;

    @Column(name = "workflow_instance_id", nullable = true, length = 80)
    private String workflowInstanceId;

    @Column(name = "incident_id", nullable = true, length = 80)
    private String incidentId;

    @Column(name = "correlation_id", nullable = true, length = 80)
    private String correlationId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected AlarmJpaEntity() {
            // Required by JPA.
        }

        public AlarmJpaEntity(
                String id,
            String alarmNumber,
            String alarmTypeId,
            String severityId,
            String priorityId,
            String titleAr,
            String titleFr,
            String titleEn,
            String descriptionAr,
            String descriptionFr,
            String descriptionEn,
            AlarmSourceType sourceType,
            String sourceReferenceId,
            String monitoringAlertCandidateId,
            String monitoringEvaluationId,
            String telemetryReadingId,
            String planningTargetId,
            String topologyAssetTypeCode,
            String topologyAssetId,
            String topologyAssetCode,
            String topologyAssetNameSnapshot,
            AlarmState currentState,
            Instant raisedAt,
            Instant firstDetectedAt,
            Instant lastUpdatedAt,
            Instant clearedAt,
            Instant closedAt,
            Instant acknowledgedAt,
            String acknowledgedByActorId,
            String owningOrganizationUnitId,
            String owningOrganizationUnitCode,
            String owningOrganizationUnitNameSnapshot,
            String workflowInstanceId,
            String incidentId,
            String correlationId,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.alarmNumber = alarmNumber;
        this.alarmTypeId = alarmTypeId;
        this.severityId = severityId;
        this.priorityId = priorityId;
        this.titleAr = titleAr;
        this.titleFr = titleFr;
        this.titleEn = titleEn;
        this.descriptionAr = descriptionAr;
        this.descriptionFr = descriptionFr;
        this.descriptionEn = descriptionEn;
        this.sourceType = sourceType;
        this.sourceReferenceId = sourceReferenceId;
        this.monitoringAlertCandidateId = monitoringAlertCandidateId;
        this.monitoringEvaluationId = monitoringEvaluationId;
        this.telemetryReadingId = telemetryReadingId;
        this.planningTargetId = planningTargetId;
        this.topologyAssetTypeCode = topologyAssetTypeCode;
        this.topologyAssetId = topologyAssetId;
        this.topologyAssetCode = topologyAssetCode;
        this.topologyAssetNameSnapshot = topologyAssetNameSnapshot;
        this.currentState = currentState;
        this.raisedAt = raisedAt;
        this.firstDetectedAt = firstDetectedAt;
        this.lastUpdatedAt = lastUpdatedAt;
        this.clearedAt = clearedAt;
        this.closedAt = closedAt;
        this.acknowledgedAt = acknowledgedAt;
        this.acknowledgedByActorId = acknowledgedByActorId;
        this.owningOrganizationUnitId = owningOrganizationUnitId;
        this.owningOrganizationUnitCode = owningOrganizationUnitCode;
        this.owningOrganizationUnitNameSnapshot = owningOrganizationUnitNameSnapshot;
        this.workflowInstanceId = workflowInstanceId;
        this.incidentId = incidentId;
        this.correlationId = correlationId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String alarmNumber() {
        return alarmNumber;
    }


    public String alarmTypeId() {
        return alarmTypeId;
    }


    public String severityId() {
        return severityId;
    }


    public String priorityId() {
        return priorityId;
    }


    public String titleAr() {
        return titleAr;
    }


    public String titleFr() {
        return titleFr;
    }


    public String titleEn() {
        return titleEn;
    }


    public String descriptionAr() {
        return descriptionAr;
    }


    public String descriptionFr() {
        return descriptionFr;
    }


    public String descriptionEn() {
        return descriptionEn;
    }


    public AlarmSourceType sourceType() {
        return sourceType;
    }


    public String sourceReferenceId() {
        return sourceReferenceId;
    }


    public String monitoringAlertCandidateId() {
        return monitoringAlertCandidateId;
    }


    public String monitoringEvaluationId() {
        return monitoringEvaluationId;
    }


    public String telemetryReadingId() {
        return telemetryReadingId;
    }


    public String planningTargetId() {
        return planningTargetId;
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


    public AlarmState currentState() {
        return currentState;
    }


    public Instant raisedAt() {
        return raisedAt;
    }


    public Instant firstDetectedAt() {
        return firstDetectedAt;
    }


    public Instant lastUpdatedAt() {
        return lastUpdatedAt;
    }


    public Instant clearedAt() {
        return clearedAt;
    }


    public Instant closedAt() {
        return closedAt;
    }


    public Instant acknowledgedAt() {
        return acknowledgedAt;
    }


    public String acknowledgedByActorId() {
        return acknowledgedByActorId;
    }


    public String owningOrganizationUnitId() {
        return owningOrganizationUnitId;
    }


    public String owningOrganizationUnitCode() {
        return owningOrganizationUnitCode;
    }


    public String owningOrganizationUnitNameSnapshot() {
        return owningOrganizationUnitNameSnapshot;
    }


    public String workflowInstanceId() {
        return workflowInstanceId;
    }


    public String incidentId() {
        return incidentId;
    }


    public String correlationId() {
        return correlationId;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
