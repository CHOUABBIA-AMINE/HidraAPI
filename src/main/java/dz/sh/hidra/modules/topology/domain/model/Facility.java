/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : Facility
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.model
 *
 * @Description : Physical facility such as station, terminal, depot, pump/compressor station.
 *
 */
package dz.sh.hidra.modules.topology.domain.model;

import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException;
import dz.sh.hidra.modules.topology.domain.value.*;
import java.time.Instant;
import java.math.BigDecimal;
public record Facility(
        String id,
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        String facilityTypeId,
        FacilityKind facilityKind,
        String ownerPartyId,
        String ownerPartyCodeSnapshot,
        String ownerPartyNameSnapshot,
        BigDecimal latitude,
        BigDecimal longitude,
        BigDecimal elevationMeters,
        FacilityStatus status,
        Instant commissionedAt,
        Instant retiredAt,
        Instant createdAt,
        Instant updatedAt
) {
    public Facility {
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidTopologyValueException("Facility id must not be blank.");
        }
        // HRA-051 required: code
        if (code == null || code.isBlank()) {
            throw new InvalidTopologyValueException("Facility code must not be blank.");
        }
        // HRA-051 required: facilityTypeId
        if (facilityTypeId == null || facilityTypeId.isBlank()) {
            throw new InvalidTopologyValueException("Facility facility type id must not be blank.");
        }
        // HRA-051 required: facilityKind
        if (facilityKind == null) {
            throw new InvalidTopologyValueException("Facility facility kind must not be null.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidTopologyValueException("Facility status must not be null.");
        }

        id = normalize(id);
        code = normalize(code);
        nameAr = normalize(nameAr);
        nameFr = normalize(nameFr);
        nameEn = normalize(nameEn);
        facilityTypeId = normalize(facilityTypeId);
        ownerPartyId = normalize(ownerPartyId);
        ownerPartyCodeSnapshot = normalize(ownerPartyCodeSnapshot);
        ownerPartyNameSnapshot = normalize(ownerPartyNameSnapshot);
    }
    private static String normalize(String value) { return value == null || value.isBlank() ? null : value.trim(); }
}
