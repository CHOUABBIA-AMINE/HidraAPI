/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportTableResult
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.domain.model
 *
 * @Description : Generated table output metadata.
 *
 */
package dz.sh.hidra.modules.reporting.domain.model;

import dz.sh.hidra.modules.reporting.domain.exception.InvalidReportingValueException;
import java.time.Instant;

    /**
     * Generated table output metadata.
     *
         * @param id id
     * @param reportSectionResultId reportSectionResultId
     * @param columnSchemaJson columnSchemaJson
     * @param rowCount rowCount
     * @param contentReference contentReference
     * @param checksum checksum
     * @param createdAt createdAt
     */
    public record ReportTableResult(
            String id,
        String reportSectionResultId,
        String columnSchemaJson,
        Long rowCount,
        String contentReference,
        String checksum,
        Instant createdAt
    ) {

        public ReportTableResult {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidReportingValueException("ReportTableResult id must not be blank.");
        }
        // HRA-051 required: reportSectionResultId
        if (reportSectionResultId == null || reportSectionResultId.isBlank()) {
            throw new InvalidReportingValueException("ReportTableResult report section result id must not be blank.");
        }
        // HRA-051 required: rowCount
        if (rowCount == null) {
            throw new InvalidReportingValueException("ReportTableResult row count must not be null.");
        }

        id = normalize(id);
        reportSectionResultId = normalize(reportSectionResultId);
        columnSchemaJson = normalize(columnSchemaJson);
        contentReference = normalize(contentReference);
        checksum = normalize(checksum);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
