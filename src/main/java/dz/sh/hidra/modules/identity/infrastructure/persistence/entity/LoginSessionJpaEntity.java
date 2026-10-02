/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LoginSessionJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for LoginSession.
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
     * Database-backed JPA entity for LoginSession.
     */
    @Entity
    @Table(name = "hidra_identity_login_session")
    public class LoginSessionJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "user_id", nullable = false, length = 80)
    private String userId;

    @Column(name = "identity_provider_id", nullable = true, length = 80)
    private String identityProviderId;

    @Column(name = "external_identity_id", nullable = true, length = 80)
    private String externalIdentityId;

    @Column(name = "started_at", nullable = false)
    private Instant startedAt;

    @Column(name = "last_seen_at", nullable = true)
    private Instant lastSeenAt;

    @Column(name = "expires_at", nullable = true)
    private Instant expiresAt;

    @Column(name = "client_ip", nullable = true, length = 80)
    private String clientIp;

    @Column(name = "user_agent", nullable = true, columnDefinition = "text")
    private String userAgent;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 40)
    private LoginSessionStatus status;

    @Column(name = "correlation_id", nullable = true, length = 120)
    private String correlationId;

        protected LoginSessionJpaEntity() {
            // Required by JPA.
        }

        public LoginSessionJpaEntity(
                String id,
            String userId,
            String identityProviderId,
            String externalIdentityId,
            Instant startedAt,
            Instant lastSeenAt,
            Instant expiresAt,
            String clientIp,
            String userAgent,
            LoginSessionStatus status,
            String correlationId
        ) {
            this.id = id;
        this.userId = userId;
        this.identityProviderId = identityProviderId;
        this.externalIdentityId = externalIdentityId;
        this.startedAt = startedAt;
        this.lastSeenAt = lastSeenAt;
        this.expiresAt = expiresAt;
        this.clientIp = clientIp;
        this.userAgent = userAgent;
        this.status = status;
        this.correlationId = correlationId;
        }


    public String id() {
        return id;
    }


    public String userId() {
        return userId;
    }


    public String identityProviderId() {
        return identityProviderId;
    }


    public String externalIdentityId() {
        return externalIdentityId;
    }


    public Instant startedAt() {
        return startedAt;
    }


    public Instant lastSeenAt() {
        return lastSeenAt;
    }


    public Instant expiresAt() {
        return expiresAt;
    }


    public String clientIp() {
        return clientIp;
    }


    public String userAgent() {
        return userAgent;
    }


    public LoginSessionStatus status() {
        return status;
    }


    public String correlationId() {
        return correlationId;
    }

    }
