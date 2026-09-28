/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportSectionDefinition
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.domain.model
 *
 * @Description : Section definition inside a report.
 *
 */
package dz.sh.hidra.modules.reporting.domain.model;

import dz.sh.hidra.modules.reporting.domain.exception.InvalidReportingValueException;
import dz.sh.hidra.modules.reporting.domain.value.*;
import java.time.Instant;

    /**
     * Section definition inside a report.
     *
         * @param id id
     * @param reportDefinitionId reportDefinitionId
     * @param code code
     * @param titleAr titleAr
     * @param titleFr titleFr
     * @param titleEn titleEn
     * @param sectionType sectionType
     * @param sortOrder sortOrder
     * @param visibleByDefault visibleByDefault
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record ReportSectionDefinition(
            String id,
        String reportDefinitionId,
        String code,
        String titleAr,
        String titleFr,
        String titleEn,
        ReportSectionType sectionType,
        int sortOrder,
        boolean visibleByDefault,
        Instant createdAt,
        Instant updatedAt
    ) {

        public ReportSectionDefinition {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidReportingValueException("ReportSectionDefinition id must not be blank.");
        }
        // HRA-051 required: reportDefinitionId
        if (reportDefinitionId == null || reportDefinitionId.isBlank()) {
            throw new InvalidReportingValueException("ReportSectionDefinition report definition id must not be blank.");
        }
        // HRA-051 required: code
        if (code == null || code.isBlank()) {
            throw new InvalidReportingValueException("ReportSectionDefinition code must not be blank.");
        }
        // HRA-051 required: sectionType
        if (sectionType == null) {
            throw new InvalidReportingValueException("ReportSectionDefinition section type must not be null.");
        }

        id = normalize(id);
        reportDefinitionId = normalize(reportDefinitionId);
        code = normalize(code);
        titleAr = normalize(titleAr);
        titleFr = normalize(titleFr);
        titleEn = normalize(titleEn);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
