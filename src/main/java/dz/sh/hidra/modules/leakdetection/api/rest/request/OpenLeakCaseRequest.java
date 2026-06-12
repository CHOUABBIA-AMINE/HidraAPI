/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OpenLeakCaseRequest
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.api.rest.request
 *
 * @Description : REST request to open leak case.
 *
 */
package dz.sh.hidra.modules.leakdetection.api.rest.request;

import java.math.BigDecimal;

/**
 * REST request to open leak case.
 */
public record OpenLeakCaseRequest(
        String caseNumber,
        String primaryCandidateId,
        String topologyAssetType,
        String topologyAssetId,
        String topologyAssetCode,
        String owningOrganizationUnitId,
        BigDecimal confidenceScore,
        String openedByActorId,
        String correlationId
) {
}
