/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ManufacturerCharacteristicRevisionApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-30
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.application.service
 *
 * @Description : Protected manufacturer source and qualification orchestration.
 *
 */
package dz.sh.hidra.modules.simulation.application.service;

import dz.sh.hidra.modules.simulation.application.port.in.ManufacturerCharacteristicRevisionUseCase;
import dz.sh.hidra.modules.simulation.application.port.out.ManufacturerCharacteristicRevisionRepositoryPort;
import dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor;
import java.util.Optional;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.DefaultTransactionStatus;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionTemplate;

/** Never fabricates an authenticated user, physical commit or active manufacturer authorization. */
@Service
public final class ManufacturerCharacteristicRevisionApplicationService
        implements ManufacturerCharacteristicRevisionUseCase {
    private final ProtectedManufacturerCharacteristicAdmissionCoordinator coordinator;
    private final ManufacturerCharacteristicRevisionRepositoryPort repository;
    private final ProtectedTransactionExecutor executor;
    private final PlatformTransactionManager manager;
    public ManufacturerCharacteristicRevisionApplicationService(
            ProtectedManufacturerCharacteristicAdmissionCoordinator coordinator,
            ManufacturerCharacteristicRevisionRepositoryPort repository,
            ProtectedTransactionExecutor executor,PlatformTransactionManager manager) {
        this.coordinator=Objects.requireNonNull(coordinator);
        this.repository=Objects.requireNonNull(repository);
        this.executor=Objects.requireNonNull(executor);
        this.manager=Objects.requireNonNull(manager);
    }

    @Override public Result execute(Command command) {
        Objects.requireNonNull(command);
        if(TransactionSynchronizationManager.isActualTransactionActive()
                ||TransactionSynchronizationManager.isSynchronizationActive())
            throw new IllegalStateException("Manufacturer source requires a new original transaction");
        var prepared=coordinator.prepare(command);
        var admission=new AtomicReference<ProtectedManufacturerCharacteristicAdmissionCoordinator.Admission>();
        return executor.executePublication(()->{
            var a=coordinator.acquire(prepared,command);
            admission.set(a);
            return coordinator.perform(a);
        },()->{
            var a=Objects.requireNonNull(admission.get(),"Missing manufacturer admission");
            coordinator.prepareFinal(a);
            coordinator.flush();
        },()->{
            var a=Objects.requireNonNull(admission.get(),"Missing manufacturer admission");
            if(!coordinator.finalGuard(a))
                throw new IllegalStateException("Manufacturer final source guard unavailable");
        });
    }


    @Override public HistoryPageResult pageHistory(HistoryPageQuery query) {
        Objects.requireNonNull(query);
        if(TransactionSynchronizationManager.isActualTransactionActive()
                ||TransactionSynchronizationManager.isSynchronizationActive())
            throw new IllegalStateException("Manufacturer history requires a new original transaction");
        var prepared=coordinator.preparePage(query);
        var admission=new AtomicReference<ProtectedManufacturerCharacteristicAdmissionCoordinator.Admission>();
        return executor.executePublication(()->{
            var a=coordinator.acquirePage(prepared);
            admission.set(a);
            return coordinator.performPage(a);
        },()->{
            var a=Objects.requireNonNull(admission.get(),"Missing manufacturer history admission");
            coordinator.prepareFinal(a);
            coordinator.flush();
        },()->{
            var a=Objects.requireNonNull(admission.get(),"Missing manufacturer history admission");
            if(!coordinator.finalGuard(a))
                throw new IllegalStateException("Manufacturer history guard unavailable");
        });
    }

    /**
     * Called only by Workflow's manufacturer-owned lookup. Suspends an existing Workflow
     * read transaction and verifies original native Identity, Party and source scope on a
     * separate restricted physical transaction. It never returns source bytes.
     */
    public Optional<ProtectedManufacturerCharacteristicAdmissionCoordinator.WorkflowTargetView>
            resolveWorkflowTarget(String digest,boolean history) {
        if(digest==null||!digest.matches("[0-9a-f]{64}"))return Optional.empty();
        executor.requireNoProtectedContext();
        TransactionTemplate suspended=new TransactionTemplate(manager);
        suspended.setPropagationBehavior(TransactionDefinition.PROPAGATION_NOT_SUPPORTED);
        return suspended.execute(status-> {
            boolean scaffold=status instanceof DefaultTransactionStatus d && d.isNewSynchronization();
            if(scaffold){
                if(TransactionSynchronizationManager.isActualTransactionActive()
                    ||!TransactionSynchronizationManager.getSynchronizations().isEmpty())
                    throw new IllegalStateException("Unexpected suspension synchronization state");
                TransactionSynchronizationManager.clearSynchronization();
            }
            try {
                var prepared=coordinator.prepareWorkflowTarget(digest,history);
                var admission=new AtomicReference<ProtectedManufacturerCharacteristicAdmissionCoordinator.Admission>();
                try {
                    return executor.executePublication(()->{
                        var a=coordinator.acquireWorkflowTarget(prepared,digest,history);
                        admission.set(a);
                        var view=coordinator.perform(a);
                        var key=new ManufacturerCharacteristicRevisionRepositoryPort.Key(
                            view.companyPartyId(),view.sourceId(),view.revisionId());
                        return Optional.of(new ProtectedManufacturerCharacteristicAdmissionCoordinator.WorkflowTargetView(
                            coordinator.targetDigest(key,view.payloadSha256()),view.revisionId()));
                    },()->{
                        coordinator.prepareFinal(admission.get());
                        coordinator.flush();
                    },()->{
                        if(!coordinator.finalGuard(admission.get()))
                            throw new IllegalStateException("Manufacturer read guard unavailable");
                    });
                }catch(ProtectedTransactionExecutor.ExecutionFailure failure){
                    if(failure.outcome()==ProtectedTransactionExecutor.Outcome.INDETERMINATE)
                        throw new IllegalStateException("Manufacturer read outcome indeterminate",failure);
                    return Optional.empty();
                }
            }finally{
                if(scaffold)TransactionSynchronizationManager.initSynchronization();
            }
        });
    }
}
