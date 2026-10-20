/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringRevisionPublicationApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-20
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Executes governed publication operations through one native protected physical commit.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import dz.sh.hidra.modules.topology.application.port.in.EngineeringRevisionPublicationUseCase;
import dz.sh.hidra.modules.topology.application.port.out.EngineeringRevisionPublicationRepositoryPort;
import dz.sh.hidra.modules.topology.application.model.EngineeringRevisionPublicationFailure;
import dz.sh.hidra.modules.topology.application.model.EngineeringRevisionPublicationFailure.Reason;
import dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
public class EngineeringRevisionPublicationApplicationService implements EngineeringRevisionPublicationUseCase {
    private final ProtectedEngineeringPublicationAdmissionCoordinator coordinator;
    private final EngineeringRevisionPublicationRepositoryPort repository;
    private final ProtectedTransactionExecutor executor;
    public EngineeringRevisionPublicationApplicationService(ProtectedEngineeringPublicationAdmissionCoordinator coordinator,
            EngineeringRevisionPublicationRepositoryPort repository, ProtectedTransactionExecutor executor) {
        this.coordinator=Objects.requireNonNull(coordinator);
        this.repository=Objects.requireNonNull(repository);this.executor=Objects.requireNonNull(executor);
    }
    @Override public Result executePublication(Command command) {
        Objects.requireNonNull(command);
        if(TransactionSynchronizationManager.isActualTransactionActive())throw new EngineeringRevisionPublicationFailure(Reason.UNAVAILABLE);
        var admission=new AtomicReference<ProtectedEngineeringPublicationAdmissionCoordinator.Admission>();
        var observation=new AtomicReference<EngineeringRevisionPublicationRepositoryPort.Observation>();
        try {
            var prepared=coordinator.prepare();
            return executor.executePublication(()->{
                var a=coordinator.acquire(prepared,command);admission.set(a);
                var sample=coordinator.collect(a);observation.set(sample);
                var ticket=coordinator.repository(a);
                repository.resolve(ticket);
                boolean mutation=command.kind()==Kind.PUBLISH || command.kind()==Kind.WITHDRAW || command.kind()==Kind.REACTIVATE;
                // Receipt resolution is authorized afresh and precedes any provisional write.
                boolean replay=mutation && repository.receipt(ticket).isPresent();
                if(mutation && !replay)coordinator.beginProvisional(a,repository.mutationSql());
                Result result=repository.apply(ticket,sample);
                if(mutation && !replay)coordinator.endProvisional(a);
                coordinator.requireCurrent(a);
                return result;
            },repository::flush,()->{
                var a=admission.get();
                if(!repository.finalGuard(coordinator.repository(a),observation.get(),coordinator.finalFences(a)))
                    throw new EngineeringRevisionPublicationFailure(Reason.UNAVAILABLE);
            });
        } catch(RuntimeException failure) { throw translate(failure); }
    }
    private static EngineeringRevisionPublicationFailure translate(RuntimeException failure) {
        if(failure instanceof EngineeringRevisionPublicationFailure e)return e;
        if(failure instanceof ProtectedTransactionExecutor.ExecutionFailure execution
                && execution.outcome()==ProtectedTransactionExecutor.Outcome.INDETERMINATE)
            return new EngineeringRevisionPublicationFailure(Reason.INDETERMINATE,failure);
        for(Throwable cause=failure;cause!=null;cause=cause.getCause()) {
            if(cause instanceof EngineeringRevisionPublicationFailure e)return e;
            if(cause instanceof org.springframework.security.access.AccessDeniedException)
                return new EngineeringRevisionPublicationFailure(Reason.DENIED,failure);
        }
        return new EngineeringRevisionPublicationFailure(Reason.UNAVAILABLE,failure);
    }
}
