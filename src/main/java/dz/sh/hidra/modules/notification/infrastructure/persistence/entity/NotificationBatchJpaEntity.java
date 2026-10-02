/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationBatchJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for NotificationBatch.
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
     * Database-backed JPA entity for NotificationBatch.
     */
    @Entity
    @Table(name = "hidra_notification_batch")
    public class NotificationBatchJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "request_id", nullable = true, length = 80)
    private String requestId;

    @Enumerated(EnumType.STRING)
    @Column(name = "batch_type", nullable = false, length = 40)
    private NotificationBatchType batchType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private NotificationBatchStatus status;

    @Column(name = "message_count", nullable = false)
    private int messageCount;

    @Column(name = "success_count", nullable = false)
    private int successCount;

    @Column(name = "failure_count", nullable = false)
    private int failureCount;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "completed_at", nullable = true)
    private Instant completedAt;

        protected NotificationBatchJpaEntity() {
            // Required by JPA.
        }

        public NotificationBatchJpaEntity(
                String id,
            String requestId,
            NotificationBatchType batchType,
            NotificationBatchStatus status,
            int messageCount,
            int successCount,
            int failureCount,
            Instant createdAt,
            Instant completedAt
        ) {
            this.id = id;
        this.requestId = requestId;
        this.batchType = batchType;
        this.status = status;
        this.messageCount = messageCount;
        this.successCount = successCount;
        this.failureCount = failureCount;
        this.createdAt = createdAt;
        this.completedAt = completedAt;
        }


    public String id() {
        return id;
    }


    public String requestId() {
        return requestId;
    }


    public NotificationBatchType batchType() {
        return batchType;
    }


    public NotificationBatchStatus status() {
        return status;
    }


    public int messageCount() {
        return messageCount;
    }


    public int successCount() {
        return successCount;
    }


    public int failureCount() {
        return failureCount;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant completedAt() {
        return completedAt;
    }

    }
