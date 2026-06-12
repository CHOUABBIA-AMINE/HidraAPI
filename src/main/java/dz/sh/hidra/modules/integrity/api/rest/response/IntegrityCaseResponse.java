/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityCaseResponse
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.api.rest.response
 *
 * @Description : REST response for integrity case.
 *
 */
package dz.sh.hidra.modules.integrity.api.rest.response;

import dz.sh.hidra.modules.integrity.domain.value.IntegrityCaseStatus;

import java.time.Instant;

/**
 * REST response for integrity case.
 */
public record IntegrityCaseResponse(
        String id,
        String caseNumber,
        String title,
        String caseTypeId,
        IntegrityCaseStatus status,
        String topologyAssetTypeCode,
        String topologyAssetId,
        String primaryDefectId,
        Instant openedAt,
        Instant closedAt
) {
}
