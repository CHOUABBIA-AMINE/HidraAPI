/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyConnectionDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.dto
 *
 * @Description : Application DTO representing a topology connection.
 *
 */
package dz.sh.hidra.modules.topology.application.dto;

import java.time.Instant;

/**
 * Represents topology connection data returned by topology use cases.
 *
 * <p>Business role:
 * This DTO describes an explicit physical graph connection between two topology nodes.
 *
 * <p>Architecture role:
 * This application DTO is framework-independent and does not expose persistence entities or REST
 * response models.
 *
 * <p>Validation:
 * This DTO is an output projection. Connection validation is enforced by topology domain objects.
 *
 * <p>Usage:
 * Use this DTO as an application output and map it to REST responses in the API layer later.
 *
 * @param topologyConnectionId topology connection identifier
 * @param code business code
 * @param name display name
 * @param fromNodeId from-node identifier
 * @param toNodeId to-node identifier
 * @param connectionType connection type
 * @param linkedAssetType linked asset type
 * @param linkedAssetId linked asset identifier
 * @param status lifecycle status
 * @param createdAt creation instant
 * @param updatedAt last update instant
 */
public record TopologyConnectionDto(
        String topologyConnectionId,
        String code,
        String name,
        String fromNodeId,
        String toNodeId,
        String connectionType,
        String linkedAssetType,
        String linkedAssetId,
        String status,
        Instant createdAt,
        Instant updatedAt) {
}
