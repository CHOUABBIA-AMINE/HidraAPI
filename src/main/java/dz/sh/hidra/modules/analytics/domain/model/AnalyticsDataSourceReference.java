/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsDataSourceReference
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.domain.model
 *
 * @Description : Reference to a source feed, read model, event stream, or trusted export.
 *
 */
package dz.sh.hidra.modules.analytics.domain.model;

import dz.sh.hidra.modules.analytics.domain.exception.InvalidAnalyticsValueException;
import dz.sh.hidra.modules.analytics.domain.value.*;
import java.time.Instant;

    /**
     * Reference to a source feed, read model, event stream, or trusted export.
     *
         * @param id id
     * @param sourceModule sourceModule
     * @param sourceType sourceType
     * @param sourceName sourceName
     * @param sourceVersion sourceVersion
     * @param accessMode accessMode
     * @param refreshMode refreshMode
     * @param trusted trusted
     * @param lastAvailableAt lastAvailableAt
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record AnalyticsDataSourceReference(
            String id,
        String sourceModule,
        String sourceType,
        String sourceName,
        String sourceVersion,
        AnalyticsAccessMode accessMode,
        AnalyticsRefreshMode refreshMode,
        boolean trusted,
        Instant lastAvailableAt,
        Instant createdAt,
        Instant updatedAt
    ) {

        public AnalyticsDataSourceReference {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidAnalyticsValueException("AnalyticsDataSourceReference id must not be blank.");
        }
        // HRA-051 required: accessMode
        if (accessMode == null) {
            throw new InvalidAnalyticsValueException("AnalyticsDataSourceReference access mode must not be null.");
        }
        // HRA-051 required: refreshMode
        if (refreshMode == null) {
            throw new InvalidAnalyticsValueException("AnalyticsDataSourceReference refresh mode must not be null.");
        }

        id = normalize(id);
        sourceModule = normalize(sourceModule);
        sourceType = normalize(sourceType);
        sourceName = normalize(sourceName);
        sourceVersion = normalize(sourceVersion);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
