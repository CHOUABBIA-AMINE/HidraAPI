/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationRetryAttemptJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for IntegrationRetryAttempt.
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
     * Database-backed JPA entity for IntegrationRetryAttempt.
     */
    @Entity
    @Table(name = "hidra_integration_retry_attempt")
    public class IntegrationRetryAttemptJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "retry_policy_id", nullable = true, length = 80)
    private String retryPolicyId;

    @Column(name = "target_record_type", nullable = false, length = 80)
    private String targetRecordType;

    @Column(name = "target_record_id", nullable = false, length = 120)
    private String targetRecordId;

    @Column(name = "attempt_number", nullable = false)
    private int attemptNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private RetryAttemptStatus status;

    @Column(name = "scheduled_at", nullable = false)
    private Instant scheduledAt;

    @Column(name = "started_at", nullable = true)
    private Instant startedAt;

    @Column(name = "completed_at", nullable = true)
    private Instant completedAt;

    @Column(name = "error_code", nullable = true, length = 120)
    private String errorCode;

    @Column(name = "error_message", nullable = true, length = 2000)
    private String errorMessage;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected IntegrationRetryAttemptJpaEntity() {
            // Required by JPA.
        }

        public IntegrationRetryAttemptJpaEntity(
                String id,
            String retryPolicyId,
            String targetRecordType,
            String targetRecordId,
            int attemptNumber,
            RetryAttemptStatus status,
            Instant scheduledAt,
            Instant startedAt,
            Instant completedAt,
            String errorCode,
            String errorMessage,
            Instant createdAt
        ) {
            this.id = id;
        this.retryPolicyId = retryPolicyId;
        this.targetRecordType = targetRecordType;
        this.targetRecordId = targetRecordId;
        this.attemptNumber = attemptNumber;
        this.status = status;
        this.scheduledAt = scheduledAt;
        this.startedAt = startedAt;
        this.completedAt = completedAt;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String retryPolicyId() {
        return retryPolicyId;
    }


    public String targetRecordType() {
        return targetRecordType;
    }


    public String targetRecordId() {
        return targetRecordId;
    }


    public int attemptNumber() {
        return attemptNumber;
    }


    public RetryAttemptStatus status() {
        return status;
    }


    public Instant scheduledAt() {
        return scheduledAt;
    }


    public Instant startedAt() {
        return startedAt;
    }


    public Instant completedAt() {
        return completedAt;
    }


    public String errorCode() {
        return errorCode;
    }


    public String errorMessage() {
        return errorMessage;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
