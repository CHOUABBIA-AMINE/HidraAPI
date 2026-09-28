/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmShelving
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.domain.model
 *
 * @Description : Temporary time-bounded shelving of an alarm.
 *
 */
package dz.sh.hidra.modules.alarm.domain.model;

import dz.sh.hidra.modules.alarm.domain.exception.InvalidAlarmValueException;
import dz.sh.hidra.modules.alarm.domain.value.*;
import java.time.Instant;

    /**
     * Temporary time-bounded shelving of an alarm.
     *
         * @param id id
     * @param alarmId alarmId
     * @param shelvingReasonId shelvingReasonId
     * @param reasonText reasonText
     * @param shelvedByActorId shelvedByActorId
     * @param shelvedAt shelvedAt
     * @param shelvedUntil shelvedUntil
     * @param unshelvedAt unshelvedAt
     * @param unshelvedByActorId unshelvedByActorId
     * @param status status
     * @param correlationId correlationId
     */
    public record AlarmShelving(
            String id,
        String alarmId,
        String shelvingReasonId,
        String reasonText,
        String shelvedByActorId,
        Instant shelvedAt,
        Instant shelvedUntil,
        Instant unshelvedAt,
        String unshelvedByActorId,
        AlarmShelvingStatus status,
        String correlationId
    ) {

        public AlarmShelving {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidAlarmValueException("AlarmShelving id must not be blank.");
        }
        // HRA-051 required: alarmId
        if (alarmId == null || alarmId.isBlank()) {
            throw new InvalidAlarmValueException("AlarmShelving alarm id must not be blank.");
        }
        // HRA-051 required: shelvingReasonId
        if (shelvingReasonId == null || shelvingReasonId.isBlank()) {
            throw new InvalidAlarmValueException("AlarmShelving shelving reason id must not be blank.");
        }
        // HRA-051 required: shelvedByActorId
        if (shelvedByActorId == null || shelvedByActorId.isBlank()) {
            throw new InvalidAlarmValueException("AlarmShelving shelved by actor id must not be blank.");
        }
        // HRA-051 required: shelvedAt
        if (shelvedAt == null) {
            throw new InvalidAlarmValueException("AlarmShelving shelved at must not be null.");
        }
        // HRA-051 required: shelvedUntil
        if (shelvedUntil == null) {
            throw new InvalidAlarmValueException("AlarmShelving shelved until must not be null.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidAlarmValueException("AlarmShelving status must not be null.");
        }

        id = normalize(id);
        alarmId = normalize(alarmId);
        shelvingReasonId = normalize(shelvingReasonId);
        reasonText = normalize(reasonText);
        shelvedByActorId = normalize(shelvedByActorId);
        unshelvedByActorId = normalize(unshelvedByActorId);
        correlationId = normalize(correlationId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
