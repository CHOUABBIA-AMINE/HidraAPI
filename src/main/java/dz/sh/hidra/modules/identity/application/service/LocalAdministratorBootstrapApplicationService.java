/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LocalAdministratorBootstrapApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-15
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.service
 *
 * @Description : Safely provisions one persistent LOCAL administrator without overwriting existing identity state.
 *
 */
package dz.sh.hidra.modules.identity.application.service;

import dz.sh.hidra.modules.identity.application.port.in.BootstrapLocalAdministratorUseCase;
import dz.sh.hidra.modules.identity.application.port.out.LocalCredentialRepositoryPort;
import dz.sh.hidra.modules.identity.application.port.out.LocalPasswordHashPort;
import dz.sh.hidra.modules.identity.application.port.out.RoleRepositoryPort;
import dz.sh.hidra.modules.identity.application.port.out.UserRepositoryPort;
import dz.sh.hidra.modules.identity.application.port.out.UserRoleGrantRepositoryPort;
import dz.sh.hidra.modules.identity.domain.model.LocalCredential;
import dz.sh.hidra.modules.identity.domain.model.Role;
import dz.sh.hidra.modules.identity.domain.model.User;
import dz.sh.hidra.modules.identity.domain.model.UserRoleGrant;
import dz.sh.hidra.modules.identity.domain.value.AuthorizationScope;
import dz.sh.hidra.modules.identity.domain.value.GrantStatus;
import dz.sh.hidra.modules.identity.domain.value.IdentityId;
import dz.sh.hidra.modules.identity.domain.value.RoleStatus;
import dz.sh.hidra.modules.identity.domain.value.RoleType;
import dz.sh.hidra.modules.identity.domain.value.UserStatus;
import dz.sh.hidra.modules.identity.domain.value.UserType;
import java.time.Instant;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Persistent, idempotent LOCAL administrator bootstrap service.
 */
@Service
@Transactional
public final class LocalAdministratorBootstrapApplicationService implements BootstrapLocalAdministratorUseCase {

    static final String ADMIN_ROLE_CODE = "HIDRA_ADMIN";
    private static final String ACTIVE_CREDENTIAL_STATUS = "ACTIVE";
    private static final String BOOTSTRAP_GRANT_REASON = "AUTH-021 persistent LOCAL administrator bootstrap";

    private final UserRepositoryPort userRepository;
    private final LocalCredentialRepositoryPort localCredentialRepository;
    private final RoleRepositoryPort roleRepository;
    private final UserRoleGrantRepositoryPort userRoleGrantRepository;
    private final LocalPasswordHashPort passwordHashPort;

    public LocalAdministratorBootstrapApplicationService(
            UserRepositoryPort userRepository,
            LocalCredentialRepositoryPort localCredentialRepository,
            RoleRepositoryPort roleRepository,
            UserRoleGrantRepositoryPort userRoleGrantRepository,
            LocalPasswordHashPort passwordHashPort
    ) {
        this.userRepository = Objects.requireNonNull(userRepository, "UserRepositoryPort must not be null.");
        this.localCredentialRepository = Objects.requireNonNull(localCredentialRepository, "LocalCredentialRepositoryPort must not be null.");
        this.roleRepository = Objects.requireNonNull(roleRepository, "RoleRepositoryPort must not be null.");
        this.userRoleGrantRepository = Objects.requireNonNull(userRoleGrantRepository, "UserRoleGrantRepositoryPort must not be null.");
        this.passwordHashPort = Objects.requireNonNull(passwordHashPort, "LocalPasswordHashPort must not be null.");
    }

    @Override
    public BootstrapResult bootstrap(BootstrapCommand command) {
        Objects.requireNonNull(command, "Bootstrap command must not be null.");
        String username = requireText(command.username(), "Bootstrap username");
        String password = requireText(command.password(), "Bootstrap password");

        Role administratorRole = roleRepository.findByCode(ADMIN_ROLE_CODE)
                .map(this::requireUsableAdministratorRole)
                .orElseGet(this::createAdministratorRole);

        return userRepository.findByUsername(username)
                .map(existing -> existingBootstrap(existing, administratorRole))
                .orElseGet(() -> createBootstrapAdministrator(command, username, password, administratorRole));
    }

    private BootstrapResult existingBootstrap(User user, Role administratorRole) {
        boolean activeCredential = localCredentialRepository.findByUserId(user.id())
                .map(credential -> ACTIVE_CREDENTIAL_STATUS.equalsIgnoreCase(credential.credentialStatus()))
                .orElse(false);
        boolean activeAdminGrant = userRoleGrantRepository
                .findActiveByUserIdAndRoleId(user.id(), administratorRole.id())
                .isPresent();

        if (user.status() == UserStatus.ACTIVE && activeCredential && activeAdminGrant) {
            return new BootstrapResult(user.id(), user.username(), ADMIN_ROLE_CODE, false);
        }

        throw new IllegalStateException(
                "Bootstrap username already exists but is not the fully provisioned persistent LOCAL administrator; refusing to modify existing identity state."
        );
    }

    private BootstrapResult createBootstrapAdministrator(
            BootstrapCommand command,
            String username,
            String password,
            Role administratorRole
    ) {
        Instant now = Instant.now();
        String userId = IdentityId.newId().value();
        User user = new User(
                userId,
                username,
                normalize(command.emailAddress()),
                normalize(command.displayName()),
                UserType.HUMAN,
                UserStatus.ACTIVE,
                null,
                null,
                0,
                null,
                now,
                now,
                null,
                null,
                now
        );
        User persistedUser = userRepository.save(user);

        localCredentialRepository.save(new LocalCredential(
                IdentityId.newId().value(),
                persistedUser.id(),
                passwordHashPort.hash(password),
                ACTIVE_CREDENTIAL_STATUS,
                now,
                now,
                now
        ));

        userRoleGrantRepository.save(new UserRoleGrant(
                IdentityId.newId().value(),
                persistedUser.id(),
                administratorRole.id(),
                AuthorizationScope.global(),
                BOOTSTRAP_GRANT_REASON,
                null,
                now,
                null,
                GrantStatus.ACTIVE,
                now,
                null,
                null
        ));

        return new BootstrapResult(persistedUser.id(), persistedUser.username(), ADMIN_ROLE_CODE, true);
    }

    private Role createAdministratorRole() {
        Instant now = Instant.now();
        return roleRepository.save(new Role(
                IdentityId.newId().value(),
                ADMIN_ROLE_CODE,
                null,
                null,
                "Hidra Administrator",
                "System administrator role used by the controlled persistent LOCAL bootstrap.",
                RoleType.ADMIN,
                RoleStatus.ACTIVE,
                now,
                now
        ));
    }

    private Role requireUsableAdministratorRole(Role role) {
        if (role.status() != RoleStatus.ACTIVE) {
            throw new IllegalStateException("Existing HIDRA_ADMIN role is not active; bootstrap refuses to alter it.");
        }
        return role;
    }

    private static String requireText(String value, String label) {
        String normalized = normalize(value);
        if (normalized == null) {
            throw new IllegalArgumentException(label + " is required.");
        }
        return normalized;
    }

    private static String normalize(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.trim();
    }
}
