/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationSuppressionRuleJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for NotificationSuppressionRule.
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
     * Database-backed JPA entity for NotificationSuppressionRule.
     */
    @Entity
    @Table(name = "hidra_notification_suppression_rule")
    public class NotificationSuppressionRuleJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "code", nullable = false, length = 120)
    private String code;

    @Column(name = "source_module", nullable = true, length = 80)
    private String sourceModule;

    @Column(name = "category_id", nullable = true, length = 80)
    private String categoryId;

    @Column(name = "priority_id", nullable = true, length = 80)
    private String priorityId;

    @Column(name = "channel_id", nullable = true, length = 80)
    private String channelId;

    @Enumerated(EnumType.STRING)
    @Column(name = "recipient_type", nullable = true, length = 40)
    private NotificationRecipientType recipientType;

    @Column(name = "recipient_reference_id", nullable = true, length = 120)
    private String recipientReferenceId;

    @Column(name = "reason_id", nullable = false, length = 80)
    private String reasonId;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "valid_from", nullable = true)
    private Instant validFrom;

    @Column(name = "valid_to", nullable = true)
    private Instant validTo;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected NotificationSuppressionRuleJpaEntity() {
            // Required by JPA.
        }

        public NotificationSuppressionRuleJpaEntity(
                String id,
            String code,
            String sourceModule,
            String categoryId,
            String priorityId,
            String channelId,
            NotificationRecipientType recipientType,
            String recipientReferenceId,
            String reasonId,
            boolean active,
            Instant validFrom,
            Instant validTo,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.code = code;
        this.sourceModule = sourceModule;
        this.categoryId = categoryId;
        this.priorityId = priorityId;
        this.channelId = channelId;
        this.recipientType = recipientType;
        this.recipientReferenceId = recipientReferenceId;
        this.reasonId = reasonId;
        this.active = active;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String code() {
        return code;
    }


    public String sourceModule() {
        return sourceModule;
    }


    public String categoryId() {
        return categoryId;
    }


    public String priorityId() {
        return priorityId;
    }


    public String channelId() {
        return channelId;
    }


    public NotificationRecipientType recipientType() {
        return recipientType;
    }


    public String recipientReferenceId() {
        return recipientReferenceId;
    }


    public String reasonId() {
        return reasonId;
    }


    public boolean active() {
        return active;
    }


    public Instant validFrom() {
        return validFrom;
    }


    public Instant validTo() {
        return validTo;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
