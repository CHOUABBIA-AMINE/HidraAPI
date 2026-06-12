/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmShelving
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
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
