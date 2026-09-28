/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : KpiDefinition
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.domain.model
 *
 * @Description : Business KPI derived from one or more metrics.
 *
 */
package dz.sh.hidra.modules.analytics.domain.model;

import dz.sh.hidra.modules.analytics.domain.exception.InvalidAnalyticsValueException;
import java.time.Instant;

    /**
     * Business KPI derived from one or more metrics.
     *
         * @param id id
     * @param code code
     * @param nameAr nameAr
     * @param nameFr nameFr
     * @param nameEn nameEn
     * @param subjectAreaId subjectAreaId
     * @param primaryMetricDefinitionId primaryMetricDefinitionId
     * @param kpiCategoryId kpiCategoryId
     * @param displayUnitId displayUnitId
     * @param defaultGranularity defaultGranularity
     * @param active active
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record KpiDefinition(
            String id,
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        String subjectAreaId,
        String primaryMetricDefinitionId,
        String kpiCategoryId,
        String displayUnitId,
        String defaultGranularity,
        boolean active,
        Instant createdAt,
        Instant updatedAt
    ) {

        public KpiDefinition {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidAnalyticsValueException("KpiDefinition id must not be blank.");
        }
        // HRA-051 required: code
        if (code == null || code.isBlank()) {
            throw new InvalidAnalyticsValueException("KpiDefinition code must not be blank.");
        }
        // HRA-051 required: subjectAreaId
        if (subjectAreaId == null || subjectAreaId.isBlank()) {
            throw new InvalidAnalyticsValueException("KpiDefinition subject area id must not be blank.");
        }
        // HRA-051 required: primaryMetricDefinitionId
        if (primaryMetricDefinitionId == null || primaryMetricDefinitionId.isBlank()) {
            throw new InvalidAnalyticsValueException("KpiDefinition primary metric definition id must not be blank.");
        }
        // HRA-051 required: kpiCategoryId
        if (kpiCategoryId == null || kpiCategoryId.isBlank()) {
            throw new InvalidAnalyticsValueException("KpiDefinition kpi category id must not be blank.");
        }

        id = normalize(id);
        code = normalize(code);
        nameAr = normalize(nameAr);
        nameFr = normalize(nameFr);
        nameEn = normalize(nameEn);
        subjectAreaId = normalize(subjectAreaId);
        primaryMetricDefinitionId = normalize(primaryMetricDefinitionId);
        kpiCategoryId = normalize(kpiCategoryId);
        displayUnitId = normalize(displayUnitId);
        defaultGranularity = normalize(defaultGranularity);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
