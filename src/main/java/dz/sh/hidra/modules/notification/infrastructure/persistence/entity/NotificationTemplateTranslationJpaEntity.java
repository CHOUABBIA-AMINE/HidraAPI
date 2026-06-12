/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationTemplateTranslationJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for NotificationTemplateTranslation.
 *
 */
package dz.sh.hidra.modules.notification.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for NotificationTemplateTranslation.
     */
    @Entity
    @Table(name = "hidra_notification_template_translation")
    public class NotificationTemplateTranslationJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "template_version_id", nullable = false, length = 80)
    private String templateVersionId;

    @Column(name = "locale", nullable = false, length = 10)
    private String locale;

    @Column(name = "subject", nullable = true, length = 1000)
    private String subject;

    @Column(name = "body", nullable = false, columnDefinition = "text")
    private String body;

    @Column(name = "short_text", nullable = true, length = 500)
    private String shortText;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected NotificationTemplateTranslationJpaEntity() {
            // Required by JPA.
        }

        public NotificationTemplateTranslationJpaEntity(
                String id,
            String templateVersionId,
            String locale,
            String subject,
            String body,
            String shortText,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.templateVersionId = templateVersionId;
        this.locale = locale;
        this.subject = subject;
        this.body = body;
        this.shortText = shortText;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String templateVersionId() {
        return templateVersionId;
    }


    public String locale() {
        return locale;
    }


    public String subject() {
        return subject;
    }


    public String body() {
        return body;
    }


    public String shortText() {
        return shortText;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
