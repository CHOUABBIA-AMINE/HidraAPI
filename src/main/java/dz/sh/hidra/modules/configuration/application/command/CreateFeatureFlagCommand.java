/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateFeatureFlagCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.application.command
 *
 * @Description : Command to create a feature flag.
 *
 */
package dz.sh.hidra.modules.configuration.application.command;

import dz.sh.hidra.modules.configuration.domain.exception.InvalidConfigurationValueException;
import dz.sh.hidra.modules.configuration.domain.value.FeatureFlagEvaluationStrategy;

/**
 * Command to create a feature flag.
 */
public record CreateFeatureFlagCommand(
        String code,
        String nameFr,
        String nameAr,
        String nameEn,
        String owningModule,
        FeatureFlagEvaluationStrategy evaluationStrategy,
        boolean defaultEnabled,
        String description
) {

    public CreateFeatureFlagCommand {
        if (owningModule == null || owningModule.isBlank()) {
            throw new InvalidConfigurationValueException(
                    "FeatureFlag owning module must not be blank."
            );
        }
        owningModule = owningModule.trim();
    }
}
