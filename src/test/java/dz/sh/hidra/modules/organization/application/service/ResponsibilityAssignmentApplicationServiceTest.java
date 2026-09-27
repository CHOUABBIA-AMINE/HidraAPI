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
 * @Description : Tests responsibility assignment against canonical scope registry IDs.
 *
 */
package dz.sh.hidra.modules.organization.application.service;

import dz.sh.hidra.modules.organization.application.command.AssignResponsibilityCommand;
import dz.sh.hidra.modules.organization.application.port.out.OperationalScopeRegistryRepositoryPort;
import dz.sh.hidra.modules.organization.application.port.out.ResponsibilityAssignmentRepositoryPort;
import dz.sh.hidra.modules.organization.domain.model.OperationalScope;
import dz.sh.hidra.modules.organization.domain.model.ResponsibilityAssignment;
import dz.sh.hidra.modules.organization.domain.value.OperationalScopeType;
import dz.sh.hidra.modules.organization.domain.value.ResponsibilityType;
import org.junit.jupiter.api.Test;

import java.time.Instant;
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

        OperationalScopeRegistryRepositoryPort scopeRepository = scopeRepository(
                Optional.of(new OperationalScope(42L, OperationalScopeType.PIPELINE, "pipeline-009"))
        );

        ResponsibilityAssignmentRepositoryPort assignmentRepository =
                assignmentRepository(saveCalls, saved);

        ResponsibilityAssignmentApplicationService service =
                new ResponsibilityAssignmentApplicationService(scopeRepository, assignmentRepository);

        String id = service.assignResponsibility(
                new AssignResponsibilityCommand(
                        ResponsibilityType.RESPONSIBLE,
                        "ORGANIZATION_UNIT",
                        "unit-001",
                        42L,
                        "Primary operational responsibility",
                        Instant.parse("2026-09-27T09:00:00Z"),
                        null
                )
        );

        assertEquals("saved-assignment-id", id);
        assertEquals(1, saveCalls.get());
        assertEquals(42L, saved.get().scopeId());
        assertEquals("unit-001", saved.get().assigneeId());
    }

    @Test
    void unknownScopeIsRejectedBeforeAssignmentPersistence() {
        AtomicInteger saveCalls = new AtomicInteger();

        ResponsibilityAssignmentApplicationService service =
                new ResponsibilityAssignmentApplicationService(
                        scopeRepository(Optional.empty()),
                        assignmentRepository(saveCalls, new AtomicReference<>())
                );

        assertThrows(
                IllegalArgumentException.class,
                () -> service.assignResponsibility(
                        new AssignResponsibilityCommand(
                                ResponsibilityType.ACCOUNTABLE,
                                "EMPLOYEE",
                                "employee-001",
                                999L,
                                null,
                                null,
                                null
                        )
                )
        );

        assertEquals(0, saveCalls.get());
    }

    @Test
    void invalidEffectiveIntervalIsRejected() {
        Instant start = Instant.parse("2026-09-27T10:00:00Z");

        assertThrows(
                IllegalArgumentException.class,
                () -> new AssignResponsibilityCommand(
                        ResponsibilityType.OWNER,
                        "ORGANIZATION_UNIT",
                        "unit-001",
                        42L,
                        null,
                        start,
                        start
                )
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
        };
    }
}
