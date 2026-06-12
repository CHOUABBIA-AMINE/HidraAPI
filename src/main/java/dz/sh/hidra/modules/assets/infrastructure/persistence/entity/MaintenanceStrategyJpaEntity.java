/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MaintenanceStrategyJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for MaintenanceStrategy.
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
     * Database-backed JPA entity for MaintenanceStrategy.
     */
    @Entity
    @Table(name = "hidra_asset_maintenance_strategy")
    public class MaintenanceStrategyJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "strategy_code", nullable = false, length = 80)
    private String strategyCode;

    @Column(name = "name", nullable = false, length = 160)
    private String name;

    @Column(name = "strategy_type_id", nullable = false, length = 80)
    private String strategyTypeId;

    @Column(name = "asset_type_id", nullable = true, length = 80)
    private String assetTypeId;

    @Column(name = "description", nullable = true, columnDefinition = "text")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private MaintenanceStrategyStatus status;

    @Column(name = "effective_from", nullable = true)
    private Instant effectiveFrom;

    @Column(name = "effective_to", nullable = true)
    private Instant effectiveTo;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected MaintenanceStrategyJpaEntity() {
            // Required by JPA.
        }

        public MaintenanceStrategyJpaEntity(
                String id,
            String strategyCode,
            String name,
            String strategyTypeId,
            String assetTypeId,
            String description,
            MaintenanceStrategyStatus status,
            Instant effectiveFrom,
            Instant effectiveTo,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.strategyCode = strategyCode;
        this.name = name;
        this.strategyTypeId = strategyTypeId;
        this.assetTypeId = assetTypeId;
        this.description = description;
        this.status = status;
        this.effectiveFrom = effectiveFrom;
        this.effectiveTo = effectiveTo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String strategyCode() {
        return strategyCode;
    }


    public String name() {
        return name;
    }


    public String strategyTypeId() {
        return strategyTypeId;
    }


    public String assetTypeId() {
        return assetTypeId;
    }


    public String description() {
        return description;
    }


    public MaintenanceStrategyStatus status() {
        return status;
    }


    public Instant effectiveFrom() {
        return effectiveFrom;
    }


    public Instant effectiveTo() {
        return effectiveTo;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
