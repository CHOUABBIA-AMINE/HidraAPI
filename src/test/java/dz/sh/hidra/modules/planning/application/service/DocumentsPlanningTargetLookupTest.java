/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentsPlanningTargetLookupTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.application.service
 *
 * @Description : Enforces Documents semantic integrity through explicit owner boundaries.
 *
 */
package dz.sh.hidra.modules.planning.application.service;
import dz.sh.hidra.modules.planning.application.port.out.*;
import dz.sh.hidra.modules.planning.domain.model.PlanRevision;
import dz.sh.hidra.modules.planning.domain.value.PlanRevisionStatus;
import java.lang.reflect.Proxy;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class DocumentsPlanningTargetLookupTest {
    @Test void historicalRevisionsRemainLinkableAndMissingUnknownTypesDeny(){
        var revisions=(PlanRevisionRepositoryPort)Proxy.newProxyInstance(getClass().getClassLoader(),new Class<?>[]{PlanRevisionRepositoryPort.class},(p,m,a)->"rev".equals(a[0])?Optional.of(new PlanRevision("rev","plan",1,"R",PlanRevisionStatus.APPROVED,null,null,null,null,null,null,null,null,null,null)):Optional.empty());
        var plans=(OperationalPlanRepositoryPort)Proxy.newProxyInstance(getClass().getClassLoader(),new Class<?>[]{OperationalPlanRepositoryPort.class},(p,m,a)->Optional.empty());
        var lookup=new DocumentsPlanningTargetLookup(revisions,plans);
        assertEquals("rev",lookup.resolve("PLAN_REVISION","rev").orElseThrow().id());
        assertTrue(lookup.resolve("OPERATIONAL_PLAN","missing").isEmpty());assertTrue(lookup.resolve("UNKNOWN","rev").isEmpty());
    }
}
