/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationRequestRecipientJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for NotificationRequestRecipient.
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
     * Database-backed JPA entity for NotificationRequestRecipient.
     */
    @Entity
    @Table(name = "hidra_notification_request_recipient")
    public class NotificationRequestRecipientJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "request_id", nullable = false, length = 80)
    private String requestId;

    @Enumerated(EnumType.STRING)
    @Column(name = "recipient_type", nullable = false, length = 40)
    private NotificationRecipientType recipientType;

    @Column(name = "recipient_reference_id", nullable = false, length = 120)
    private String recipientReferenceId;

    @Column(name = "recipient_display_name_snapshot", nullable = true, length = 160)
    private String recipientDisplayNameSnapshot;

    @Column(name = "recipient_locale", nullable = true, length = 10)
    private String recipientLocale;

    @Column(name = "resolved_from_type", nullable = true, length = 80)
    private String resolvedFromType;

    @Column(name = "resolved_from_reference_id", nullable = true, length = 120)
    private String resolvedFromReferenceId;

    @Enumerated(EnumType.STRING)
    @Column(name = "resolution_status", nullable = false, length = 40)
    private RecipientResolutionStatus resolutionStatus;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected NotificationRequestRecipientJpaEntity() {
            // Required by JPA.
        }

        public NotificationRequestRecipientJpaEntity(
                String id,
            String requestId,
            NotificationRecipientType recipientType,
            String recipientReferenceId,
            String recipientDisplayNameSnapshot,
            String recipientLocale,
            String resolvedFromType,
            String resolvedFromReferenceId,
            RecipientResolutionStatus resolutionStatus,
            Instant createdAt
        ) {
            this.id = id;
        this.requestId = requestId;
        this.recipientType = recipientType;
        this.recipientReferenceId = recipientReferenceId;
        this.recipientDisplayNameSnapshot = recipientDisplayNameSnapshot;
        this.recipientLocale = recipientLocale;
        this.resolvedFromType = resolvedFromType;
        this.resolvedFromReferenceId = resolvedFromReferenceId;
        this.resolutionStatus = resolutionStatus;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String requestId() {
        return requestId;
    }


    public NotificationRecipientType recipientType() {
        return recipientType;
    }


    public String recipientReferenceId() {
        return recipientReferenceId;
    }


    public String recipientDisplayNameSnapshot() {
        return recipientDisplayNameSnapshot;
    }


    public String recipientLocale() {
        return recipientLocale;
    }


    public String resolvedFromType() {
        return resolvedFromType;
    }


    public String resolvedFromReferenceId() {
        return resolvedFromReferenceId;
    }


    public RecipientResolutionStatus resolutionStatus() {
        return resolutionStatus;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
