/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationScheduleJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for NotificationSchedule.
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
     * Database-backed JPA entity for NotificationSchedule.
     */
    @Entity
    @Table(name = "hidra_notification_schedule")
    public class NotificationScheduleJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "request_id", nullable = true, length = 80)
    private String requestId;

    @Column(name = "message_id", nullable = true, length = 80)
    private String messageId;

    @Enumerated(EnumType.STRING)
    @Column(name = "schedule_type", nullable = false, length = 40)
    private NotificationScheduleType scheduleType;

    @Column(name = "scheduled_at", nullable = false)
    private Instant scheduledAt;

    @Column(name = "timezone", nullable = true, length = 80)
    private String timezone;

    @Column(name = "recurrence_rule", nullable = true, length = 500)
    private String recurrenceRule;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private NotificationScheduleStatus status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected NotificationScheduleJpaEntity() {
            // Required by JPA.
        }

        public NotificationScheduleJpaEntity(
                String id,
            String requestId,
            String messageId,
            NotificationScheduleType scheduleType,
            Instant scheduledAt,
            String timezone,
            String recurrenceRule,
            NotificationScheduleStatus status,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.requestId = requestId;
        this.messageId = messageId;
        this.scheduleType = scheduleType;
        this.scheduledAt = scheduledAt;
        this.timezone = timezone;
        this.recurrenceRule = recurrenceRule;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String requestId() {
        return requestId;
    }


    public String messageId() {
        return messageId;
    }


    public NotificationScheduleType scheduleType() {
        return scheduleType;
    }


    public Instant scheduledAt() {
        return scheduledAt;
    }


    public String timezone() {
        return timezone;
    }


    public String recurrenceRule() {
        return recurrenceRule;
    }


    public NotificationScheduleStatus status() {
        return status;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
