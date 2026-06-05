/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateTopologyConnectionCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.command
 *
 * @Description : Application command for creating a topology connection.
 *
 */
package dz.sh.hidra.modules.topology.application.command;

import java.util.Objects;

import dz.sh.hidra.kernel.application.command.Command;
import dz.sh.hidra.modules.topology.domain.value.ConnectionType;
import dz.sh.hidra.modules.topology.domain.value.TopologyAssetType;
import dz.sh.hidra.modules.topology.domain.value.TopologyCode;
import dz.sh.hidra.modules.topology.domain.value.TopologyName;
import dz.sh.hidra.modules.topology.domain.value.TopologyNodeId;

/**
 * Carries input required to create an explicit topology connection.
 *
 * <p>Business role:
 * This command creates a graph edge between two topology nodes.
 *
 * <p>Architecture role:
 * This is an application command and does not depend on REST, JPA, repositories, identity, or
 * organization implementation.
 *
 * <p>Validation:
 * Code, name, endpoint nodes, connection type, linked asset type, and linked asset id are mandatory.
 * Endpoint nodes must be different.
 *
 * @param code business code
 * @param name display name
 * @param fromNodeId from-node identifier
 * @param toNodeId to-node identifier
 * @param connectionType connection type
 * @param linkedAssetType linked asset type
 * @param linkedAssetId linked asset identifier
 */
public record CreateTopologyConnectionCommand(
        TopologyCode code,
        TopologyName name,
        TopologyNodeId fromNodeId,
        TopologyNodeId toNodeId,
        ConnectionType connectionType,
        TopologyAssetType linkedAssetType,
        String linkedAssetId) implements Command {

    public CreateTopologyConnectionCommand {
        Objects.requireNonNull(code, "Topology connection code must not be null.");
        Objects.requireNonNull(name, "Topology connection name must not be null.");
        Objects.requireNonNull(fromNodeId, "Topology connection from node id must not be null.");
        Objects.requireNonNull(toNodeId, "Topology connection to node id must not be null.");
        Objects.requireNonNull(connectionType, "Topology connection type must not be null.");
        Objects.requireNonNull(linkedAssetType, "Topology connection linked asset type must not be null.");
        linkedAssetId = normalizeRequired(linkedAssetId, "Topology connection linked asset id", 120);

        if (fromNodeId.equals(toNodeId)) {
            throw new IllegalArgumentException("Topology connection from node and to node must be different.");
        }
    }

    private static String normalizeRequired(String value, String label, int maxLength) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(label + " must not be null or blank.");
        }

        String normalized = value.trim();
        if (normalized.length() > maxLength) {
            throw new IllegalArgumentException(label + " must not exceed " + maxLength + " characters.");
        }
        return normalized;
    }
}
