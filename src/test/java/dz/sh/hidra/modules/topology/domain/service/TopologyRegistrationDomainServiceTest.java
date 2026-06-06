/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyRegistrationDomainServiceTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.service
 *
 * @Description : Unit tests for topology registration domain service.
 *
 */
package dz.sh.hidra.modules.topology.domain.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.modules.topology.domain.TopologyDomainTestData;
import dz.sh.hidra.modules.topology.domain.exception.TopologyValidationException;
import dz.sh.hidra.modules.topology.domain.model.Equipment;
import dz.sh.hidra.modules.topology.domain.model.Facility;
import dz.sh.hidra.modules.topology.domain.model.Pipeline;
import dz.sh.hidra.modules.topology.domain.model.PipelineSystem;
import dz.sh.hidra.modules.topology.domain.model.TopologyNode;
import dz.sh.hidra.modules.topology.domain.policy.FacilityTopologyPolicy;
import dz.sh.hidra.modules.topology.domain.policy.PipelineAppurtenancePolicy;
import dz.sh.hidra.modules.topology.domain.policy.TopologyAssetStatusPolicy;
import dz.sh.hidra.modules.topology.domain.policy.TopologyConnectivityPolicy;
import dz.sh.hidra.modules.topology.domain.value.NodeType;
import dz.sh.hidra.modules.topology.domain.value.PipelineSystemId;

/**
 * Unit tests for topology registration domain service.
 */
class TopologyRegistrationDomainServiceTest {

    private final TopologyRegistrationDomainService service = new TopologyRegistrationDomainService(
            new TopologyAssetStatusPolicy(),
            new TopologyConnectivityPolicy(),
            new FacilityTopologyPolicy(),
            new PipelineAppurtenancePolicy());

    @Test
    void shouldValidatePipelineRegistrationAgainstParentSystem() {
        PipelineSystem pipelineSystem = TopologyDomainTestData.pipelineSystem();
        Pipeline pipeline = TopologyDomainTestData.pipeline(pipelineSystem);

        assertDoesNotThrow(() -> service.validatePipelineRegistration(pipelineSystem, pipeline));
    }

    @Test
    void shouldRejectPipelineRegistrationWithDifferentPipelineSystem() {
        PipelineSystem pipelineSystem = TopologyDomainTestData.pipelineSystem();
        Pipeline pipeline = TopologyDomainTestData.pipelineRestoredWithSystemId(PipelineSystemId.newId());

        assertThrows(TopologyValidationException.class, () -> service.validatePipelineRegistration(pipelineSystem, pipeline));
    }

    @Test
    void shouldRejectChildRegistrationOnRetiredPipelineSystem() {
        PipelineSystem retiredPipelineSystem = TopologyDomainTestData.retiredPipelineSystem();
        Pipeline pipeline = TopologyDomainTestData.pipeline(retiredPipelineSystem);

        assertThrows(TopologyValidationException.class, () -> service.validatePipelineRegistration(retiredPipelineSystem, pipeline));
    }

    @Test
    void shouldValidateFacilityAndFacilityNodeRegistration() {
        Facility facility = TopologyDomainTestData.facility();
        TopologyNode node = TopologyDomainTestData.facilityNode(facility, NodeType.FACILITY_OUTLET);

        assertDoesNotThrow(() -> service.validateFacilityRegistration(facility));
        assertDoesNotThrow(() -> service.validateFacilityNodeRegistration(facility, node));
    }

    @Test
    void shouldRejectEquipmentWithEquipmentParentAssetType() {
        Equipment equipment = TopologyDomainTestData.equipmentWithEquipmentParent();

        assertThrows(TopologyValidationException.class, () -> service.validateEquipmentRegistration(equipment));
    }
}
