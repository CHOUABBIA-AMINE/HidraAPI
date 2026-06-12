/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportTableResult
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
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
