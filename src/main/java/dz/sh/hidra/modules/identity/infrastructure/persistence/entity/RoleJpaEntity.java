/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RoleJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.entity
 *
 * @Description : JPA representation of an identity role.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * JPA representation of an identity role.
 *
 * <p>Business role: stores identity roles that group permissions for user assignment.</p>
 *
 * <p>Architecture role: infrastructure persistence entity used only by identity
 * persistence adapters and repositories.</p>
 *
 * <p>Validation responsibility: database constraints enforce required columns and unique
 * role codes; domain rules remain in the role aggregate.</p>
 *
 * <p>Usage: use through {@code RoleJpaRepository} and {@code IdentityPersistenceMapper}
 * only.</p>
 */
@Entity
@Table(
        name = "hidra_identity_role",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_hidra_identity_role_code", columnNames = "code")
        }
)
public class RoleJpaEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false, length = 36)
    private String id;

    @Column(name = "code", nullable = false, length = 64)
    private String code;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "status", nullable = false, length = 32)
    private String status;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private Set<RolePermissionJpaEntity> permissionAssignments = new LinkedHashSet<>();

    protected RoleJpaEntity() {
    }

    private RoleJpaEntity(
            String id,
            String code,
            String name,
            String status,
            Collection<RolePermissionJpaEntity> permissionAssignments
    ) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.status = status;
        replacePermissionAssignments(permissionAssignments);
    }

    public static RoleJpaEntity of(
            String id,
            String code,
            String name,
            String status,
            Collection<RolePermissionJpaEntity> permissionAssignments
    ) {
        return new RoleJpaEntity(id, code, name, status, permissionAssignments);
    }

    public String getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public Set<RolePermissionJpaEntity> getPermissionAssignments() {
        return Collections.unmodifiableSet(permissionAssignments);
    }

    public void replacePermissionAssignments(Collection<RolePermissionJpaEntity> assignments) {
        permissionAssignments.clear();
        if (assignments != null) {
            permissionAssignments.addAll(assignments);
        }
    }
}
