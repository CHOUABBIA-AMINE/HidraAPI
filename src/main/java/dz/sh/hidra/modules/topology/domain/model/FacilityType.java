/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : FacilityType
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.model
 *
 * @Description : Catalog of facility types.
 *
 */
package dz.sh.hidra.modules.topology.domain.model;

import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException;
import dz.sh.hidra.modules.topology.domain.value.*;
import java.time.Instant;
public record FacilityType(
        String id,
        String code,
        String name,
        FacilityKind facilityKind,
        String description,
        TopologyStatus status,
        Instant createdAt,
        Instant updatedAt
) {
    public FacilityType {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidTopologyValueException("FacilityType id must not be blank.");
        }
        // HRA-051 required: code
        if (code == null || code.isBlank()) {
            throw new InvalidTopologyValueException("FacilityType code must not be blank.");
        }
        // HRA-051 required: facilityKind
        if (facilityKind == null) {
            throw new InvalidTopologyValueException("FacilityType facility kind must not be null.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidTopologyValueException("FacilityType status must not be null.");
        }

        id = normalize(id);
        code = normalize(code);
        name = normalize(name);
        description = normalize(description);
    }
    private static String normalize(String value) { return value == null || value.isBlank() ? null : value.trim(); }
}
