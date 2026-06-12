/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseCaseResponse
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.api.rest.response
 *
 * @Description : REST response for HSE case.
 *
 */
package dz.sh.hidra.modules.hse.api.rest.response;

import dz.sh.hidra.modules.hse.domain.value.HseCaseSourceType;
import dz.sh.hidra.modules.hse.domain.value.HseCaseStatus;

import java.time.Instant;

/**
 * REST response for HSE case.
 */
public record HseCaseResponse(
        String id,
        String caseNumber,
        String title,
        String caseTypeId,
        String severityId,
        HseCaseStatus status,
        HseCaseSourceType sourceType,
        String incidentReferenceId,
        String targetModule,
        String targetTypeCode,
        String targetId,
        Instant reportedAt,
        Instant closedAt
) {
}
