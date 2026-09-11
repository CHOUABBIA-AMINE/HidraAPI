/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityAdministrationCommandApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-11
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.service
 *
 * @Description : Orchestrates identity role, permission, and grant administration through domain repositories.
 *
 */
package dz.sh.hidra.modules.identity.application.service;

import dz.sh.hidra.modules.identity.application.command.IdentityAdministrationCommands.CreatePermission;
import dz.sh.hidra.modules.identity.application.command.IdentityAdministrationCommands.CreateRole;
import dz.sh.hidra.modules.identity.application.command.IdentityAdministrationCommands.GrantPermissionToRole;
import dz.sh.hidra.modules.identity.application.command.IdentityAdministrationCommands.GrantPermissionToUser;
import dz.sh.hidra.modules.identity.application.command.IdentityAdministrationCommands.GrantRoleToUser;
import dz.sh.hidra.modules.identity.application.port.in.IdentityAdministrationCommandUseCase;
import dz.sh.hidra.modules.identity.application.port.in.IdentityAdministrationQueryUseCase.PermissionView;
import dz.sh.hidra.modules.identity.application.port.in.IdentityAdministrationQueryUseCase.RoleView;
import dz.sh.hidra.modules.identity.application.port.out.PermissionRepositoryPort;
import dz.sh.hidra.modules.identity.application.port.out.RolePermissionGrantRepositoryPort;
import dz.sh.hidra.modules.identity.application.port.out.RoleRepositoryPort;
import dz.sh.hidra.modules.identity.application.port.out.UserPermissionGrantRepositoryPort;
import dz.sh.hidra.modules.identity.application.port.out.UserRepositoryPort;
import dz.sh.hidra.modules.identity.application.port.out.UserRoleGrantRepositoryPort;
import dz.sh.hidra.modules.identity.domain.model.Permission;
import dz.sh.hidra.modules.identity.domain.model.Role;
import dz.sh.hidra.modules.identity.domain.model.RolePermissionGrant;
import dz.sh.hidra.modules.identity.domain.model.UserPermissionGrant;
import dz.sh.hidra.modules.identity.domain.model.UserRoleGrant;
import dz.sh.hidra.modules.identity.domain.value.AuthorizationScope;
import dz.sh.hidra.modules.identity.domain.value.GrantEffect;
import dz.sh.hidra.modules.identity.domain.value.GrantStatus;
import dz.sh.hidra.modules.identity.domain.value.PermissionStatus;
import dz.sh.hidra.modules.identity.domain.value.RoleStatus;
import dz.sh.hidra.modules.identity.domain.value.RoleType;
import dz.sh.hidra.modules.identity.domain.value.ScopeType;
import java.time.Instant;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Application service for identity administration mutations.
 */
@Service
@Transactional
public class IdentityAdministrationCommandApplicationService implements IdentityAdministrationCommandUseCase {

    private static final Pattern PERMISSION_CODE = Pattern.compile("^[a-z0-9-]+:[a-z0-9-]+:[a-z0-9-]+$");

    private final UserRepositoryPort userRepository;
    private final RoleRepositoryPort roleRepository;
    private final PermissionRepositoryPort permissionRepository;
    private final UserRoleGrantRepositoryPort userRoleGrantRepository;
    private final RolePermissionGrantRepositoryPort rolePermissionGrantRepository;
    private final UserPermissionGrantRepositoryPort userPermissionGrantRepository;

    public IdentityAdministrationCommandApplicationService(
            UserRepositoryPort userRepository,
            RoleRepositoryPort roleRepository,
            PermissionRepositoryPort permissionRepository,
            UserRoleGrantRepositoryPort userRoleGrantRepository,
            RolePermissionGrantRepositoryPort rolePermissionGrantRepository,
            UserPermissionGrantRepositoryPort userPermissionGrantRepository
    ) {
        this.userRepository = Objects.requireNonNull(userRepository, "UserRepositoryPort must not be null.");
        this.roleRepository = Objects.requireNonNull(roleRepository, "RoleRepositoryPort must not be null.");
        this.permissionRepository = Objects.requireNonNull(permissionRepository, "PermissionRepositoryPort must not be null.");
        this.userRoleGrantRepository = Objects.requireNonNull(userRoleGrantRepository, "UserRoleGrantRepositoryPort must not be null.");
        this.rolePermissionGrantRepository = Objects.requireNonNull(rolePermissionGrantRepository, "RolePermissionGrantRepositoryPort must not be null.");
        this.userPermissionGrantRepository = Objects.requireNonNull(userPermissionGrantRepository, "UserPermissionGrantRepositoryPort must not be null.");
    }

    @Override
    public RoleView createRole(CreateRole command) {
        Objects.requireNonNull(command, "CreateRole command must not be null.");
        Instant now = Instant.now();
        Role role = new Role(
                UUID.randomUUID().toString(),
                requireText(command.code(), "Role code"),
                command.nameAr(),
                command.nameFr(),
                command.nameEn(),
                command.description(),
                enumValue(RoleType.class, command.roleType(), "Role type"),
                enumValue(RoleStatus.class, command.status(), "Role status"),
                now,
                now
        );
        return toView(roleRepository.save(role));
    }

