/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : UserRoleJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.entity
 *
 * @Description : JPA representation of an identity user-role assignment.
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
 * JPA representation of an identity user-role assignment.
 *
 * <p>Business role: stores the assignment of one role to one user.</p>
 *
 * <p>Architecture role: infrastructure persistence entity owned by the identity
 * persistence layer. It is mapped as a child row of {@link UserJpaEntity}.</p>
 *
 * <p>Validation responsibility: database constraints enforce required role values and
 * uniqueness per user/role pair; domain rules remain in the user aggregate and role
 * assignment policy.</p>
 *
 * <p>Usage: create through {@code IdentityPersistenceMapper} when persisting user role
 * assignments.</p>
 */
@Entity
@Table(
        name = "hidra_identity_user_role",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_hidra_identity_user_role_user_role",
                        columnNames = {"user_id", "role_id"}
                )
        }
)
public class UserRoleJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "role_id", nullable = false, length = 36)
    private String roleId;

    @Column(name = "role_code", nullable = false, length = 64)
    private String roleCode;

    @Column(name = "assigned_at", nullable = false)
    private Instant assignedAt;

    protected UserRoleJpaEntity() {
    }

    private UserRoleJpaEntity(String roleId, String roleCode, Instant assignedAt) {
        this.roleId = roleId;
        this.roleCode = roleCode;
        this.assignedAt = assignedAt;
    }

    public static UserRoleJpaEntity of(String roleId, String roleCode, Instant assignedAt) {
        return new UserRoleJpaEntity(roleId, roleCode, assignedAt);
    }

    public Long getId() {
        return id;
    }

    public String getRoleId() {
        return roleId;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public Instant getAssignedAt() {
        return assignedAt;
    }
}
