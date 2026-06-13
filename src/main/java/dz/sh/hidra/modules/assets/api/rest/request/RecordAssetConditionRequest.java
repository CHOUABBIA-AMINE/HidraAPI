/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RecordAssetConditionRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.api.rest.request
 *
 * @Description : REST request for record asset condition.
 *
 */
package dz.sh.hidra.modules.assets.api.rest.request;

import dz.sh.hidra.modules.assets.domain.value.AssetConditionStatus;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * REST request for record asset condition.
 */
public record RecordAssetConditionRequest(
        String maintainableAssetId,
        AssetConditionStatus conditionStatus,
        String conditionTypeId,
        String sourceModule,
        String sourceReferenceId,
        String summary,
        BigDecimal conditionScore,
        Instant observedAt,
        String observedByActorId
) {
}
