/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyId
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.value
 *
 * @Description : Stable topology identifier.
 *
 */
package dz.sh.hidra.modules.topology.domain.value;

import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException;
import java.util.UUID;
public record TopologyId(String value) {
    public TopologyId { if (value == null || value.isBlank()) throw new InvalidTopologyValueException("Topology ID must not be blank."); value = value.trim(); }
    public static TopologyId of(String value) { return new TopologyId(value); }
    public static TopologyId newId() { return new TopologyId(UUID.randomUUID().toString()); }
}
