/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyConnectionValidator
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.service
 *
 * @Description : Validates topology connections.
 *
 */
package dz.sh.hidra.modules.topology.domain.service;

import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyConnectionException;
import dz.sh.hidra.modules.topology.domain.model.TopologyConnection;
public class TopologyConnectionValidator {
    public void validate(TopologyConnection connection) {
        if (connection == null) throw new InvalidTopologyConnectionException("Topology connection must not be null.");
        if (connection.fromNodeId() != null && connection.fromNodeId().equals(connection.toNodeId())) throw new InvalidTopologyConnectionException("Topology connection must not connect a node to itself.");
    }
}
