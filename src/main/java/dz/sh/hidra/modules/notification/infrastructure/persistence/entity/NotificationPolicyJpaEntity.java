/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationPolicyJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for NotificationPolicy.
 *
 */
package dz.sh.hidra.modules.notification.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for NotificationPolicy.
     */
    @Entity
    @Table(name = "hidra_notification_policy")
    public class NotificationPolicyJpaEntity {

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

    @Column(name = "source_module", nullable = false, length = 80)
    private String sourceModule;

    @Column(name = "category_id", nullable = false, length = 80)
    private String categoryId;

    @Column(name = "priority_id", nullable = true, length = 80)
    private String priorityId;

    @Column(name = "default_template_id", nullable = true, length = 80)
    private String defaultTemplateId;

    @Column(name = "default_channel_id", nullable = true, length = 80)
    private String defaultChannelId;

    @Column(name = "recipient_resolution_mode", nullable = false, length = 80)
    private String recipientResolutionMode;

    @Column(name = "allow_preference_override", nullable = false)
    private boolean allowPreferenceOverride;

    @Column(name = "allow_quiet_hour_delay", nullable = false)
    private boolean allowQuietHourDelay;

    @Column(name = "requires_acknowledgement", nullable = false)
    private boolean requiresAcknowledgement;

    @Column(name = "max_retry_count", nullable = true)
    private Integer maxRetryCount;

    @Column(name = "retry_policy_id", nullable = true, length = 80)
    private String retryPolicyId;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected NotificationPolicyJpaEntity() {
            // Required by JPA.
        }

        public NotificationPolicyJpaEntity(
                String id,
            String code,
            String nameAr,
            String nameFr,
            String nameEn,
            String sourceModule,
            String categoryId,
            String priorityId,
            String defaultTemplateId,
            String defaultChannelId,
            String recipientResolutionMode,
            boolean allowPreferenceOverride,
            boolean allowQuietHourDelay,
            boolean requiresAcknowledgement,
            Integer maxRetryCount,
            String retryPolicyId,
            boolean active,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.code = code;
        this.nameAr = nameAr;
        this.nameFr = nameFr;
        this.nameEn = nameEn;
        this.sourceModule = sourceModule;
        this.categoryId = categoryId;
        this.priorityId = priorityId;
        this.defaultTemplateId = defaultTemplateId;
        this.defaultChannelId = defaultChannelId;
        this.recipientResolutionMode = recipientResolutionMode;
        this.allowPreferenceOverride = allowPreferenceOverride;
        this.allowQuietHourDelay = allowQuietHourDelay;
        this.requiresAcknowledgement = requiresAcknowledgement;
        this.maxRetryCount = maxRetryCount;
        this.retryPolicyId = retryPolicyId;
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


    public String sourceModule() {
        return sourceModule;
    }


    public String categoryId() {
        return categoryId;
    }


    public String priorityId() {
        return priorityId;
    }


    public String defaultTemplateId() {
        return defaultTemplateId;
    }


    public String defaultChannelId() {
        return defaultChannelId;
    }


    public String recipientResolutionMode() {
        return recipientResolutionMode;
    }


    public boolean allowPreferenceOverride() {
        return allowPreferenceOverride;
    }


    public boolean allowQuietHourDelay() {
        return allowQuietHourDelay;
    }


    public boolean requiresAcknowledgement() {
        return requiresAcknowledgement;
    }


    public Integer maxRetryCount() {
        return maxRetryCount;
    }


    public String retryPolicyId() {
        return retryPolicyId;
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
