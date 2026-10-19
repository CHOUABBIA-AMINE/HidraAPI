/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringNetworkDraftApplicationServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-19
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Implements the bounded company-scoped engineering draft contract.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;
import java.util.*;
import java.util.function.Supplier;
import dz.sh.hidra.modules.topology.application.port.in.EngineeringNetworkDraftUseCase.*;
import dz.sh.hidra.modules.topology.application.port.out.EngineeringNetworkDraftRepositoryPort;
import dz.sh.hidra.modules.topology.domain.model.EngineeringNetworkDraft.Metadata;
import dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure;
import dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor;
import org.springframework.transaction.support.TransactionSynchronizationManager;
class EngineeringNetworkDraftApplicationServiceTest {
    private Command create(){return new Command(Kind.CREATE,"c","n",null,UUID.randomUUID(),null,new Metadata(null,null,null));}
    @Test void ambientTransactionNeverPreparesOrExecutes(){
        var c=mock(ProtectedEngineeringDraftAdmissionCoordinator.class);var r=mock(EngineeringNetworkDraftRepositoryPort.class);var e=mock(ProtectedTransactionExecutor.class);
        TransactionSynchronizationManager.setActualTransactionActive(true);
        try {assertThrows(EngineeringNetworkDraftFailure.class,()->new EngineeringNetworkDraftApplicationService(c,r,e).execute(create()));verifyNoInteractions(c,r,e);}
        finally {TransactionSynchronizationManager.setActualTransactionActive(false);}
    }
    @Test void eachAttemptPreparesFreshAuthorityWithoutAutomaticRetry(){
        var c=mock(ProtectedEngineeringDraftAdmissionCoordinator.class);var r=mock(EngineeringNetworkDraftRepositoryPort.class);var e=mock(ProtectedTransactionExecutor.class);
        doThrow(new IllegalStateException("unavailable")).when(e).executePublication(any(),any(),any());
        var service=new EngineeringNetworkDraftApplicationService(c,r,e);
        assertThrows(EngineeringNetworkDraftFailure.class,()->service.execute(create()));verify(c,times(1)).prepare();verify(e,times(1)).executePublication(any(),any(),any());
    }
    @Test void updateAndValidationRequireExplicitPositiveExpectedVersion(){
        for(Kind k:List.of(Kind.UPDATE,Kind.VALIDATE))assertThrows(IllegalArgumentException.class,()->new Command(k,"c","n",UUID.randomUUID(),null,null,new Metadata(null,null,null)));
    }
}
