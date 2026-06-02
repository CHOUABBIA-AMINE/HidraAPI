/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateRoleServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Identity Application Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.service
 *
 * @Description : Tests identity role creation application service.
 *
 */
package dz.sh.hidra.modules.identity.application.service;

import dz.sh.hidra.kernel.domain.event.DomainEvent;
import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.identity.application.command.CreateRoleCommand;
import dz.sh.hidra.modules.identity.application.dto.RoleDto;
import dz.sh.hidra.modules.identity.application.port.out.DomainEventPublisherPort;
import dz.sh.hidra.modules.identity.application.port.out.RoleRepository;
import dz.sh.hidra.modules.identity.domain.model.Role;
import dz.sh.hidra.modules.identity.domain.value.RoleCode;
import dz.sh.hidra.modules.identity.domain.value.RoleId;
import dz.sh.hidra.modules.identity.domain.value.RoleName;
import dz.sh.hidra.modules.identity.domain.value.RoleStatus;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests identity role creation application service.
 *
 * <p>Business role: verifies that the create-role use case creates roles only when role
 * code is unique.</p>
 *
 * <p>Architecture role: application service test using in-memory application ports. It
 * does not depend on Spring, JPA, platform security, REST controllers, or organization
 * modules.</p>
 *
 * <p>Validation responsibility: covers successful save/event publication and duplicate
 * role code rejection.</p>
 *
 * <p>Usage: executed by the identity application test suite.</p>
 */
class CreateRoleServiceTest {

    private static final Clock FIXED_CLOCK = Clock.fixed(
            Instant.parse("2026-05-30T10:15:30Z"),
            ZoneOffset.UTC
    );

    @Test
    void createRoleShouldCreateRoleAndPublishEventWhenCodeIsUnique() {
        InMemoryRoleRepository roleRepository = new InMemoryRoleRepository();
        CapturingDomainEventPublisher publisher = new CapturingDomainEventPublisher();
        CreateRoleService service = new CreateRoleService(roleRepository, publisher, FIXED_CLOCK);

        RoleDto result = service.createRole(new CreateRoleCommand(
                RoleCode.of("IDENTITY_ADMIN"),
                RoleName.of("Identity Administrator")
        ));

        assertEquals("IDENTITY_ADMIN", result.code().value());
        assertEquals(RoleStatus.ACTIVE, result.status());
        assertEquals(1, roleRepository.savedRoles);
        assertEquals(1, publisher.events.size());
        assertEquals("identity.role.created", publisher.events.get(0).eventType());
    }

    @Test
    void createRoleShouldRejectDuplicateRoleCode() {
        InMemoryRoleRepository roleRepository = new InMemoryRoleRepository();
        roleRepository.save(Role.create(
                RoleId.of("existing-role"),
                RoleCode.of("IDENTITY_ADMIN"),
                RoleName.of("Identity Administrator")
        ));

        CreateRoleService service = new CreateRoleService(
                roleRepository,
                new CapturingDomainEventPublisher(),
                FIXED_CLOCK
        );

        CreateRoleCommand command = new CreateRoleCommand(
                RoleCode.of("IDENTITY_ADMIN"),
                RoleName.of("Identity Administrator")
        );

        assertThrows(BusinessRuleViolationException.class, () -> service.createRole(command));
    }

    private static final class InMemoryRoleRepository implements RoleRepository {

        private final Map<String, Role> rolesById = new LinkedHashMap<>();
        private int savedRoles;

        @Override
        public Role save(Role role) {
            rolesById.put(role.id().value(), role);
            savedRoles++;
            return role;
        }

        @Override
        public Optional<Role> findById(RoleId roleId) {
            return Optional.ofNullable(rolesById.get(roleId.value()));
        }

        @Override
        public Optional<Role> findByCode(RoleCode roleCode) {
            return rolesById.values().stream()
                    .filter(role -> role.code().equals(roleCode))
                    .findFirst();
        }

        @Override
        public List<Role> findAll() {
            return new ArrayList<>(rolesById.values());
        }

        @Override
        public boolean existsByCode(RoleCode roleCode) {
            return findByCode(roleCode).isPresent();
        }
    }

    private static final class CapturingDomainEventPublisher implements DomainEventPublisherPort {

        private final List<DomainEvent> events = new ArrayList<>();

        @Override
        public void publish(DomainEvent event) {
            events.add(event);
        }

        @Override
        public void publishAll(List<? extends DomainEvent> events) {
            this.events.addAll(events);
        }
    }
}
