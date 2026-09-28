/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmLifecycleEvent
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.domain.model
 *
 * @Description : Append-only timeline event for alarm state and lifecycle actions.
 *
 */
package dz.sh.hidra.modules.alarm.domain.model;

import dz.sh.hidra.modules.alarm.domain.exception.InvalidAlarmValueException;
import dz.sh.hidra.modules.alarm.domain.value.*;
import java.time.Instant;

    /**
     * Append-only timeline event for alarm state and lifecycle actions.
     *
         * @param id id
     * @param alarmId alarmId
     * @param eventType eventType
     * @param previousState previousState
     * @param newState newState
     * @param reasonId reasonId
     * @param reasonText reasonText
     * @param actorId actorId
     * @param actorDisplayName actorDisplayName
     * @param organizationUnitId organizationUnitId
     * @param organizationUnitCode organizationUnitCode
     * @param organizationUnitNameSnapshot organizationUnitNameSnapshot
     * @param occurredAt occurredAt
     * @param correlationId correlationId
     * @param metadataJson metadataJson
     */
    public record AlarmLifecycleEvent(
            String id,
        String alarmId,
        AlarmLifecycleEventType eventType,
        AlarmState previousState,
        AlarmState newState,
        String reasonId,
        String reasonText,
        String actorId,
        String actorDisplayName,
        String organizationUnitId,
        String organizationUnitCode,
        String organizationUnitNameSnapshot,
        Instant occurredAt,
        String correlationId,
        String metadataJson
    ) {

        public AlarmLifecycleEvent {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidAlarmValueException("AlarmLifecycleEvent id must not be blank.");
        }
        // HRA-051 required: alarmId
        if (alarmId == null || alarmId.isBlank()) {
            throw new InvalidAlarmValueException("AlarmLifecycleEvent alarm id must not be blank.");
        }
        // HRA-051 required: eventType
        if (eventType == null) {
            throw new InvalidAlarmValueException("AlarmLifecycleEvent event type must not be null.");
        }
        // HRA-051 required: newState
        if (newState == null) {
            throw new InvalidAlarmValueException("AlarmLifecycleEvent new state must not be null.");
        }
        // HRA-051 required: actorId
        if (actorId == null || actorId.isBlank()) {
            throw new InvalidAlarmValueException("AlarmLifecycleEvent actor id must not be blank.");
        }
        // HRA-051 required: occurredAt
        if (occurredAt == null) {
            throw new InvalidAlarmValueException("AlarmLifecycleEvent occurred at must not be null.");
        }

        id = normalize(id);
        alarmId = normalize(alarmId);
        reasonId = normalize(reasonId);
        reasonText = normalize(reasonText);
        actorId = normalize(actorId);
        actorDisplayName = normalize(actorDisplayName);
        organizationUnitId = normalize(organizationUnitId);
        organizationUnitCode = normalize(organizationUnitCode);
        organizationUnitNameSnapshot = normalize(organizationUnitNameSnapshot);
        correlationId = normalize(correlationId);
        metadataJson = normalize(metadataJson);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
