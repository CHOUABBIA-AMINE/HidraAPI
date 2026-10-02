/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OrganizationOperationalScopeEndToEndTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-29
 *
 * @Type        : Class
 * @Layer       : Organization Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.service
 *
 * @Description : Final ORG-033 application-level verification for multi-scope responsibility and retirement history.
 *
 */
package dz.sh.hidra.modules.organization.application.service;

import static org.assertj.core.api.Assertions.assertThat;

import dz.sh.hidra.modules.audit.application.contract.organization.OrganizationResponsibilityAuditContract;
import dz.sh.hidra.modules.organization.application.command.AssignResponsibilityCommand;
import dz.sh.hidra.modules.organization.application.command.ResponsibilityOperationContext;
import dz.sh.hidra.modules.organization.application.dto.ResponsibilityReconciliationResult.IssueCode;
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
import dz.sh.hidra.modules.organization.domain.value.OperationalScopeType;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitStatus;
import dz.sh.hidra.modules.organization.domain.value.ResponsibilityAssigneeType;
import dz.sh.hidra.modules.organization.domain.value.ResponsibilityType;
import dz.sh.hidra.modules.workflow.application.contract.organization.OrganizationResponsibilityWorkflowContract;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;

/**
 * Closes the issue-#130 application-level cardinality and owner-retirement evidence gap.
 *
 * <p>Database replay, canonical schema constraints, REST security derivation, Workflow rejection,
 * Audit catalog resolution, pessimistic locking, and architecture boundaries are covered by the
 * dedicated tests referenced from the ORG-033 roadmap acceptance matrix.</p>
 */
class OrganizationOperationalScopeEndToEndTest {

    private static final Instant NOW = Instant.parse("2026-09-29T12:00:00Z");

    @Test
    void unitCanHoldAndRetrieveMultipleConcurrentScopedResponsibilitiesWithoutOverwrite() {
        var scopes = new InMemoryScopeRepository(
                new OperationalScope(42L, OperationalScopeType.PIPELINE, "pipeline-1"),
                new OperationalScope(43L, OperationalScopeType.FACILITY, "facility-1")
        );
        var targets = new MutableTargetResolver();
        var assignments = new InMemoryAssignmentRepository();
        var auditEvents = new ArrayList<OrganizationResponsibilityAuditContract.Event>();

        var assignmentService = assignmentService(scopes, targets, assignments, auditEvents);

        String pipelineAssignment = assignmentService.assignResponsibility(command(
                ResponsibilityType.RESPONSIBLE,
                42L,
                "pipeline responsibility"
        ));
        String facilityAssignment = assignmentService.assignResponsibility(command(
                ResponsibilityType.APPROVER,
                43L,
                "facility approval responsibility"
        ));

        var query = new ResponsibilityQueryApplicationService(assignments);
        List<ResponsibilityAssignment> retrieved =
                query.listByAssignee(ResponsibilityAssigneeType.ORGANIZATION_UNIT, "unit-1");

        assertThat(pipelineAssignment).isNotEqualTo(facilityAssignment);
        assertThat(retrieved).hasSize(2);
        assertThat(retrieved).extracting(ResponsibilityAssignment::scopeId)
                .containsExactlyInAnyOrder(42L, 43L);
        assertThat(retrieved).extracting(ResponsibilityAssignment::responsibilityType)
                .containsExactlyInAnyOrder(
                        ResponsibilityType.RESPONSIBLE,
                        ResponsibilityType.APPROVER
                );
        assertThat(retrieved).allMatch(
                assignment -> assignment.validFrom().equals(NOW)
                        && assignment.validTo() == null
                        && assignment.status() == AssignmentStatus.ACTIVE
        );
        assertThat(scopes.writeLockReads).isEqualTo(2);
        assertThat(auditEvents).hasSize(2);
    }

