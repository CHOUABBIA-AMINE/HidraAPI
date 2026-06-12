/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NearMissReport
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.domain.model
 *
 * @Description : Near-miss report.
 *
 */
package dz.sh.hidra.modules.hse.domain.model;

import dz.sh.hidra.modules.hse.domain.value.*;
import java.time.Instant;

    /**
     * Near-miss report.
     *
         * @param id id
     * @param reportNumber reportNumber
     * @param nearMissTypeId nearMissTypeId
     * @param title title
     * @param description description
     * @param potentialConsequenceId potentialConsequenceId
     * @param potentialSeverity potentialSeverity
     * @param targetModule targetModule
     * @param targetTypeCode targetTypeCode
     * @param targetId targetId
     * @param status status
     * @param reportedByActorId reportedByActorId
     * @param reportedAt reportedAt
     * @param linkedHseCaseId linkedHseCaseId
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record NearMissReport(
            String id,
        String reportNumber,
        String nearMissTypeId,
        String title,
        String description,
        String potentialConsequenceId,
        HseImpactSeverity potentialSeverity,
        String targetModule,
        String targetTypeCode,
        String targetId,
        ReportStatus status,
        String reportedByActorId,
        Instant reportedAt,
        String linkedHseCaseId,
        Instant createdAt,
        Instant updatedAt
    ) {

        public NearMissReport {
        id = normalize(id);
        reportNumber = normalize(reportNumber);
        nearMissTypeId = normalize(nearMissTypeId);
        title = normalize(title);
        description = normalize(description);
        potentialConsequenceId = normalize(potentialConsequenceId);
        targetModule = normalize(targetModule);
        targetTypeCode = normalize(targetTypeCode);
        targetId = normalize(targetId);
        reportedByActorId = normalize(reportedByActorId);
        linkedHseCaseId = normalize(linkedHseCaseId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
