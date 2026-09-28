/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EnvironmentalEvent
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.domain.model
 *
 * @Description : Environmental event.
 *
 */
package dz.sh.hidra.modules.hse.domain.model;

import dz.sh.hidra.modules.hse.domain.exception.InvalidHseValueException;
import dz.sh.hidra.modules.hse.domain.value.*;
import java.time.Instant;
import java.math.BigDecimal;

    /**
     * Environmental event.
     *
         * @param id id
     * @param eventNumber eventNumber
     * @param eventType eventType
     * @param title title
     * @param description description
     * @param substanceId substanceId
     * @param quantity quantity
     * @param quantityUnitId quantityUnitId
     * @param mediumAffectedId mediumAffectedId
     * @param targetModule targetModule
     * @param targetTypeCode targetTypeCode
     * @param targetId targetId
     * @param severity severity
     * @param status status
     * @param occurredAt occurredAt
     * @param linkedHseCaseId linkedHseCaseId
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record EnvironmentalEvent(
            String id,
        String eventNumber,
        EnvironmentalEventType eventType,
        String title,
        String description,
        String substanceId,
        BigDecimal quantity,
        String quantityUnitId,
        String mediumAffectedId,
        String targetModule,
        String targetTypeCode,
        String targetId,
        HseImpactSeverity severity,
        ReportStatus status,
        Instant occurredAt,
        String linkedHseCaseId,
        Instant createdAt,
        Instant updatedAt
    ) {

        public EnvironmentalEvent {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidHseValueException("EnvironmentalEvent id must not be blank.");
        }
        // HRA-051 required: eventNumber
        if (eventNumber == null || eventNumber.isBlank()) {
            throw new InvalidHseValueException("EnvironmentalEvent event number must not be blank.");
        }
        // HRA-051 required: eventType
        if (eventType == null) {
            throw new InvalidHseValueException("EnvironmentalEvent event type must not be null.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidHseValueException("EnvironmentalEvent status must not be null.");
        }
        // HRA-051 required: occurredAt
        if (occurredAt == null) {
            throw new InvalidHseValueException("EnvironmentalEvent occurred at must not be null.");
        }

        id = normalize(id);
        eventNumber = normalize(eventNumber);
        title = normalize(title);
        description = normalize(description);
        substanceId = normalize(substanceId);
        quantityUnitId = normalize(quantityUnitId);
        mediumAffectedId = normalize(mediumAffectedId);
        targetModule = normalize(targetModule);
        targetTypeCode = normalize(targetTypeCode);
        targetId = normalize(targetId);
        linkedHseCaseId = normalize(linkedHseCaseId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
