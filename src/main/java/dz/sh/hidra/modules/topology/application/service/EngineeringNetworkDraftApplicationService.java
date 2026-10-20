/** HAPI-588B work-in-progress: existing aggregate owner, one protected physical transaction. */
package dz.sh.hidra.modules.topology.application.service;

import dz.sh.hidra.modules.topology.application.port.in.EngineeringNetworkDraftUseCase;
import dz.sh.hidra.modules.topology.application.port.in.EngineeringGraphDraftUseCase;
import dz.sh.hidra.modules.topology.application.port.out.EngineeringNetworkDraftRepositoryPort;
import dz.sh.hidra.modules.topology.application.port.out.EngineeringGraphDraftRepositoryPort;
import dz.sh.hidra.modules.topology.application.port.out.EngineeringGraphStationWitnessPort;
import dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure;
import dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure.Reason;
import dz.sh.hidra.modules.topology.domain.model.EngineeringNetworkDraft;
import dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
public class EngineeringNetworkDraftApplicationService implements EngineeringNetworkDraftUseCase, EngineeringGraphDraftUseCase {
    private final ProtectedEngineeringDraftAdmissionCoordinator coordinator;
    private final EngineeringNetworkDraftRepositoryPort repository;
    private final ProtectedTransactionExecutor executor;
    private final EngineeringGraphDraftRepositoryPort graphRepository;
    private final EngineeringGraphStationWitnessPort stationWitness;

    @Autowired
    public EngineeringNetworkDraftApplicationService(ProtectedEngineeringDraftAdmissionCoordinator c,
            EngineeringNetworkDraftRepositoryPort r, ProtectedTransactionExecutor e,
            EngineeringGraphDraftRepositoryPort graphs, EngineeringGraphStationWitnessPort witnesses) {
        coordinator=Objects.requireNonNull(c);repository=Objects.requireNonNull(r);executor=Objects.requireNonNull(e);
        graphRepository=Objects.requireNonNull(graphs);stationWitness=Objects.requireNonNull(witnesses);
    }
    /** Retained for isolated metadata tests. It must never activate graph authoring. */
    public EngineeringNetworkDraftApplicationService(ProtectedEngineeringDraftAdmissionCoordinator c,
            EngineeringNetworkDraftRepositoryPort r, ProtectedTransactionExecutor e) {
        coordinator=Objects.requireNonNull(c);repository=Objects.requireNonNull(r);executor=Objects.requireNonNull(e);
        graphRepository=null;stationWitness=null;
    }

    @Override public EngineeringNetworkDraftUseCase.Result execute(EngineeringNetworkDraftUseCase.Command cmd) {
        Objects.requireNonNull(cmd);
        if (cmd.kind()==EngineeringNetworkDraftUseCase.Kind.GRAPH_READ ||
                cmd.kind()==EngineeringNetworkDraftUseCase.Kind.GRAPH_UPDATE)
            throw new EngineeringNetworkDraftFailure(Reason.UNAVAILABLE); // graph must use typed endpoint
        requireNoOuterTransaction();
        var a=new AtomicReference<ProtectedEngineeringDraftAdmissionCoordinator.Admission>();
        var c=new AtomicReference<ProtectedEngineeringDraftAdmissionCoordinator.Contribution>();
        try {var prepared=coordinator.prepare();
            return executor.executePublication(()->{
                var admission=coordinator.acquire(prepared,cmd);a.set(admission);c.set(coordinator.collect(admission));
                var r=coordinator.repository(admission);
                boolean mutation=cmd.kind()==EngineeringNetworkDraftUseCase.Kind.UPDATE ||
                        (cmd.kind()==EngineeringNetworkDraftUseCase.Kind.CREATE && !repository.replay(r));
                if(mutation)coordinator.beginProvisional(admission,repository.mutationSql());
                var result=repository.apply(r,c.get());
                if(mutation)coordinator.endProvisional(admission);
                coordinator.requireCurrent(admission);return result;
            },repository::flush,()->{
                if(!repository.finalGuard(coordinator.repository(a.get()),c.get(),coordinator.finalFences(a.get())))
                    throw new EngineeringNetworkDraftFailure(Reason.UNAVAILABLE);
            });
        } catch (RuntimeException failure) {throw translate(failure);}
    }

