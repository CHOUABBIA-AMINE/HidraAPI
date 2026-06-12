/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyNode
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
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
        id = normalize(id);
        code = normalize(code);
        name = normalize(name);
        facilityId = normalize(facilityId);
    }
    private static String normalize(String value) { return value == null || value.isBlank() ? null : value.trim(); }
}
