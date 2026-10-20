/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SurveyRouteProfileApplicationServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-20
 *
 * @Type        : Class
 * @Layer       : Topology Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Tests typed native survey orchestration and unretried failure outcomes.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import dz.sh.hidra.modules.topology.application.port.in.*;
import dz.sh.hidra.modules.topology.application.port.out.*;
import dz.sh.hidra.modules.topology.application.model.EngineeringNetworkDraftFailure;
import dz.sh.hidra.modules.topology.domain.model.*;
import dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor;
import java.util.*;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;
class SurveyRouteProfileApplicationServiceTest {

    private static SurveyRouteProfile profile(){return new SurveyRouteProfile("parallel-a",
        new SurveyRouteProfile.Crs("SYNTHETIC","crs",List.of(new SurveyRouteProfile.Axis("north","metre"),new SurveyRouteProfile.Axis("east","metre"))),
        null,"origin",SurveyRouteProfile.Direction.FROM_TO,"evidence",
        List.of(point("a","0.00"),point("b","10.000")),List.of(new SurveyRouteProfile.Section("s",new BigDecimal("0.0"),new BigDecimal("10.0"),"section")));}
    private static SurveyRouteProfile.Point point(String id,String chainage){return new SurveyRouteProfile.Point(id,new BigDecimal("1.000"),new BigDecimal("2.00"),new BigDecimal(chainage),null,new BigDecimal("0.01"),new BigDecimal("0.001"),null);}

    private SurveyRouteProfileUseCase.Command write(){return new SurveyRouteProfileUseCase.Command(SurveyRouteProfileUseCase.Kind.UPDATE,"c","n",UUID.randomUUID(),"parallel-a",2L,null,profile());}
    @Test void requiresTypedSurveyCommandAndMatchingBranch(){
        assertThrows(IllegalArgumentException.class,()->new SurveyRouteProfileUseCase.Command(SurveyRouteProfileUseCase.Kind.UPDATE,"c","n",UUID.randomUUID(),"other",2L,null,profile()));
        assertThrows(IllegalArgumentException.class,()->new SurveyRouteProfileUseCase.Command(SurveyRouteProfileUseCase.Kind.READ,"c","n",UUID.randomUUID(),"parallel-a",2L,null,null));
        var service=new EngineeringNetworkDraftApplicationService(mock(ProtectedEngineeringDraftAdmissionCoordinator.class),mock(EngineeringNetworkDraftRepositoryPort.class),mock(ProtectedTransactionExecutor.class));
        assertThrows(EngineeringNetworkDraftFailure.class,()->service.executeSurvey(write()));
        assertThrows(EngineeringNetworkDraftFailure.class,()->service.execute(new EngineeringNetworkDraftUseCase.Command(EngineeringNetworkDraftUseCase.Kind.SURVEY_UPDATE,"c","n",UUID.randomUUID(),null,2L,null,null,null,"parallel-a",profile())));
    }
    @Test void usesNativeReadUpdateOnOneDraftOwner(){
        var c=mock(ProtectedEngineeringDraftAdmissionCoordinator.class);var r=mock(EngineeringNetworkDraftRepositoryPort.class);var e=mock(ProtectedTransactionExecutor.class);
        var surveys=mock(SurveyRouteProfileRepositoryPort.class);var service=new EngineeringNetworkDraftApplicationService(c,r,e,mock(EngineeringGraphDraftRepositoryPort.class),mock(EngineeringGraphStationWitnessPort.class),surveys);
        var failure=new IllegalStateException("stop");doThrow(failure).when(e).executePublication(any(),any(),any());
        assertThrows(EngineeringNetworkDraftFailure.class,()->service.executeSurvey(write()));verify(surveys).validateProfile(profile());verify(c).prepare();
        org.springframework.transaction.support.TransactionSynchronizationManager.setActualTransactionActive(true);
        try{assertThrows(EngineeringNetworkDraftFailure.class,()->service.executeSurvey(write()));verify(c,times(1)).prepare();}
        finally{org.springframework.transaction.support.TransactionSynchronizationManager.setActualTransactionActive(false);}
    }
    @Test void rollsBackSurveyFailureAndRetainsCause(){
        var c=mock(ProtectedEngineeringDraftAdmissionCoordinator.class);var e=mock(ProtectedTransactionExecutor.class);var r=mock(EngineeringNetworkDraftRepositoryPort.class);
        var service=new EngineeringNetworkDraftApplicationService(c,r,e,mock(EngineeringGraphDraftRepositoryPort.class),mock(EngineeringGraphStationWitnessPort.class),mock(SurveyRouteProfileRepositoryPort.class));
        var cause=new IllegalStateException("database failure");doThrow(cause).when(e).executePublication(any(),any(),any());
        var f=assertThrows(EngineeringNetworkDraftFailure.class,()->service.executeSurvey(write()));assertSame(cause,f.getCause());assertEquals(EngineeringNetworkDraftFailure.Reason.UNAVAILABLE,f.reason());verify(e,times(1)).executePublication(any(),any(),any());
    }
    @Test void indeterminateSurveyWriteIsNotRetried(){
        var c=mock(ProtectedEngineeringDraftAdmissionCoordinator.class);var e=mock(ProtectedTransactionExecutor.class);
        var service=new EngineeringNetworkDraftApplicationService(c,mock(EngineeringNetworkDraftRepositoryPort.class),e,mock(EngineeringGraphDraftRepositoryPort.class),mock(EngineeringGraphStationWitnessPort.class),mock(SurveyRouteProfileRepositoryPort.class));
        var cause=mock(ProtectedTransactionExecutor.ExecutionFailure.class);when(cause.outcome()).thenReturn(ProtectedTransactionExecutor.Outcome.INDETERMINATE);doThrow(cause).when(e).executePublication(any(),any(),any());
        var f=assertThrows(EngineeringNetworkDraftFailure.class,()->service.executeSurvey(write()));assertEquals(EngineeringNetworkDraftFailure.Reason.INDETERMINATE,f.reason());assertSame(cause,f.getCause());verify(e,times(1)).executePublication(any(),any(),any());
    }
}