    @Override public EngineeringGraphDraftUseCase.Result executeGraph(EngineeringGraphDraftUseCase.Command command) {
        Objects.requireNonNull(command);
        requireNoOuterTransaction();
        if(graphRepository==null || stationWitness==null)throw new EngineeringNetworkDraftFailure(Reason.UNAVAILABLE);
        var action = command.kind()==EngineeringGraphDraftUseCase.Kind.UPDATE
                ? EngineeringNetworkDraftUseCase.Kind.GRAPH_UPDATE : EngineeringNetworkDraftUseCase.Kind.GRAPH_READ;
        var cmd=new EngineeringNetworkDraftUseCase.Command(action,command.companyPartyId(),command.pipelineSystemId(),
                command.draftId(),null,command.expectedVersion(),null,command.requestedVersion(),command.graph());
        var a=new AtomicReference<ProtectedEngineeringDraftAdmissionCoordinator.Admission>();
        var c=new AtomicReference<ProtectedEngineeringDraftAdmissionCoordinator.Contribution>();
        try {var prepared=coordinator.prepare();
            return executor.executePublication(()->{
                var admission=coordinator.acquire(prepared,cmd);a.set(admission);c.set(coordinator.collect(admission));
                var ticket=coordinator.repository(admission);
                boolean update=action==EngineeringNetworkDraftUseCase.Kind.GRAPH_UPDATE;
                var existing=repository.currentGraphDraft(ticket,command.requestedVersion());
                // Witness observations happen after native Identity/Party authorization and
                // before any provisional mutation, under all graph/facility relation fences.
                if(update)stationWitness.requireCurrent(command.companyPartyId(),command.graph());
                if(update)coordinator.beginProvisional(admission,repository.graphMutationSql());
                EngineeringNetworkDraftUseCase.Result base=repository.apply(ticket,c.get());
                long selected=update?base.draft().version():
                    command.requestedVersion()==null?base.draft().version():command.requestedVersion();
                EngineeringGraphDraftRepositoryPort.Snapshot snap=update
                    ? graphRepository.append(command.draftId(),selected,command.graph(),c.get().transactionId())
                    : graphRepository.asOf(command.draftId(),selected);
                // The outbound adapter must not silently substitute a different authored graph.
                if(update && (snap==null || !command.graph().equals(snap.graph())
                        || snap.version()!=selected))
                    throw new EngineeringNetworkDraftFailure(Reason.UNAVAILABLE);
                if(update)coordinator.endProvisional(admission);
                var graph=snap==null?null:snap.graph();
                var witnesses=stationWitness.requireCurrent(command.companyPartyId(),
                    graph==null?new dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft(List.of(),List.of(),List.of()):graph);
                repository.bindGraphProof(ticket, selected, snap, witnesses, existing);
                coordinator.requireCurrent(admission);
                var diagnostics=graph==null?List.of("GRAPH_NOT_AUTHORED"):graph.diagnostics();
                return new EngineeringGraphDraftUseCase.Result(command.draftId(),selected,
                    snap==null?null:snap.version(),snap==null?null:snap.sha256(),graph,diagnostics);
            },repository::flush,()->{
                if(!repository.finalGuard(coordinator.repository(a.get()),c.get(),coordinator.finalFences(a.get())))
                    throw new EngineeringNetworkDraftFailure(Reason.UNAVAILABLE);
            });
        } catch (RuntimeException failure) {throw translate(failure);}
    }
    private static void requireNoOuterTransaction(){
        if(TransactionSynchronizationManager.isActualTransactionActive())
            throw new EngineeringNetworkDraftFailure(Reason.UNAVAILABLE);
    }
    private static EngineeringNetworkDraftFailure translate(RuntimeException failure){
        if(failure instanceof EngineeringNetworkDraftFailure e)return e;
        if(failure instanceof ProtectedTransactionExecutor.ExecutionFailure execution){
            if(execution.outcome()==ProtectedTransactionExecutor.Outcome.INDETERMINATE)
                return new EngineeringNetworkDraftFailure(Reason.INDETERMINATE);
            Throwable cause=execution;
            while(cause!=null){
                if(cause instanceof EngineeringNetworkDraftFailure e)return e;
                if(cause instanceof org.springframework.security.access.AccessDeniedException)
                    return new EngineeringNetworkDraftFailure(Reason.DENIED);
                cause=cause.getCause();
            }
            return new EngineeringNetworkDraftFailure(Reason.UNAVAILABLE);
        }
        if(failure instanceof org.springframework.security.access.AccessDeniedException)
            return new EngineeringNetworkDraftFailure(Reason.DENIED);
        return new EngineeringNetworkDraftFailure(Reason.UNAVAILABLE);
    }
}
