/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MaintenanceExecutionRecordJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for MaintenanceExecutionRecord.
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
     * Database-backed JPA entity for MaintenanceExecutionRecord.
     */
    @Entity
    @Table(name = "hidra_asset_maintenance_execution_record")
    public class MaintenanceExecutionRecordJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "work_order_id", nullable = false, length = 80)
    private String workOrderId;

    @Column(name = "task_id", nullable = true, length = 80)
    private String taskId;

    @Enumerated(EnumType.STRING)
    @Column(name = "execution_result", nullable = false, length = 40)
    private ExecutionResultStatus executionResult;

    @Column(name = "performed_by_actor_id", nullable = true, length = 80)
    private String performedByActorId;

    @Column(name = "executed_at", nullable = false)
    private Instant executedAt;

    @Column(name = "duration_minutes", nullable = true)
    private Integer durationMinutes;

    @Column(name = "result_summary", nullable = true, columnDefinition = "text")
    private String resultSummary;

    @Column(name = "measurement_json", nullable = true, columnDefinition = "jsonb")
    private String measurementJson;

    @Column(name = "follow_up_recommendation_id", nullable = true, length = 80)
    private String followUpRecommendationId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected MaintenanceExecutionRecordJpaEntity() {
            // Required by JPA.
        }

        public MaintenanceExecutionRecordJpaEntity(
                String id,
            String workOrderId,
            String taskId,
            ExecutionResultStatus executionResult,
            String performedByActorId,
            Instant executedAt,
            Integer durationMinutes,
            String resultSummary,
            String measurementJson,
            String followUpRecommendationId,
            Instant createdAt
        ) {
            this.id = id;
        this.workOrderId = workOrderId;
        this.taskId = taskId;
        this.executionResult = executionResult;
        this.performedByActorId = performedByActorId;
        this.executedAt = executedAt;
        this.durationMinutes = durationMinutes;
        this.resultSummary = resultSummary;
        this.measurementJson = measurementJson;
        this.followUpRecommendationId = followUpRecommendationId;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String workOrderId() {
        return workOrderId;
    }


    public String taskId() {
        return taskId;
    }


    public ExecutionResultStatus executionResult() {
        return executionResult;
    }


    public String performedByActorId() {
        return performedByActorId;
    }


    public Instant executedAt() {
        return executedAt;
    }


    public Integer durationMinutes() {
        return durationMinutes;
    }


    public String resultSummary() {
        return resultSummary;
    }


    public String measurementJson() {
        return measurementJson;
    }


    public String followUpRecommendationId() {
        return followUpRecommendationId;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
