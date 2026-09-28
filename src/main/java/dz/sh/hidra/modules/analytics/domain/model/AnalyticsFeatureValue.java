/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsFeatureValue
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.analytics.domain.exception.InvalidAnalyticsValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidAnalyticsValueException("AnalyticsFeatureValue id must not be blank.");
        }
        // HRA-051 required: featureSetId
        if (featureSetId == null || featureSetId.isBlank()) {
            throw new InvalidAnalyticsValueException("AnalyticsFeatureValue feature set id must not be blank.");
        }
        // HRA-051 required: datasetVersionId
        if (datasetVersionId == null || datasetVersionId.isBlank()) {
            throw new InvalidAnalyticsValueException("AnalyticsFeatureValue dataset version id must not be blank.");
        }
        // HRA-051 required: calculatedAt
        if (calculatedAt == null) {
            throw new InvalidAnalyticsValueException("AnalyticsFeatureValue calculated at must not be null.");
        }
        // HRA-051 order: periodStart <= periodEnd
        if (periodStart != null && periodEnd != null && periodEnd.isBefore(periodStart)) {
            throw new InvalidAnalyticsValueException("AnalyticsFeatureValue period end must not be before period start.");
        }

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
