/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationMessageJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for NotificationMessage.
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
     * Database-backed JPA entity for NotificationMessage.
     */
    @Entity
    @Table(name = "hidra_notification_message")
    public class NotificationMessageJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "request_id", nullable = false, length = 80)
    private String requestId;

    @Column(name = "recipient_id", nullable = false, length = 80)
    private String recipientId;

    @Column(name = "channel_id", nullable = false, length = 80)
    private String channelId;

    @Column(name = "template_id", nullable = true, length = 80)
    private String templateId;

    @Column(name = "template_version_id", nullable = true, length = 80)
    private String templateVersionId;

    @Column(name = "locale", nullable = true, length = 10)
    private String locale;

    @Column(name = "subject_rendered", nullable = true, length = 1000)
    private String subjectRendered;

    @Column(name = "body_rendered", nullable = true, columnDefinition = "text")
    private String bodyRendered;

    @Column(name = "short_text_rendered", nullable = true, length = 500)
    private String shortTextRendered;

    @Column(name = "payload_hash", nullable = true, length = 160)
    private String payloadHash;

    @Column(name = "priority_id", nullable = true, length = 80)
    private String priorityId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private NotificationMessageStatus status;

    @Column(name = "scheduled_at", nullable = true)
    private Instant scheduledAt;

    @Column(name = "expires_at", nullable = true)
    private Instant expiresAt;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected NotificationMessageJpaEntity() {
            // Required by JPA.
        }

        public NotificationMessageJpaEntity(
                String id,
            String requestId,
            String recipientId,
            String channelId,
            String templateId,
            String templateVersionId,
            String locale,
            String subjectRendered,
            String bodyRendered,
            String shortTextRendered,
            String payloadHash,
            String priorityId,
            NotificationMessageStatus status,
            Instant scheduledAt,
            Instant expiresAt,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.requestId = requestId;
        this.recipientId = recipientId;
        this.channelId = channelId;
        this.templateId = templateId;
        this.templateVersionId = templateVersionId;
        this.locale = locale;
        this.subjectRendered = subjectRendered;
        this.bodyRendered = bodyRendered;
        this.shortTextRendered = shortTextRendered;
        this.payloadHash = payloadHash;
        this.priorityId = priorityId;
        this.status = status;
        this.scheduledAt = scheduledAt;
        this.expiresAt = expiresAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String requestId() {
        return requestId;
    }


    public String recipientId() {
        return recipientId;
    }


    public String channelId() {
        return channelId;
    }


    public String templateId() {
        return templateId;
    }


    public String templateVersionId() {
        return templateVersionId;
    }


    public String locale() {
        return locale;
    }


    public String subjectRendered() {
        return subjectRendered;
    }


    public String bodyRendered() {
        return bodyRendered;
    }


    public String shortTextRendered() {
        return shortTextRendered;
    }


    public String payloadHash() {
        return payloadHash;
    }


    public String priorityId() {
        return priorityId;
    }


    public NotificationMessageStatus status() {
        return status;
    }


    public Instant scheduledAt() {
        return scheduledAt;
    }


    public Instant expiresAt() {
        return expiresAt;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
