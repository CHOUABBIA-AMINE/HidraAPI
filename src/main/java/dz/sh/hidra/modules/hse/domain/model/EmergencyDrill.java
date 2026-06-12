/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmergencyDrill
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.domain.model
 *
 * @Description : Emergency drill.
 *
 */
package dz.sh.hidra.modules.hse.domain.model;

import dz.sh.hidra.modules.hse.domain.value.*;
import java.time.Instant;

    /**
     * Emergency drill.
     *
         * @param id id
     * @param drillNumber drillNumber
     * @param drillTypeId drillTypeId
     * @param title title
     * @param targetModule targetModule
     * @param targetTypeCode targetTypeCode
     * @param targetId targetId
     * @param plannedAt plannedAt
     * @param executedAt executedAt
     * @param status status
     * @param participantsCount participantsCount
     * @param evaluationSummary evaluationSummary
     * @param linkedHseCaseId linkedHseCaseId
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record EmergencyDrill(
            String id,
        String drillNumber,
        String drillTypeId,
        String title,
        String targetModule,
        String targetTypeCode,
        String targetId,
        Instant plannedAt,
        Instant executedAt,
        DrillStatus status,
        Integer participantsCount,
        String evaluationSummary,
        String linkedHseCaseId,
        Instant createdAt,
        Instant updatedAt
    ) {

        public EmergencyDrill {
        id = normalize(id);
        drillNumber = normalize(drillNumber);
        drillTypeId = normalize(drillTypeId);
        title = normalize(title);
        targetModule = normalize(targetModule);
        targetTypeCode = normalize(targetTypeCode);
        targetId = normalize(targetId);
        evaluationSummary = normalize(evaluationSummary);
        linkedHseCaseId = normalize(linkedHseCaseId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
