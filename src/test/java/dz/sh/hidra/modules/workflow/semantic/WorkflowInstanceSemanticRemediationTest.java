/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowInstanceSemanticRemediationTest
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

import dz.sh.hidra.modules.workflow.application.command.StartWorkflowInstanceCommand;
import dz.sh.hidra.modules.workflow.application.service.*;
import dz.sh.hidra.modules.workflow.application.port.out.*;
import dz.sh.hidra.modules.workflow.domain.model.*;
import dz.sh.hidra.modules.workflow.domain.value.*;
import dz.sh.hidra.modules.identity.application.contract.workflow.WorkflowActorContract;
import dz.sh.hidra.modules.organization.application.contract.workflow.WorkflowOrganizationContract;
import dz.sh.hidra.modules.workflow.application.contract.target.WorkflowOwnedTargetLookup;
import dz.sh.hidra.platform.security.CurrentSecurityContext;
import dz.sh.hidra.platform.security.AuthenticatedPrincipal;
import dz.sh.hidra.kernel.domain.value.ActorId;
import java.time.Instant;
import java.util.*;
import java.lang.reflect.Proxy;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WorkflowInstanceSemanticRemediationTest {
    @Test void startUsesCanonicalEvidence() {
        var f=new WorkflowSemanticFixtures(); var result=f.service().startWorkflowInstance(f.start());
        assertEquals("Alice",f.savedInstance.startedByDisplayNameSnapshot());
        assertEquals("PLAN",f.savedInstance.targetCodeSnapshot());
        assertNull(f.savedInstance.startedByRoleCodeSnapshot());
    }
    @Test void callerCannotImpersonateStarter() {
        var f=new WorkflowSemanticFixtures(); f.actorId="intruder";
        assertThrows(RuntimeException.class,()->f.service().startWorkflowInstance(f.start())); assertNull(f.savedInstance);
    }
    @Test void inactiveActorCannotStart() {
        var f=new WorkflowSemanticFixtures(); f.actorEligible=false;
        assertThrows(RuntimeException.class,()->f.service().startWorkflowInstance(f.start()));assertNull(f.savedInstance);
    }
    @Test void requiresActiveMatchingDefinitionVersion() {
        var f=new WorkflowSemanticFixtures();f.version=2;
        assertThrows(RuntimeException.class,()->f.service().startWorkflowInstance(f.start()));
        f.version=1;f.definitionStatus=WorkflowDefinitionStatus.DRAFT;
        assertThrows(RuntimeException.class,()->f.service().startWorkflowInstance(f.start()));
    }
    @Test void missingBindingOrWrongCatalogDeniesStart() {
        var f=new WorkflowSemanticFixtures();f.binding=false;
        assertThrows(RuntimeException.class,()->f.service().startWorkflowInstance(f.start()));
        f.binding=true;f.catalogs.remove("purpose");
        assertThrows(RuntimeException.class,()->f.service().startWorkflowInstance(f.start()));
    }
    @Test void currentStepMustBelongToDefinition() {
        var f=new WorkflowSemanticFixtures();f.stepDefinition="another";
        assertThrows(RuntimeException.class,()->f.service().startWorkflowInstance(f.start()));
    }
    @Test void missingAndAmbiguousTargetOwnersDenyStart() {
        var f=new WorkflowSemanticFixtures();f.targets=List.of();
        assertThrows(RuntimeException.class,()->f.service().startWorkflowInstance(f.start()));
        var target=f.target();f.targets=List.of(target,target);
        assertThrows(RuntimeException.class,()->f.service().startWorkflowInstance(f.start()));
    }
    @Test void absentTargetDeniedWithoutSavingInstance() {
        var f=new WorkflowSemanticFixtures();f.targetEligible=false;
        assertThrows(RuntimeException.class,()->f.service().startWorkflowInstance(f.start()));assertNull(f.savedInstance);
    }
}

