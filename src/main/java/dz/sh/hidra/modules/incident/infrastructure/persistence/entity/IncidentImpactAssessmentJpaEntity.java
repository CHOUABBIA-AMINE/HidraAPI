/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentImpactAssessmentJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for IncidentImpactAssessment.
 *
 */
package dz.sh.hidra.modules.incident.infrastructure.persistence.entity;

import java.math.BigDecimal;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for IncidentImpactAssessment.
     */
    @Entity
    @Table(name = "hidra_incident_impact_assessment")
    public class IncidentImpactAssessmentJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "incident_id", nullable = false, length = 80)
    private String incidentId;

    @Column(name = "impact_type_id", nullable = false, length = 80)
    private String impactTypeId;

    @Column(name = "impact_level_id", nullable = false, length = 80)
    private String impactLevelId;

    @Column(name = "estimated", nullable = false)
    private boolean estimated;

    @Column(name = "description", nullable = true, columnDefinition = "text")
    private String description;

    @Column(name = "affected_topology_asset_type_code", nullable = true, length = 80)
    private String affectedTopologyAssetTypeCode;

    @Column(name = "affected_topology_asset_id", nullable = true, length = 80)
    private String affectedTopologyAssetId;

    @Column(name = "affected_organization_unit_id", nullable = true, length = 80)
    private String affectedOrganizationUnitId;

    @Column(name = "estimated_volume_loss", nullable = true, precision = 18, scale = 6)
    private BigDecimal estimatedVolumeLoss;

    @Column(name = "estimated_volume_unit_id", nullable = true, length = 80)
    private String estimatedVolumeUnitId;

    @Column(name = "estimated_duration_minutes", nullable = true)
    private Integer estimatedDurationMinutes;

    @Column(name = "assessed_by_actor_id", nullable = false, length = 80)
    private String assessedByActorId;

    @Column(name = "assessed_at", nullable = false)
    private Instant assessedAt;

        protected IncidentImpactAssessmentJpaEntity() {
            // Required by JPA.
        }

        public IncidentImpactAssessmentJpaEntity(
                String id,
            String incidentId,
            String impactTypeId,
            String impactLevelId,
            boolean estimated,
            String description,
            String affectedTopologyAssetTypeCode,
            String affectedTopologyAssetId,
            String affectedOrganizationUnitId,
            BigDecimal estimatedVolumeLoss,
            String estimatedVolumeUnitId,
            Integer estimatedDurationMinutes,
            String assessedByActorId,
            Instant assessedAt
        ) {
            this.id = id;
        this.incidentId = incidentId;
        this.impactTypeId = impactTypeId;
        this.impactLevelId = impactLevelId;
        this.estimated = estimated;
        this.description = description;
        this.affectedTopologyAssetTypeCode = affectedTopologyAssetTypeCode;
        this.affectedTopologyAssetId = affectedTopologyAssetId;
        this.affectedOrganizationUnitId = affectedOrganizationUnitId;
        this.estimatedVolumeLoss = estimatedVolumeLoss;
        this.estimatedVolumeUnitId = estimatedVolumeUnitId;
        this.estimatedDurationMinutes = estimatedDurationMinutes;
        this.assessedByActorId = assessedByActorId;
        this.assessedAt = assessedAt;
        }


    public String id() {
        return id;
    }


    public String incidentId() {
        return incidentId;
    }


    public String impactTypeId() {
        return impactTypeId;
    }


    public String impactLevelId() {
        return impactLevelId;
    }


    public boolean estimated() {
        return estimated;
    }


    public String description() {
        return description;
    }


    public String affectedTopologyAssetTypeCode() {
        return affectedTopologyAssetTypeCode;
    }


    public String affectedTopologyAssetId() {
        return affectedTopologyAssetId;
    }


    public String affectedOrganizationUnitId() {
        return affectedOrganizationUnitId;
    }


    public BigDecimal estimatedVolumeLoss() {
        return estimatedVolumeLoss;
    }


    public String estimatedVolumeUnitId() {
        return estimatedVolumeUnitId;
    }


    public Integer estimatedDurationMinutes() {
        return estimatedDurationMinutes;
    }


    public String assessedByActorId() {
        return assessedByActorId;
    }


    public Instant assessedAt() {
        return assessedAt;
    }

    }
