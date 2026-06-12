/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MetricDefinition
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.domain.model
 *
 * @Description : Reusable analytical metric definition.
 *
 */
package dz.sh.hidra.modules.analytics.domain.model;

import java.time.Instant;

    /**
     * Reusable analytical metric definition.
     *
         * @param id id
     * @param code code
     * @param nameAr nameAr
     * @param nameFr nameFr
     * @param nameEn nameEn
     * @param subjectAreaId subjectAreaId
     * @param metricType metricType
     * @param formulaExpression formulaExpression
     * @param unitId unitId
     * @param aggregationMethod aggregationMethod
     * @param periodGranularity periodGranularity
     * @param active active
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record MetricDefinition(
            String id,
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        String subjectAreaId,
        String metricType,
        String formulaExpression,
        String unitId,
        String aggregationMethod,
        String periodGranularity,
        boolean active,
        Instant createdAt,
        Instant updatedAt
    ) {

        public MetricDefinition {
        id = normalize(id);
        code = normalize(code);
        nameAr = normalize(nameAr);
        nameFr = normalize(nameFr);
        nameEn = normalize(nameEn);
        subjectAreaId = normalize(subjectAreaId);
        metricType = normalize(metricType);
        formulaExpression = normalize(formulaExpression);
        unitId = normalize(unitId);
        aggregationMethod = normalize(aggregationMethod);
        periodGranularity = normalize(periodGranularity);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
