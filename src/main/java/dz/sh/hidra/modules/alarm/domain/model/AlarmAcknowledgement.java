/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmAcknowledgement
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
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
