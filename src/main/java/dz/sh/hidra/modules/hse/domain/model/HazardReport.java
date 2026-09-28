/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HazardReport
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.domain.model
 *
 * @Description : Hazard report.
 *
 */
package dz.sh.hidra.modules.hse.domain.model;

import dz.sh.hidra.modules.hse.domain.exception.InvalidHseValueException;
import dz.sh.hidra.modules.hse.domain.value.*;
import java.time.Instant;

    /**
     * Hazard report.
     *
         * @param id id
     * @param reportNumber reportNumber
     * @param hazardTypeId hazardTypeId
     * @param title title
     * @param description description
     * @param targetModule targetModule
     * @param targetTypeCode targetTypeCode
     * @param targetId targetId
     * @param targetCodeSnapshot targetCodeSnapshot
     * @param initialSeverity initialSeverity
     * @param status status
     * @param reportedByActorId reportedByActorId
     * @param reportedAt reportedAt
     * @param linkedHseCaseId linkedHseCaseId
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record HazardReport(
            String id,
        String reportNumber,
        String hazardTypeId,
        String title,
        String description,
        String targetModule,
        String targetTypeCode,
        String targetId,
        String targetCodeSnapshot,
        HseImpactSeverity initialSeverity,
        ReportStatus status,
        String reportedByActorId,
        Instant reportedAt,
        String linkedHseCaseId,
        Instant createdAt,
        Instant updatedAt
    ) {

        public HazardReport {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidHseValueException("HazardReport id must not be blank.");
        }
        // HRA-051 required: reportNumber
        if (reportNumber == null || reportNumber.isBlank()) {
            throw new InvalidHseValueException("HazardReport report number must not be blank.");
        }
        // HRA-051 required: hazardTypeId
        if (hazardTypeId == null || hazardTypeId.isBlank()) {
            throw new InvalidHseValueException("HazardReport hazard type id must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidHseValueException("HazardReport status must not be null.");
        }
        // HRA-051 required: reportedAt
        if (reportedAt == null) {
            throw new InvalidHseValueException("HazardReport reported at must not be null.");
        }

        id = normalize(id);
        reportNumber = normalize(reportNumber);
        hazardTypeId = normalize(hazardTypeId);
        title = normalize(title);
        description = normalize(description);
        targetModule = normalize(targetModule);
        targetTypeCode = normalize(targetTypeCode);
        targetId = normalize(targetId);
        targetCodeSnapshot = normalize(targetCodeSnapshot);
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
