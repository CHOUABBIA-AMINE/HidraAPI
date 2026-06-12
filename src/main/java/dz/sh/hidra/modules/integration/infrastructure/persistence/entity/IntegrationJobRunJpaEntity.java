/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationJobRunJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for IntegrationJobRun.
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
     * Database-backed JPA entity for IntegrationJobRun.
     */
    @Entity
    @Table(name = "hidra_integration_job_run")
    public class IntegrationJobRunJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "job_definition_id", nullable = false, length = 80)
    private String jobDefinitionId;

    @Column(name = "run_number", nullable = false)
    private long runNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "trigger_type", nullable = false, length = 40)
    private JobTriggerType triggerType;

    @Column(name = "triggered_by_actor_id", nullable = true, length = 80)
    private String triggeredByActorId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private JobRunStatus status;

    @Column(name = "correlation_id", nullable = true, length = 120)
    private String correlationId;

    @Column(name = "started_at", nullable = false)
    private Instant startedAt;

    @Column(name = "completed_at", nullable = true)
    private Instant completedAt;

    @Column(name = "received_count", nullable = false)
    private long receivedCount;

    @Column(name = "mapped_count", nullable = false)
    private long mappedCount;

    @Column(name = "accepted_count", nullable = false)
    private long acceptedCount;

    @Column(name = "rejected_count", nullable = false)
    private long rejectedCount;

    @Column(name = "dead_letter_count", nullable = false)
    private long deadLetterCount;

    @Column(name = "retry_count", nullable = false)
    private long retryCount;

    @Column(name = "failure_reason", nullable = true, length = 2000)
    private String failureReason;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected IntegrationJobRunJpaEntity() {
            // Required by JPA.
        }

        public IntegrationJobRunJpaEntity(
                String id,
            String jobDefinitionId,
            long runNumber,
            JobTriggerType triggerType,
            String triggeredByActorId,
            JobRunStatus status,
            String correlationId,
            Instant startedAt,
            Instant completedAt,
            long receivedCount,
            long mappedCount,
            long acceptedCount,
            long rejectedCount,
            long deadLetterCount,
            long retryCount,
            String failureReason,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.jobDefinitionId = jobDefinitionId;
        this.runNumber = runNumber;
        this.triggerType = triggerType;
        this.triggeredByActorId = triggeredByActorId;
        this.status = status;
        this.correlationId = correlationId;
        this.startedAt = startedAt;
        this.completedAt = completedAt;
        this.receivedCount = receivedCount;
        this.mappedCount = mappedCount;
        this.acceptedCount = acceptedCount;
        this.rejectedCount = rejectedCount;
        this.deadLetterCount = deadLetterCount;
        this.retryCount = retryCount;
        this.failureReason = failureReason;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String jobDefinitionId() {
        return jobDefinitionId;
    }


    public long runNumber() {
        return runNumber;
    }


    public JobTriggerType triggerType() {
        return triggerType;
    }


    public String triggeredByActorId() {
        return triggeredByActorId;
    }


    public JobRunStatus status() {
        return status;
    }


    public String correlationId() {
        return correlationId;
    }


    public Instant startedAt() {
        return startedAt;
    }


    public Instant completedAt() {
        return completedAt;
    }


    public long receivedCount() {
        return receivedCount;
    }


    public long mappedCount() {
        return mappedCount;
    }


    public long acceptedCount() {
        return acceptedCount;
    }


    public long rejectedCount() {
        return rejectedCount;
    }


    public long deadLetterCount() {
        return deadLetterCount;
    }


    public long retryCount() {
        return retryCount;
    }


    public String failureReason() {
        return failureReason;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
