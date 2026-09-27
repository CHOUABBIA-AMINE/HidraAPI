/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RegisterOperationalScopeCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.command
 *
 * @Description : Command to register one canonical operational-scope target.
 *
 */
package dz.sh.hidra.modules.organization.application.command;

import dz.sh.hidra.modules.organization.domain.value.OperationalScopeType;

import java.util.Objects;

/**
 * Registration request for a canonical operational scope.
 *
 * @param type requested scope category
 * @param targetId canonical target-owner identifier, null only for GLOBAL
 */
public record RegisterOperationalScopeCommand(
        OperationalScopeType type,
        String targetId
) {

    public RegisterOperationalScopeCommand {
        Objects.requireNonNull(type, "Operational scope type must not be null.");
        targetId = targetId == null || targetId.isBlank() ? null : targetId.trim();

        if (type == OperationalScopeType.GLOBAL && targetId != null) {
            throw new IllegalArgumentException("GLOBAL scope must not reference a target ID.");
        }

        if (type != OperationalScopeType.GLOBAL && targetId == null) {
            throw new IllegalArgumentException("Entity-backed scope requires a target ID.");
        }
    }
}
