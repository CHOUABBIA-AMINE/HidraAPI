/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EquipmentAttributeDefinition
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.domain.model
 *
 * @Description : Type-specific equipment attribute definition.
 *
 */
package dz.sh.hidra.modules.topology.domain.model;

import dz.sh.hidra.modules.topology.domain.value.*;
import java.time.Instant;
public record EquipmentAttributeDefinition(
        String id,
        String equipmentTypeVersionId,
        String attributeCode,
        String label,
        AttributeDataType dataType,
        String unitCode,
        boolean required,
        TopologyStatus status,
        Instant createdAt,
        Instant updatedAt
) {
    public EquipmentAttributeDefinition {
        id = normalize(id);
        equipmentTypeVersionId = normalize(equipmentTypeVersionId);
        attributeCode = normalize(attributeCode);
        label = normalize(label);
        unitCode = normalize(unitCode);
    }
    private static String normalize(String value) { return value == null || value.isBlank() ? null : value.trim(); }
}
