/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsInsightEvidence
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.domain.model
 *
 * @Description : Supporting evidence for an analytics insight.
 *
 */
package dz.sh.hidra.modules.analytics.domain.model;

import java.math.BigDecimal;
import java.time.Instant;

    /**
     * Supporting evidence for an analytics insight.
     *
         * @param id id
     * @param analyticsInsightId analyticsInsightId
     * @param evidenceType evidenceType
     * @param sourceModule sourceModule
     * @param sourceObjectType sourceObjectType
     * @param sourceObjectId sourceObjectId
     * @param sourceLabelSnapshot sourceLabelSnapshot
     * @param weight weight
     * @param createdAt createdAt
     */
    public record AnalyticsInsightEvidence(
            String id,
        String analyticsInsightId,
        String evidenceType,
        String sourceModule,
        String sourceObjectType,
        String sourceObjectId,
        String sourceLabelSnapshot,
        BigDecimal weight,
        Instant createdAt
    ) {

        public AnalyticsInsightEvidence {
        id = normalize(id);
        analyticsInsightId = normalize(analyticsInsightId);
        evidenceType = normalize(evidenceType);
        sourceModule = normalize(sourceModule);
        sourceObjectType = normalize(sourceObjectType);
        sourceObjectId = normalize(sourceObjectId);
        sourceLabelSnapshot = normalize(sourceLabelSnapshot);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
