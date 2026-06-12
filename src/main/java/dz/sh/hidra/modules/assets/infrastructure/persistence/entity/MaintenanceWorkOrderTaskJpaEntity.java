/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MaintenanceWorkOrderTaskJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for MaintenanceWorkOrderTask.
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
     * Database-backed JPA entity for MaintenanceWorkOrderTask.
     */
    @Entity
    @Table(name = "hidra_asset_maintenance_work_order_task")
    public class MaintenanceWorkOrderTaskJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "work_order_id", nullable = false, length = 80)
    private String workOrderId;

    @Column(name = "task_template_id", nullable = true, length = 80)
    private String taskTemplateId;

    @Column(name = "task_number", nullable = false, length = 80)
    private String taskNumber;

    @Column(name = "task_type_id", nullable = false, length = 80)
    private String taskTypeId;

    @Column(name = "description", nullable = true, columnDefinition = "text")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private MaintenanceTaskStatus status;

    @Column(name = "sequence_number", nullable = false)
    private Integer sequenceNumber;

    @Column(name = "assigned_actor_id", nullable = true, length = 80)
    private String assignedActorId;

    @Column(name = "started_at", nullable = true)
    private Instant startedAt;

    @Column(name = "completed_at", nullable = true)
    private Instant completedAt;

    @Column(name = "result_summary", nullable = true, columnDefinition = "text")
    private String resultSummary;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected MaintenanceWorkOrderTaskJpaEntity() {
            // Required by JPA.
        }

        public MaintenanceWorkOrderTaskJpaEntity(
                String id,
            String workOrderId,
            String taskTemplateId,
            String taskNumber,
            String taskTypeId,
            String description,
            MaintenanceTaskStatus status,
            Integer sequenceNumber,
            String assignedActorId,
            Instant startedAt,
            Instant completedAt,
            String resultSummary,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.workOrderId = workOrderId;
        this.taskTemplateId = taskTemplateId;
        this.taskNumber = taskNumber;
        this.taskTypeId = taskTypeId;
        this.description = description;
        this.status = status;
        this.sequenceNumber = sequenceNumber;
        this.assignedActorId = assignedActorId;
        this.startedAt = startedAt;
        this.completedAt = completedAt;
        this.resultSummary = resultSummary;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String workOrderId() {
        return workOrderId;
    }


    public String taskTemplateId() {
        return taskTemplateId;
    }


    public String taskNumber() {
        return taskNumber;
    }


    public String taskTypeId() {
        return taskTypeId;
    }


    public String description() {
        return description;
    }


    public MaintenanceTaskStatus status() {
        return status;
    }


    public Integer sequenceNumber() {
        return sequenceNumber;
    }


    public String assignedActorId() {
        return assignedActorId;
    }


    public Instant startedAt() {
        return startedAt;
    }


    public Instant completedAt() {
        return completedAt;
    }


    public String resultSummary() {
        return resultSummary;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
