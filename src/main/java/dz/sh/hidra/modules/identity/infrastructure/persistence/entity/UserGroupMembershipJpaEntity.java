/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : UserGroupMembershipJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for UserGroupMembership.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence.entity;

import dz.sh.hidra.modules.identity.domain.value.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

    /**
     * Database-backed JPA entity for UserGroupMembership.
     */
    @Entity
    @Table(name = "hidra_identity_user_group_membership")
    public class UserGroupMembershipJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "user_id", nullable = false, length = 80)
    private String userId;

    @Column(name = "group_id", nullable = false, length = 80)
    private String groupId;

    @Enumerated(EnumType.STRING)
    @Column(name = "membership_type", nullable = false, length = 80)
    private MembershipType membershipType;

    @Column(name = "source_provider_id", nullable = true, length = 120)
    private String sourceProviderId;

    @Column(name = "source_mapping_id", nullable = true, length = 120)
    private String sourceMappingId;

    @Column(name = "valid_from", nullable = false)
    private Instant validFrom;

    @Column(name = "valid_to", nullable = true)
    private Instant validTo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private MembershipStatus status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected UserGroupMembershipJpaEntity() {
            // Required by JPA.
        }

        public UserGroupMembershipJpaEntity(
                String id,
            String userId,
            String groupId,
            MembershipType membershipType,
            String sourceProviderId,
            String sourceMappingId,
            Instant validFrom,
            Instant validTo,
            MembershipStatus status,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.userId = userId;
        this.groupId = groupId;
        this.membershipType = membershipType;
        this.sourceProviderId = sourceProviderId;
        this.sourceMappingId = sourceMappingId;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String userId() {
        return userId;
    }


    public String groupId() {
        return groupId;
    }


    public MembershipType membershipType() {
        return membershipType;
    }


    public String sourceProviderId() {
        return sourceProviderId;
    }


    public String sourceMappingId() {
        return sourceMappingId;
    }


    public Instant validFrom() {
        return validFrom;
    }


    public Instant validTo() {
        return validTo;
    }


    public MembershipStatus status() {
        return status;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
