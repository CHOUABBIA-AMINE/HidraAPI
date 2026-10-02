/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ExternalIdentityJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for ExternalIdentity.
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
     * Database-backed JPA entity for ExternalIdentity.
     */
    @Entity
    @Table(name = "hidra_identity_external_identity")
    public class ExternalIdentityJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "user_id", nullable = false, length = 80)
    private String userId;

    @Column(name = "identity_provider_id", nullable = false, length = 80)
    private String identityProviderId;

    @Column(name = "external_subject", nullable = false, columnDefinition = "text")
    private String externalSubject;

    @Column(name = "external_immutable_id", nullable = true, columnDefinition = "text")
    private String externalImmutableId;

    @Column(name = "external_username", nullable = true, length = 255)
    private String externalUsername;

    @Column(name = "external_email", nullable = true, length = 254)
    private String externalEmail;

    @Column(name = "external_display_name", nullable = true, length = 255)
    private String externalDisplayName;

    @Column(name = "external_distinguished_name", nullable = true, columnDefinition = "text")
    private String externalDistinguishedName;

    @Column(name = "external_attributes_snapshot", nullable = true, columnDefinition = "jsonb")
    private String externalAttributesSnapshot;

    @Column(name = "last_login_at", nullable = true)
    private Instant lastLoginAt;

    @Column(name = "last_synced_at", nullable = true)
    private Instant lastSyncedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private ExternalIdentityStatus status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected ExternalIdentityJpaEntity() {
            // Required by JPA.
        }

        public ExternalIdentityJpaEntity(
                String id,
            String userId,
            String identityProviderId,
            String externalSubject,
            String externalImmutableId,
            String externalUsername,
            String externalEmail,
            String externalDisplayName,
            String externalDistinguishedName,
            String externalAttributesSnapshot,
            Instant lastLoginAt,
            Instant lastSyncedAt,
            ExternalIdentityStatus status,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.userId = userId;
        this.identityProviderId = identityProviderId;
        this.externalSubject = externalSubject;
        this.externalImmutableId = externalImmutableId;
        this.externalUsername = externalUsername;
        this.externalEmail = externalEmail;
        this.externalDisplayName = externalDisplayName;
        this.externalDistinguishedName = externalDistinguishedName;
        this.externalAttributesSnapshot = externalAttributesSnapshot;
        this.lastLoginAt = lastLoginAt;
        this.lastSyncedAt = lastSyncedAt;
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


    public String identityProviderId() {
        return identityProviderId;
    }


    public String externalSubject() {
        return externalSubject;
    }


    public String externalImmutableId() {
        return externalImmutableId;
    }


    public String externalUsername() {
        return externalUsername;
    }


    public String externalEmail() {
        return externalEmail;
    }


    public String externalDisplayName() {
        return externalDisplayName;
    }


    public String externalDistinguishedName() {
        return externalDistinguishedName;
    }


    public String externalAttributesSnapshot() {
        return externalAttributesSnapshot;
    }


    public Instant lastLoginAt() {
        return lastLoginAt;
    }


    public Instant lastSyncedAt() {
        return lastSyncedAt;
    }


    public ExternalIdentityStatus status() {
        return status;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
