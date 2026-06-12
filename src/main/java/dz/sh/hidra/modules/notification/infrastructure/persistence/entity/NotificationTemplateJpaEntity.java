/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationTemplateJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for NotificationTemplate.
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
     * Database-backed JPA entity for NotificationTemplate.
     */
    @Entity
    @Table(name = "hidra_notification_template")
    public class NotificationTemplateJpaEntity {

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

    @Column(name = "template_type_id", nullable = false, length = 80)
    private String templateTypeId;

    @Column(name = "category_id", nullable = true, length = 80)
    private String categoryId;

    @Column(name = "default_channel_id", nullable = true, length = 80)
    private String defaultChannelId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private NotificationTemplateStatus status;

    @Column(name = "current_version", nullable = true)
    private Integer currentVersion;

    @Column(name = "system_defined", nullable = false)
    private boolean systemDefined;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected NotificationTemplateJpaEntity() {
            // Required by JPA.
        }

        public NotificationTemplateJpaEntity(
                String id,
            String code,
            String nameAr,
            String nameFr,
            String nameEn,
            String templateTypeId,
            String categoryId,
            String defaultChannelId,
            NotificationTemplateStatus status,
            Integer currentVersion,
            boolean systemDefined,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.code = code;
        this.nameAr = nameAr;
        this.nameFr = nameFr;
        this.nameEn = nameEn;
        this.templateTypeId = templateTypeId;
        this.categoryId = categoryId;
        this.defaultChannelId = defaultChannelId;
        this.status = status;
        this.currentVersion = currentVersion;
        this.systemDefined = systemDefined;
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


    public String templateTypeId() {
        return templateTypeId;
    }


    public String categoryId() {
        return categoryId;
    }


    public String defaultChannelId() {
        return defaultChannelId;
    }


    public NotificationTemplateStatus status() {
        return status;
    }


    public Integer currentVersion() {
        return currentVersion;
    }


    public boolean systemDefined() {
        return systemDefined;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
