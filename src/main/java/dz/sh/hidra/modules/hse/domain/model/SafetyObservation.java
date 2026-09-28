/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SafetyObservation
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.hse.domain.exception.InvalidHseValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidHseValueException("SafetyObservation id must not be blank.");
        }
        // HRA-051 required: observationNumber
        if (observationNumber == null || observationNumber.isBlank()) {
            throw new InvalidHseValueException("SafetyObservation observation number must not be blank.");
        }
        // HRA-051 required: observationType
        if (observationType == null) {
            throw new InvalidHseValueException("SafetyObservation observation type must not be null.");
        }
        // HRA-051 required: observedAt
        if (observedAt == null) {
            throw new InvalidHseValueException("SafetyObservation observed at must not be null.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidHseValueException("SafetyObservation status must not be null.");
        }

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
