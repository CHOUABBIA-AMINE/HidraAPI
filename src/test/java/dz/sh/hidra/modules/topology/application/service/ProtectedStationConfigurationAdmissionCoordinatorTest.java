/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProtectedStationConfigurationAdmissionCoordinatorTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-30
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Bounded station configuration transaction and rejection tests.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import dz.sh.hidra.modules.identity.application.contract.topology.TopologyStationConfigurationAuthorizationContract;
import dz.sh.hidra.modules.topology.application.port.in.StationInstalledConfigurationUseCase.*;
import dz.sh.hidra.modules.topology.application.port.out.*;
import dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor;
import java.util.*;
import java.lang.reflect.Modifier;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProtectedStationConfigurationAdmissionCoordinatorTest {
    private final TopologyStationConfigurationAuthorizationContract identity=mock(TopologyStationConfigurationAuthorizationContract.class);
    private final StationConfigurationSourceEvidencePort sources=mock(StationConfigurationSourceEvidencePort.class);
    private final StationInstalledConfigurationRepositoryPort repository=mock(StationInstalledConfigurationRepositoryPort.class);
    private final ProtectedTransactionExecutor executor=mock(ProtectedTransactionExecutor.class);
    private final ProtectedStationConfigurationAdmissionCoordinator coordinator=
        new ProtectedStationConfigurationAdmissionCoordinator(identity,sources,repository,executor);
    private Command read(){return new Command(Operation.READ_CURRENT,"company","station",null,null,0);}

    @Test void ambientTransactionRejectsBeforeNativeIdentityPreparation(){
        TransactionSynchronizationManager.setActualTransactionActive(true);
        try {
            assertThrows(RuntimeException.class,()->coordinator.prepare(read()));
            verifyNoInteractions(identity,sources,repository);
        }finally{TransactionSynchronizationManager.setActualTransactionActive(false);TransactionSynchronizationManager.clear();}
    }

    @Test void forgedPreparedTicketCannotReachProtectedSourceOrNativeGroup(){
        assertThrows(RuntimeException.class,()->coordinator.acquire(null,read()));
        verify(executor).beginAdmission();
        verify(executor).rejectAdmission();
        verifyNoInteractions(identity,sources,repository);
    }

    @Test void missingNativeAuthorizationContractCannotBeConstructed(){
        assertThrows(NullPointerException.class,()->new ProtectedStationConfigurationAdmissionCoordinator(
            null,sources,repository,executor));
    }

    @Test void noPublicAdmissionTicketConstructorOrFabricationMethod(){
        assertEquals(0,ProtectedStationConfigurationAdmissionCoordinator.Prepared.class.getConstructors().length);
        assertEquals(0,ProtectedStationConfigurationAdmissionCoordinator.Admission.class.getConstructors().length);
        for(var t:List.of(ProtectedStationConfigurationAdmissionCoordinator.Prepared.class,
                         ProtectedStationConfigurationAdmissionCoordinator.Admission.class))
            assertTrue(Arrays.stream(t.getDeclaredConstructors()).noneMatch(c->Modifier.isPublic(c.getModifiers())));
    }

    @Test void nativeScopeCannotTreatCompanyReadAsFacilityGrant(){
        var req=new TopologyStationConfigurationAuthorizationContract.Request(
            TopologyStationConfigurationAuthorizationContract.Operation.READ_CURRENT,
            "company","station",null,List.of("unit-b","unit-a"));
        var requirements=req.requirements();
        assertEquals(4,requirements.size());
        assertTrue(requirements.stream().anyMatch(r->r.scope()==TopologyStationConfigurationAuthorizationContract.Scope.COMPANY
            && r.resourceType().equals(TopologyStationConfigurationAuthorizationContract.CONFIGURATION_RESOURCE)));
        assertTrue(requirements.stream().anyMatch(r->r.scope()==TopologyStationConfigurationAuthorizationContract.Scope.FACILITY
            && r.resourceType().equals(TopologyStationConfigurationAuthorizationContract.CONFIGURATION_RESOURCE)));
        assertEquals(List.of("unit-a","unit-b"),req.referencedUnitIds());
    }

    @Test void sourceAccessWithoutPhysicalTransactionCannotReturnData(){
        assertThrows(RuntimeException.class,()->coordinator.acquire(null,read()));
        verifyNoInteractions(repository,identity,sources);
    }
}
