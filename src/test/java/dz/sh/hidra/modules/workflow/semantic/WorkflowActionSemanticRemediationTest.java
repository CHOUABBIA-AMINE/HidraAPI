/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowActionSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : workflow
 * @Package     : dz.sh.hidra.modules.workflow.semantic
 *
 * @Description : Enforces owner-validated Workflow execution and immutable evidence.
 *
 */
package dz.sh.hidra.modules.workflow.semantic;

import dz.sh.hidra.modules.workflow.domain.model.*;
import dz.sh.hidra.modules.workflow.domain.value.*;
import dz.sh.hidra.modules.workflow.application.command.*;
import dz.sh.hidra.modules.workflow.application.service.WorkflowTransitionApplicationService;
import dz.sh.hidra.modules.workflow.application.port.out.*;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class WorkflowActionSemanticRemediationTest {
    RecordWorkflowActionCommand comment(WorkflowActionType type,WorkflowDecision decision,String task,String instance){
        return new RecordWorkflowActionCommand(instance,task,type,decision,null,null,"Comment","actor","forged","forged","ADMIN",null,null,"ADMIN",null,999,"forged",java.time.Instant.EPOCH);
    }
    @Test void genericEndpointCannotProduceDecisionOrLifecycleEvidence(){
        var f=new WorkflowSemanticFixtures();
        for(var type:WorkflowActionType.values()) if(type!=WorkflowActionType.COMMENT){
            assertThrows(RuntimeException.class,()->f.service().recordWorkflowAction(comment(type,null,null,"instance")));
        }
        assertThrows(RuntimeException.class,()->f.service().recordWorkflowAction(comment(WorkflowActionType.COMMENT,WorkflowDecision.APPROVE,null,"instance")));
        assertNull(f.savedAction);
    }
    @Test void commentUsesServerSequenceTimeAndCanonicalActor(){
        var f=new WorkflowSemanticFixtures();f.service().recordWorkflowAction(comment(WorkflowActionType.COMMENT,null,"task","instance"));
        assertEquals(7L,f.savedAction.actionSequence());assertEquals("Alice",f.savedAction.actorDisplayNameSnapshot());
        assertEquals("HIDRA_API",f.savedAction.sourceSystem());assertTrue(f.savedAction.actedAt().isAfter(java.time.Instant.EPOCH));
        assertNull(f.savedAction.actorRoleCodeSnapshot());
    }
    @Test void suppliedTaskMustExistAndBelongToInstance(){
        var f=new WorkflowSemanticFixtures();
        var tasks=WorkflowSemanticFixtures.port(WorkflowTaskRepositoryPort.class,(name,a)->Optional.empty());
        var defs=WorkflowSemanticFixtures.port(WorkflowDefinitionRepositoryPort.class,(name,a)->Optional.empty());
        var service=new dz.sh.hidra.modules.workflow.application.service.WorkflowApplicationService(f.instances(),tasks,f.actions(),defs,f.steps(),f.configuration(),f.ownership());
        assertThrows(RuntimeException.class,()->service.recordWorkflowAction(comment(WorkflowActionType.COMMENT,null,"unknown","instance")));
        var alien=WorkflowSemanticFixtures.port(WorkflowInstanceRepositoryPort.class,(name,a)->Optional.of(new WorkflowInstance("foreign","def",1,"purpose","planning","type","plan",null,null,WorkflowInstanceStatus.IN_PROGRESS,"step","actor","alice","Alice",null,f.at,null,null,null,f.at,f.at)));
        var finalService=new dz.sh.hidra.modules.workflow.application.service.WorkflowApplicationService(alien,f.tasks(),f.actions(),defs,f.steps(),f.configuration(),f.ownership());
        assertThrows(RuntimeException.class,()->finalService.recordWorkflowAction(comment(WorkflowActionType.COMMENT,null,"task","foreign")));
    }
    WorkflowAction action(WorkflowActionType type,WorkflowDecision decision,String reason,String text,String display){
        return new WorkflowAction("action","instance","task",type,decision,reason,null,text,"actor","alice",display,null,null,null,null,null,1L,"HIDRA_API",null,null,new WorkflowSemanticFixtures().at);
    }
    @Test void mandatoryActorEvidenceAndConditionalDecisionEvidenceFailFast(){
        assertThrows(RuntimeException.class,()->action(WorkflowActionType.COMMENT,null,null,"Text"," "));
        for(var d:List.of(WorkflowDecision.REJECT,WorkflowDecision.REQUEST_CORRECTION,WorkflowDecision.RETURN,WorkflowDecision.DELEGATE,WorkflowDecision.ESCALATE,WorkflowDecision.CANCEL))
            assertThrows(RuntimeException.class,()->action(WorkflowActionType.valueOf(d.name()),d,null,"Text","Alice"));
        assertThrows(RuntimeException.class,()->action(WorkflowActionType.REQUEST_CORRECTION,WorkflowDecision.REQUEST_CORRECTION,"reason",null,"Alice"));
        assertThrows(RuntimeException.class,()->action(WorkflowActionType.APPROVE,WorkflowDecision.REJECT,"reason","Text","Alice"));
    }
    @Test void callerWildcardCannotBypassLiveTransitionPermission(){
        var f=new WorkflowSemanticFixtures();f.permission=false;
        var transitions=WorkflowSemanticFixtures.port(WorkflowTransitionRepositoryPort.class,(name,a)->Optional.of(new WorkflowTransition("transition","def","step","next",WorkflowDecision.APPROVE,false,false,null,"governed-permission",null,f.at,f.at)));
        var history=WorkflowSemanticFixtures.port(WorkflowStateHistoryRepositoryPort.class,(name,a)->{throw new AssertionError("No history write allowed");});
        var rules=WorkflowSemanticFixtures.port(WorkflowStepAssignmentRuleRepositoryPort.class,(name,a)->Optional.empty());
        var service=new WorkflowTransitionApplicationService(f.tasks(),f.instances(),transitions,f.actions(),history,f.steps(),rules,f.ownership(),f.configuration());
        assertThrows(RuntimeException.class,()->service.execute(new ExecuteWorkflowTransitionCommand("task","transition",f.at,null,null,null,null,"actor","forged","forged",Set.of("*"))));
        assertNull(f.savedAction);
    }
}
