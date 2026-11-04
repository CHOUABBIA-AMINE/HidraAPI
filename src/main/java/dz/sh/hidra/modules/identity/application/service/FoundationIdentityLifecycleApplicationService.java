/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : FoundationIdentityLifecycleApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-03
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.service
 *
 * @Description : Coordinates authorized atomic Identity lifecycle operations.
 *
 */
package dz.sh.hidra.modules.identity.application.service;

import dz.sh.hidra.modules.identity.application.port.in.FoundationIdentityLifecycleUseCase;
import dz.sh.hidra.modules.identity.application.port.out.FoundationIdentityLifecyclePersistencePort;
import dz.sh.hidra.modules.identity.application.port.out.FoundationIdentityAuthorizationPort;
import dz.sh.hidra.modules.identity.domain.service.IdentityLifecyclePolicy;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FoundationIdentityLifecycleApplicationService implements FoundationIdentityLifecycleUseCase {
    private final FoundationIdentityLifecyclePersistencePort persistence;
    private final FoundationIdentityAuthorizationPort authorization;
    private final PasswordEncoder passwords;
    public FoundationIdentityLifecycleApplicationService(FoundationIdentityLifecyclePersistencePort persistence,
            FoundationIdentityAuthorizationPort authorization, PasswordEncoder passwords) {
        this.persistence = Objects.requireNonNull(persistence);
        this.authorization = Objects.requireNonNull(authorization);
        this.passwords = Objects.requireNonNull(passwords);
    }

    @Override
    @Transactional(readOnly = true)
    public UserState currentUser(String userId) {
        return persistence.readUser(userId, authorization.administratorActor());
    }

    @Override
    @Transactional(readOnly = true)
    public CredentialState currentCredential(String userId) {
        return persistence.readCredential(userId, authorization.administratorActor());
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public UserState activate(String userId, Instant expected, String reason) {
        IdentityLifecyclePolicy.reason(reason);
        return persistence.changeUser(userId, expected, true, reason.strip(), authorization.administratorActor());
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public UserState deactivate(String userId, Instant expected, String reason) {
        IdentityLifecyclePolicy.reason(reason);
        return persistence.changeUser(userId, expected, false, reason.strip(), authorization.administratorActor());
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public CredentialState credential(String userId, String password, long expectedVersion, String reason) {
        IdentityLifecyclePolicy.reason(reason);
        if (password == null || password.length() < 12 || password.length() > 128)
            throw new IllegalArgumentException("LOCAL password must have between 12 and 128 characters.");
        if (expectedVersion < 0) throw new IllegalArgumentException("Invalid credential generation.");
        // The raw password is used only for hashing; it is never returned, persisted or logged.
        String hash = passwords.encode(password);
        return persistence.setCredential(userId, hash, expectedVersion, reason.strip(), authorization.administratorActor());
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    public GrantState revoke(GrantKind kind, String id, Instant expected, String reason) {
        IdentityLifecyclePolicy.reason(reason);
        return persistence.revokeGrant(Objects.requireNonNull(kind), id, expected, reason.strip(), authorization.administratorActor());
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> capabilities(String companyId, String resourceType, String resourceId) {
        String actor = authorization.authenticatedActor();
        // Advisory only. A company/resource selector never grants access to the resource.
        if ((companyId == null || companyId.isBlank()) && (resourceType == null || resourceType.isBlank())
                && (resourceId == null || resourceId.isBlank()) && authorization.currentAdministrator(actor))
            return List.of("VIEW_OWN_PROFILE", "MANAGE_USERS", "MANAGE_GRANTS");
        return List.of("VIEW_OWN_PROFILE");
    }
}
