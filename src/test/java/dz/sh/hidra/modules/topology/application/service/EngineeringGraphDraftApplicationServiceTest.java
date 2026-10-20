package dz.sh.hidra.modules.topology.application.service;

import dz.sh.hidra.modules.topology.application.port.in.EngineeringGraphDraftUseCase;
import dz.sh.hidra.modules.topology.application.port.out.*;
import dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure;
import dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import java.util.*;
import java.util.function.Supplier;
import dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft;
import dz.sh.hidra.modules.topology.domain.model.EngineeringNetworkDraft;
import dz.sh.hidra.modules.topology.application.port.in.EngineeringNetworkDraftUseCase;
import dz.sh.hidra.modules.topology.application.service.ProtectedEngineeringDraftAdmissionCoordinator.Contribution;
import dz.sh.hidra.modules.identity.application.contract.topology.TopologyProtectedEngineeringDraftAuthorizationContract;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

class EngineeringGraphDraftApplicationServiceTest {
    private EngineeringGraphDraftUseCase.Command read(){return new EngineeringGraphDraftUseCase.Command(
        EngineeringGraphDraftUseCase.Kind.READ,"c","n",UUID.randomUUID(),null,null,null);}
    @Test void metadataOnlyConstructorDoesNotActivateGraph(){
        var service=new EngineeringNetworkDraftApplicationService(mock(ProtectedEngineeringDraftAdmissionCoordinator.class),
            mock(EngineeringNetworkDraftRepositoryPort.class),mock(ProtectedTransactionExecutor.class));
        assertEquals(EngineeringNetworkDraftFailure.Reason.UNAVAILABLE,
            assertThrows(EngineeringNetworkDraftFailure.class,()->service.executeGraph(read())).reason());
    }
    @Test void ambientTransactionDoesNotEvenPrepareGraphAuthority(){
        var coordinator=mock(ProtectedEngineeringDraftAdmissionCoordinator.class);
        var executor=mock(ProtectedTransactionExecutor.class);
        var service=new EngineeringNetworkDraftApplicationService(coordinator,mock(EngineeringNetworkDraftRepositoryPort.class),executor,
            mock(EngineeringGraphDraftRepositoryPort.class),mock(EngineeringGraphStationWitnessPort.class));
        TransactionSynchronizationManager.setActualTransactionActive(true);
        try {assertThrows(EngineeringNetworkDraftFailure.class,()->service.executeGraph(read()));verifyNoInteractions(coordinator,executor);}
        finally {TransactionSynchronizationManager.setActualTransactionActive(false);}
    }
    @Test void transientPublicationFailureDoesNotRetryGraphOperation(){
        var coordinator=mock(ProtectedEngineeringDraftAdmissionCoordinator.class);
        var executor=mock(ProtectedTransactionExecutor.class);
        var service=new EngineeringNetworkDraftApplicationService(coordinator,mock(EngineeringNetworkDraftRepositoryPort.class),executor,
            mock(EngineeringGraphDraftRepositoryPort.class),mock(EngineeringGraphStationWitnessPort.class));
        doThrow(new IllegalStateException("unavailable")).when(executor).executePublication(any(),any(),any());
        assertThrows(EngineeringNetworkDraftFailure.class,()->service.executeGraph(read()));
        verify(coordinator,times(1)).prepare();verify(executor,times(1)).executePublication(any(),any(),any());
    }

