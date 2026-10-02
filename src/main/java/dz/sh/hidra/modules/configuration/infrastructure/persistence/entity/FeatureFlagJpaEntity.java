/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : FeatureFlagJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for FeatureFlag.
 *
 */
package dz.sh.hidra.modules.configuration.infrastructure.persistence.entity;

import dz.sh.hidra.modules.configuration.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for FeatureFlag.
     */
    @Entity
    @Table(name = "hidra_configuration_feature_flag")
    public class FeatureFlagJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "code", nullable = false, length = 120)
    private String code;

    @Column(name = "name_fr", nullable = false, length = 160)
    private String nameFr;

    @Column(name = "name_ar", nullable = true, length = 160)
    private String nameAr;

    @Column(name = "name_en", nullable = true, length = 160)
    private String nameEn;

    @Column(name = "owning_module", nullable = false, length = 80)
    private String owningModule;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private FeatureFlagStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "evaluation_strategy", nullable = false, length = 40)
    private FeatureFlagEvaluationStrategy evaluationStrategy;

    @Column(name = "default_enabled", nullable = false)
    private boolean defaultEnabled;

    @Column(name = "description", nullable = true, length = 1000)
    private String description;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected FeatureFlagJpaEntity() {
            // Required by JPA.
        }

        public FeatureFlagJpaEntity(
                String id,
            String code,
            String nameFr,
            String nameAr,
            String nameEn,
            String owningModule,
            FeatureFlagStatus status,
            FeatureFlagEvaluationStrategy evaluationStrategy,
            boolean defaultEnabled,
            String description,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.code = code;
        this.nameFr = nameFr;
        this.nameAr = nameAr;
        this.nameEn = nameEn;
        this.owningModule = owningModule;
        this.status = status;
        this.evaluationStrategy = evaluationStrategy;
        this.defaultEnabled = defaultEnabled;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String code() {
        return code;
    }


    public String nameFr() {
        return nameFr;
    }


    public String nameAr() {
        return nameAr;
    }


    public String nameEn() {
        return nameEn;
    }


    public String owningModule() {
        return owningModule;
    }


    public FeatureFlagStatus status() {
        return status;
    }


    public FeatureFlagEvaluationStrategy evaluationStrategy() {
        return evaluationStrategy;
    }


    public boolean defaultEnabled() {
        return defaultEnabled;
    }


    public String description() {
        return description;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
