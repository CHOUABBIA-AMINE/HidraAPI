/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ExternalGroupMappingJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for ExternalGroupMapping.
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
     * Database-backed JPA entity for ExternalGroupMapping.
     */
    @Entity
    @Table(name = "hidra_identity_external_group_mapping")
    public class ExternalGroupMappingJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "identity_provider_id", nullable = false, length = 80)
    private String identityProviderId;

    @Column(name = "group_id", nullable = false, length = 80)
    private String groupId;

    @Column(name = "external_group_id", nullable = true, columnDefinition = "text")
    private String externalGroupId;

    @Column(name = "external_group_name", nullable = false, columnDefinition = "text")
    private String externalGroupName;

    @Column(name = "external_group_dn", nullable = true, columnDefinition = "text")
    private String externalGroupDn;

    @Column(name = "claim_name", nullable = true, length = 120)
    private String claimName;

    @Enumerated(EnumType.STRING)
    @Column(name = "mapping_mode", nullable = false, length = 80)
    private ExternalMappingMode mappingMode;

    @Column(name = "auto_create_membership", nullable = false)
    private boolean autoCreateMembership;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private ExternalMappingStatus status;

    @Column(name = "last_synced_at", nullable = true)
    private Instant lastSyncedAt;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected ExternalGroupMappingJpaEntity() {
            // Required by JPA.
        }

        public ExternalGroupMappingJpaEntity(
                String id,
            String identityProviderId,
            String groupId,
            String externalGroupId,
            String externalGroupName,
            String externalGroupDn,
            String claimName,
            ExternalMappingMode mappingMode,
            boolean autoCreateMembership,
            ExternalMappingStatus status,
            Instant lastSyncedAt,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.identityProviderId = identityProviderId;
        this.groupId = groupId;
        this.externalGroupId = externalGroupId;
        this.externalGroupName = externalGroupName;
        this.externalGroupDn = externalGroupDn;
        this.claimName = claimName;
        this.mappingMode = mappingMode;
        this.autoCreateMembership = autoCreateMembership;
        this.status = status;
        this.lastSyncedAt = lastSyncedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String identityProviderId() {
        return identityProviderId;
    }


    public String groupId() {
        return groupId;
    }


    public String externalGroupId() {
        return externalGroupId;
    }


    public String externalGroupName() {
        return externalGroupName;
    }


    public String externalGroupDn() {
        return externalGroupDn;
    }


    public String claimName() {
        return claimName;
    }


    public ExternalMappingMode mappingMode() {
        return mappingMode;
    }


    public boolean autoCreateMembership() {
        return autoCreateMembership;
    }


    public ExternalMappingStatus status() {
        return status;
    }


    public Instant lastSyncedAt() {
        return lastSyncedAt;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