    @Test void graphPutCopiesMetadataAndAppendsSnapshotWithinSameProtectedWork() {
        var coordinator=mock(ProtectedEngineeringDraftAdmissionCoordinator.class);
        var draftRepo=mock(EngineeringNetworkDraftRepositoryPort.class);
        var graphRepo=mock(EngineeringGraphDraftRepositoryPort.class);
        var stations=mock(EngineeringGraphStationWitnessPort.class);
        var executor=mock(ProtectedTransactionExecutor.class);
        var service=new EngineeringNetworkDraftApplicationService(coordinator,draftRepo,executor,graphRepo,stations);
        UUID id=UUID.randomUUID(),context=UUID.randomUUID();
        EngineeringGraphDraft graph=new EngineeringGraphDraft(List.of(),List.of(),List.of());
        var command=new EngineeringGraphDraftUseCase.Command(
            EngineeringGraphDraftUseCase.Kind.UPDATE,"c","n",id,1L,null,graph);
        var ticket=mock(EngineeringNetworkDraftRepositoryPort.Admission.class);
        var prepared=mock(ProtectedEngineeringDraftAdmissionCoordinator.Prepared.class);
        var admission=mock(ProtectedEngineeringDraftAdmissionCoordinator.Admission.class);
        var identityRequest=new TopologyProtectedEngineeringDraftAuthorizationContract.Request(
            TopologyProtectedEngineeringDraftAuthorizationContract.Action.UPDATE,"c","n");
        var contribution=new Contribution(identityRequest,1,"binding","session","user",
            java.time.Instant.parse("2026-10-19T12:00:00Z"),
            java.time.Instant.parse("2026-10-19T12:05:00Z"),1,42);
        var draft=new EngineeringNetworkDraft(context,"c","n",id,2,
            new EngineeringNetworkDraft.Metadata("retained",null,"evidence"));
        var base=new EngineeringNetworkDraftUseCase.Result(draft,false,List.of());
        var snapshot=new EngineeringGraphDraftRepositoryPort.Snapshot(2,"a".repeat(64),graph);
        when(coordinator.prepare()).thenReturn(prepared);
        when(coordinator.acquire(eq(prepared),any())).thenReturn(admission);
        when(coordinator.collect(admission)).thenReturn(contribution);
        when(coordinator.repository(admission)).thenReturn(ticket);
        when(draftRepo.currentGraphDraft(eq(ticket),isNull())).thenReturn(1L);
        when(draftRepo.graphMutationSql()).thenReturn(Set.of("metadata insert","graph insert"));
        when(draftRepo.apply(ticket,contribution)).thenReturn(base);
        when(graphRepo.append(id,2,graph,42)).thenReturn(snapshot);
        when(stations.requireCurrent(eq("c"),any())).thenReturn(List.of());
        when(draftRepo.finalGuard(eq(ticket),eq(contribution),anyList())).thenReturn(true);
        doAnswer(inv -> {
            Object response=((Supplier<?>)inv.getArgument(0)).get();
            ((Runnable)inv.getArgument(1)).run();
            ((Runnable)inv.getArgument(2)).run();
            return response;
        }).when(executor).executePublication(any(),any(),any());
        var result=service.executeGraph(command);
        assertEquals(2,result.version());
        assertEquals(2L,result.graphVersion());
        assertEquals(snapshot.sha256(),result.graphSha256());
        var order=inOrder(coordinator,draftRepo,graphRepo);
        order.verify(coordinator).beginProvisional(eq(admission),anySet());
        order.verify(draftRepo).apply(ticket,contribution);
        order.verify(graphRepo).append(id,2,graph,42);
        order.verify(coordinator).endProvisional(admission);
        verify(draftRepo).bindGraphProof(eq(ticket),eq(2L),eq(snapshot),eq(List.of()),eq(1L));
        verify(draftRepo).finalGuard(eq(ticket),eq(contribution),anyList());
        verify(executor,times(1)).executePublication(any(),any(),any());
    }


