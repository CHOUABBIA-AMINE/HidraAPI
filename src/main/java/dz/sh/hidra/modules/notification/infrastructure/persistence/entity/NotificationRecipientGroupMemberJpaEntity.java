/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NotificationRecipientGroupMemberJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for NotificationRecipientGroupMember.
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
     * Database-backed JPA entity for NotificationRecipientGroupMember.
     */
    @Entity
    @Table(name = "hidra_notification_recipient_group_member")
    public class NotificationRecipientGroupMemberJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "group_id", nullable = false, length = 80)
    private String groupId;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_type", nullable = false, length = 40)
    private NotificationRecipientType memberType;

    @Column(name = "member_reference_id", nullable = false, length = 120)
    private String memberReferenceId;

    @Column(name = "member_label_snapshot", nullable = true, length = 160)
    private String memberLabelSnapshot;

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

        protected NotificationRecipientGroupMemberJpaEntity() {
            // Required by JPA.
        }

        public NotificationRecipientGroupMemberJpaEntity(
                String id,
            String groupId,
            NotificationRecipientType memberType,
            String memberReferenceId,
            String memberLabelSnapshot,
            boolean active,
            Instant validFrom,
            Instant validTo,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.groupId = groupId;
        this.memberType = memberType;
        this.memberReferenceId = memberReferenceId;
        this.memberLabelSnapshot = memberLabelSnapshot;
        this.active = active;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String groupId() {
        return groupId;
    }


    public NotificationRecipientType memberType() {
        return memberType;
    }


    public String memberReferenceId() {
        return memberReferenceId;
    }


    public String memberLabelSnapshot() {
        return memberLabelSnapshot;
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
