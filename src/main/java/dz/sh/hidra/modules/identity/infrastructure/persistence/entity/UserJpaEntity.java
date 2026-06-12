/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : UserJpaEntity
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : JpaEntity
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for User.
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
     * Database-backed JPA entity for User.
     */
    @Entity
    @Table(name = "hidra_identity_user")
    public class UserJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "username", nullable = false, length = 120)
    private String username;

    @Column(name = "email_address", nullable = true, length = 254)
    private String emailAddress;

    @Column(name = "display_name", nullable = true, length = 255)
    private String displayName;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = false, length = 40)
    private UserType userType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private UserStatus status;

    @Column(name = "employee_reference_id", nullable = true, length = 120)
    private String employeeReferenceId;

    @Column(name = "last_authenticated_at", nullable = true)
    private Instant lastAuthenticatedAt;

    @Column(name = "failed_login_count", nullable = false)
    private int failedLoginCount;

    @Column(name = "locked_until", nullable = true)
    private Instant lockedUntil;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "activated_at", nullable = true)
    private Instant activatedAt;

    @Column(name = "suspended_at", nullable = true)
    private Instant suspendedAt;

    @Column(name = "disabled_at", nullable = true)
    private Instant disabledAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

        protected UserJpaEntity() {
            // Required by JPA.
        }

        public UserJpaEntity(
                String id,
            String username,
            String emailAddress,
            String displayName,
            UserType userType,
            UserStatus status,
            String employeeReferenceId,
            Instant lastAuthenticatedAt,
            int failedLoginCount,
            Instant lockedUntil,
            Instant createdAt,
            Instant activatedAt,
            Instant suspendedAt,
            Instant disabledAt,
            Instant updatedAt
        ) {
            this.id = id;
        this.username = username;
        this.emailAddress = emailAddress;
        this.displayName = displayName;
        this.userType = userType;
        this.status = status;
        this.employeeReferenceId = employeeReferenceId;
        this.lastAuthenticatedAt = lastAuthenticatedAt;
        this.failedLoginCount = failedLoginCount;
        this.lockedUntil = lockedUntil;
        this.createdAt = createdAt;
        this.activatedAt = activatedAt;
        this.suspendedAt = suspendedAt;
        this.disabledAt = disabledAt;
        this.updatedAt = updatedAt;
        }


    public String id() {
        return id;
    }


    public String username() {
        return username;
    }


    public String emailAddress() {
        return emailAddress;
    }


    public String displayName() {
        return displayName;
    }


    public UserType userType() {
        return userType;
    }


    public UserStatus status() {
        return status;
    }


    public String employeeReferenceId() {
        return employeeReferenceId;
    }


    public Instant lastAuthenticatedAt() {
        return lastAuthenticatedAt;
    }


    public int failedLoginCount() {
        return failedLoginCount;
    }


    public Instant lockedUntil() {
        return lockedUntil;
    }


    public Instant createdAt() {
        return createdAt;
    }


    public Instant activatedAt() {
        return activatedAt;
    }


    public Instant suspendedAt() {
        return suspendedAt;
    }


    public Instant disabledAt() {
        return disabledAt;
    }


    public Instant updatedAt() {
        return updatedAt;
    }

    }
