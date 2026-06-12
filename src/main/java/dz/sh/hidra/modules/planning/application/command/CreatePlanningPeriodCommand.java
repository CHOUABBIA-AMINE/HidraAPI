/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreatePlanningPeriodCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.application.command
 *
 * @Description : Command to create planning period.
 *
 */
package dz.sh.hidra.modules.planning.application.command;

import java.time.Instant;

/**
 * Command to create planning period.
 */
public record CreatePlanningPeriodCommand(
        String code,
        String nameAr,
        String nameFr,
        String nameEn,
        String periodTypeId,
        Instant periodStart,
        Instant periodEnd,
        String timeZone,
        String createdByActorId
) {
}
