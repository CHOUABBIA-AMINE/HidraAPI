package dz.sh.hidra.modules.organization.application.service;

import dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract;
import dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract.Action;
import dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract.CompanyUnit;
import dz.sh.hidra.modules.identity.application.contract.organization.OrganizationProtectedAppointmentAuthorizationContract.Request;
import dz.sh.hidra.modules.organization.application.command.EmployeeAppointmentCommand;
import dz.sh.hidra.modules.organization.application.port.out.ProtectedEmployeeAppointmentRepositoryPort;
import dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery;
import dz.sh.hidra.modules.party.application.contract.organization.OrganizationProtectedPartyEligibilityContract;
import dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * Non-wired admission coordinator: no Spring bean or protected DML route is
 * activated until physical PostgreSQL guards and exact executor caller support
 * are implemented and tested. Constructor ports are genuine owner contracts.
 */
public final class ProtectedEmployeeAppointmentAdmissionCoordinator {
    private final OrganizationProtectedAppointmentAuthorizationContract identity;
    private final OrganizationProtectedPartyEligibilityContract party;
    private final ProtectedEmployeeAppointmentRepositoryPort storage;
    private final ProtectedTransactionExecutor executor;

    public ProtectedEmployeeAppointmentAdmissionCoordinator(
            OrganizationProtectedAppointmentAuthorizationContract identity,
            OrganizationProtectedPartyEligibilityContract party,
            ProtectedEmployeeAppointmentRepositoryPort storage,
            ProtectedTransactionExecutor executor) {
        this.identity = Objects.requireNonNull(identity);
        this.party = Objects.requireNonNull(party);
        this.storage = Objects.requireNonNull(storage);
        this.executor = Objects.requireNonNull(executor);
    }

    /** Capture native identity before opening the protected physical transaction. */
    public Prepared prepare() {
        if (TransactionSynchronizationManager.isActualTransactionActive()) throw denied();
        return new Prepared(this, Objects.requireNonNull(identity.prepare()));
    }

    /** Only an exact complete descriptor closure can become a native request. */
    static Request requestFor(EmployeeAppointmentCommand command, EmployeeAppointmentQuery query,
                              ProtectedEmployeeAppointmentRepositoryPort.DescriptorPreview preview) {
        if ((command == null) == (query == null) || preview == null || preview.overflow()) throw denied();
        Action action;
        String company;
        String employee = preview.employeeId();
        String assignment = preview.assignmentId();
        List<CompanyUnit> pairs = preview.companyUnits();
        if (command != null) {
            company = command.companyPartyId();
            action = switch (command.kind()) {
                case CREATE -> Action.CREATE;
                case END -> Action.END;
                case TRANSFER -> Action.TRANSFER;
                case CORRECT -> Action.CORRECT;
            };
            if (employee == null || pairs.stream().anyMatch(p -> !company.equals(p.companyPartyId())))
                throw denied();
            if (command instanceof EmployeeAppointmentCommand.Create create) {
                if (assignment != null || !employee.equals(create.employeeId())
                        || !pairs.equals(List.of(new CompanyUnit(company, create.organizationUnitId()))))
                    throw denied();
            } else {
                String selected = command instanceof EmployeeAppointmentCommand.End end ? end.assignmentId()
                        : command instanceof EmployeeAppointmentCommand.Transfer transfer ? transfer.assignmentId()
                        : ((EmployeeAppointmentCommand.Correct) command).assignmentId();
                if (!selected.equals(assignment)) throw denied();
                if (command instanceof EmployeeAppointmentCommand.Transfer transfer
                        && !pairs.contains(new CompanyUnit(company, transfer.destinationOrganizationUnitId())))
                    throw denied();
                if (command instanceof EmployeeAppointmentCommand.Correct correct
                        && !pairs.contains(new CompanyUnit(company, correct.correctedOrganizationUnitId())))
                    throw denied();
            }
        } else {
            company = query.companyPartyId();
            action = query instanceof EmployeeAppointmentQuery.Policy ? Action.READ_POLICY
                    : query instanceof EmployeeAppointmentQuery.ExactVersion ? Action.READ_CURRENT
                    : Action.READ_HISTORY;
            if (pairs.stream().noneMatch(p -> company.equals(p.companyPartyId()))) throw denied();
            if (query instanceof EmployeeAppointmentQuery.Policy policy) {
                if (employee != null || assignment != null
                        || !pairs.equals(List.of(new CompanyUnit(company, policy.unitId())))) throw denied();
            } else if (query instanceof EmployeeAppointmentQuery.ExactVersion exact) {
                if (!exact.assignmentId().equals(assignment) || employee == null) throw denied();
            } else if (query instanceof EmployeeAppointmentQuery.History history) {
                switch (history.scope()) {
                    case ASSIGNMENT -> {
                        if (!history.assignmentId().equals(assignment) || employee == null) throw denied();
                    }
                    case EMPLOYEE -> {
                        if (!history.employeeId().equals(employee) || assignment != null) throw denied();
                    }
                    case POSITION -> {
                        if (employee != null || assignment != null
                                || !pairs.contains(new CompanyUnit(company, history.unitId()))) throw denied();
                    }
                }
            }
        }
        return new Request(action, pairs, employee);
    }

    /** Fail closed on absent transaction, foreign issuer, reused token or bad owner preview. */
    Request begin(Prepared prepared, EmployeeAppointmentCommand command) {
        return begin(prepared, Objects.requireNonNull(command), null);
    }

    Request begin(Prepared prepared, EmployeeAppointmentQuery query) {
        return begin(prepared, null, Objects.requireNonNull(query));
    }

    private Request begin(Prepared prepared, EmployeeAppointmentCommand command, EmployeeAppointmentQuery query) {
        try {
            if (!TransactionSynchronizationManager.isActualTransactionActive()
                    || !TransactionSynchronizationManager.isSynchronizationActive()
                    || TransactionSynchronizationManager.isCurrentTransactionReadOnly()) throw denied();
            if (prepared == null || prepared.owner != this
                    || prepared.thread != Thread.currentThread()
                    || !prepared.used.compareAndSet(false, true)) throw denied();
            executor.beginAdmission();
            var preview = command == null ? storage.preview(query) : storage.preview(command);
            if (preview == null) throw denied();
            preview.requireBounded();
            // Return value is an internal request descriptor, never an admission
            // or permission. No Identity/Party/Organization ticket is consumed
            // in this B3E slice. The subsequent protected coordinator stage must
            // acquire every owner fence BEFORE using it for any result.
            return requestFor(command, query, preview);
        } catch (RuntimeException failure) {
            executor.rejectAdmission();
            throw denied();
        }
    }

    private static AccessDeniedException denied() {
        return new AccessDeniedException("Protected appointment admission unavailable");
    }

    /** An opaque preparation is never a transferable native credential. */
    public static final class Prepared {
        private final ProtectedEmployeeAppointmentAdmissionCoordinator owner;
        private final OrganizationProtectedAppointmentAuthorizationContract.Prepared nativeIdentity;
        private final Thread thread = Thread.currentThread();
        private final AtomicBoolean used = new AtomicBoolean();
        private Prepared(ProtectedEmployeeAppointmentAdmissionCoordinator owner,
                         OrganizationProtectedAppointmentAuthorizationContract.Prepared nativeIdentity) {
            this.owner = owner;
            this.nativeIdentity = nativeIdentity;
        }
        @Override public String toString() { return "Prepared[opaque appointment admission]"; }
    }
}
