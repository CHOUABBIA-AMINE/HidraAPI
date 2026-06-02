/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateRoleService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.service
 *
 * @Description : Application service creating identity roles.
 *
 */
package dz.sh.hidra.modules.identity.application.service;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.kernel.domain.exception.InvalidValueObjectException;
import dz.sh.hidra.modules.identity.application.command.CreateRoleCommand;
import dz.sh.hidra.modules.identity.application.dto.RoleDto;
import dz.sh.hidra.modules.identity.application.port.in.CreateRoleUseCase;
import dz.sh.hidra.modules.identity.application.port.out.DomainEventPublisherPort;
import dz.sh.hidra.modules.identity.application.port.out.RoleRepository;
import dz.sh.hidra.modules.identity.domain.event.RoleCreatedEvent;
import dz.sh.hidra.modules.identity.domain.model.Role;
import dz.sh.hidra.modules.identity.domain.value.RoleId;

import java.time.Clock;
import java.time.Instant;
import java.util.List;

/**
 * Application service creating identity roles.
 *
 * <p>Business role: creates a role when its code is unique in the identity module.</p>
 *
 * <p>Architecture role: implements the create-role inbound port by orchestrating role
 * aggregate creation, repository persistence, and domain-event publishing.</p>
 *
 * <p>Validation responsibility: rejects null dependencies and commands, prevents
 * duplicate role codes, and delegates value validation to domain value objects.</p>
 *
 * <p>Usage: called by future API adapters through {@link CreateRoleUseCase}.</p>
 */
public final class CreateRoleService implements CreateRoleUseCase {

    private final RoleRepository roleRepository;
    private final DomainEventPublisherPort domainEventPublisher;
    private final Clock clock;

    public CreateRoleService(
            RoleRepository roleRepository,
            DomainEventPublisherPort domainEventPublisher,
            Clock clock
    ) {
        this.roleRepository = requireNonNull(roleRepository, "RoleRepository");
        this.domainEventPublisher = requireNonNull(domainEventPublisher, "DomainEventPublisherPort");
        this.clock = requireNonNull(clock, "Clock");
    }

    @Override
    public RoleDto createRole(CreateRoleCommand command) {
        CreateRoleCommand requiredCommand = requireNonNull(command, "CreateRoleCommand");

        if (roleRepository.existsByCode(requiredCommand.roleCode())) {
            throw new BusinessRuleViolationException(
                    "Role code already exists: " + requiredCommand.roleCode().value() + "."
            );
        }

        Role role = Role.create(RoleId.newId(), requiredCommand.roleCode(), requiredCommand.roleName());
        Role savedRole = roleRepository.save(role);
        Instant occurredAt = Instant.now(clock);

        domainEventPublisher.publish(
                RoleCreatedEvent.newEvent(savedRole.id(), savedRole.code(), savedRole.name(), occurredAt)
        );

        return new RoleDto(savedRole.id(), savedRole.code(), savedRole.name(), savedRole.status(), List.of());
    }

private static <T> T requireNonNull(T value, String fieldName) {
    if (value == null) {
        throw new InvalidValueObjectException(fieldName + " must not be null.");
    }
    return value;
}
}
