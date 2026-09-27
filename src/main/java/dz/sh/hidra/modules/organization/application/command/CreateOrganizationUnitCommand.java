/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateOrganizationUnitCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-27
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.command
 *
 * @Description : Command to create an organization unit with a canonical stable business code.
 *
 */
package dz.sh.hidra.modules.organization.application.command;

import dz.sh.hidra.modules.organization.domain.value.OrganizationCode;
import dz.sh.hidra.modules.organization.domain.value.OrganizationUnitStatus;

import java.time.Instant;
import java.util.Objects;

/**
 * Command to create an organization unit.
 *
 * <p>Business role: carries the language-neutral unit code and multilingual display
 * state required to create an OrganizationUnit.</p>
 *
 * <p>Architecture role: application input that adopts {@link OrganizationCode} at the
 * active write boundary while keeping REST representation independent and textual.</p>
 *
 * <p>Validation: the code is validated/normalized by OrganizationCode; other existing
 * command fields retain their established validation behavior.</p>
 *
 * <p>Usage: REST mapping should construct the code value object explicitly. A
 * deprecated textual constructor remains for current internal compatibility.</p>
 *
 * @param code canonical language-neutral organization code
 * @param nameAr Arabic unit name
 * @param nameFr French unit name
 * @param nameEn English unit name
 * @param unitTypeId organization-unit-type identifier
 * @param parentUnitId optional parent organization-unit identifier
 * @param status lifecycle status
 * @param validFrom optional effective start
 */
public record CreateOrganizationUnitCommand(
        OrganizationCode code,
        String nameAr,
        String nameFr,
        String nameEn,
        String unitTypeId,
        String parentUnitId,
        OrganizationUnitStatus status,
        Instant validFrom
) {

    public CreateOrganizationUnitCommand {
        Objects.requireNonNull(code, "Organization code must not be null.");
    }

    /**
     * Transitional constructor for textual callers.
     */
    @Deprecated(forRemoval = true)
    public CreateOrganizationUnitCommand(
            String code,
            String nameAr,
            String nameFr,
            String nameEn,
            String unitTypeId,
            String parentUnitId,
            OrganizationUnitStatus status,
            Instant validFrom
    ) {
        this(
                OrganizationCode.of(code),
                nameAr,
                nameFr,
                nameEn,
                unitTypeId,
                parentUnitId,
                status,
                validFrom
        );
    }
}
