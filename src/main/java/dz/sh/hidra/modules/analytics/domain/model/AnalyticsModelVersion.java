/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsModelVersion
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.domain.model
 *
 * @Description : Version of an analytical model.
 *
 */
package dz.sh.hidra.modules.analytics.domain.model;

import dz.sh.hidra.modules.analytics.domain.exception.InvalidAnalyticsValueException;
import dz.sh.hidra.modules.analytics.domain.value.*;
import java.time.Instant;

    /**
     * Version of an analytical model.
     *
         * @param id id
     * @param analyticsModelId analyticsModelId
     * @param versionNumber versionNumber
     * @param modelArtifactReference modelArtifactReference
     * @param trainingDatasetVersionId trainingDatasetVersionId
     * @param validationDatasetVersionId validationDatasetVersionId
     * @param modelParametersJson modelParametersJson
     * @param performanceSummaryJson performanceSummaryJson
     * @param status status
     * @param createdByActorId createdByActorId
     * @param createdAt createdAt
     */
    public record AnalyticsModelVersion(
            String id,
        String analyticsModelId,
        int versionNumber,
        String modelArtifactReference,
        String trainingDatasetVersionId,
        String validationDatasetVersionId,
        String modelParametersJson,
        String performanceSummaryJson,
        AnalyticsModelStatus status,
        String createdByActorId,
        Instant createdAt
    ) {

        public AnalyticsModelVersion {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidAnalyticsValueException("AnalyticsModelVersion id must not be blank.");
        }
        // HRA-051 required: analyticsModelId
        if (analyticsModelId == null || analyticsModelId.isBlank()) {
            throw new InvalidAnalyticsValueException("AnalyticsModelVersion analytics model id must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidAnalyticsValueException("AnalyticsModelVersion status must not be null.");
        }

        id = normalize(id);
        analyticsModelId = normalize(analyticsModelId);
        modelArtifactReference = normalize(modelArtifactReference);
        trainingDatasetVersionId = normalize(trainingDatasetVersionId);
        validationDatasetVersionId = normalize(validationDatasetVersionId);
        modelParametersJson = normalize(modelParametersJson);
        performanceSummaryJson = normalize(performanceSummaryJson);
        createdByActorId = normalize(createdByActorId);
        }

        private static String normalize(String value) {
            if (value == null || value.isBlank()) {
                return null;
            }
            return value.trim();
        }
    }
