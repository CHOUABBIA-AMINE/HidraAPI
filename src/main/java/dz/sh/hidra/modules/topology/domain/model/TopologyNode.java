/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyNode
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.model
 *
 * @Description : Graph node such as source, junction, station, delivery point, or tie-in.
 *
 */
package dz.sh.hidra.modules.topology.domain.model;

import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException;
import dz.sh.hidra.modules.topology.domain.value.*;
import java.time.Instant;
import java.math.BigDecimal;
public record TopologyNode(
        String id,
        String code,
        String name,
        NodeType nodeType,
        String facilityId,
        BigDecimal latitude,
        BigDecimal longitude,
        BigDecimal elevationMeters,
        TopologyStatus status,
        Instant createdAt,
        Instant updatedAt
) {
    public TopologyNode {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidTopologyValueException("TopologyNode id must not be blank.");
        }
        // HRA-051 required: code
        if (code == null || code.isBlank()) {
            throw new InvalidTopologyValueException("TopologyNode code must not be blank.");
        }
        // HRA-051 required: nodeType
        if (nodeType == null) {
            throw new InvalidTopologyValueException("TopologyNode node type must not be null.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidTopologyValueException("TopologyNode status must not be null.");
        }

        id = normalize(id);
        code = normalize(code);
        name = normalize(name);
        facilityId = normalize(facilityId);
    }
    private static String normalize(String value) { return value == null || value.isBlank() ? null : value.trim(); }
}
