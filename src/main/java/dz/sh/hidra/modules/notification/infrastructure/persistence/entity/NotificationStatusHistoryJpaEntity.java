/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationStatusHistoryJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for NotificationStatusHistory.
 *
 */
package dz.sh.hidra.modules.notification.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for NotificationStatusHistory.
     */
    @Entity
    @Table(name = "hidra_notification_status_history")
    public class NotificationStatusHistoryJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "entity_type", nullable = false, length = 80)
    private String entityType;

    @Column(name = "entity_id", nullable = false, length = 80)
    private String entityId;

    @Column(name = "from_status", nullable = true, length = 80)
    private String fromStatus;

    @Column(name = "to_status", nullable = false, length = 80)
    private String toStatus;

    @Column(name = "reason_id", nullable = true, length = 80)
    private String reasonId;

    @Column(name = "reason_text", nullable = true, length = 1000)
    private String reasonText;

    @Column(name = "changed_by_actor_id", nullable = true, length = 80)
    private String changedByActorId;

    @Column(name = "changed_by_display_name_snapshot", nullable = true, length = 160)
    private String changedByDisplayNameSnapshot;

    @Column(name = "changed_at", nullable = false)
    private Instant changedAt;

    @Column(name = "correlation_id", nullable = true, length = 120)
    private String correlationId;

        protected NotificationStatusHistoryJpaEntity() {
            // Required by JPA.
        }

        public NotificationStatusHistoryJpaEntity(
                String id,
            String entityType,
            String entityId,
            String fromStatus,
            String toStatus,
            String reasonId,
            String reasonText,
            String changedByActorId,
            String changedByDisplayNameSnapshot,
            Instant changedAt,
            String correlationId
        ) {
            this.id = id;
        this.entityType = entityType;
        this.entityId = entityId;
        this.fromStatus = fromStatus;
        this.toStatus = toStatus;
        this.reasonId = reasonId;
        this.reasonText = reasonText;
        this.changedByActorId = changedByActorId;
        this.changedByDisplayNameSnapshot = changedByDisplayNameSnapshot;
        this.changedAt = changedAt;
        this.correlationId = correlationId;
        }


    public String id() {
        return id;
    }


    public String entityType() {
        return entityType;
    }


    public String entityId() {
        return entityId;
    }


    public String fromStatus() {
        return fromStatus;
    }


    public String toStatus() {
        return toStatus;
    }


    public String reasonId() {
        return reasonId;
    }


    public String reasonText() {
        return reasonText;
    }


    public String changedByActorId() {
        return changedByActorId;
    }


    public String changedByDisplayNameSnapshot() {
        return changedByDisplayNameSnapshot;
    }


    public Instant changedAt() {
        return changedAt;
    }


    public String correlationId() {
        return correlationId;
    }

    }
