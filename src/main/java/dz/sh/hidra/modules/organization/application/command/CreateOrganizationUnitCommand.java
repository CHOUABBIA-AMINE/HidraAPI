/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateOrganizationUnitCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.command
 *
 * @Description : Application command for creating an organization unit.
 *
 */
package dz.sh.hidra.modules.organization.application.command;

import java.util.Objects;

import dz.sh.hidra.kernel.application.command.Command;
import dz.sh.hidra.modules.organization.domain.value.OperationalScopeType;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitCode;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitId;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitName;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitType;

/**
 * Carries input required to create an organization unit.
 *
 * <p>Business role:
 * This command creates an operational organization unit such as a division, department, region,
 * station-as-organization-unit, or team. A physical station asset remains owned by topology.
 *
 * <p>Architecture role:
 * This is an application command. It may carry neutral operational scope fields but must not
 * import topology domain classes or persistence/API types.
 *
 * <p>Validation:
 * Code, name, and unit type are mandatory. Parent id and operational scope fields are optional.
 * Scope fields are neutral strings and are normalized by this command when present.
 *
 * <p>Usage:
 * Use this command when API or orchestration code requests creation of an organization unit.
 *
 * @param code organization unit business code
 * @param name organization unit display name
 * @param type organization unit type
 * @param parentId optional parent organization unit identifier
 * @param operationalScopeType optional neutral operational scope type
 * @param operationalScopeId optional neutral operational scope identifier
 * @param operationalScopeCode optional neutral operational scope business code
 * @param operationalScopeName optional neutral operational scope display name
 */
public record CreateOrganizationUnitCommand(
        OrganizationUnitCode code,
        OrganizationUnitName name,
        OrganizationUnitType type,
        OrganizationUnitId parentId,
        OperationalScopeType operationalScopeType,
        String operationalScopeId,
        String operationalScopeCode,
        String operationalScopeName) implements Command {

    public CreateOrganizationUnitCommand {
        Objects.requireNonNull(code, "Organization unit code must not be null.");
        Objects.requireNonNull(name, "Organization unit name must not be null.");
        Objects.requireNonNull(type, "Organization unit type must not be null.");

        operationalScopeId = normalizeOptional(operationalScopeId, "Operational scope id", 120);
        operationalScopeCode = normalizeOptional(operationalScopeCode, "Operational scope code", 120);
        operationalScopeName = normalizeOptional(operationalScopeName, "Operational scope name", 160);
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
