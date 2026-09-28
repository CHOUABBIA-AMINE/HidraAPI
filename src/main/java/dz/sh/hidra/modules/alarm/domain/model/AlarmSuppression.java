/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmSuppression
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.domain.model
 *
 * @Description : Controlled suppression of alarm generation or visibility.
 *
 */
package dz.sh.hidra.modules.alarm.domain.model;

import dz.sh.hidra.modules.alarm.domain.exception.InvalidAlarmValueException;
import dz.sh.hidra.modules.alarm.domain.value.*;
import java.time.Instant;

    /**
     * Controlled suppression of alarm generation or visibility.
     *
         * @param id id
     * @param scopeType scopeType
     * @param scopeReferenceId scopeReferenceId
     * @param alarmId alarmId
     * @param alarmTypeId alarmTypeId
     * @param topologyAssetTypeCode topologyAssetTypeCode
     * @param topologyAssetId topologyAssetId
     * @param suppressionReasonId suppressionReasonId
     * @param reasonText reasonText
     * @param suppressedByActorId suppressedByActorId
     * @param suppressedAt suppressedAt
     * @param suppressedUntil suppressedUntil
     * @param releasedAt releasedAt
     * @param releasedByActorId releasedByActorId
     * @param status status
     * @param workflowInstanceId workflowInstanceId
     * @param correlationId correlationId
     */
    public record AlarmSuppression(
            String id,
        AlarmSuppressionScopeType scopeType,
        String scopeReferenceId,
        String alarmId,
        String alarmTypeId,
        String topologyAssetTypeCode,
        String topologyAssetId,
        String suppressionReasonId,
        String reasonText,
        String suppressedByActorId,
        Instant suppressedAt,
        Instant suppressedUntil,
        Instant releasedAt,
        String releasedByActorId,
        AlarmSuppressionStatus status,
        String workflowInstanceId,
        String correlationId
    ) {

        public AlarmSuppression {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidAlarmValueException("AlarmSuppression id must not be blank.");
        }
        // HRA-051 required: scopeType
        if (scopeType == null) {
            throw new InvalidAlarmValueException("AlarmSuppression scope type must not be null.");
        }
        // HRA-051 required: scopeReferenceId
        if (scopeReferenceId == null || scopeReferenceId.isBlank()) {
            throw new InvalidAlarmValueException("AlarmSuppression scope reference id must not be blank.");
        }
        // HRA-051 required: suppressionReasonId
        if (suppressionReasonId == null || suppressionReasonId.isBlank()) {
            throw new InvalidAlarmValueException("AlarmSuppression suppression reason id must not be blank.");
        }
        // HRA-051 required: suppressedByActorId
        if (suppressedByActorId == null || suppressedByActorId.isBlank()) {
            throw new InvalidAlarmValueException("AlarmSuppression suppressed by actor id must not be blank.");
        }
        // HRA-051 required: suppressedAt
        if (suppressedAt == null) {
            throw new InvalidAlarmValueException("AlarmSuppression suppressed at must not be null.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidAlarmValueException("AlarmSuppression status must not be null.");
        }

        id = normalize(id);
        scopeReferenceId = normalize(scopeReferenceId);
        alarmId = normalize(alarmId);
        alarmTypeId = normalize(alarmTypeId);
        topologyAssetTypeCode = normalize(topologyAssetTypeCode);
        topologyAssetId = normalize(topologyAssetId);
        suppressionReasonId = normalize(suppressionReasonId);
        reasonText = normalize(reasonText);
        suppressedByActorId = normalize(suppressedByActorId);
        releasedByActorId = normalize(releasedByActorId);
        workflowInstanceId = normalize(workflowInstanceId);
        correlationId = normalize(correlationId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
