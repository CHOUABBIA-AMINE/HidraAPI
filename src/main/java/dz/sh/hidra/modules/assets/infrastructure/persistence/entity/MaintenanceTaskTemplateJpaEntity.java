/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MaintenanceTaskTemplateJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for MaintenanceTaskTemplate.
 *
 */
package dz.sh.hidra.modules.assets.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for MaintenanceTaskTemplate.
     */
    @Entity
    @Table(name = "hidra_asset_maintenance_task_template")
    public class MaintenanceTaskTemplateJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "template_code", nullable = false, length = 80)
    private String templateCode;

    @Column(name = "name", nullable = false, length = 160)
    private String name;

    @Column(name = "asset_type_id", nullable = true, length = 80)
    private String assetTypeId;

    @Column(name = "maintenance_strategy_id", nullable = true, length = 80)
    private String maintenanceStrategyId;

    @Column(name = "task_type_id", nullable = false, length = 80)
    private String taskTypeId;

    @Column(name = "instructions", nullable = true, columnDefinition = "text")
    private String instructions;

    @Column(name = "estimated_duration_minutes", nullable = true)
    private Integer estimatedDurationMinutes;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected MaintenanceTaskTemplateJpaEntity() {
            // Required by JPA.
        }

        public MaintenanceTaskTemplateJpaEntity(
                String id,
            String templateCode,
            String name,
            String assetTypeId,
            String maintenanceStrategyId,
            String taskTypeId,
            String instructions,
            Integer estimatedDurationMinutes,
            boolean active,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.templateCode = templateCode;
        this.name = name;
        this.assetTypeId = assetTypeId;
        this.maintenanceStrategyId = maintenanceStrategyId;
        this.taskTypeId = taskTypeId;
        this.instructions = instructions;
        this.estimatedDurationMinutes = estimatedDurationMinutes;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String templateCode() {
        return templateCode;
    }


    public String name() {
        return name;
    }


    public String assetTypeId() {
        return assetTypeId;
    }


    public String maintenanceStrategyId() {
        return maintenanceStrategyId;
    }


    public String taskTypeId() {
        return taskTypeId;
    }


    public String instructions() {
        return instructions;
    }


    public Integer estimatedDurationMinutes() {
        return estimatedDurationMinutes;
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
