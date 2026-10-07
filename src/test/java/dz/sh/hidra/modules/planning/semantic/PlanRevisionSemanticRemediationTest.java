/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanRevisionSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-07
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.semantic
 *
 * @Description : Enforces Planning-owned semantic integrity through explicit owner boundaries.
 *
 */
package dz.sh.hidra.modules.planning.semantic;

import dz.sh.hidra.modules.planning.domain.model.*;
import dz.sh.hidra.modules.planning.domain.value.*;
import dz.sh.hidra.modules.planning.application.port.out.*;
import dz.sh.hidra.modules.planning.application.port.in.UpdatePlanRevisionUseCase;
import dz.sh.hidra.modules.planning.application.service.PlanRevisionUpdateApplicationService;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlanRevisionSemanticRemediationTest {
    static final Instant NOW=Instant.parse("2026-10-07T00:00:00Z");
    static PlanRevision revision(int number,String base,PlanRevisionStatus status) {
        return new PlanRevision("rev","plan",number,"R1",status,null,null,base,null,null,null,null,null,NOW,NOW);
    }
    @Test void positiveNumberRequired() {
        assertThrows(dz.sh.hidra.modules.planning.domain.exception.InvalidPlanningValueException.class,()->revision(0,null,PlanRevisionStatus.DRAFT));
        assertThrows(dz.sh.hidra.modules.planning.domain.exception.InvalidPlanningValueException.class,()->revision(-1,null,PlanRevisionStatus.DRAFT));
        assertEquals(1,revision(1,null,PlanRevisionStatus.DRAFT).revisionNumber());
    }
    @Test void normalizedSelfLineageDeniedButNullAllowed() {
        assertThrows(dz.sh.hidra.modules.planning.domain.exception.InvalidPlanningValueException.class,()->revision(1," rev ",PlanRevisionStatus.DRAFT));
        assertNull(revision(1,null,PlanRevisionStatus.DRAFT).baseRevisionId());
    }
    static class Revisions implements PlanRevisionRepositoryPort {
        PlanRevision value;int saves;
        Revisions(PlanRevision value){this.value=value;}
        public Optional<PlanRevision> findById(String id){return Optional.of(value);}
        public PlanRevision save(PlanRevision value){saves++;return this.value=value;}
    }
    static class Plans implements OperationalPlanRepositoryPort {
        public Optional<OperationalPlan> findById(String id){return Optional.of(new OperationalPlan("plan","period","P",null,"Plan",null,"type",null,"PIPELINE","pipe","P1",null,null,OperationalPlanStatus.DRAFT,"rev",null,"actor",NOW,NOW));}
        public OperationalPlan save(OperationalPlan p){return p;}
    }
    @Test void approvedMetadataRejectedBeforeAnySaveOrCatalogLookup() {
        var repository=new Revisions(revision(1,null,PlanRevisionStatus.APPROVED));
        var service=new PlanRevisionUpdateApplicationService(repository,new Plans(),(id,family)->{throw new AssertionError();});
        assertThrows(RuntimeException.class,()->service.update("rev",new UpdatePlanRevisionUseCase.Command(NOW,null,"edit")));
        assertEquals(0,repository.saves);
    }
    @Test void wrongOrInactiveReasonRejectedAndValidReasonUsesExactFamily() {
        var repository=new Revisions(revision(1,null,PlanRevisionStatus.DRAFT));
        PlanningCatalogEligibilityPort denied=(id,family)->{throw new IllegalArgumentException("inactive/wrong family");};
        var service=new PlanRevisionUpdateApplicationService(repository,new Plans(),denied);
        assertThrows(IllegalArgumentException.class,()->service.update("rev",new UpdatePlanRevisionUseCase.Command(NOW,"wrong","edit")));
        assertEquals(0,repository.saves);
        var accepted=new PlanRevisionUpdateApplicationService(repository,new Plans(),(id,family)->{assertEquals("reason",id);assertEquals("REVISION_REASON",family);});
        assertEquals("reason",accepted.update("rev",new UpdatePlanRevisionUseCase.Command(NOW,"reason","edit")).changeReasonCodeId());
        assertEquals(1,repository.saves);
    }
}
