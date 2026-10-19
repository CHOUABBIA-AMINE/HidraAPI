/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringNetworkDraftApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-19
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Implements the bounded company-scoped engineering draft contract.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import dz.sh.hidra.modules.topology.application.port.in.EngineeringNetworkDraftUseCase;
import dz.sh.hidra.modules.topology.application.port.out.EngineeringNetworkDraftRepositoryPort;
import dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure;
import dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure.Reason;
import dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
public class EngineeringNetworkDraftApplicationService implements EngineeringNetworkDraftUseCase {
    private final ProtectedEngineeringDraftAdmissionCoordinator coordinator;private final EngineeringNetworkDraftRepositoryPort repository;
    private final ProtectedTransactionExecutor executor;
    public EngineeringNetworkDraftApplicationService(ProtectedEngineeringDraftAdmissionCoordinator c,EngineeringNetworkDraftRepositoryPort r,ProtectedTransactionExecutor e){
        coordinator=Objects.requireNonNull(c);repository=Objects.requireNonNull(r);executor=Objects.requireNonNull(e);}
    @Override public Result execute(Command cmd){
        Objects.requireNonNull(cmd);if(TransactionSynchronizationManager.isActualTransactionActive())throw new EngineeringNetworkDraftFailure(Reason.UNAVAILABLE);
        var a=new AtomicReference<ProtectedEngineeringDraftAdmissionCoordinator.Admission>();
        var c=new AtomicReference<ProtectedEngineeringDraftAdmissionCoordinator.Contribution>();
        try {var prepared=coordinator.prepare();
            return executor.executePublication(()->{
                var admission=coordinator.acquire(prepared,cmd);a.set(admission);c.set(coordinator.collect(admission));
                var r=coordinator.repository(admission);
                boolean mutation=cmd.kind()==Kind.UPDATE || (cmd.kind()==Kind.CREATE && !repository.replay(r));
                if(mutation)coordinator.beginProvisional(admission,repository.mutationSql());
                Result result=repository.apply(r,c.get());
                if(mutation)coordinator.endProvisional(admission);
                coordinator.requireCurrent(admission);return result;
            },repository::flush,()->{
                if(!repository.finalGuard(coordinator.repository(a.get()),c.get(),coordinator.finalFences(a.get())))
                    throw new EngineeringNetworkDraftFailure(Reason.UNAVAILABLE);
            });
        }catch(ProtectedTransactionExecutor.ExecutionFailure f){
            if(f.outcome()==ProtectedTransactionExecutor.Outcome.INDETERMINATE)throw new EngineeringNetworkDraftFailure(Reason.INDETERMINATE);
            Throwable cause=f;while(cause!=null){if(cause instanceof EngineeringNetworkDraftFailure e)throw e;
                if(cause instanceof org.springframework.security.access.AccessDeniedException)throw new EngineeringNetworkDraftFailure(Reason.DENIED);cause=cause.getCause();}
            throw new EngineeringNetworkDraftFailure(Reason.UNAVAILABLE);
        }catch(org.springframework.security.access.AccessDeniedException e){throw new EngineeringNetworkDraftFailure(Reason.DENIED);}
        catch(EngineeringNetworkDraftFailure e){throw e;}
        catch(RuntimeException e){throw new EngineeringNetworkDraftFailure(Reason.UNAVAILABLE);}
    }
}
