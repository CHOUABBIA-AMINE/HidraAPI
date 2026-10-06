/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowStateHistorySemanticRemediationTest
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

import dz.sh.hidra.modules.workflow.domain.model.WorkflowStateHistory;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.adapter.JpaWorkflowStateHistoryRepositoryAdapter;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.repository.WorkflowStateHistoryJpaRepository;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.mapper.WorkflowPersistenceMapper;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.entity.*;
import jakarta.persistence.EntityManager;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class WorkflowStateHistorySemanticRemediationTest {
    WorkflowStateHistory history(String task,String from,String action,String reason,String status,String display){
        return new WorkflowStateHistory("history","instance",task,from,null,null,status,"actor","alice",display,null,action,reason,null,new WorkflowSemanticFixtures().at);
    }
    @Test void mandatoryDestinationAndActorEvidenceFailFast(){
        assertThrows(RuntimeException.class,()->history(null,null,null,null," ","Alice"));
        assertThrows(RuntimeException.class,()->history(null,null,null,null,"COMPLETED",null));
    }
    @Test void optionalReferencesStayOptionalAndPersistenceInserts(){
        var f=new WorkflowSemanticFixtures();var inserted=new ArrayList<Object>();
        var em=WorkflowSemanticFixtures.port(EntityManager.class,(name,a)->{
            if(name.equals("find"))return WorkflowPersistenceMapper.toEntity(f.instance());
            if(name.equals("persist")){inserted.add(a[0]);return null;}return null;
        });
        var repo=WorkflowSemanticFixtures.port(WorkflowStateHistoryJpaRepository.class,(name,a)->{throw new AssertionError("Must not merge/upsert history");});
        var model=history(null,null,null,null,"COMPLETED","Alice");
        assertEquals(model,new JpaWorkflowStateHistoryRepositoryAdapter(repo,em).save(model));assertEquals(1,inserted.size());
    }
    @Test void missingOrForeignOptionalReferencesFailClosed(){
        var f=new WorkflowSemanticFixtures();var em=WorkflowSemanticFixtures.port(EntityManager.class,(name,a)->{
            if(name.equals("find") && a[0]==WorkflowInstanceJpaEntity.class)return WorkflowPersistenceMapper.toEntity(f.instance());
            if(name.equals("persist"))throw new AssertionError("Invalid history must not persist");return null;
        });
        var repo=WorkflowSemanticFixtures.port(WorkflowStateHistoryJpaRepository.class,(name,a)->null);
        var adapter=new JpaWorkflowStateHistoryRepositoryAdapter(repo,em);
        assertThrows(RuntimeException.class,()->adapter.save(history("unknown",null,null,null,"COMPLETED","Alice")));
        assertThrows(RuntimeException.class,()->adapter.save(history(null,"unknown",null,null,"COMPLETED","Alice")));
        assertThrows(RuntimeException.class,()->adapter.save(history(null,null,"unknown",null,"COMPLETED","Alice")));
        assertThrows(RuntimeException.class,()->adapter.save(history(null,null,null,"unknown","COMPLETED","Alice")));
    }
    @Test void taskSourceAndDefinitionMustBeCoherent(){
        var f=new WorkflowSemanticFixtures();var em=WorkflowSemanticFixtures.port(EntityManager.class,(name,a)->{
            if(a[0]==WorkflowInstanceJpaEntity.class)return WorkflowPersistenceMapper.toEntity(f.instance());
            if(a[0]==WorkflowTaskJpaEntity.class)return WorkflowPersistenceMapper.toEntity(f.task());
            if(a[0]==WorkflowStepJpaEntity.class)return new WorkflowStepJpaEntity((String)a[1],"other","STEP",null,"Review",null,1,true,null,null,null,true,false,false,f.at,f.at);
            throw new AssertionError("History must not persist");
        });
        var repo=WorkflowSemanticFixtures.port(WorkflowStateHistoryJpaRepository.class,(name,a)->null);
        assertThrows(RuntimeException.class,()->new JpaWorkflowStateHistoryRepositoryAdapter(repo,em).save(history("task","step",null,null,"COMPLETED","Alice")));
    }
}
