/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseCapaResponse
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.api.rest.response
 *
 * @Description : REST response for HSE CAPA.
 *
 */
package dz.sh.hidra.modules.hse.api.rest.response;

import dz.sh.hidra.modules.hse.domain.value.CapaStatus;

import java.time.Instant;

/**
 * REST response for HSE CAPA.
 */
public record HseCapaResponse(
        String id,
        String hseCaseId,
        String actionNumber,
        String title,
        CapaStatus status,
        Instant targetDate,
        Instant completedAt
) {
}
