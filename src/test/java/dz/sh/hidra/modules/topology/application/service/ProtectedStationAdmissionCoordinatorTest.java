/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedStationAdmissionCoordinatorTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-18
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Preserves pure owner exports and pins admission catalog checks to actual migration bodies.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.*;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import dz.sh.hidra.modules.identity.application.contract.topology.*;
import dz.sh.hidra.modules.party.application.contract.topology.*;
import dz.sh.hidra.modules.topology.application.contract.station.*;
import dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor;
import java.time.Instant;
import java.util.*;

class ProtectedStationAdmissionCoordinatorTest {
    final TopologyProtectedAuthorizationContract i=mock(TopologyProtectedAuthorizationContract.class);
    final TopologyProtectedPartyEligibilityContract p=mock(TopologyProtectedPartyEligibilityContract.class);
    final StationProtectedFacilityOwnershipContract f=mock(StationProtectedFacilityOwnershipContract.class);
    final ProtectedTransactionExecutor e=mock(ProtectedTransactionExecutor.class);
    final ProtectedStationAdmissionCoordinator c=new ProtectedStationAdmissionCoordinator(i,p,f,e);
    final TopologyScopedAccessContract.Request r=new TopologyScopedAccessContract.Request(TopologyScopedAccessContract.Operation.AUTHOR,"company","facility");
    final TopologyProtectedAuthorizationContract.Prepared prepared=mock(TopologyProtectedAuthorizationContract.Prepared.class);
    final TopologyProtectedAuthorizationContract.Admission ia=mock(TopologyProtectedAuthorizationContract.Admission.class);
    final TopologyProtectedPartyEligibilityContract.Admission pa=mock(TopologyProtectedPartyEligibilityContract.Admission.class);
    final StationProtectedFacilityOwnershipContract.Admission fa=mock(StationProtectedFacilityOwnershipContract.Admission.class);
    @BeforeEach void setup() {
        var fences=new java.util.ArrayList<dz.sh.hidra.platform.persistence.ProtectedRelationFence>();
        for(int n=0;n<24;n++)fences.add(new dz.sh.hidra.platform.persistence.ProtectedRelationFence(
            "public.identity_test_"+n,dz.sh.hidra.platform.persistence.ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE));
        when(i.finalFences(ia)).thenReturn(List.copyOf(fences));
        when(p.finalFences(pa)).thenReturn(List.of(
            new dz.sh.hidra.platform.persistence.ProtectedRelationFence("public.party_test",dz.sh.hidra.platform.persistence.ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE),
            new dz.sh.hidra.platform.persistence.ProtectedRelationFence("public.party_generation",dz.sh.hidra.platform.persistence.ProtectedRelationFence.Mode.ACCESS_SHARE)));
        when(f.finalFences(fa)).thenReturn(List.of(
            new dz.sh.hidra.platform.persistence.ProtectedRelationFence("public.facility_test",dz.sh.hidra.platform.persistence.ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE),
            new dz.sh.hidra.platform.persistence.ProtectedRelationFence("public.facility_generation",dz.sh.hidra.platform.persistence.ProtectedRelationFence.Mode.ACCESS_SHARE)));
        when(i.prepare()).thenReturn(prepared);when(i.acquire(prepared,r)).thenReturn(ia);
        when(p.acquire("company")).thenReturn(pa);when(f.acquire("facility")).thenReturn(fa);
        when(i.contribute(ia)).thenReturn(identity(TopologyProtectedAuthorizationContract.Decision.PERMIT));
        when(p.read(pa)).thenReturn(new TopologyProtectedPartyEligibilityContract.Contribution("company",1,12,34));
        when(f.read(fa)).thenReturn(new StationProtectedFacilityOwnershipContract.Contribution("facility","company",2,12,34));
    }
    @AfterEach void clear() { if(TransactionSynchronizationManager.isSynchronizationActive()) TransactionSynchronizationManager.clearSynchronization();TransactionSynchronizationManager.setActualTransactionActive(false); }
    ProtectedStationAdmissionCoordinator.Admission admit() {
        var pre=c.prepare();TransactionSynchronizationManager.setActualTransactionActive(true);TransactionSynchronizationManager.initSynchronization();return c.acquire(pre,r);
    }
    TopologyProtectedAuthorizationContract.Contribution identity(TopologyProtectedAuthorizationContract.Decision d) {
        var s=Instant.parse("2026-10-18T00:00:00Z");return new TopologyProtectedAuthorizationContract.Contribution("binding","session","user",r.operation(),TopologyProtectedAuthorizationContract.permissionCode(r.operation()),"company","facility",d,TopologyProtectedAuthorizationContract.Decision.PERMIT,d,s,s.plusSeconds(30),12,34);
    }
    @Test void acquiresIdentityPartyFacilityInOrderBeforeCollecting() {
        var a=admit();var order=inOrder(e,i,p,f);order.verify(e).beginAdmission();order.verify(i).acquire(prepared,r);order.verify(p).acquire("company");order.verify(f).acquire("facility");
        verify(i,never()).contribute(ia);var result=c.collect(a);assertEquals(2,result.facilityGeneration());order.verify(i).contribute(ia);order.verify(p).read(pa);order.verify(f).read(fa);verify(e).sealAdmission();
    }
    @Test void validatesExactRequestOwnerAndPhysicalCoordinates() {
        when(f.read(fa)).thenReturn(new StationProtectedFacilityOwnershipContract.Contribution("facility","company",1,13,34));var a=admit();assertThrows(RuntimeException.class,()->c.collect(a));verify(e).rejectAdmission();
    }
    @Test void requiresBothPairedPermitsAndLiveOwnerWitnesses() {
        when(i.contribute(ia)).thenReturn(identity(TopologyProtectedAuthorizationContract.Decision.INDETERMINATE));var a=admit();assertThrows(RuntimeException.class,()->c.collect(a));verify(e,never()).sealAdmission();
    }
    @Test void rejectsForeignReusedAndIncompleteCredentials() {
        var a=admit();var foreign=new ProtectedStationAdmissionCoordinator(i,p,f,e);assertThrows(RuntimeException.class,()->foreign.collect(a));assertThrows(RuntimeException.class,()->c.requireCurrent(a));assertThrows(RuntimeException.class,()->c.collect(a));assertThrows(RuntimeException.class,()->c.collect(null));
    }
    @Test void anyCaughtOwnerFailurePoisonsAdmission() {
        when(p.read(pa)).thenThrow(new IllegalStateException("unavailable"));var a=admit();assertThrows(RuntimeException.class,()->c.collect(a));assertThrows(RuntimeException.class,()->c.requireCurrent(a));verify(e,atLeastOnce()).rejectAdmission();
    }
    @Test void preservesOpaqueScalarOnlyBoundary() {
        for(var t:List.of(ProtectedStationAdmissionCoordinator.Prepared.class,ProtectedStationAdmissionCoordinator.Admission.class))assertEquals(0,t.getConstructors().length);
        var a=admit();var result=c.collect(a);assertFalse(result.toString().contains("company"));assertThrows(RuntimeException.class,()->c.collect(a));
    }
}
