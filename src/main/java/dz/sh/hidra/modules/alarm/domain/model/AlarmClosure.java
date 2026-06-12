/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AlarmClosure
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.domain.model
 *
 * @Description : Administrative closure after clear, cancellation, or escalation.
 *
 */
package dz.sh.hidra.modules.alarm.domain.model;

import dz.sh.hidra.modules.alarm.domain.value.*;
import java.time.Instant;

    /**
     * Administrative closure after clear, cancellation, or escalation.
     *
         * @param id id
     * @param alarmId alarmId
     * @param closureType closureType
     * @param closureReasonId closureReasonId
     * @param closureComment closureComment
     * @param closedByActorId closedByActorId
     * @param closedAt closedAt
     * @param requiresReview requiresReview
     * @param reviewWorkflowInstanceId reviewWorkflowInstanceId
     * @param correlationId correlationId
     */
    public record AlarmClosure(
            String id,
        String alarmId,
        AlarmClosureType closureType,
        String closureReasonId,
        String closureComment,
        String closedByActorId,
        Instant closedAt,
        boolean requiresReview,
        String reviewWorkflowInstanceId,
        String correlationId
    ) {

        public AlarmClosure {
        id = normalize(id);
        alarmId = normalize(alarmId);
        closureReasonId = normalize(closureReasonId);
        closureComment = normalize(closureComment);
        closedByActorId = normalize(closedByActorId);
        reviewWorkflowInstanceId = normalize(reviewWorkflowInstanceId);
        correlationId = normalize(correlationId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
