/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationRetryPolicyJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for IntegrationRetryPolicy.
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
     * Database-backed JPA entity for IntegrationRetryPolicy.
     */
    @Entity
    @Table(name = "hidra_integration_retry_policy")
    public class IntegrationRetryPolicyJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "code", nullable = false, length = 120)
    private String code;

    @Column(name = "max_attempts", nullable = false)
    private int maxAttempts;

    @Column(name = "initial_delay_seconds", nullable = false)
    private int initialDelaySeconds;

    @Column(name = "max_delay_seconds", nullable = false)
    private int maxDelaySeconds;

    @Enumerated(EnumType.STRING)
    @Column(name = "backoff_strategy", nullable = false, length = 40)
    private RetryBackoffStrategy backoffStrategy;

    @Column(name = "retryable_error_codes", nullable = true, columnDefinition = "jsonb")
    private String retryableErrorCodes;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected IntegrationRetryPolicyJpaEntity() {
            // Required by JPA.
        }

        public IntegrationRetryPolicyJpaEntity(
                String id,
            String code,
            int maxAttempts,
            int initialDelaySeconds,
            int maxDelaySeconds,
            RetryBackoffStrategy backoffStrategy,
            String retryableErrorCodes,
            boolean active,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.code = code;
        this.maxAttempts = maxAttempts;
        this.initialDelaySeconds = initialDelaySeconds;
        this.maxDelaySeconds = maxDelaySeconds;
        this.backoffStrategy = backoffStrategy;
        this.retryableErrorCodes = retryableErrorCodes;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String code() {
        return code;
    }


    public int maxAttempts() {
        return maxAttempts;
    }


    public int initialDelaySeconds() {
        return initialDelaySeconds;
    }


    public int maxDelaySeconds() {
        return maxDelaySeconds;
    }


    public RetryBackoffStrategy backoffStrategy() {
        return backoffStrategy;
    }


    public String retryableErrorCodes() {
        return retryableErrorCodes;
    }


    public boolean active() {
        return active;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
