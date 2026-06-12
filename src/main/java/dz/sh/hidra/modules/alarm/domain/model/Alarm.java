/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : Alarm
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.domain.model
 *
 * @Description : Formal operational alarm instance.
 *
 */
package dz.sh.hidra.modules.alarm.domain.model;

import dz.sh.hidra.modules.alarm.domain.value.*;
import java.time.Instant;

    /**
     * Formal operational alarm instance.
     *
         * @param id id
     * @param alarmNumber alarmNumber
     * @param alarmTypeId alarmTypeId
     * @param severityId severityId
     * @param priorityId priorityId
     * @param titleAr titleAr
     * @param titleFr titleFr
     * @param titleEn titleEn
     * @param descriptionAr descriptionAr
     * @param descriptionFr descriptionFr
     * @param descriptionEn descriptionEn
     * @param sourceType sourceType
     * @param sourceReferenceId sourceReferenceId
     * @param monitoringAlertCandidateId monitoringAlertCandidateId
     * @param monitoringEvaluationId monitoringEvaluationId
     * @param telemetryReadingId telemetryReadingId
     * @param planningTargetId planningTargetId
     * @param topologyAssetTypeCode topologyAssetTypeCode
     * @param topologyAssetId topologyAssetId
     * @param topologyAssetCode topologyAssetCode
     * @param topologyAssetNameSnapshot topologyAssetNameSnapshot
     * @param currentState currentState
     * @param raisedAt raisedAt
     * @param firstDetectedAt firstDetectedAt
     * @param lastUpdatedAt lastUpdatedAt
     * @param clearedAt clearedAt
     * @param closedAt closedAt
     * @param acknowledgedAt acknowledgedAt
     * @param acknowledgedByActorId acknowledgedByActorId
     * @param owningOrganizationUnitId owningOrganizationUnitId
     * @param owningOrganizationUnitCode owningOrganizationUnitCode
     * @param owningOrganizationUnitNameSnapshot owningOrganizationUnitNameSnapshot
     * @param workflowInstanceId workflowInstanceId
     * @param incidentId incidentId
     * @param correlationId correlationId
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record Alarm(
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

        public Alarm {
        id = normalize(id);
        alarmNumber = normalize(alarmNumber);
        alarmTypeId = normalize(alarmTypeId);
        severityId = normalize(severityId);
        priorityId = normalize(priorityId);
        titleAr = normalize(titleAr);
        titleFr = normalize(titleFr);
        titleEn = normalize(titleEn);
        descriptionAr = normalize(descriptionAr);
        descriptionFr = normalize(descriptionFr);
        descriptionEn = normalize(descriptionEn);
        sourceReferenceId = normalize(sourceReferenceId);
        monitoringAlertCandidateId = normalize(monitoringAlertCandidateId);
        monitoringEvaluationId = normalize(monitoringEvaluationId);
        telemetryReadingId = normalize(telemetryReadingId);
        planningTargetId = normalize(planningTargetId);
        topologyAssetTypeCode = normalize(topologyAssetTypeCode);
        topologyAssetId = normalize(topologyAssetId);
        topologyAssetCode = normalize(topologyAssetCode);
        topologyAssetNameSnapshot = normalize(topologyAssetNameSnapshot);
        acknowledgedByActorId = normalize(acknowledgedByActorId);
        owningOrganizationUnitId = normalize(owningOrganizationUnitId);
        owningOrganizationUnitCode = normalize(owningOrganizationUnitCode);
        owningOrganizationUnitNameSnapshot = normalize(owningOrganizationUnitNameSnapshot);
        workflowInstanceId = normalize(workflowInstanceId);
        incidentId = normalize(incidentId);
        correlationId = normalize(correlationId);
        }
        public boolean activeLifecycle() {
            return currentState != AlarmState.CLOSED
                    && currentState != AlarmState.CANCELLED;
        }

        public boolean closed() {
            return currentState == AlarmState.CLOSED || currentState == AlarmState.CANCELLED;
        }
        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
