/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedInstalledUnitAdmissionCoordinatorTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-28
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Implements the protected installed-unit lifecycle within its owning boundary.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;
import dz.sh.hidra.modules.identity.application.contract.topology.TopologyInstalledUnitAuthorizationContract;
import dz.sh.hidra.modules.party.application.contract.topology.*;
import dz.sh.hidra.modules.topology.application.port.in.InstalledUnitLifecycleUseCase.*;
import dz.sh.hidra.modules.topology.application.port.out.*;
import dz.sh.hidra.modules.topology.domain.model.InstalledUnit;
import dz.sh.hidra.platform.persistence.*;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import java.util.*;
import java.time.Instant;
class ProtectedInstalledUnitAdmissionCoordinatorTest {
    private final TopologyInstalledUnitAuthorizationContract identity=mock(TopologyInstalledUnitAuthorizationContract.class);
    private final TopologyInstalledUnitCompanyEvidenceContract company=mock(TopologyInstalledUnitCompanyEvidenceContract.class);
    private final TopologyInstalledUnitManufacturerEvidenceContract manufacturer=mock(TopologyInstalledUnitManufacturerEvidenceContract.class);
    private final InstalledUnitPositionOwnershipPort positions=mock(InstalledUnitPositionOwnershipPort.class);
    private final ProtectedInstalledUnitRepositoryPort storage=mock(ProtectedInstalledUnitRepositoryPort.class);
    private final ProtectedTransactionExecutor executor=mock(ProtectedTransactionExecutor.class);
    private final ProtectedInstalledUnitAdmissionCoordinator coordinator=new ProtectedInstalledUnitAdmissionCoordinator(identity,company,manufacturer,positions,storage,executor);
    private Command read(){return new Command(Operation.READ_UNIT,null,"company","unit",null,null,null,null,null,null,null,null,0);}
    @AfterEach void clear(){TransactionSynchronizationManager.clear();TransactionSynchronizationManager.setActualTransactionActive(false);}
    @Test void ambientTransactionCannotPrepareOrConsumeNativeEntry() {
        TransactionSynchronizationManager.setActualTransactionActive(true);assertThrows(RuntimeException.class,()->coordinator.prepare(read()));verifyNoInteractions(identity,company,manufacturer,storage);
    }
    @Test void forgedPreparationCannotReachAnyOwnerOrDml() {
        assertThrows(RuntimeException.class,()->coordinator.acquire(null,read()));verify(executor).rejectAdmission();verifyNoInteractions(identity,company,manufacturer,storage,positions);
    }
    @Test void publicTicketShapesContainNoConstructorOrAuthorityFactory() {
        for(var type:List.of(ProtectedInstalledUnitAdmissionCoordinator.Prepared.class,ProtectedInstalledUnitAdmissionCoordinator.Admission.class)) {
            assertEquals(0,type.getConstructors().length);for(var field:type.getDeclaredFields())assertTrue(java.lang.reflect.Modifier.isPrivate(field.getModifiers()));
        }
    }
    @Test void exactNativeOperationDescriptorsAreDerivedFromTypedCommand() throws Exception {
        var method=ProtectedInstalledUnitAdmissionCoordinator.class.getDeclaredMethod("request",Command.class);method.setAccessible(true);
        var c=new Command(Operation.RELOCATE,"request","company","unit","old","new",new Position("left","source"),new Position("right","destination"),null,Instant.EPOCH,Instant.EPOCH,"source",1);
        var actual=(TopologyInstalledUnitAuthorizationContract.Request)method.invoke(null,c);
        assertEquals(TopologyInstalledUnitAuthorizationContract.Operation.RELOCATE,actual.operation());assertEquals("unit",actual.unitId());assertEquals(6,actual.requirements().size());
    }
    @Test void invalidCrossOperationPayloadsCannotHideInCommandDigest() {
        assertThrows(IllegalArgumentException.class,()->new Command(Operation.READ_UNIT,"request","company","unit",null,null,null,null,null,null,null,null,0));
        assertThrows(IllegalArgumentException.class,()->new Command(Operation.RELOCATE,"request","company","unit","same","same",new Position("left","source"),new Position("right","destination"),null,Instant.EPOCH,Instant.EPOCH,"source",1));
        assertThrows(IllegalArgumentException.class,()->new Command(Operation.RECORD_INSTALLATION,"request","company","unit","installation",null,null,new Position("position","facility"),null,Instant.EPOCH.plusNanos(1),null,"source",0));
    }
    @Test void mutationSqlPinsAreExactAcrossCoordinatorAndTechnicalExecutor() throws Exception {
        var c=ProtectedInstalledUnitAdmissionCoordinator.class.getDeclaredField("DML_HASHES");c.setAccessible(true);
        var e=ProtectedTransactionExecutor.class.getDeclaredField("INSTALLED_DML_HASHES");e.setAccessible(true);
        assertEquals(4,((Set<?>)c.get(null)).size());assertEquals(c.get(null),e.get(null));
    }

