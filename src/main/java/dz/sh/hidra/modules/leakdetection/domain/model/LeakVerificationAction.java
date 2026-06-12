/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakVerificationAction
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
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
