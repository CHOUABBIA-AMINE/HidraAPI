/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthenticationEventJpaEntity
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.infrastructure.persistence.entity
 *
 * @Description : Database-backed JPA entity for AuthenticationEvent.
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
import java.math.BigDecimal;
import java.time.Instant;

    /**
     * Database-backed JPA entity for AuthenticationEvent.
     */
    @Entity
    @Table(name = "hidra_identity_authentication_event")
    public class AuthenticationEventJpaEntity {

        @Id
    @Column(name = "id", nullable = false, length = 80)
    private String id;

    @Column(name = "user_id", nullable = true, length = 80)
    private String userId;

    @Column(name = "identity_provider_id", nullable = true, length = 80)
    private String identityProviderId;

    @Column(name = "external_identity_id", nullable = true, length = 80)
    private String externalIdentityId;

    @Column(name = "external_subject", nullable = true, columnDefinition = "text")
    private String externalSubject;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type", nullable = false, length = 80)
    private AuthenticationEventType eventType;

    @Enumerated(EnumType.STRING)
    @Column(name = "protocol", nullable = false, length = 80)
    private AuthenticationProtocol protocol;

    @Column(name = "client_ip", nullable = true, length = 80)
    private String clientIp;

    @Column(name = "user_agent", nullable = true, columnDefinition = "text")
    private String userAgent;

    @Column(name = "failure_reason", nullable = true, columnDefinition = "text")
    private String failureReason;

    @Column(name = "risk_score", nullable = true, precision = 10, scale = 4)
    private BigDecimal riskScore;

    @Column(name = "occurred_at", nullable = false)
    private Instant occurredAt;

    @Column(name = "correlation_id", nullable = true, length = 120)
    private String correlationId;

        protected AuthenticationEventJpaEntity() {
            // Required by JPA.
        }

        public AuthenticationEventJpaEntity(
                String id,
            String userId,
            String identityProviderId,
            String externalIdentityId,
            String externalSubject,
            AuthenticationEventType eventType,
            AuthenticationProtocol protocol,
            String clientIp,
            String userAgent,
            String failureReason,
            BigDecimal riskScore,
            Instant occurredAt,
            String correlationId
        ) {
            this.id = id;
        this.userId = userId;
        this.identityProviderId = identityProviderId;
        this.externalIdentityId = externalIdentityId;
        this.externalSubject = externalSubject;
        this.eventType = eventType;
        this.protocol = protocol;
        this.clientIp = clientIp;
        this.userAgent = userAgent;
        this.failureReason = failureReason;
        this.riskScore = riskScore;
        this.occurredAt = occurredAt;
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


    public String externalSubject() {
        return externalSubject;
    }


    public AuthenticationEventType eventType() {
        return eventType;
    }


    public AuthenticationProtocol protocol() {
        return protocol;
    }


    public String clientIp() {
        return clientIp;
    }


    public String userAgent() {
        return userAgent;
    }


    public String failureReason() {
        return failureReason;
    }


    public BigDecimal riskScore() {
        return riskScore;
    }


    public Instant occurredAt() {
        return occurredAt;
    }


    public String correlationId() {
        return correlationId;
    }

    }
