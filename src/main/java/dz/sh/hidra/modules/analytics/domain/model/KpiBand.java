/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : KpiBand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.domain.model
 *
 * @Description : Analytical interpretation band for KPI dashboards.
 *
 */
package dz.sh.hidra.modules.analytics.domain.model;

import dz.sh.hidra.modules.analytics.domain.exception.InvalidAnalyticsValueException;
import java.math.BigDecimal;
import java.time.Instant;

    /**
     * Analytical interpretation band for KPI dashboards.
     *
         * @param id id
     * @param kpiDefinitionId kpiDefinitionId
     * @param bandCode bandCode
     * @param labelAr labelAr
     * @param labelFr labelFr
     * @param labelEn labelEn
     * @param minValue minValue
     * @param maxValue maxValue
     * @param severityId severityId
     * @param sortOrder sortOrder
     * @param active active
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record KpiBand(
            String id,
        String kpiDefinitionId,
        String bandCode,
        String labelAr,
        String labelFr,
        String labelEn,
        BigDecimal minValue,
        BigDecimal maxValue,
        String severityId,
        int sortOrder,
        boolean active,
        Instant createdAt,
        Instant updatedAt
    ) {

        public KpiBand {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidAnalyticsValueException("KpiBand id must not be blank.");
        }
        // HRA-051 required: kpiDefinitionId
        if (kpiDefinitionId == null || kpiDefinitionId.isBlank()) {
            throw new InvalidAnalyticsValueException("KpiBand kpi definition id must not be blank.");
        }
        // HRA-051 required: bandCode
        if (bandCode == null || bandCode.isBlank()) {
            throw new InvalidAnalyticsValueException("KpiBand band code must not be blank.");
        }
        // HRA-051 required: severityId
        if (severityId == null || severityId.isBlank()) {
            throw new InvalidAnalyticsValueException("KpiBand severity id must not be blank.");
        }

        id = normalize(id);
        kpiDefinitionId = normalize(kpiDefinitionId);
        bandCode = normalize(bandCode);
        labelAr = normalize(labelAr);
        labelFr = normalize(labelFr);
        labelEn = normalize(labelEn);
        severityId = normalize(severityId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
