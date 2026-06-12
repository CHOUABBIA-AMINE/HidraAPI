/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsFeatureValue
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.domain.model
 *
 * @Description : Materialized calculated feature value.
 *
 */
package dz.sh.hidra.modules.analytics.domain.model;

import java.math.BigDecimal;
import java.time.Instant;

    /**
     * Materialized calculated feature value.
     *
         * @param id id
     * @param featureSetId featureSetId
     * @param datasetVersionId datasetVersionId
     * @param scopeType scopeType
     * @param scopeId scopeId
     * @param featureName featureName
     * @param featureValueNumeric featureValueNumeric
     * @param featureValueText featureValueText
     * @param featureValueBoolean featureValueBoolean
     * @param periodStart periodStart
     * @param periodEnd periodEnd
     * @param calculatedAt calculatedAt
     */
    public record AnalyticsFeatureValue(
            String id,
        String featureSetId,
        String datasetVersionId,
        String scopeType,
        String scopeId,
        String featureName,
        BigDecimal featureValueNumeric,
        String featureValueText,
        Boolean featureValueBoolean,
        Instant periodStart,
        Instant periodEnd,
        Instant calculatedAt
    ) {

        public AnalyticsFeatureValue {
        id = normalize(id);
        featureSetId = normalize(featureSetId);
        datasetVersionId = normalize(datasetVersionId);
        scopeType = normalize(scopeType);
        scopeId = normalize(scopeId);
        featureName = normalize(featureName);
        featureValueText = normalize(featureValueText);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
