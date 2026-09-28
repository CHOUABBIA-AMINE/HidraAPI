/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakVerificationAction
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.domain.model
 *
 * @Description : Operator or technical verification action.
 *
 */
package dz.sh.hidra.modules.leakdetection.domain.model;

import dz.sh.hidra.modules.leakdetection.domain.exception.InvalidLeakDetectionValueException;
import dz.sh.hidra.modules.leakdetection.domain.value.*;
import java.time.Instant;

    /**
     * Operator or technical verification action.
     *
         * @param id id
     * @param candidateId candidateId
     * @param caseId caseId
     * @param actionType actionType
     * @param assignedOrganizationUnitId assignedOrganizationUnitId
     * @param assignedActorId assignedActorId
     * @param status status
     * @param requestedAt requestedAt
     * @param startedAt startedAt
     * @param completedAt completedAt
     * @param resultText resultText
     * @param correlationId correlationId
     */
    public record LeakVerificationAction(
            String id,
        String candidateId,
        String caseId,
        LeakVerificationActionType actionType,
        String assignedOrganizationUnitId,
        String assignedActorId,
        LeakVerificationStatus status,
        Instant requestedAt,
        Instant startedAt,
        Instant completedAt,
        String resultText,
        String correlationId
    ) {

        public LeakVerificationAction {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidLeakDetectionValueException("LeakVerificationAction id must not be blank.");
        }
        // HRA-051 required: candidateId
        if (candidateId == null || candidateId.isBlank()) {
            throw new InvalidLeakDetectionValueException("LeakVerificationAction candidate id must not be blank.");
        }
        // HRA-051 required: actionType
        if (actionType == null) {
            throw new InvalidLeakDetectionValueException("LeakVerificationAction action type must not be null.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidLeakDetectionValueException("LeakVerificationAction status must not be null.");
        }
        // HRA-051 required: requestedAt
        if (requestedAt == null) {
            throw new InvalidLeakDetectionValueException("LeakVerificationAction requested at must not be null.");
        }

        id = normalize(id);
        candidateId = normalize(candidateId);
        caseId = normalize(caseId);
        assignedOrganizationUnitId = normalize(assignedOrganizationUnitId);
        assignedActorId = normalize(assignedActorId);
        resultText = normalize(resultText);
        correlationId = normalize(correlationId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
