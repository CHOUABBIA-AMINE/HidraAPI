/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmLifecycleEvent
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.domain.model
 *
 * @Description : Enforces Alarm-owned transactional lifecycle evidence and integrity.
 *
 */
package dz.sh.hidra.modules.alarm.domain.model;

import dz.sh.hidra.modules.alarm.domain.value.AlarmLifecycleEventType;
import dz.sh.hidra.modules.alarm.domain.value.AlarmState;
import java.time.Instant;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;

public record AlarmLifecycleEvent(
        String id, String alarmId, AlarmLifecycleEventType eventType,
        AlarmState previousState, AlarmState newState, String reasonId, String reasonText,
        String actorId, String actorDisplayName, String organizationUnitId,
        String organizationUnitCode, String organizationUnitNameSnapshot,
        Instant occurredAt, String correlationId, String metadataJson
) {
    public AlarmLifecycleEvent {
        if (id == null || id.isBlank() || alarmId == null || alarmId.isBlank()
                || actorId == null || actorId.isBlank()) {
            throw new IllegalArgumentException("Lifecycle identity and actor must not be blank.");
        }
        Objects.requireNonNull(eventType, "Lifecycle event type is required.");
        Objects.requireNonNull(newState, "Lifecycle new state is required.");
        Objects.requireNonNull(occurredAt, "Lifecycle occurrence time is required.");
    }

    public static String operationId(String operation, String evidenceId) {
        Objects.requireNonNull(operation); Objects.requireNonNull(evidenceId);
        return UUID.nameUUIDFromBytes((operation + "\u0000" + evidenceId)
                .getBytes(StandardCharsets.UTF_8)).toString();
    }
}
