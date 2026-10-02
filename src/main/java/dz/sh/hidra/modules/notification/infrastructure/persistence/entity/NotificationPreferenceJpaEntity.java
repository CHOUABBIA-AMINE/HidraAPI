/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationPreferenceJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for NotificationPreference.
 *
 */
package dz.sh.hidra.modules.notification.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for NotificationPreference.
     */
    @Entity
    @Table(name = "hidra_notification_preference")
    public class NotificationPreferenceJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "recipient_profile_id", nullable = false, length = 80)
    private String recipientProfileId;

    @Column(name = "channel_id", nullable = false, length = 80)
    private String channelId;

    @Column(name = "category_id", nullable = false, length = 80)
    private String categoryId;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @Column(name = "quiet_hours_enabled", nullable = false)
    private boolean quietHoursEnabled;

    @Column(name = "quiet_hours_start", nullable = true, length = 10)
    private String quietHoursStart;

    @Column(name = "quiet_hours_end", nullable = true, length = 10)
    private String quietHoursEnd;

    @Column(name = "timezone", nullable = true, length = 80)
    private String timezone;

    @Column(name = "max_frequency_per_hour", nullable = true)
    private Integer maxFrequencyPerHour;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected NotificationPreferenceJpaEntity() {
            // Required by JPA.
        }

        public NotificationPreferenceJpaEntity(
                String id,
            String recipientProfileId,
            String channelId,
            String categoryId,
            boolean enabled,
            boolean quietHoursEnabled,
            String quietHoursStart,
            String quietHoursEnd,
            String timezone,
            Integer maxFrequencyPerHour,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.recipientProfileId = recipientProfileId;
        this.channelId = channelId;
        this.categoryId = categoryId;
        this.enabled = enabled;
        this.quietHoursEnabled = quietHoursEnabled;
        this.quietHoursStart = quietHoursStart;
        this.quietHoursEnd = quietHoursEnd;
        this.timezone = timezone;
        this.maxFrequencyPerHour = maxFrequencyPerHour;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String recipientProfileId() {
        return recipientProfileId;
    }


    public String channelId() {
        return channelId;
    }


    public String categoryId() {
        return categoryId;
    }


    public boolean enabled() {
        return enabled;
    }


    public boolean quietHoursEnabled() {
        return quietHoursEnabled;
    }


    public String quietHoursStart() {
        return quietHoursStart;
    }


    public String quietHoursEnd() {
        return quietHoursEnd;
    }


    public String timezone() {
        return timezone;
    }


    public Integer maxFrequencyPerHour() {
        return maxFrequencyPerHour;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
