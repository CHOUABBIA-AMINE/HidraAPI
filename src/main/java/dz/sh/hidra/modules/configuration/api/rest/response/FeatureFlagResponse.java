/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : FeatureFlagResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.api.rest.response
 *
 * @Description : REST response for feature flag.
 *
 */
package dz.sh.hidra.modules.configuration.api.rest.response;

import dz.sh.hidra.modules.configuration.domain.value.FeatureFlagEvaluationStrategy;
import dz.sh.hidra.modules.configuration.domain.value.FeatureFlagStatus;

/**
 * REST response for feature flag.
 */
public record FeatureFlagResponse(
        String id,
        String code,
        String nameFr,
        String owningModule,
        FeatureFlagStatus status,
        FeatureFlagEvaluationStrategy evaluationStrategy,
        boolean defaultEnabled
) {
}
