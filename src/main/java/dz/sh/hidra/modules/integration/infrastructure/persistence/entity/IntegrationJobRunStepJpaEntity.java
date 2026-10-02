/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationJobRunStepJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for IntegrationJobRunStep.
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
     * Database-backed JPA entity for IntegrationJobRunStep.
     */
    @Entity
    @Table(name = "hidra_integration_job_run_step")
    public class IntegrationJobRunStepJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "job_run_id", nullable = false, length = 80)
    private String jobRunId;

    @Column(name = "step_name", nullable = false, length = 120)
    private String stepName;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private JobRunStepStatus status;

    @Column(name = "started_at", nullable = false)
    private Instant startedAt;

    @Column(name = "completed_at", nullable = true)
    private Instant completedAt;

    @Column(name = "processed_count", nullable = false)
    private long processedCount;

    @Column(name = "error_count", nullable = false)
    private long errorCount;

    @Column(name = "details_json", nullable = true, columnDefinition = "jsonb")
    private String detailsJson;

        protected IntegrationJobRunStepJpaEntity() {
            // Required by JPA.
        }

        public IntegrationJobRunStepJpaEntity(
                String id,
            String jobRunId,
            String stepName,
            JobRunStepStatus status,
            Instant startedAt,
            Instant completedAt,
            long processedCount,
            long errorCount,
            String detailsJson
        ) {
            this.id = id;
        this.jobRunId = jobRunId;
        this.stepName = stepName;
        this.status = status;
        this.startedAt = startedAt;
        this.completedAt = completedAt;
        this.processedCount = processedCount;
        this.errorCount = errorCount;
        this.detailsJson = detailsJson;
        }


    public String id() {
        return id;
    }


    public String jobRunId() {
        return jobRunId;
    }


    public String stepName() {
        return stepName;
    }


    public JobRunStepStatus status() {
        return status;
    }


    public Instant startedAt() {
        return startedAt;
    }


    public Instant completedAt() {
        return completedAt;
    }


    public long processedCount() {
        return processedCount;
    }


    public long errorCount() {
        return errorCount;
    }


    public String detailsJson() {
        return detailsJson;
    }

    }
