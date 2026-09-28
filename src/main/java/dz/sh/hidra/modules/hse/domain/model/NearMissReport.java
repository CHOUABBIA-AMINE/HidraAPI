/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NearMissReport
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.hse.domain.exception.InvalidHseValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidHseValueException("NearMissReport id must not be blank.");
        }
        // HRA-051 required: reportNumber
        if (reportNumber == null || reportNumber.isBlank()) {
            throw new InvalidHseValueException("NearMissReport report number must not be blank.");
        }
        // HRA-051 required: nearMissTypeId
        if (nearMissTypeId == null || nearMissTypeId.isBlank()) {
            throw new InvalidHseValueException("NearMissReport near miss type id must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidHseValueException("NearMissReport status must not be null.");
        }
        // HRA-051 required: reportedAt
        if (reportedAt == null) {
            throw new InvalidHseValueException("NearMissReport reported at must not be null.");
        }

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
