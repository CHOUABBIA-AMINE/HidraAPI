/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : HseCapaLifecycleApplicationServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Class
 * @Layer       : Test
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.application.service
 * @Description : Authorization-before-existence, paired owner, CAS and immutable replay negative cases.
 */
package dz.sh.hidra.modules.hse.application.service;

import dz.sh.hidra.modules.hse.application.port.in.HseCapaLifecycleUseCase;
import dz.sh.hidra.modules.hse.application.port.out.*;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class HseCapaLifecycleApplicationServiceTest {
    final HseCapaAuthorityPort auth=mock(HseCapaAuthorityPort.class);
    final HseCapaFacilityPort facility=mock(HseCapaFacilityPort.class);
    final HseCapaIncidentPort incident=mock(HseCapaIncidentPort.class);
    final HseCapaDocumentsPort documents=mock(HseCapaDocumentsPort.class);
    final HseCapaWorkflowPort workflow=mock(HseCapaWorkflowPort.class);
    final HseCapaLifecycleStorePort store=mock(HseCapaLifecycleStorePort.class);
    final HseCaseRepositoryPort cases=mock(HseCaseRepositoryPort.class);
    final HseCorrectivePreventiveActionRepositoryPort capas=mock(HseCorrectivePreventiveActionRepositoryPort.class);
    final HseCapaLifecycleApplicationService handler=new HseCapaLifecycleApplicationService(
            auth,facility,incident,documents,workflow,store,cases,capas);
    @Test void deniesBeforeProtectedCaseExistenceCountsOrHistory(){
        when(auth.require(anyString(),anyString(),anyString())).thenThrow(new SecurityException("deny"));
        assertThrows(SecurityException.class,()->handler.cases("company","facility",0,25));
        assertThrows(SecurityException.class,()->handler.caseDetail("company","facility","protected-case"));
        assertThrows(SecurityException.class,()->handler.history("company","facility","protected-case",0,25));
        assertThrows(SecurityException.class, () -> handler.assessImpact("company", "facility", "protected-case",
                new HseCapaLifecycleUseCase.AssessImpact("request", 0,
                        dz.sh.hidra.modules.hse.domain.value.HseImpactDomain.OPERATIONS,
                        "type", dz.sh.hidra.modules.hse.domain.value.HseImpactSeverity.LOW, "Synthetic assessment")));
        verifyNoInteractions(store,facility,incident,documents,workflow,cases,capas);
    }
    @Test void wrongFacilityCannotResolveScopedExistence(){
        when(auth.require(anyString(),anyString(),anyString())).thenReturn(
                new HseCapaAuthorityPort.Actor("actor","Actor"));
        when(facility.active("wrong")).thenReturn(Optional.of(new HseCapaFacilityPort.Facility(
                "wrong","other-company","W","Wrong")));
        assertThrows(NoSuchElementException.class,()->handler.caseDetail("company","wrong","case"));
        verifyNoInteractions(store,incident,documents,workflow,cases,capas);
    }
    @Test void missingReviewedIncidentCannotStartEnrollment(){
        when(auth.require(anyString(),anyString(),anyString())).thenReturn(
                new HseCapaAuthorityPort.Actor("actor","Actor"));
        when(facility.active("facility")).thenReturn(Optional.of(new HseCapaFacilityPort.Facility(
                "facility","company","F","Facility")));
        when(store.created("company","facility","request")).thenReturn(Optional.empty());
        assertThrows(NoSuchElementException.class,()->handler.enroll("company","facility",
                new HseCapaLifecycleUseCase.Enroll("request","incident","case-no",
                        "Title",null,"type","severity")));
        verify(cases,never()).save(any());
        verify(store,never()).bind(any());
    }
    @Test void closedCaseCannotAdmitNewCapa(){
        when(auth.require(anyString(),anyString(),anyString())).thenReturn(
                new HseCapaAuthorityPort.Actor("actor","Actor"));
        when(facility.active("facility")).thenReturn(Optional.of(new HseCapaFacilityPort.Facility(
                "facility","company","F","Facility")));
        when(store.scope("case")).thenReturn(Optional.of(new HseCapaLifecycleStorePort.Scope(
                "case","company","facility","incident","old","digest","actor",java.time.Instant.now())));
        // A reviewed T16 Incident is a mandatory independent prerequisite.
        // The negative test must reach the closed-case invariant, not fail
        // earlier on missing authoritative Incident evidence.
        when(incident.reviewed("incident","company","facility")).thenReturn(Optional.of(
                new HseCapaIncidentPort.Source("incident","INC-GZ2","Reviewed incident",
                        "company","facility","independent-reviewer","review-1")));
        when(store.caseView("case")).thenReturn(new HseCapaLifecycleUseCase.CaseView(
                "case","company","facility","incident","NO","Title","CLOSED","actor",
                java.time.Instant.now(),"closure"));
        assertThrows(IllegalStateException.class,()->handler.propose("company","facility","case",
                new HseCapaLifecycleUseCase.Propose("new",0,"CAPA","type",
                        "Title",null,"actor")));
        verify(capas,never()).save(any());
    }
}
