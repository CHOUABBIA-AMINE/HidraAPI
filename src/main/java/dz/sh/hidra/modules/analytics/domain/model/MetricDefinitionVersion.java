/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MetricDefinitionVersion
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.domain.model
 *
 * @Description : Versioned metric formula and computation policy.
 *
 */
package dz.sh.hidra.modules.analytics.domain.model;

import java.time.Instant;

    /**
     * Versioned metric formula and computation policy.
     *
         * @param id id
     * @param metricDefinitionId metricDefinitionId
     * @param versionNumber versionNumber
     * @param formulaExpression formulaExpression
     * @param calculationDescription calculationDescription
     * @param validFrom validFrom
     * @param validTo validTo
     * @param createdByActorId createdByActorId
     * @param createdAt createdAt
     */
    public record MetricDefinitionVersion(
            String id,
        String metricDefinitionId,
        int versionNumber,
        String formulaExpression,
        String calculationDescription,
        Instant validFrom,
        Instant validTo,
        String createdByActorId,
        Instant createdAt
    ) {

        public MetricDefinitionVersion {
        id = normalize(id);
        metricDefinitionId = normalize(metricDefinitionId);
        formulaExpression = normalize(formulaExpression);
        calculationDescription = normalize(calculationDescription);
        createdByActorId = normalize(createdByActorId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
