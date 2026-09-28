/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : FacilityAttributeValue
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidTopologyValueException("FacilityAttributeValue id must not be blank.");
        }
        // HRA-051 required: facilityId
        if (facilityId == null || facilityId.isBlank()) {
            throw new InvalidTopologyValueException("FacilityAttributeValue facility id must not be blank.");
        }
        // HRA-051 required: attributeDefinitionId
        if (attributeDefinitionId == null || attributeDefinitionId.isBlank()) {
            throw new InvalidTopologyValueException("FacilityAttributeValue attribute definition id must not be blank.");
        }
        // HRA-051 order: validFrom <= validTo
        if (validFrom != null && validTo != null && validTo.isBefore(validFrom)) {
            throw new InvalidTopologyValueException("FacilityAttributeValue valid to must not be before valid from.");
        }

        id = normalize(id);
        facilityId = normalize(facilityId);
        attributeDefinitionId = normalize(attributeDefinitionId);
        valueText = normalize(valueText);
        valueJson = normalize(valueJson);
    }
    private static String normalize(String value) { return value == null || value.isBlank() ? null : value.trim(); }
}
