/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationApplicationMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.application.mapper
 *
 * @Description : Maps configuration domain models to DTOs.
 *
 */
package dz.sh.hidra.modules.configuration.application.mapper;

import dz.sh.hidra.modules.configuration.application.dto.ConfigurationDefinitionSummaryDto;
import dz.sh.hidra.modules.configuration.application.dto.ConfigurationValueSummaryDto;
import dz.sh.hidra.modules.configuration.application.dto.FeatureFlagSummaryDto;
import dz.sh.hidra.modules.configuration.domain.model.ConfigurationDefinition;
import dz.sh.hidra.modules.configuration.domain.model.ConfigurationValue;
import dz.sh.hidra.modules.configuration.domain.model.FeatureFlag;

/**
 * Maps configuration domain models to DTOs.
 */
public final class ConfigurationApplicationMapper {

    private ConfigurationApplicationMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static ConfigurationDefinitionSummaryDto toSummary(ConfigurationDefinition definition) {
        return new ConfigurationDefinitionSummaryDto(definition.id(), definition.namespaceId(), definition.key(), definition.displayNameFr(), definition.valueType(), definition.sensitivity(), definition.status(), definition.scoped(), definition.requiresApproval());
    }

    public static ConfigurationValueSummaryDto toSummary(ConfigurationValue value) {
        return new ConfigurationValueSummaryDto(value.id(), value.definitionId(), value.definitionVersionId(), value.environment(), value.status(), value.effectiveFrom(), value.effectiveTo());
    }

    public static FeatureFlagSummaryDto toSummary(FeatureFlag featureFlag) {
        return new FeatureFlagSummaryDto(featureFlag.id(), featureFlag.code(), featureFlag.nameFr(), featureFlag.owningModule(), featureFlag.status(), featureFlag.evaluationStrategy(), featureFlag.defaultEnabled());
    }
}
