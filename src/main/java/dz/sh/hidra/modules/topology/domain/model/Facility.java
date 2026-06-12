/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : Facility
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
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
