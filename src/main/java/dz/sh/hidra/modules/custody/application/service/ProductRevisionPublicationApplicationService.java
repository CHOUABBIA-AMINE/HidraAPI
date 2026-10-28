/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProductRevisionPublicationApplicationService
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

import dz.sh.hidra.modules.custody.application.port.in.ProductRevisionPublicationUseCase;
import dz.sh.hidra.modules.custody.application.port.out.ProductRevisionPublicationRepositoryPort;
import dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
public class ProductRevisionPublicationApplicationService implements ProductRevisionPublicationUseCase {
    private final ProtectedProductPublicationAdmissionCoordinator coordinator;
    private final ProductRevisionPublicationRepositoryPort repository;
    private final ProtectedTransactionExecutor executor;
    private final org.springframework.transaction.PlatformTransactionManager manager;
    public ProductRevisionPublicationApplicationService(ProtectedProductPublicationAdmissionCoordinator coordinator,
            ProductRevisionPublicationRepositoryPort repository,ProtectedTransactionExecutor executor,org.springframework.transaction.PlatformTransactionManager manager) {
        this.coordinator=Objects.requireNonNull(coordinator);this.repository=Objects.requireNonNull(repository);this.executor=Objects.requireNonNull(executor);this.manager=Objects.requireNonNull(manager);
    }
    @Override public Optional<String> candidateForWorkflow(String digest) {
        executor.requireNoProtectedContext();
        var suspended=new org.springframework.transaction.support.TransactionTemplate(manager);
        suspended.setPropagationBehavior(org.springframework.transaction.TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
        return suspended.execute(status->{
            try {var result=execute(Command.workflow(digest));return Optional.of(result.candidateDigest());}
            catch(Failure denied){if(denied.reason()==Reason.DENIED)return Optional.empty();throw denied;}
        });
    }
    @Override public Result execute(Command command) {
        Objects.requireNonNull(command);
        if(TransactionSynchronizationManager.isActualTransactionActive() || TransactionSynchronizationManager.isSynchronizationActive())
            throw new Failure(Reason.UNAVAILABLE);
        var admission=new AtomicReference<ProtectedProductPublicationAdmissionCoordinator.Admission>();
        var contribution=new AtomicReference<ProtectedProductPublicationAdmissionCoordinator.Contribution>();
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
                repository.captureImages(storage,coordinator.finalFences(a));
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