    @Test void historicalGraphReadDoesNotCreateAnotherMetadataOrGraphVersion() {
        var coordinator=mock(ProtectedEngineeringDraftAdmissionCoordinator.class);
        var draftRepo=mock(EngineeringNetworkDraftRepositoryPort.class);
        var graphRepo=mock(EngineeringGraphDraftRepositoryPort.class);
        var stations=mock(EngineeringGraphStationWitnessPort.class);
        var executor=mock(ProtectedTransactionExecutor.class);
        var service=new EngineeringNetworkDraftApplicationService(coordinator,draftRepo,executor,graphRepo,stations);
        UUID id=UUID.randomUUID();
        var command=new EngineeringGraphDraftUseCase.Command(EngineeringGraphDraftUseCase.Kind.READ,
            "c","n",id,null,3L,null);
        var ticket=mock(EngineeringNetworkDraftRepositoryPort.Admission.class);
        var prepared=mock(ProtectedEngineeringDraftAdmissionCoordinator.Prepared.class);
        var admission=mock(ProtectedEngineeringDraftAdmissionCoordinator.Admission.class);
        var request=new TopologyProtectedEngineeringDraftAuthorizationContract.Request(
            TopologyProtectedEngineeringDraftAuthorizationContract.Action.READ,"c","n");
        var contribution=new Contribution(request,1,"binding","session","user",
            java.time.Instant.parse("2026-10-19T12:00:00Z"),
            java.time.Instant.parse("2026-10-19T12:05:00Z"),1,42);
        var draft=new EngineeringNetworkDraft(UUID.randomUUID(),"c","n",id,4L,
            new EngineeringNetworkDraft.Metadata("retained",null,"evidence"));
        var historical=new EngineeringGraphDraft(List.of(),List.of(),List.of());
        var snapshot=new EngineeringGraphDraftRepositoryPort.Snapshot(2L,"a".repeat(64),historical);
        when(coordinator.prepare()).thenReturn(prepared);
        when(coordinator.acquire(eq(prepared),any())).thenReturn(admission);
        when(coordinator.collect(admission)).thenReturn(contribution);
        when(coordinator.repository(admission)).thenReturn(ticket);
        when(draftRepo.currentGraphDraft(ticket,3L)).thenReturn(4L);
        when(draftRepo.apply(ticket,contribution)).thenReturn(new EngineeringNetworkDraftUseCase.Result(draft,false,List.of()));
        when(graphRepo.asOf(id,3L)).thenReturn(snapshot);
        when(stations.requireCurrent(eq("c"),eq(historical))).thenReturn(List.of());
        when(draftRepo.finalGuard(eq(ticket),eq(contribution),anyList())).thenReturn(true);
        doAnswer(inv->{
            Object response=((Supplier<?>)inv.getArgument(0)).get();
            ((Runnable)inv.getArgument(1)).run();
            ((Runnable)inv.getArgument(2)).run();
            return response;
        }).when(executor).executePublication(any(),any(),any());
        var result=service.executeGraph(command);
        assertEquals(3L,result.version());
        assertEquals(2L,result.graphVersion());
        assertEquals(snapshot.sha256(),result.graphSha256());
        verify(graphRepo).asOf(id,3L);
        verify(graphRepo,never()).append(any(),anyLong(),any(),anyLong());
        verify(draftRepo,never()).graphMutationSql();
        verify(coordinator,never()).beginProvisional(any(),anySet());
        verify(coordinator,never()).endProvisional(any());
        verify(draftRepo).bindGraphProof(ticket,3L,snapshot,List.of(),4L);
        verify(draftRepo).finalGuard(eq(ticket),eq(contribution),anyList());
    }

    @Test void invalidCurrentStationWitnessCannotOpenGraphProvisionalWindow() {
        var coordinator=mock(ProtectedEngineeringDraftAdmissionCoordinator.class);
        var draftRepo=mock(EngineeringNetworkDraftRepositoryPort.class);
        var graphRepo=mock(EngineeringGraphDraftRepositoryPort.class);
        var stations=mock(EngineeringGraphStationWitnessPort.class);
        var executor=mock(ProtectedTransactionExecutor.class);
        var service=new EngineeringNetworkDraftApplicationService(coordinator,draftRepo,executor,graphRepo,stations);
        UUID id=UUID.randomUUID();
        var graph=new EngineeringGraphDraft(List.of(
            new EngineeringGraphDraft.Node("station",EngineeringGraphDraft.NodeKind.STATION,"facility")),List.of(),List.of());
        var command=new EngineeringGraphDraftUseCase.Command(EngineeringGraphDraftUseCase.Kind.UPDATE,
            "c","n",id,1L,null,graph);
        var ticket=mock(EngineeringNetworkDraftRepositoryPort.Admission.class);
        var prepared=mock(ProtectedEngineeringDraftAdmissionCoordinator.Prepared.class);
        var admission=mock(ProtectedEngineeringDraftAdmissionCoordinator.Admission.class);
        when(coordinator.prepare()).thenReturn(prepared);
        when(coordinator.acquire(eq(prepared),any())).thenReturn(admission);
        when(coordinator.collect(admission)).thenReturn(new Contribution(
            new TopologyProtectedEngineeringDraftAuthorizationContract.Request(
                TopologyProtectedEngineeringDraftAuthorizationContract.Action.UPDATE,"c","n"),
            1,"binding","session","user",java.time.Instant.parse("2026-10-19T12:00:00Z"),
            java.time.Instant.parse("2026-10-19T12:05:00Z"),1,42));
        when(coordinator.repository(admission)).thenReturn(ticket);
        when(draftRepo.currentGraphDraft(ticket,null)).thenReturn(1L);
        when(stations.requireCurrent("c",graph)).thenThrow(new IllegalStateException("station unavailable"));
        doAnswer(inv->((Supplier<?>)inv.getArgument(0)).get())
            .when(executor).executePublication(any(),any(),any());
        var exception=assertThrows(EngineeringNetworkDraftFailure.class,()->service.executeGraph(command));
        assertEquals(EngineeringNetworkDraftFailure.Reason.UNAVAILABLE,exception.reason());
        verify(coordinator,never()).beginProvisional(any(),anySet());
        verify(draftRepo,never()).apply(any(),any());
        verify(graphRepo,never()).append(any(),anyLong(),any(),anyLong());
    }


