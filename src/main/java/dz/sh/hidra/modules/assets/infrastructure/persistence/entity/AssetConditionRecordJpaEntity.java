/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetConditionRecordJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for AssetConditionRecord.
 *
 */
package dz.sh.hidra.modules.assets.infrastructure.persistence.entity;

import dz.sh.hidra.modules.assets.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;

    /**
     * Database-backed JPA entity for AssetConditionRecord.
     */
    @Entity
    @Table(name = "hidra_asset_condition_record")
    public class AssetConditionRecordJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "maintainable_asset_id", nullable = false, length = 80)
    private String maintainableAssetId;

    @Enumerated(EnumType.STRING)
    @Column(name = "condition_status", nullable = false, length = 40)
    private AssetConditionStatus conditionStatus;

    @Column(name = "condition_type_id", nullable = true, length = 80)
    private String conditionTypeId;

    @Column(name = "source_module", nullable = true, length = 80)
    private String sourceModule;

    @Column(name = "source_reference_id", nullable = true, length = 80)
    private String sourceReferenceId;

    @Column(name = "summary", nullable = true, columnDefinition = "text")
    private String summary;

    @Column(name = "condition_score", nullable = true, precision = 10, scale = 4)
    private BigDecimal conditionScore;

    @Column(name = "observed_at", nullable = false)
    private Instant observedAt;

    @Column(name = "observed_by_actor_id", nullable = true, length = 80)
    private String observedByActorId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected AssetConditionRecordJpaEntity() {
            // Required by JPA.
        }

        public AssetConditionRecordJpaEntity(
                String id,
            String maintainableAssetId,
            AssetConditionStatus conditionStatus,
            String conditionTypeId,
            String sourceModule,
            String sourceReferenceId,
            String summary,
            BigDecimal conditionScore,
            Instant observedAt,
            String observedByActorId,
            Instant createdAt
        ) {
            this.id = id;
        this.maintainableAssetId = maintainableAssetId;
        this.conditionStatus = conditionStatus;
        this.conditionTypeId = conditionTypeId;
        this.sourceModule = sourceModule;
        this.sourceReferenceId = sourceReferenceId;
        this.summary = summary;
        this.conditionScore = conditionScore;
        this.observedAt = observedAt;
        this.observedByActorId = observedByActorId;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String maintainableAssetId() {
        return maintainableAssetId;
    }


    public AssetConditionStatus conditionStatus() {
        return conditionStatus;
    }


    public String conditionTypeId() {
        return conditionTypeId;
    }


    public String sourceModule() {
        return sourceModule;
    }


    public String sourceReferenceId() {
        return sourceReferenceId;
    }


    public String summary() {
        return summary;
    }


    public BigDecimal conditionScore() {
        return conditionScore;
    }


    public Instant observedAt() {
        return observedAt;
    }


    public String observedByActorId() {
        return observedByActorId;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
