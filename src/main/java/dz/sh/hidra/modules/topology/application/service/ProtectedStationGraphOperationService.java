/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedStationGraphOperationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-18
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Orchestrates authenticated station publications under one physical protected transaction.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import dz.sh.hidra.modules.identity.application.contract.topology.TopologyScopedAccessContract.Operation;
import dz.sh.hidra.modules.identity.application.contract.topology.TopologyScopedAccessContract.Request;
import dz.sh.hidra.modules.topology.application.port.in.ProtectedStationGraphOperationUseCase;
import dz.sh.hidra.modules.topology.application.port.out.StationGraphProtectedPublicationPort;
import dz.sh.hidra.modules.topology.domain.model.*;
import dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor;
import jakarta.persistence.EntityManager;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/** No REST endpoint, implicit permission, cross-module persistence write or user callback. */
@Service
public final class ProtectedStationGraphOperationService implements ProtectedStationGraphOperationUseCase {
    private final ProtectedStationAdmissionCoordinator coordinator;
    private final StationGraphProtectedPublicationPort publication;
    private final ProtectedTransactionExecutor executor;
    private final EntityManager em;
    public ProtectedStationGraphOperationService(ProtectedStationAdmissionCoordinator c,
            StationGraphProtectedPublicationPort p,ProtectedTransactionExecutor e,EntityManager em){
        coordinator=Objects.requireNonNull(c);publication=Objects.requireNonNull(p);
        executor=Objects.requireNonNull(e);this.em=Objects.requireNonNull(em);
    }
    private static IllegalStateException invalid(){return new IllegalStateException("Protected station operation unavailable");}
    private static Operation operation(Kind k){
        return switch(k){
            case AUTHOR->Operation.AUTHOR;case PUBLISH->Operation.PUBLISH;
            case READ_CURRENT->Operation.READ_CURRENT;case READ_HISTORY->Operation.READ_HISTORY;
            case LIST_HISTORY->Operation.LIST_HISTORY;case WITHDRAW->Operation.WITHDRAW;
            case REACTIVATE->Operation.REACTIVATE;case TRANSFER->throw invalid();
        };
    }
    private static StationGraphPublicationEvent event(StationGraphPublicationEvent.Kind kind,
           UUID id,Long epoch,String prior,String next,ProtectedStationAdmissionCoordinator.Contribution c) {
        return new StationGraphPublicationEvent(id,c.facilityId(),kind,epoch,prior,next,
            c.bindingId(),c.sessionId(),c.userId(),c.companyPartyId(),c.partyGeneration(),
            c.facilityGeneration(),c.sampledAt(),c.validUntil(),c.backendPid(),c.transactionId());
    }
    @Override @Transactional(propagation=Propagation.NEVER)
    public Result execute(Command cmd){
        Objects.requireNonNull(cmd);
        Operation op=operation(cmd.kind()); // TRANSFER is unsupported before disclosing existence.
        if(cmd.kind()==Kind.AUTHOR && (cmd.authoredRevision()==null || cmd.revisionId()==null))throw invalid();
        if(Set.of(Kind.PUBLISH,Kind.READ_HISTORY).contains(cmd.kind()) && cmd.revisionId()==null)throw invalid();
        if(Set.of(Kind.PUBLISH,Kind.WITHDRAW,Kind.REACTIVATE).contains(cmd.kind()) &&
            (cmd.expectedEpoch()==null || cmd.expectedEpoch()<0))throw invalid();
        if(cmd.kind()==Kind.LIST_HISTORY && cmd.limit()<1)throw invalid();
        var prepared=coordinator.prepare();
        AtomicReference<ProtectedStationAdmissionCoordinator.Admission> auth=new AtomicReference<>();
        AtomicReference<StationGraphProtectedPublicationPort.Admission> ticket=new AtomicReference<>();
        AtomicReference<ProtectedStationAdmissionCoordinator.Contribution> evidence=new AtomicReference<>();
        AtomicReference<StationGraphPublicationHead> resultHead=new AtomicReference<>();
        AtomicReference<UUID> eventId=new AtomicReference<>();
        Result uncommitted=executor.execute(()->{
            var admission=coordinator.acquire(prepared,new Request(op,cmd.companyPartyId(),cmd.facilityId()));
            auth.set(admission);
            ticket.set(publication.acquire(cmd.facilityId())); // Head/event/revision fences BEFORE common sample.
            var c=coordinator.collect(admission);
            evidence.set(c);
            var current=publication.head(ticket.get()).orElse(null);
            resultHead.set(current);
            StationInternalGraphRevision revision=null;List<StationGraphPublicationEvent> history=List.of();
            UUID id=UUID.randomUUID();
            switch(cmd.kind()){
                case AUTHOR->{
                    revision=cmd.authoredRevision();
                    var e=event(StationGraphPublicationEvent.Kind.AUTHOR,id,null,
                        revision.expectedPredecessorRevisionId(),revision.revisionId(),c);
                    coordinator.beginProvisional(admission,publication.mutationSql());
                    publication.append(ticket.get(),revision,e);
                    coordinator.endProvisional(admission);eventId.set(id);
                }
                case PUBLISH->{
                    revision=publication.revision(ticket.get(),cmd.revisionId()).orElseThrow(ProtectedStationGraphOperationService::invalid);
                    if(revision.graph().status()!=StationInternalGraph.Status.COMPLETE ||
                       revision.qualification()!=StationInternalGraphRevision.Qualification.STRUCTURALLY_QUALIFIED ||
                       revision.evidenceReference()==null)throw invalid();
                    long expected=cmd.expectedEpoch();
                    if(expected==0){
                        if(current!=null || cmd.expectedRevisionId()!=null ||
                           revision.expectedPredecessorRevisionId()!=null)throw invalid();
                    }else if(current==null || current.epoch()!=expected ||
                             current.state()!=StationGraphPublicationHead.State.ACTIVE ||
                             !current.revisionId().equals(cmd.expectedRevisionId()) ||
                             !current.revisionId().equals(revision.expectedPredecessorRevisionId()))throw invalid();
                    var next=current==null
                        ?new StationGraphPublicationHead(cmd.facilityId(),cmd.revisionId(),1,
                            StationGraphPublicationHead.State.ACTIVE,id)
                        :current.transitioned(cmd.revisionId(),StationGraphPublicationHead.State.ACTIVE,id);
                    var e=event(StationGraphPublicationEvent.Kind.PUBLISH,id,next.epoch(),
                           current==null?null:current.revisionId(),next.revisionId(),c);
                    coordinator.beginProvisional(admission,publication.mutationSql());
                    resultHead.set(publication.change(ticket.get(),next,e,expected,cmd.expectedRevisionId()));
                    coordinator.endProvisional(admission);eventId.set(id);
                }
                case WITHDRAW,REACTIVATE->{
                    if(current==null || !Objects.equals(cmd.expectedEpoch(),current.epoch()) ||
                       !Objects.equals(cmd.expectedRevisionId(),current.revisionId()))throw invalid();
                    boolean withdrawal=cmd.kind()==Kind.WITHDRAW;
                    var next=current.transitioned(current.revisionId(),
                        withdrawal?StationGraphPublicationHead.State.WITHDRAWN:StationGraphPublicationHead.State.ACTIVE,id);
                    var e=event(withdrawal?StationGraphPublicationEvent.Kind.WITHDRAW:
                            StationGraphPublicationEvent.Kind.REACTIVATE,id,next.epoch(),
                            current.revisionId(),next.revisionId(),c);
                    coordinator.beginProvisional(admission,publication.mutationSql());
                    resultHead.set(publication.change(ticket.get(),next,e,current.epoch(),current.revisionId()));
                    coordinator.endProvisional(admission);eventId.set(id);
                }
                case READ_CURRENT->{
                    if(current!=null && current.state()==StationGraphPublicationHead.State.ACTIVE)
                        revision=publication.revision(ticket.get(),current.revisionId()).orElseThrow(ProtectedStationGraphOperationService::invalid);
                }
                case READ_HISTORY->{
                    var items=publication.history(ticket.get(),100);
                    if(items.stream().noneMatch(x->cmd.revisionId().equals(x.resultingRevisionId())))throw invalid();
                    revision=publication.revision(ticket.get(),cmd.revisionId()).orElseThrow(ProtectedStationGraphOperationService::invalid);
                }
                case LIST_HISTORY->history=publication.history(ticket.get(),cmd.limit());
                case TRANSFER->throw invalid();
            }
            coordinator.requireCurrent(admission);
            return new Result(resultHead.get(),revision,history,false);
        },()->{
            em.flush();coordinator.requireCurrent(auth.get());
        },()->{
            var e=evidence.get();
            if(e==null || !publication.finalGuard(ticket.get(),e.sampledAt(),e.validUntil(),
                 e.companyPartyId(),e.partyGeneration(),e.facilityGeneration(),
                 resultHead.get(),eventId.get()))throw invalid();
        });
        return new Result(uncommitted.head(),uncommitted.revision(),uncommitted.history(),true);
    }
}
