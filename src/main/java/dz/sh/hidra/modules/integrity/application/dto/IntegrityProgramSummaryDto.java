/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityProgramSummaryDto
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.application.dto
 *
 * @Description : Integrity program summary DTO.
 *
 */
package dz.sh.hidra.modules.integrity.application.dto;

import dz.sh.hidra.modules.integrity.domain.value.IntegrityProgramStatus;

import java.time.Instant;

/**
 * Integrity program summary DTO.
 */
public record IntegrityProgramSummaryDto(
        String id,
        String code,
        String nameFr,
        String programTypeId,
        IntegrityProgramStatus status,
        Instant plannedStartAt,
        Instant plannedEndAt
) {
}
