/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateIntegrityProgramCommand
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.application.command
 *
 * @Description : Command to create integrity program.
 *
 */
package dz.sh.hidra.modules.integrity.application.command;

import java.time.Instant;

/**
 * Command to create integrity program.
 */
public record CreateIntegrityProgramCommand(
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        String description,
        String programTypeId,
        String ownerOrganizationUnitId,
        String ownerOrganizationUnitNameSnapshot,
        Instant plannedStartAt,
        Instant plannedEndAt,
        String createdByActorId
) {
}
