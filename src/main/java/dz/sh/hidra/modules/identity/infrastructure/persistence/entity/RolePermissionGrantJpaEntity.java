/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RolePermissionGrantJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for RolePermissionGrant.
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
     * Database-backed JPA entity for RolePermissionGrant.
     */
    @Entity
    @Table(name = "hidra_identity_role_permission_grant")
    public class RolePermissionGrantJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "role_id", nullable = false, length = 80)
    private String roleId;

    @Column(name = "permission_id", nullable = false, length = 80)
    private String permissionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "effect", nullable = false, length = 40)
    private GrantEffect effect;

    @Column(name = "condition_expression", nullable = true, columnDefinition = "jsonb")
    private String conditionExpression;

    @Column(name = "valid_from", nullable = false)
    private Instant validFrom;

    @Column(name = "valid_to", nullable = true)
    private Instant validTo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private GrantStatus status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

        protected RolePermissionGrantJpaEntity() {
            // Required by JPA.
        }

        public RolePermissionGrantJpaEntity(
                String id,
            String roleId,
            String permissionId,
            GrantEffect effect,
            String conditionExpression,
            Instant validFrom,
            Instant validTo,
            GrantStatus status,
            Instant createdAt
        ) {
            this.id = id;
        this.roleId = roleId;
        this.permissionId = permissionId;
        this.effect = effect;
        this.conditionExpression = conditionExpression;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.status = status;
        this.createdAt = createdAt;
        }


    public String id() {
        return id;
    }


    public String roleId() {
        return roleId;
    }


    public String permissionId() {
        return permissionId;
    }


    public GrantEffect effect() {
        return effect;
    }


    public String conditionExpression() {
        return conditionExpression;
    }


    public Instant validFrom() {
        return validFrom;
    }


    public Instant validTo() {
        return validTo;
    }


    public GrantStatus status() {
        return status;
    }


    public Instant createdAt() {
        return createdAt;
    }

    }
