/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : FacilityAttributeValue
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.model
 *
 * @Description : Facility attribute value.
 *
 */
package dz.sh.hidra.modules.topology.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
public record FacilityAttributeValue(
        String id,
        String facilityId,
        String attributeDefinitionId,
        String valueText,
        BigDecimal valueNumber,
        String valueJson,
        Instant validFrom,
        Instant validTo,
        Instant createdAt,
        Instant updatedAt
) {
    public FacilityAttributeValue {
        id = normalize(id);
        facilityId = normalize(facilityId);
        attributeDefinitionId = normalize(attributeDefinitionId);
        valueText = normalize(valueText);
        valueJson = normalize(valueJson);
    }
    private static String normalize(String value) { return value == null || value.isBlank() ? null : value.trim(); }
}
