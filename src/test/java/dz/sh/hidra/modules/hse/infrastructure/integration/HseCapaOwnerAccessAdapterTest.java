/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : HseCapaOwnerAccessAdapterTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Class
 * @Layer       : Test
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.infrastructure.integration
 * @Description : Owner-only integration contract translations with missing-evidence fail-closed behavior.
 */
package dz.sh.hidra.modules.hse.infrastructure.integration;

import dz.sh.hidra.modules.identity.application.contract.hse.HseCapaScopedAuthorizationContract;
import dz.sh.hidra.modules.topology.application.contract.hse.HseFacilityOwnershipContract;
import dz.sh.hidra.modules.incident.application.contract.hse.HseIncidentSourceContract;
import dz.sh.hidra.modules.documents.application.contract.hse.HseControlledDocumentEvidenceContract;
import dz.sh.hidra.modules.workflow.application.contract.hse.HseCapaDecisionContract;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class HseCapaOwnerAccessAdapterTest {
    final HseCapaScopedAuthorizationContract auth=mock(HseCapaScopedAuthorizationContract.class);
    final HseFacilityOwnershipContract facility=mock(HseFacilityOwnershipContract.class);
    final HseIncidentSourceContract incident=mock(HseIncidentSourceContract.class);
    final HseControlledDocumentEvidenceContract docs=mock(HseControlledDocumentEvidenceContract.class);
    final HseCapaDecisionContract workflow=mock(HseCapaDecisionContract.class);
    final HseCapaOwnerAccessAdapter bridge=new HseCapaOwnerAccessAdapter(auth,facility,incident,docs,workflow);
    @Test void onlyOwnerResolvedEvidenceIsReturned(){
        when(facility.active("f")).thenReturn(Optional.of(new HseFacilityOwnershipContract.Facility(
                "f","c","F","Facility")));
        when(incident.reviewed("i","c","f")).thenReturn(Optional.of(new HseIncidentSourceContract.Source(
                "i","INC-1","Reviewed","c","f","reviewer","7")));
        when(docs.accepted("c","f","d","v")).thenReturn(Optional.of(
                new HseControlledDocumentEvidenceContract.Evidence("d","v","link",
                        "f".repeat(64),128,"c","f")));
        when(workflow.approved("a","HSE_CAPA","cap")).thenReturn(Optional.of(
                new HseCapaDecisionContract.Decision("a","inst","task","cap",
                        "reviewer","f".repeat(64))));
        assertEquals("c",bridge.active("f").orElseThrow().companyId());
        assertEquals("7",bridge.reviewed("i","c","f").orElseThrow().reviewVersion());
        assertEquals(128,bridge.accepted("c","f","d","v").orElseThrow().contentLength());
        assertEquals("reviewer",bridge.approved("a","HSE_CAPA","cap").orElseThrow().approverId());
    }
    @Test void missingOwnerReferencesNeverBecomeSyntheticSuccess(){
        assertTrue(bridge.active("f").isEmpty());
        assertTrue(bridge.reviewed("i","c","f").isEmpty());
        assertTrue(bridge.accepted("c","f","d","v").isEmpty());
        assertTrue(bridge.approved("a","HSE_CASE","case").isEmpty());
    }
    @Test void rejectedAuthorizationCannotBeSubstitutedByOtherEvidence(){
        when(auth.require("hse:capa:close","c","f")).thenThrow(new SecurityException());
        assertThrows(SecurityException.class,()->bridge.require("hse:capa:close","c","f"));
        verifyNoInteractions(facility,incident,docs,workflow);
    }
}
