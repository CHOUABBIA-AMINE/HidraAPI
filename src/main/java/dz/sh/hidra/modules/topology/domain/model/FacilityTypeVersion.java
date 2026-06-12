/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : FacilityTypeVersion
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
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
        id = normalize(id);
        facilityTypeId = normalize(facilityTypeId);
        definitionPayload = normalize(definitionPayload);
    }
    private static String normalize(String value) { return value == null || value.isBlank() ? null : value.trim(); }
}
