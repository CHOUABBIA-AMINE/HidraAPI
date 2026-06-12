/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationRetryPolicyJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for NotificationRetryPolicy.
 *
 */
package dz.sh.hidra.modules.notification.infrastructure.persistence.entity;

import dz.sh.hidra.modules.notification.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for NotificationRetryPolicy.
     */
    @Entity
    @Table(name = "hidra_notification_retry_policy")
    public class NotificationRetryPolicyJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "code", nullable = false, length = 120)
    private String code;

    @Column(name = "name_ar", nullable = true, length = 160)
    private String nameAr;

    @Column(name = "name_fr", nullable = false, length = 160)
    private String nameFr;

    @Column(name = "name_en", nullable = true, length = 160)
    private String nameEn;

    @Column(name = "max_attempts", nullable = false)
    private int maxAttempts;

    @Column(name = "initial_delay_seconds", nullable = false)
    private int initialDelaySeconds;

    @Column(name = "max_delay_seconds", nullable = false)
    private int maxDelaySeconds;

    @Enumerated(EnumType.STRING)
    @Column(name = "backoff_strategy", nullable = false, length = 40)
    private NotificationBackoffStrategy backoffStrategy;

    @Column(name = "retry_on_temporary_failure", nullable = false)
    private boolean retryOnTemporaryFailure;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected NotificationRetryPolicyJpaEntity() {
            // Required by JPA.
        }

        public NotificationRetryPolicyJpaEntity(
                String id,
            String code,
            String nameAr,
            String nameFr,
            String nameEn,
            int maxAttempts,
            int initialDelaySeconds,
            int maxDelaySeconds,
            NotificationBackoffStrategy backoffStrategy,
            boolean retryOnTemporaryFailure,
            boolean active,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.code = code;
        this.nameAr = nameAr;
        this.nameFr = nameFr;
        this.nameEn = nameEn;
        this.maxAttempts = maxAttempts;
        this.initialDelaySeconds = initialDelaySeconds;
        this.maxDelaySeconds = maxDelaySeconds;
        this.backoffStrategy = backoffStrategy;
        this.retryOnTemporaryFailure = retryOnTemporaryFailure;
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


    public String nameAr() {
        return nameAr;
    }


    public String nameFr() {
        return nameFr;
    }


    public String nameEn() {
        return nameEn;
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


    public NotificationBackoffStrategy backoffStrategy() {
        return backoffStrategy;
    }


    public boolean retryOnTemporaryFailure() {
        return retryOnTemporaryFailure;
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
