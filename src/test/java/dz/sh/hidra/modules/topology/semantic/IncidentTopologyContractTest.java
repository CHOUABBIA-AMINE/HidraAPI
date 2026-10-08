/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentTopologyContractTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Class
 * @Layer       : Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.semantic
 *
 * @Description : Enforces Incident lifecycle integrity and owner-controlled evidence.
 *
 */
package dz.sh.hidra.modules.topology.semantic;

import dz.sh.hidra.modules.topology.infrastructure.integration.IncidentTopologyQueryAdapter;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.*;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class IncidentTopologyContractTest {
    @Test void unknownAndMissingTypedAssetsFailClosed() {
        var pipeline=mock(PipelineJpaRepository.class);var query=new IncidentTopologyQueryAdapter(pipeline,mock(PipelineSegmentJpaRepository.class),mock(FacilityJpaRepository.class),mock(EquipmentJpaRepository.class),mock(TopologyNodeJpaRepository.class),mock(TopologyConnectionJpaRepository.class));
        assertTrue(query.resolve("STATION","id").isEmpty());verifyNoInteractions(pipeline);
        when(pipeline.findById("id")).thenReturn(Optional.empty());assertTrue(query.resolve("PIPELINE","id").isEmpty());
    }
}
