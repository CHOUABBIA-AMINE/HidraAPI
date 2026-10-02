/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PlanningPeriodSummaryDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : planning
 * @Package     : dz.sh.hidra.modules.planning.application.dto
 *
 * @Description : Planning period summary DTO.
 *
 */
package dz.sh.hidra.modules.planning.application.dto;

import dz.sh.hidra.modules.planning.domain.value.PlanningPeriodStatus;

import java.time.Instant;

/**
 * Planning period summary DTO.
 */
public record PlanningPeriodSummaryDto(
        String id,
        String code,
        String nameFr,
        Instant periodStart,
        Instant periodEnd,
        String timeZone,
        PlanningPeriodStatus status
) {
}
