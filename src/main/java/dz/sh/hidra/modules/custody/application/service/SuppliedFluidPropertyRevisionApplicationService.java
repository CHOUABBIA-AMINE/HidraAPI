/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SuppliedFluidPropertyRevisionApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-27
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.application.service
 *
 * @Description : Preserves exact authored fluid facts independently of numerical qualification.
 *
 */
package dz.sh.hidra.modules.custody.application.service;

import dz.sh.hidra.modules.custody.application.port.in.SuppliedFluidPropertyRevisionUseCase;
import dz.sh.hidra.modules.custody.application.port.out.SuppliedFluidPropertyRevisionRepositoryPort;
import dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
public class SuppliedFluidPropertyRevisionApplicationService implements SuppliedFluidPropertyRevisionUseCase {
    private final ProtectedSuppliedFluidPropertyAdmissionCoordinator coordinator;
    private final SuppliedFluidPropertyRevisionRepositoryPort repository;
    private final ProtectedTransactionExecutor executor;
    public SuppliedFluidPropertyRevisionApplicationService(ProtectedSuppliedFluidPropertyAdmissionCoordinator coordinator,
            SuppliedFluidPropertyRevisionRepositoryPort repository,ProtectedTransactionExecutor executor) {
        this.coordinator=Objects.requireNonNull(coordinator);this.repository=Objects.requireNonNull(repository);this.executor=Objects.requireNonNull(executor);
    }
    @Override public Result execute(Command command) {
        Objects.requireNonNull(command);
        if(TransactionSynchronizationManager.isActualTransactionActive() || TransactionSynchronizationManager.isSynchronizationActive())
            throw new Failure(Reason.UNAVAILABLE);
        var admission=new AtomicReference<ProtectedSuppliedFluidPropertyAdmissionCoordinator.Admission>();
        var contribution=new AtomicReference<ProtectedSuppliedFluidPropertyAdmissionCoordinator.Contribution>();
        try {
            var prepared=coordinator.prepare();
            return executor.executePublication(()->{
                var a=coordinator.acquire(prepared,command);admission.set(a);contribution.set(coordinator.collect(a));
                var storage=coordinator.repository(a);boolean mutation=repository.mutation(storage);
                if(mutation)coordinator.beginProvisional(a,repository.mutationSql());
                var result=repository.apply(storage,contribution.get());
                // endProvisional revalidates after writes; reads/replays need that same check here.
                if(mutation)coordinator.endProvisional(a);
                else coordinator.requireCurrent(a);
                return result;
            },repository::flush,()->{
                if(!repository.finalGuard(coordinator.repository(admission.get()),contribution.get(),coordinator.finalFences(admission.get())))
                    throw new Failure(Reason.UNAVAILABLE);
            });
        }catch(RuntimeException error) {
            if(error instanceof ProtectedTransactionExecutor.ExecutionFailure e
                    && e.outcome()==ProtectedTransactionExecutor.Outcome.INDETERMINATE)throw new Failure(Reason.INDETERMINATE,error);
            for(Throwable cause=error;cause!=null;cause=cause.getCause()) {
                if(cause instanceof Failure failure)throw failure;
                if(cause instanceof org.springframework.security.access.AccessDeniedException)throw new Failure(Reason.DENIED,error);
                if(cause instanceof java.sql.SQLException sql && sql.getSQLState()!=null && Set.of("23505","40001","40P01").contains(sql.getSQLState()))
                    throw new Failure(Reason.CONFLICT,error);
            }
            throw new Failure(Reason.UNAVAILABLE,error);
        }
    }
}
