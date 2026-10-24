/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EmployeeAppointmentLifecycleApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-24
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.service
 *
 * @Description : Coordinates detached appointment results and one final guard under observed physical commit.
 *
 */
package dz.sh.hidra.modules.organization.application.service;

import dz.sh.hidra.modules.organization.application.command.EmployeeAppointmentCommand;
import dz.sh.hidra.modules.organization.application.dto.EmployeeAppointmentResult;
import dz.sh.hidra.modules.organization.application.port.in.EmployeeAppointmentLifecycleUseCase;
import dz.sh.hidra.modules.organization.application.query.EmployeeAppointmentQuery;
import dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor;
import jakarta.persistence.EntityManager;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

/**
 * Explicitly constructed integration boundary. Public bean/REST activation awaits
 * genuine native, publisher, executor and PostgreSQL composition acceptance.
 * No callback returns an admission, mutable holder, connection or lazy entity.
 */
public final class EmployeeAppointmentLifecycleApplicationService implements EmployeeAppointmentLifecycleUseCase {
    private final ProtectedEmployeeAppointmentAdmissionCoordinator coordinator;
    private final ProtectedTransactionExecutor executor;
    private final EntityManager entityManager;

    public EmployeeAppointmentLifecycleApplicationService(
            ProtectedEmployeeAppointmentAdmissionCoordinator coordinator,
            ProtectedTransactionExecutor executor, EntityManager entityManager) {
        this.coordinator=Objects.requireNonNull(coordinator);
        this.executor=Objects.requireNonNull(executor);
        this.entityManager=Objects.requireNonNull(entityManager);
    }

    @Override public EmployeeAppointmentResult.MutationReceipt create(EmployeeAppointmentCommand.Create command) {
        return mutate(Objects.requireNonNull(command));
    }
    @Override public EmployeeAppointmentResult.MutationReceipt end(EmployeeAppointmentCommand.End command) {
        return mutate(Objects.requireNonNull(command));
    }
    @Override public EmployeeAppointmentResult.MutationReceipt transfer(EmployeeAppointmentCommand.Transfer command) {
        return mutate(Objects.requireNonNull(command));
    }
    @Override public EmployeeAppointmentResult.MutationReceipt correct(EmployeeAppointmentCommand.Correct command) {
        return mutate(Objects.requireNonNull(command));
    }

    private EmployeeAppointmentResult.MutationReceipt mutate(EmployeeAppointmentCommand command) {
        var prepared=coordinator.prepare();
        var retained=new AtomicReference<ProtectedEmployeeAppointmentAdmissionCoordinator.WriteAdmission>();
        // executePublication validates this exact compiled class before selecting
        // the private principal, and returns only after observing physical commit.
        return executor.executePublication(()->{
            var admission=coordinator.acquireWrite(prepared,command);
            retained.set(admission);
            return coordinator.apply(admission);
        },()->{
            entityManager.flush();
            coordinator.prepareFinal(retained.get());
        },()->coordinator.finalizeOperation(retained.get()));
    }

    @Override public EmployeeAppointmentResult.HistoryPage<EmployeeAppointmentResult.PrivateHistoryEvent>
            assignmentHistory(EmployeeAppointmentQuery.History query) {
        requireScope(query,EmployeeAppointmentQuery.Scope.ASSIGNMENT);
        return read(query,coordinator::privateHistory);
    }
    @Override public EmployeeAppointmentResult.HistoryPage<EmployeeAppointmentResult.PrivateHistoryEvent>
            employeeHistory(EmployeeAppointmentQuery.History query) {
        requireScope(query,EmployeeAppointmentQuery.Scope.EMPLOYEE);
        return read(query,coordinator::privateHistory);
    }
    @Override public EmployeeAppointmentResult.HistoryPage<EmployeeAppointmentResult.PositionHistoryEvent>
            positionHistory(EmployeeAppointmentQuery.History query) {
        requireScope(query,EmployeeAppointmentQuery.Scope.POSITION);
        return read(query,coordinator::positionHistory);
    }
    @Override public EmployeeAppointmentResult.PinnedVersion exactVersion(EmployeeAppointmentQuery.ExactVersion query) {
        return read(Objects.requireNonNull(query),coordinator::exactVersion);
    }
    @Override public EmployeeAppointmentResult.PolicyView readPolicy(EmployeeAppointmentQuery.Policy query) {
        return read(Objects.requireNonNull(query),coordinator::readPolicy);
    }

    private <T> T read(EmployeeAppointmentQuery query,
            Function<ProtectedEmployeeAppointmentAdmissionCoordinator.ReadAdmission,T> materialize) {
        var prepared=coordinator.prepare();
        var retained=new AtomicReference<ProtectedEmployeeAppointmentAdmissionCoordinator.ReadAdmission>();
        return executor.executePublication(()->{
            var admission=coordinator.acquireRead(prepared,query);
            retained.set(admission);
            coordinator.sealRead(admission);
            return materialize.apply(admission);
        },()->{
            entityManager.flush();
            coordinator.prepareFinalRead(retained.get());
        },()->coordinator.finalizeRead(retained.get()));
    }

    private static void requireScope(EmployeeAppointmentQuery.History query,EmployeeAppointmentQuery.Scope scope) {
        if(Objects.requireNonNull(query).scope()!=scope)
            throw new IllegalArgumentException("Appointment history scope does not match its use case");
    }
}
