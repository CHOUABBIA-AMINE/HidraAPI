/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationAcknowledgementJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for NotificationAcknowledgement.
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
     * Database-backed JPA entity for NotificationAcknowledgement.
     */
    @Entity
    @Table(name = "hidra_notification_acknowledgement")
    public class NotificationAcknowledgementJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "message_id", nullable = false, length = 80)
    private String messageId;

    @Column(name = "recipient_id", nullable = false, length = 80)
    private String recipientId;

    @Enumerated(EnumType.STRING)
    @Column(name = "acknowledgement_status", nullable = false, length = 40)
    private NotificationAcknowledgementStatus acknowledgementStatus;

    @Column(name = "acknowledged_by_actor_id", nullable = true, length = 80)
    private String acknowledgedByActorId;

    @Column(name = "acknowledged_by_display_name_snapshot", nullable = true, length = 160)
    private String acknowledgedByDisplayNameSnapshot;

    @Column(name = "acknowledged_at", nullable = true)
    private Instant acknowledgedAt;

    @Column(name = "comment_text", nullable = true, length = 2000)
    private String commentText;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected NotificationAcknowledgementJpaEntity() {
            // Required by JPA.
        }

        public NotificationAcknowledgementJpaEntity(
                String id,
            String messageId,
            String recipientId,
            NotificationAcknowledgementStatus acknowledgementStatus,
            String acknowledgedByActorId,
            String acknowledgedByDisplayNameSnapshot,
            Instant acknowledgedAt,
            String commentText,
            Instant createdAt
        ) {
            this.id = id;
        this.messageId = messageId;
        this.recipientId = recipientId;
        this.acknowledgementStatus = acknowledgementStatus;
        this.acknowledgedByActorId = acknowledgedByActorId;
        this.acknowledgedByDisplayNameSnapshot = acknowledgedByDisplayNameSnapshot;
        this.acknowledgedAt = acknowledgedAt;
        this.commentText = commentText;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String messageId() {
        return messageId;
    }


    public String recipientId() {
        return recipientId;
    }


    public NotificationAcknowledgementStatus acknowledgementStatus() {
        return acknowledgementStatus;
    }


    public String acknowledgedByActorId() {
        return acknowledgedByActorId;
    }


    public String acknowledgedByDisplayNameSnapshot() {
        return acknowledgedByDisplayNameSnapshot;
    }


    public Instant acknowledgedAt() {
        return acknowledgedAt;
    }


    public String commentText() {
        return commentText;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
