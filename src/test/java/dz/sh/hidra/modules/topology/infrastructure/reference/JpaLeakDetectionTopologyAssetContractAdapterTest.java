/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaLeakDetectionTopologyAssetContractAdapterTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Class
 * @Layer       : Topology Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.reference
 *
 * @Description : Verifies the Topology-owned Leak Detection asset contract.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.reference;

import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.PipelineJpaEntity;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.EquipmentJpaRepository;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.FacilityJpaRepository;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.PipelineJpaRepository;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.PipelineSegmentJpaRepository;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.TopologyNodeJpaRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class JpaLeakDetectionTopologyAssetContractAdapterTest {

    @Test
    void resolvesOwnerSnapshotsAndFailsClosedForUnsupportedTypes() {
        var pipelineRepository = mock(PipelineJpaRepository.class);
        var segmentRepository = mock(PipelineSegmentJpaRepository.class);
        var facilityRepository = mock(FacilityJpaRepository.class);
        var nodeRepository = mock(TopologyNodeJpaRepository.class);
        var equipmentRepository = mock(EquipmentJpaRepository.class);
        var adapter = new JpaLeakDetectionTopologyAssetContractAdapter(
                pipelineRepository,
                segmentRepository,
                facilityRepository,
                nodeRepository,
                equipmentRepository
        );

        var pipeline = mock(PipelineJpaEntity.class);
        when(pipeline.id()).thenReturn("pipeline-1");
        when(pipeline.code()).thenReturn("GZ1");
        when(pipeline.nameEn()).thenReturn(null);
        when(pipeline.nameFr()).thenReturn("Gazoduc 1");
        when(pipeline.nameAr()).thenReturn(null);
        when(pipelineRepository.findById("pipeline-1")).thenReturn(Optional.of(pipeline));

        var resolved = adapter.resolve("pipeline", "pipeline-1");
        assertThat(resolved.supported()).isTrue();
        assertThat(resolved.exists()).isTrue();
        assertThat(resolved.id()).isEqualTo("pipeline-1");
        assertThat(resolved.code()).isEqualTo("GZ1");
        assertThat(resolved.name()).isEqualTo("Gazoduc 1");

        assertThat(adapter.resolve("PIPELINE_SYSTEM", "system-1").supported()).isFalse();
    }

    @Test
    void allFiveDocumentedLeakDetectionTypesAreSupportedEvenWhenTargetIsMissing() {
        var pipelineRepository = mock(PipelineJpaRepository.class);
        var segmentRepository = mock(PipelineSegmentJpaRepository.class);
        var facilityRepository = mock(FacilityJpaRepository.class);
        var nodeRepository = mock(TopologyNodeJpaRepository.class);
        var equipmentRepository = mock(EquipmentJpaRepository.class);
        var adapter = new JpaLeakDetectionTopologyAssetContractAdapter(
                pipelineRepository,
                segmentRepository,
                facilityRepository,
                nodeRepository,
                equipmentRepository
        );

        assertThat(adapter.resolve("PIPELINE", "missing").supported()).isTrue();
        assertThat(adapter.resolve("PIPELINE_SEGMENT", "missing").supported()).isTrue();
        assertThat(adapter.resolve("FACILITY", "missing").supported()).isTrue();
        assertThat(adapter.resolve("TOPOLOGY_NODE", "missing").supported()).isTrue();
        assertThat(adapter.resolve("EQUIPMENT", "missing").supported()).isTrue();
    }
}
