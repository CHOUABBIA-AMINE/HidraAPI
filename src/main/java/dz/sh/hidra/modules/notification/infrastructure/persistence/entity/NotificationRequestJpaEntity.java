/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationRequestJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for NotificationRequest.
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
     * Database-backed JPA entity for NotificationRequest.
     */
    @Entity
    @Table(name = "hidra_notification_request")
    public class NotificationRequestJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "source_module", nullable = false, length = 80)
    private String sourceModule;

    @Column(name = "source_event_type", nullable = false, length = 120)
    private String sourceEventType;

    @Column(name = "source_event_id", nullable = false, length = 120)
    private String sourceEventId;

    @Column(name = "target_type", nullable = true, length = 120)
    private String targetType;

    @Column(name = "target_id", nullable = true, length = 120)
    private String targetId;

    @Column(name = "target_code_snapshot", nullable = true, length = 120)
    private String targetCodeSnapshot;

    @Column(name = "target_label_snapshot", nullable = true, length = 240)
    private String targetLabelSnapshot;

    @Column(name = "category_id", nullable = false, length = 80)
    private String categoryId;

    @Column(name = "priority_id", nullable = true, length = 80)
    private String priorityId;

    @Column(name = "policy_id", nullable = true, length = 80)
    private String policyId;

    @Column(name = "template_id", nullable = true, length = 80)
    private String templateId;

    @Column(name = "template_version_id", nullable = true, length = 80)
    private String templateVersionId;

    @Column(name = "requested_by_actor_id", nullable = true, length = 80)
    private String requestedByActorId;

    @Column(name = "requested_by_display_name_snapshot", nullable = true, length = 160)
    private String requestedByDisplayNameSnapshot;

    @Column(name = "requested_at", nullable = false)
    private Instant requestedAt;

    @Column(name = "correlation_id", nullable = true, length = 120)
    private String correlationId;

    @Column(name = "request_id", nullable = true, length = 120)
    private String requestId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private NotificationRequestStatus status;

    @Column(name = "expires_at", nullable = true)
    private Instant expiresAt;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected NotificationRequestJpaEntity() {
            // Required by JPA.
        }

        public NotificationRequestJpaEntity(
                String id,
            String sourceModule,
            String sourceEventType,
            String sourceEventId,
            String targetType,
            String targetId,
            String targetCodeSnapshot,
            String targetLabelSnapshot,
            String categoryId,
            String priorityId,
            String policyId,
            String templateId,
            String templateVersionId,
            String requestedByActorId,
            String requestedByDisplayNameSnapshot,
            Instant requestedAt,
            String correlationId,
            String requestId,
            NotificationRequestStatus status,
            Instant expiresAt,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.sourceModule = sourceModule;
        this.sourceEventType = sourceEventType;
        this.sourceEventId = sourceEventId;
        this.targetType = targetType;
        this.targetId = targetId;
        this.targetCodeSnapshot = targetCodeSnapshot;
        this.targetLabelSnapshot = targetLabelSnapshot;
        this.categoryId = categoryId;
        this.priorityId = priorityId;
        this.policyId = policyId;
        this.templateId = templateId;
        this.templateVersionId = templateVersionId;
        this.requestedByActorId = requestedByActorId;
        this.requestedByDisplayNameSnapshot = requestedByDisplayNameSnapshot;
        this.requestedAt = requestedAt;
        this.correlationId = correlationId;
        this.requestId = requestId;
        this.status = status;
        this.expiresAt = expiresAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String sourceModule() {
        return sourceModule;
    }


    public String sourceEventType() {
        return sourceEventType;
    }


    public String sourceEventId() {
        return sourceEventId;
    }


    public String targetType() {
        return targetType;
    }


    public String targetId() {
        return targetId;
    }


    public String targetCodeSnapshot() {
        return targetCodeSnapshot;
    }


    public String targetLabelSnapshot() {
        return targetLabelSnapshot;
    }


    public String categoryId() {
        return categoryId;
    }


    public String priorityId() {
        return priorityId;
    }


    public String policyId() {
        return policyId;
    }


    public String templateId() {
        return templateId;
    }


    public String templateVersionId() {
        return templateVersionId;
    }


    public String requestedByActorId() {
        return requestedByActorId;
    }


    public String requestedByDisplayNameSnapshot() {
        return requestedByDisplayNameSnapshot;
    }


    public Instant requestedAt() {
        return requestedAt;
    }


    public String correlationId() {
        return correlationId;
    }


    public String requestId() {
        return requestId;
    }


    public NotificationRequestStatus status() {
        return status;
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
