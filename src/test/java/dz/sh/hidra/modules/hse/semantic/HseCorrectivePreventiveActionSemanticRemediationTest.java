/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseCorrectivePreventiveActionSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.semantic
 *
 * @Description : Enforces HSE-owned lifecycle integrity through owner-controlled references.
 *
 */
package dz.sh.hidra.modules.hse.semantic;

import dz.sh.hidra.modules.hse.domain.model.HseCorrectivePreventiveAction;
import dz.sh.hidra.modules.hse.domain.value.*;
import dz.sh.hidra.modules.hse.application.port.out.*;
import dz.sh.hidra.modules.hse.application.service.HseApplicationService;
import dz.sh.hidra.modules.hse.application.command.CreateHseCapaCommand;
import dz.sh.hidra.modules.hse.infrastructure.configuration.HseCatalogFieldPolicy;
import dz.sh.hidra.modules.hse.infrastructure.persistence.adapter.HseCapaReferenceValidation;
import dz.sh.hidra.modules.hse.infrastructure.persistence.entity.HseCatalogEntryJpaEntity;
import dz.sh.hidra.modules.hse.infrastructure.persistence.repository.HseCatalogEntryJpaRepository;
import dz.sh.hidra.modules.identity.application.contract.hse.HseActorContract;
import dz.sh.hidra.modules.organization.application.contract.hse.HseOrganizationReferenceContract;
import dz.sh.hidra.modules.assets.application.contract.hse.HseWorkOrderReferenceContract;
import dz.sh.hidra.modules.workflow.application.contract.hse.HseWorkflowReferenceContract;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class HseCorrectivePreventiveActionSemanticRemediationTest {
    final HseCatalogFieldPolicy policy=mock(HseCatalogFieldPolicy.class);
    final HseCatalogEntryJpaRepository catalogs=mock(HseCatalogEntryJpaRepository.class);
    final HseActorContract actors=mock(HseActorContract.class);
    final HseOrganizationReferenceContract units=mock(HseOrganizationReferenceContract.class);
    final HseWorkOrderReferenceContract workOrders=mock(HseWorkOrderReferenceContract.class);
    final HseWorkflowReferenceContract workflows=mock(HseWorkflowReferenceContract.class);
    final HseCapaReferenceValidation validator=new HseCapaReferenceValidation(policy,catalogs,actors,units,workOrders,workflows);
    HseCorrectivePreventiveAction action(String owner,String unit,String order,String task) {
        return new HseCorrectivePreventiveAction("capa","case","CAPA","type","Title",null,owner,"supplied",unit,"supplied",null,null,false,null,null,CapaStatus.PROPOSED,order,task,Instant.now(),Instant.now());
    }
    void catalog(String family,boolean active) {
        when(policy.requiredFamily("CAPA_ACTION_TYPE",true)).thenReturn("APPROVED_OWNER_FAMILY");when(policy.requiredFamily("CAPA_ACTION_TYPE",false)).thenReturn("APPROVED_OWNER_FAMILY");
        when(catalogs.findByIdForShare("type")).thenReturn(Optional.of(new HseCatalogEntryJpaEntity("type",family,"CODE",active,0,false,Instant.now(),Instant.now())));
    }
    @Test void freshWrongFamilyInactiveAndMissingMappingRejectWrites() {
        catalog("OTHER",true);assertThrows(IllegalArgumentException.class,() -> validator.validate(action(null,null,null,null),null));
        catalog("APPROVED_OWNER_FAMILY",false);assertThrows(IllegalArgumentException.class,() -> validator.validate(action(null,null,null,null),null));
        when(policy.requiredFamily("CAPA_ACTION_TYPE",true)).thenThrow(new IllegalArgumentException("mapping absent"));assertThrows(IllegalArgumentException.class,() -> validator.validate(action(null,null,null,null),null));
    }
    @Test void unchangedInactiveHistoryIsNotRefreshed() {
        catalog("APPROVED_OWNER_FAMILY",false);var old=action("historical","historical",null,null);assertEquals(old,validator.validate(old,old));verify(actors,never()).eligibleActor(any(),any());verify(units,never()).resolve(any());
    }
    @Test void populatedOwnerUnitAndWorkOrderUseOwnersAndCanonicalSnapshots() {
        catalog("APPROVED_OWNER_FAMILY",true);when(actors.eligibleActor(eq("actor"),any())).thenReturn(Optional.of(new HseActorContract.Actor("actor","Canonical Actor")));
        when(units.resolve("unit")).thenReturn(Optional.of(new HseOrganizationReferenceContract.Unit("unit","UNIT","Canonical Unit")));when(workOrders.exists("order")).thenReturn(true);
        var value=validator.validate(action("actor","unit","order",null),null);assertEquals("Canonical Actor",value.ownerDisplayNameSnapshot());assertEquals("Canonical Unit",value.ownerOrganizationUnitNameSnapshot());
        when(workOrders.exists("order")).thenReturn(false);assertThrows(IllegalArgumentException.class,() -> validator.validate(action("actor","unit","order",null),null));
    }
    @Test void unrelatedWorkflowTaskCannotBeUsedAsApprovalOrContext() {
        catalog("APPROVED_OWNER_FAMILY",true);assertThrows(IllegalArgumentException.class,() -> validator.validate(action(null,null,null,"task"),null));
        when(workflows.taskMatches("task","case","capa")).thenReturn(true);assertDoesNotThrow(() -> validator.validate(action(null,null,null,"task"),null));
    }
    @Test void missingParentStopsApplicationBeforeCapaRepository() {
        var cases=mock(HseCaseRepositoryPort.class);var capAs=mock(HseCorrectivePreventiveActionRepositoryPort.class);when(cases.findByIdForUpdate("missing")).thenReturn(Optional.empty());
        var service=new HseApplicationService(cases,capAs,mock(HseClosureLifecyclePort.class));
        assertThrows(IllegalArgumentException.class,() -> service.createHseCapa(new CreateHseCapaCommand("missing","CAPA","type","Title",null,null,null,null,null,null,false,null,null)));
        verify(capAs,never()).save(any());
    }
    @Test void currentPolicyDoesNotInventClosedParentExclusion() {
        var cases=mock(HseCaseRepositoryPort.class);var capAs=mock(HseCorrectivePreventiveActionRepositoryPort.class);
        when(cases.findByIdForUpdate("case")).thenReturn(Optional.of(HseCaseSemanticRemediationTest.parent(HseCaseStatus.CLOSED)));
        when(capAs.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        var service=new HseApplicationService(cases,capAs,mock(HseClosureLifecyclePort.class));
        assertDoesNotThrow(() -> service.createHseCapa(new CreateHseCapaCommand("case","CAPA","type","Title",null,null,null,null,null,null,false,null,null)));
    }
    @Test void verifierEligibilityIsCheckedWithoutInventingVerificationStateRules() {
        catalog("APPROVED_OWNER_FAMILY",true);var base=action(null,null,null,null);
        var value=new HseCorrectivePreventiveAction(base.id(),base.hseCaseId(),base.actionNumber(),base.actionTypeId(),base.title(),null,null,null,null,null,
                null,null,false,"verifier",null,CapaStatus.PROPOSED,null,null,base.createdAt(),base.updatedAt());
        assertThrows(IllegalArgumentException.class,() -> validator.validate(value,null));
        when(actors.eligibleActor(eq("verifier"),any())).thenReturn(Optional.of(new HseActorContract.Actor("verifier","Verifier")));
        assertDoesNotThrow(() -> validator.validate(value,null));
    }
    @Test void policyProviderRejectsAbsentAndAmbiguousMetadataInsteadOfGuessingFamily() {
        var em=mock(jakarta.persistence.EntityManager.class);var query=mock(jakarta.persistence.Query.class);
        when(em.createNativeQuery(anyString())).thenReturn(query);when(query.setParameter(anyString(),any())).thenReturn(query);
        when(query.getResultList()).thenReturn(java.util.List.of());var provider=new HseCatalogFieldPolicy(em);
        assertThrows(IllegalArgumentException.class,() -> provider.requiredFamily("CAPA_ACTION_TYPE"));
        when(query.getResultList()).thenAnswer(invocation -> java.util.List.of("A","B"));assertThrows(IllegalArgumentException.class,() -> provider.requiredFamily("CAPA_ACTION_TYPE"));
        when(query.getResultList()).thenAnswer(invocation -> java.util.List.of("OWNER_APPROVED_FAMILY"));assertEquals("OWNER_APPROVED_FAMILY",provider.requiredFamily("CAPA_ACTION_TYPE"));
    }
}
