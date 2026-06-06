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
import dz.sh.hidra.modules.topology.domain.value.FacilityTypeReference;
import dz.sh.hidra.modules.topology.domain.value.NodeTypeReference;

/**
 * Validates physical facility rules.
 *
 * <p>Business role:
 * This policy protects the distinction between a physical facility in topology and a facility/station
 * as an organization unit in organization.
 *
 * <p>Architecture role:
 * This is a pure domain policy. It evaluates configurable facility and node type references by their
 * stable language-neutral codes.
 *
 * <p>Validation:
 * Facilities are physical assets only. Facility nodes must reference the facility and must use a
 * facility-compatible node type. Terminal, processing plant, and production field are allowed as
 * physical facility types.
 */
public final class FacilityTopologyPolicy {

    public void validateFacility(Facility facility) {
        Objects.requireNonNull(facility, "Facility must not be null.");

        if (facility.facilityType().is("DISPATCHING_CENTER") && facility.productType() == null) {
            throw new TopologyValidationException("Dispatching center facility must still provide a product type classification.");
        }
    }

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

    public void ensurePhysicalFacilityOnly(Facility facility) {
        Objects.requireNonNull(facility, "Facility must not be null.");

        if (facility.organizationUnitReference() != null
                && facility.organizationUnitReference().referenceId().equals(facility.id().value())) {

            throw new TopologyValidationException("Facility id must not be reused as an organization unit id.");
        }
    }

    public boolean canOwnFacilityConnectionNodes(FacilityTypeReference facilityType) {
        Objects.requireNonNull(facilityType, "Facility type reference must not be null.");

        return facilityType.isAny(
                "COMPRESSION_STATION",
                "PUMPING_STATION",
                "METERING_STATION",
                "VALVE_STATION",
                "TERMINAL",
                "PROCESSING_PLANT",
                "PRODUCTION_FIELD",
                "GATHERING_CENTER",
                "STORAGE_FACILITY",
                "DELIVERY_FACILITY",
                "RECEIPT_FACILITY");
    }

    @Deprecated(forRemoval = true)
    public boolean canOwnFacilityConnectionNodes(FacilityType facilityType) {
        return canOwnFacilityConnectionNodes(FacilityTypeReference.from(facilityType));
    }

    private boolean isFacilityNodeType(NodeTypeReference nodeType) {
        return nodeType.isAny(
                "FACILITY_INLET",
                "FACILITY_OUTLET",
                "FACILITY_INTERNAL",
                "RECEIPT_POINT",
                "DELIVERY_POINT",
                "METERING_POINT",
                "CONNECTION_POINT");
    }
}
