/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyMeasurementPeriodResponse
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.api.rest.response
 *
 * @Description : REST response for custody measurement period.
 *
 */
package dz.sh.hidra.modules.custody.api.rest.response;

import dz.sh.hidra.modules.custody.domain.value.CustodyPeriodStatus;

import java.time.Instant;

/**
 * REST response for custody measurement period.
 */
public record CustodyMeasurementPeriodResponse(
        String id,
        String periodCode,
        String agreementId,
        String transferPointId,
        Instant periodStart,
        Instant periodEnd,
        CustodyPeriodStatus status
) {
}
