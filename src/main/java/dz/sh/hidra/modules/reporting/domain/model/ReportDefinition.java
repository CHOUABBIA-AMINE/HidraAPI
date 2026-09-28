/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportDefinition
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.domain.model
 *
 * @Description : Reusable formal report type.
 *
 */
package dz.sh.hidra.modules.reporting.domain.model;

import dz.sh.hidra.modules.reporting.domain.exception.InvalidReportingValueException;
import java.time.Instant;

    /**
     * Reusable formal report type.
     *
         * @param id id
     * @param code code
     * @param nameAr nameAr
     * @param nameFr nameFr
     * @param nameEn nameEn
     * @param reportCategoryId reportCategoryId
     * @param ownerModule ownerModule
     * @param description description
     * @param active active
     * @param currentTemplateVersionId currentTemplateVersionId
     * @param requiresApproval requiresApproval
     * @param restricted restricted
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record ReportDefinition(
            String id,
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        String reportCategoryId,
        String ownerModule,
        String description,
        boolean active,
        String currentTemplateVersionId,
        boolean requiresApproval,
        boolean restricted,
        Instant createdAt,
        Instant updatedAt
    ) {

        public ReportDefinition {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidReportingValueException("ReportDefinition id must not be blank.");
        }
        // HRA-051 required: code
        if (code == null || code.isBlank()) {
            throw new InvalidReportingValueException("ReportDefinition code must not be blank.");
        }
        // HRA-051 required: reportCategoryId
        if (reportCategoryId == null || reportCategoryId.isBlank()) {
            throw new InvalidReportingValueException("ReportDefinition report category id must not be blank.");
        }

        id = normalize(id);
        code = normalize(code);
        nameAr = normalize(nameAr);
        nameFr = normalize(nameFr);
        nameEn = normalize(nameEn);
        reportCategoryId = normalize(reportCategoryId);
        ownerModule = normalize(ownerModule);
        description = normalize(description);
        currentTemplateVersionId = normalize(currentTemplateVersionId);
        }
        public boolean usableForNewRequests() {
            return active;
        }
        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