    @Override
    public PermissionView createPermission(CreatePermission command) {
        Objects.requireNonNull(command, "CreatePermission command must not be null.");
        String code = requireText(command.code(), "Permission code");
        if (!PERMISSION_CODE.matcher(code).matches()) {
            throw new IllegalArgumentException("Permission code must use lower-case <context>:<resource>:<action> format.");
        }
        Instant now = Instant.now();
        Permission permission = new Permission(
                UUID.randomUUID().toString(),
                code,
                command.nameAr(),
                command.nameFr(),
                command.nameEn(),
                command.description(),
                requireText(command.permissionDomain(), "Permission domain"),
                requireText(command.resourceType(), "Permission resource type"),
                requireText(command.action(), "Permission action"),
                command.sensitive(),
                enumValue(PermissionStatus.class, command.status(), "Permission status"),
                now,
                now
        );
        return toView(permissionRepository.save(permission));
    }

    @Override
    public String grantRoleToUser(GrantRoleToUser command) {
        Objects.requireNonNull(command, "GrantRoleToUser command must not be null.");
        requireUser(command.userId());
        requireRole(command.roleId());
        validateValidity(command.validFrom(), command.validTo());
        Instant now = Instant.now();
        UserRoleGrant grant = new UserRoleGrant(
                UUID.randomUUID().toString(),
                command.userId(),
                command.roleId(),
                scope(command.scopeType(), command.scopeReferenceId(), command.scopeCodeSnapshot()),
                command.reason(),
                command.approvedByWorkflowId(),
                effectiveFrom(command.validFrom(), now),
                command.validTo(),
                GrantStatus.ACTIVE,
                now,
                null,
                null
        );
        return userRoleGrantRepository.save(grant).id();
    }

    @Override
    public String grantPermissionToRole(GrantPermissionToRole command) {
        Objects.requireNonNull(command, "GrantPermissionToRole command must not be null.");
        requireRole(command.roleId());
        requirePermission(command.permissionId());
        validateValidity(command.validFrom(), command.validTo());
        Instant now = Instant.now();
        RolePermissionGrant grant = new RolePermissionGrant(
                UUID.randomUUID().toString(),
                command.roleId(),
                command.permissionId(),
                enumValue(GrantEffect.class, command.effect(), "Grant effect"),
                command.conditionExpression(),
                effectiveFrom(command.validFrom(), now),
                command.validTo(),
                GrantStatus.ACTIVE,
                now
        );
        return rolePermissionGrantRepository.save(grant).id();
    }

    @Override
    public String grantPermissionToUser(GrantPermissionToUser command) {
        Objects.requireNonNull(command, "GrantPermissionToUser command must not be null.");
        requireUser(command.userId());
        requirePermission(command.permissionId());
        validateValidity(command.validFrom(), command.validTo());
        Instant now = Instant.now();
        UserPermissionGrant grant = new UserPermissionGrant(
                UUID.randomUUID().toString(),
                command.userId(),
                command.permissionId(),
                enumValue(GrantEffect.class, command.effect(), "Grant effect"),
                scope(command.scopeType(), command.scopeReferenceId(), command.scopeCodeSnapshot()),
                command.reason(),
                command.approvedByWorkflowId(),
                command.emergencyAccess(),
                effectiveFrom(command.validFrom(), now),
                command.validTo(),
                GrantStatus.ACTIVE,
                now,
                null
        );
        return userPermissionGrantRepository.save(grant).id();
    }

    private void requireUser(String id) {
        String normalized = requireText(id, "User id");
        userRepository.findById(normalized)
                .orElseThrow(() -> new NoSuchElementException("Unknown identity user: " + normalized));
    }

    private void requireRole(String id) {
        String normalized = requireText(id, "Role id");
        roleRepository.findById(normalized)
                .orElseThrow(() -> new NoSuchElementException("Unknown identity role: " + normalized));
    }

    private void requirePermission(String id) {
        String normalized = requireText(id, "Permission id");
        permissionRepository.findById(normalized)
                .orElseThrow(() -> new NoSuchElementException("Unknown identity permission: " + normalized));
    }

    private static AuthorizationScope scope(String scopeType, String referenceId, String codeSnapshot) {
        if (scopeType == null || scopeType.isBlank()) {
            return AuthorizationScope.global();
        }
        ScopeType type = enumValue(ScopeType.class, scopeType, "Scope type");
        return new AuthorizationScope(type, referenceId, codeSnapshot);
    }

    private static Instant effectiveFrom(Instant validFrom, Instant now) {
        return validFrom == null ? now : validFrom;
    }

    private static void validateValidity(Instant validFrom, Instant validTo) {
        if (validFrom != null && validTo != null && validFrom.isAfter(validTo)) {
            throw new IllegalArgumentException("validFrom must not be after validTo.");
        }
    }

    private static String requireText(String value, String label) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(label + " is required.");
        }
        return value.trim();
    }

    private static <E extends Enum<E>> E enumValue(Class<E> type, String value, String label) {
        String normalized = requireText(value, label).toUpperCase(Locale.ROOT);
        try {
            return Enum.valueOf(type, normalized);
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("Unsupported " + label.toLowerCase(Locale.ROOT) + ": " + value, exception);
        }
    }

    private static RoleView toView(Role role) {
        return new RoleView(
                role.id(),
                role.code(),
                role.nameAr(),
                role.nameFr(),
                role.nameEn(),
                role.description(),
                String.valueOf(role.roleType()),
                String.valueOf(role.status())
        );
    }

    private static PermissionView toView(Permission permission) {
        return new PermissionView(
                permission.id(),
                permission.code(),
                permission.nameAr(),
                permission.nameFr(),
                permission.nameEn(),
                permission.description(),
                permission.permissionDomain(),
                permission.resourceType(),
                permission.action(),
                permission.sensitive(),
                String.valueOf(permission.status())
        );
    }
}
