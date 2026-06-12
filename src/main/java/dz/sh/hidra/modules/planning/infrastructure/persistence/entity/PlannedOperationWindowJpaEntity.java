/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlannedOperationWindowJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for PlannedOperationWindow.
 *
 */
package dz.sh.hidra.modules.planning.infrastructure.persistence.entity;

import dz.sh.hidra.modules.planning.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;

    /**
     * Database-backed JPA entity for PlannedOperationWindow.
     */
    @Entity
    @Table(name = "hidra_planning_operation_window")
    public class PlannedOperationWindowJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "revision_id", nullable = false, length = 80)
    private String revisionId;

    @Column(name = "scenario_id", nullable = true, length = 80)
    private String scenarioId;

    @Column(name = "code", nullable = false, length = 80)
    private String code;

    @Column(name = "window_type_id", nullable = false, length = 80)
    private String windowTypeId;

    @Column(name = "topology_asset_type", nullable = false, length = 160)
    private String topologyAssetType;

    @Column(name = "topology_asset_id", nullable = false, length = 80)
    private String topologyAssetId;

    @Column(name = "topology_asset_code", nullable = false, length = 160)
    private String topologyAssetCode;

    @Column(name = "planned_start", nullable = false)
    private Instant plannedStart;

    @Column(name = "planned_end", nullable = false)
    private Instant plannedEnd;

    @Column(name = "capacity_impact_percent", nullable = true, precision = 10, scale = 4)
    private BigDecimal capacityImpactPercent;

    @Column(name = "description", nullable = true, length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private OperationWindowStatus status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected PlannedOperationWindowJpaEntity() {
            // Required by JPA.
        }

        public PlannedOperationWindowJpaEntity(
                String id,
            String revisionId,
            String scenarioId,
            String code,
            String windowTypeId,
            String topologyAssetType,
            String topologyAssetId,
            String topologyAssetCode,
            Instant plannedStart,
            Instant plannedEnd,
            BigDecimal capacityImpactPercent,
            String description,
            OperationWindowStatus status,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.revisionId = revisionId;
        this.scenarioId = scenarioId;
        this.code = code;
        this.windowTypeId = windowTypeId;
        this.topologyAssetType = topologyAssetType;
        this.topologyAssetId = topologyAssetId;
        this.topologyAssetCode = topologyAssetCode;
        this.plannedStart = plannedStart;
        this.plannedEnd = plannedEnd;
        this.capacityImpactPercent = capacityImpactPercent;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String revisionId() {
        return revisionId;
    }


    public String scenarioId() {
        return scenarioId;
    }


    public String code() {
        return code;
    }


    public String windowTypeId() {
        return windowTypeId;
    }


    public String topologyAssetType() {
        return topologyAssetType;
    }


    public String topologyAssetId() {
        return topologyAssetId;
    }


    public String topologyAssetCode() {
        return topologyAssetCode;
    }


    public Instant plannedStart() {
        return plannedStart;
    }


    public Instant plannedEnd() {
        return plannedEnd;
    }


    public BigDecimal capacityImpactPercent() {
        return capacityImpactPercent;
    }


    public String description() {
        return description;
    }


    public OperationWindowStatus status() {
        return status;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
