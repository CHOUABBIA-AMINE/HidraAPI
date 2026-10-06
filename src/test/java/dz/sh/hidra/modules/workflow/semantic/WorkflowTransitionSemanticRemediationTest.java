/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : WorkflowTransitionSemanticRemediationTest
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

import dz.sh.hidra.modules.workflow.domain.model.WorkflowTransition;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDecision;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.adapter.JpaWorkflowTransitionRepositoryAdapter;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.repository.WorkflowTransitionJpaRepository;
import dz.sh.hidra.modules.workflow.infrastructure.persistence.entity.*;
import dz.sh.hidra.modules.workflow.domain.value.WorkflowDefinitionStatus;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class WorkflowTransitionSemanticRemediationTest {
    final Instant at=Instant.parse("2026-10-06T12:00:00Z");
    @Test void sameStepFailsAfterNormalization(){assertThrows(RuntimeException.class,()->new WorkflowTransition("t","d"," s ","s",WorkflowDecision.APPROVE,false,false,null,null,null,at,at));}
    @Test void unsupportedActiveConfigurationDeniedBeforeWrite(){
        var em=WorkflowSemanticFixtures.port(EntityManager.class,(name,a)->{
            if(a[0]==WorkflowDefinitionJpaEntity.class)return new WorkflowDefinitionJpaEntity("def","DEF",null,"Definition",null,"type",WorkflowDefinitionStatus.ACTIVE,1,at,at);
            return new WorkflowStepJpaEntity((String)a[1],"def","STEP",null,"Review",null,1,true,null,null,null,true,false,false,at,at);
        });
        var repository=WorkflowSemanticFixtures.port(WorkflowTransitionJpaRepository.class,(name,a)->{throw new AssertionError("Must not persist invalid configuration");});
        var adapter=new JpaWorkflowTransitionRepositoryAdapter(repository,em);
        assertThrows(RuntimeException.class,()->adapter.save(new WorkflowTransition("t","def","s1","s2",WorkflowDecision.APPROVE,false,false,"true",null,null,at,at)));
        assertThrows(RuntimeException.class,()->adapter.save(new WorkflowTransition("t","def","s1","s2",WorkflowDecision.APPROVE,false,false,null,null,"callback",at,at)));
    }
    @Test void crossDefinitionSourceAndDestinationDenied(){
        var em=WorkflowSemanticFixtures.port(EntityManager.class,(name,a)->{
            if(a[0]==WorkflowDefinitionJpaEntity.class)return new WorkflowDefinitionJpaEntity("def","DEF",null,"Definition",null,"type",WorkflowDefinitionStatus.DRAFT,1,at,at);
            return new WorkflowStepJpaEntity((String)a[1],"other","STEP",null,"Review",null,1,true,null,null,null,true,false,false,at,at);
        });
        var repository=WorkflowSemanticFixtures.port(WorkflowTransitionJpaRepository.class,(name,a)->{throw new AssertionError("Must not write");});
        assertThrows(RuntimeException.class,()->new JpaWorkflowTransitionRepositoryAdapter(repository,em).save(new WorkflowTransition("t","def","s1","s2",WorkflowDecision.APPROVE,false,false,null,null,null,at,at)));
    }
}
