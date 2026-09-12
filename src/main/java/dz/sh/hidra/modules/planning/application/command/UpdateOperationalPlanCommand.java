/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : UpdateOperationalPlanCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-12
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.application.command
 *
 * @Description : Command for updating mutable operational-plan metadata with an explicit concurrency precondition.
 *
 */
package dz.sh.hidra.modules.planning.application.command;

import java.time.Instant;

/**
 * Updates operational-plan presentation and ownership metadata using the current backend token.
 */
public record UpdateOperationalPlanCommand(
        String id,
        Instant expectedUpdatedAt,
        String nameAr,
        String nameFr,
        String nameEn,
        String responsibleOrganizationUnitId
) {
}
