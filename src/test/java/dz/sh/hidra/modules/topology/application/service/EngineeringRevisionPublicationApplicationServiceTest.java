/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringRevisionPublicationApplicationServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-20
 *
 * @Type        : Class
 * @Layer       : Topology Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Verifies publication command ordering, immutable replay and commit outcome classification.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import dz.sh.hidra.modules.topology.application.port.in.EngineeringRevisionPublicationUseCase.*;
import dz.sh.hidra.modules.topology.application.port.out.EngineeringRevisionPublicationRepositoryPort;
import dz.sh.hidra.modules.topology.application.model.EngineeringRevisionPublicationFailure;
import dz.sh.hidra.modules.topology.application.model.EngineeringRevisionPublicationFailure.Reason;
import dz.sh.hidra.modules.topology.domain.model.EngineeringRevisionPublication.*;
import dz.sh.hidra.modules.topology.domain.model.EngineeringRevisionPublication;
import dz.sh.hidra.modules.identity.application.contract.topology.TopologyProtectedEngineeringDraftAuthorizationContract;
import dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor;
import java.time.Instant;
import java.util.*;
import java.util.function.Supplier;
import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

class EngineeringRevisionPublicationApplicationServiceTest {
    private final ProtectedEngineeringPublicationAdmissionCoordinator coordinator=mock(ProtectedEngineeringPublicationAdmissionCoordinator.class);
    private final EngineeringRevisionPublicationRepositoryPort repository=mock(EngineeringRevisionPublicationRepositoryPort.class);
    private final ProtectedTransactionExecutor executor=mock(ProtectedTransactionExecutor.class);
    private final EngineeringRevisionPublicationApplicationService service=new EngineeringRevisionPublicationApplicationService(coordinator,repository,executor);
    private final ProtectedEngineeringPublicationAdmissionCoordinator.Prepared prepared=mock(ProtectedEngineeringPublicationAdmissionCoordinator.Prepared.class);
    private final ProtectedEngineeringPublicationAdmissionCoordinator.Admission admission=mock(ProtectedEngineeringPublicationAdmissionCoordinator.Admission.class);
    private final EngineeringRevisionPublicationRepositoryPort.Admission ticket=mock(EngineeringRevisionPublicationRepositoryPort.Admission.class);
    private final UUID draft=UUID.randomUUID(),request=UUID.randomUUID(),revision=UUID.randomUUID();
    private final Scope scope=new Scope(UUID.randomUUID(),"company","network");
    private final Instant time=Instant.parse("2026-10-20T12:00:00Z");
    private final EngineeringRevisionPublicationRepositoryPort.Observation sample=new EngineeringRevisionPublicationRepositoryPort.Observation(
            scope,"actor",UUID.randomUUID().toString(),UUID.randomUUID().toString(),1,time,time.plusMillis(500),1,42);
    private final Result empty=new Result(null,null,List.of(),null,null,false);
    private Command publish(){return new Command(Kind.PUBLISH,"company","network",draft,request,1L,0L,null,null,null,null,null);}
    private Command exact(){return new Command(Kind.READ_EXACT,"company","network",draft,null,null,null,null,revision,null,null,null);}
    @BeforeEach void setup() {
        when(coordinator.prepare()).thenReturn(prepared);when(coordinator.acquire(eq(prepared),any())).thenReturn(admission);
        when(coordinator.collect(admission)).thenReturn(sample);when(coordinator.repository(admission)).thenReturn(ticket);
        when(repository.receipt(ticket)).thenReturn(Optional.empty());when(repository.mutationSql()).thenReturn(Set.of("reviewed"));
        when(repository.apply(ticket,sample)).thenReturn(empty);when(repository.finalGuard(eq(ticket),eq(sample),anyList())).thenReturn(true);
        doAnswer(i->{Object result=((Supplier<?>)i.getArgument(0)).get();((Runnable)i.getArgument(1)).run();((Runnable)i.getArgument(2)).run();return result;})
            .when(executor).executePublication(any(),any(),any());
    }
    @Test void requiresPublicationAuthorityBeforeAnyProtectedLookup() {
        doThrow(new org.springframework.security.access.AccessDeniedException("denied")).when(coordinator).collect(admission);
        assertEquals(Reason.DENIED,assertThrows(EngineeringRevisionPublicationFailure.class,()->service.executePublication(publish())).reason());
        verifyNoInteractions(repository);
    }
    @Test void usesOnePublisherTransactionAndOneOwnerSample() {
        assertSame(empty,service.executePublication(publish()));
        verify(executor,times(1)).executePublication(any(),any(),any());verify(coordinator,times(1)).collect(admission);
        var order=inOrder(coordinator,repository);
        order.verify(coordinator).collect(admission);order.verify(coordinator).repository(admission);
        order.verify(repository).resolve(ticket);order.verify(repository).receipt(ticket);
        order.verify(coordinator).beginProvisional(admission,Set.of("reviewed"));
        order.verify(repository).apply(ticket,sample);order.verify(coordinator).endProvisional(admission);
        order.verify(coordinator).requireCurrent(admission);order.verify(repository).flush();
        order.verify(coordinator).repository(admission);order.verify(coordinator).finalFences(admission);
        order.verify(repository).finalGuard(eq(ticket),eq(sample),anyList());
    }
    @Test void rejectsDraftUpdateAsPublicationAuthority() {
        var publicationAction=ProtectedEngineeringPublicationAdmissionCoordinator.authorityAction(Kind.PUBLISH);
        assertEquals(TopologyProtectedEngineeringDraftAuthorizationContract.Action.PUBLICATION_PUBLISH,publicationAction);
        assertNotEquals(TopologyProtectedEngineeringDraftAuthorizationContract.permissionCode(
            TopologyProtectedEngineeringDraftAuthorizationContract.Action.UPDATE),
            TopologyProtectedEngineeringDraftAuthorizationContract.permissionCode(publicationAction));
        for(Kind kind:List.of(Kind.READ_EXACT,Kind.READ_CURRENT,Kind.READ_EFFECTIVE,Kind.LIST_HISTORY,Kind.READ_REQUEST))
            assertEquals(TopologyProtectedEngineeringDraftAuthorizationContract.Action.PUBLICATION_READ,
                ProtectedEngineeringPublicationAdmissionCoordinator.authorityAction(kind));
    }
    @Test void publishesExactVersionWithoutEngineeringPromotion() {
        var metadata=Component.of(1,"HIDRA_ENGINEERING_METADATA_V1",new byte[]{1});
        var snapshot=new Snapshot(scope,draft,1,metadata,null,List.of(),List.of("GRAPH_NOT_AUTHORED",EngineeringRevisionPublication.UNQUALIFIED));
        var publication=new EngineeringRevisionPublication(revision,snapshot);
        var result=new Result(null,publication,List.of(),null,null,false);
        when(repository.apply(ticket,sample)).thenReturn(result);
        assertSame(snapshot,service.executePublication(publish()).publication().snapshot());
        assertFalse(publication.engineeringQualified());assertEquals(1,snapshot.version());assertNull(snapshot.graph());
    }
    @Test void rejectsStaleHeadSourceAndConflictingReplay() {
        doThrow(new EngineeringRevisionPublicationFailure(Reason.CONFLICT)).when(repository).resolve(ticket);
        assertEquals(Reason.CONFLICT,assertThrows(EngineeringRevisionPublicationFailure.class,()->service.executePublication(publish())).reason());
        verify(coordinator,never()).beginProvisional(any(),anySet());verify(repository,never()).apply(any(),any());
    }
    @Test void replaysOriginalOutcomeAfterFreshAuthorityWithoutHeadMutation() {
        var event=EngineeringRevisionPublication.transition(scope,null,0,null,Action.PUBLISH,revision,UUID.randomUUID(),request,"0".repeat(64),time);
        var receipt=new Receipt(scope,"actor",request,"0".repeat(64),event);
        when(repository.receipt(ticket)).thenReturn(Optional.of(receipt));
        var replay=new Result(event.head(),null,List.of(event),receipt,null,true);when(repository.apply(ticket,sample)).thenReturn(replay);
        assertSame(receipt,service.executePublication(publish()).receipt());verify(coordinator).collect(admission);
        verify(coordinator,never()).beginProvisional(any(),anySet());verify(coordinator,never()).endProvisional(any());
    }
    @Test void neverUsesListingWindowForExactRead() {
        service.executePublication(exact());verify(coordinator).acquire(prepared,exact());
        verify(repository).resolve(ticket);verify(repository,never()).receipt(any());verify(coordinator,never()).beginProvisional(any(),anySet());
        assertNull(exact().limit());assertNull(exact().cursor());
    }
    @Test void returnsOnlyAfterObservedCommitAndNeverRetriesIndeterminate() throws Exception {
        var delivered=new AtomicBoolean();
        doAnswer(i->{Object result=((Supplier<?>)i.getArgument(0)).get();((Runnable)i.getArgument(1)).run();((Runnable)i.getArgument(2)).run();delivered.set(true);return result;})
            .when(executor).executePublication(any(),any(),any());
        service.executePublication(exact());assertTrue(delivered.get());
        var constructor=ProtectedTransactionExecutor.ExecutionFailure.class.getDeclaredConstructor(ProtectedTransactionExecutor.Outcome.class,Throwable.class);
        constructor.setAccessible(true);
        var unknown=constructor.newInstance(ProtectedTransactionExecutor.Outcome.INDETERMINATE,new IllegalStateException("synthetic acknowledgement failure"));
        reset(executor);doThrow(unknown).when(executor).executePublication(any(),any(),any());
        var failure=assertThrows(EngineeringRevisionPublicationFailure.class,()->service.executePublication(publish()));
        assertEquals(Reason.INDETERMINATE,failure.reason());assertSame(unknown,failure.getCause());
        verify(executor,times(1)).executePublication(any(),any(),any());
    }
}
