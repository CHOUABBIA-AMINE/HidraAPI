/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ResponsibilityAssignmentApplicationServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
 *
 * @Type        : Class
 * @Layer       : Organization Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.service
 *
 * @Description : Tests canonical scope validation, idempotency and overlap policy.
 *
 */
package dz.sh.hidra.modules.organization.application.service;

import dz.sh.hidra.modules.organization.application.command.AssignResponsibilityCommand;
import dz.sh.hidra.modules.organization.application.port.out.EmployeeRepositoryPort;
import dz.sh.hidra.modules.organization.application.port.out.OperationalScopeRegistryRepositoryPort;
import dz.sh.hidra.modules.organization.application.port.out.OperationalScopeTargetResolverPort;
import dz.sh.hidra.modules.organization.application.port.out.OrganizationUnitRepositoryPort;
import dz.sh.hidra.modules.organization.application.port.out.ResponsibilityAssignmentRepositoryPort;
import dz.sh.hidra.modules.organization.domain.model.Employee;
import dz.sh.hidra.modules.organization.domain.model.OperationalScope;
import dz.sh.hidra.modules.organization.domain.model.OrganizationUnit;
import dz.sh.hidra.modules.organization.domain.model.ResponsibilityAssignment;
import dz.sh.hidra.modules.organization.domain.value.AssignmentStatus;
import dz.sh.hidra.modules.organization.domain.value.EmployeeStatus;
import dz.sh.hidra.modules.organization.domain.value.EmployeeType;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitStatus;
import dz.sh.hidra.modules.organization.domain.value.OperationalScopeType;
import dz.sh.hidra.modules.organization.domain.value.ResponsibilityType;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ResponsibilityAssignmentApplicationServiceTest {

    @Test
    void assignsResponsibilityUsingExistingRegistryScope() {
        AtomicInteger saveCalls = new AtomicInteger();
        AtomicReference<ResponsibilityAssignment> saved = new AtomicReference<>();

        ResponsibilityAssignmentApplicationService service =
                new ResponsibilityAssignmentApplicationService(
                        scopeRepository(Optional.of(scope())),
                        resolver(true),
                        employeeRepository(),
                        organizationUnitRepository(OrganizationUnitStatus.ACTIVE),
                        assignmentRepository(List.of(), saveCalls, saved)
                );

        String id = service.assignResponsibility(command(
                Instant.parse("2026-09-27T09:00:00Z"),
                null
        ));

        assertEquals("saved-assignment-id", id);
        assertEquals(1, saveCalls.get());
        assertEquals(42L, saved.get().scopeId());
        assertEquals("unit-001", saved.get().assigneeId());
    }

    @Test
    void exactReplayReturnsExistingAssignmentIdWithoutSavingDuplicate() {
        AtomicInteger saveCalls = new AtomicInteger();
        Instant start = Instant.parse("2026-09-27T09:00:00Z");
        Instant end = Instant.parse("2026-09-27T12:00:00Z");

        ResponsibilityAssignment existing = existing("existing-id", start, end);

        ResponsibilityAssignmentApplicationService service =
                new ResponsibilityAssignmentApplicationService(
                        scopeRepository(Optional.of(scope())),
                        resolver(true),
                        employeeRepository(),
                        organizationUnitRepository(OrganizationUnitStatus.ACTIVE),
                        assignmentRepository(List.of(existing), saveCalls, new AtomicReference<>())
                );

        String id = service.assignResponsibility(command(start, end));

        assertEquals("existing-id", id);
        assertEquals(0, saveCalls.get());
    }

    @Test
    void overlappingActiveAssignmentIsRejected() {
        AtomicInteger saveCalls = new AtomicInteger();

        ResponsibilityAssignment existing = existing(
                "existing-id",
                Instant.parse("2026-09-27T09:00:00Z"),
                Instant.parse("2026-09-27T12:00:00Z")
        );

        ResponsibilityAssignmentApplicationService service =
                new ResponsibilityAssignmentApplicationService(
                        scopeRepository(Optional.of(scope())),
                        resolver(true),
                        employeeRepository(),
                        organizationUnitRepository(OrganizationUnitStatus.ACTIVE),
                        assignmentRepository(List.of(existing), saveCalls, new AtomicReference<>())
                );

        assertThrows(
                IllegalStateException.class,
                () -> service.assignResponsibility(command(
                        Instant.parse("2026-09-27T11:00:00Z"),
                        Instant.parse("2026-09-27T13:00:00Z")
                ))
        );

        assertEquals(0, saveCalls.get());
    }

    @Test
    void adjacentHalfOpenPeriodIsAllowed() {
        AtomicInteger saveCalls = new AtomicInteger();

        ResponsibilityAssignment existing = existing(
                "existing-id",
                Instant.parse("2026-09-27T09:00:00Z"),
                Instant.parse("2026-09-27T12:00:00Z")
        );

        ResponsibilityAssignmentApplicationService service =
                new ResponsibilityAssignmentApplicationService(
                        scopeRepository(Optional.of(scope())),
                        resolver(true),
                        employeeRepository(),
                        organizationUnitRepository(OrganizationUnitStatus.ACTIVE),
                        assignmentRepository(List.of(existing), saveCalls, new AtomicReference<>())
                );

        service.assignResponsibility(command(
                Instant.parse("2026-09-27T12:00:00Z"),
                Instant.parse("2026-09-27T15:00:00Z")
        ));

        assertEquals(1, saveCalls.get());
    }

    @Test
    void unknownScopeIsRejectedBeforeAssignmentPersistence() {
        AtomicInteger saveCalls = new AtomicInteger();

        ResponsibilityAssignmentApplicationService service =
                new ResponsibilityAssignmentApplicationService(
                        scopeRepository(Optional.empty()),
                        resolver(true),
                        employeeRepository(),
                        organizationUnitRepository(OrganizationUnitStatus.ACTIVE),
                        assignmentRepository(List.of(), saveCalls, new AtomicReference<>())
                );

        assertThrows(
                IllegalArgumentException.class,
                () -> service.assignResponsibility(command(null, null))
        );

        assertEquals(0, saveCalls.get());
    }

    @Test
    void invalidEffectiveIntervalIsRejectedByCommand() {
        Instant start = Instant.parse("2026-09-27T10:00:00Z");

        assertThrows(
                IllegalArgumentException.class,
                () -> command(start, start)
        );
    }

    @Test
    void inactiveAssigneeAndRetiredOwnerAreRejected() {
        var inactiveUnit = new ResponsibilityAssignmentApplicationService(
                scopeRepository(Optional.of(scope())), resolver(true), employeeRepository(),
                organizationUnitRepository(OrganizationUnitStatus.INACTIVE),
                assignmentRepository(List.of(), new AtomicInteger(), new AtomicReference<>()));
        assertThrows(IllegalArgumentException.class, () -> inactiveUnit.assignResponsibility(command(null, null)));

        var retiredOwner = new ResponsibilityAssignmentApplicationService(
                scopeRepository(Optional.of(scope())), resolver(false), employeeRepository(),
                organizationUnitRepository(OrganizationUnitStatus.ACTIVE),
                assignmentRepository(List.of(), new AtomicInteger(), new AtomicReference<>()));
        assertThrows(IllegalArgumentException.class, () -> retiredOwner.assignResponsibility(command(null, null)));
    }

    private static OperationalScopeTargetResolverPort resolver(boolean assignable) {
        return new OperationalScopeTargetResolverPort() {
            @Override public boolean supports(OperationalScopeType type) { return type == OperationalScopeType.PIPELINE; }
            @Override public Optional<ResolvedTarget> resolve(OperationalScopeType type, String targetId) {
                return Optional.of(new ResolvedTarget(type, targetId, "PL-009", "Pipeline 009", assignable));
            }
        };
    }

    private static EmployeeRepositoryPort employeeRepository() {
        return new EmployeeRepositoryPort() {
            @Override public Employee save(Employee model) { return model; }
            @Override public Optional<Employee> findById(String id) {
                return Optional.of(new Employee(id, "E-1", null, null, "A", "B", null, "A B", null, null,
                        EmployeeType.PERMANENT, EmployeeStatus.ACTIVE, null, null, null, null, null));
            }
        };
    }

    private static OrganizationUnitRepositoryPort organizationUnitRepository(OrganizationUnitStatus status) {
        return new OrganizationUnitRepositoryPort() {
            @Override public OrganizationUnit save(OrganizationUnit model) { return model; }
            @Override public Optional<OrganizationUnit> findById(String id) {
                Instant now = Instant.parse("2026-09-27T08:00:00Z");
                return Optional.of(new OrganizationUnit(id, "OU-1", null, null, "Unit", "type-1", null,
                        status, now, null, now, now));
            }
        };
    }

    private static OperationalScope scope() {
        return new OperationalScope(42L, OperationalScopeType.PIPELINE, "pipeline-009");
    }

    private static AssignResponsibilityCommand command(Instant validFrom, Instant validTo) {
        return new AssignResponsibilityCommand(
                ResponsibilityType.RESPONSIBLE,
                "ORGANIZATION_UNIT",
                "unit-001",
                42L,
                "Primary operational responsibility",
                validFrom,
                validTo
        );
    }

    private static ResponsibilityAssignment existing(
            String id,
            Instant validFrom,
            Instant validTo
    ) {
        Instant created = Instant.parse("2026-09-27T08:00:00Z");
        return new ResponsibilityAssignment(
                id,
                ResponsibilityType.RESPONSIBLE,
                "ORGANIZATION_UNIT",
                "unit-001",
                42L,
                "Existing responsibility",
                validFrom,
                validTo,
                AssignmentStatus.ACTIVE,
                created,
                created
        );
    }

    private static OperationalScopeRegistryRepositoryPort scopeRepository(
            Optional<OperationalScope> scope
    ) {
        return new OperationalScopeRegistryRepositoryPort() {
            @Override
            public OperationalScope register(OperationalScopeType type, String targetId) {
                throw new UnsupportedOperationException();
            }

            @Override
            public Optional<OperationalScope> findById(Long scopeId) {
                return scope;
            }

            @Override
            public Optional<OperationalScope> findByTypeAndTargetId(
                    OperationalScopeType type,
                    String targetId
            ) {
                return Optional.empty();
            }

            @Override
            public Optional<OperationalScope> findGlobal() {
                return Optional.empty();
            }
        };
    }

    private static ResponsibilityAssignmentRepositoryPort assignmentRepository(
            List<ResponsibilityAssignment> existing,
            AtomicInteger saveCalls,
            AtomicReference<ResponsibilityAssignment> saved
    ) {
        return new ResponsibilityAssignmentRepositoryPort() {
            @Override
            public ResponsibilityAssignment save(ResponsibilityAssignment model) {
                saveCalls.incrementAndGet();
                saved.set(model);
                return new ResponsibilityAssignment(
                        "saved-assignment-id",
                        model.responsibilityType(),
                        model.assigneeType(),
                        model.assigneeId(),
                        model.scopeId(),
                        model.description(),
                        model.validFrom(),
                        model.validTo(),
                        model.status(),
                        model.createdAt(),
                        model.updatedAt()
                );
            }

            @Override
            public Optional<ResponsibilityAssignment> findById(String id) {
                return Optional.empty();
            }

            @Override
            public List<ResponsibilityAssignment> findActiveByAssigneeAndResponsibilityAndScope(
                    String assigneeType,
                    String assigneeId,
                    ResponsibilityType responsibilityType,
                    Long scopeId
            ) {
                return existing;
            }
        };
    }
}
