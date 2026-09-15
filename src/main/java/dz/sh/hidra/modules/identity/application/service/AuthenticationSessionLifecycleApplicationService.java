/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthenticationSessionLifecycleApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-15
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.service
 *
 * @Description : Manages provider-neutral authentication session lifecycle and authentication trace events without storing raw tokens.
 *
 */
package dz.sh.hidra.modules.identity.application.service;

import dz.sh.hidra.modules.identity.application.port.out.AuthenticationEventRepositoryPort;
import dz.sh.hidra.modules.identity.application.port.out.IdentityProviderRepositoryPort;
import dz.sh.hidra.modules.identity.application.port.out.LoginSessionRepositoryPort;
import dz.sh.hidra.modules.identity.domain.model.AuthenticationEvent;
import dz.sh.hidra.modules.identity.domain.model.HidraPrincipal;
import dz.sh.hidra.modules.identity.domain.model.IdentityProvider;
import dz.sh.hidra.modules.identity.domain.model.LoginSession;
import dz.sh.hidra.modules.identity.domain.value.AuthenticationEventType;
import dz.sh.hidra.modules.identity.domain.value.AuthenticationProtocol;
import dz.sh.hidra.modules.identity.domain.value.LoginSessionStatus;
import dz.sh.hidra.modules.identity.domain.value.ProviderType;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Provider-neutral lifecycle service for logical authentication sessions.
 *
 * <p>The session deliberately stores only lifecycle metadata. Raw JWT values are never
 * accepted or persisted here. LOCAL login success events remain recorded by
 * {@link LocalAuthenticationOutcomeApplicationService}; this service records login
 * success for the other normalized provider paths to avoid duplicate LOCAL events.</p>
 */
@Service
public class AuthenticationSessionLifecycleApplicationService {

    private final LoginSessionRepositoryPort loginSessionRepository;
    private final AuthenticationEventRepositoryPort authenticationEventRepository;
    private final IdentityProviderRepositoryPort identityProviderRepository;

    public AuthenticationSessionLifecycleApplicationService(
            LoginSessionRepositoryPort loginSessionRepository,
            AuthenticationEventRepositoryPort authenticationEventRepository,
            IdentityProviderRepositoryPort identityProviderRepository
    ) {
        this.loginSessionRepository = Objects.requireNonNull(loginSessionRepository);
        this.authenticationEventRepository = Objects.requireNonNull(authenticationEventRepository);
        this.identityProviderRepository = Objects.requireNonNull(identityProviderRepository);
    }

    /**
     * Starts one logical session for an already authenticated Hidra principal.
     */
    @Transactional
    public LoginSession startSession(
            HidraPrincipal principal,
            Instant expiresAt,
            String clientIp,
            String userAgent,
            String correlationId
    ) {
        Objects.requireNonNull(principal, "Hidra principal must not be null.");
        Instant now = Instant.now();
        if (expiresAt == null || !expiresAt.isAfter(now)) {
            throw new IllegalArgumentException("Session expiry must be after the session start time.");
        }

        LoginSession session = loginSessionRepository.save(new LoginSession(
                UUID.randomUUID().toString(),
                principal.userId(),
                principal.identityProviderId(),
                null,
                now,
                now,
                expiresAt,
                clientIp,
                userAgent,
                LoginSessionStatus.ACTIVE,
                correlationId
        ));

        if (principal.authenticationType() != ProviderType.LOCAL) {
            authenticationEventRepository.save(authenticationEvent(
                    session,
                    AuthenticationEventType.LOGIN_SUCCESS,
                    protocol(principal.authenticationType()),
                    null,
                    now
            ));
        }
        return session;
    }

    /**
     * Updates last-seen state for an active session and expires it when its deadline passed.
     */
    @Transactional
    public LoginSession touchSession(String sessionId, Instant seenAt) {
        LoginSession session = requireSession(sessionId);
        Instant effectiveSeenAt = seenAt == null ? Instant.now() : seenAt;
        LoginSessionStatus status = session.status();
        if (status == LoginSessionStatus.ACTIVE
                && session.expiresAt() != null
                && !effectiveSeenAt.isBefore(session.expiresAt())) {
            status = LoginSessionStatus.EXPIRED;
        }
        if (status != LoginSessionStatus.ACTIVE) {
            return status == session.status() ? session : saveWithStatus(session, status, effectiveSeenAt);
        }
        return loginSessionRepository.save(copy(session, effectiveSeenAt, LoginSessionStatus.ACTIVE));
    }

