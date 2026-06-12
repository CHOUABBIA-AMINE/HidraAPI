/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakEscalationReference
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.domain.model
 *
 * @Description : Neutral reference to alarm, incident, workflow, notification, or audit.
 *
 */
package dz.sh.hidra.modules.leakdetection.domain.model;

import dz.sh.hidra.modules.leakdetection.domain.value.*;
import java.time.Instant;

    /**
     * Neutral reference to alarm, incident, workflow, notification, or audit.
     *
         * @param id id
     * @param caseId caseId
     * @param candidateId candidateId
     * @param targetType targetType
     * @param targetReferenceId targetReferenceId
     * @param targetCodeSnapshot targetCodeSnapshot
     * @param targetNameSnapshot targetNameSnapshot
     * @param status status
     * @param escalatedByActorId escalatedByActorId
     * @param escalatedAt escalatedAt
     * @param reasonText reasonText
     * @param correlationId correlationId
     */
    public record LeakEscalationReference(
            String id,
        String caseId,
        String candidateId,
        LeakEscalationTargetType targetType,
        String targetReferenceId,
        String targetCodeSnapshot,
        String targetNameSnapshot,
        LeakEscalationStatus status,
        String escalatedByActorId,
        Instant escalatedAt,
        String reasonText,
        String correlationId
    ) {

        public LeakEscalationReference {
        id = normalize(id);
        caseId = normalize(caseId);
        candidateId = normalize(candidateId);
        targetReferenceId = normalize(targetReferenceId);
        targetCodeSnapshot = normalize(targetCodeSnapshot);
        targetNameSnapshot = normalize(targetNameSnapshot);
        escalatedByActorId = normalize(escalatedByActorId);
        reasonText = normalize(reasonText);
        correlationId = normalize(correlationId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
