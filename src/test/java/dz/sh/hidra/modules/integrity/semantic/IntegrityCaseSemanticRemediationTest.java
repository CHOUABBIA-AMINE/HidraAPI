/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityCaseSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.semantic
 *
 * @Description : Validates IntegrityCase provenance through explicit owner-controlled references.
 *
 */
package dz.sh.hidra.modules.integrity.semantic;

import dz.sh.hidra.modules.integrity.domain.model.*;
import dz.sh.hidra.modules.integrity.domain.value.*;
import dz.sh.hidra.modules.integrity.domain.exception.InvalidIntegrityValueException;
import dz.sh.hidra.modules.integrity.application.service.IntegrityApplicationService;
import dz.sh.hidra.modules.integrity.application.command.OpenIntegrityCaseCommand;
import dz.sh.hidra.modules.integrity.application.port.out.*;
import dz.sh.hidra.modules.integrity.infrastructure.configuration.IntegrityCatalogFieldPolicy;
import dz.sh.hidra.modules.integrity.infrastructure.persistence.adapter.*;
import dz.sh.hidra.modules.integrity.infrastructure.persistence.entity.*;
import dz.sh.hidra.modules.integrity.infrastructure.persistence.repository.*;
import dz.sh.hidra.modules.identity.application.contract.integrity.IntegrityCaseActorReferenceContract;
import dz.sh.hidra.modules.organization.application.contract.integrity.IntegrityOrganizationUnitReferenceContract;
import dz.sh.hidra.modules.workflow.application.contract.integrity.IntegrityCaseWorkflowReferenceContract;
import dz.sh.hidra.modules.topology.application.contract.integrity.IntegrityCaseTopologyReferenceContract;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class IntegrityCaseSemanticRemediationTest {
    static final Instant AT=Instant.parse("2026-10-08T12:00:00Z");
    final IntegrityCatalogFieldPolicy policy=mock(IntegrityCatalogFieldPolicy.class);
    final IntegrityCatalogEntryJpaRepository catalogs=mock(IntegrityCatalogEntryJpaRepository.class);
    final PipelineDefectJpaRepository defects=mock(PipelineDefectJpaRepository.class);
    final IntegrityCaseTopologyReferenceContract topology=mock(IntegrityCaseTopologyReferenceContract.class);
    final IntegrityCaseActorReferenceContract actors=mock(IntegrityCaseActorReferenceContract.class);
    final IntegrityOrganizationUnitReferenceContract units=mock(IntegrityOrganizationUnitReferenceContract.class);
    final IntegrityCaseWorkflowReferenceContract workflows=mock(IntegrityCaseWorkflowReferenceContract.class);
    final IntegrityCaseReferenceValidation validator=new IntegrityCaseReferenceValidation(policy,catalogs,defects,topology,actors,units,workflows);
    static IntegrityCase value(String defect,String actor,String unit,String workflow,String snapshot,IntegrityCaseStatus status,Instant closedAt) {
        return new IntegrityCase("case","CASE","Title",null,"type",status,null,"PIPELINE","asset",snapshot,defect,"neutral-incident","neutral-hse",unit,workflow,AT,closedAt,actor,AT,AT);
    }
    IntegrityCase value(String defect) {return value(defect,null,null,null,"untrusted",IntegrityCaseStatus.OPEN,null);}
    void validCatalog(String family,boolean active) {
        when(policy.requiredFamily("CASE_TYPE",true)).thenReturn("OWNER_APPROVED_CASE_FAMILY");when(policy.requiredFamily("CASE_TYPE",false)).thenReturn("OWNER_APPROVED_CASE_FAMILY");
        when(catalogs.findByIdForShare("type")).thenReturn(Optional.of(new IntegrityCatalogEntryJpaEntity("type",family,"TYPE",active,0,false,AT,AT)));
        when(topology.resolve("PIPELINE","asset")).thenReturn(Optional.of(new IntegrityCaseTopologyReferenceContract.Asset("asset","CanonicalCode","Canonical label")));
    }
    void valid() {validCatalog("OWNER_APPROVED_CASE_FAMILY",true);}
    @Test void nullOptionalDefectAndNeutralSourcesArePreservedWithCanonicalFreshTarget() {
        valid();var result=validator.validate(value(null),null);assertNull(result.primaryDefectId());assertEquals("CanonicalCode",result.topologyAssetCodeSnapshot());
        assertEquals("neutral-incident",result.sourceIncidentId());assertEquals("neutral-hse",result.sourceHseCaseId());verify(defects,never()).findByIdForShare(any());
    }
    @Test void missingPopulatedDefectRejectsWriteBeforeRepositorySave() {
        valid();when(defects.findByIdForShare("missing")).thenReturn(Optional.empty());assertThrows(InvalidIntegrityValueException.class,() -> validator.validate(value("missing"),null));
    }
    @Test void knownDefectDoesNotInventStatusOrTopologyEquality() {
        valid();var defect=mock(PipelineDefectJpaEntity.class);when(defect.id()).thenReturn("defect");when(defect.topologyAssetId()).thenReturn("another-asset");when(defect.status()).thenReturn(DefectStatus.CLOSED);
        when(defects.findByIdForShare("defect")).thenReturn(Optional.of(defect));assertEquals("defect",validator.validate(value("defect"),null).primaryDefectId());
    }
    @Test void wrongFamilyInactiveFreshAndMissingMappingFailClosed() {
        validCatalog("OTHER",true);assertThrows(InvalidIntegrityValueException.class,() -> validator.validate(value(null),null));
        validCatalog("OWNER_APPROVED_CASE_FAMILY",false);assertThrows(InvalidIntegrityValueException.class,() -> validator.validate(value(null),null));
        when(policy.requiredFamily("CASE_TYPE",true)).thenThrow(new InvalidIntegrityValueException("mapping absent"));assertThrows(InvalidIntegrityValueException.class,() -> validator.validate(value(null),null));
    }
    @Test void unchangedInactiveHistoryAvoidsLiveOwnerRefresh() {
        validCatalog("OWNER_APPROVED_CASE_FAMILY",false);var old=value(null,"historical-actor","historical-unit","historical-workflow","HistoricalCode",IntegrityCaseStatus.CLOSED,null);
        assertEquals(old,validator.validate(old,old));verify(topology,never()).resolve(any(),any());verify(actors,never()).eligible(any(),any());verify(units,never()).exists(any());verify(workflows,never()).matches(any(),any());
    }
    @Test void unknownAndMismatchedTypedTargetDenyReference() {
        valid();when(topology.resolve("PIPELINE","asset")).thenReturn(Optional.empty());assertThrows(InvalidIntegrityValueException.class,() -> validator.validate(value(null),null));
        when(topology.resolve("PIPELINE","asset")).thenReturn(Optional.of(new IntegrityCaseTopologyReferenceContract.Asset("other","Code",null)));assertThrows(InvalidIntegrityValueException.class,() -> validator.validate(value(null),null));
    }
    @Test void unchangedTargetSnapshotCannotBeOverwritten() {
        valid();var old=value(null,null,null,null,"HistoricalCode",IntegrityCaseStatus.OPEN,null);assertThrows(InvalidIntegrityValueException.class,() -> validator.validate(value(null),old));
    }
    @Test void populatedActorUnitAndWorkflowRequireTheirActualOwners() {
        valid();var proposed=value(null,"actor","unit","workflow","untrusted",IntegrityCaseStatus.OPEN,null);
        assertThrows(InvalidIntegrityValueException.class,() -> validator.validate(proposed,null));when(actors.eligible(eq("actor"),any())).thenReturn(true);
        assertThrows(InvalidIntegrityValueException.class,() -> validator.validate(proposed,null));when(units.exists("unit")).thenReturn(true);
        assertThrows(InvalidIntegrityValueException.class,() -> validator.validate(proposed,null));when(workflows.matches("workflow","case")).thenReturn(true);assertDoesNotThrow(() -> validator.validate(proposed,null));
    }
    @Test void existingTemporalOrderingIsEnforcedWithoutStrongerStatusCoupling() {
        assertThrows(InvalidIntegrityValueException.class,() -> value(null,null,null,null,null,IntegrityCaseStatus.OPEN,AT.minusSeconds(1)));
        assertDoesNotThrow(() -> value(null,null,null,null,null,IntegrityCaseStatus.CLOSED,null));assertDoesNotThrow(() -> value(null,null,null,null,null,IntegrityCaseStatus.OPEN,AT));
    }
    @Test void applicationRejectsMissingDefectBeforeCaseSave() {
        var cases=mock(IntegrityCaseRepositoryPort.class);var ownDefects=mock(PipelineDefectRepositoryPort.class);when(ownDefects.findById("missing")).thenReturn(Optional.empty());
        var app=new IntegrityApplicationService(mock(IntegrityProgramRepositoryPort.class),mock(IntegrityAssessmentRepositoryPort.class),cases,units,ownDefects);
        assertThrows(InvalidIntegrityValueException.class,() -> app.openIntegrityCase(command("missing")));verify(cases,never()).save(any());
    }
    @Test void applicationRetainsOptionalityAndDoesNotImposeDefectStateOrTargetRule() {
        var cases=mock(IntegrityCaseRepositoryPort.class);var ownDefects=mock(PipelineDefectRepositoryPort.class);var defect=new PipelineDefect("defect","D","defectType",null,DefectStatus.CLOSED,null,"SEGMENT","anotherAsset",null,null,null,null,null,AT,null,null,AT,AT);
        when(ownDefects.findById("defect")).thenReturn(Optional.of(defect));when(cases.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        var app=new IntegrityApplicationService(mock(IntegrityProgramRepositoryPort.class),mock(IntegrityAssessmentRepositoryPort.class),cases,units,ownDefects);
        assertDoesNotThrow(() -> app.openIntegrityCase(command("defect")));assertDoesNotThrow(() -> app.openIntegrityCase(command(null)));assertDoesNotThrow(() -> app.openIntegrityCase(command(" ")));
    }
    @Test void policyProviderRejectsAbsentAndAmbiguousMetadataInsteadOfGuessing() {
        var em=mock(jakarta.persistence.EntityManager.class);var query=mock(jakarta.persistence.Query.class);when(em.createNativeQuery(anyString())).thenReturn(query);when(query.setParameter(anyString(),any())).thenReturn(query);
        when(query.getResultList()).thenReturn(java.util.List.of());var provider=new IntegrityCatalogFieldPolicy(em);assertThrows(InvalidIntegrityValueException.class,() -> provider.requiredFamily("CASE_TYPE",true));
        when(query.getResultList()).thenAnswer(invocation -> java.util.List.of("A","B"));assertThrows(InvalidIntegrityValueException.class,() -> provider.requiredFamily("CASE_TYPE",true));
        when(query.getResultList()).thenAnswer(invocation -> java.util.List.of("OWNER_APPROVED_CASE_FAMILY"));assertEquals("OWNER_APPROVED_CASE_FAMILY",provider.requiredFamily("CASE_TYPE",true));
    }
    static OpenIntegrityCaseCommand command(String defect) {return new OpenIntegrityCaseCommand("CASE","Title",null,"type",null,"PIPELINE","asset","untrusted",defect,null,null,null,null,null);}
}
