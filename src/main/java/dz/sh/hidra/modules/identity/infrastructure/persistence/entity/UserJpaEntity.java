/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : UserJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.entity
 *
 * @Description : JPA representation of an identity user.
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
 * JPA representation of an identity user.
 *
 * <p>Business role: stores HidraAPI security identity users in the identity database
 * tables.</p>
 *
 * <p>Architecture role: infrastructure persistence entity used only by identity
 * persistence adapters. It is not a domain aggregate and must not leak into domain,
 * application, API, platform, or organization code.</p>
 *
 * <p>Validation responsibility: database constraints enforce required columns and unique
 * username/email values; domain validation remains in domain value objects and
 * aggregates.</p>
 *
 * <p>Usage: use through {@code UserJpaRepository} and {@code IdentityPersistenceMapper}
 * only.</p>
 */
@Entity
@Table(
        name = "hidra_identity_user",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_hidra_identity_user_username", columnNames = "username"),
                @UniqueConstraint(name = "uk_hidra_identity_user_email_address", columnNames = "email_address")
        }
)
public class UserJpaEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false, length = 36)
    private String id;

    @Column(name = "username", nullable = false, length = 64)
    private String username;

    @Column(name = "email_address", nullable = false, length = 254)
    private String emailAddress;

    @Column(name = "status", nullable = false, length = 32)
    private String status;

    @Column(name = "employee_reference", length = 128)
    private String employeeReference;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private Set<UserRoleJpaEntity> roleAssignments = new LinkedHashSet<>();

    protected UserJpaEntity() {
    }

    private UserJpaEntity(
            String id,
            String username,
            String emailAddress,
            String status,
            String employeeReference,
            Collection<UserRoleJpaEntity> roleAssignments
    ) {
        this.id = id;
        this.username = username;
        this.emailAddress = emailAddress;
        this.status = status;
        this.employeeReference = employeeReference;
        replaceRoleAssignments(roleAssignments);
    }

    public static UserJpaEntity of(
            String id,
            String username,
            String emailAddress,
            String status,
            String employeeReference,
            Collection<UserRoleJpaEntity> roleAssignments
    ) {
        return new UserJpaEntity(id, username, emailAddress, status, employeeReference, roleAssignments);
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getStatus() {
        return status;
    }

    public String getEmployeeReference() {
        return employeeReference;
    }

    public Set<UserRoleJpaEntity> getRoleAssignments() {
        return Collections.unmodifiableSet(roleAssignments);
    }

    public void replaceRoleAssignments(Collection<UserRoleJpaEntity> assignments) {
        roleAssignments.clear();
        if (assignments != null) {
            roleAssignments.addAll(assignments);
        }
    }
}
