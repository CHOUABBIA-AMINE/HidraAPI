/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityProviderJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for IdentityProvider.
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
     * Database-backed JPA entity for IdentityProvider.
     */
    @Entity
    @Table(name = "hidra_identity_provider")
    public class IdentityProviderJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "code", nullable = false, length = 120)
    private String code;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider_type", nullable = false, length = 80)
    private ProviderType providerType;

    @Column(name = "issuer_uri", nullable = true, columnDefinition = "text")
    private String issuerUri;

    @Column(name = "authorization_endpoint", nullable = true, columnDefinition = "text")
    private String authorizationEndpoint;

    @Column(name = "token_endpoint", nullable = true, columnDefinition = "text")
    private String tokenEndpoint;

    @Column(name = "jwks_uri", nullable = true, columnDefinition = "text")
    private String jwksUri;

    @Column(name = "directory_base_dn", nullable = true, columnDefinition = "text")
    private String directoryBaseDn;

    @Column(name = "user_search_base", nullable = true, columnDefinition = "text")
    private String userSearchBase;

    @Column(name = "group_search_base", nullable = true, columnDefinition = "text")
    private String groupSearchBase;

    @Column(name = "username_attribute", nullable = true, length = 120)
    private String usernameAttribute;

    @Column(name = "email_attribute", nullable = true, length = 120)
    private String emailAttribute;

    @Column(name = "display_name_attribute", nullable = true, length = 120)
    private String displayNameAttribute;

    @Column(name = "external_id_attribute", nullable = true, length = 120)
    private String externalIdAttribute;

    @Column(name = "group_membership_attribute", nullable = true, length = 120)
    private String groupMembershipAttribute;

    @Column(name = "sync_enabled", nullable = false)
    private boolean syncEnabled;

    @Column(name = "just_in_time_provisioning_enabled", nullable = false)
    private boolean justInTimeProvisioningEnabled;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private IdentityProviderStatus status;

    @Column(name = "metadata", nullable = true, columnDefinition = "jsonb")
    private String metadata;

    @Column(name = "secret_reference", nullable = true, length = 255)
    private String secretReference;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected IdentityProviderJpaEntity() {
            // Required by JPA.
        }

        public IdentityProviderJpaEntity(
                String id,
            String code,
            String name,
            ProviderType providerType,
            String issuerUri,
            String authorizationEndpoint,
            String tokenEndpoint,
            String jwksUri,
            String directoryBaseDn,
            String userSearchBase,
            String groupSearchBase,
            String usernameAttribute,
            String emailAttribute,
            String displayNameAttribute,
            String externalIdAttribute,
            String groupMembershipAttribute,
            boolean syncEnabled,
            boolean justInTimeProvisioningEnabled,
            IdentityProviderStatus status,
            String metadata,
            String secretReference,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.code = code;
        this.name = name;
        this.providerType = providerType;
        this.issuerUri = issuerUri;
        this.authorizationEndpoint = authorizationEndpoint;
        this.tokenEndpoint = tokenEndpoint;
        this.jwksUri = jwksUri;
        this.directoryBaseDn = directoryBaseDn;
        this.userSearchBase = userSearchBase;
        this.groupSearchBase = groupSearchBase;
        this.usernameAttribute = usernameAttribute;
        this.emailAttribute = emailAttribute;
        this.displayNameAttribute = displayNameAttribute;
        this.externalIdAttribute = externalIdAttribute;
        this.groupMembershipAttribute = groupMembershipAttribute;
        this.syncEnabled = syncEnabled;
        this.justInTimeProvisioningEnabled = justInTimeProvisioningEnabled;
        this.status = status;
        this.metadata = metadata;
        this.secretReference = secretReference;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String code() {
        return code;
    }


    public String name() {
        return name;
    }


    public ProviderType providerType() {
        return providerType;
    }


    public String issuerUri() {
        return issuerUri;
    }


    public String authorizationEndpoint() {
        return authorizationEndpoint;
    }


    public String tokenEndpoint() {
        return tokenEndpoint;
    }


    public String jwksUri() {
        return jwksUri;
    }


    public String directoryBaseDn() {
        return directoryBaseDn;
    }


    public String userSearchBase() {
        return userSearchBase;
    }


    public String groupSearchBase() {
        return groupSearchBase;
    }


    public String usernameAttribute() {
        return usernameAttribute;
    }


    public String emailAttribute() {
        return emailAttribute;
    }


    public String displayNameAttribute() {
        return displayNameAttribute;
    }


    public String externalIdAttribute() {
        return externalIdAttribute;
    }


    public String groupMembershipAttribute() {
        return groupMembershipAttribute;
    }


    public boolean syncEnabled() {
        return syncEnabled;
    }


    public boolean justInTimeProvisioningEnabled() {
        return justInTimeProvisioningEnabled;
    }


    public IdentityProviderStatus status() {
        return status;
    }


    public String metadata() {
        return metadata;
    }


    public String secretReference() {
        return secretReference;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
