/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyOperationalScopeTargetQueryServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Class
 * @Layer       : Topology Test
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Verifies topology-owned operational-scope target resolution.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import dz.sh.hidra.modules.topology.application.port.out.EquipmentRepositoryPort;
import dz.sh.hidra.modules.topology.application.port.out.FacilityRepositoryPort;
import dz.sh.hidra.modules.topology.application.port.out.PipelineRepositoryPort;
import dz.sh.hidra.modules.topology.application.port.out.PipelineSystemRepositoryPort;
import dz.sh.hidra.modules.topology.domain.model.Equipment;
import dz.sh.hidra.modules.topology.domain.model.Facility;
import dz.sh.hidra.modules.topology.domain.model.Pipeline;
import dz.sh.hidra.modules.topology.domain.model.PipelineSystem;
import dz.sh.hidra.modules.topology.domain.value.EquipmentKind;
import dz.sh.hidra.modules.topology.domain.value.EquipmentStatus;
import dz.sh.hidra.modules.topology.domain.value.FacilityKind;
import dz.sh.hidra.modules.topology.domain.value.FacilityStatus;
import dz.sh.hidra.modules.topology.domain.value.PipelineSystemType;
import dz.sh.hidra.modules.topology.domain.value.PipelineType;
import dz.sh.hidra.modules.topology.domain.value.TopologyStatus;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TopologyOperationalScopeTargetQueryServiceTest {

    private static final Instant NOW = Instant.parse("2026-09-27T10:00:00Z");

    @Test
    void resolvesAllSupportedTopologyOwnersWithCurrentDisplayAndLifecycle() {
        TopologyOperationalScopeTargetQueryService service = service(
                pipelineSystem("system-1", TopologyStatus.ACTIVE),
                pipeline("pipeline-1", TopologyStatus.SUSPENDED),
                facility("facility-1", FacilityStatus.RETIRED),
                equipment("equipment-1", EquipmentStatus.ACTIVE)
        );

        var system = service.resolvePipelineSystem(" system-1 ").orElseThrow();
        assertEquals("SYS-1", system.code());
        assertEquals("System EN", system.name());
        assertTrue(system.assignable());

        var pipeline = service.resolvePipeline("pipeline-1").orElseThrow();
        assertEquals("Canalisation FR", pipeline.name());
        assertFalse(pipeline.assignable());

        var facility = service.resolveFacility("facility-1").orElseThrow();
        assertEquals("Installation FR", facility.name());
        assertFalse(facility.assignable());

        var equipment = service.resolveEquipment("equipment-1").orElseThrow();
        assertEquals("Equipment current name", equipment.name());
        assertTrue(equipment.assignable());
    }

    @Test
    void absentOwnerRemainsAbsentAndBlankOwnerIdIsRejected() {
        TopologyOperationalScopeTargetQueryService service = service(
                pipelineSystem("system-1", TopologyStatus.ACTIVE),
                pipeline("pipeline-1", TopologyStatus.ACTIVE),
                facility("facility-1", FacilityStatus.ACTIVE),
                equipment("equipment-1", EquipmentStatus.ACTIVE)
        );

        assertTrue(service.resolvePipeline("missing").isEmpty());
        assertThrows(IllegalArgumentException.class, () -> service.resolveFacility("  "));
    }

    private static TopologyOperationalScopeTargetQueryService service(
            PipelineSystem system,
            Pipeline pipeline,
            Facility facility,
            Equipment equipment
    ) {
        return new TopologyOperationalScopeTargetQueryService(
                pipelineSystemRepository(system),
                pipelineRepository(pipeline),
                facilityRepository(facility),
                equipmentRepository(equipment)
        );
    }

    private static PipelineSystem pipelineSystem(String id, TopologyStatus status) {
        return new PipelineSystem(
                id,
                "SYS-1",
                "نظام",
                "Système FR",
                "System EN",
                PipelineSystemType.TRANSPORT,
                status,
                null,
                null,
                null,
                NOW,
                NOW
        );
    }

    private static Pipeline pipeline(String id, TopologyStatus status) {
        return new Pipeline(
                id,
                "system-1",
                "PL-1",
                "خط",
                "Canalisation FR",
                null,
                PipelineType.NATURAL_GAS,
                null,
                null,
                null,
                null,
                null,
                status,
                NOW,
                NOW
        );
    }

    private static Facility facility(String id, FacilityStatus status) {
        return new Facility(
                id,
                "FAC-1",
                "منشأة",
                "Installation FR",
                null,
                "FACILITY-TYPE-1",
                FacilityKind.STATION,
                null,
                null,
                null,
                null,
                null,
                null,
                status,
                null,
                null,
                NOW,
                NOW
        );
    }

    private static Equipment equipment(String id, EquipmentStatus status) {
        return new Equipment(
                id,
                "EQ-1",
                "Equipment current name",
                "facility-1",
                null,
                null,
                "EQUIPMENT-TYPE-1",
                EquipmentKind.PUMP,
                null,
                null,
                null,
                status,
                null,
                null,
                NOW,
                NOW
        );
    }

    private static PipelineSystemRepositoryPort pipelineSystemRepository(PipelineSystem model) {
        return new PipelineSystemRepositoryPort() {
            @Override
            public PipelineSystem save(PipelineSystem value) {
                return value;
            }

            @Override
            public Optional<PipelineSystem> findById(String id) {
                return model.id().equals(id) ? Optional.of(model) : Optional.empty();
            }
        };
    }

    private static PipelineRepositoryPort pipelineRepository(Pipeline model) {
        return new PipelineRepositoryPort() {
            @Override
            public Pipeline save(Pipeline value) {
                return value;
            }

            @Override
            public Optional<Pipeline> findById(String id) {
                return model.id().equals(id) ? Optional.of(model) : Optional.empty();
            }
        };
    }

    private static FacilityRepositoryPort facilityRepository(Facility model) {
        return new FacilityRepositoryPort() {
            @Override
            public Facility save(Facility value) {
                return value;
            }

            @Override
            public Optional<Facility> findById(String id) {
                return model.id().equals(id) ? Optional.of(model) : Optional.empty();
            }
        };
    }

    private static EquipmentRepositoryPort equipmentRepository(Equipment model) {
        return new EquipmentRepositoryPort() {
            @Override
            public Equipment save(Equipment value) {
                return value;
            }

            @Override
            public Optional<Equipment> findById(String id) {
                return model.id().equals(id) ? Optional.of(model) : Optional.empty();
            }
        };
    }
}
