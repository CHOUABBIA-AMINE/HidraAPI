/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : HseCapaDecisionQueryService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Class
 * @Layer       : Application
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.application.service
 * @Description : Actual Workflow APPROVE action with bound HSE_CAPA or HSE_CASE target, not matching-only.
 */
package dz.sh.hidra.modules.workflow.application.service;

import dz.sh.hidra.modules.workflow.application.contract.hse.HseCapaDecisionContract;
import dz.sh.hidra.modules.workflow.application.contract.hse.HseWorkflowReferenceContract;
import dz.sh.hidra.modules.workflow.application.port.out.*;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDecision;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HexFormat;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
@Service
public class HseCapaDecisionQueryService implements HseCapaDecisionContract {
    private final WorkflowActionRepositoryPort actions;
    private final WorkflowInstanceRepositoryPort instances;
    private final HseWorkflowReferenceContract bindings;
    public HseCapaDecisionQueryService(WorkflowActionRepositoryPort actions,
            WorkflowInstanceRepositoryPort instances,HseWorkflowReferenceContract bindings){
        this.actions=actions;this.instances=instances;this.bindings=bindings;
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Optional<Decision> approved(String actionId,String type,String targetId){
        if(actionId==null||targetId==null
                ||!java.util.Set.of("HSE_CAPA","HSE_CASE").contains(type))return Optional.empty();
        var action=actions.findById(actionId).orElse(null);
        if(action==null||action.decision()!=WorkflowDecision.APPROVE
                ||action.actionType()!=dz.sh.hidra.modules.workflow.domain.value.WorkflowActionType.APPROVE
                ||action.taskId()==null||action.actorId()==null)return Optional.empty();
        var instance=instances.findById(action.instanceId()).orElse(null);
        if(instance==null||!"hse".equals(instance.targetModule())
                ||!targetId.equals(instance.targetId()))return Optional.empty();
        boolean bound="HSE_CASE".equals(type)
                ? bindings.caseMatches(instance.id(),targetId)
                : bindings.taskMatches(action.taskId(),null,targetId);
        if(!bound)return Optional.empty();
        String input=action.id()+"|"+action.instanceId()+"|"+action.actionSequence()+"|"+
                action.actorId()+"|"+action.actedAt();
        try{
            byte[] digest=MessageDigest.getInstance("SHA-256").digest(input.getBytes(StandardCharsets.UTF_8));
            return Optional.of(new Decision(action.id(),instance.id(),action.taskId(),targetId,
                    action.actorId(),HexFormat.of().formatHex(digest)));
        }catch(java.security.NoSuchAlgorithmException impossible){throw new IllegalStateException(impossible);}
    }
}
