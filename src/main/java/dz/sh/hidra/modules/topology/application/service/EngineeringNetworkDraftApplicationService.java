/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringNetworkDraftApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-20
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Coordinates protected metadata and graph operations in one physical transaction.
 *
 */
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
public class EngineeringNetworkDraftApplicationService implements EngineeringNetworkDraftUseCase, EngineeringGraphDraftUseCase, dz.sh.hidra.modules.topology.application.port.in.SurveyRouteProfileUseCase {
    private final ProtectedEngineeringDraftAdmissionCoordinator coordinator;
    private final EngineeringNetworkDraftRepositoryPort repository;
    private final ProtectedTransactionExecutor executor;
    private final EngineeringGraphDraftRepositoryPort graphRepository;
    private final EngineeringGraphStationWitnessPort stationWitness;

    private final dz.sh.hidra.modules.topology.application.port.out.SurveyRouteProfileRepositoryPort surveys;

    public EngineeringNetworkDraftApplicationService(ProtectedEngineeringDraftAdmissionCoordinator c,
            EngineeringNetworkDraftRepositoryPort r, ProtectedTransactionExecutor e,
            EngineeringGraphDraftRepositoryPort graphs, EngineeringGraphStationWitnessPort witnesses,
            dz.sh.hidra.modules.topology.application.port.out.SurveyRouteProfileRepositoryPort surveys) {
        coordinator=Objects.requireNonNull(c);repository=Objects.requireNonNull(r);executor=Objects.requireNonNull(e);
        graphRepository=Objects.requireNonNull(graphs);stationWitness=Objects.requireNonNull(witnesses);this.surveys=surveys;
    }
    private dz.sh.hidra.modules.topology.application.port.out.PipelineAuthoringPersistencePort pipelines;
    @Autowired
    public EngineeringNetworkDraftApplicationService(ProtectedEngineeringDraftAdmissionCoordinator c,
            EngineeringNetworkDraftRepositoryPort r,ProtectedTransactionExecutor e,
            EngineeringGraphDraftRepositoryPort graphs,EngineeringGraphStationWitnessPort witnesses,
            dz.sh.hidra.modules.topology.application.port.out.SurveyRouteProfileRepositoryPort surveys,
            dz.sh.hidra.modules.topology.application.port.out.PipelineAuthoringPersistencePort pipelines) {
        this(c,r,e,graphs,witnesses,surveys);this.pipelines=Objects.requireNonNull(pipelines);
    }
    /** Retained isolated graph constructor; survey operations fail closed without a repository. */
    public EngineeringNetworkDraftApplicationService(ProtectedEngineeringDraftAdmissionCoordinator c,
            EngineeringNetworkDraftRepositoryPort r, ProtectedTransactionExecutor e,
            EngineeringGraphDraftRepositoryPort graphs, EngineeringGraphStationWitnessPort witnesses) {
        this(c,r,e,graphs,witnesses,null);
    }
    /** Retained for isolated metadata tests. It must never activate graph authoring. */
    public EngineeringNetworkDraftApplicationService(ProtectedEngineeringDraftAdmissionCoordinator c,
            EngineeringNetworkDraftRepositoryPort r, ProtectedTransactionExecutor e) {
        coordinator=Objects.requireNonNull(c);repository=Objects.requireNonNull(r);executor=Objects.requireNonNull(e);
        graphRepository=null;stationWitness=null;surveys=null;
    }

