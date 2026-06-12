/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringAlertCandidateJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for MonitoringAlertCandidate.
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
     * Database-backed JPA entity for MonitoringAlertCandidate.
     */
    @Entity
    @Table(name = "hidra_monitoring_alert_candidate")
    public class MonitoringAlertCandidateJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "deviation_id", nullable = true, length = 80)
    private String deviationId;

    @Column(name = "evaluation_id", nullable = true, length = 80)
    private String evaluationId;

    @Column(name = "rule_id", nullable = true, length = 80)
    private String ruleId;

    @Column(name = "candidate_code", nullable = false, length = 80)
    private String candidateCode;

    @Column(name = "candidate_type_id", nullable = false, length = 80)
    private String candidateTypeId;

    @Enumerated(EnumType.STRING)
    @Column(name = "severity", nullable = false, length = 40)
    private DeviationSeverity severity;

    @Column(name = "topology_asset_type", nullable = true, length = 160)
    private String topologyAssetType;

    @Column(name = "topology_asset_id", nullable = true, length = 80)
    private String topologyAssetId;

    @Column(name = "telemetry_point_id", nullable = true, length = 80)
    private String telemetryPointId;

    @Column(name = "summary", nullable = true, length = 500)
    private String summary;

    @Enumerated(EnumType.STRING)
    @Column(name = "lifecycle_status", nullable = false, length = 40)
    private MonitoringLifecycleStatus lifecycleStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "candidate_status", nullable = false, length = 40)
    private AlertCandidateStatus candidateStatus;

    @Column(name = "escalation_reference_id", nullable = true, length = 80)
    private String escalationReferenceId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "expires_at", nullable = true)
    private Instant expiresAt;

        protected MonitoringAlertCandidateJpaEntity() {
            // Required by JPA.
        }

        public MonitoringAlertCandidateJpaEntity(
                String id,
            String deviationId,
            String evaluationId,
            String ruleId,
            String candidateCode,
            String candidateTypeId,
            DeviationSeverity severity,
            String topologyAssetType,
            String topologyAssetId,
            String telemetryPointId,
            String summary,
            MonitoringLifecycleStatus lifecycleStatus,
            AlertCandidateStatus candidateStatus,
            String escalationReferenceId,
            Instant createdAt,
            Instant expiresAt
        ) {
            this.id = id;
        this.deviationId = deviationId;
        this.evaluationId = evaluationId;
        this.ruleId = ruleId;
        this.candidateCode = candidateCode;
        this.candidateTypeId = candidateTypeId;
        this.severity = severity;
        this.topologyAssetType = topologyAssetType;
        this.topologyAssetId = topologyAssetId;
        this.telemetryPointId = telemetryPointId;
        this.summary = summary;
        this.lifecycleStatus = lifecycleStatus;
        this.candidateStatus = candidateStatus;
        this.escalationReferenceId = escalationReferenceId;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        }


    public String id() {
        return id;
    }


    public String deviationId() {
        return deviationId;
    }


    public String evaluationId() {
        return evaluationId;
    }


    public String ruleId() {
        return ruleId;
    }


    public String candidateCode() {
        return candidateCode;
    }


    public String candidateTypeId() {
        return candidateTypeId;
    }


    public DeviationSeverity severity() {
        return severity;
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


    public String summary() {
        return summary;
    }


    public MonitoringLifecycleStatus lifecycleStatus() {
        return lifecycleStatus;
    }


    public AlertCandidateStatus candidateStatus() {
        return candidateStatus;
    }


    public String escalationReferenceId() {
        return escalationReferenceId;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant expiresAt() {
        return expiresAt;
    }

    }
