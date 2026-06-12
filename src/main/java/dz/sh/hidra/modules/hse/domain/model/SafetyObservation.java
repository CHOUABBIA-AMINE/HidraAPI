/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SafetyObservation
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.domain.model
 *
 * @Description : Safety observation.
 *
 */
package dz.sh.hidra.modules.hse.domain.model;

import dz.sh.hidra.modules.hse.domain.value.*;
import java.time.Instant;

    /**
     * Safety observation.
     *
         * @param id id
     * @param observationNumber observationNumber
     * @param observationType observationType
     * @param title title
     * @param description description
     * @param targetModule targetModule
     * @param targetTypeCode targetTypeCode
     * @param targetId targetId
     * @param observedByActorId observedByActorId
     * @param observedAt observedAt
     * @param status status
     * @param linkedHseCaseId linkedHseCaseId
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record SafetyObservation(
            String id,
        String observationNumber,
        ObservationType observationType,
        String title,
        String description,
        String targetModule,
        String targetTypeCode,
        String targetId,
        String observedByActorId,
        Instant observedAt,
        ReportStatus status,
        String linkedHseCaseId,
        Instant createdAt,
        Instant updatedAt
    ) {

        public SafetyObservation {
        id = normalize(id);
        observationNumber = normalize(observationNumber);
        title = normalize(title);
        description = normalize(description);
        targetModule = normalize(targetModule);
        targetTypeCode = normalize(targetTypeCode);
        targetId = normalize(targetId);
        observedByActorId = normalize(observedByActorId);
        linkedHseCaseId = normalize(linkedHseCaseId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
