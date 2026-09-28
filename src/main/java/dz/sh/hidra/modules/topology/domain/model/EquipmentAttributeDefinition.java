/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EquipmentAttributeDefinition
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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

import dz.sh.hidra.modules.topology.domain.exception.InvalidTopologyValueException;
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
        // HRA-051 required: id
        if (id == null || id.isBlank()) {
            throw new InvalidTopologyValueException("EquipmentAttributeDefinition id must not be blank.");
        }
        // HRA-051 required: equipmentTypeVersionId
        if (equipmentTypeVersionId == null || equipmentTypeVersionId.isBlank()) {
            throw new InvalidTopologyValueException("EquipmentAttributeDefinition equipment type version id must not be blank.");
        }
        // HRA-051 required: attributeCode
        if (attributeCode == null || attributeCode.isBlank()) {
            throw new InvalidTopologyValueException("EquipmentAttributeDefinition attribute code must not be blank.");
        }
        // HRA-051 required: dataType
        if (dataType == null) {
            throw new InvalidTopologyValueException("EquipmentAttributeDefinition data type must not be null.");
        }
        // HRA-051 required: status
        if (status == null) {
            throw new InvalidTopologyValueException("EquipmentAttributeDefinition status must not be null.");
        }

        id = normalize(id);
        equipmentTypeVersionId = normalize(equipmentTypeVersionId);
        attributeCode = normalize(attributeCode);
        label = normalize(label);
        unitCode = normalize(unitCode);
    }
    private static String normalize(String value) { return value == null || value.isBlank() ? null : value.trim(); }
}
