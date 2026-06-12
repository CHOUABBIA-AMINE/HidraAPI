/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsProjectionDefinition
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.domain.model
 *
 * @Description : Materialized analytical projection definition.
 *
 */
package dz.sh.hidra.modules.analytics.domain.model;

import java.time.Instant;

    /**
     * Materialized analytical projection definition.
     *
         * @param id id
     * @param code code
     * @param nameAr nameAr
     * @param nameFr nameFr
     * @param nameEn nameEn
     * @param subjectAreaId subjectAreaId
     * @param projectionType projectionType
     * @param calculationPolicy calculationPolicy
     * @param refreshPolicy refreshPolicy
     * @param retentionPolicy retentionPolicy
     * @param active active
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record AnalyticsProjectionDefinition(
            String id,
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        String subjectAreaId,
        String projectionType,
        String calculationPolicy,
        String refreshPolicy,
        String retentionPolicy,
        boolean active,
        Instant createdAt,
        Instant updatedAt
    ) {

        public AnalyticsProjectionDefinition {
        id = normalize(id);
        code = normalize(code);
        nameAr = normalize(nameAr);
        nameFr = normalize(nameFr);
        nameEn = normalize(nameEn);
        subjectAreaId = normalize(subjectAreaId);
        projectionType = normalize(projectionType);
        calculationPolicy = normalize(calculationPolicy);
        refreshPolicy = normalize(refreshPolicy);
        retentionPolicy = normalize(retentionPolicy);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
