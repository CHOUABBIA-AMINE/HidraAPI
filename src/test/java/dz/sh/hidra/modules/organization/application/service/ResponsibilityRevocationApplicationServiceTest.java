/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ResponsibilityRevocationApplicationServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
 *
 * @Type        : Class
 * @Layer       : Organization Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.service
 *
 * @Description : Tests historical responsibility revocation and idempotent ending.
 *
 */
package dz.sh.hidra.modules.organization.application.service;

import dz.sh.hidra.modules.organization.application.command.RevokeResponsibilityCommand;
import dz.sh.hidra.modules.organization.application.port.out.ResponsibilityAssignmentRepositoryPort;
import dz.sh.hidra.modules.organization.domain.model.ResponsibilityAssignment;
import dz.sh.hidra.modules.organization.domain.value.AssignmentStatus;
import dz.sh.hidra.modules.organization.domain.value.ResponsibilityType;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ResponsibilityRevocationApplicationServiceTest {

    private static final Instant START = Instant.parse("2026-09-27T08:00:00Z");
    private static final Instant END = Instant.parse("2026-09-27T12:00:00Z");
    private static final Instant CREATED = Instant.parse("2026-09-27T07:00:00Z");

    @Test
    void endsActiveAssignmentAndPreservesIdentityAndHistory() {
        AtomicInteger saveCalls = new AtomicInteger();
        AtomicReference<ResponsibilityAssignment> saved = new AtomicReference<>();

        ResponsibilityRevocationApplicationService service =
                new ResponsibilityRevocationApplicationService(
                        repository(Optional.of(assignment(AssignmentStatus.ACTIVE, null)), saveCalls, saved)
                );

        String id = service.revokeResponsibility(
                new RevokeResponsibilityCommand("resp-1", END)
        );

        assertEquals("resp-1", id);
        assertEquals(1, saveCalls.get());
        assertEquals(AssignmentStatus.ENDED, saved.get().status());
        assertEquals(END, saved.get().validTo());
        assertEquals(42L, saved.get().scopeId());
        assertEquals(CREATED, saved.get().createdAt());
    }

    @Test
    void alreadyEndedAssignmentIsIdempotent() {
        AtomicInteger saveCalls = new AtomicInteger();

        ResponsibilityRevocationApplicationService service =
                new ResponsibilityRevocationApplicationService(
                        repository(
                                Optional.of(assignment(AssignmentStatus.ENDED, END)),
                                saveCalls,
                                new AtomicReference<>()
                        )
                );

        String id = service.revokeResponsibility(
                new RevokeResponsibilityCommand("resp-1", END)
        );

        assertEquals("resp-1", id);
        assertEquals(0, saveCalls.get());
    }

    @Test
    void suspendedAssignmentMayBeEnded() {
        AtomicInteger saveCalls = new AtomicInteger();
        AtomicReference<ResponsibilityAssignment> saved = new AtomicReference<>();

        ResponsibilityRevocationApplicationService service =
                new ResponsibilityRevocationApplicationService(
                        repository(Optional.of(assignment(AssignmentStatus.SUSPENDED, null)), saveCalls, saved)
                );

        service.revokeResponsibility(
                new RevokeResponsibilityCommand("resp-1", END)
        );

        assertEquals(1, saveCalls.get());
        assertEquals(AssignmentStatus.ENDED, saved.get().status());
    }

    @Test
    void cancelledAssignmentCannotBeRevoked() {
        AtomicInteger saveCalls = new AtomicInteger();

        ResponsibilityRevocationApplicationService service =
                new ResponsibilityRevocationApplicationService(
                        repository(
                                Optional.of(assignment(AssignmentStatus.CANCELLED, END)),
                                saveCalls,
                                new AtomicReference<>()
                        )
                );

        assertThrows(
                IllegalStateException.class,
                () -> service.revokeResponsibility(
                        new RevokeResponsibilityCommand("resp-1", END)
                )
        );

        assertEquals(0, saveCalls.get());
    }

    @Test
    void unknownAssignmentIsRejected() {
        AtomicInteger saveCalls = new AtomicInteger();

        ResponsibilityRevocationApplicationService service =
                new ResponsibilityRevocationApplicationService(
                        repository(Optional.empty(), saveCalls, new AtomicReference<>())
                );

        assertThrows(
                IllegalArgumentException.class,
                () -> service.revokeResponsibility(
                        new RevokeResponsibilityCommand("missing", END)
                )
        );

        assertEquals(0, saveCalls.get());
    }

    @Test
    void revocationMustBeAfterStart() {
        ResponsibilityRevocationApplicationService service =
                new ResponsibilityRevocationApplicationService(
                        repository(
                                Optional.of(assignment(AssignmentStatus.ACTIVE, null)),
                                new AtomicInteger(),
                                new AtomicReference<>()
                        )
                );

        assertThrows(
                IllegalArgumentException.class,
                () -> service.revokeResponsibility(
                        new RevokeResponsibilityCommand("resp-1", START)
                )
        );
    }

    @Test
    void revocationCannotExtendScheduledEnd() {
        ResponsibilityRevocationApplicationService service =
                new ResponsibilityRevocationApplicationService(
                        repository(
                                Optional.of(assignment(AssignmentStatus.ACTIVE, END)),
                                new AtomicInteger(),
                                new AtomicReference<>()
                        )
                );

        assertThrows(
                IllegalArgumentException.class,
                () -> service.revokeResponsibility(
                        new RevokeResponsibilityCommand(
                                "resp-1",
                                Instant.parse("2026-09-27T13:00:00Z")
                        )
                )
        );
    }

    private static ResponsibilityAssignment assignment(
            AssignmentStatus status,
            Instant validTo
    ) {
        return new ResponsibilityAssignment(
                "resp-1",
                ResponsibilityType.RESPONSIBLE,
                "ORGANIZATION_UNIT",
                "unit-001",
                42L,
                "Pipeline responsibility",
                START,
                validTo,
                status,
                CREATED,
                CREATED
        );
    }

    private static ResponsibilityAssignmentRepositoryPort repository(
            Optional<ResponsibilityAssignment> existing,
            AtomicInteger saveCalls,
            AtomicReference<ResponsibilityAssignment> saved
    ) {
        return new ResponsibilityAssignmentRepositoryPort() {
            @Override
            public ResponsibilityAssignment save(ResponsibilityAssignment model) {
                saveCalls.incrementAndGet();
                saved.set(model);
                return model;
            }

            @Override
            public Optional<ResponsibilityAssignment> findById(String id) {
                return existing;
            }
        };
    }
}
