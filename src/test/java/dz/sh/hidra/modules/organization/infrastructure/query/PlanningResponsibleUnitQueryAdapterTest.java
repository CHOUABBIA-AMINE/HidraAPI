/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningResponsibleUnitQueryAdapterTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.infrastructure.query
 *
 * @Description : Enforces Planning-owned semantic integrity through explicit owner boundaries.
 *
 */
package dz.sh.hidra.modules.organization.infrastructure.query;
import dz.sh.hidra.modules.organization.application.contract.workflow.WorkflowOrganizationContract;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlanningResponsibleUnitQueryAdapterTest {
    @Test void delegatesOwnerValidityWithoutInventingMembershipRequirement() {
        Instant at=Instant.parse("2026-10-07T00:00:00Z");
        var owner=new WorkflowOrganizationContract(){
            public Optional<Unit> availableUnit(String id,Instant time){assertEquals(at,time);return "caller".equals(id)?Optional.of(new Unit("canonical","Unit")):Optional.empty();}
            public boolean eligibleMember(String e,String u,Instant time){throw new AssertionError("Membership is not required");}
        };
        var adapter=new PlanningResponsibleUnitQueryAdapter(owner);
        assertEquals("canonical",adapter.availableUnit("caller",at).orElseThrow().id());assertTrue(adapter.availableUnit("expired",at).isEmpty());
    }
}
