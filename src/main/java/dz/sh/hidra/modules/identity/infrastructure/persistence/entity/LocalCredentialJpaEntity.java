/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LocalCredentialJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-15
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for Identity-owned LOCAL credentials.
 *
 */
package dz.sh.hidra.modules.identity.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Instant;

/**
 * Persists the one-way LOCAL password hash and credential lifecycle metadata.
 */
@Entity
@Table(name = "hidra_identity_local_credential")
public class LocalCredentialJpaEntity {

    @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "user_id", nullable = false, unique = true, length = 80)
    private String userId;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    @Column(name = "credential_status", nullable = false, length = 40)
    private String credentialStatus;

    @Column(name = "password_changed_at")
    private Instant passwordChangedAt;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    protected LocalCredentialJpaEntity() {
        // Required by JPA.
    }

    public LocalCredentialJpaEntity(
            String id,
            String userId,
            String passwordHash,
            String credentialStatus,
            Instant passwordChangedAt,
            Instant createdAt,
            Instant updatedAt
    ) {
        this.id = id;
        this.userId = userId;
        this.passwordHash = passwordHash;
        this.credentialStatus = credentialStatus;
        this.passwordChangedAt = passwordChangedAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String id() {
        return id;
    }

    public String userId() {
        return userId;
    }

    public String passwordHash() {
        return passwordHash;
    }

    public String credentialStatus() {
        return credentialStatus;
    }

    public Instant passwordChangedAt() {
        return passwordChangedAt;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }
}
