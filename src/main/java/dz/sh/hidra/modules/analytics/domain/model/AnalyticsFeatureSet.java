/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsFeatureSet
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.domain.model
 *
 * @Description : Features prepared for models or advanced analytics.
 *
 */
package dz.sh.hidra.modules.analytics.domain.model;

import java.time.Instant;

    /**
     * Features prepared for models or advanced analytics.
     *
         * @param id id
     * @param code code
     * @param nameAr nameAr
     * @param nameFr nameFr
     * @param nameEn nameEn
     * @param subjectAreaId subjectAreaId
     * @param sourceDatasetId sourceDatasetId
     * @param featureSchemaVersion featureSchemaVersion
     * @param active active
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record AnalyticsFeatureSet(
            String id,
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        String subjectAreaId,
        String sourceDatasetId,
        String featureSchemaVersion,
        boolean active,
        Instant createdAt,
        Instant updatedAt
    ) {

        public AnalyticsFeatureSet {
        id = normalize(id);
        code = normalize(code);
        nameAr = normalize(nameAr);
        nameFr = normalize(nameFr);
        nameEn = normalize(nameEn);
        subjectAreaId = normalize(subjectAreaId);
        sourceDatasetId = normalize(sourceDatasetId);
        featureSchemaVersion = normalize(featureSchemaVersion);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
