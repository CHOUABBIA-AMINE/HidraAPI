/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssetConditionSummaryDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.application.dto
 *
 * @Description : Asset condition summary DTO.
 *
 */
package dz.sh.hidra.modules.assets.application.dto;

import dz.sh.hidra.modules.assets.domain.value.AssetConditionStatus;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Asset condition summary DTO.
 */
public record AssetConditionSummaryDto(
        String id,
        String maintainableAssetId,
        AssetConditionStatus conditionStatus,
        BigDecimal conditionScore,
        Instant observedAt
) {
}
