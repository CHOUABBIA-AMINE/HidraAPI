/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationRecipientProfileJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for NotificationRecipientProfile.
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
     * Database-backed JPA entity for NotificationRecipientProfile.
     */
    @Entity
    @Table(name = "hidra_notification_recipient_profile")
    public class NotificationRecipientProfileJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(name = "recipient_type", nullable = false, length = 40)
    private NotificationRecipientType recipientType;

    @Column(name = "recipient_reference_id", nullable = false, length = 120)
    private String recipientReferenceId;

    @Column(name = "recipient_code_snapshot", nullable = true, length = 120)
    private String recipientCodeSnapshot;

    @Column(name = "recipient_display_name_snapshot", nullable = true, length = 160)
    private String recipientDisplayNameSnapshot;

    @Column(name = "preferred_locale", nullable = true, length = 10)
    private String preferredLocale;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected NotificationRecipientProfileJpaEntity() {
            // Required by JPA.
        }

        public NotificationRecipientProfileJpaEntity(
                String id,
            NotificationRecipientType recipientType,
            String recipientReferenceId,
            String recipientCodeSnapshot,
            String recipientDisplayNameSnapshot,
            String preferredLocale,
            boolean active,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.recipientType = recipientType;
        this.recipientReferenceId = recipientReferenceId;
        this.recipientCodeSnapshot = recipientCodeSnapshot;
        this.recipientDisplayNameSnapshot = recipientDisplayNameSnapshot;
        this.preferredLocale = preferredLocale;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public NotificationRecipientType recipientType() {
        return recipientType;
    }


    public String recipientReferenceId() {
        return recipientReferenceId;
    }


    public String recipientCodeSnapshot() {
        return recipientCodeSnapshot;
    }


    public String recipientDisplayNameSnapshot() {
        return recipientDisplayNameSnapshot;
    }


    public String preferredLocale() {
        return preferredLocale;
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
