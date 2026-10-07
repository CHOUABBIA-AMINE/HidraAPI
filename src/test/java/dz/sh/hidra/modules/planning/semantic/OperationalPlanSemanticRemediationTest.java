/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OperationalPlanSemanticRemediationTest
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
import dz.sh.hidra.modules.planning.application.command.CreateOperationalPlanCommand;
import dz.sh.hidra.modules.planning.application.service.OperationalPlanApplicationService;
import dz.sh.hidra.modules.planning.infrastructure.persistence.adapter.JpaOperationalPlanRepositoryAdapter;
import dz.sh.hidra.modules.planning.infrastructure.persistence.repository.OperationalPlanJpaRepository;
import dz.sh.hidra.modules.topology.application.contract.planning.PlanningTopologyScopeContract;
import dz.sh.hidra.modules.identity.application.contract.planning.PlanningCreatorContract;
import dz.sh.hidra.modules.organization.application.contract.planning.PlanningResponsibleUnitContract;
import dz.sh.hidra.platform.security.CurrentSecurityContext;
import dz.sh.hidra.platform.security.AuthenticatedPrincipal;
import dz.sh.hidra.kernel.domain.value.ActorId;
import java.time.Instant;
import java.util.Optional;
import dz.sh.hidra.modules.planning.infrastructure.persistence.adapter.JpaPlanningCatalogEligibilityAdapter;
import dz.sh.hidra.modules.planning.infrastructure.persistence.repository.PlanningCatalogEntryJpaRepository;
import dz.sh.hidra.modules.planning.infrastructure.persistence.entity.PlanningCatalogEntryJpaEntity;
import java.lang.reflect.Proxy;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OperationalPlanSemanticRemediationTest {
    static final Instant NOW=Instant.parse("2026-10-07T00:00:00Z");
    static class Plans implements OperationalPlanRepositoryPort {
        OperationalPlan saved;int saves;
        public Optional<OperationalPlan> findById(String id){return Optional.ofNullable(saved);}
        public OperationalPlan save(OperationalPlan value){saves++;return saved=value;}
    }
    static class Security implements CurrentSecurityContext {
        boolean authenticated=true;
        public Optional<AuthenticatedPrincipal> currentPrincipal(){return Optional.of(new AuthenticatedPrincipal(new ActorId("actor"),"Alice",authenticated));}
        public void clear(){}
    }
    static final PlanningCatalogEligibilityPort CATALOGS=(id,family)->{if(!"type".equals(id)||!"PLAN_TYPE".equals(family))throw new IllegalArgumentException("catalog");};
    static final PlanningTopologyScopeContract TOPOLOGY=(type,id)->"PIPELINE".equals(type)&&"pipe".equals(id)?Optional.of(new PlanningTopologyScopeContract.Scope("pipe","OWNER-CODE","Owner label")):Optional.empty();
    static final PlanningCreatorContract CREATORS=(id,at)->"actor".equals(id)?Optional.of(new PlanningCreatorContract.Creator(id)):Optional.empty();
    static final PlanningResponsibleUnitContract UNITS=(id,at)->"unit".equals(id)?Optional.of(new PlanningResponsibleUnitContract.Unit(id,"Owner unit")):Optional.empty();
    CreateOperationalPlanCommand command(String type,String actor,String unit,String catalog,String name) {
        return new CreateOperationalPlanCommand("period","P",null,name,null,catalog,null,type,"pipe","caller-code","caller label",unit,actor);
    }
    OperationalPlanApplicationService service(Plans plans,Security security,PlanningCreatorContract creators) {
        return new OperationalPlanApplicationService(plans,CATALOGS,TOPOLOGY,creators,UNITS,security);
    }
    @Test void supportedCreationUsesOwnerSnapshotsAndOptionalUnitRemainsOptional() {
        var plans=new Plans();var service=service(plans,new Security(),CREATORS);
        service.createOperationalPlan(command(" pipeline ","actor",null,"type","Plan"));
        assertEquals("OWNER-CODE",plans.saved.topologyScopeCode());assertEquals("Owner label",plans.saved.topologyScopeNameSnapshot());
        assertEquals("PIPELINE",plans.saved.topologyScopeType());assertNull(plans.saved.responsibleOrganizationUnitId());
        assertEquals("actor",plans.saved.createdByActorId());assertNull(plans.saved.currentRevisionId());assertNull(plans.saved.approvedRevisionId());
    }
    @Test void unsupportedOrMissingScopeRejectedBeforeSave() {
        var plans=new Plans();var service=service(plans,new Security(),CREATORS);
        for(String type:new String[]{"REGION","NETWORK","UNKNOWN",null})
            assertThrows(IllegalArgumentException.class,()->service.createOperationalPlan(command(type,"actor",null,"type","Plan")));
        assertEquals(0,plans.saves);
    }
    @Test void creatorImpersonationAnonymousOrIneligibleActorRejected() {
        var plans=new Plans();var security=new Security();var service=service(plans,security,CREATORS);
        assertThrows(SecurityException.class,()->service.createOperationalPlan(command("PIPELINE","other",null,"type","Plan")));
        security.authenticated=false;
        assertThrows(SecurityException.class,()->service.createOperationalPlan(command("PIPELINE","actor",null,"type","Plan")));
        security.authenticated=true;var denied=service(plans,security,(id,at)->Optional.empty());
        assertThrows(IllegalArgumentException.class,()->denied.createOperationalPlan(command("PIPELINE","actor",null,"type","Plan")));
        assertEquals(0,plans.saves);
    }
    @Test void requiredNameCatalogAndPopulatedUnitAreChecked() {
        var plans=new Plans();var service=service(plans,new Security(),CREATORS);
        assertThrows(RuntimeException.class,()->service.createOperationalPlan(command("PIPELINE","actor",null,"type"," ")));
        assertThrows(IllegalArgumentException.class,()->service.createOperationalPlan(command("PIPELINE","actor",null,"wrong","Plan")));
        assertThrows(IllegalArgumentException.class,()->service.createOperationalPlan(command("PIPELINE","actor","missing","type","Plan")));
        service.createOperationalPlan(command("PIPELINE","actor","unit","type","Plan"));assertEquals("unit",plans.saved.responsibleOrganizationUnitId());
    }
    OperationalPlan plan(String current,String approved) {
        return new OperationalPlan("plan","period","P",null,"Plan",null,"type",null,"PIPELINE","pipe","P",null,null,OperationalPlanStatus.DRAFT,current,approved,"actor",NOW,NOW);
    }
    @Test void authoritativeDomainRequiresScopeType() {
        assertThrows(RuntimeException.class,()->new OperationalPlan("plan","period","P",null,"Plan",null,"type",null," ","pipe","P",null,null,OperationalPlanStatus.DRAFT,null,null,"actor",NOW,NOW));
    }
    @Test void persistenceRejectsMissingAndOtherPlanPointersAndAllowsNull() {
        int[] saves={0};
        var jpa=(OperationalPlanJpaRepository)Proxy.newProxyInstance(getClass().getClassLoader(),new Class<?>[]{OperationalPlanJpaRepository.class},(proxy,m,args)->{
            if(m.getName().equals("saveAndFlush")){saves[0]++;return args[0];}throw new AssertionError(m.getName());
        });
        PlanRevisionRepositoryPort revisions=new PlanRevisionRepositoryPort(){
            public PlanRevision save(PlanRevision r){return r;}
            public Optional<PlanRevision> findById(String id){return "missing".equals(id)?Optional.empty():Optional.of(new PlanRevision(id,"other",1,"R",PlanRevisionStatus.DRAFT,null,null,null,null,null,null,null,null,NOW,NOW));}
        };
        var adapter=new JpaOperationalPlanRepositoryAdapter(jpa,CATALOGS,TOPOLOGY,CREATORS,UNITS,revisions);
        assertThrows(IllegalArgumentException.class,()->adapter.save(plan("missing",null)));
        assertThrows(IllegalArgumentException.class,()->adapter.save(plan(null,"other-revision")));
        assertEquals(0,saves[0]);adapter.save(plan(null,null));assertEquals(1,saves[0]);
    }
    @Test void persistenceDoesNotTrustCallerOwnerIds() {
        var jpa=(OperationalPlanJpaRepository)Proxy.newProxyInstance(getClass().getClassLoader(),new Class<?>[]{OperationalPlanJpaRepository.class},(p,m,a)->{throw new AssertionError("Unexpected save");});
        PlanRevisionRepositoryPort revisions=new PlanRevisionRepositoryPort(){public PlanRevision save(PlanRevision r){return r;}public Optional<PlanRevision> findById(String id){return Optional.empty();}};
        var adapter=new JpaOperationalPlanRepositoryAdapter(jpa,CATALOGS,(type,id)->Optional.empty(),CREATORS,UNITS,revisions);
        assertThrows(IllegalArgumentException.class,()->adapter.save(plan(null,null)));
        var denied=new JpaOperationalPlanRepositoryAdapter(jpa,CATALOGS,TOPOLOGY,(id,at)->Optional.empty(),UNITS,revisions);assertThrows(IllegalArgumentException.class,()->denied.save(plan(null,null)));
    }

    @Test void actualCatalogAdapterChecksBothExactFamiliesAndActiveFlag() {
        PlanningCatalogEntryJpaEntity[] entry={new PlanningCatalogEntryJpaEntity("type","PLAN_TYPE","NORMAL",true,0,false,NOW,NOW)};
        var repository=(PlanningCatalogEntryJpaRepository)Proxy.newProxyInstance(getClass().getClassLoader(),new Class<?>[]{PlanningCatalogEntryJpaRepository.class},(p,m,a)->Optional.ofNullable(entry[0]));
        var adapter=new JpaPlanningCatalogEligibilityAdapter(repository);
        adapter.requireActive("type","PLAN_TYPE");
        assertThrows(IllegalArgumentException.class,()->adapter.requireActive("type","REVISION_REASON"));
        entry[0]=new PlanningCatalogEntryJpaEntity("reason","REVISION_REASON","CHANGE",false,0,false,NOW,NOW);
        assertThrows(IllegalArgumentException.class,()->adapter.requireActive("reason","REVISION_REASON"));
        entry[0]=new PlanningCatalogEntryJpaEntity("reason","REVISION_REASON","CHANGE",true,0,false,NOW,NOW);
        adapter.requireActive("reason","REVISION_REASON");entry[0]=null;
        assertThrows(IllegalArgumentException.class,()->adapter.requireActive("missing","PLAN_TYPE"));
    }
}
