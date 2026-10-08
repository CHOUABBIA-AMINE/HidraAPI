/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NominationSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.semantic
 *
 * @Description : Enforces owner-controlled Nomination reference integrity.
 *
 */
package dz.sh.hidra.modules.planning.semantic;

import dz.sh.hidra.modules.planning.domain.model.Nomination;
import dz.sh.hidra.modules.planning.domain.exception.InvalidPlanningValueException;
import dz.sh.hidra.modules.planning.domain.value.NominationStatus;
import dz.sh.hidra.modules.planning.infrastructure.persistence.adapter.*;
import dz.sh.hidra.modules.planning.infrastructure.persistence.repository.*;
import dz.sh.hidra.modules.planning.infrastructure.persistence.entity.*;
import dz.sh.hidra.modules.custody.application.contract.planning.PlanningProductReferenceContract;
import dz.sh.hidra.modules.telemetry.application.contract.planning.PlanningUnitReferenceContract;
import dz.sh.hidra.modules.party.application.contract.planning.PlanningPartyReferenceContract;
import dz.sh.hidra.modules.topology.application.contract.planning.PlanningTargetTopologyReferenceContract;
import java.time.Instant;
import java.math.BigDecimal;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

class NominationSemanticRemediationTest {
    static final Instant AT=Instant.parse("2026-10-08T00:00:00Z");
    Nomination value(BigDecimal quantity,Instant end,Instant created,Instant updated,String rate,String scenario,String asset,String shipper,String snapshot) {
        return new Nomination("n","rev",scenario,"CODE","type","p",quantity,"q",null,rate,asset==null?null:"PIPELINE",asset,snapshot,
                null,null,null,shipper,snapshot,null,"NEUTRAL-CONTRACT",7,NominationStatus.DRAFT,AT,end,created,updated);
    }
    Nomination valid(){return value(BigDecimal.ONE,AT.plusSeconds(60),AT,AT,null,null,null,null,null);}
    class Fixture {
        final PlanRevisionJpaRepository revisions=mock(PlanRevisionJpaRepository.class);
        final PlanScenarioJpaRepository scenarios=mock(PlanScenarioJpaRepository.class);
        final PlanningCatalogEntryJpaRepository catalogs=mock(PlanningCatalogEntryJpaRepository.class);
        final PlanningProductReferenceContract products=mock(PlanningProductReferenceContract.class);
        final PlanningUnitReferenceContract units=mock(PlanningUnitReferenceContract.class);
        final PlanningPartyReferenceContract parties=mock(PlanningPartyReferenceContract.class);
        final PlanningTargetTopologyReferenceContract topology=mock(PlanningTargetTopologyReferenceContract.class);
        final PlanningCatalogEntryJpaEntity type=mock(PlanningCatalogEntryJpaEntity.class);
        final NominationReferenceValidation validation;
        Fixture(){
            var revision=mock(PlanRevisionJpaEntity.class);when(revision.id()).thenReturn("rev");when(revisions.findByIdForShare("rev")).thenReturn(Optional.of(revision));
            when(type.id()).thenReturn("type");when(type.catalogName()).thenReturn("NOMINATION_TYPE");when(type.active()).thenReturn(true);
            when(catalogs.findByIdForShare("type")).thenReturn(Optional.of(type));
            when(products.resolve("p")).thenReturn(Optional.of(new PlanningProductReferenceContract.Product("p","PRODUCT",true)));
            when(units.resolve("q",null)).thenReturn(Optional.of(new PlanningUnitReferenceContract.Units(new PlanningUnitReferenceContract.Unit("q","Q","u","DIM",true),null,true)));
            validation=new NominationReferenceValidation(revisions,scenarios,catalogs,products,units,parties,topology);
        }
    }
    @Test void positiveQuantityAndStrictIntervalAreIntrinsic() {
        for(var q:List.of(BigDecimal.ZERO,BigDecimal.ONE.negate()))assertThrows(InvalidPlanningValueException.class,()->value(q,AT.plusSeconds(1),AT,AT,null,null,null,null,null));
        assertThrows(InvalidPlanningValueException.class,()->value(BigDecimal.ONE,AT,AT,AT,null,null,null,null,null));
        assertThrows(InvalidPlanningValueException.class,()->value(BigDecimal.ONE,AT.minusSeconds(1),AT,AT,null,null,null,null,null));
    }
    @Test void auditTimestampsAreRequiredAndRatePairingIsNotInvented() {
        assertThrows(InvalidPlanningValueException.class,()->value(BigDecimal.ONE,AT.plusSeconds(1),null,AT,null,null,null,null,null));
        assertThrows(InvalidPlanningValueException.class,()->value(BigDecimal.ONE,AT.plusSeconds(1),AT,null,null,null,null,null,null));
        assertDoesNotThrow(()->value(BigDecimal.ONE,AT.plusSeconds(1),AT,AT,"r",null,null,null,null));
    }
    @Test void exactFamilyAndFreshEligibilityFailClosed() {
        var f=new Fixture();when(f.type.catalogName()).thenReturn("TARGET_TYPE");assertThrows(IllegalArgumentException.class,()->f.validation.validate(valid(),null));
        when(f.type.catalogName()).thenReturn("NOMINATION_TYPE");when(f.type.active()).thenReturn(false);assertThrows(IllegalArgumentException.class,()->f.validation.validate(valid(),null));
    }
    @Test void unchangedInactiveReferencesPreserveHistoricalMeaning() {
        var f=new Fixture();when(f.type.active()).thenReturn(false);
        var old=value(BigDecimal.ONE,AT.plusSeconds(60),AT,AT,null,null,"asset","party","HISTORICAL");
        var attempted=value(BigDecimal.TEN,AT.plusSeconds(60),AT,AT,null,null,"asset","party","CALLER");
        var actual=f.validation.validate(attempted,old);
        assertEquals("HISTORICAL",actual.sourceAssetCode());assertEquals("HISTORICAL",actual.shipperPartyCodeSnapshot());
        assertEquals("NEUTRAL-CONTRACT",actual.contractReferenceId());assertEquals(26,Nomination.class.getRecordComponents().length);
        verifyNoInteractions(f.products,f.units,f.parties,f.topology);
    }
    @Test void missingProductUnitsAndIncompatiblePairsAreDenied() {
        var f=new Fixture();when(f.products.resolve("p")).thenReturn(Optional.empty());assertThrows(IllegalArgumentException.class,()->f.validation.validate(valid(),null));
        var missingUnits=new Fixture();when(missingUnits.units.resolve("q",null)).thenReturn(Optional.empty());assertThrows(IllegalArgumentException.class,()->missingUnits.validation.validate(valid(),null));
        var paired=new Fixture();when(paired.units.resolve("q","r")).thenReturn(Optional.of(new PlanningUnitReferenceContract.Units(new PlanningUnitReferenceContract.Unit("q","Q","u","DIM",true),new PlanningUnitReferenceContract.Unit("r","R","v","RATE",true),false)));
        assertThrows(IllegalArgumentException.class,()->paired.validation.validate(value(BigDecimal.ONE,AT.plusSeconds(60),AT,AT,"r",null,null,null,null),null));
    }
    @Test void scenarioMustBelongToSameRevision() {
        var f=new Fixture();var s=mock(PlanScenarioJpaEntity.class);when(s.id()).thenReturn("scenario");when(s.revisionId()).thenReturn("other");
        when(f.scenarios.findByIdForShare("scenario")).thenReturn(Optional.of(s));
        assertThrows(IllegalArgumentException.class,()->f.validation.validate(value(BigDecimal.ONE,AT.plusSeconds(60),AT,AT,null,"scenario",null,null,null),null));
    }
    @Test void unknownRevisionWrongOwnerIdentityAndInactiveFreshUnitsAreDenied() {
        var f=new Fixture();when(f.revisions.findByIdForShare("rev")).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class,()->f.validation.validate(valid(),null));
        var wrong=new Fixture();when(wrong.products.resolve("p")).thenReturn(Optional.of(new PlanningProductReferenceContract.Product("other","PRODUCT",true)));
        assertThrows(IllegalArgumentException.class,()->wrong.validation.validate(valid(),null));
        var inactive=new Fixture();when(inactive.units.resolve("q",null)).thenReturn(Optional.of(new PlanningUnitReferenceContract.Units(new PlanningUnitReferenceContract.Unit("q","Q","u","DIM",false),null,true)));
        assertThrows(IllegalArgumentException.class,()->inactive.validation.validate(valid(),null));
    }
    @Test void everyStatusAndAllFieldsRetainThePersistenceMirror() {
        for(var status:NominationStatus.values()) {
            var n=new Nomination("n","rev","scenario","CODE","type","p",BigDecimal.ONE,"q",BigDecimal.TEN,"r","PIPELINE","source","S","PIPELINE","destination","D","party","P","counterparty","neutral",7,status,AT,AT.plusSeconds(60),AT,AT);
            assertEquals(n,dz.sh.hidra.modules.planning.infrastructure.persistence.mapper.PlanningPersistenceMapper.toDomain(
                    dz.sh.hidra.modules.planning.infrastructure.persistence.mapper.PlanningPersistenceMapper.toEntity(n)));
        }
    }
    @Test void freshOwnerSnapshotsReplaceCallerEvidenceAndRemovalClearsThem() {
        var f=new Fixture();when(f.parties.resolve("party")).thenReturn(Optional.of(new PlanningPartyReferenceContract.Party("party","PARTY")));
        when(f.topology.resolve("PIPELINE","asset")).thenReturn(Optional.of(new PlanningTargetTopologyReferenceContract.Asset("asset","ASSET",null)));
        var requested=value(BigDecimal.ONE,AT.plusSeconds(60),AT,AT,null,null,"asset","party","CALLER");
        var actual=f.validation.validate(requested,null);assertEquals("PARTY",actual.shipperPartyCodeSnapshot());assertEquals("ASSET",actual.sourceAssetCode());
        var cleared=f.validation.validate(valid(),actual);assertNull(cleared.sourceAssetCode());assertNull(cleared.shipperPartyCodeSnapshot());
    }
    @Test void freshMissingOptionalOwnersAreDenied() {
        var f=new Fixture();when(f.parties.resolve("party")).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class,()->f.validation.validate(value(BigDecimal.ONE,AT.plusSeconds(60),AT,AT,null,null,null,"party","CALLER"),null));
        when(f.topology.resolve("PIPELINE","asset")).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class,()->f.validation.validate(value(BigDecimal.ONE,AT.plusSeconds(60),AT,AT,null,null,"asset",null,"CALLER"),null));
    }
    @Test void adapterChecksDuplicateCodeAndFlushesValidatedSave() {
        var repo=mock(NominationJpaRepository.class);var validation=mock(NominationReferenceValidation.class);var model=valid();
        when(repo.findByIdForUpdate("n")).thenReturn(Optional.empty());when(validation.validate(model,null)).thenReturn(model);
        when(repo.existsByRevisionIdAndCodeAndIdNot("rev","CODE","n")).thenReturn(true);
        var adapter=new JpaNominationRepositoryAdapter(repo,validation);assertThrows(IllegalArgumentException.class,()->adapter.save(model));
        verify(repo,never()).saveAndFlush(any());verify(validation).validate(model,null);
    }
}
