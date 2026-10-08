/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanTargetSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.semantic
 *
 * @Description : Verifies accepted Batch 19 owner evidence and semantic integrity.
 *
 */
package dz.sh.hidra.modules.planning.semantic;

import dz.sh.hidra.modules.planning.domain.model.PlanTarget;
import dz.sh.hidra.modules.planning.domain.value.PlanTargetStatus;
import dz.sh.hidra.modules.planning.infrastructure.configuration.PlanningTargetValuePolicy;
import dz.sh.hidra.modules.planning.infrastructure.configuration.PlanningTargetValuePolicy.*;
import dz.sh.hidra.modules.planning.infrastructure.persistence.adapter.*;
import dz.sh.hidra.modules.planning.infrastructure.persistence.entity.*;
import dz.sh.hidra.modules.planning.infrastructure.persistence.mapper.PlanningPersistenceMapper;
import dz.sh.hidra.modules.planning.infrastructure.persistence.repository.*;
import dz.sh.hidra.modules.topology.application.contract.planning.PlanningTargetTopologyReferenceContract;
import dz.sh.hidra.modules.telemetry.application.contract.planning.PlanningTelemetryPointReferenceContract;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlanTargetSemanticRemediationTest {
    final PlanRevisionJpaRepository revisions = mock(PlanRevisionJpaRepository.class);
    final NominationJpaRepository nominations = mock(NominationJpaRepository.class);
    final PlanScenarioJpaRepository scenarios = mock(PlanScenarioJpaRepository.class);
    final PlanningCatalogEntryJpaRepository catalogs = mock(PlanningCatalogEntryJpaRepository.class);
    final PlanningTargetValuePolicy policies = mock(PlanningTargetValuePolicy.class);
    final PlanningTargetTopologyReferenceContract topology = mock(PlanningTargetTopologyReferenceContract.class);
    final PlanningTelemetryPointReferenceContract points = mock(PlanningTelemetryPointReferenceContract.class);
    final PlanTargetReferenceValidation validation = new PlanTargetReferenceValidation(
            revisions,nominations,scenarios,catalogs,policies,topology,points);
    final Instant at = Instant.parse("2026-10-08T00:00:00Z");

    @BeforeEach void setup() {
        var revision = mock(PlanRevisionJpaEntity.class);
        when(revision.id()).thenReturn("revision");
        when(revisions.findByIdForShare("revision")).thenReturn(Optional.of(revision));
        catalog("TARGET_TYPE",true);
        policy(Representation.NUMERIC,true);
        when(topology.resolve("PIPELINE","asset")).thenReturn(Optional.of(
                new PlanningTargetTopologyReferenceContract.Asset("asset","OWNER","Owner name")));
        when(points.resolve("point")).thenReturn(Optional.of(new PlanningTelemetryPointReferenceContract.Point("point","POINT")));
    }
    void catalog(String family,boolean active) {
        when(catalogs.findByIdForShare("type")).thenReturn(Optional.of(
                new PlanningCatalogEntryJpaEntity("type",family,"OWNER_TYPE",active,0,false,at,at)));
    }
    void policy(Representation kind,boolean active) {
        when(policies.findByIdForShare("type")).thenReturn(Optional.of(new Policy("type",kind,active)));
    }
    PlanTarget target(String assetType,String nomination,String scenario,String point,BigDecimal value,String text,String unit) {
        return new PlanTarget("target","revision",scenario,nomination,"type",assetType,"asset","CALLER","Caller name",
                point,"Caller point",value,text,unit,null,null,at,at,1,PlanTargetStatus.DRAFT,at,at);
    }
    PlanTarget numeric() {return target("PIPELINE",null,null,null,BigDecimal.ONE,null,"unit");}

    @Test void requiredNamespaceAndEqualityValidityRetainExistingBehavior() {
        assertThrows(RuntimeException.class,() -> target(" ",null,null,null,BigDecimal.ONE,null,"unit"));
        assertEquals(at,numeric().validTo());
        assertEquals(22,PlanTarget.class.getRecordComponents().length);
    }
    @Test void exactFamilyMissingPolicyAndNumericShapeFailClosed() {
        catalog("OTHER",true);
        assertThrows(IllegalArgumentException.class,() -> validation.validate(numeric(),null));
        catalog("TARGET_TYPE",true);
        when(policies.findByIdForShare("type")).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class,() -> validation.validate(numeric(),null));
        policy(Representation.NUMERIC,true);
        assertThrows(IllegalArgumentException.class,() -> validation.validate(target("PIPELINE",null,null,null,null,null,"unit"),null));
        assertThrows(IllegalArgumentException.class,() -> validation.validate(target("PIPELINE",null,null,null,BigDecimal.ONE,null," "),null));
    }
    @Test void textShapeDoesNotInventMutualExclusivity() {
        policy(Representation.TEXT,true);
        assertThrows(IllegalArgumentException.class,() -> validation.validate(numeric(),null));
        var both=target("PIPELINE",null,null,null,BigDecimal.ONE,"STATE",null);
        assertEquals("STATE",validation.validate(both,null).targetTextValue());
        assertEquals(BigDecimal.ONE,validation.validate(both,null).targetValue());
    }
    @Test void ownReferencesAreOptionalButExistenceAndRevisionMustAgree() {
        assertDoesNotThrow(() -> validation.validate(numeric(),null));
        var requested=target("PIPELINE","nomination","scenario",null,BigDecimal.ONE,null,"unit");
        assertThrows(IllegalArgumentException.class,() -> validation.validate(requested,null));
        var n=mock(NominationJpaEntity.class);when(n.id()).thenReturn("nomination");when(n.revisionId()).thenReturn("other");
        when(nominations.findByIdForShare("nomination")).thenReturn(Optional.of(n));
        assertThrows(IllegalArgumentException.class,() -> validation.validate(requested,null));
        when(n.revisionId()).thenReturn("revision");
        var s=mock(PlanScenarioJpaEntity.class);when(s.id()).thenReturn("scenario");when(s.revisionId()).thenReturn("other");
        when(scenarios.findByIdForShare("scenario")).thenReturn(Optional.of(s));
        assertThrows(IllegalArgumentException.class,() -> validation.validate(requested,null));
        when(s.revisionId()).thenReturn("revision");
        assertDoesNotThrow(() -> validation.validate(requested,null));
        when(revisions.findByIdForShare("revision")).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class,() -> validation.validate(numeric(),null));
    }
    @Test void ownersResolveFreshCodesAndRejectUnknownOrWrongIdentity() {
        var requested=target("PIPELINE",null,null,"point",BigDecimal.ONE,null,"unit");
        var saved=validation.validate(requested,null);
        assertEquals("OWNER",saved.topologyAssetCode());
        assertEquals("Owner name",saved.topologyAssetNameSnapshot());
        assertEquals("POINT",saved.telemetryPointCodeSnapshot());
        when(points.resolve("point")).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class,() -> validation.validate(requested,null));
        when(topology.resolve("PIPELINE","asset")).thenReturn(Optional.of(new PlanningTargetTopologyReferenceContract.Asset("other","OWNER",null)));
        assertThrows(IllegalArgumentException.class,() -> validation.validate(numeric(),null));
        assertThrows(IllegalArgumentException.class,() -> validation.validate(target("FACILITY",null,null,null,BigDecimal.ONE,null,"unit"),null));
    }
    @Test void unchangedInactiveHistoryRetainsSnapshotsWithoutOwnerRefresh() {
        var old=validation.validate(target("PIPELINE",null,null,"point",new BigDecimal("1.000000"),null,"unit"),null);
        clearInvocations(topology,points);
        catalog("TARGET_TYPE",false);policy(Representation.NUMERIC,false);
        var saved=validation.validate(target("PIPELINE",null,null,"point",BigDecimal.ONE,null,"unit"),old);
        assertEquals(old.topologyAssetCode(),saved.topologyAssetCode());
        assertEquals(old.telemetryPointCodeSnapshot(),saved.telemetryPointCodeSnapshot());
        verifyNoInteractions(topology,points);
        assertThrows(IllegalArgumentException.class,() -> validation.validate(numeric(),null));
        assertThrows(IllegalArgumentException.class,() -> validation.validate(target("PIPELINE",null,null,"point",BigDecimal.TEN,null,"unit"),old));
    }
    @Test void directAdapterSaveCannotBypassValidationAndUsesFlush() {
        var repository=mock(PlanTargetJpaRepository.class);
        when(repository.findByIdForUpdate("target")).thenReturn(Optional.empty());
        var adapter=new JpaPlanTargetRepositoryAdapter(repository,validation);
        when(policies.findByIdForShare("type")).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class,() -> adapter.save(numeric()));
        verify(repository,never()).saveAndFlush(any());
        policy(Representation.NUMERIC,true);
        when(repository.saveAndFlush(any())).thenAnswer(call -> call.getArgument(0));
        assertEquals("OWNER",adapter.save(numeric()).topologyAssetCode());
        verify(repository).saveAndFlush(any());
    }
}
