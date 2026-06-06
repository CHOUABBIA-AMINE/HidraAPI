/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EquipmentApplicationServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Unit tests for equipment application service.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.kernel.domain.exception.BusinessRuleViolationException;
import dz.sh.hidra.modules.topology.application.command.RegisterEquipmentCommand;
import dz.sh.hidra.modules.topology.application.dto.EquipmentDto;
import dz.sh.hidra.modules.topology.domain.TopologyDomainTestData;
import dz.sh.hidra.modules.topology.domain.model.Facility;
import dz.sh.hidra.modules.topology.domain.policy.FacilityTopologyPolicy;
import dz.sh.hidra.modules.topology.domain.policy.PipelineAppurtenancePolicy;
import dz.sh.hidra.modules.topology.domain.policy.TopologyAssetStatusPolicy;
import dz.sh.hidra.modules.topology.domain.policy.TopologyConnectivityPolicy;
import dz.sh.hidra.modules.topology.domain.service.TopologyRegistrationDomainService;
import dz.sh.hidra.modules.topology.domain.value.EquipmentType;
import dz.sh.hidra.modules.topology.domain.value.TopologyAssetType;

/**
 * Unit tests for EquipmentApplicationService.
 */
class EquipmentApplicationServiceTest {

    @Test
    void shouldRegisterEquipmentAndReturnDto() {
        InMemoryTopologyRepositoryPorts.EquipmentItems equipment = new InMemoryTopologyRepositoryPorts.EquipmentItems();
        EquipmentApplicationService service = new EquipmentApplicationService(equipment, registrationDomainService());
        Facility facility = TopologyDomainTestData.facility();

        EquipmentDto dto = service.registerEquipment(new RegisterEquipmentCommand(
                TopologyDomainTestData.code("APP-EQP"),
                TopologyDomainTestData.name("Application Equipment"),
                EquipmentType.COMPRESSOR,
                TopologyAssetType.FACILITY,
                facility.id().value()));

        assertEquals("APP-EQP", dto.code());
        assertEquals("COMPRESSOR", dto.equipmentType());
        assertEquals("FACILITY", dto.parentAssetType());
        assertEquals(facility.id().value(), dto.parentAssetId());
        assertEquals("PLANNED", dto.status());
    }

    @Test
    void shouldRejectDuplicateEquipmentCode() {
        InMemoryTopologyRepositoryPorts.EquipmentItems equipment = new InMemoryTopologyRepositoryPorts.EquipmentItems();
        equipment.save(TopologyDomainTestData.equipment(TopologyDomainTestData.facility()));
        EquipmentApplicationService service = new EquipmentApplicationService(equipment, registrationDomainService());

        assertThrows(BusinessRuleViolationException.class, () -> service.registerEquipment(new RegisterEquipmentCommand(
                TopologyDomainTestData.equipment(TopologyDomainTestData.facility()).code(),
                TopologyDomainTestData.name("Duplicate Equipment"),
                EquipmentType.COMPRESSOR,
                TopologyAssetType.FACILITY,
                TopologyDomainTestData.facility().id().value())));
    }

    @Test
    void shouldRejectEquipmentWithEquipmentParentAssetType() {
        EquipmentApplicationService service = new EquipmentApplicationService(
                new InMemoryTopologyRepositoryPorts.EquipmentItems(),
                registrationDomainService());

        assertThrows(BusinessRuleViolationException.class, () -> service.registerEquipment(new RegisterEquipmentCommand(
                TopologyDomainTestData.code("APP-EQP-BAD-PARENT"),
                TopologyDomainTestData.name("Bad Parent Equipment"),
                EquipmentType.METER,
                TopologyAssetType.EQUIPMENT,
                "eqp-parent")));
    }

    private static TopologyRegistrationDomainService registrationDomainService() {
        return new TopologyRegistrationDomainService(
                new TopologyAssetStatusPolicy(),
                new TopologyConnectivityPolicy(),
                new FacilityTopologyPolicy(),
                new PipelineAppurtenancePolicy());
    }


}
