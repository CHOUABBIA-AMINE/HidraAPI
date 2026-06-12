/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : FacilityNodeBinding
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
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
        id = normalize(id);
        facilityId = normalize(facilityId);
        nodeId = normalize(nodeId);
        bindingRoleCode = normalize(bindingRoleCode);
    }
    private static String normalize(String value) { return value == null || value.isBlank() ? null : value.trim(); }
}
