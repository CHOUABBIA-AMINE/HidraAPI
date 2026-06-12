/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportSectionResult
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.domain.model
 *
 * @Description : Generated section-level result metadata.
 *
 */
package dz.sh.hidra.modules.reporting.domain.model;

import dz.sh.hidra.modules.reporting.domain.value.*;
import java.time.Instant;

    /**
     * Generated section-level result metadata.
     *
         * @param id id
     * @param reportRunId reportRunId
     * @param sectionDefinitionId sectionDefinitionId
     * @param sectionCode sectionCode
     * @param status status
     * @param resultReference resultReference
     * @param rowCount rowCount
     * @param warningCount warningCount
     * @param createdAt createdAt
     */
    public record ReportSectionResult(
            String id,
        String reportRunId,
        String sectionDefinitionId,
        String sectionCode,
        ReportSectionResultStatus status,
        String resultReference,
        Long rowCount,
        Long warningCount,
        Instant createdAt
    ) {

        public ReportSectionResult {
        id = normalize(id);
        reportRunId = normalize(reportRunId);
        sectionDefinitionId = normalize(sectionDefinitionId);
        sectionCode = normalize(sectionCode);
        resultReference = normalize(resultReference);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
