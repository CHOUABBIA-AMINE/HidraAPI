/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportTemplate
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.domain.model
 *
 * @Description : Logical template family for a report.
 *
 */
package dz.sh.hidra.modules.reporting.domain.model;

import java.time.Instant;

    /**
     * Logical template family for a report.
     *
         * @param id id
     * @param reportDefinitionId reportDefinitionId
     * @param code code
     * @param nameAr nameAr
     * @param nameFr nameFr
     * @param nameEn nameEn
     * @param templateEngine templateEngine
     * @param active active
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record ReportTemplate(
            String id,
        String reportDefinitionId,
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        String templateEngine,
        boolean active,
        Instant createdAt,
        Instant updatedAt
    ) {

        public ReportTemplate {
        id = normalize(id);
        reportDefinitionId = normalize(reportDefinitionId);
        code = normalize(code);
        nameAr = normalize(nameAr);
        nameFr = normalize(nameFr);
        nameEn = normalize(nameEn);
        templateEngine = normalize(templateEngine);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
