/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologySimulationScopeQueryServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Application Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Verifies Simulation-facing Topology scope resolution.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import dz.sh.hidra.modules.topology.application.port.out.PipelineRepositoryPort;
import dz.sh.hidra.modules.topology.application.port.out.PipelineSystemRepositoryPort;
import java.util.Optional;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TopologySimulationScopeQueryServiceTest {

    @Test
    void reportsDocumentedButUnrepresentedGroupTypesAsUnsupported() {
        var service = new TopologySimulationScopeQueryService(
                mock(PipelineSystemRepositoryPort.class),
                mock(PipelineRepositoryPort.class)
        );

        assertThat(service.resolve("SEGMENT_GROUP", "group-1").supported()).isFalse();
        assertThat(service.resolve("FACILITY_NETWORK", "network-1").supported()).isFalse();
    }

    @Test
    void reportsMissingPipelineAsSupportedButAbsent() {
        var systems = mock(PipelineSystemRepositoryPort.class);
        var pipelines = mock(PipelineRepositoryPort.class);
        when(pipelines.findById("pipeline-1")).thenReturn(Optional.empty());
        var service = new TopologySimulationScopeQueryService(systems, pipelines);

        var result = service.resolve("PIPELINE", "pipeline-1");

        assertThat(result.supported()).isTrue();
        assertThat(result.exists()).isFalse();
        assertThat(result.eligible()).isFalse();
    }
}
