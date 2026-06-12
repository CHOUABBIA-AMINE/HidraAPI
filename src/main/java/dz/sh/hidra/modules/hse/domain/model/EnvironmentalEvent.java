/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EnvironmentalEvent
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
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
