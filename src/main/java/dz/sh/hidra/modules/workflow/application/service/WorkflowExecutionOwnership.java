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
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowStepAssignmentRuleRepositoryPort;
import dz.sh.hidra.modules.workflow.application.port.out.WorkflowConfigurationPort;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowDefinition;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowInstance;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowStep;
import dz.sh.hidra.modules.workflow.domain.model.WorkflowTask;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDefinitionStatus;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowTaskStatus;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final WorkflowStepAssignmentRuleRepositoryPort rules;
    // Preserve direct constructor clients; production uses the explicitly wired source rule port.
    public WorkflowExecutionOwnership(WorkflowActorContract actors,WorkflowOrganizationContract organizations,
            CurrentSecurityContext security,List<WorkflowOwnedTargetLookup> targets) {
        this(actors,organizations,security,targets,null);
    }
    @Autowired
    public WorkflowExecutionOwnership(WorkflowActorContract actors,WorkflowOrganizationContract organizations,
            CurrentSecurityContext security,List<WorkflowOwnedTargetLookup> targets,
            WorkflowStepAssignmentRuleRepositoryPort rules) {
        this.actors=Objects.requireNonNull(actors);this.organizations=Objects.requireNonNull(organizations);
        this.security=Objects.requireNonNull(security);this.targets=List.copyOf(targets);
        this.rules=rules;
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
    public boolean pressureOwnerAllowed(String targetId, String locator, String actor, boolean start) {
        var owners = targets.stream().filter(owner -> "topology".equals(owner.module())
                && owner.targetTypeCodes().contains(WorkflowOwnedTargetLookup.OPERATING_PRESSURE_TARGET)).toList();
        if (owners.size() != 1) return false;
        var owner = owners.getFirst();
        return start ? owner.pressureStartAllowed(targetId, locator, actor)
                : owner.pressureReviewAllowed(targetId, locator, actor);
    }

    public boolean pressureReviewAllowed(String module, String targetId, String locator, String targetTypeId,
                                         String actor, WorkflowConfigurationPort configuration) {
        if (!"topology".equals(module) || locator == null || !locator.startsWith("t36-v1:")) return true;
        var type = configuration.requireActiveCatalog(targetTypeId, "WORKFLOW_TARGET_TYPE");
        return !WorkflowOwnedTargetLookup.OPERATING_PRESSURE_TARGET.equals(type.code())
                || pressureOwnerAllowed(targetId, locator, actor, false);
    }

    public boolean permitted(String actorId,String permission,String instanceId){return actors.permitted(actorId,permission,instanceId);}
    public WorkflowOrganizationContract.Unit requireUnit(String id){
        return organizations.availableUnit(id,Instant.now()).orElseThrow(()->denied("Workflow organization unit unavailable: "+id));
    }
    public boolean member(WorkflowActorContract.Actor actor,String unitId){
        return organizations.eligibleMember(actor.employeeId(),unitId,Instant.now());
    }
    public WorkflowOwnedTargetLookup.Target requireTarget(String module,String code,String id){
        return requireTarget(module,code,id,null);
    }
    public WorkflowOwnedTargetLookup.Target requireTarget(String module,String code,String id,String locator){
        var matches=targets.stream().filter(t->module.equals(t.module()) && t.targetTypeCodes().contains(code)).toList();
        if(matches.size()!=1) throw denied("Exactly one owner target resolver required: "+module+"/"+code);
        var target=matches.get(0).eligibleTarget(code,id,locator).orElseThrow(()->denied("Workflow target is unavailable."));
        if(!id.equals(target.id())) throw denied("Owner target identity mismatch.");
        return target;
    }
    /** Refresh native Topology rights at every Workflow operation. */
    public void requireProtectedCurrentTarget(dz.sh.hidra.modules.workflow.domain.model.WorkflowInstance instance,
            dz.sh.hidra.modules.workflow.application.port.out.WorkflowConfigurationPort configuration) {
        if ("reporting".equals(instance.targetModule())) {
            var type=configuration.requireActiveCatalog(instance.targetTypeId(),"WORKFLOW_TARGET_TYPE");
            var purpose=configuration.requireActiveCatalog(instance.workflowPurposeId(),"WORKFLOW_PURPOSE");
            if (!WorkflowOwnedTargetLookup.scopedReportApproval(type.code(),purpose.code())
                    || !configuration.activeBinding(instance.definitionId(),"reporting",
                            instance.targetTypeId(),instance.workflowPurposeId()))
                throw denied("Workflow target unavailable.");
            requireTarget("reporting",type.code(),instance.targetId());
            return;
        }
        if ("hse".equals(instance.targetModule())) {
            var type = configuration.requireActiveCatalog(instance.targetTypeId(), "WORKFLOW_TARGET_TYPE");
            var purpose = configuration.requireActiveCatalog(instance.workflowPurposeId(), "WORKFLOW_PURPOSE");
            if (!WorkflowOwnedTargetLookup.hseApproval(type.code(), purpose.code())
                    || !configuration.activeBinding(instance.definitionId(), "hse",
                            instance.targetTypeId(), instance.workflowPurposeId()))
                throw denied("Workflow target unavailable.");
            requireTarget("hse", type.code(), instance.targetId());
            return;
        }
        if (!"topology".equals(instance.targetModule())) return;
        var type=configuration.requireActiveCatalog(instance.targetTypeId(),"WORKFLOW_TARGET_TYPE");
        boolean baseline = WorkflowOwnedTargetLookup.BASELINE_TARGET.equals(type.code());
        boolean pressure = "OPERATING_PRESSURE_RESTRICTION".equals(type.code());
        if (!baseline && !pressure && !"INSTALLED_CHARACTERISTIC_LINK_REVISION".equals(type.code())) return;
        var purpose=configuration.requireActiveCatalog(instance.workflowPurposeId(),"WORKFLOW_PURPOSE");
        if (!(baseline ? WorkflowOwnedTargetLookup.BASELINE_PURPOSE : pressure
                ? "OPERATING_PRESSURE_RESTRICTION_APPROVAL"
                : "INSTALLED_CHARACTERISTIC_LINK_APPROVAL").equals(purpose.code())
                || !configuration.activeBinding(instance.definitionId(),instance.targetModule(),
                        instance.targetTypeId(),instance.workflowPurposeId()))
            throw denied("Topology Workflow target unavailable.");
        requireTarget(instance.targetModule(),type.code(),instance.targetId(),instance.targetCodeSnapshot());
    }
    /**
     * The opted-in Reporting definition is the only Workflow assignment source for T27.
     * Validate the saved task and exact current DIRECT default rule before delegating
     * the owned report target into its original source transaction.
     */
    public void publishOptedInReportAssignment(WorkflowInstance instance,WorkflowStep step,
            WorkflowTask task,WorkflowDefinition definition,WorkflowConfigurationPort configuration) {
        if (!"T27_SCOPED_REPORT_APPROVAL_INBOX_V1".equals(definition.code())) return;
        if (definition.status()!=WorkflowDefinitionStatus.ACTIVE
                || definition.version()!=instance.definitionVersion()
                || !definition.id().equals(instance.definitionId())
                || !"reporting".equals(instance.targetModule())
                || task.status()!=WorkflowTaskStatus.OPEN
                || !task.instanceId().equals(instance.id())
                || !step.id().equals(task.stepId())
                || !instance.definitionId().equals(step.definitionId())
                || task.assignedActorId()==null
                || task.assignedActorId().equals(instance.startedByActorId())
                || task.assignedOrganizationUnitId()!=null
                || task.assignedRoleCodeSnapshot()!=null
                || step.defaultAssignmentRuleId()==null
                || rules==null)
            throw denied("Opted-in report task requires a current independent direct assignment.");
        var type=configuration.requireActiveCatalog(instance.targetTypeId(),"WORKFLOW_TARGET_TYPE");
        var purpose=configuration.requireActiveCatalog(instance.workflowPurposeId(),"WORKFLOW_PURPOSE");
        if (!WorkflowOwnedTargetLookup.scopedReportApproval(type.code(),purpose.code())
                || !configuration.activeBinding(definition.id(),"reporting",
                        instance.targetTypeId(),instance.workflowPurposeId()))
            throw denied("Opted-in task requires the exact active Reporting binding.");
        var rule=rules.findById(step.defaultAssignmentRuleId())
                .orElseThrow(()->denied("Missing opted-in assignment rule."));
        if (!rule.active() || !definition.id().equals(rule.definitionId())
                || !step.id().equals(rule.stepId())
                || !task.assignedActorId().equals(rule.actorId())
                || rule.organizationUnitId()!=null || rule.roleCode()!=null
                || rule.organizationRoleCode()!=null || rule.targetOwnerMode()!=null
                || !"DIRECT".equals(configuration.requireActiveCatalog(
                        rule.assignmentModeId(),"WORKFLOW_ASSIGNMENT_MODE").code())
                || task.assignmentModeId()!=null
                    && !"DIRECT".equals(configuration.requireActiveCatalog(
                            task.assignmentModeId(),"WORKFLOW_ASSIGNMENT_MODE").code()))
            throw denied("Opted-in report assignment does not match the current direct rule.");
        var owners=targets.stream().filter(t->"reporting".equals(t.module())
                && t.targetTypeCodes().contains(WorkflowOwnedTargetLookup.REPORT_REQUEST_TARGET)).toList();
        if (owners.size()!=1 || !owners.getFirst().reportTaskAssigned(
                instance.targetId(),task.id(),task.assignedActorId(),task.createdAt()))
            throw denied("No single Reporting owner accepted the opted-in assignment.");
    }

    public boolean scopedReportStarterAllowed(String targetId) {
        return targets.stream().filter(t -> "reporting".equals(t.module())
                && t.targetTypeCodes().contains(WorkflowOwnedTargetLookup.REPORT_REQUEST_TARGET))
                .anyMatch(t -> t.scopedReportStarterAllowed(targetId));
    }
    public boolean scopedReportReviewerAllowed(String targetId) {
        var owners=targets.stream().filter(t -> "reporting".equals(t.module())
                && t.targetTypeCodes().contains(WorkflowOwnedTargetLookup.REPORT_REQUEST_TARGET)).toList();
        return owners.size()==1 && owners.getFirst().protectedReviewAllowed(
                WorkflowOwnedTargetLookup.REPORT_REQUEST_TARGET,targetId,null);
    }
    public boolean hseReviewerAllowed(String type, String targetId) {
        var owners = targets.stream().filter(t -> "hse".equals(t.module())
                && t.targetTypeCodes().contains(type)).toList();
        return owners.size() == 1 && owners.getFirst().protectedReviewAllowed(type, targetId, null);
    }

    public boolean baselineReviewerAllowed(dz.sh.hidra.modules.workflow.domain.model.WorkflowInstance instance) {
        var owners = targets.stream().filter(t -> "topology".equals(t.module())
                && t.targetTypeCodes().contains(WorkflowOwnedTargetLookup.BASELINE_TARGET)).toList();
        return owners.size() == 1 && owners.getFirst().protectedReviewAllowed(WorkflowOwnedTargetLookup.BASELINE_TARGET,
                instance.targetId(), instance.targetCodeSnapshot());
    }

    private static WorkflowTransitionDeniedException denied(String message){return new WorkflowTransitionDeniedException(message);}
}
