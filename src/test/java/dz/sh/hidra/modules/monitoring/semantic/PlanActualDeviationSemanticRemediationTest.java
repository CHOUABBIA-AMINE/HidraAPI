/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanActualDeviationSemanticRemediationTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.semantic
 *
 * @Description : Verifies accepted deviation owner evidence and preserved Monitoring behavior.
 *
 */
package dz.sh.hidra.modules.monitoring.semantic;

import dz.sh.hidra.modules.monitoring.application.command.RecordDeviationCommand;
import dz.sh.hidra.modules.monitoring.application.port.out.PlanActualDeviationRepositoryPort;
import dz.sh.hidra.modules.monitoring.application.service.DeviationApplicationService;
import dz.sh.hidra.modules.monitoring.domain.model.PlanActualDeviation;
import dz.sh.hidra.modules.monitoring.domain.service.DeviationSeverityClassifier;
import dz.sh.hidra.modules.monitoring.domain.value.*;
import dz.sh.hidra.modules.monitoring.infrastructure.persistence.adapter.*;
import dz.sh.hidra.modules.monitoring.infrastructure.persistence.entity.MonitoringEvaluationJpaEntity;
import dz.sh.hidra.modules.monitoring.infrastructure.persistence.repository.*;
import dz.sh.hidra.modules.planning.application.contract.monitoring.MonitoringPlanTargetReferenceContract;
import dz.sh.hidra.modules.telemetry.application.contract.monitoring.MonitoringTelemetryPointReferenceContract;
import dz.sh.hidra.modules.telemetry.application.contract.monitoring.MonitoringTrustedReadingReferenceContract;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlanActualDeviationSemanticRemediationTest {
    final MonitoringPlanTargetReferenceContract targets=mock(MonitoringPlanTargetReferenceContract.class);
    final MonitoringTelemetryPointReferenceContract points=mock(MonitoringTelemetryPointReferenceContract.class);
    final MonitoringTrustedReadingReferenceContract readings=mock(MonitoringTrustedReadingReferenceContract.class);
    final MonitoringEvaluationJpaRepository evaluations=mock(MonitoringEvaluationJpaRepository.class);
    final PlanActualDeviationReferenceValidation validation=new PlanActualDeviationReferenceValidation(targets,points,readings,evaluations);
    final Instant at=Instant.parse("2026-10-08T00:00:00Z");
    @BeforeEach void setup(){
        when(targets.resolve("target")).thenReturn(Optional.of(new MonitoringPlanTargetReferenceContract.Target(
                "target","revision","PIPELINE","asset","point","CANCELLED",null,null,null,at,at)));
        when(points.exists("point")).thenReturn(true);
        when(readings.resolve("reading")).thenReturn(Optional.of(new MonitoringTrustedReadingReferenceContract.Reading("reading","point","LOW")));
    }
    PlanActualDeviation requested(String evaluation,String reading,String point) {
        return new PlanActualDeviation("deviation",evaluation,"target","neutral-expected-state",reading,point,"PIPELINE","asset",
                "CALLER",null,null,null,null,null,DeviationSeverity.INFO,DeviationStatus.OPEN,at,null,null,null);
    }
    MonitoringEvaluationJpaEntity evaluation(){
        var e=mock(MonitoringEvaluationJpaEntity.class);when(e.id()).thenReturn("evaluation");
        when(e.planRevisionId()).thenReturn("revision");when(e.topologyAssetType()).thenReturn("PIPELINE");
        when(e.topologyAssetId()).thenReturn("asset");when(e.telemetryPointId()).thenReturn("point");
        when(evaluations.findByIdForShare("evaluation")).thenReturn(Optional.of(e));return e;
    }
    @Test void unknownMandatoryTargetFailsAndOptionalReferencesStayNullable(){
        when(targets.resolve("target")).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class,() -> validation.validate(requested(null,null,null),null));
        setup();assertDoesNotThrow(() -> validation.validate(requested(null,null,null),null));
        assertEquals(20,PlanActualDeviation.class.getRecordComponents().length);
    }
    @Test void unknownPointReadingAndReadingPointMismatchFail(){
        when(points.exists("point")).thenReturn(false);
        assertThrows(IllegalArgumentException.class,() -> validation.validate(requested(null,null,"point"),null));
        when(points.exists("point")).thenReturn(true);when(readings.resolve("reading")).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class,() -> validation.validate(requested(null,"reading",null),null));
        when(readings.resolve("reading")).thenReturn(Optional.of(new MonitoringTrustedReadingReferenceContract.Reading("reading","other","HIGH")));
        assertThrows(IllegalArgumentException.class,() -> validation.validate(requested(null,"reading","point"),null));
        setup();assertDoesNotThrow(() -> validation.validate(requested(null,"reading","point"),null));
    }
    @Test void evaluationMustExistAndEachPopulatedContextMustAgree(){
        var request=requested("evaluation","reading","point");
        assertThrows(IllegalArgumentException.class,() -> validation.validate(request,null));
        var e=evaluation();assertDoesNotThrow(() -> validation.validate(request,null));
        when(e.planRevisionId()).thenReturn("wrong");assertThrows(IllegalArgumentException.class,() -> validation.validate(request,null));
        when(e.planRevisionId()).thenReturn("revision");when(e.topologyAssetType()).thenReturn("FACILITY");
        assertThrows(IllegalArgumentException.class,() -> validation.validate(request,null));
        when(e.topologyAssetType()).thenReturn("PIPELINE");when(e.topologyAssetId()).thenReturn("wrong");
        assertThrows(IllegalArgumentException.class,() -> validation.validate(request,null));
        when(e.topologyAssetId()).thenReturn("asset");when(e.telemetryPointId()).thenReturn("wrong");
        assertThrows(IllegalArgumentException.class,() -> validation.validate(request,null));
        when(e.telemetryPointId()).thenReturn(null);when(e.planRevisionId()).thenReturn(null);
        when(e.topologyAssetType()).thenReturn(null);when(e.topologyAssetId()).thenReturn(null);
        assertDoesNotThrow(() -> validation.validate(request,null));
    }
    @Test void historicalOwnerEvidenceAndTopologySnapshotAreNotRefreshed(){
        var old=requested(null,"reading","point");clearInvocations(readings,points);
        var saved=validation.validate(old,old);assertEquals(old.topologyAssetCode(),saved.topologyAssetCode());
        verifyNoInteractions(readings,points);
        when(points.exists("other")).thenReturn(true);
        assertThrows(IllegalArgumentException.class,() -> validation.validate(requested(null,"reading","other"),old));
    }
    @Test void directRepositorySavesValidateBeforeFlush(){
        var repository=mock(PlanActualDeviationJpaRepository.class);when(repository.findByIdForUpdate("deviation")).thenReturn(Optional.empty());
        var adapter=new JpaPlanActualDeviationRepositoryAdapter(repository,validation);
        when(targets.resolve("target")).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class,() -> adapter.save(requested(null,null,null)));
        verify(repository,never()).saveAndFlush(any());
        setup();when(repository.saveAndFlush(any())).thenAnswer(call -> call.getArgument(0));
        assertEquals("neutral-expected-state",adapter.save(requested(null,null,null)).expectedFlowStateId());
        verify(repository).saveAndFlush(any());
    }
    RecordDeviationCommand command(BigDecimal difference,DeviationSeverity severity){
        return new RecordDeviationCommand(null,"target",null,null,null,"PIPELINE","asset","CODE",null,null,null,difference,null,severity,null,null);
    }
    @Test void liveRecordValidatesBeforeSaveAndRetainsSeverityFallback(){
        var repository=mock(PlanActualDeviationRepositoryPort.class);
        when(repository.save(any())).thenAnswer(call -> call.getArgument(0));
        var service=new DeviationApplicationService(repository,new DeviationSeverityClassifier(),validation);
        when(targets.resolve("target")).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class,() -> service.recordDeviation(command(null,null)));verifyNoInteractions(repository);
        setup();service.recordDeviation(command(new BigDecimal("-50"),null));
        verify(repository).save(argThat(d -> d.severity()==DeviationSeverity.CRITICAL && d.status()==DeviationStatus.OPEN));
        service.recordDeviation(command(null,DeviationSeverity.LOW));
        verify(repository).save(argThat(d -> d.severity()==DeviationSeverity.LOW));
    }
}
