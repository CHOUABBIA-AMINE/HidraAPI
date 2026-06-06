/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : FacilityTopologyPolicyTest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Test
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.policy
 *
 * @Description : Unit tests for physical facility topology policy.
 *
 */
package dz.sh.hidra.modules.topology.domain.policy;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dz.sh.hidra.modules.topology.domain.TopologyDomainTestData;
import dz.sh.hidra.modules.topology.domain.exception.TopologyValidationException;
import dz.sh.hidra.modules.topology.domain.model.Facility;
import dz.sh.hidra.modules.topology.domain.model.TopologyNode;
import dz.sh.hidra.modules.topology.domain.value.FacilityType;
import dz.sh.hidra.modules.topology.domain.value.NodeType;

/**
 * Unit tests for physical facility topology policy.
 */
class FacilityTopologyPolicyTest {

    private final FacilityTopologyPolicy policy = new FacilityTopologyPolicy();

    @Test
    void shouldAllowTerminalProcessingPlantAndProductionFieldAsPhysicalFacilityTypes() {
        assertTrue(policy.canOwnFacilityConnectionNodes(FacilityType.TERMINAL));
        assertTrue(policy.canOwnFacilityConnectionNodes(FacilityType.PROCESSING_PLANT));
        assertTrue(policy.canOwnFacilityConnectionNodes(FacilityType.PRODUCTION_FIELD));
        assertFalse(policy.canOwnFacilityConnectionNodes(FacilityType.DISPATCHING_CENTER));
    }

    @Test
    void shouldValidateFacilityNodeThatReferencesProvidedFacility() {
        Facility facility = TopologyDomainTestData.facility();
        TopologyNode node = TopologyDomainTestData.facilityNode(facility, NodeType.FACILITY_INLET);

        assertDoesNotThrow(() -> policy.validateFacilityNode(facility, node));
    }

    @Test
    void shouldRejectFacilityNodeWithoutFacilityReference() {
        Facility facility = TopologyDomainTestData.facility();
        TopologyNode node = TopologyDomainTestData.freeNode(NodeType.FACILITY_INLET, "NO-FACILITY");

        assertThrows(TopologyValidationException.class, () -> policy.validateFacilityNode(facility, node));
    }

    @Test
    void shouldRejectFacilityNodeThatReferencesDifferentFacility() {
        Facility firstFacility = TopologyDomainTestData.facility();
        Facility secondFacility = TopologyDomainTestData.facility();
        TopologyNode node = TopologyDomainTestData.facilityNode(secondFacility, NodeType.FACILITY_OUTLET);

        assertThrows(TopologyValidationException.class, () -> policy.validateFacilityNode(firstFacility, node));
    }

    @Test
    void shouldRejectPipelineValveNodeAsFacilityNode() {
        Facility facility = TopologyDomainTestData.facility();
        TopologyNode node = TopologyDomainTestData.facilityNode(facility, NodeType.PIPELINE_VALVE_POINT);

        assertThrows(TopologyValidationException.class, () -> policy.validateFacilityNode(facility, node));
    }

    @Test
    void shouldRejectPhysicalFacilityIdReusedAsOrganizationUnitId() {
        Facility facility = TopologyDomainTestData.restoredFacilityWithIdAsOrganizationUnitReference();

        assertThrows(TopologyValidationException.class, () -> policy.ensurePhysicalFacilityOnly(facility));
    }
}
