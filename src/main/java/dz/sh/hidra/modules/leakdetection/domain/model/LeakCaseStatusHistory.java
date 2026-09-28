/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakCaseStatusHistory
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.domain.model
 *
 * @Description : Append-only case status history.
 *
 */
package dz.sh.hidra.modules.leakdetection.domain.model;

import dz.sh.hidra.modules.leakdetection.domain.exception.InvalidLeakDetectionValueException;
import dz.sh.hidra.modules.leakdetection.domain.value.*;
import java.time.Instant;

    /**
     * Append-only case status history.
     *
         * @param id id
     * @param caseId caseId
     * @param oldStatus oldStatus
     * @param newStatus newStatus
     * @param reasonId reasonId
     * @param reasonText reasonText
     * @param changedByActorId changedByActorId
     * @param changedAt changedAt
     * @param correlationId correlationId
     */
    public record LeakCaseStatusHistory(
            String id,
        String caseId,
        LeakDetectionCaseStatus oldStatus,
        LeakDetectionCaseStatus newStatus,
        String reasonId,
        String reasonText,
        String changedByActorId,
        Instant changedAt,
        String correlationId
    ) {

        public LeakCaseStatusHistory {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidLeakDetectionValueException("LeakCaseStatusHistory id must not be blank.");
        }
        // HRA-051 required: caseId
        if (caseId == null || caseId.isBlank()) {
            throw new InvalidLeakDetectionValueException("LeakCaseStatusHistory case id must not be blank.");
        }
        // HRA-051 required: newStatus
        if (newStatus == null) {
            throw new InvalidLeakDetectionValueException("LeakCaseStatusHistory new status must not be null.");
        }
        // HRA-051 required: changedAt
        if (changedAt == null) {
            throw new InvalidLeakDetectionValueException("LeakCaseStatusHistory changed at must not be null.");
        }

        id = normalize(id);
        caseId = normalize(caseId);
        reasonId = normalize(reasonId);
        reasonText = normalize(reasonText);
        changedByActorId = normalize(changedByActorId);
        correlationId = normalize(correlationId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
