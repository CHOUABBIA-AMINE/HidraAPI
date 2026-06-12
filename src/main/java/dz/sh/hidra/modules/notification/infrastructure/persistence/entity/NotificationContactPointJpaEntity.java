/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationContactPointJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for NotificationContactPoint.
 *
 */
package dz.sh.hidra.modules.notification.infrastructure.persistence.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

    /**
     * Database-backed JPA entity for NotificationContactPoint.
     */
    @Entity
    @Table(name = "hidra_notification_contact_point")
    public class NotificationContactPointJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "recipient_profile_id", nullable = false, length = 80)
    private String recipientProfileId;

    @Column(name = "channel_id", nullable = false, length = 80)
    private String channelId;

    @Column(name = "address_value", nullable = false, length = 500)
    private String addressValue;

    @Column(name = "address_label", nullable = true, length = 160)
    private String addressLabel;

    @Column(name = "verified", nullable = false)
    private boolean verified;

    @Column(name = "primary_for_channel", nullable = false)
    private boolean primaryForChannel;

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

        protected NotificationContactPointJpaEntity() {
            // Required by JPA.
        }

        public NotificationContactPointJpaEntity(
                String id,
            String recipientProfileId,
            String channelId,
            String addressValue,
            String addressLabel,
            boolean verified,
            boolean primaryForChannel,
            boolean active,
            Instant validFrom,
            Instant validTo,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.recipientProfileId = recipientProfileId;
        this.channelId = channelId;
        this.addressValue = addressValue;
        this.addressLabel = addressLabel;
        this.verified = verified;
        this.primaryForChannel = primaryForChannel;
        this.active = active;
        this.validFrom = validFrom;
        this.validTo = validTo;
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


    public String addressValue() {
        return addressValue;
    }


    public String addressLabel() {
        return addressLabel;
    }


    public boolean verified() {
        return verified;
    }


    public boolean primaryForChannel() {
        return primaryForChannel;
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