    @Test
    void retiredOwnerIsFlaggedWithoutDeletingHistoricalResponsibility() {
        var scopes = new InMemoryScopeRepository(
                new OperationalScope(42L, OperationalScopeType.PIPELINE, "pipeline-1"),
                new OperationalScope(43L, OperationalScopeType.FACILITY, "facility-1")
        );
        var targets = new MutableTargetResolver();
        var assignments = new InMemoryAssignmentRepository();
        var auditEvents = new ArrayList<OrganizationResponsibilityAuditContract.Event>();

        var assignmentService = assignmentService(scopes, targets, assignments, auditEvents);
        assignmentService.assignResponsibility(command(
                ResponsibilityType.RESPONSIBLE,
                42L,
                "pipeline responsibility"
        ));
        assignmentService.assignResponsibility(command(
                ResponsibilityType.APPROVER,
                43L,
                "facility approval responsibility"
        ));

        targets.setAssignable("pipeline-1", false);

        var reconciliation = new ResponsibilityReconciliationApplicationService(
                assignments,
                scopes,
                targets,
                employeeRepository(),
                organizationUnitRepository(),
                audit(auditEvents)
        );

        var result = reconciliation.reconcileResponsibilities(reconcileContext());

        assertThat(result.scannedAssignments()).isEqualTo(2);
        assertThat(result.issues()).hasSize(1);
        assertThat(result.issues().getFirst().code())
                .isEqualTo(IssueCode.SCOPE_OWNER_NOT_ASSIGNABLE);
        assertThat(result.issues().getFirst().referenceId()).isEqualTo("pipeline-1");

        var query = new ResponsibilityQueryApplicationService(assignments);
        assertThat(query.listByAssignee(
                ResponsibilityAssigneeType.ORGANIZATION_UNIT,
                "unit-1"
        )).hasSize(2);
        assertThat(assignments.findAll()).hasSize(2);
    }

    private static ResponsibilityAssignmentApplicationService assignmentService(
            InMemoryScopeRepository scopes,
            MutableTargetResolver targets,
            InMemoryAssignmentRepository assignments,
            List<OrganizationResponsibilityAuditContract.Event> auditEvents
    ) {
        return new ResponsibilityAssignmentApplicationService(
                scopes,
                targets,
                employeeRepository(),
                organizationUnitRepository(),
                assignments,
                workflow(),
                audit(auditEvents)
        );
    }

    private static AssignResponsibilityCommand command(
            ResponsibilityType responsibilityType,
            Long scopeId,
            String description
    ) {
        return new AssignResponsibilityCommand(
                responsibilityType,
                ResponsibilityAssigneeType.ORGANIZATION_UNIT,
                "unit-1",
                scopeId,
                description,
                NOW,
                null,
                assignContext()
        );
    }

    private static ResponsibilityOperationContext assignContext() {
        return new ResponsibilityOperationContext(
                "actor-1",
                "operator",
                "Operator",
                Set.of(ResponsibilityAssignmentApplicationService.ASSIGN_PERMISSION),
                "workflow-1",
                "operation-1",
                "request-1",
                "correlation-1"
        );
    }

    private static ResponsibilityOperationContext reconcileContext() {
        return new ResponsibilityOperationContext(
                "actor-1",
                "auditor",
                "Auditor",
                Set.of(ResponsibilityReconciliationApplicationService.RECONCILE_PERMISSION),
                null,
                "reconcile-1",
                "request-2",
                "correlation-2"
        );
    }

    private static OrganizationResponsibilityWorkflowContract workflow() {
        return (workflowInstanceId, operationReference) ->
                new OrganizationResponsibilityWorkflowContract.ApprovalEvidence(
                        workflowInstanceId,
                        "task-1",
                        "action-1",
                        "APPROVE",
                        NOW.minusSeconds(60)
                );
    }

    private static OrganizationResponsibilityAuditContract audit(
            List<OrganizationResponsibilityAuditContract.Event> events
    ) {
        return event -> {
            events.add(event);
            return "audit-" + events.size();
        };
    }

    private static EmployeeRepositoryPort employeeRepository() {
        return new EmployeeRepositoryPort() {
            @Override
            public Employee save(Employee model) {
                return model;
            }

            @Override
            public Optional<Employee> findById(String id) {
                return Optional.empty();
            }
        };
    }

    private static OrganizationUnitRepositoryPort organizationUnitRepository() {
        return new OrganizationUnitRepositoryPort() {
            @Override
            public OrganizationUnit save(OrganizationUnit model) {
                return model;
            }

            @Override
            public Optional<OrganizationUnit> findById(String id) {
                return Optional.of(new OrganizationUnit(
                        id,
                        "UNIT-1",
                        null,
                        null,
                        "Unit",
                        "unit-type-1",
                        null,
                        OrganizationUnitStatus.ACTIVE,
                        NOW.minusSeconds(3600),
                        null,
                        NOW.minusSeconds(3600),
                        NOW.minusSeconds(3600)
                ));
            }
        };
    }

