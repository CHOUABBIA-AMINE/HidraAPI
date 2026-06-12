/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationChannelJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for NotificationChannel.
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
     * Database-backed JPA entity for NotificationChannel.
     */
    @Entity
    @Table(name = "hidra_notification_channel")
    public class NotificationChannelJpaEntity {

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

    @Enumerated(EnumType.STRING)
    @Column(name = "channel_type", nullable = false, length = 40)
    private NotificationChannelType channelType;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "provider_reference", nullable = true, length = 255)
    private String providerReference;

    @Column(name = "supports_delivery_receipt", nullable = false)
    private boolean supportsDeliveryReceipt;

    @Column(name = "supports_read_receipt", nullable = false)
    private boolean supportsReadReceipt;

    @Column(name = "supports_html", nullable = false)
    private boolean supportsHtml;

    @Column(name = "supports_attachments", nullable = false)
    private boolean supportsAttachments;

    @Column(name = "max_payload_size", nullable = true)
    private Integer maxPayloadSize;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected NotificationChannelJpaEntity() {
            // Required by JPA.
        }

        public NotificationChannelJpaEntity(
                String id,
            String code,
            String nameAr,
            String nameFr,
            String nameEn,
            NotificationChannelType channelType,
            boolean active,
            String providerReference,
            boolean supportsDeliveryReceipt,
            boolean supportsReadReceipt,
            boolean supportsHtml,
            boolean supportsAttachments,
            Integer maxPayloadSize,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.code = code;
        this.nameAr = nameAr;
        this.nameFr = nameFr;
        this.nameEn = nameEn;
        this.channelType = channelType;
        this.active = active;
        this.providerReference = providerReference;
        this.supportsDeliveryReceipt = supportsDeliveryReceipt;
        this.supportsReadReceipt = supportsReadReceipt;
        this.supportsHtml = supportsHtml;
        this.supportsAttachments = supportsAttachments;
        this.maxPayloadSize = maxPayloadSize;
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


    public NotificationChannelType channelType() {
        return channelType;
    }


    public boolean active() {
        return active;
    }


    public String providerReference() {
        return providerReference;
    }


    public boolean supportsDeliveryReceipt() {
        return supportsDeliveryReceipt;
    }


    public boolean supportsReadReceipt() {
        return supportsReadReceipt;
    }


    public boolean supportsHtml() {
        return supportsHtml;
    }


    public boolean supportsAttachments() {
        return supportsAttachments;
    }


    public Integer maxPayloadSize() {
        return maxPayloadSize;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
