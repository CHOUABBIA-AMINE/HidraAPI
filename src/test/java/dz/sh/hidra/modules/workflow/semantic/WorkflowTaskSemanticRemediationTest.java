/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowTaskSemanticRemediationTest
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

import dz.sh.hidra.modules.workflow.domain.model.WorkflowTask;
import dz.sh.hidra.modules.workflow.domain.value.*;
import dz.sh.hidra.modules.workflow.application.command.CreateWorkflowTaskCommand;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.adapter.JpaWorkflowTaskRepositoryAdapter;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.mapper.WorkflowPersistenceMapper;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.repository.WorkflowTaskJpaRepository;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class WorkflowTaskSemanticRemediationTest {
    final Instant at=Instant.parse("2026-10-06T12:00:00Z");
    WorkflowTask task(String actor,String unit,String claimant,Instant claim,String completer,Instant complete,WorkflowTaskStatus status){
        return new WorkflowTask("task","instance","step",status,actor,"alice","Alice",unit,null,"ADMIN",null,null,claimant,claim,completer,complete,"mode","Review",WorkflowSlaStatus.NORMAL,null,null,null,at,at);
    }
    @Test void actionableTaskRequiresExplicitAssignment(){assertThrows(RuntimeException.class,()->task(null,null,null,null,null,null,WorkflowTaskStatus.OPEN));}
    @Test void claimAndCompletionRequirePairsAndChronology(){
        assertThrows(RuntimeException.class,()->task("actor",null,"actor",null,null,null,WorkflowTaskStatus.CLAIMED));
        assertThrows(RuntimeException.class,()->task("actor",null,null,null,"actor",at.minusSeconds(1),WorkflowTaskStatus.APPROVED));
        assertThrows(RuntimeException.class,()->task("actor",null,"other",at,null,null,WorkflowTaskStatus.CLAIMED));
    }
    @Test void usernameSnapshotCannotGrantTaskExecution(){
        var f=new WorkflowSemanticFixtures();
        assertFalse(f.ownership().canExecute(task("other",null,null,null,null,null,WorkflowTaskStatus.OPEN),"actor",true));
        assertTrue(f.ownership().canExecute(f.task(),"actor",false));
    }
    @Test void unitPoolRequiresLiveMembershipAndClaimableStep(){
        var f=new WorkflowSemanticFixtures();var pool=task(null,"unit",null,null,null,null,WorkflowTaskStatus.OPEN);
        assertTrue(f.ownership().canExecute(pool,"actor",true));
        assertFalse(f.ownership().canExecute(pool,"actor",false));
        f.member=false;assertFalse(f.ownership().canExecute(pool,"actor",true));
    }
    @Test void inactiveActorAndWrongTaskCatalogDenied(){
        var f=new WorkflowSemanticFixtures();f.actorEligible=false;
        assertThrows(RuntimeException.class,()->f.ownership().validateAssignment(f.task(),f.configuration()));
        f.actorEligible=true;f.catalogs.remove("mode");
        assertThrows(RuntimeException.class,()->f.ownership().validateAssignment(f.task(),f.configuration()));
    }
    @Test void genericCreationChecksOwnerAndCanonicalSnapshots(){
        var f=new WorkflowSemanticFixtures();f.service().createWorkflowTask(new CreateWorkflowTaskCommand("instance","step","actor","forged","forged",null,null,"ADMIN","priority",null,"mode","Review"));
        assertEquals("Alice",f.savedTask.assignedActorDisplayNameSnapshot());
        f.stepDefinition="other";
        assertThrows(RuntimeException.class,()->f.service().createWorkflowTask(new CreateWorkflowTaskCommand("instance","step","actor",null,null,null,null,null,null,null,"mode","Review")));
    }
    @Test void terminalTaskCannotBeOverwrittenThroughRepository(){
        var f=new WorkflowSemanticFixtures();var terminal=task("actor",null,null,null,"actor",at,WorkflowTaskStatus.APPROVED);
        var repository=WorkflowSemanticFixtures.port(WorkflowTaskJpaRepository.class,(name,a)->{
            if(name.equals("findById"))return Optional.of(WorkflowPersistenceMapper.toEntity(terminal));
            throw new AssertionError("Terminal task must not be saved");
        });
        assertThrows(RuntimeException.class,()->new JpaWorkflowTaskRepositoryAdapter(repository,f.ownership(),f.configuration()).save(f.task()));
    }
}