    @Test void graphAdapterCannotReplaceTheAuthoredGraphWithoutFailingTheProtectedWork() {
        var coordinator=mock(ProtectedEngineeringDraftAdmissionCoordinator.class);
        var draftRepo=mock(EngineeringNetworkDraftRepositoryPort.class);
        var graphRepo=mock(EngineeringGraphDraftRepositoryPort.class);
        var stations=mock(EngineeringGraphStationWitnessPort.class);
        var executor=mock(ProtectedTransactionExecutor.class);
        var service=new EngineeringNetworkDraftApplicationService(coordinator,draftRepo,executor,graphRepo,stations);
        UUID id=UUID.randomUUID();
        var requested=new EngineeringGraphDraft(List.of(),List.of(),List.of());
        var forged=new EngineeringGraphDraft(List.of(
            new EngineeringGraphDraft.Node("forged",EngineeringGraphDraft.NodeKind.JUNCTION,null)),
            List.of(),List.of());
        var command=new EngineeringGraphDraftUseCase.Command(EngineeringGraphDraftUseCase.Kind.UPDATE,
            "c","n",id,1L,null,requested);
        var ticket=mock(EngineeringNetworkDraftRepositoryPort.Admission.class);
        var prepared=mock(ProtectedEngineeringDraftAdmissionCoordinator.Prepared.class);
        var admission=mock(ProtectedEngineeringDraftAdmissionCoordinator.Admission.class);
        var contribution=new Contribution(new TopologyProtectedEngineeringDraftAuthorizationContract.Request(
            TopologyProtectedEngineeringDraftAuthorizationContract.Action.UPDATE,"c","n"),
            1,"binding","session","user",java.time.Instant.parse("2026-10-19T12:00:00Z"),
            java.time.Instant.parse("2026-10-19T12:05:00Z"),1,42);
        when(coordinator.prepare()).thenReturn(prepared);
        when(coordinator.acquire(eq(prepared),any())).thenReturn(admission);
        when(coordinator.collect(admission)).thenReturn(contribution);
        when(coordinator.repository(admission)).thenReturn(ticket);
        when(draftRepo.currentGraphDraft(ticket,null)).thenReturn(1L);
        when(draftRepo.apply(ticket,contribution)).thenReturn(new EngineeringNetworkDraftUseCase.Result(
            new EngineeringNetworkDraft(UUID.randomUUID(),"c","n",id,2,
                new EngineeringNetworkDraft.Metadata("retained",null,null)),false,List.of()));
        when(graphRepo.append(id,2,requested,42)).thenReturn(
            new EngineeringGraphDraftRepositoryPort.Snapshot(2,"a".repeat(64),forged));
        when(stations.requireCurrent("c",requested)).thenReturn(List.of());
        doAnswer(inv->((Supplier<?>)inv.getArgument(0)).get())
            .when(executor).executePublication(any(),any(),any());
        var error=assertThrows(EngineeringNetworkDraftFailure.class,()->service.executeGraph(command));
        assertEquals(EngineeringNetworkDraftFailure.Reason.UNAVAILABLE,error.reason());
        verify(graphRepo).append(id,2,requested,42);
        verify(coordinator,never()).endProvisional(admission);
        verify(draftRepo,never()).bindGraphProof(any(),anyLong(),any(),anyList(),anyLong());
    }
}
