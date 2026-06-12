/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MaintenancePlanJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for MaintenancePlan.
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
import java.time.Instant;

    /**
     * Database-backed JPA entity for MaintenancePlan.
     */
    @Entity
    @Table(name = "hidra_asset_maintenance_plan")
    public class MaintenancePlanJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "plan_code", nullable = false, length = 80)
    private String planCode;

    @Column(name = "name", nullable = false, length = 160)
    private String name;

    @Column(name = "maintainable_asset_id", nullable = false, length = 80)
    private String maintainableAssetId;

    @Column(name = "maintenance_strategy_id", nullable = true, length = 80)
    private String maintenanceStrategyId;

    @Column(name = "frequency_id", nullable = true, length = 80)
    private String frequencyId;

    @Column(name = "next_due_at", nullable = true)
    private Instant nextDueAt;

    @Column(name = "last_executed_at", nullable = true)
    private Instant lastExecutedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private MaintenancePlanStatus status;

    @Column(name = "created_by_actor_id", nullable = true, length = 80)
    private String createdByActorId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected MaintenancePlanJpaEntity() {
            // Required by JPA.
        }

        public MaintenancePlanJpaEntity(
                String id,
            String planCode,
            String name,
            String maintainableAssetId,
            String maintenanceStrategyId,
            String frequencyId,
            Instant nextDueAt,
            Instant lastExecutedAt,
            MaintenancePlanStatus status,
            String createdByActorId,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.planCode = planCode;
        this.name = name;
        this.maintainableAssetId = maintainableAssetId;
        this.maintenanceStrategyId = maintenanceStrategyId;
        this.frequencyId = frequencyId;
        this.nextDueAt = nextDueAt;
        this.lastExecutedAt = lastExecutedAt;
        this.status = status;
        this.createdByActorId = createdByActorId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String planCode() {
        return planCode;
    }


    public String name() {
        return name;
    }


    public String maintainableAssetId() {
        return maintainableAssetId;
    }


    public String maintenanceStrategyId() {
        return maintenanceStrategyId;
    }


    public String frequencyId() {
        return frequencyId;
    }


    public Instant nextDueAt() {
        return nextDueAt;
    }


    public Instant lastExecutedAt() {
        return lastExecutedAt;
    }


    public MaintenancePlanStatus status() {
        return status;
    }


    public String createdByActorId() {
        return createdByActorId;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
