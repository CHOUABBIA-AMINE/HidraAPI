/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PermissionJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.entity
 *
 * @Description : JPA representation of an identity permission.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

/**
 * JPA representation of an identity permission.
 *
 * <p>Business role: stores permission catalog entries that can be granted to roles.</p>
 *
 * <p>Architecture role: infrastructure persistence entity used only by identity
 * persistence adapters and repositories.</p>
 *
 * <p>Validation responsibility: database constraints enforce required columns and unique
 * permission codes; permission-code validation remains in the domain model and policy.</p>
 *
 * <p>Usage: use through {@code PermissionJpaRepository} and
 * {@code IdentityPersistenceMapper} only.</p>
 */
@Entity
@Table(
        name = "hidra_identity_permission",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_hidra_identity_permission_code", columnNames = "code")
        }
)
public class PermissionJpaEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false, length = 36)
    private String id;

    @Column(name = "code", nullable = false, length = 120)
    private String code;

    @Column(name = "name", nullable = false, length = 120)
    private String name;

    @Column(name = "description", length = 500)
    private String description;

    protected PermissionJpaEntity() {
    }

    private PermissionJpaEntity(String id, String code, String name, String description) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
    }

    public static PermissionJpaEntity of(String id, String code, String name, String description) {
        return new PermissionJpaEntity(id, code, name, description);
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

    public String getDescription() {
        return description;
    }
}
