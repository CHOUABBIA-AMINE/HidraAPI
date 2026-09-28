/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : FacilityTypeVersion
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.model
 *
 * @Description : Versioned facility-type definition.
 *
 */
package dz.sh.hidra.modules.topology.domain.model;

import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException;
import dz.sh.hidra.modules.topology.domain.value.*;
import java.time.Instant;
public record FacilityTypeVersion(
        String id,
        String facilityTypeId,
        int versionNumber,
        String definitionPayload,
        SnapshotStatus status,
        Instant effectiveFrom,
        Instant effectiveTo,
        Instant createdAt,
        Instant updatedAt
) {
    public FacilityTypeVersion {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidTopologyValueException("FacilityTypeVersion id must not be blank.");
        }
        // HRA-051 required: facilityTypeId
        if (facilityTypeId == null || facilityTypeId.isBlank()) {
            throw new InvalidTopologyValueException("FacilityTypeVersion facility type id must not be blank.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidTopologyValueException("FacilityTypeVersion status must not be null.");
        }
        // HRA-051 order: effectiveFrom <= effectiveTo
        if (effectiveFrom != null && effectiveTo != null && effectiveTo.isBefore(effectiveFrom)) {
            throw new InvalidTopologyValueException("FacilityTypeVersion effective to must not be before effective from.");
        }

        id = normalize(id);
        facilityTypeId = normalize(facilityTypeId);
        definitionPayload = normalize(definitionPayload);
    }
    private static String normalize(String value) { return value == null || value.isBlank() ? null : value.trim(); }
}
