/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ResponsibilityReconciliationApplicationServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-29
 *
 * @Type        : Class
 * @Layer       : Organization Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.service
 *
 * @Description : Verifies read-only responsibility reconciliation and fail-closed owner rechecks.
 *
 */
package dz.sh.hidra.modules.organization.application.service;

import static org.assertj.core.api.Assertions.assertThat;

import dz.sh.hidra.modules.audit.application.contract.organization.OrganizationResponsibilityAuditContract;
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
import dz.sh.hidra.modules.organization.domain.value.EmployeeStatus;
import dz.sh.hidra.modules.organization.domain.value.EmployeeType;
import dz.sh.hidra.modules.organization.domain.value.OperationalScopeType;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitStatus;
import dz.sh.hidra.modules.organization.domain.value.ResponsibilityAssigneeType;
import dz.sh.hidra.modules.organization.domain.value.ResponsibilityType;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ResponsibilityReconciliationApplicationServiceTest {

    private static final Instant NOW = Instant.parse("2026-09-27T18:00:00Z");

    @Test
    void healthyAssignmentProducesNoFindingAndDoesNotMutateRepositories() {
        var assignment = assignment("a-1", ResponsibilityAssigneeType.EMPLOYEE, "emp-1", 10L);
        var assignmentRepo = new AssignmentRepository(List.of(assignment));

        var service = new ResponsibilityReconciliationApplicationService(
                assignmentRepo,
                scopeRepository(new OperationalScope(10L, OperationalScopeType.PIPELINE, "pipe-1")),
                resolver(true, true, false),
                employeeRepository(EmployeeStatus.ACTIVE),
                unitRepository(OrganizationUnitStatus.ACTIVE)
        ,
                audit());

        var result = service.reconcileResponsibilities(context());

        assertThat(result.scannedAssignments()).isEqualTo(1);
        assertThat(result.issues()).isEmpty();
        assertThat(assignmentRepo.saveCalls).isZero();
    }

    @Test
    void reportsMissingAssigneeWithoutRepairingAnything() {
        var assignment = assignment("a-2", ResponsibilityAssigneeType.EMPLOYEE, "missing", 10L);
        var assignmentRepo = new AssignmentRepository(List.of(assignment));

        var service = new ResponsibilityReconciliationApplicationService(
                assignmentRepo,
                scopeRepository(new OperationalScope(10L, OperationalScopeType.PIPELINE, "pipe-1")),
                resolver(true, true, false),
                missingEmployeeRepository(),
                unitRepository(OrganizationUnitStatus.ACTIVE)
        ,
                audit());

        var result = service.reconcileResponsibilities(context());

        assertThat(result.issues()).extracting(issue -> issue.code())
                .containsExactly(IssueCode.MISSING_ASSIGNEE);
        assertThat(assignmentRepo.saveCalls).isZero();
    }

    @Test
    void reportsUnknownScopeBeforeOwnerResolution() {
        var assignment = assignment("a-3", ResponsibilityAssigneeType.EMPLOYEE, "emp-1", 99L);

        var service = new ResponsibilityReconciliationApplicationService(
                new AssignmentRepository(List.of(assignment)),
                scopeRepository(null),
                resolver(true, true, false),
                employeeRepository(EmployeeStatus.ACTIVE),
                unitRepository(OrganizationUnitStatus.ACTIVE)
        ,
                audit());

        var result = service.reconcileResponsibilities(context());

        assertThat(result.issues()).extracting(issue -> issue.code())
                .containsExactly(IssueCode.UNKNOWN_SCOPE);
    }

    @Test
    void reportsRetiredOwnerAndInactiveAssignee() {
        var assignment = assignment(
                "a-4",
                ResponsibilityAssigneeType.ORGANIZATION_UNIT,
                "unit-1",
                10L
        );

        var service = new ResponsibilityReconciliationApplicationService(
                new AssignmentRepository(List.of(assignment)),
                scopeRepository(new OperationalScope(10L, OperationalScopeType.PIPELINE, "pipe-1")),
                resolver(true, false, false),
                employeeRepository(EmployeeStatus.ACTIVE),
                unitRepository(OrganizationUnitStatus.INACTIVE)
        ,
                audit());

        var result = service.reconcileResponsibilities(context());

        assertThat(result.issues()).extracting(issue -> issue.code())
                .containsExactly(
                        IssueCode.ASSIGNEE_NOT_ACTIVE,
                        IssueCode.SCOPE_OWNER_NOT_ASSIGNABLE
                );
    }

    @Test
    void reportsUnsupportedResolverAndNeverGuessesOwnerIdentity() {
        var assignment = assignment("a-5", ResponsibilityAssigneeType.EMPLOYEE, "emp-1", 10L);

        var service = new ResponsibilityReconciliationApplicationService(
                new AssignmentRepository(List.of(assignment)),
                scopeRepository(new OperationalScope(10L, OperationalScopeType.FACILITY, "facility-1")),
                resolver(false, true, false),
                employeeRepository(EmployeeStatus.ACTIVE),
                unitRepository(OrganizationUnitStatus.ACTIVE)
        ,
                audit());

        var result = service.reconcileResponsibilities(context());

        assertThat(result.issues()).extracting(issue -> issue.code())
                .containsExactly(IssueCode.UNSUPPORTED_SCOPE_RESOLVER);
    }

    @Test
    void reportsResolverIdentityMismatchAndOrganizationUnitSelfTarget() {
        var mismatch = assignment("a-6", ResponsibilityAssigneeType.EMPLOYEE, "emp-1", 10L);
        var selfTarget = assignment(
                "a-7",
                ResponsibilityAssigneeType.ORGANIZATION_UNIT,
                "unit-1",
                11L
        );

        OperationalScopeRegistryRepositoryPort scopes = new OperationalScopeRegistryRepositoryPort() {
            @Override
            public Optional<OperationalScope> findById(Long id) {
                if (id == 10L) {
                    return Optional.of(new OperationalScope(10L, OperationalScopeType.PIPELINE, "pipe-1"));
                }
                if (id == 11L) {
                    return Optional.of(new OperationalScope(11L, OperationalScopeType.ORGANIZATION_UNIT, "unit-1"));
                }
                return Optional.empty();
            }

            @Override
            public Optional<OperationalScope> findByTypeAndTargetId(OperationalScopeType type, String targetId) {
                return Optional.empty();
            }

            @Override
            public Optional<OperationalScope> findGlobal() {
                return Optional.empty();
            }
        };

        OperationalScopeTargetResolverPort resolver = new OperationalScopeTargetResolverPort() {
            @Override
            public boolean supports(OperationalScopeType type) {
                return true;
            }

            @Override
            public Optional<ResolvedTarget> resolve(OperationalScopeType type, String targetId) {
                if (type == OperationalScopeType.PIPELINE) {
                    return Optional.of(new ResolvedTarget(
                            OperationalScopeType.PIPELINE,
                            "different-pipe",
                            "P-2",
                            "Different",
                            true
                    ));
                }
                return Optional.of(new ResolvedTarget(type, targetId, "OU-1", "Unit", true));
            }
        };

        var service = new ResponsibilityReconciliationApplicationService(
                new AssignmentRepository(List.of(mismatch, selfTarget)),
                scopes,
                resolver,
                employeeRepository(EmployeeStatus.ACTIVE),
                unitRepository(OrganizationUnitStatus.ACTIVE)
        ,
                audit());

        var result = service.reconcileResponsibilities(context());

        assertThat(result.issues()).extracting(issue -> issue.code())
                .containsExactly(
                        IssueCode.SCOPE_OWNER_IDENTITY_MISMATCH,
                        IssueCode.SELF_TARGET_RESPONSIBILITY
                );
    }

    private static ResponsibilityAssignment assignment(
            String id,
            ResponsibilityAssigneeType assigneeType,
            String assigneeId,
            Long scopeId
    ) {
        return new ResponsibilityAssignment(
                id,
                ResponsibilityType.RESPONSIBLE,
                assigneeType,
                assigneeId,
                scopeId,
                "reconciliation test",
                NOW,
                null,
                AssignmentStatus.ACTIVE,
                NOW,
                NOW
        );
    }

    private static OperationalScopeRegistryRepositoryPort scopeRepository(OperationalScope scope) {
        return new OperationalScopeRegistryRepositoryPort() {
            @Override
            public Optional<OperationalScope> findById(Long scopeId) {
                return Optional.ofNullable(scope);
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

    private static OperationalScopeTargetResolverPort resolver(
            boolean supports,
            boolean assignable,
            boolean missing
    ) {
        return new OperationalScopeTargetResolverPort() {
            @Override
            public boolean supports(OperationalScopeType type) {
                return supports;
            }

            @Override
            public Optional<ResolvedTarget> resolve(OperationalScopeType type, String targetId) {
                if (missing) {
                    return Optional.empty();
                }
                return Optional.of(new ResolvedTarget(type, targetId, "CODE", "Name", assignable));
            }
        };
    }

    private static EmployeeRepositoryPort employeeRepository(EmployeeStatus status) {
        return new EmployeeRepositoryPort() {
            @Override
            public Employee save(Employee model) {
                return model;
            }

            @Override
            public Optional<Employee> findById(String id) {
                return Optional.of(new Employee(
                        id, "E-1", null, null, "A", "B", null, "A B",
                        null, null, EmployeeType.PERMANENT, status,
                        null, null, null, NOW, NOW
                ));
            }
        };
    }

    private static EmployeeRepositoryPort missingEmployeeRepository() {
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

    private static OrganizationUnitRepositoryPort unitRepository(OrganizationUnitStatus status) {
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
                        "type-1",
                        null,
                        status,
                        NOW,
                        null,
                        NOW,
                        NOW
                ));
            }
        };
    }

    private static final class AssignmentRepository
            implements ResponsibilityAssignmentRepositoryPort {

        private final List<ResponsibilityAssignment> assignments;
        private int saveCalls;

        private AssignmentRepository(List<ResponsibilityAssignment> assignments) {
            this.assignments = assignments;
        }

        @Override
        public ResponsibilityAssignment save(ResponsibilityAssignment model) {
            saveCalls++;
            return model;
        }

        @Override
        public Optional<ResponsibilityAssignment> findById(String id) {
            return assignments.stream().filter(item -> item.id().equals(id)).findFirst();
        }

        @Override
        public List<ResponsibilityAssignment> findAll() {
            return assignments;
        }
    }

    private static ResponsibilityOperationContext context() {
        return new ResponsibilityOperationContext(
                "actor-1",
                "auditor",
                "Auditor",
                Set.of(ResponsibilityReconciliationApplicationService.RECONCILE_PERMISSION),
                null,
                "reconcile-run-1",
                "request-1",
                "correlation-1"
        );
    }

    private static OrganizationResponsibilityAuditContract audit() {
        return event -> "audit-1";
    }

}
