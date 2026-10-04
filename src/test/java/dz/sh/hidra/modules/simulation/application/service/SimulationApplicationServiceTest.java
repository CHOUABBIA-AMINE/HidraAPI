/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationApplicationServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Application Test
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.application.service
 *
 * @Description : Verifies HMR-009 SimulationModel creation validation.
 *
 */
package dz.sh.hidra.modules.simulation.application.service;

import dz.sh.hidra.modules.simulation.application.command.CreateSimulationModelCommand;
import dz.sh.hidra.modules.simulation.application.port.out.SimulationModelRepositoryPort;
import dz.sh.hidra.modules.simulation.application.port.out.SimulationRecommendationRepositoryPort;
import dz.sh.hidra.modules.simulation.application.port.out.SimulationRunRepositoryPort;
import dz.sh.hidra.modules.simulation.application.port.out.SimulationScenarioRepositoryPort;
import dz.sh.hidra.modules.simulation.domain.exception.InvalidSimulationValueException;
import dz.sh.hidra.modules.topology.application.contract.simulation.SimulationTopologyScopeContract;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SimulationApplicationServiceTest {

    @Test
    void rejectsDuplicateModelCodeBeforeSave() {
        var modelRepository = mock(SimulationModelRepositoryPort.class);
        var service = service(modelRepository, mock(SimulationTopologyScopeContract.class));
        var command = command("MODEL-1", "PIPELINE", null);

        when(modelRepository.existsByCode("MODEL-1")).thenReturn(true);

        assertThatThrownBy(() -> service.createSimulationModel(command))
                .isInstanceOf(InvalidSimulationValueException.class)
                .hasMessageContaining("unique");

        verify(modelRepository, never()).save(org.mockito.ArgumentMatchers.any());
    }

    @Test
    void rejectsWrongOrInactiveModelTypeFamilyBeforeSave() {
        var modelRepository = mock(SimulationModelRepositoryPort.class);
        var service = service(modelRepository, mock(SimulationTopologyScopeContract.class));
        var command = command("MODEL-1", "PIPELINE", null);

        when(modelRepository.existsByCode("MODEL-1")).thenReturn(false);
        when(modelRepository.isActiveModelType("model-type-1")).thenReturn(false);

        assertThatThrownBy(() -> service.createSimulationModel(command))
                .isInstanceOf(InvalidSimulationValueException.class)
                .hasMessageContaining("SIMULATION_MODEL_TYPE");
    }

    @Test
    void rejectsUnsupportedSuppliedTopologyScopePair() {
        var modelRepository = mock(SimulationModelRepositoryPort.class);
        var topology = mock(SimulationTopologyScopeContract.class);
        var service = service(modelRepository, topology);
        var command = command("MODEL-1", "SEGMENT_GROUP", "group-1");

        when(modelRepository.existsByCode("MODEL-1")).thenReturn(false);
        when(modelRepository.isActiveModelType("model-type-1")).thenReturn(true);
        when(topology.resolve("SEGMENT_GROUP", "group-1"))
                .thenReturn(SimulationTopologyScopeContract.ScopeResolution.unsupported());

        assertThatThrownBy(() -> service.createSimulationModel(command))
                .isInstanceOf(InvalidSimulationValueException.class)
                .hasMessageContaining("not currently supported");
    }

    private static SimulationApplicationService service(
            SimulationModelRepositoryPort modelRepository,
            SimulationTopologyScopeContract topology
    ) {
        return new SimulationApplicationService(
                modelRepository,
                mock(SimulationScenarioRepositoryPort.class),
                mock(SimulationRunRepositoryPort.class),
                mock(SimulationRecommendationRepositoryPort.class),
                topology
        );
    }

    private static CreateSimulationModelCommand command(String code, String scopeType, String scopeId) {
        return new CreateSimulationModelCommand(
                code,
                null,
                "Modèle",
                null,
                "model-type-1",
                scopeType,
                scopeId,
                "description"
        );
    }
}
