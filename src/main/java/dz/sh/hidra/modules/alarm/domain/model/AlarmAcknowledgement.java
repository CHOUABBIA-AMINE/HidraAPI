/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmAcknowledgement
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.domain.model
 *
 * @Description : Actor acknowledgement of an alarm.
 *
 */
package dz.sh.hidra.modules.alarm.domain.model;

import dz.sh.hidra.modules.alarm.domain.exception.InvalidAlarmValueException;
import java.time.Instant;

    /**
     * Actor acknowledgement of an alarm.
     *
         * @param id id
     * @param alarmId alarmId
     * @param acknowledgedByActorId acknowledgedByActorId
     * @param acknowledgedByDisplayName acknowledgedByDisplayName
     * @param organizationUnitId organizationUnitId
     * @param organizationUnitCode organizationUnitCode
     * @param acknowledgedAt acknowledgedAt
     * @param comment comment
     * @param correlationId correlationId
     */
    public record AlarmAcknowledgement(
            String id,
        String alarmId,
        String acknowledgedByActorId,
        String acknowledgedByDisplayName,
        String organizationUnitId,
        String organizationUnitCode,
        Instant acknowledgedAt,
        String comment,
        String correlationId
    ) {

        public AlarmAcknowledgement {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidAlarmValueException("AlarmAcknowledgement id must not be blank.");
        }
        // HRA-051 required: alarmId
        if (alarmId == null || alarmId.isBlank()) {
            throw new InvalidAlarmValueException("AlarmAcknowledgement alarm id must not be blank.");
        }
        // HRA-051 required: acknowledgedByActorId
        if (acknowledgedByActorId == null || acknowledgedByActorId.isBlank()) {
            throw new InvalidAlarmValueException("AlarmAcknowledgement acknowledged by actor id must not be blank.");
        }
        // HRA-051 required: acknowledgedAt
        if (acknowledgedAt == null) {
            throw new InvalidAlarmValueException("AlarmAcknowledgement acknowledged at must not be null.");
        }

        id = normalize(id);
        alarmId = normalize(alarmId);
        acknowledgedByActorId = normalize(acknowledgedByActorId);
        acknowledgedByDisplayName = normalize(acknowledgedByDisplayName);
        organizationUnitId = normalize(organizationUnitId);
        organizationUnitCode = normalize(organizationUnitCode);
        comment = normalize(comment);
        correlationId = normalize(correlationId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
