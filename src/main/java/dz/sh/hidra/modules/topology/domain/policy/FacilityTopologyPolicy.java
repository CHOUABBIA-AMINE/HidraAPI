/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : FacilityTopologyPolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.policy
 *
 * @Description : Policy validating physical facility topology rules.
 *
 */
package dz.sh.hidra.modules.topology.domain.policy;

import java.util.Objects;

import dz.sh.hidra.modules.topology.domain.exception.TopologyValidationException;
import dz.sh.hidra.modules.topology.domain.model.Facility;
import dz.sh.hidra.modules.topology.domain.model.TopologyNode;
import dz.sh.hidra.modules.topology.domain.value.FacilityType;
import dz.sh.hidra.modules.topology.domain.value.NodeType;

/**
 * Validates physical facility rules.
 *
 * <p>Business role:
 * This policy protects the distinction between a physical facility in topology and a facility/station
 * as an organization unit in organization.
 *
 * <p>Architecture role:
 * This is a pure domain policy. It must not be annotated as a Spring bean and must not access
 * repositories, persistence adapters, REST DTOs, identity implementation, organization implementation,
 * measurement, flow, risk, workflow, or infrastructure code.
 *
 * <p>Validation:
 * Facilities are physical assets only. Facility nodes must reference the facility and must use a
 * facility-compatible node type. Terminal, processing plant, and production field are allowed as
 * physical facility types.
 *
 * <p>Usage:
 * Domain/application services may call this policy before attaching nodes to facilities.
 */
public final class FacilityTopologyPolicy {

    /**
     * Validates a facility as a physical topology asset.
     *
     * @param facility facility to validate
     */
    public void validateFacility(Facility facility) {
        Objects.requireNonNull(facility, "Facility must not be null.");

        if (facility.facilityType() == FacilityType.DISPATCHING_CENTER && facility.productType() == null) {
            throw new TopologyValidationException("Dispatching center facility must still provide a product type classification.");
        }
    }

    /**
     * Validates that a node belongs to a physical facility.
     *
     * @param facility physical facility
     * @param node topology node
     */
    public void validateFacilityNode(Facility facility, TopologyNode node) {
        Objects.requireNonNull(facility, "Facility must not be null.");
        Objects.requireNonNull(node, "Topology node must not be null.");

        if (node.facilityId() == null) {
            throw new TopologyValidationException("Facility topology node must reference a facility id.");
        }

        if (!node.facilityId().equals(facility.id())) {
            throw new TopologyValidationException("Facility topology node must reference the provided facility.");
        }

        if (!isFacilityNodeType(node.nodeType())) {
            throw new TopologyValidationException("Topology node type is not valid for a facility node.");
        }
    }

    /**
     * Ensures the physical facility is not treated as an organization unit implementation.
     *
     * @param facility physical facility
     */
    public void ensurePhysicalFacilityOnly(Facility facility) {
        Objects.requireNonNull(facility, "Facility must not be null.");

        if (facility.organizationUnitReference() != null
                && facility.organizationUnitReference().referenceId().equals(facility.id().value())) {

            throw new TopologyValidationException("Facility id must not be reused as an organization unit id.");
        }
    }

    /**
     * Indicates whether the facility type is a site that can own facility inlet/outlet nodes.
     *
     * @param facilityType facility type
     * @return true when inlet/outlet nodes are allowed
     */
    public boolean canOwnFacilityConnectionNodes(FacilityType facilityType) {
        Objects.requireNonNull(facilityType, "Facility type must not be null.");

        return switch (facilityType) {
            case COMPRESSION_STATION,
                    PUMPING_STATION,
                    METERING_STATION,
                    VALVE_STATION,
                    TERMINAL,
                    PROCESSING_PLANT,
                    PRODUCTION_FIELD,
                    GATHERING_CENTER,
                    STORAGE_FACILITY,
                    DELIVERY_FACILITY,
                    RECEIPT_FACILITY -> true;
            case DISPATCHING_CENTER,
                    OTHER -> false;
        };
    }

    private boolean isFacilityNodeType(NodeType nodeType) {
        return nodeType == NodeType.FACILITY_INLET
                || nodeType == NodeType.FACILITY_OUTLET
                || nodeType == NodeType.FACILITY_INTERNAL
                || nodeType == NodeType.RECEIPT_POINT
                || nodeType == NodeType.DELIVERY_POINT
                || nodeType == NodeType.METERING_POINT
                || nodeType == NodeType.CONNECTION_POINT;
    }
}
