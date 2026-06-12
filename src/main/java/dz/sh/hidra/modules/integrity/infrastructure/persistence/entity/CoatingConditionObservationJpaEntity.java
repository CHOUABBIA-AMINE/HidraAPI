/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CoatingConditionObservationJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for CoatingConditionObservation.
 *
 */
package dz.sh.hidra.modules.integrity.infrastructure.persistence.entity;

import dz.sh.hidra.modules.integrity.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;

    /**
     * Database-backed JPA entity for CoatingConditionObservation.
     */
    @Entity
    @Table(name = "hidra_integrity_coating_condition_observation")
    public class CoatingConditionObservationJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "inspection_run_id", nullable = true, length = 80)
    private String inspectionRunId;

    @Column(name = "topology_asset_type_code", nullable = false, length = 80)
    private String topologyAssetTypeCode;

    @Column(name = "topology_asset_id", nullable = false, length = 80)
    private String topologyAssetId;

    @Column(name = "coating_condition_id", nullable = false, length = 80)
    private String coatingConditionId;

    @Column(name = "kilometer_point", nullable = true, precision = 14, scale = 4)
    private BigDecimal kilometerPoint;

    @Column(name = "description", nullable = true, columnDefinition = "text")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "severity", nullable = true, length = 40)
    private FindingSeverity severity;

    @Column(name = "observed_at", nullable = false)
    private Instant observedAt;

    @Column(name = "observed_by_actor_id", nullable = true, length = 80)
    private String observedByActorId;

        protected CoatingConditionObservationJpaEntity() {
            // Required by JPA.
        }

        public CoatingConditionObservationJpaEntity(
                String id,
            String inspectionRunId,
            String topologyAssetTypeCode,
            String topologyAssetId,
            String coatingConditionId,
            BigDecimal kilometerPoint,
            String description,
            FindingSeverity severity,
            Instant observedAt,
            String observedByActorId
        ) {
            this.id = id;
        this.inspectionRunId = inspectionRunId;
        this.topologyAssetTypeCode = topologyAssetTypeCode;
        this.topologyAssetId = topologyAssetId;
        this.coatingConditionId = coatingConditionId;
        this.kilometerPoint = kilometerPoint;
        this.description = description;
        this.severity = severity;
        this.observedAt = observedAt;
        this.observedByActorId = observedByActorId;
        }


    public String id() {
        return id;
    }


    public String inspectionRunId() {
        return inspectionRunId;
    }


    public String topologyAssetTypeCode() {
        return topologyAssetTypeCode;
    }


    public String topologyAssetId() {
        return topologyAssetId;
    }


    public String coatingConditionId() {
        return coatingConditionId;
    }


    public BigDecimal kilometerPoint() {
        return kilometerPoint;
    }


    public String description() {
        return description;
    }


    public FindingSeverity severity() {
        return severity;
    }


    public Instant observedAt() {
        return observedAt;
    }


    public String observedByActorId() {
        return observedByActorId;
    }

    }
