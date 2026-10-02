/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LocalAuthenticationOutcomeApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-15
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.service
 *
 * @Description : Records LOCAL authentication success/failure outcomes using existing Identity user state and authentication events.
 *
 */
package dz.sh.hidra.modules.identity.application.service;

import dz.sh.hidra.modules.identity.application.port.out.AuthenticationEventRepositoryPort;
import dz.sh.hidra.modules.identity.application.port.out.UserRepositoryPort;
import dz.sh.hidra.modules.identity.domain.model.AuthenticationEvent;
import dz.sh.hidra.modules.identity.domain.model.User;
import dz.sh.hidra.modules.identity.domain.value.AuthenticationEventType;
import dz.sh.hidra.modules.identity.domain.value.AuthenticationProtocol;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Persists LOCAL authentication outcome state independently from the authentication provider transaction.
 */
@Service
public class LocalAuthenticationOutcomeApplicationService {

    private final UserRepositoryPort userRepository;
    private final AuthenticationEventRepositoryPort authenticationEventRepository;

    public LocalAuthenticationOutcomeApplicationService(
            UserRepositoryPort userRepository,
            AuthenticationEventRepositoryPort authenticationEventRepository
    ) {
        this.userRepository = Objects.requireNonNull(userRepository, "UserRepositoryPort must not be null.");
        this.authenticationEventRepository = Objects.requireNonNull(
                authenticationEventRepository,
                "AuthenticationEventRepositoryPort must not be null."
        );
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void recordSuccess(String userId, String identityProviderId) {
        Instant occurredAt = Instant.now();
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("Authenticated Hidra user no longer exists."));

        userRepository.save(copyWithLoginState(user, occurredAt, 0));
        authenticationEventRepository.save(authenticationEvent(
                user.id(),
                identityProviderId,
                AuthenticationEventType.LOGIN_SUCCESS,
                null,
                occurredAt
        ));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void recordFailure(String userId, String identityProviderId, String failureReason) {
        Instant occurredAt = Instant.now();
        if (userId != null) {
            userRepository.findById(userId).ifPresent(user -> userRepository.save(copyWithLoginState(
                    user,
                    user.lastAuthenticatedAt(),
                    incrementFailedLoginCount(user.failedLoginCount()),
                    occurredAt
            )));
        }

        authenticationEventRepository.save(authenticationEvent(
                userId,
                identityProviderId,
                AuthenticationEventType.LOGIN_FAILED,
                failureReason,
                occurredAt
        ));
    }

    private static User copyWithLoginState(User user, Instant lastAuthenticatedAt, int failedLoginCount) {
        return copyWithLoginState(user, lastAuthenticatedAt, failedLoginCount, Instant.now());
    }

    private static User copyWithLoginState(
            User user,
            Instant lastAuthenticatedAt,
            int failedLoginCount,
            Instant updatedAt
    ) {
        return new User(
                user.id(),
                user.username(),
                user.emailAddress(),
                user.displayName(),
                user.userType(),
                user.status(),
                user.employeeReferenceId(),
                lastAuthenticatedAt,
                failedLoginCount,
                user.lockedUntil(),
                user.createdAt(),
                user.activatedAt(),
                user.suspendedAt(),
                user.disabledAt(),
                updatedAt
        );
    }

    private static AuthenticationEvent authenticationEvent(
            String userId,
            String identityProviderId,
            AuthenticationEventType eventType,
            String failureReason,
            Instant occurredAt
    ) {
        return new AuthenticationEvent(
                UUID.randomUUID().toString(),
                userId,
                identityProviderId,
                null,
                null,
                eventType,
                AuthenticationProtocol.LOCAL,
                null,
                null,
                failureReason,
                null,
                occurredAt,
                null
        );
    }

    private static int incrementFailedLoginCount(int current) {
        return current == Integer.MAX_VALUE ? current : current + 1;
    }
}
