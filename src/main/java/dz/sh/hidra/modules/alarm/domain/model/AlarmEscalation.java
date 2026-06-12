/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmEscalation
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.domain.model
 *
 * @Description : Escalation of alarm to responsibility level, workflow, or incident reference.
 *
 */
package dz.sh.hidra.modules.alarm.domain.model;

import dz.sh.hidra.modules.alarm.domain.value.*;
import java.time.Instant;

    /**
     * Escalation of alarm to responsibility level, workflow, or incident reference.
     *
         * @param id id
     * @param alarmId alarmId
     * @param escalationLevel escalationLevel
     * @param escalationType escalationType
     * @param targetOrganizationUnitId targetOrganizationUnitId
     * @param targetActorId targetActorId
     * @param workflowInstanceId workflowInstanceId
     * @param incidentId incidentId
     * @param reasonId reasonId
     * @param reasonText reasonText
     * @param escalatedByActorId escalatedByActorId
     * @param escalatedAt escalatedAt
     * @param status status
     * @param correlationId correlationId
     */
    public record AlarmEscalation(
            String id,
        String alarmId,
        Integer escalationLevel,
        AlarmEscalationType escalationType,
        String targetOrganizationUnitId,
        String targetActorId,
        String workflowInstanceId,
        String incidentId,
        String reasonId,
        String reasonText,
        String escalatedByActorId,
        Instant escalatedAt,
        AlarmEscalationStatus status,
        String correlationId
    ) {

        public AlarmEscalation {
        id = normalize(id);
        alarmId = normalize(alarmId);
        targetOrganizationUnitId = normalize(targetOrganizationUnitId);
        targetActorId = normalize(targetActorId);
        workflowInstanceId = normalize(workflowInstanceId);
        incidentId = normalize(incidentId);
        reasonId = normalize(reasonId);
        reasonText = normalize(reasonText);
        escalatedByActorId = normalize(escalatedByActorId);
        correlationId = normalize(correlationId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
