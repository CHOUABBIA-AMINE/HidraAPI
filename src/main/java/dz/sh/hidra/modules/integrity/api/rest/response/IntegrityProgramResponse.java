/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityProgramResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.api.rest.response
 *
 * @Description : REST response for integrity program.
 *
 */
package dz.sh.hidra.modules.integrity.api.rest.response;

import dz.sh.hidra.modules.integrity.domain.value.IntegrityProgramStatus;

import java.time.Instant;

/**
 * REST response for integrity program.
 */
public record IntegrityProgramResponse(
        String id,
        String code,
        String nameFr,
        String programTypeId,
        IntegrityProgramStatus status,
        Instant plannedStartAt,
        Instant plannedEndAt
) {
}
