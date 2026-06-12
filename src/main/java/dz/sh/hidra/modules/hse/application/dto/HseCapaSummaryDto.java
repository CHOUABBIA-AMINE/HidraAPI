/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseCapaSummaryDto
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.application.dto
 *
 * @Description : HSE CAPA summary DTO.
 *
 */
package dz.sh.hidra.modules.hse.application.dto;

import dz.sh.hidra.modules.hse.domain.value.CapaStatus;

import java.time.Instant;

/**
 * HSE CAPA summary DTO.
 */
public record HseCapaSummaryDto(
        String id,
        String hseCaseId,
        String actionNumber,
        String title,
        CapaStatus status,
        Instant targetDate,
        Instant completedAt
) {
}
