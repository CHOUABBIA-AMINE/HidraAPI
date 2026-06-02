/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RolePermissionJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.entity
 *
 * @Description : JPA representation of an identity role-permission assignment.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.time.Instant;

/**
 * JPA representation of an identity role-permission assignment.
 *
 * <p>Business role: stores the assignment of one permission to one role.</p>
 *
 * <p>Architecture role: infrastructure persistence entity owned by the identity
 * persistence layer. It is mapped as a child row of {@link RoleJpaEntity}.</p>
 *
 * <p>Validation responsibility: database constraints enforce required permission values
 * and uniqueness per role/permission pair; domain rules remain in the role aggregate.</p>
 *
 * <p>Usage: create through {@code IdentityPersistenceMapper} when persisting role
 * permission assignments.</p>
 */
@Entity
@Table(
        name = "hidra_identity_role_permission",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_hidra_identity_role_permission_role_permission",
                        columnNames = {"role_id", "permission_id"}
                )
        }
)
public class RolePermissionJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "permission_id", nullable = false, length = 36)
    private String permissionId;

    @Column(name = "permission_code", nullable = false, length = 120)
    private String permissionCode;

    @Column(name = "assigned_at", nullable = false)
    private Instant assignedAt;

    protected RolePermissionJpaEntity() {
    }

    private RolePermissionJpaEntity(String permissionId, String permissionCode, Instant assignedAt) {
        this.permissionId = permissionId;
        this.permissionCode = permissionCode;
        this.assignedAt = assignedAt;
    }

    public static RolePermissionJpaEntity of(String permissionId, String permissionCode, Instant assignedAt) {
        return new RolePermissionJpaEntity(permissionId, permissionCode, assignedAt);
    }

    public Long getId() {
        return id;
    }

    public String getPermissionId() {
        return permissionId;
    }

    public String getPermissionCode() {
        return permissionCode;
    }

    public Instant getAssignedAt() {
        return assignedAt;
    }
}
