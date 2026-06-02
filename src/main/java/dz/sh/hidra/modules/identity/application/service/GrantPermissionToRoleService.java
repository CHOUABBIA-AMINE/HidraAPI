/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : GrantPermissionToRoleService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.service
 *
 * @Description : Application service granting permissions to identity roles.
 *
 */
package dz.sh.hidra.modules.identity.application.service;

import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.modules.identity.application.command.GrantPermissionToRoleCommand;
import dz.sh.hidra.modules.identity.application.dto.PermissionDto;
import dz.sh.hidra.modules.identity.application.dto.RoleDto;
import dz.sh.hidra.modules.identity.application.port.in.GrantPermissionToRoleUseCase;
import dz.sh.hidra.modules.identity.application.port.out.DomainEventPublisherPort;
import dz.sh.hidra.modules.identity.application.port.out.PermissionRepository;
import dz.sh.hidra.modules.identity.application.port.out.RoleRepository;
import dz.sh.hidra.modules.identity.domain.event.PermissionGrantedToRoleEvent;
import dz.sh.hidra.modules.identity.domain.exception.IdentityDomainException;
import dz.sh.hidra.modules.identity.domain.model.Permission;
import dz.sh.hidra.modules.identity.domain.model.Role;

import java.time.Clock;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Application service granting permissions to identity roles.
 *
 * <p>Business role: grants a catalog permission to a role.</p>
 *
 * <p>Architecture role: implements the grant-permission-to-role inbound port by loading
 * role and permission domain models, delegating grant rules to the role aggregate,
 * persisting the changed role, and publishing a domain event.</p>
 *
 * <p>Validation responsibility: rejects null dependencies and commands, fails for
 * missing roles or permissions, and delegates duplicate permission checks to the role
 * aggregate.</p>
 *
 * <p>Usage: called by future API adapters through {@link GrantPermissionToRoleUseCase}.</p>
 */
public final class GrantPermissionToRoleService implements GrantPermissionToRoleUseCase {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    private final DomainEventPublisherPort domainEventPublisher;
    private final Clock clock;

    public GrantPermissionToRoleService(
            RoleRepository roleRepository,
            PermissionRepository permissionRepository,
            DomainEventPublisherPort domainEventPublisher,
            Clock clock
    ) {
        this.roleRepository = requireNonNull(roleRepository, "RoleRepository");
        this.permissionRepository = requireNonNull(permissionRepository, "PermissionRepository");
        this.domainEventPublisher = requireNonNull(domainEventPublisher, "DomainEventPublisherPort");
        this.clock = requireNonNull(clock, "Clock");
    }

    @Override
    public RoleDto grantPermissionToRole(GrantPermissionToRoleCommand command) {
        GrantPermissionToRoleCommand requiredCommand = requireNonNull(command, "GrantPermissionToRoleCommand");

        Role role = roleRepository.findById(requiredCommand.roleId())
                .orElseThrow(() -> new IdentityDomainException("Role not found: " + requiredCommand.roleId().value() + "."));

        Permission permission = permissionRepository.findByCode(requiredCommand.permissionCode())
                .orElseThrow(() -> new IdentityDomainException(
                        "Permission not found: " + requiredCommand.permissionCode().value() + "."
                ));

        role.grantPermission(permission, clock);
        Role savedRole = roleRepository.save(role);
        Instant occurredAt = Instant.now(clock);

        domainEventPublisher.publish(
                PermissionGrantedToRoleEvent.newEvent(
                        savedRole.id(),
                        savedRole.code(),
                        permission.id(),
                        permission.code(),
                        occurredAt
                )
        );

        return toRoleDto(savedRole);
    }

    private RoleDto toRoleDto(Role role) {
        List<PermissionDto> permissions = role.permissionAssignments().stream()
                .map(assignment -> permissionRepository.findByCode(assignment.permissionCode()))
                .flatMap(Optional::stream)
                .map(this::toPermissionDto)
                .toList();

        return new RoleDto(role.id(), role.code(), role.name(), role.status(), permissions);
    }

    private PermissionDto toPermissionDto(Permission permission) {
        return new PermissionDto(permission.id(), permission.code(), permission.name(), permission.description());
    }

private static <T> T requireNonNull(T value, String fieldName) {
    if (value == null) {
        throw new InvalidValueObjectException(fieldName + " must not be null.");
    }
    return value;
}
}