class WorkflowSemanticFixtures {
    final Instant at=Instant.parse("2026-10-06T12:00:00Z");
    String actorId="actor",stepDefinition="def";
    int version=1;
    boolean binding=true,actorEligible=true,targetEligible=true,member=true,permission=true;
    WorkflowDefinitionStatus definitionStatus=WorkflowDefinitionStatus.ACTIVE;
    WorkflowInstance savedInstance;
    WorkflowTask savedTask;
    WorkflowAction savedAction;
    Map<String,WorkflowConfigurationPort.Catalog> catalogs=new HashMap<>();
    List<WorkflowOwnedTargetLookup> targets=List.of(target());
    WorkflowSemanticFixtures(){
        for(String[] pair:List.of(new String[]{"purpose","WORKFLOW_PURPOSE"},new String[]{"type","WORKFLOW_TARGET_TYPE"},
                new String[]{"mode","WORKFLOW_ASSIGNMENT_MODE"},new String[]{"priority","WORKFLOW_PRIORITY"},new String[]{"reason","WORKFLOW_REASON"}))
            catalogs.put(pair[0],new WorkflowConfigurationPort.Catalog(pair[0],pair[1],pair[0].equals("type")?"PLAN_REVISION":pair[0],true));
    }
    WorkflowOwnedTargetLookup target(){return new WorkflowOwnedTargetLookup(){
        public String module(){return "planning";}public Set<String> targetTypeCodes(){return Set.of("PLAN_REVISION");}
        public Optional<Target> eligibleTarget(String type,String id){return targetEligible?Optional.of(new Target(id,"PLAN","Canonical plan")):Optional.empty();}
    };}
    WorkflowActorContract actors(){return new WorkflowActorContract(){
        public Optional<Actor> eligibleActor(String id,Instant time){return actorEligible && "actor".equals(id)?Optional.of(new Actor(id,"alice","Alice","employee")):Optional.empty();}
        public Optional<Actor> eligibleReference(String ref,Instant time){return eligibleActor("alice".equals(ref)?"actor":ref,time);}
        public boolean permitted(String id,String code,String instance){return permission;}
    };}
    WorkflowExecutionOwnership ownership(){
        var organizations=new WorkflowOrganizationContract(){
            public Optional<Unit> availableUnit(String id,Instant time){return "unit".equals(id)?Optional.of(new Unit(id,"Operations")):Optional.empty();}
            public boolean eligibleMember(String employee,String id,Instant time){return member && "employee".equals(employee) && "unit".equals(id);}
        };
        var context=new CurrentSecurityContext(){
            public Optional<AuthenticatedPrincipal> currentPrincipal(){return Optional.of(new AuthenticatedPrincipal(ActorId.of("actor"),"alice",true));}
            public void clear(){}
        };
        return new WorkflowExecutionOwnership(actors(),organizations,context,targets);
    }
    WorkflowConfigurationPort configuration(){return new WorkflowConfigurationPort(){
        public Catalog requireActiveCatalog(String id,String family){var c=catalogs.get(id);if(c==null || !c.active() || !family.equals(c.family())) throw new IllegalArgumentException("Invalid catalog");return c;}
        public boolean activeBinding(String def,String module,String type,String purpose){return binding;}
    };}
    @SuppressWarnings("unchecked") static <T>T port(Class<T> type,java.util.function.BiFunction<String,Object[],Object> calls){
        return (T)Proxy.newProxyInstance(type.getClassLoader(),new Class<?>[]{type},(p,m,a)->calls.apply(m.getName(),a));
    }
    WorkflowInstanceRepositoryPort instances(){return port(WorkflowInstanceRepositoryPort.class,(name,a)->{
        if(name.equals("save")){savedInstance=(WorkflowInstance)a[0];return savedInstance;}return Optional.of(instance());
    });}
    WorkflowTaskRepositoryPort tasks(){return port(WorkflowTaskRepositoryPort.class,(name,a)->{
        if(name.equals("save")){savedTask=(WorkflowTask)a[0];return savedTask;}return Optional.of(task());
    });}
    WorkflowActionRepositoryPort actions(){return port(WorkflowActionRepositoryPort.class,(name,a)->{
        if(name.equals("save")){savedAction=(WorkflowAction)a[0];return savedAction;}if(name.equals("nextSequence"))return 7L;return Optional.empty();
    });}
    WorkflowStepRepositoryPort steps(){return port(WorkflowStepRepositoryPort.class,(name,a)->Optional.of(step()));}
    WorkflowApplicationService service(){
        var definitions=port(WorkflowDefinitionRepositoryPort.class,(name,a)->Optional.of(new WorkflowDefinition("def","DEF",null,"Definition",null,"definition-type",definitionStatus,1,at,at)));
        return new WorkflowApplicationService(instances(),tasks(),actions(),definitions,steps(),configuration(),ownership());
    }
    StartWorkflowInstanceCommand start(){return new StartWorkflowInstanceCommand("def",version,"purpose","planning","type","plan","forged","forged", "step",actorId,"forged","forged","ADMIN",null);}
    WorkflowStep step(){return new WorkflowStep("step",stepDefinition,"STEP",null,"Review",null,1,true,"step-type",null,null,true,false,false,at,at);}
    WorkflowInstance instance(){return new WorkflowInstance("instance","def",1,"purpose","planning","type","plan","PLAN","Plan",WorkflowInstanceStatus.IN_PROGRESS,"step","actor","alice","Alice",null,at,null,null,null,at,at);}
    WorkflowTask task(){return new WorkflowTask("task","instance","step",WorkflowTaskStatus.OPEN,"actor","alice","Alice",null,null,null,null,null,null,null,null,null,"mode","Review",WorkflowSlaStatus.NORMAL,null,null,null,at,at);}
}
