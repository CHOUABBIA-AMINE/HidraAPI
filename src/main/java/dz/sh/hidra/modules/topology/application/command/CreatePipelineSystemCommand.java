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
import dz.sh.hidra.modules.topology.domain.value.ProductType;
import dz.sh.hidra.modules.topology.domain.value.TopologyCode;
import dz.sh.hidra.modules.topology.domain.value.TopologyName;

/**
 * Carries input required to create a pipeline system.
 *
 * <p>Business role:
 * This command creates a physical hydrocarbon transportation system that groups pipelines.
 *
 * <p>Architecture role:
 * This is an application command. It must not depend on API, persistence, Spring, JPA, identity,
 * organization implementation, measurement, flow, risk, workflow, or infrastructure code.
 *
 * <p>Validation:
 * Code, name, and product type are mandatory. Description and operational owner reference are
 * optional.
 *
 * <p>Usage:
 * Use this command when API or orchestration code requests creation of a pipeline system.
 *
 * @param code business code
 * @param name display name
 * @param description optional description
 * @param productType hydrocarbon product type
 * @param operationalOwnerReference optional neutral operational owner reference
 */
public record CreatePipelineSystemCommand(
        TopologyCode code,
        TopologyName name,
        String description,
        ProductType productType,
        OperationalOwnerReference operationalOwnerReference) implements Command {

    public CreatePipelineSystemCommand {
        Objects.requireNonNull(code, "Pipeline system code must not be null.");
        Objects.requireNonNull(name, "Pipeline system name must not be null.");
        Objects.requireNonNull(productType, "Pipeline system product type must not be null.");
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
