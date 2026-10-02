/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringEvaluationJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for MonitoringEvaluation.
 *
 */
package dz.sh.hidra.modules.monitoring.infrastructure.persistence.entity;

import dz.sh.hidra.modules.monitoring.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for MonitoringEvaluation.
     */
    @Entity
    @Table(name = "hidra_monitoring_evaluation")
    public class MonitoringEvaluationJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "rule_id", nullable = true, length = 80)
    private String ruleId;

    @Column(name = "period_id", nullable = true, length = 80)
    private String periodId;

    @Column(name = "plan_revision_id", nullable = true, length = 80)
    private String planRevisionId;

    @Column(name = "topology_asset_type", nullable = true, length = 160)
    private String topologyAssetType;

    @Column(name = "topology_asset_id", nullable = true, length = 80)
    private String topologyAssetId;

    @Column(name = "telemetry_point_id", nullable = true, length = 80)
    private String telemetryPointId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private EvaluationStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "result", nullable = true, length = 40)
    private EvaluationResult result;

    @Column(name = "evaluation_start", nullable = false)
    private Instant evaluationStart;

    @Column(name = "evaluation_end", nullable = true)
    private Instant evaluationEnd;

    @Column(name = "actual_reading_count", nullable = false)
    private int actualReadingCount;

    @Column(name = "deviation_count", nullable = false)
    private int deviationCount;

    @Column(name = "failure_reason", nullable = true, columnDefinition = "text")
    private String failureReason;

    @Column(name = "correlation_id", nullable = true, length = 80)
    private String correlationId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected MonitoringEvaluationJpaEntity() {
            // Required by JPA.
        }

        public MonitoringEvaluationJpaEntity(
                String id,
            String ruleId,
            String periodId,
            String planRevisionId,
            String topologyAssetType,
            String topologyAssetId,
            String telemetryPointId,
            EvaluationStatus status,
            EvaluationResult result,
            Instant evaluationStart,
            Instant evaluationEnd,
            int actualReadingCount,
            int deviationCount,
            String failureReason,
            String correlationId,
            Instant createdAt
        ) {
            this.id = id;
        this.ruleId = ruleId;
        this.periodId = periodId;
        this.planRevisionId = planRevisionId;
        this.topologyAssetType = topologyAssetType;
        this.topologyAssetId = topologyAssetId;
        this.telemetryPointId = telemetryPointId;
        this.status = status;
        this.result = result;
        this.evaluationStart = evaluationStart;
        this.evaluationEnd = evaluationEnd;
        this.actualReadingCount = actualReadingCount;
        this.deviationCount = deviationCount;
        this.failureReason = failureReason;
        this.correlationId = correlationId;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String ruleId() {
        return ruleId;
    }


    public String periodId() {
        return periodId;
    }


    public String planRevisionId() {
        return planRevisionId;
    }


    public String topologyAssetType() {
        return topologyAssetType;
    }


    public String topologyAssetId() {
        return topologyAssetId;
    }


    public String telemetryPointId() {
        return telemetryPointId;
    }


    public EvaluationStatus status() {
        return status;
    }


    public EvaluationResult result() {
        return result;
    }


    public Instant evaluationStart() {
        return evaluationStart;
    }


    public Instant evaluationEnd() {
        return evaluationEnd;
    }


    public int actualReadingCount() {
        return actualReadingCount;
    }


    public int deviationCount() {
        return deviationCount;
    }


    public String failureReason() {
        return failureReason;
    }


    public String correlationId() {
        return correlationId;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
