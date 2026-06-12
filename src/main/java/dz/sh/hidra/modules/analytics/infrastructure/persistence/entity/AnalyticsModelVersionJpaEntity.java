/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsModelVersionJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for AnalyticsModelVersion.
 *
 */
package dz.sh.hidra.modules.analytics.infrastructure.persistence.entity;

import dz.sh.hidra.modules.analytics.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for AnalyticsModelVersion.
     */
    @Entity
    @Table(name = "hidra_analytics_model_version")
    public class AnalyticsModelVersionJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "analytics_model_id", nullable = false, length = 80)
    private String analyticsModelId;

    @Column(name = "version_number", nullable = false)
    private int versionNumber;

    @Column(name = "model_artifact_reference", nullable = false, length = 500)
    private String modelArtifactReference;

    @Column(name = "training_dataset_version_id", nullable = true, length = 80)
    private String trainingDatasetVersionId;

    @Column(name = "validation_dataset_version_id", nullable = true, length = 80)
    private String validationDatasetVersionId;

    @Column(name = "model_parameters_json", nullable = true, columnDefinition = "jsonb")
    private String modelParametersJson;

    @Column(name = "performance_summary_json", nullable = true, columnDefinition = "jsonb")
    private String performanceSummaryJson;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private AnalyticsModelStatus status;

    @Column(name = "created_by_actor_id", nullable = true, length = 80)
    private String createdByActorId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected AnalyticsModelVersionJpaEntity() {
            // Required by JPA.
        }

        public AnalyticsModelVersionJpaEntity(
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
            this.id = id;
        this.analyticsModelId = analyticsModelId;
        this.versionNumber = versionNumber;
        this.modelArtifactReference = modelArtifactReference;
        this.trainingDatasetVersionId = trainingDatasetVersionId;
        this.validationDatasetVersionId = validationDatasetVersionId;
        this.modelParametersJson = modelParametersJson;
        this.performanceSummaryJson = performanceSummaryJson;
        this.status = status;
        this.createdByActorId = createdByActorId;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String analyticsModelId() {
        return analyticsModelId;
    }


    public int versionNumber() {
        return versionNumber;
    }


    public String modelArtifactReference() {
        return modelArtifactReference;
    }


    public String trainingDatasetVersionId() {
        return trainingDatasetVersionId;
    }


    public String validationDatasetVersionId() {
        return validationDatasetVersionId;
    }


    public String modelParametersJson() {
        return modelParametersJson;
    }


    public String performanceSummaryJson() {
        return performanceSummaryJson;
    }


    public AnalyticsModelStatus status() {
        return status;
    }


    public String createdByActorId() {
        return createdByActorId;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
