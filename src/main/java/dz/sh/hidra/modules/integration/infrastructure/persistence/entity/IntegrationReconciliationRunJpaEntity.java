/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationReconciliationRunJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for IntegrationReconciliationRun.
 *
 */
package dz.sh.hidra.modules.integration.infrastructure.persistence.entity;

import dz.sh.hidra.modules.integration.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for IntegrationReconciliationRun.
     */
    @Entity
    @Table(name = "hidra_integration_reconciliation_run")
    public class IntegrationReconciliationRunJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "external_system_id", nullable = false, length = 80)
    private String externalSystemId;

    @Column(name = "job_definition_id", nullable = true, length = 80)
    private String jobDefinitionId;

    @Column(name = "target_module", nullable = false, length = 80)
    private String targetModule;

    @Column(name = "target_type_code", nullable = false, length = 120)
    private String targetTypeCode;

    @Column(name = "reconciliation_period_start", nullable = true)
    private Instant reconciliationPeriodStart;

    @Column(name = "reconciliation_period_end", nullable = true)
    private Instant reconciliationPeriodEnd;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private ReconciliationRunStatus status;

    @Column(name = "hidra_count", nullable = false)
    private long hidraCount;

    @Column(name = "external_count", nullable = false)
    private long externalCount;

    @Column(name = "matched_count", nullable = false)
    private long matchedCount;

    @Column(name = "missing_in_hidra_count", nullable = false)
    private long missingInHidraCount;

    @Column(name = "missing_externally_count", nullable = false)
    private long missingExternallyCount;

    @Column(name = "mismatch_count", nullable = false)
    private long mismatchCount;

    @Column(name = "started_at", nullable = false)
    private Instant startedAt;

    @Column(name = "completed_at", nullable = true)
    private Instant completedAt;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected IntegrationReconciliationRunJpaEntity() {
            // Required by JPA.
        }

        public IntegrationReconciliationRunJpaEntity(
                String id,
            String externalSystemId,
            String jobDefinitionId,
            String targetModule,
            String targetTypeCode,
            Instant reconciliationPeriodStart,
            Instant reconciliationPeriodEnd,
            ReconciliationRunStatus status,
            long hidraCount,
            long externalCount,
            long matchedCount,
            long missingInHidraCount,
            long missingExternallyCount,
            long mismatchCount,
            Instant startedAt,
            Instant completedAt,
            Instant createdAt
        ) {
            this.id = id;
        this.externalSystemId = externalSystemId;
        this.jobDefinitionId = jobDefinitionId;
        this.targetModule = targetModule;
        this.targetTypeCode = targetTypeCode;
        this.reconciliationPeriodStart = reconciliationPeriodStart;
        this.reconciliationPeriodEnd = reconciliationPeriodEnd;
        this.status = status;
        this.hidraCount = hidraCount;
        this.externalCount = externalCount;
        this.matchedCount = matchedCount;
        this.missingInHidraCount = missingInHidraCount;
        this.missingExternallyCount = missingExternallyCount;
        this.mismatchCount = mismatchCount;
        this.startedAt = startedAt;
        this.completedAt = completedAt;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String externalSystemId() {
        return externalSystemId;
    }


    public String jobDefinitionId() {
        return jobDefinitionId;
    }


    public String targetModule() {
        return targetModule;
    }


    public String targetTypeCode() {
        return targetTypeCode;
    }


    public Instant reconciliationPeriodStart() {
        return reconciliationPeriodStart;
    }


    public Instant reconciliationPeriodEnd() {
        return reconciliationPeriodEnd;
    }


    public ReconciliationRunStatus status() {
        return status;
    }


    public long hidraCount() {
        return hidraCount;
    }


    public long externalCount() {
        return externalCount;
    }


    public long matchedCount() {
        return matchedCount;
    }


    public long missingInHidraCount() {
        return missingInHidraCount;
    }


    public long missingExternallyCount() {
        return missingExternallyCount;
    }


    public long mismatchCount() {
        return mismatchCount;
    }


    public Instant startedAt() {
        return startedAt;
    }


    public Instant completedAt() {
        return completedAt;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
