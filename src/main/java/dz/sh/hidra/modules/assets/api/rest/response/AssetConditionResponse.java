/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetConditionResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.api.rest.response
 *
 * @Description : REST response for asset condition.
 *
 */
package dz.sh.hidra.modules.assets.api.rest.response;

import dz.sh.hidra.modules.assets.domain.value.AssetConditionStatus;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * REST response for asset condition.
 */
public record AssetConditionResponse(
        String id,
        String maintainableAssetId,
        AssetConditionStatus conditionStatus,
        BigDecimal conditionScore,
        Instant observedAt
) {
}
