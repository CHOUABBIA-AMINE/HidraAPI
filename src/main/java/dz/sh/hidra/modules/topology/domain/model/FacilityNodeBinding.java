/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : FacilityNodeBinding
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.model
 *
 * @Description : Binding between facility and graph node.
 *
 */
package dz.sh.hidra.modules.topology.domain.model;

import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException;
import dz.sh.hidra.modules.topology.domain.value.*;
import java.time.Instant;
public record FacilityNodeBinding(
        String id,
        String facilityId,
        String nodeId,
        String bindingRoleCode,
        boolean primaryBinding,
        TopologyStatus status,
        Instant createdAt,
        Instant updatedAt
) {
    public FacilityNodeBinding {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidTopologyValueException("FacilityNodeBinding id must not be blank.");
        }
        // HRA-051 required: facilityId
        if (facilityId == null || facilityId.isBlank()) {
            throw new InvalidTopologyValueException("FacilityNodeBinding facility id must not be blank.");
        }
        // HRA-051 required: nodeId
        if (nodeId == null || nodeId.isBlank()) {
            throw new InvalidTopologyValueException("FacilityNodeBinding node id must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidTopologyValueException("FacilityNodeBinding status must not be null.");
        }

        id = normalize(id);
        facilityId = normalize(facilityId);
        nodeId = normalize(nodeId);
        bindingRoleCode = normalize(bindingRoleCode);
    }
    private static String normalize(String value) { return value == null || value.isBlank() ? null : value.trim(); }
}
