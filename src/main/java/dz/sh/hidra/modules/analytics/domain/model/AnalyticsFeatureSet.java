/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsFeatureSet
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.analytics.domain.exception.InvalidAnalyticsValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidAnalyticsValueException("AnalyticsFeatureSet id must not be blank.");
        }
        // HRA-051 required: code
        if (code == null || code.isBlank()) {
            throw new InvalidAnalyticsValueException("AnalyticsFeatureSet code must not be blank.");
        }
        // HRA-051 required: subjectAreaId
        if (subjectAreaId == null || subjectAreaId.isBlank()) {
            throw new InvalidAnalyticsValueException("AnalyticsFeatureSet subject area id must not be blank.");
        }
        // HRA-051 required: sourceDatasetId
        if (sourceDatasetId == null || sourceDatasetId.isBlank()) {
            throw new InvalidAnalyticsValueException("AnalyticsFeatureSet source dataset id must not be blank.");
        }
        // HRA-051 required: featureSchemaVersion
        if (featureSchemaVersion == null || featureSchemaVersion.isBlank()) {
            throw new InvalidAnalyticsValueException("AnalyticsFeatureSet feature schema version must not be blank.");
        }

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
