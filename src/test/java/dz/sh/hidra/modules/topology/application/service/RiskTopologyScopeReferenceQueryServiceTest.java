/**
 * @Project : HidraAPI
 * @Product : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author : Abir MEDJERAB
 * @Owner : Sonatrach / TRC : Digitalization Initiative
 * @Name : RiskTopologyScopeReferenceQueryServiceTest
 * @CreatedOn : 2025-06-26
 * @UpdatedOn : 2026-10-05
 * @Type : Class
 * @Layer : Topology Test
 * @Module : topology
 * @Package : dz.sh.hidra.modules.topology.application.service
 */
package dz.sh.hidra.modules.topology.application.service;

import dz.sh.hidra.modules.topology.application.port.out.EquipmentRepositoryPort;
import dz.sh.hidra.modules.topology.application.port.out.FacilityRepositoryPort;
import dz.sh.hidra.modules.topology.application.port.out.PipelineRepositoryPort;
import dz.sh.hidra.modules.topology.application.port.out.PipelineSystemRepositoryPort;
import dz.sh.hidra.modules.topology.domain.model.Equipment;
import java.util.Optional;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RiskTopologyScopeReferenceQueryServiceTest {

    @Test
    void supportsOnlyRegisteredTopologyScopeTypes() {
        PipelineSystemRepositoryPort systems = mock(PipelineSystemRepositoryPort.class);
        PipelineRepositoryPort pipelines = mock(PipelineRepositoryPort.class);
        FacilityRepositoryPort facilities = mock(FacilityRepositoryPort.class);
        EquipmentRepositoryPort equipment = mock(EquipmentRepositoryPort.class);
        Equipment model = mock(Equipment.class);
        when(model.id()).thenReturn("equipment-1");
        when(model.code()).thenReturn("EQ-1");
        when(model.name()).thenReturn("Equipment 1");
        when(equipment.findById("equipment-1")).thenReturn(Optional.of(model));

        RiskTopologyScopeReferenceQueryService service =
                new RiskTopologyScopeReferenceQueryService(systems, pipelines, facilities, equipment);

        assertThat(service.resolve("EQUIPMENT", " equipment-1 ")).isPresent();
        assertThat(service.resolve("PIPELINE_SEGMENT", "segment-1")).isEmpty();
    }
}
