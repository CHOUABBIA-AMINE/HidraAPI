/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineSystemFacility
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.model
 *
 * @Description : Association between system and facility.
 *
 */
package dz.sh.hidra.modules.topology.domain.model;

import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException;
import dz.sh.hidra.modules.topology.domain.value.*;
import java.time.Instant;
public record PipelineSystemFacility(
        String id,
        String pipelineSystemId,
        String facilityId,
        String relationshipCode,
        Instant validFrom,
        Instant validTo,
        TopologyStatus status,
        Instant createdAt,
        Instant updatedAt
) {
    public PipelineSystemFacility {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidTopologyValueException("PipelineSystemFacility id must not be blank.");
        }
        // HRA-051 required: pipelineSystemId
        if (pipelineSystemId == null || pipelineSystemId.isBlank()) {
            throw new InvalidTopologyValueException("PipelineSystemFacility pipeline system id must not be blank.");
        }
        // HRA-051 required: facilityId
        if (facilityId == null || facilityId.isBlank()) {
            throw new InvalidTopologyValueException("PipelineSystemFacility facility id must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidTopologyValueException("PipelineSystemFacility status must not be null.");
        }
        // HRA-051 order: validFrom <= validTo
        if (validFrom != null && validTo != null && validTo.isBefore(validFrom)) {
            throw new InvalidTopologyValueException("PipelineSystemFacility valid to must not be before valid from.");
        }

        id = normalize(id);
        pipelineSystemId = normalize(pipelineSystemId);
        facilityId = normalize(facilityId);
        relationshipCode = normalize(relationshipCode);
    }
    private static String normalize(String value) { return value == null || value.isBlank() ? null : value.trim(); }
}
