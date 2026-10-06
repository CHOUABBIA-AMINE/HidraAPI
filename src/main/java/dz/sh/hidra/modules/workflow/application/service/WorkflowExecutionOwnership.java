/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowExecutionOwnership
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.service
 *
 * @Description : Enforces owner-validated Workflow execution and immutable evidence.
 *
 */
package dz.sh.hidra.modules.workflow.application.service;

import dz.sh.hidra.modules.identity.application.contract.workflow.WorkflowActorContract;
import dz.sh.hidra.modules.organization.application.contract.workflow.WorkflowOrganizationContract;
import dz.sh.hidra.modules.workflow.application.contract.target.WorkflowOwnedTargetLookup;
import dz.sh.hidra.platform.security.CurrentSecurityContext;
import dz.sh.hidra.modules.workflow.domain.exception.WorkflowTransitionDeniedException;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;
@Service
public class WorkflowExecutionOwnership {
    private final WorkflowActorContract actors;
    private final WorkflowOrganizationContract organizations;
    private final CurrentSecurityContext security;
    private final List<WorkflowOwnedTargetLookup> targets;
    public WorkflowExecutionOwnership(WorkflowActorContract actors,WorkflowOrganizationContract organizations,
            CurrentSecurityContext security,List<WorkflowOwnedTargetLookup> targets) {
        this.actors=Objects.requireNonNull(actors);this.organizations=Objects.requireNonNull(organizations);
        this.security=Objects.requireNonNull(security);this.targets=List.copyOf(targets);
    }
    public WorkflowActorContract.Actor requireCurrentActor(){
        var principal=security.currentPrincipal().filter(p->p.authenticated()).orElseThrow(()->denied("Authenticated Workflow actor required."));
        return requireActor(principal.actorId().value());
    }
    public void validateAssignment(dz.sh.hidra.modules.workflow.domain.model.WorkflowTask task,
            dz.sh.hidra.modules.workflow.application.port.out.WorkflowConfigurationPort configuration){
        if(task.priorityId()!=null) configuration.requireActiveCatalog(task.priorityId(),"WORKFLOW_PRIORITY");
        if(task.assignmentModeId()!=null) configuration.requireActiveCatalog(task.assignmentModeId(),"WORKFLOW_ASSIGNMENT_MODE");
        var actor=task.assignedActorId()==null?null:requireActor(task.assignedActorId());
        if(task.assignedOrganizationUnitId()!=null){
            requireUnit(task.assignedOrganizationUnitId());
            if(actor!=null && !member(actor,task.assignedOrganizationUnitId())) throw denied("Assigned actor is not an eligible organization member.");
        }
        if(task.claimedByActorId()!=null){
            var claimant=requireActor(task.claimedByActorId());
            if(task.assignedOrganizationUnitId()!=null && !member(claimant,task.assignedOrganizationUnitId())) throw denied("Claimant is not an eligible organization member.");
        }
    }
    public boolean canExecute(dz.sh.hidra.modules.workflow.domain.model.WorkflowTask task,String actorId,boolean allowClaim){
        var actor=actors.eligibleActor(actorId,Instant.now());
        if(actor.isEmpty() || !task.openTask()) return false;
        if(task.assignedActorId()!=null && !task.assignedActorId().equals(actorId)) return false;
        if(task.claimedByActorId()!=null && !task.claimedByActorId().equals(actorId)) return false;
        if(task.assignedOrganizationUnitId()!=null && !member(actor.get(),task.assignedOrganizationUnitId())) return false;
        return task.assignedActorId()!=null || (task.assignedOrganizationUnitId()!=null && allowClaim);
    }
    public WorkflowActorContract.Actor requireCurrentActor(String suppliedId){
        var principal=security.currentPrincipal().filter(p->p.authenticated())
            .orElseThrow(()->denied("Authenticated Workflow actor required."));
        if(!principal.actorId().value().equals(suppliedId)) throw denied("Workflow actor must match authenticated principal.");
        return requireActor(suppliedId);
    }
    public WorkflowActorContract.Actor requireActor(String id){
        return actors.eligibleActor(id,Instant.now()).orElseThrow(()->denied("Workflow actor is unavailable: "+id));
    }
    public Optional<WorkflowActorContract.Actor> resolveActor(String reference){
        return actors.eligibleReference(reference,Instant.now());
    }
    public boolean permitted(String actorId,String permission,String instanceId){return actors.permitted(actorId,permission,instanceId);}
    public WorkflowOrganizationContract.Unit requireUnit(String id){
        return organizations.availableUnit(id,Instant.now()).orElseThrow(()->denied("Workflow organization unit unavailable: "+id));
    }
    public boolean member(WorkflowActorContract.Actor actor,String unitId){
        return organizations.eligibleMember(actor.employeeId(),unitId,Instant.now());
    }
    public WorkflowOwnedTargetLookup.Target requireTarget(String module,String code,String id){
        var matches=targets.stream().filter(t->module.equals(t.module()) && t.targetTypeCodes().contains(code)).toList();
        if(matches.size()!=1) throw denied("Exactly one owner target resolver required: "+module+"/"+code);
        var target=matches.get(0).eligibleTarget(code,id).orElseThrow(()->denied("Workflow target is unavailable."));
        if(!id.equals(target.id())) throw denied("Owner target identity mismatch.");
        return target;
    }
    private static WorkflowTransitionDeniedException denied(String message){return new WorkflowTransitionDeniedException(message);}
}
