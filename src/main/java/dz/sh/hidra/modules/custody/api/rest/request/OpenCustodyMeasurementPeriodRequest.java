/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OpenCustodyMeasurementPeriodRequest
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.api.rest.request
 *
 * @Description : REST request to open custody measurement period.
 *
 */
package dz.sh.hidra.modules.custody.api.rest.request;

import java.time.Instant;

/**
 * REST request to open custody measurement period.
 */
public record OpenCustodyMeasurementPeriodRequest(
        String periodCode,
        String agreementId,
        String transferPointId,
        Instant periodStart,
        Instant periodEnd
) {
}
