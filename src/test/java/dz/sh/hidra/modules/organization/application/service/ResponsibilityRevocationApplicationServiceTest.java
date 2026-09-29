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

import dz.sh.hidra.modules.audit.application.contract.organization.OrganizationResponsibilityAuditContract;
import dz.sh.hidra.modules.organization.application.command.ResponsibilityOperationContext;
import dz.sh.hidra.modules.organization.application.command.RevokeResponsibilityCommand;
import dz.sh.hidra.modules.workflow.application.contract.organization.OrganizationResponsibilityWorkflowContract;
import dz.sh.hidra.modules.organization.application.port.out.ResponsibilityAssignmentRepositoryPort;
import dz.sh.hidra.modules.organization.domain.model.ResponsibilityAssignment;
import dz.sh.hidra.modules.organization.domain.value.AssignmentStatus;
import dz.sh.hidra.modules.organization.domain.value.ResponsibilityType;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
                ,
                        workflow(),
                        audit());

        String id = service.revokeResponsibility(
                new RevokeResponsibilityCommand("resp-1", END,
                        context(ResponsibilityRevocationApplicationService.REVOKE_PERMISSION))
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
                ,
                        workflow(),
                        audit());

        String id = service.revokeResponsibility(
                new RevokeResponsibilityCommand("resp-1", END,
                        context(ResponsibilityRevocationApplicationService.REVOKE_PERMISSION))
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
                ,
                        workflow(),
                        audit());

        service.revokeResponsibility(
                new RevokeResponsibilityCommand("resp-1", END,
                        context(ResponsibilityRevocationApplicationService.REVOKE_PERMISSION))
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
                ,
                        workflow(),
                        audit());

        assertThrows(
                IllegalStateException.class,
                () -> service.revokeResponsibility(
                        new RevokeResponsibilityCommand("resp-1", END,
                        context(ResponsibilityRevocationApplicationService.REVOKE_PERMISSION))
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
                ,
                        workflow(),
                        audit());

        assertThrows(
                IllegalArgumentException.class,
                () -> service.revokeResponsibility(
                        new RevokeResponsibilityCommand("missing", END,
                        context(ResponsibilityRevocationApplicationService.REVOKE_PERMISSION))
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
                ,
                        workflow(),
                        audit());

        assertThrows(
                IllegalArgumentException.class,
                () -> service.revokeResponsibility(
                        new RevokeResponsibilityCommand("resp-1", START,
                        context(ResponsibilityRevocationApplicationService.REVOKE_PERMISSION))
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
                ,
                        workflow(),
                        audit());

        assertThrows(
                IllegalArgumentException.class,
                () -> service.revokeResponsibility(
                        new RevokeResponsibilityCommand(
                                "resp-1",
                                Instant.parse("2026-09-27T13:00:00Z")
                        ,
                        context(ResponsibilityRevocationApplicationService.REVOKE_PERMISSION))
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

            @Override
            public List<ResponsibilityAssignment> findActiveByAssigneeAndResponsibilityAndScope(
                    String assigneeType,
                    String assigneeId,
                    ResponsibilityType responsibilityType,
                    Long scopeId
            ) {
                return List.of();
            }
        };
    }

    private static ResponsibilityOperationContext context(String permission) {
        return new ResponsibilityOperationContext(
                "actor-1",
                "operator",
                "Operator",
                Set.of(permission),
                "wf-1",
                "operation-1",
                "request-1",
                "correlation-1"
        );
    }

    private static OrganizationResponsibilityWorkflowContract workflow() {
        return (workflowInstanceId, operationReference) ->
                new OrganizationResponsibilityWorkflowContract.ApprovalEvidence(
                        workflowInstanceId,
                        "task-1",
                        "action-1",
                        "APPROVE",
                        Instant.parse("2026-09-27T07:30:00Z")
                );
    }

    private static OrganizationResponsibilityAuditContract audit() {
        return event -> "audit-1";
    }

}
