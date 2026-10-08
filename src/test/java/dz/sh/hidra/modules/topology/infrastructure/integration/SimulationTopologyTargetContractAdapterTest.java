/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SimulationTopologyTargetContractAdapterTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.integration
 *
 * @Description : Enforces the accepted Simulation owner boundary.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.integration;

import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.*;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class SimulationTopologyTargetContractAdapterTest {
    @Test void allSixOwnerTypesResolveAndOtherScopesRemainUnsupported() {
        var pipeline=mock(PipelineJpaRepository.class);
        var segment=mock(PipelineSegmentJpaRepository.class);
        var facility=mock(FacilityJpaRepository.class);
        var equipment=mock(EquipmentJpaRepository.class);
        var node=mock(TopologyNodeJpaRepository.class);
        var connection=mock(TopologyConnectionJpaRepository.class);
        when(pipeline.existsById("target")).thenReturn(true);
        when(segment.existsById("target")).thenReturn(true);
        when(facility.existsById("target")).thenReturn(true);
        when(equipment.existsById("target")).thenReturn(true);
        when(node.existsById("target")).thenReturn(true);
        when(connection.existsById("target")).thenReturn(true);
        var adapter=new SimulationTopologyTargetContractAdapter(pipeline,segment,facility,equipment,node,connection);
        for (String type:new String[]{"PIPELINE","SEGMENT","FACILITY","EQUIPMENT","NODE","CONNECTION"}) {
            assertThat(adapter.exists(" "+type+" "," target ")).isTrue();
            assertThat(adapter.exists(type,"missing")).isFalse();
        }
        assertThat(adapter.exists("PIPELINE_SYSTEM","target")).isFalse();
        assertThat(adapter.exists("pipeline","target")).isFalse();
        assertThat(adapter.exists(null,"target")).isFalse();
        assertThat(adapter.exists("PIPELINE"," ")).isFalse();
    }
}