    private record Setup(Command command,ProtectedInstalledUnitAdmissionCoordinator.Prepared prepared,ProtectedInstalledUnitRepositoryPort.Admission storageTicket) {}
    @SuppressWarnings("unchecked") private Setup registrationSetup(boolean permitted) throws Exception {
        var unit=new InstalledUnit("unit","company","kind",InstalledUnit.Evidence.unknown(),InstalledUnit.Evidence.unknown(),InstalledUnit.Evidence.unknown(),null);
        var c=new Command(Operation.REGISTER_UNIT,"request","company","unit",null,null,null,null,unit,null,null,null,0);
        when(identity.prepare()).thenReturn(new TopologyInstalledUnitAuthorizationContract.Prepared(){});
        var prepared=coordinator.prepare(c);
        var nativeTicket=new TopologyInstalledUnitAuthorizationContract.Admission(){};var companyTicket=new TopologyInstalledUnitCompanyEvidenceContract.Admission(){};
        var stored=new ProtectedInstalledUnitRepositoryPort.Admission(){};
        var req=new TopologyInstalledUnitAuthorizationContract.Request(TopologyInstalledUnitAuthorizationContract.Operation.REGISTER_UNIT,"company","unit",null,null,null,null);
        var decisions=req.requirements().stream().map(r->new TopologyInstalledUnitAuthorizationContract.ResourceDecision(r,permitted?TopologyInstalledUnitAuthorizationContract.Decision.PERMIT:TopologyInstalledUnitAuthorizationContract.Decision.DENY)).toList();
        when(identity.acquire(any(),eq(req))).thenReturn(nativeTicket);
        when(identity.contribute(nativeTicket)).thenReturn(new TopologyInstalledUnitAuthorizationContract.Contribution(req,decisions,"binding","session","actor",Instant.EPOCH,Instant.EPOCH.plusSeconds(30),1,1));
        when(company.acquire("company")).thenReturn(companyTicket);
        when(company.read(companyTicket)).thenReturn(new TopologyInstalledUnitCompanyEvidenceContract.Contribution("company","ACTIVE",1,1,1,"publisher"));
        when(storage.discover(c)).thenReturn(new ProtectedInstalledUnitRepositoryPort.Hint(null,null));when(storage.acquire(eq(c),any(),anyList())).thenReturn(stored);
        when(storage.observe(stored)).thenReturn(new ProtectedInstalledUnitRepositoryPort.Snapshot(null,null,null,List.of(),List.of(),null,null));
        var method=ProtectedInstalledUnitAdmissionCoordinator.class.getDeclaredMethod("expectedFences",boolean.class);method.setAccessible(true);
        var fences=(List<ProtectedRelationFence>)method.invoke(null,false);
        when(identity.finalFences(nativeTicket)).thenReturn(fences.stream().filter(f->f.relation().startsWith("public.hidra_identity_")).toList());
        when(company.finalFences(companyTicket)).thenReturn(fences.stream().filter(f->f.relation().startsWith("public.hidra_party_")).toList());
        when(storage.finalFences(stored)).thenReturn(fences.stream().filter(f->f.relation().startsWith("public.hidra_topology_")).toList());
        when(executor.sealAdmission()).thenReturn(mock(ProtectedTransactionExecutor.ProvisionalPermit.class));
        TransactionSynchronizationManager.setActualTransactionActive(true);TransactionSynchronizationManager.initSynchronization();
        return new Setup(c,prepared,stored);
    }
    @Test void ownerDenyCannotOpenProvisionalMutation() throws Exception {
        var f=registrationSetup(false);assertThrows(RuntimeException.class,()->coordinator.acquire(f.prepared(),f.command()));
        verify(executor,never()).beginProvisional(any(),any());verify(storage,never()).apply(any(),any(),any());verify(storage).reject(f.storageTicket());
    }
    @Test void arbitrarySqlCannotEnterEvenAfterPermittedNativeAdmission() throws Exception {
        var f=registrationSetup(true);var a=coordinator.acquire(f.prepared(),f.command());
        when(storage.mutationSql()).thenReturn(Set.of("DELETE FROM public.hidra_topology_installed_unit"));
        assertThrows(RuntimeException.class,()->coordinator.perform(a));verify(executor,never()).beginProvisional(any(),any());verify(storage,never()).apply(any(),any(),any());
    }
    @Test void preparedTicketCannotBeConsumedTwice() throws Exception {
        var f=registrationSetup(true);coordinator.acquire(f.prepared(),f.command());
        assertThrows(RuntimeException.class,()->coordinator.acquire(f.prepared(),f.command()));verify(identity,times(1)).contribute(any());verify(storage,never()).apply(any(),any(),any());
    }
}