    @Override public EngineeringNetworkDraftUseCase.Result execute(EngineeringNetworkDraftUseCase.Command cmd) {
        Objects.requireNonNull(cmd);
        if (cmd.kind()==EngineeringNetworkDraftUseCase.Kind.SURVEY_READ || cmd.kind()==EngineeringNetworkDraftUseCase.Kind.SURVEY_UPDATE ||
                cmd.kind()==EngineeringNetworkDraftUseCase.Kind.GRAPH_READ ||
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
    @Override public dz.sh.hidra.modules.topology.application.port.in.SurveyRouteProfileUseCase.Result executeSurvey(
            dz.sh.hidra.modules.topology.application.port.in.SurveyRouteProfileUseCase.Command command){
        Objects.requireNonNull(command);requireNoOuterTransaction();
        if(surveys==null || graphRepository==null || stationWitness==null)throw new EngineeringNetworkDraftFailure(Reason.UNAVAILABLE);
        boolean update=command.kind()==dz.sh.hidra.modules.topology.application.port.in.SurveyRouteProfileUseCase.Kind.UPDATE;
        // Pure codec bounds checked before provisional storage; invalid payload remains a 400.
        if(update)surveys.validateProfile(command.profile());
        var kind=update?EngineeringNetworkDraftUseCase.Kind.SURVEY_UPDATE:EngineeringNetworkDraftUseCase.Kind.SURVEY_READ;
        var cmd=new EngineeringNetworkDraftUseCase.Command(kind,command.companyPartyId(),command.pipelineSystemId(),command.draftId(),
            null,command.expectedVersion(),null,command.requestedVersion(),null,command.branchId(),command.profile());
        var a=new AtomicReference<ProtectedEngineeringDraftAdmissionCoordinator.Admission>();
        var c=new AtomicReference<ProtectedEngineeringDraftAdmissionCoordinator.Contribution>();
        try {var prepared=coordinator.prepare();
            return executor.executePublication(()->{
                var admission=coordinator.acquire(prepared,cmd);a.set(admission);c.set(coordinator.collect(admission));
                var ticket=coordinator.repository(admission);
                long observed=repository.currentGraphDraft(ticket,command.requestedVersion());
                long graphAt=command.requestedVersion()==null?observed:command.requestedVersion();
                var graph=graphRepository.asOf(command.draftId(),graphAt);
                if(graph==null || graph.graph().branches().stream().noneMatch(b->b.id().equals(command.branchId())
                    && b.kind()==dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft.BranchKind.PIPE))
                    throw new EngineeringNetworkDraftFailure(Reason.NOT_FOUND);
                var witnesses=stationWitness.requireCurrent(command.companyPartyId(),graph.graph());
                var snapshot=update?null:surveys.asOf(command.draftId(),command.branchId(),graphAt);
                if(snapshot!=null && (snapshot.graphVersion()!=graph.version() || !snapshot.graphSha256().equals(graph.sha256())))
                    throw new EngineeringNetworkDraftFailure(Reason.CONFLICT);
                if(update)coordinator.beginProvisional(admission,repository.surveyMutationSql());
                var base=repository.apply(ticket,c.get());
                long selected=update?base.draft().version():graphAt;
                if(update)snapshot=surveys.append(command.draftId(),selected,graph.version(),graph.sha256(),command.profile(),c.get().transactionId());
                if(update && (snapshot==null || snapshot.version()!=selected || !command.profile().equals(snapshot.profile())))
                    throw new EngineeringNetworkDraftFailure(Reason.UNAVAILABLE);
                if(update)coordinator.endProvisional(admission);
                repository.bindGraphProof(ticket,selected,graph,witnesses,observed);
                repository.bindSurveyProof(ticket,snapshot);coordinator.requireCurrent(admission);
                return new dz.sh.hidra.modules.topology.application.port.in.SurveyRouteProfileUseCase.Result(command.draftId(),command.branchId(),selected,
                    snapshot==null?null:snapshot.version(),snapshot==null?null:snapshot.sha256(),snapshot==null?null:snapshot.graphVersion(),
                    snapshot==null?null:snapshot.graphSha256(),snapshot==null?null:snapshot.profile(),
                    snapshot==null?List.of("SURVEY_NOT_AUTHORED"):List.of("PHYSICAL_QUALIFICATION_NOT_ESTABLISHED"));
            },repository::flush,()->{
                if(!repository.finalGuard(coordinator.repository(a.get()),c.get(),coordinator.finalFences(a.get())))
                    throw new EngineeringNetworkDraftFailure(Reason.UNAVAILABLE);
            });
        }catch(RuntimeException failure){throw translate(failure);}
    }

    @Override public dz.sh.hidra.modules.topology.application.port.in.AuthoredTopologyViewUseCase.View executePipeline(
            dz.sh.hidra.modules.topology.application.port.in.PipelineAuthoringUseCase.Write command){
        Objects.requireNonNull(command);return executeAuthored(command.scope(),command,null,null,null).view();
    }
    @Override public dz.sh.hidra.modules.topology.application.port.in.AuthoredTopologyViewUseCase.View executeAuthoredView(
            dz.sh.hidra.modules.topology.application.port.in.PipelineAuthoringUseCase.Scope scope,Long version){
        if(version!=null&&version<1)throw new IllegalArgumentException("Positive as-of version required");return executeAuthored(scope,null,version,null,null).view();
    }
    @Override public dz.sh.hidra.modules.topology.application.port.in.AuthoredTopologyViewUseCase.Discovery executeAuthoredDiscovery(
            dz.sh.hidra.modules.topology.application.port.in.PipelineAuthoringUseCase.Scope scope,int offset,int limit){
        if(offset<0||offset>100000||limit<1||limit>200)throw new IllegalArgumentException("Bounded discovery required");return executeAuthored(scope,null,null,offset,limit).discovery();
    }
    public record AuthoredResult(dz.sh.hidra.modules.topology.application.port.in.AuthoredTopologyViewUseCase.View view,
            dz.sh.hidra.modules.topology.application.port.in.AuthoredTopologyViewUseCase.Discovery discovery) {}
    private AuthoredResult executeAuthored(dz.sh.hidra.modules.topology.application.port.in.PipelineAuthoringUseCase.Scope scope,
            dz.sh.hidra.modules.topology.application.port.in.PipelineAuthoringUseCase.Write write,Long version,Integer offset,Integer limit){
        Objects.requireNonNull(scope);requireNoOuterTransaction();
        if(pipelines==null||graphRepository==null||stationWitness==null)throw new EngineeringNetworkDraftFailure(Reason.UNAVAILABLE);
        boolean update=write!=null;
        var cmd=new EngineeringNetworkDraftUseCase.Command(update?EngineeringNetworkDraftUseCase.Kind.GRAPH_UPDATE:EngineeringNetworkDraftUseCase.Kind.GRAPH_READ,
            scope.companyPartyId(),scope.pipelineSystemId(),scope.draftId(),null,update?write.expectedVersion():null,null,version,update?write.graph():null);
        var retained=new AtomicReference<ProtectedEngineeringDraftAdmissionCoordinator.Admission>();
        var actor=new AtomicReference<ProtectedEngineeringDraftAdmissionCoordinator.Contribution>();
        try {var prepared=coordinator.prepare();
            return executor.executePublication(()->{
                var admission=coordinator.acquire(prepared,cmd);retained.set(admission);
                // Additional source/association locks precede sealing, in the same physical transaction.
                var pipelineAdmission=pipelines.acquire(scope,offset!=null);actor.set(coordinator.collect(admission));
                var ticket=coordinator.repository(admission);long observed=repository.currentGraphDraft(ticket,version);
                if(update){
                    stationWitness.requireCurrent(scope.companyPartyId(),write.graph());pipelines.prepare(pipelineAdmission,write,actor.get());
                    var sql=new HashSet<String>(repository.graphMutationSql());sql.addAll(pipelines.mutationSql());
                    coordinator.beginPipelineProvisional(admission,Set.copyOf(sql));
                }
                var base=repository.apply(ticket,actor.get());long selected=update?base.draft().version():version==null?base.draft().version():version;
                var snapshot=update?graphRepository.append(scope.draftId(),selected,write.graph(),actor.get().transactionId()):graphRepository.asOf(scope.draftId(),selected);
                if(update){
                    if(snapshot==null||snapshot.version()!=selected||!snapshot.graph().equals(write.graph()))throw new EngineeringNetworkDraftFailure(Reason.UNAVAILABLE);
                    pipelines.persist(pipelineAdmission);coordinator.endProvisional(admission);
                }
                var graph=snapshot==null?null:snapshot.graph();
                var witnesses=stationWitness.requireCurrent(scope.companyPartyId(),graph==null?new dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft(List.of(),List.of(),List.of()):graph);
                repository.bindGraphProof(ticket,selected,snapshot,witnesses,observed);coordinator.requireCurrent(admission);
                var result=new EngineeringGraphDraftUseCase.Result(scope.draftId(),selected,snapshot==null?null:snapshot.version(),snapshot==null?null:snapshot.sha256(),graph,graph==null?List.of("GRAPH_NOT_AUTHORED"):graph.diagnostics());
                var view=pipelines.materialize(pipelineAdmission,result);
                var choices=offset==null?null:pipelines.discover(pipelineAdmission,offset,limit);
                pipelines.freeze(pipelineAdmission);repository.bindPipelineProof(ticket,pipelineAdmission);
                return new AuthoredResult(view,choices);
            },repository::flush,()->{
                if(!repository.finalGuard(coordinator.repository(retained.get()),actor.get(),coordinator.finalFences(retained.get())))throw new EngineeringNetworkDraftFailure(Reason.UNAVAILABLE);
            });
        }catch(RuntimeException failure){throw translate(failure);}
    }

    private static void requireNoOuterTransaction(){
        if(TransactionSynchronizationManager.isActualTransactionActive())
            throw new EngineeringNetworkDraftFailure(Reason.UNAVAILABLE);
    }
    private static EngineeringNetworkDraftFailure translate(RuntimeException failure){
        if(failure instanceof EngineeringNetworkDraftFailure e)return e;
        if(failure instanceof ProtectedTransactionExecutor.ExecutionFailure execution){
            if(execution.outcome()==ProtectedTransactionExecutor.Outcome.INDETERMINATE)
                return translated(Reason.INDETERMINATE,failure);
            Throwable cause=execution;
            while(cause!=null){
                if(cause instanceof EngineeringNetworkDraftFailure e)return e;
                if(cause instanceof org.springframework.security.access.AccessDeniedException)
                    return translated(Reason.DENIED,failure);
                cause=cause.getCause();
            }
            return translated(Reason.UNAVAILABLE,failure);
        }
        if(failure instanceof org.springframework.security.access.AccessDeniedException)
            return translated(Reason.DENIED,failure);
        return translated(Reason.UNAVAILABLE,failure);
    }
    /** Preserve internal rollback evidence while the HTTP boundary exposes only the fixed reason. */
    private static EngineeringNetworkDraftFailure translated(Reason reason, RuntimeException cause) {
        var failure=new EngineeringNetworkDraftFailure(reason);
        failure.initCause(cause);
        return failure;
    }

}
