/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningCreatorQueryServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.service
 *
 * @Description : Enforces Planning-owned semantic integrity through explicit owner boundaries.
 *
 */
package dz.sh.hidra.modules.identity.application.service;
import dz.sh.hidra.modules.identity.application.contract.workflow.WorkflowActorContract;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlanningCreatorQueryServiceTest {
    @Test void delegatesOwnerEligibilityAndKeepsCanonicalId() {
        Instant at=Instant.parse("2026-10-07T00:00:00Z");
        var owner=new WorkflowActorContract(){
            public Optional<Actor> eligibleActor(String id,Instant time){assertEquals(at,time);return "caller".equals(id)?Optional.of(new Actor("canonical","user","Name",null)):Optional.empty();}
            public Optional<Actor> eligibleReference(String r,Instant t){throw new AssertionError();}
            public boolean permitted(String id,String p,String i){throw new AssertionError();}
        };
        var service=new PlanningCreatorQueryService(owner);
        assertEquals("canonical",service.eligibleCreator("caller",at).orElseThrow().id());assertTrue(service.eligibleCreator("inactive",at).isEmpty());
    }
}
