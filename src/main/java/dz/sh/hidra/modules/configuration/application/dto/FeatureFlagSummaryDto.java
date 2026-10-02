/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : FeatureFlagSummaryDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.application.dto
 *
 * @Description : Feature flag summary DTO.
 *
 */
package dz.sh.hidra.modules.configuration.application.dto;

import dz.sh.hidra.modules.configuration.domain.value.FeatureFlagEvaluationStrategy;
import dz.sh.hidra.modules.configuration.domain.value.FeatureFlagStatus;

/**
 * Feature flag summary DTO.
 */
public record FeatureFlagSummaryDto(
        String id,
        String code,
        String nameFr,
        String owningModule,
        FeatureFlagStatus status,
        FeatureFlagEvaluationStrategy evaluationStrategy,
        boolean defaultEnabled
) {
}
