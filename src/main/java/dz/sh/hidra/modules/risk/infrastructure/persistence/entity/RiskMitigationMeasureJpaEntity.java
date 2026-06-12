/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskMitigationMeasureJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for RiskMitigationMeasure.
 *
 */
package dz.sh.hidra.modules.risk.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for RiskMitigationMeasure.
     */
    @Entity
    @Table(name = "hidra_risk_mitigation_measure")
    public class RiskMitigationMeasureJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "code", nullable = false, length = 80)
    private String code;

    @Column(name = "name_ar", nullable = true, length = 160)
    private String nameAr;

    @Column(name = "name_fr", nullable = false, length = 160)
    private String nameFr;

    @Column(name = "name_en", nullable = true, length = 160)
    private String nameEn;

    @Column(name = "description", nullable = true, columnDefinition = "text")
    private String description;

    @Column(name = "mitigation_type_id", nullable = false, length = 80)
    private String mitigationTypeId;

    @Column(name = "applicable_threat_type_id", nullable = true, length = 80)
    private String applicableThreatTypeId;

    @Column(name = "applicable_asset_type_id", nullable = true, length = 80)
    private String applicableAssetTypeId;

    @Column(name = "expected_effect_on_likelihood", nullable = true, precision = 10, scale = 6)
    private BigDecimal expectedEffectOnLikelihood;

    @Column(name = "expected_effect_on_consequence", nullable = true, precision = 10, scale = 6)
    private BigDecimal expectedEffectOnConsequence;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected RiskMitigationMeasureJpaEntity() {
            // Required by JPA.
        }

        public RiskMitigationMeasureJpaEntity(
                String id,
            String code,
            String nameAr,
            String nameFr,
            String nameEn,
            String description,
            String mitigationTypeId,
            String applicableThreatTypeId,
            String applicableAssetTypeId,
            BigDecimal expectedEffectOnLikelihood,
            BigDecimal expectedEffectOnConsequence,
            boolean active,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.code = code;
        this.nameAr = nameAr;
        this.nameFr = nameFr;
        this.nameEn = nameEn;
        this.description = description;
        this.mitigationTypeId = mitigationTypeId;
        this.applicableThreatTypeId = applicableThreatTypeId;
        this.applicableAssetTypeId = applicableAssetTypeId;
        this.expectedEffectOnLikelihood = expectedEffectOnLikelihood;
        this.expectedEffectOnConsequence = expectedEffectOnConsequence;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String code() {
        return code;
    }


    public String nameAr() {
        return nameAr;
    }


    public String nameFr() {
        return nameFr;
    }


    public String nameEn() {
        return nameEn;
    }


    public String description() {
        return description;
    }


    public String mitigationTypeId() {
        return mitigationTypeId;
    }


    public String applicableThreatTypeId() {
        return applicableThreatTypeId;
    }


    public String applicableAssetTypeId() {
        return applicableAssetTypeId;
    }


    public BigDecimal expectedEffectOnLikelihood() {
        return expectedEffectOnLikelihood;
    }


    public BigDecimal expectedEffectOnConsequence() {
        return expectedEffectOnConsequence;
    }


    public boolean active() {
        return active;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
