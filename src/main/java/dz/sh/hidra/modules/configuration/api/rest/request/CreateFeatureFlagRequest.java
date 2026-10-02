/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateFeatureFlagRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.api.rest.request
 *
 * @Description : REST request for create feature flag.
 *
 */
package dz.sh.hidra.modules.configuration.api.rest.request;

import dz.sh.hidra.modules.configuration.domain.value.FeatureFlagEvaluationStrategy;

/**
 * REST request for create feature flag.
 */
public record CreateFeatureFlagRequest(
        String code,
        String nameFr,
        String nameAr,
        String nameEn,
        String owningModule,
        FeatureFlagEvaluationStrategy evaluationStrategy,
        boolean defaultEnabled,
        String description
) {
}
