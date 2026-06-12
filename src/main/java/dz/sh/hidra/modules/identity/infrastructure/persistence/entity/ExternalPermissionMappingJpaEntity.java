/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ExternalPermissionMappingJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for ExternalPermissionMapping.
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
     * Database-backed JPA entity for ExternalPermissionMapping.
     */
    @Entity
    @Table(name = "hidra_identity_external_permission_mapping")
    public class ExternalPermissionMappingJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "identity_provider_id", nullable = false, length = 80)
    private String identityProviderId;

    @Column(name = "permission_id", nullable = false, length = 80)
    private String permissionId;

    @Column(name = "external_permission_code", nullable = false, length = 160)
    private String externalPermissionCode;

    @Column(name = "claim_name", nullable = true, length = 120)
    private String claimName;

    @Enumerated(EnumType.STRING)
    @Column(name = "mapping_mode", nullable = false, length = 80)
    private ExternalMappingMode mappingMode;

    @Enumerated(EnumType.STRING)
    @Column(name = "effect", nullable = false, length = 40)
    private GrantEffect effect;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private ExternalMappingStatus status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected ExternalPermissionMappingJpaEntity() {
            // Required by JPA.
        }

        public ExternalPermissionMappingJpaEntity(
                String id,
            String identityProviderId,
            String permissionId,
            String externalPermissionCode,
            String claimName,
            ExternalMappingMode mappingMode,
            GrantEffect effect,
            ExternalMappingStatus status,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.identityProviderId = identityProviderId;
        this.permissionId = permissionId;
        this.externalPermissionCode = externalPermissionCode;
        this.claimName = claimName;
        this.mappingMode = mappingMode;
        this.effect = effect;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String identityProviderId() {
        return identityProviderId;
    }


    public String permissionId() {
        return permissionId;
    }


    public String externalPermissionCode() {
        return externalPermissionCode;
    }


    public String claimName() {
        return claimName;
    }


    public ExternalMappingMode mappingMode() {
        return mappingMode;
    }


    public GrantEffect effect() {
        return effect;
    }


    public ExternalMappingStatus status() {
        return status;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
