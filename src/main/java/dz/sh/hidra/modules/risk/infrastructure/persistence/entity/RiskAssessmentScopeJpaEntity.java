/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskAssessmentScopeJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for RiskAssessmentScope.
 *
 */
package dz.sh.hidra.modules.risk.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for RiskAssessmentScope.
     */
    @Entity
    @Table(name = "hidra_risk_assessment_scope")
    public class RiskAssessmentScopeJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "risk_assessment_id", nullable = false, length = 80)
    private String riskAssessmentId;

    @Column(name = "scope_type", nullable = false, length = 160)
    private String scopeType;

    @Column(name = "scope_id", nullable = false, length = 80)
    private String scopeId;

    @Column(name = "scope_code_snapshot", nullable = true, length = 160)
    private String scopeCodeSnapshot;

    @Column(name = "scope_label_snapshot", nullable = true, length = 500)
    private String scopeLabelSnapshot;

    @Column(name = "topology_snapshot_id", nullable = true, length = 80)
    private String topologySnapshotId;

    @Column(name = "operational_period_start", nullable = true)
    private Instant operationalPeriodStart;

    @Column(name = "operational_period_end", nullable = true)
    private Instant operationalPeriodEnd;

    @Column(name = "included", nullable = false)
    private boolean included;

    @Column(name = "scope_note", nullable = true, columnDefinition = "text")
    private String scopeNote;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected RiskAssessmentScopeJpaEntity() {
            // Required by JPA.
        }

        public RiskAssessmentScopeJpaEntity(
                String id,
            String riskAssessmentId,
            String scopeType,
            String scopeId,
            String scopeCodeSnapshot,
            String scopeLabelSnapshot,
            String topologySnapshotId,
            Instant operationalPeriodStart,
            Instant operationalPeriodEnd,
            boolean included,
            String scopeNote,
            Instant createdAt
        ) {
            this.id = id;
        this.riskAssessmentId = riskAssessmentId;
        this.scopeType = scopeType;
        this.scopeId = scopeId;
        this.scopeCodeSnapshot = scopeCodeSnapshot;
        this.scopeLabelSnapshot = scopeLabelSnapshot;
        this.topologySnapshotId = topologySnapshotId;
        this.operationalPeriodStart = operationalPeriodStart;
        this.operationalPeriodEnd = operationalPeriodEnd;
        this.included = included;
        this.scopeNote = scopeNote;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String riskAssessmentId() {
        return riskAssessmentId;
    }


    public String scopeType() {
        return scopeType;
    }


    public String scopeId() {
        return scopeId;
    }


    public String scopeCodeSnapshot() {
        return scopeCodeSnapshot;
    }


    public String scopeLabelSnapshot() {
        return scopeLabelSnapshot;
    }


    public String topologySnapshotId() {
        return topologySnapshotId;
    }


    public Instant operationalPeriodStart() {
        return operationalPeriodStart;
    }


    public Instant operationalPeriodEnd() {
        return operationalPeriodEnd;
    }


    public boolean included() {
        return included;
    }


    public String scopeNote() {
        return scopeNote;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
