/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsFeatureValueJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for AnalyticsFeatureValue.
 *
 */
package dz.sh.hidra.modules.analytics.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for AnalyticsFeatureValue.
     */
    @Entity
    @Table(name = "hidra_analytics_feature_value")
    public class AnalyticsFeatureValueJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "feature_set_id", nullable = false, length = 80)
    private String featureSetId;

    @Column(name = "dataset_version_id", nullable = false, length = 80)
    private String datasetVersionId;

    @Column(name = "scope_type", nullable = false, length = 80)
    private String scopeType;

    @Column(name = "scope_id", nullable = true, length = 120)
    private String scopeId;

    @Column(name = "feature_name", nullable = false, length = 160)
    private String featureName;

    @Column(name = "feature_value_numeric", nullable = true, precision = 18, scale = 6)
    private BigDecimal featureValueNumeric;

    @Column(name = "feature_value_text", nullable = true, length = 2000)
    private String featureValueText;

    @Column(name = "feature_value_boolean", nullable = true)
    private Boolean featureValueBoolean;

    @Column(name = "period_start", nullable = true)
    private Instant periodStart;

    @Column(name = "period_end", nullable = true)
    private Instant periodEnd;

    @Column(name = "calculated_at", nullable = false)
    private Instant calculatedAt;

        protected AnalyticsFeatureValueJpaEntity() {
            // Required by JPA.
        }

        public AnalyticsFeatureValueJpaEntity(
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
            this.id = id;
        this.featureSetId = featureSetId;
        this.datasetVersionId = datasetVersionId;
        this.scopeType = scopeType;
        this.scopeId = scopeId;
        this.featureName = featureName;
        this.featureValueNumeric = featureValueNumeric;
        this.featureValueText = featureValueText;
        this.featureValueBoolean = featureValueBoolean;
        this.periodStart = periodStart;
        this.periodEnd = periodEnd;
        this.calculatedAt = calculatedAt;
        }


    public String id() {
        return id;
    }


    public String featureSetId() {
        return featureSetId;
    }


    public String datasetVersionId() {
        return datasetVersionId;
    }


    public String scopeType() {
        return scopeType;
    }


    public String scopeId() {
        return scopeId;
    }


    public String featureName() {
        return featureName;
    }


    public BigDecimal featureValueNumeric() {
        return featureValueNumeric;
    }


    public String featureValueText() {
        return featureValueText;
    }


    public Boolean featureValueBoolean() {
        return featureValueBoolean;
    }


    public Instant periodStart() {
        return periodStart;
    }


    public Instant periodEnd() {
        return periodEnd;
    }


    public Instant calculatedAt() {
        return calculatedAt;
    }

    }
