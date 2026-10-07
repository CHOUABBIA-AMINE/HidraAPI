/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationResolverQueryServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.application.service
 *
 * @Description : Enforces Integration evidence integrity through explicit owner boundaries.
 *
 */
package dz.sh.hidra.modules.identity.application.service;
import dz.sh.hidra.modules.identity.application.contract.workflow.WorkflowActorContract;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class IntegrationResolverQueryServiceTest {
    @Test void eligibilityRemainsOwnedByIdentityAndUsesSuppliedCurrentTime(){
        var at=Instant.now();var calls=new java.util.concurrent.atomic.AtomicInteger();
        WorkflowActorContract actors=new WorkflowActorContract(){
            public Optional<Actor> eligibleActor(String id,Instant time){calls.incrementAndGet();assertEquals(at,time);return "eligible".equals(id)?Optional.of(new Actor(id,"user","Actor",null)):Optional.empty();}
            public Optional<Actor> eligibleReference(String ref,Instant time){throw new AssertionError("Resolver must use actor ID");}
            public boolean permitted(String id,String permission,String instance){throw new AssertionError("No permission taxonomy is admitted");}
        };
        var query=new IntegrationResolverQueryService(actors);
        assertFalse(query.eligibleResolver(null,at));assertFalse(query.eligibleResolver(" ",at));assertFalse(query.eligibleResolver("eligible",null));
        assertFalse(query.eligibleResolver("inactive",at));assertTrue(query.eligibleResolver(" eligible ",at));assertEquals(2,calls.get());
    }
}
