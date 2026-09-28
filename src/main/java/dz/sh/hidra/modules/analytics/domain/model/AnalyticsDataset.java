/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsDataset
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.domain.model
 *
 * @Description : Curated analytical dataset metadata.
 *
 */
package dz.sh.hidra.modules.analytics.domain.model;

import dz.sh.hidra.modules.analytics.domain.exception.InvalidAnalyticsValueException;
import dz.sh.hidra.modules.analytics.domain.value.*;
import java.time.Instant;

    /**
     * Curated analytical dataset metadata.
     *
         * @param id id
     * @param code code
     * @param nameAr nameAr
     * @param nameFr nameFr
     * @param nameEn nameEn
     * @param subjectAreaId subjectAreaId
     * @param datasetType datasetType
     * @param refreshMode refreshMode
     * @param lineageStatus lineageStatus
     * @param qualityStatus qualityStatus
     * @param schemaVersion schemaVersion
     * @param createdFrom createdFrom
     * @param validFrom validFrom
     * @param validTo validTo
     * @param createdAt createdAt
     * @param updatedAt updatedAt
     */
    public record AnalyticsDataset(
            String id,
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        String subjectAreaId,
        AnalyticsDatasetType datasetType,
        AnalyticsRefreshMode refreshMode,
        AnalyticsLineageStatus lineageStatus,
        AnalyticsQualityStatus qualityStatus,
        String schemaVersion,
        String createdFrom,
        Instant validFrom,
        Instant validTo,
        Instant createdAt,
        Instant updatedAt
    ) {

        public AnalyticsDataset {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidAnalyticsValueException("AnalyticsDataset id must not be blank.");
        }
        // HRA-051 required: code
        if (code == null || code.isBlank()) {
            throw new InvalidAnalyticsValueException("AnalyticsDataset code must not be blank.");
        }
        // HRA-051 required: subjectAreaId
        if (subjectAreaId == null || subjectAreaId.isBlank()) {
            throw new InvalidAnalyticsValueException("AnalyticsDataset subject area id must not be blank.");
        }
        // HRA-051 required: datasetType
        if (datasetType == null) {
            throw new InvalidAnalyticsValueException("AnalyticsDataset dataset type must not be null.");
        }
        // HRA-051 required: refreshMode
        if (refreshMode == null) {
            throw new InvalidAnalyticsValueException("AnalyticsDataset refresh mode must not be null.");
        }
        // HRA-051 required: lineageStatus
        if (lineageStatus == null) {
            throw new InvalidAnalyticsValueException("AnalyticsDataset lineage status must not be null.");
        }
        // HRA-051 required: qualityStatus
        if (qualityStatus == null) {
            throw new InvalidAnalyticsValueException("AnalyticsDataset quality status must not be null.");
        }
        // HRA-051 order: validFrom <= validTo
        if (validFrom != null && validTo != null && validTo.isBefore(validFrom)) {
            throw new InvalidAnalyticsValueException("AnalyticsDataset valid to must not be before valid from.");
        }

        id = normalize(id);
        code = normalize(code);
        nameAr = normalize(nameAr);
        nameFr = normalize(nameFr);
        nameEn = normalize(nameEn);
        subjectAreaId = normalize(subjectAreaId);
        schemaVersion = normalize(schemaVersion);
        createdFrom = normalize(createdFrom);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
