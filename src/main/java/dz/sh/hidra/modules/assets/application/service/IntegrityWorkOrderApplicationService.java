/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : IntegrityWorkOrderApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 * @Type        : Class
 * @Layer       : Application
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.application.service
 * @Description : T18 transaction-bound Assets Integrity-origin work-order lifecycle.
 */
package dz.sh.hidra.modules.assets.application.service;

import dz.sh.hidra.modules.assets.application.port.in.IntegrityWorkOrderUseCase;
import dz.sh.hidra.modules.assets.application.port.out.IntegrityWorkOrderStorePort;
import dz.sh.hidra.modules.assets.application.port.out.MaintainableAssetRepositoryPort;
import dz.sh.hidra.modules.assets.application.port.out.MaintenanceWorkOrderRepositoryPort;
import dz.sh.hidra.modules.assets.domain.service.IntegrityWorkOrderLifecycleGuard;
import dz.sh.hidra.modules.assets.domain.model.MaintenanceWorkOrder;
import dz.sh.hidra.modules.assets.domain.model.MaintainableAsset;
import dz.sh.hidra.modules.assets.domain.value.*;
import dz.sh.hidra.modules.integrity.application.contract.assets.ApprovedMaintenanceHandoffContract;
import dz.sh.hidra.modules.identity.application.contract.assets.AssetsMaintenanceScopedAuthorizationContract;
import dz.sh.hidra.modules.identity.application.contract.assets.MaintenanceWorkOrderActorReferenceContract;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.HexFormat;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public final class IntegrityWorkOrderApplicationService implements IntegrityWorkOrderUseCase {
    private final IntegrityWorkOrderStorePort store;
    private final MaintainableAssetRepositoryPort assets;
    private final MaintenanceWorkOrderRepositoryPort orders;
    private final ApprovedMaintenanceHandoffContract integrity;
    private final AssetsMaintenanceScopedAuthorizationContract identity;
    private final MaintenanceWorkOrderActorReferenceContract actorEligibility;
    private final IntegrityWorkOrderLifecycleGuard guard=new IntegrityWorkOrderLifecycleGuard();
    public IntegrityWorkOrderApplicationService(IntegrityWorkOrderStorePort store,
            MaintainableAssetRepositoryPort assets,MaintenanceWorkOrderRepositoryPort orders,
            ApprovedMaintenanceHandoffContract integrity,AssetsMaintenanceScopedAuthorizationContract identity,
            MaintenanceWorkOrderActorReferenceContract actorEligibility){
        this.store=Objects.requireNonNull(store);this.assets=Objects.requireNonNull(assets);
        this.orders=Objects.requireNonNull(orders);this.integrity=Objects.requireNonNull(integrity);
        this.identity=Objects.requireNonNull(identity);this.actorEligibility=Objects.requireNonNull(actorEligibility);
    }
    @Override @Transactional
    public Receipt handoff(Create c){
        Objects.requireNonNull(c,"Governed handoff input required.");
        String key=IntegrityWorkOrderLifecycleGuard.required(c.commandId(),80);
        String approval=IntegrityWorkOrderLifecycleGuard.required(c.approvalId(),80);
        String assetId=IntegrityWorkOrderLifecycleGuard.required(c.maintainableAssetId(),80);
        String number=IntegrityWorkOrderLifecycleGuard.required(c.workOrderNumber(),80);
        String type=IntegrityWorkOrderLifecycleGuard.required(c.workOrderTypeId(),80);
        String title=IntegrityWorkOrderLifecycleGuard.required(c.title(),255);
        if(c.description()!=null&&c.description().length()>1000)
            throw new IllegalArgumentException("Maintenance description exceeds allowed length.");
        var source=integrity.current(approval); // Integrity recommendation lock precedes any Assets order lock.
        identity.require("assets:integrity-work-orders:handoff",source.companyId(),source.assetType(),source.assetId());
        String actor=identity.currentActor();
        String digest=digest(key,approval,assetId,number,type,c.priorityId(),title,c.description(),actor);
        var prior=store.replay(key,digest);
        if(prior.isPresent()){
            var e=prior.orElseThrow();
            if(!"CREATE".equals(e.operation())||!source.approvalId().equals(
                    store.read(e.workOrderId()).orElseThrow().approvalId())
                    ||!actor.equals(e.actorId()))
                throw new IllegalStateException("Maintenance create replay binding conflict.");
            return receipt(e,source.approvalId(),true);
        }
        MaintainableAsset asset=assets.findByIdForUpdate(assetId)
                .orElseThrow(()->new NoSuchElementException("Protected maintainable asset unavailable."));
        if(asset.status()!=AssetLifecycleStatus.ACTIVE
                ||!source.assetType().equals(asset.topologyAssetTypeCode())
                ||!source.assetId().equals(asset.topologyAssetId()))
            throw new NoSuchElementException("Protected maintainable asset unavailable.");
        if(store.hasExistingRecommendation(source.recommendationId()))
            throw new IllegalStateException("Source recommendation already has an ordinary work order.");
        String id=UUID.randomUUID().toString();
        Instant at=Instant.now();
        // First save without foreign recommendation. The new Assets-owned link is then
        // inserted before associating the recommendation under HMR-069 validation.
        MaintenanceWorkOrder initial=new MaintenanceWorkOrder(id,number,assetId,null,null,
                type,c.priorityId(),MaintenanceWorkOrderStatus.DRAFT,title,c.description(),
                null,null,null,null,null,null,null,actor,at,at);
        orders.save(initial);
        var state=new IntegrityWorkOrderStorePort.State(id,source.approvalId(),source.findingId(),
                source.recommendationId(),source.companyId(),source.assetType(),source.assetId(),
                assetId,"DRAFT",0,null,null);
        store.createState(state);
        orders.save(copy(initial,MaintenanceWorkOrderStatus.DRAFT,null,source.recommendationId(),null,null,at));
        var event=store.append(state,key,digest,"CREATE","DRAFT",actor,null,null,null,
                "Approved Integrity handoff",at);
        integrity.link(approval,id);
        return receipt(event,approval,false);
    }
    @Override @Transactional
    public Receipt transition(String rawId,Action c){
        Objects.requireNonNull(c,"Maintenance transition required.");
        String id=IntegrityWorkOrderLifecycleGuard.required(rawId,80);
        String key=IntegrityWorkOrderLifecycleGuard.required(c.commandId(),80);
        if(c.expectedVersion()<0)throw new IllegalArgumentException("Expected version cannot be negative.");
        String operation=IntegrityWorkOrderLifecycleGuard.required(c.operation(),20);
        if(!java.util.Set.of("ASSIGN","START","EXECUTE","VERIFY").contains(operation))
            throw new IllegalArgumentException("Unsupported maintenance operation.");
        var snapshot=store.read(id).orElseThrow(
                ()->new NoSuchElementException("Protected maintenance order unavailable."));
        var source=integrity.current(snapshot.approvalId());
        requireMatching(snapshot,source);
        identity.require(permission(operation),snapshot.companyId(),snapshot.assetType(),snapshot.assetId());
        String actor=identity.currentActor();
        var state=store.lock(id);
        String summary=IntegrityWorkOrderLifecycleGuard.required(c.summary(),1000);
        String digest=digest(key,id,Long.toString(c.expectedVersion()),operation,
                c.assigneeId(),c.result(),c.completionId(),c.verdict(),summary,
                c.occurredAt()==null?null:c.occurredAt().toString(),actor);
        var prior=store.replay(key,digest);
        if(prior.isPresent()){
            var event=prior.orElseThrow();
            if(!id.equals(event.workOrderId())||!actor.equals(event.actorId()))
                throw new IllegalStateException("Maintenance command key belongs to another order or actor.");
            return receipt(event,state.approvalId(),true);
        }
        if(state.version()!=c.expectedVersion())
            throw new IllegalStateException("Stale maintenance order transition.");
        MaintenanceWorkOrder order=orders.findById(id).orElseThrow(
                ()->new NoSuchElementException("Protected maintenance parent unavailable."));
        if(!state.status().equals(order.status().name())
                ||!state.recommendationId().equals(order.sourceRecommendationId())
                ||!state.maintainableAssetId().equals(order.maintainableAssetId()))
            throw new IllegalStateException("Work order state and parent mismatch.");
        String assignee=state.assignedActorId();
        if("ASSIGN".equals(operation)){
            assignee=IntegrityWorkOrderLifecycleGuard.required(c.assigneeId(),80);
            if(!actorEligibility.eligible(assignee,Instant.now())
                    ||!identity.assigneeEligible(assignee,state.companyId(),state.assetType(),state.assetId()))
                throw new NoSuchElementException("Protected assignee unavailable.");
        }
        if("EXECUTE".equals(operation)){
            if(!"SUCCESS".equals(c.result()))
                throw new IllegalArgumentException("Completed maintenance requires an explicit SUCCESS execution result.");
            if(c.occurredAt()==null||c.occurredAt().isAfter(Instant.now())
                    ||order.startedAt()==null||c.occurredAt().isBefore(order.startedAt()))
                throw new IllegalArgumentException("Maintenance execution time must follow the authorized start.");
        }
        MaintenanceWorkOrderStatus next=guard.next(order.status(),operation,actor,
                state.assignedActorId(),c.completionId(),state.completionId(),c.verdict());
        Instant now=Instant.now();
        Instant updated=now.isAfter(order.updatedAt())?now:order.updatedAt().plusNanos(1);
        orders.save(copy(order,next,"ASSIGN".equals(operation)?assignee:state.assignedActorId(),
                state.recommendationId(),"START".equals(operation)?now:order.startedAt(),
                "EXECUTE".equals(operation)?c.occurredAt():order.completedAt(),updated));
        var event=store.append(state,key,digest,operation,next.name(),actor,assignee,
                state.completionId(),c.verdict(),summary,
                "EXECUTE".equals(operation)?c.occurredAt():now);
        if("VERIFY".equals(operation)){
            integrity.recordVerification(source.approvalId(),id,c.completionId(),event.eventId(),
                    actor,c.verdict(),summary,now);
        }
        return receipt(event,state.approvalId(),false);
    }
    @Override @Transactional
    public History history(String rawId){
        String id=IntegrityWorkOrderLifecycleGuard.required(rawId,80);
        var state=store.read(id).orElseThrow(()->new NoSuchElementException("Protected maintenance order unavailable."));
        var source=integrity.current(state.approvalId());
        requireMatching(state,source);
        identity.require("assets:integrity-work-orders:read",state.companyId(),state.assetType(),state.assetId());
        return new History(state.workOrderId(),state.approvalId(),state.recommendationId(),
                state.findingId(),state.assetId(),state.assetType(),state.companyId(),
                state.maintainableAssetId(),state.status(),state.version(),state.assignedActorId(),
                state.completionId(),store.history(id));
    }
    private static void requireMatching(IntegrityWorkOrderStorePort.State state,
            ApprovedMaintenanceHandoffContract.Approved source){
        if(!source.approvalId().equals(state.approvalId())
                ||!source.recommendationId().equals(state.recommendationId())
                ||!source.assetId().equals(state.assetId())||!source.assetType().equals(state.assetType())
                ||!source.companyId().equals(state.companyId())
                ||!source.findingId().equals(state.findingId()))
            throw new NoSuchElementException("Protected maintenance source mismatch.");
    }
    private static String permission(String op){
        return switch(op){
            case "ASSIGN" -> "assets:integrity-work-orders:assign";
            case "START","EXECUTE" -> "assets:integrity-work-orders:execute";
            case "VERIFY" -> "assets:integrity-work-orders:verify";
            default -> throw new IllegalArgumentException("Unsupported transition.");
        };
    }
    private static MaintenanceWorkOrder copy(MaintenanceWorkOrder x,MaintenanceWorkOrderStatus status,
            String assignee,String sourceRecommendationId,Instant started,Instant completed,Instant updated){
        return new MaintenanceWorkOrder(x.id(),x.workOrderNumber(),x.maintainableAssetId(),
                x.maintenancePlanId(),sourceRecommendationId,x.workOrderTypeId(),x.priorityId(),status,
                x.title(),x.description(),x.assignedOrganizationUnitId(),assignee,
                x.plannedStartAt(),x.plannedEndAt(),started,completed,x.workflowInstanceId(),
                x.createdByActorId(),x.createdAt(),updated);
    }
    private static Receipt receipt(IntegrityWorkOrderStorePort.Event e,String approval,boolean replay){
        return new Receipt(e.workOrderId(),approval,e.eventId(),e.operation(),
                e.status(),e.version(),e.actorId(),e.recordedAt(),replay);
    }
    private static String digest(String... parts){
        var b=new StringBuilder();
        for(String p:parts)if(p==null)b.append("N0:");else b.append("V").append(p.length()).append(':').append(p);
        try{return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256")
                .digest(b.toString().getBytes(StandardCharsets.UTF_8)));}
        catch(NoSuchAlgorithmException ex){throw new IllegalStateException(ex);}
    }
}
