/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EquipmentAttributeValue
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.model
 *
 * @Description : Equipment attribute value.
 *
 */
package dz.sh.hidra.modules.topology.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
public record EquipmentAttributeValue(
        String id,
        String equipmentId,
        String attributeDefinitionId,
        String valueText,
        BigDecimal valueNumber,
        String valueJson,
        Instant validFrom,
        Instant validTo,
        Instant createdAt,
        Instant updatedAt
) {
    public EquipmentAttributeValue {
        id = normalize(id);
        equipmentId = normalize(equipmentId);
        attributeDefinitionId = normalize(attributeDefinitionId);
        valueText = normalize(valueText);
        valueJson = normalize(valueJson);
    }
    private static String normalize(String value) { return value == null || value.isBlank() ? null : value.trim(); }
}
