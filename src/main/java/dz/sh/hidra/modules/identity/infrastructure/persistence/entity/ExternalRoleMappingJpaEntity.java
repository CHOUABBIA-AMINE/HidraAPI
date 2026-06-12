/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ExternalRoleMappingJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for ExternalRoleMapping.
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
     * Database-backed JPA entity for ExternalRoleMapping.
     */
    @Entity
    @Table(name = "hidra_identity_external_role_mapping")
    public class ExternalRoleMappingJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "identity_provider_id", nullable = false, length = 80)
    private String identityProviderId;

    @Column(name = "role_id", nullable = false, length = 80)
    private String roleId;

    @Column(name = "external_role_code", nullable = false, length = 160)
    private String externalRoleCode;

    @Column(name = "claim_name", nullable = true, length = 120)
    private String claimName;

    @Enumerated(EnumType.STRING)
    @Column(name = "mapping_mode", nullable = false, length = 80)
    private ExternalMappingMode mappingMode;

    @Enumerated(EnumType.STRING)
    @Column(name = "scope_type", nullable = true, length = 80)
    private ScopeType scopeType;

    @Column(name = "scope_reference_id", nullable = true, length = 120)
    private String scopeReferenceId;

    @Column(name = "scope_code_snapshot", nullable = true, length = 120)
    private String scopeCodeSnapshot;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private ExternalMappingStatus status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected ExternalRoleMappingJpaEntity() {
            // Required by JPA.
        }

        public ExternalRoleMappingJpaEntity(
                String id,
            String identityProviderId,
            String roleId,
            String externalRoleCode,
            String claimName,
            ExternalMappingMode mappingMode,
            ScopeType scopeType,
            String scopeReferenceId,
            String scopeCodeSnapshot,
            ExternalMappingStatus status,
            Instant createdAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.identityProviderId = identityProviderId;
        this.roleId = roleId;
        this.externalRoleCode = externalRoleCode;
        this.claimName = claimName;
        this.mappingMode = mappingMode;
        this.scopeType = scopeType;
        this.scopeReferenceId = scopeReferenceId;
        this.scopeCodeSnapshot = scopeCodeSnapshot;
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


    public String roleId() {
        return roleId;
    }


    public String externalRoleCode() {
        return externalRoleCode;
    }


    public String claimName() {
        return claimName;
    }


    public ExternalMappingMode mappingMode() {
        return mappingMode;
    }


    public ScopeType scopeType() {
        return scopeType;
    }


    public String scopeReferenceId() {
        return scopeReferenceId;
    }


    public String scopeCodeSnapshot() {
        return scopeCodeSnapshot;
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