    /**
     * Marks a session as logged out and records the corresponding authentication event.
     */
    @Transactional
    public LoginSession logoutSession(String sessionId) {
        LoginSession session = requireSession(sessionId);
        if (session.status() == LoginSessionStatus.LOGGED_OUT) {
            return session;
        }
        Instant now = Instant.now();
        LoginSession loggedOut = saveWithStatus(session, LoginSessionStatus.LOGGED_OUT, now);
        authenticationEventRepository.save(authenticationEvent(
                loggedOut,
                AuthenticationEventType.LOGOUT,
                protocolForSession(loggedOut),
                null,
                now
        ));
        return loggedOut;
    }

    /**
     * Revokes a session without inventing an authentication event type that the domain does not define.
     */
    @Transactional
    public LoginSession revokeSession(String sessionId) {
        LoginSession session = requireSession(sessionId);
        if (session.status() == LoginSessionStatus.REVOKED) {
            return session;
        }
        return saveWithStatus(session, LoginSessionStatus.REVOKED, Instant.now());
    }

    /**
     * Marks a session expired without storing or inspecting bearer token material.
     */
    @Transactional
    public LoginSession expireSession(String sessionId) {
        LoginSession session = requireSession(sessionId);
        if (session.status() == LoginSessionStatus.EXPIRED) {
            return session;
        }
        return saveWithStatus(session, LoginSessionStatus.EXPIRED, Instant.now());
    }

    private LoginSession requireSession(String sessionId) {
        if (sessionId == null || sessionId.isBlank()) {
            throw new IllegalArgumentException("Session id must not be blank.");
        }
        return loginSessionRepository.findById(sessionId.trim())
                .orElseThrow(() -> new IllegalArgumentException("Authentication session was not found."));
    }

    private LoginSession saveWithStatus(LoginSession session, LoginSessionStatus status, Instant seenAt) {
        return loginSessionRepository.save(copy(session, seenAt, status));
    }

    private static LoginSession copy(LoginSession session, Instant lastSeenAt, LoginSessionStatus status) {
        return new LoginSession(
                session.id(),
                session.userId(),
                session.identityProviderId(),
                session.externalIdentityId(),
                session.startedAt(),
                lastSeenAt,
                session.expiresAt(),
                session.clientIp(),
                session.userAgent(),
                status,
                session.correlationId()
        );
    }

    private AuthenticationProtocol protocolForSession(LoginSession session) {
        if (session.identityProviderId() == null) {
            return AuthenticationProtocol.LOCAL;
        }
        IdentityProvider provider = identityProviderRepository.findById(session.identityProviderId())
                .orElseThrow(() -> new IllegalStateException("Authentication session identity provider no longer exists."));
        return protocol(provider.providerType());
    }

    private static AuthenticationProtocol protocol(ProviderType providerType) {
        Objects.requireNonNull(providerType, "Provider type must not be null.");
        return switch (providerType) {
            case LOCAL -> AuthenticationProtocol.LOCAL;
            case LDAP, ACTIVE_DIRECTORY -> AuthenticationProtocol.LDAP;
            case OIDC, KEYCLOAK, AZURE_AD, OKTA -> AuthenticationProtocol.OIDC;
            case OAUTH2 -> AuthenticationProtocol.OAUTH2;
            case SAML2 -> AuthenticationProtocol.SAML2;
            default -> throw new IllegalArgumentException("Unsupported authentication provider protocol: " + providerType);
        };
    }

    private static AuthenticationEvent authenticationEvent(
            LoginSession session,
            AuthenticationEventType eventType,
            AuthenticationProtocol protocol,
            String failureReason,
            Instant occurredAt
    ) {
        return new AuthenticationEvent(
                UUID.randomUUID().toString(),
                session.userId(),
                session.identityProviderId(),
                session.externalIdentityId(),
                null,
                eventType,
                protocol,
                session.clientIp(),
                session.userAgent(),
                failureReason,
                null,
                occurredAt,
                session.correlationId()
        );
    }
}
