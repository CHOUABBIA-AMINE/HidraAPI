/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaAnalyticsTopologyScopeContractAdapterTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Topology Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.reference
 *
 * @Description : Verifies the Topology-owned Analytics scope contract.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.reference;

import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.EquipmentJpaRepository;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.FacilityJpaRepository;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.PipelineJpaRepository;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.PipelineSegmentJpaRepository;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.PipelineSystemJpaRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class JpaAnalyticsTopologyScopeContractAdapterTest {

    @Test
    void supportsOnlyRegisteredAnalyticsTopologyScopesAndChecksOwnerExistence() {
        var systems = mock(PipelineSystemJpaRepository.class);
        var pipelines = mock(PipelineJpaRepository.class);
        var segments = mock(PipelineSegmentJpaRepository.class);
        var facilities = mock(FacilityJpaRepository.class);
        var equipment = mock(EquipmentJpaRepository.class);
        var adapter = new JpaAnalyticsTopologyScopeContractAdapter(
                systems, pipelines, segments, facilities, equipment
        );

        when(pipelines.existsById("pipeline-1")).thenReturn(true);

        var resolved = adapter.resolve("pipeline", "pipeline-1");
        assertThat(resolved.supported()).isTrue();
        assertThat(resolved.exists()).isTrue();

        assertThat(adapter.resolve("PIPELINE_SYSTEM", "missing").supported()).isTrue();
        assertThat(adapter.resolve("PIPELINE_SEGMENT", "missing").supported()).isTrue();
        assertThat(adapter.resolve("FACILITY", "missing").supported()).isTrue();
        assertThat(adapter.resolve("EQUIPMENT", "missing").supported()).isTrue();
        assertThat(adapter.resolve("PRODUCT", "product-1").supported()).isFalse();
    }
}