    private static final class InMemoryScopeRepository
            implements OperationalScopeRegistryRepositoryPort {

        private final Map<Long, OperationalScope> scopes = new HashMap<>();
        private int writeLockReads;

        private InMemoryScopeRepository(OperationalScope... scopes) {
            for (OperationalScope scope : scopes) {
                this.scopes.put(scope.id(), scope);
            }
        }

        @Override
        public Optional<OperationalScope> findById(Long scopeId) {
            return Optional.ofNullable(scopes.get(scopeId));
        }

        @Override
        public Optional<OperationalScope> findByIdForUpdate(Long scopeId) {
            writeLockReads++;
            return findById(scopeId);
        }

        @Override
        public Optional<OperationalScope> findByTypeAndTargetId(
                OperationalScopeType type,
                String targetId
        ) {
            return scopes.values().stream()
                    .filter(scope -> scope.type() == type)
                    .filter(scope -> java.util.Objects.equals(scope.targetId(), targetId))
                    .findFirst();
        }

        @Override
        public Optional<OperationalScope> findGlobal() {
            return scopes.values().stream()
                    .filter(scope -> scope.type() == OperationalScopeType.GLOBAL)
                    .findFirst();
        }
    }

    private static final class MutableTargetResolver
            implements OperationalScopeTargetResolverPort {

        private final Map<String, Boolean> assignable = new HashMap<>();

        private MutableTargetResolver() {
            assignable.put("pipeline-1", true);
            assignable.put("facility-1", true);
        }

        private void setAssignable(String targetId, boolean value) {
            assignable.put(targetId, value);
        }

        @Override
        public boolean supports(OperationalScopeType type) {
            return type == OperationalScopeType.PIPELINE
                    || type == OperationalScopeType.FACILITY;
        }

        @Override
        public Optional<ResolvedTarget> resolve(
                OperationalScopeType type,
                String targetId
        ) {
            Boolean current = assignable.get(targetId);
            if (current == null || !supports(type)) {
                return Optional.empty();
            }
            return Optional.of(new ResolvedTarget(
                    type,
                    targetId,
                    type == OperationalScopeType.PIPELINE ? "PL-1" : "FAC-1",
                    type == OperationalScopeType.PIPELINE ? "Pipeline 1" : "Facility 1",
                    current
            ));
        }
    }

    private static final class InMemoryAssignmentRepository
            implements ResponsibilityAssignmentRepositoryPort {

        private final List<ResponsibilityAssignment> assignments = new ArrayList<>();

        @Override
        public ResponsibilityAssignment save(ResponsibilityAssignment model) {
            assignments.add(model);
            return model;
        }

        @Override
        public Optional<ResponsibilityAssignment> findById(String id) {
            return assignments.stream()
                    .filter(assignment -> assignment.id().equals(id))
                    .findFirst();
        }

        @Override
        public List<ResponsibilityAssignment> findAll() {
            return List.copyOf(assignments);
        }

        @Override
        public List<ResponsibilityAssignment> findByScopeId(Long scopeId) {
            return assignments.stream()
                    .filter(assignment -> assignment.scopeId().equals(scopeId))
                    .toList();
        }

        @Override
        public List<ResponsibilityAssignment> findByAssignee(
                ResponsibilityAssigneeType assigneeType,
                String assigneeId
        ) {
            return assignments.stream()
                    .filter(assignment -> assignment.assigneeType() == assigneeType)
                    .filter(assignment -> assignment.assigneeId().equals(assigneeId))
                    .toList();
        }

        @Override
        public List<ResponsibilityAssignment> findActiveByAssigneeAndResponsibilityAndScope(
                ResponsibilityAssigneeType assigneeType,
                String assigneeId,
                ResponsibilityType responsibilityType,
                Long scopeId
        ) {
            return assignments.stream()
                    .filter(assignment -> assignment.status() == AssignmentStatus.ACTIVE)
                    .filter(assignment -> assignment.assigneeType() == assigneeType)
                    .filter(assignment -> assignment.assigneeId().equals(assigneeId))
                    .filter(assignment -> assignment.responsibilityType() == responsibilityType)
                    .filter(assignment -> assignment.scopeId().equals(scopeId))
                    .toList();
        }
    }
}
