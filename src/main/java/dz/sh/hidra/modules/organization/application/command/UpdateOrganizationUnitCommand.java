/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : UpdateOrganizationUnitCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.command
 *
 * @Description : Application command for updating an organization unit.
 *
 */
package dz.sh.hidra.modules.organization.application.command;

import java.util.Objects;

import dz.sh.hidra.kernel.application.command.Command;
import dz.sh.hidra.modules.organization.domain.value.OperationalScopeType;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitId;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitName;

/**
 * Carries input required to update an organization unit.
 *
 * <p>Business role:
 * This command updates an organization unit name, hierarchy parent, or neutral operational scope
 * reference. It does not update a physical topology station asset.
 *
 * <p>Architecture role:
 * This is an application command used by organization use cases. It must not depend on REST DTOs,
 * persistence entities, Spring, JPA, identity, topology, platform, or infrastructure code.
 *
 * <p>Validation:
 * Organization unit id and name are mandatory. Parent id and operational scope fields are optional
 * and normalized when present.
 *
 * <p>Usage:
 * Use this command when changing an organization unit while keeping topology references neutral.
 *
 * @param organizationUnitId organization unit identifier
 * @param name new organization unit name
 * @param parentId optional parent organization unit identifier
 * @param operationalScopeType optional neutral operational scope type
 * @param operationalScopeId optional neutral operational scope identifier
 * @param operationalScopeCode optional neutral operational scope business code
 * @param operationalScopeName optional neutral operational scope display name
 */
public record UpdateOrganizationUnitCommand(
        OrganizationUnitId organizationUnitId,
        OrganizationUnitName name,
        OrganizationUnitId parentId,
        OperationalScopeType operationalScopeType,
        String operationalScopeId,
        String operationalScopeCode,
        String operationalScopeName) implements Command {

    public UpdateOrganizationUnitCommand {
        Objects.requireNonNull(organizationUnitId, "Organization unit id must not be null.");
        Objects.requireNonNull(name, "Organization unit name must not be null.");

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
