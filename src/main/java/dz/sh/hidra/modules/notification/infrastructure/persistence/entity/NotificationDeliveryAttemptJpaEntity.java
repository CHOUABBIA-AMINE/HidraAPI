/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationDeliveryAttemptJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for NotificationDeliveryAttempt.
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
     * Database-backed JPA entity for NotificationDeliveryAttempt.
     */
    @Entity
    @Table(name = "hidra_notification_delivery_attempt")
    public class NotificationDeliveryAttemptJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "message_id", nullable = false, length = 80)
    private String messageId;

    @Column(name = "attempt_number", nullable = false)
    private int attemptNumber;

    @Column(name = "channel_id", nullable = false, length = 80)
    private String channelId;

    @Column(name = "provider_reference", nullable = true, length = 255)
    private String providerReference;

    @Column(name = "provider_message_id", nullable = true, length = 255)
    private String providerMessageId;

    @Enumerated(EnumType.STRING)
    @Column(name = "attempt_status", nullable = false, length = 40)
    private DeliveryAttemptStatus attemptStatus;

    @Column(name = "attempted_at", nullable = false)
    private Instant attemptedAt;

    @Column(name = "completed_at", nullable = true)
    private Instant completedAt;

    @Column(name = "failure_code", nullable = true, length = 120)
    private String failureCode;

    @Column(name = "failure_message", nullable = true, length = 2000)
    private String failureMessage;

    @Column(name = "next_retry_at", nullable = true)
    private Instant nextRetryAt;

    @Column(name = "correlation_id", nullable = true, length = 120)
    private String correlationId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected NotificationDeliveryAttemptJpaEntity() {
            // Required by JPA.
        }

        public NotificationDeliveryAttemptJpaEntity(
                String id,
            String messageId,
            int attemptNumber,
            String channelId,
            String providerReference,
            String providerMessageId,
            DeliveryAttemptStatus attemptStatus,
            Instant attemptedAt,
            Instant completedAt,
            String failureCode,
            String failureMessage,
            Instant nextRetryAt,
            String correlationId,
            Instant createdAt
        ) {
            this.id = id;
        this.messageId = messageId;
        this.attemptNumber = attemptNumber;
        this.channelId = channelId;
        this.providerReference = providerReference;
        this.providerMessageId = providerMessageId;
        this.attemptStatus = attemptStatus;
        this.attemptedAt = attemptedAt;
        this.completedAt = completedAt;
        this.failureCode = failureCode;
        this.failureMessage = failureMessage;
        this.nextRetryAt = nextRetryAt;
        this.correlationId = correlationId;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String messageId() {
        return messageId;
    }


    public int attemptNumber() {
        return attemptNumber;
    }


    public String channelId() {
        return channelId;
    }


    public String providerReference() {
        return providerReference;
    }


    public String providerMessageId() {
        return providerMessageId;
    }


    public DeliveryAttemptStatus attemptStatus() {
        return attemptStatus;
    }


    public Instant attemptedAt() {
        return attemptedAt;
    }


    public Instant completedAt() {
        return completedAt;
    }


    public String failureCode() {
        return failureCode;
    }


    public String failureMessage() {
        return failureMessage;
    }


    public Instant nextRetryAt() {
        return nextRetryAt;
    }


    public String correlationId() {
        return correlationId;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
