/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseImpactAssessmentJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for HseImpactAssessment.
 *
 */
package dz.sh.hidra.modules.hse.infrastructure.persistence.entity;

import dz.sh.hidra.modules.hse.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;

    /**
     * Database-backed JPA entity for HseImpactAssessment.
     */
    @Entity
    @Table(name = "hidra_hse_impact_assessment")
    public class HseImpactAssessmentJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "hse_case_id", nullable = false, length = 80)
    private String hseCaseId;

    @Enumerated(EnumType.STRING)
    @Column(name = "impact_domain", nullable = false, length = 40)
    private HseImpactDomain impactDomain;

    @Column(name = "impact_type_id", nullable = false, length = 80)
    private String impactTypeId;

    @Enumerated(EnumType.STRING)
    @Column(name = "severity", nullable = false, length = 40)
    private HseImpactSeverity severity;

    @Column(name = "description", nullable = true, columnDefinition = "text")
    private String description;

    @Column(name = "people_affected_count", nullable = true)
    private Integer peopleAffectedCount;

    @Column(name = "injury_count", nullable = true)
    private Integer injuryCount;

    @Column(name = "spill_volume", nullable = true, precision = 18, scale = 6)
    private BigDecimal spillVolume;

    @Column(name = "spill_volume_unit_id", nullable = true, length = 80)
    private String spillVolumeUnitId;

    @Column(name = "estimated_cost", nullable = true, precision = 18, scale = 6)
    private BigDecimal estimatedCost;

    @Column(name = "currency_code", nullable = true, length = 3)
    private String currencyCode;

    @Column(name = "regulatory_reference_id", nullable = true, length = 80)
    private String regulatoryReferenceId;

    @Column(name = "assessed_by_actor_id", nullable = true, length = 80)
    private String assessedByActorId;

    @Column(name = "assessed_at", nullable = false)
    private Instant assessedAt;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected HseImpactAssessmentJpaEntity() {
            // Required by JPA.
        }

        public HseImpactAssessmentJpaEntity(
                String id,
            String hseCaseId,
            HseImpactDomain impactDomain,
            String impactTypeId,
            HseImpactSeverity severity,
            String description,
            Integer peopleAffectedCount,
            Integer injuryCount,
            BigDecimal spillVolume,
            String spillVolumeUnitId,
            BigDecimal estimatedCost,
            String currencyCode,
            String regulatoryReferenceId,
            String assessedByActorId,
            Instant assessedAt,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.hseCaseId = hseCaseId;
        this.impactDomain = impactDomain;
        this.impactTypeId = impactTypeId;
        this.severity = severity;
        this.description = description;
        this.peopleAffectedCount = peopleAffectedCount;
        this.injuryCount = injuryCount;
        this.spillVolume = spillVolume;
        this.spillVolumeUnitId = spillVolumeUnitId;
        this.estimatedCost = estimatedCost;
        this.currencyCode = currencyCode;
        this.regulatoryReferenceId = regulatoryReferenceId;
        this.assessedByActorId = assessedByActorId;
        this.assessedAt = assessedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String hseCaseId() {
        return hseCaseId;
    }


    public HseImpactDomain impactDomain() {
        return impactDomain;
    }


    public String impactTypeId() {
        return impactTypeId;
    }


    public HseImpactSeverity severity() {
        return severity;
    }


    public String description() {
        return description;
    }


    public Integer peopleAffectedCount() {
        return peopleAffectedCount;
    }


    public Integer injuryCount() {
        return injuryCount;
    }


    public BigDecimal spillVolume() {
        return spillVolume;
    }


    public String spillVolumeUnitId() {
        return spillVolumeUnitId;
    }


    public BigDecimal estimatedCost() {
        return estimatedCost;
    }


    public String currencyCode() {
        return currencyCode;
    }


    public String regulatoryReferenceId() {
        return regulatoryReferenceId;
    }


    public String assessedByActorId() {
        return assessedByActorId;
    }


    public Instant assessedAt() {
        return assessedAt;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
