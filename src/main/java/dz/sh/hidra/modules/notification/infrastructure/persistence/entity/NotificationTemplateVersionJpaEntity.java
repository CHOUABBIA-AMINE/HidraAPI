/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationTemplateVersionJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for NotificationTemplateVersion.
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
     * Database-backed JPA entity for NotificationTemplateVersion.
     */
    @Entity
    @Table(name = "hidra_notification_template_version")
    public class NotificationTemplateVersionJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "template_id", nullable = false, length = 80)
    private String templateId;

    @Column(name = "version_number", nullable = false)
    private int versionNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private NotificationTemplateVersionStatus status;

    @Column(name = "subject_template", nullable = true, length = 1000)
    private String subjectTemplate;

    @Column(name = "body_template", nullable = false, columnDefinition = "text")
    private String bodyTemplate;

    @Enumerated(EnumType.STRING)
    @Column(name = "content_format", nullable = false, length = 40)
    private NotificationContentFormat contentFormat;

    @Column(name = "variable_schema_json", nullable = true, columnDefinition = "jsonb")
    private String variableSchemaJson;

    @Column(name = "created_by_actor_id", nullable = true, length = 80)
    private String createdByActorId;

    @Column(name = "created_by_display_name_snapshot", nullable = true, length = 160)
    private String createdByDisplayNameSnapshot;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "activated_at", nullable = true)
    private Instant activatedAt;

    @Column(name = "retired_at", nullable = true)
    private Instant retiredAt;

        protected NotificationTemplateVersionJpaEntity() {
            // Required by JPA.
        }

        public NotificationTemplateVersionJpaEntity(
                String id,
            String templateId,
            int versionNumber,
            NotificationTemplateVersionStatus status,
            String subjectTemplate,
            String bodyTemplate,
            NotificationContentFormat contentFormat,
            String variableSchemaJson,
            String createdByActorId,
            String createdByDisplayNameSnapshot,
            Instant createdAt,
            Instant activatedAt,
            Instant retiredAt
        ) {
            this.id = id;
        this.templateId = templateId;
        this.versionNumber = versionNumber;
        this.status = status;
        this.subjectTemplate = subjectTemplate;
        this.bodyTemplate = bodyTemplate;
        this.contentFormat = contentFormat;
        this.variableSchemaJson = variableSchemaJson;
        this.createdByActorId = createdByActorId;
        this.createdByDisplayNameSnapshot = createdByDisplayNameSnapshot;
        this.createdAt = createdAt;
        this.activatedAt = activatedAt;
        this.retiredAt = retiredAt;
        }


    public String id() {
        return id;
    }


    public String templateId() {
        return templateId;
    }


    public int versionNumber() {
        return versionNumber;
    }


    public NotificationTemplateVersionStatus status() {
        return status;
    }


    public String subjectTemplate() {
        return subjectTemplate;
    }


    public String bodyTemplate() {
        return bodyTemplate;
    }


    public NotificationContentFormat contentFormat() {
        return contentFormat;
    }


    public String variableSchemaJson() {
        return variableSchemaJson;
    }


    public String createdByActorId() {
        return createdByActorId;
    }


    public String createdByDisplayNameSnapshot() {
        return createdByDisplayNameSnapshot;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant activatedAt() {
        return activatedAt;
    }


    public Instant retiredAt() {
        return retiredAt;
    }

    }
