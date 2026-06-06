/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RegisterEquipmentCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.command
 *
 * @Description : Application command for registering topology equipment.
 *
 */
package dz.sh.hidra.modules.topology.application.command;

import java.util.Objects;

import dz.sh.hidra.kernel.application.command.Command;
import dz.sh.hidra.modules.topology.domain.value.EquipmentType;
import dz.sh.hidra.modules.topology.domain.value.EquipmentTypeReference;
import dz.sh.hidra.modules.topology.domain.value.TopologyAssetType;
import dz.sh.hidra.modules.topology.domain.value.TopologyCode;
import dz.sh.hidra.modules.topology.domain.value.TopologyName;

/**
 * Carries input required to register topology equipment.
 */
public record RegisterEquipmentCommand(
        TopologyCode code,
        TopologyName name,
        EquipmentTypeReference equipmentType,
        TopologyAssetType parentAssetType,
        String parentAssetId) implements Command {

    public RegisterEquipmentCommand {
        Objects.requireNonNull(code, "Equipment code must not be null.");
        Objects.requireNonNull(name, "Equipment name must not be null.");
        Objects.requireNonNull(equipmentType, "Equipment type reference must not be null.");
        Objects.requireNonNull(parentAssetType, "Equipment parent asset type must not be null.");
        parentAssetId = normalizeRequired(parentAssetId, "Equipment parent asset id", 120);
        if (parentAssetType == TopologyAssetType.EQUIPMENT) {
            throw new IllegalArgumentException("Equipment must not use another equipment reference as its parent asset.");
        }
    }

    @Deprecated(forRemoval = true)
    public RegisterEquipmentCommand(
            TopologyCode code,
            TopologyName name,
            EquipmentType equipmentType,
            TopologyAssetType parentAssetType,
            String parentAssetId) {

        this(code, name, EquipmentTypeReference.from(equipmentType), parentAssetType, parentAssetId);
    }

    private static String normalizeRequired(String value, String label, int maxLength) {
        if (value == null || value.isBlank()) { throw new IllegalArgumentException(label + " must not be null or blank."); }
        String normalized = value.trim();
        if (normalized.length() > maxLength) { throw new IllegalArgumentException(label + " must not exceed " + maxLength + " characters."); }
        return normalized;
    }
}
