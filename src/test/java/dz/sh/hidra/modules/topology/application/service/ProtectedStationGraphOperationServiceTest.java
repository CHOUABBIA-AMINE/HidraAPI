/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedStationGraphOperationServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-18
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Checks fail-closed preparation, owner sequence and post-commit results.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import dz.sh.hidra.modules.identity.application.contract.topology.TopologyScopedAccessContract.Request;
import dz.sh.hidra.modules.topology.application.port.in.ProtectedStationGraphOperationUseCase;
import dz.sh.hidra.modules.topology.application.port.in.ProtectedStationGraphOperationUseCase.*;
import dz.sh.hidra.modules.topology.application.port.out.StationGraphProtectedPublicationPort;
import dz.sh.hidra.modules.topology.domain.model.*;
import dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor;
import java.time.Instant;
import java.util.*;
import java.util.function.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ProtectedStationGraphOperationServiceTest {
    ProtectedStationAdmissionCoordinator coordinator;
    StationGraphProtectedPublicationPort publication;
    ProtectedTransactionExecutor executor;
    ProtectedStationGraphOperationService service;
    ProtectedStationAdmissionCoordinator.Prepared prepared;
    ProtectedStationAdmissionCoordinator.Admission admitted;
    StationGraphProtectedPublicationPort.Admission ticket;
    final Instant sampled=Instant.parse("2026-10-18T12:00:00Z");
    final ProtectedStationAdmissionCoordinator.Contribution evidence=
      new ProtectedStationAdmissionCoordinator.Contribution(
        dz.sh.hidra.modules.identity.application.contract.topology.TopologyScopedAccessContract.Operation.READ_CURRENT,
        "company","facility",1,2,"binding","session","user",sampled,sampled.plusSeconds(1),123,456);
    @BeforeEach void setup(){
        coordinator=mock(ProtectedStationAdmissionCoordinator.class);
        publication=mock(StationGraphProtectedPublicationPort.class);
        executor=mock(ProtectedTransactionExecutor.class);
        prepared=mock(ProtectedStationAdmissionCoordinator.Prepared.class);
        admitted=mock(ProtectedStationAdmissionCoordinator.Admission.class);
        ticket=mock(StationGraphProtectedPublicationPort.Admission.class);
        service=new ProtectedStationGraphOperationService(coordinator,publication,executor);
        when(coordinator.prepare()).thenReturn(prepared);
        when(coordinator.acquire(eq(prepared),any(Request.class))).thenReturn(admitted);
        when(coordinator.collect(admitted)).thenReturn(evidence);
        when(publication.acquire("facility")).thenReturn(ticket);
        when(publication.head(ticket)).thenReturn(Optional.empty());
        when(coordinator.finalFences(admitted)).thenReturn(List.of());
        when(publication.finalGuard(eq(ticket),eq(sampled),any(Instant.class),eq("company"),eq(1L),eq(2L),isNull(),isNull(),anyList())).thenReturn(true);
        doAnswer(call->{
            @SuppressWarnings("unchecked") Supplier<Object> body=call.getArgument(0);
            Runnable flush=call.getArgument(1),guard=call.getArgument(2);
            Object r=body.get();flush.run();guard.run();return r;
        }).when(executor).executePublication(any(),any(),any());
    }
    private Command command(Kind kind){return new Command(kind,"company","facility",null,null,null,null,null,5);}
    @Test void rejectsAmbientTransactionBeforePreparation(){
        org.springframework.transaction.support.TransactionSynchronizationManager.setActualTransactionActive(true);
        try {
            assertThrows(IllegalStateException.class,()->service.execute(command(Kind.READ_CURRENT)));
            verifyNoInteractions(coordinator,publication,executor);
        } finally {
            org.springframework.transaction.support.TransactionSynchronizationManager.setActualTransactionActive(false);
        }
    }
    @Test void requiresActualOpaqueKAdmissionAndBothOwnerPermits(){
        doThrow(new IllegalStateException("DENY")).when(coordinator).requireCurrent(admitted);
        assertThrows(IllegalStateException.class,()->service.execute(command(Kind.READ_CURRENT)));
        verify(publication).acquire("facility");
        verify(publication).head(ticket);
        verify(executor,never()).beginProvisional(any(),anySet());
    }
    @Test void acquiresHeadAndRevisionFencesBeforeCommonSample(){
        service.execute(command(Kind.READ_CURRENT));
        InOrder order=inOrder(coordinator,publication);
        order.verify(coordinator).acquire(eq(prepared),any(Request.class));
        order.verify(publication).acquire("facility");
        order.verify(coordinator).collect(admitted);
        order.verify(publication).head(ticket);
    }
    @Test void rejectsTransferWithoutForgingFacilityOwnership(){
        assertThrows(IllegalStateException.class,()->service.execute(command(Kind.TRANSFER)));
        verifyNoInteractions(publication);
        verify(coordinator,never()).prepare();
    }
    @Test void neverDisclosesCurrentHistoryOrCountsBeforeAuthority(){
        when(coordinator.acquire(eq(prepared),any(Request.class))).thenThrow(new IllegalStateException("unknown"));
        for(Kind kind:List.of(Kind.READ_CURRENT,Kind.READ_HISTORY,Kind.LIST_HISTORY)){
            Command c=kind==Kind.READ_HISTORY?new Command(kind,"company","facility","r",null,null,null,null,5):command(kind);
            assertThrows(IllegalStateException.class,()->service.execute(c));
        }
        verifyNoInteractions(publication);
    }
    @Test void rejectsStaleHeadOrConsumedTicketAndConflictingEpoch(){
        when(coordinator.collect(admitted)).thenReturn(new ProtectedStationAdmissionCoordinator.Contribution(
            dz.sh.hidra.modules.identity.application.contract.topology.TopologyScopedAccessContract.Operation.PUBLISH,
            "company","facility",1,2,"binding","session","user",sampled,sampled.plusSeconds(1),123,456));
        var current=new StationGraphPublicationHead("facility","r1",3,StationGraphPublicationHead.State.ACTIVE,UUID.randomUUID());
        when(publication.head(ticket)).thenReturn(Optional.of(current));
        assertThrows(IllegalStateException.class,()->service.execute(
            new Command(Kind.PUBLISH,"company","facility","r2",0L,null,null,null,0)));
        verify(publication,never()).change(any(),any(),any(),any(),any());
    }
    @Test void materializesResultsOnlyAfterObservedPhysicalCommit(){
        var result=service.execute(command(Kind.READ_CURRENT));
        assertTrue(result.observedCommit());assertNull(result.revision());
        InOrder order=inOrder(executor,coordinator,publication);
        order.verify(executor).executePublication(any(),any(),any());
        order.verify(coordinator).acquire(eq(prepared),any(Request.class));
        order.verify(publication).acquire("facility");
        order.verify(coordinator).collect(admitted);
        order.verify(coordinator).requireCurrent(admitted);
        order.verify(publication).flush();
        order.verify(publication).finalGuard(eq(ticket),eq(sampled),any(Instant.class),
            eq("company"),eq(1L),eq(2L),isNull(),isNull(),anyList());
    }
    @Test void poisonsCaughtMutationAndGuardFailures(){
        when(publication.finalGuard(eq(ticket),eq(sampled),any(Instant.class),eq("company"),
                eq(1L),eq(2L),isNull(),isNull(),anyList())).thenReturn(false);
        assertThrows(IllegalStateException.class,()->service.execute(command(Kind.READ_CURRENT)));
    }
    @Test void exactHistoryLookupDoesNotUseTheListingWindow(){
        var graph=new StationInternalGraph("facility","old-revision",StationInternalGraph.Status.DRAFT,
            List.of(),List.of(),List.of());
        var expected=new StationInternalGraphRevision("facility","old-revision",graph,
            StationInternalGraphRevision.SourceClassification.SYNTHETIC,sampled,sampled,null,null,null,
            StationInternalGraphRevision.Qualification.DRAFT);
        when(publication.historicalRevision(ticket,"old-revision")).thenReturn(Optional.of(expected));
        var result=service.execute(new Command(Kind.READ_HISTORY,"company","facility","old-revision",null,null,null,null,5));
        assertSame(expected,result.revision());assertTrue(result.observedCommit());
        verify(publication).historicalRevision(ticket,"old-revision");
        verify(publication,never()).history(any(),anyInt());
        verify(publication,never()).revision(any(),anyString());
    }
    @Test void exactHistoryRejectsMissingLedgerEvidence(){
        when(publication.historicalRevision(ticket,"legacy-only")).thenReturn(Optional.empty());
        assertThrows(IllegalStateException.class,()->service.execute(
            new Command(Kind.READ_HISTORY,"company","facility","legacy-only",null,null,null,null,5)));
        verify(publication).historicalRevision(ticket,"legacy-only");
        verify(publication,never()).history(any(),anyInt());
        verify(publication,never()).revision(any(),anyString());
        verify(publication,never()).flush();
    }

}
