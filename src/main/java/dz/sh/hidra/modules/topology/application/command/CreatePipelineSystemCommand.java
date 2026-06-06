/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreatePipelineSystemCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.command
 *
 * @Description : Application command for creating a pipeline system.
 *
 */
package dz.sh.hidra.modules.topology.application.command;

import java.util.Objects;

import dz.sh.hidra.kernel.application.command.Command;
import dz.sh.hidra.modules.topology.domain.value.OperationalOwnerReference;
import dz.sh.hidra.modules.topology.domain.value.ProductTypeReference;
import dz.sh.hidra.modules.topology.domain.value.TopologyCode;
import dz.sh.hidra.modules.topology.domain.value.TopologyName;

/**
 * Carries input required to create a pipeline system.
 *
 * <p>Business role:
 * This command creates a physical hydrocarbon transportation system that groups pipelines.
 *
 * <p>Architecture role:
 * This command carries product type as a catalog reference.
 *
 * <p>Validation:
 * Code, name, and product type reference are mandatory. Description and operational owner reference
 * are optional.
 */
public record CreatePipelineSystemCommand(
        TopologyCode code,
        TopologyName name,
        String description,
        ProductTypeReference productType,
        OperationalOwnerReference operationalOwnerReference) implements Command {

    public CreatePipelineSystemCommand {
        Objects.requireNonNull(code, "Pipeline system code must not be null.");
        Objects.requireNonNull(name, "Pipeline system name must not be null.");
        Objects.requireNonNull(productType, "Pipeline system product type reference must not be null.");
        description = normalizeOptional(description, "Pipeline system description", 500);
    }

    private static String normalizeOptional(String value, String label, int maxLength) {
        if (value == null || value.isBlank()) {
            return null;
        }
        String normalized = value.trim();
        if (normalized.length() > maxLength) {
            throw new IllegalArgumentException(label + " must not exceed " + maxLength + " characters.");
        }
        return normalized;
    }
}
