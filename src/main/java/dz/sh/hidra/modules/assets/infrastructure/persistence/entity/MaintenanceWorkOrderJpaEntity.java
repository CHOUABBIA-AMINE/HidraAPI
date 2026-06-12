/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MaintenanceWorkOrderJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for MaintenanceWorkOrder.
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
     * Database-backed JPA entity for MaintenanceWorkOrder.
     */
    @Entity
    @Table(name = "hidra_asset_maintenance_work_order")
    public class MaintenanceWorkOrderJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "work_order_number", nullable = false, length = 80)
    private String workOrderNumber;

    @Column(name = "maintainable_asset_id", nullable = false, length = 80)
    private String maintainableAssetId;

    @Column(name = "maintenance_plan_id", nullable = true, length = 80)
    private String maintenancePlanId;

    @Column(name = "source_recommendation_id", nullable = true, length = 80)
    private String sourceRecommendationId;

    @Column(name = "work_order_type_id", nullable = false, length = 80)
    private String workOrderTypeId;

    @Column(name = "priority_id", nullable = true, length = 80)
    private String priorityId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private MaintenanceWorkOrderStatus status;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "description", nullable = true, columnDefinition = "text")
    private String description;

    @Column(name = "assigned_organization_unit_id", nullable = true, length = 80)
    private String assignedOrganizationUnitId;

    @Column(name = "assigned_actor_id", nullable = true, length = 80)
    private String assignedActorId;

    @Column(name = "planned_start_at", nullable = true)
    private Instant plannedStartAt;

    @Column(name = "planned_end_at", nullable = true)
    private Instant plannedEndAt;

    @Column(name = "started_at", nullable = true)
    private Instant startedAt;

    @Column(name = "completed_at", nullable = true)
    private Instant completedAt;

    @Column(name = "workflow_instance_id", nullable = true, length = 80)
    private String workflowInstanceId;

    @Column(name = "created_by_actor_id", nullable = true, length = 80)
    private String createdByActorId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected MaintenanceWorkOrderJpaEntity() {
            // Required by JPA.
        }

        public MaintenanceWorkOrderJpaEntity(
                String id,
            String workOrderNumber,
            String maintainableAssetId,
            String maintenancePlanId,
            String sourceRecommendationId,
            String workOrderTypeId,
            String priorityId,
            MaintenanceWorkOrderStatus status,
            String title,
            String description,
            String assignedOrganizationUnitId,
            String assignedActorId,
            Instant plannedStartAt,
            Instant plannedEndAt,
            Instant startedAt,
            Instant completedAt,
            String workflowInstanceId,
            String createdByActorId,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.workOrderNumber = workOrderNumber;
        this.maintainableAssetId = maintainableAssetId;
        this.maintenancePlanId = maintenancePlanId;
        this.sourceRecommendationId = sourceRecommendationId;
        this.workOrderTypeId = workOrderTypeId;
        this.priorityId = priorityId;
        this.status = status;
        this.title = title;
        this.description = description;
        this.assignedOrganizationUnitId = assignedOrganizationUnitId;
        this.assignedActorId = assignedActorId;
        this.plannedStartAt = plannedStartAt;
        this.plannedEndAt = plannedEndAt;
        this.startedAt = startedAt;
        this.completedAt = completedAt;
        this.workflowInstanceId = workflowInstanceId;
        this.createdByActorId = createdByActorId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String workOrderNumber() {
        return workOrderNumber;
    }


    public String maintainableAssetId() {
        return maintainableAssetId;
    }


    public String maintenancePlanId() {
        return maintenancePlanId;
    }


    public String sourceRecommendationId() {
        return sourceRecommendationId;
    }


    public String workOrderTypeId() {
        return workOrderTypeId;
    }


    public String priorityId() {
        return priorityId;
    }


    public MaintenanceWorkOrderStatus status() {
        return status;
    }


    public String title() {
        return title;
    }


    public String description() {
        return description;
    }


    public String assignedOrganizationUnitId() {
        return assignedOrganizationUnitId;
    }


    public String assignedActorId() {
        return assignedActorId;
    }


    public Instant plannedStartAt() {
        return plannedStartAt;
    }


    public Instant plannedEndAt() {
        return plannedEndAt;
    }


    public Instant startedAt() {
        return startedAt;
    }


    public Instant completedAt() {
        return completedAt;
    }


    public String workflowInstanceId() {
        return workflowInstanceId;
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
