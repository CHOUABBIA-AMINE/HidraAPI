/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : Equipment
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.model
 *
 * @Description : Equipment as part of the topology/network.
 *
 */
package dz.sh.hidra.modules.topology.domain.model;

import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException;
import dz.sh.hidra.modules.topology.domain.value.*;
import java.time.Instant;
public record Equipment(
        String id,
        String code,
        String name,
        String facilityId,
        String nodeId,
        String pipelineSegmentId,
        String equipmentTypeId,
        String manufacturerPartyId,
        String manufacturerPartyCodeSnapshot,
        String manufacturerPartyNameSnapshot,
        EquipmentStatus status,
        Instant installedAt,
        Instant retiredAt,
        Instant createdAt,
        Instant updatedAt
) {
    public Equipment {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidTopologyValueException("Equipment id must not be blank.");
        }
        // HRA-051 required: code
        if (code == null || code.isBlank()) {
            throw new InvalidTopologyValueException("Equipment code must not be blank.");
        }
        // HRA-051 required: equipmentTypeId
        if (equipmentTypeId == null || equipmentTypeId.isBlank()) {
            throw new InvalidTopologyValueException("Equipment equipment type id must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidTopologyValueException("Equipment status must not be null.");
        }

        id = normalize(id);
        code = normalize(code);
        name = normalize(name);
        facilityId = normalize(facilityId);
        nodeId = normalize(nodeId);
        pipelineSegmentId = normalize(pipelineSegmentId);
        equipmentTypeId = normalize(equipmentTypeId);
        manufacturerPartyId = normalize(manufacturerPartyId);
        manufacturerPartyCodeSnapshot = normalize(manufacturerPartyCodeSnapshot);
        manufacturerPartyNameSnapshot = normalize(manufacturerPartyNameSnapshot);
    }
    private static String normalize(String value) { return value == null || value.isBlank() ? null : value.trim(); }
}
