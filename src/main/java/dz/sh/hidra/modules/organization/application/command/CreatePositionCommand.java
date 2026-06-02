/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreatePositionCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.command
 *
 * @Description : Application command for creating an organization position.
 *
 */
package dz.sh.hidra.modules.organization.application.command;

import java.util.Objects;

import dz.sh.hidra.kernel.application.command.Command;
import dz.sh.hidra.modules.organization.domain.value.PositionCode;
import dz.sh.hidra.modules.organization.domain.value.PositionTitle;

/**
 * Carries input required to create an organization position.
 *
 * <p>Business role:
 * This command requests creation of an operational position or function such as Station Team
 * Leader, Station Boss, Region Director, Gas Flux Director, or Department Chief. It is not an
 * identity role.
 *
 * <p>Architecture role:
 * This is an application command used by organization use cases. It must not depend on REST DTOs,
 * persistence entities, Spring, JPA, identity, topology, platform, or infrastructure code.
 *
 * <p>Validation:
 * Position code and title are mandatory. Description is optional and bounded by this command.
 *
 * <p>Usage:
 * API mappers should translate REST position requests into this command.
 *
 * @param code unique position business code
 * @param title position display title
 * @param description optional business description
 */
public record CreatePositionCommand(
        PositionCode code,
        PositionTitle title,
        String description) implements Command {

    private static final int DESCRIPTION_MAX_LENGTH = 500;

    public CreatePositionCommand {
        Objects.requireNonNull(code, "Position code must not be null.");
        Objects.requireNonNull(title, "Position title must not be null.");
        description = normalizeOptional(description);
    }

    private static String normalizeOptional(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }

        String normalized = value.trim();
        if (normalized.length() > DESCRIPTION_MAX_LENGTH) {
            throw new IllegalArgumentException("Position description must not exceed 500 characters.");
        }
        return normalized;
    }
}
