/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakDetectionRunJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for LeakDetectionRun.
 *
 */
package dz.sh.hidra.modules.leakdetection.infrastructure.persistence.entity;

import dz.sh.hidra.modules.leakdetection.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for LeakDetectionRun.
     */
    @Entity
    @Table(name = "hidra_leak_detection_run")
    public class LeakDetectionRunJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "profile_id", nullable = false, length = 80)
    private String profileId;

    @Column(name = "method_id", nullable = false, length = 80)
    private String methodId;

    @Column(name = "run_code", nullable = false, length = 80)
    private String runCode;

    @Column(name = "evaluation_start", nullable = false)
    private Instant evaluationStart;

    @Column(name = "evaluation_end", nullable = true)
    private Instant evaluationEnd;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private LeakDetectionRunStatus status;

    @Column(name = "candidate_count", nullable = false)
    private int candidateCount;

    @Column(name = "failure_reason", nullable = true, columnDefinition = "text")
    private String failureReason;

    @Column(name = "correlation_id", nullable = true, length = 80)
    private String correlationId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected LeakDetectionRunJpaEntity() {
            // Required by JPA.
        }

        public LeakDetectionRunJpaEntity(
                String id,
            String profileId,
            String methodId,
            String runCode,
            Instant evaluationStart,
            Instant evaluationEnd,
            LeakDetectionRunStatus status,
            int candidateCount,
            String failureReason,
            String correlationId,
            Instant createdAt
        ) {
            this.id = id;
        this.profileId = profileId;
        this.methodId = methodId;
        this.runCode = runCode;
        this.evaluationStart = evaluationStart;
        this.evaluationEnd = evaluationEnd;
        this.status = status;
        this.candidateCount = candidateCount;
        this.failureReason = failureReason;
        this.correlationId = correlationId;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String profileId() {
        return profileId;
    }


    public String methodId() {
        return methodId;
    }


    public String runCode() {
        return runCode;
    }


    public Instant evaluationStart() {
        return evaluationStart;
    }


    public Instant evaluationEnd() {
        return evaluationEnd;
    }


    public LeakDetectionRunStatus status() {
        return status;
    }


    public int candidateCount() {
        return candidateCount;
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
