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
import dz.sh.hidra.modules.topology.domain.value.TopologyAssetType;
import dz.sh.hidra.modules.topology.domain.value.TopologyCode;
import dz.sh.hidra.modules.topology.domain.value.TopologyName;

/**
 * Carries input required to register topology equipment.
 *
 * <p>Business role:
 * This command registers optional physical equipment/component references attached to topology
 * assets. Detailed maintenance, inspection, reliability, telemetry, and workflow behavior belong to
 * future modules.
 *
 * <p>Architecture role:
 * This is an application command and does not depend on REST, JPA, repositories, identity, or
 * organization implementation.
 *
 * <p>Validation:
 * Code, name, equipment type, parent asset type, and parent asset id are mandatory.
 *
 * @param code business code
 * @param name display name
 * @param equipmentType equipment type
 * @param parentAssetType parent topology asset type
 * @param parentAssetId parent topology asset identifier
 */
public record RegisterEquipmentCommand(
        TopologyCode code,
        TopologyName name,
        EquipmentType equipmentType,
        TopologyAssetType parentAssetType,
        String parentAssetId) implements Command {

    public RegisterEquipmentCommand {
        Objects.requireNonNull(code, "Equipment code must not be null.");
        Objects.requireNonNull(name, "Equipment name must not be null.");
        Objects.requireNonNull(equipmentType, "Equipment type must not be null.");
        Objects.requireNonNull(parentAssetType, "Equipment parent asset type must not be null.");
        parentAssetId = normalizeRequired(parentAssetId, "Equipment parent asset id", 120);

        if (parentAssetType == TopologyAssetType.EQUIPMENT) {
            throw new IllegalArgumentException("Equipment must not use another equipment reference as its parent asset.");
        }
    }

    private static String normalizeRequired(String value, String label, int maxLength) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(label + " must not be null or blank.");
        }

        String normalized = value.trim();
        if (normalized.length() > maxLength) {
            throw new IllegalArgumentException(label + " must not exceed " + maxLength + " characters.");
        }
        return normalized;
    }
}
