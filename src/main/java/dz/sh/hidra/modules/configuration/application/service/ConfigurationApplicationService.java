/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.application.service
 *
 * @Description : Application service for configuration definitions, values, and feature flags.
 *
 */
package dz.sh.hidra.modules.configuration.application.service;

import dz.sh.hidra.modules.configuration.application.command.CreateConfigurationDefinitionCommand;
import dz.sh.hidra.modules.configuration.application.command.CreateFeatureFlagCommand;
import dz.sh.hidra.modules.configuration.application.command.SetConfigurationValueCommand;
import dz.sh.hidra.modules.configuration.application.dto.ConfigurationDefinitionSummaryDto;
import dz.sh.hidra.modules.configuration.application.dto.ConfigurationValueSummaryDto;
import dz.sh.hidra.modules.configuration.application.dto.FeatureFlagSummaryDto;
import dz.sh.hidra.modules.configuration.application.mapper.ConfigurationApplicationMapper;
import dz.sh.hidra.modules.configuration.application.port.in.CreateConfigurationDefinitionUseCase;
import dz.sh.hidra.modules.configuration.application.port.in.CreateFeatureFlagUseCase;
import dz.sh.hidra.modules.configuration.application.port.in.SetConfigurationValueUseCase;
import dz.sh.hidra.modules.configuration.application.port.out.ConfigurationDefinitionRepositoryPort;
import dz.sh.hidra.modules.configuration.application.port.out.ConfigurationValueRepositoryPort;
import dz.sh.hidra.modules.configuration.application.port.out.FeatureFlagRepositoryPort;
import dz.sh.hidra.modules.configuration.domain.model.ConfigurationDefinition;
import dz.sh.hidra.modules.configuration.domain.model.ConfigurationValue;
import dz.sh.hidra.modules.configuration.domain.model.FeatureFlag;
import dz.sh.hidra.modules.configuration.domain.service.ConfigurationValueGuard;
import dz.sh.hidra.modules.configuration.domain.value.ConfigurationDefinitionStatus;
import dz.sh.hidra.modules.configuration.domain.value.ConfigurationId;
import dz.sh.hidra.modules.configuration.domain.value.ConfigurationValueStatus;
import dz.sh.hidra.modules.configuration.domain.value.FeatureFlagStatus;

import java.time.Instant;
import java.util.Objects;

/**
 * Application service for configuration definitions, values, and feature flags.
 */
public final class ConfigurationApplicationService implements CreateConfigurationDefinitionUseCase, SetConfigurationValueUseCase, CreateFeatureFlagUseCase {

    private final ConfigurationDefinitionRepositoryPort definitionRepositoryPort;
    private final ConfigurationValueRepositoryPort valueRepositoryPort;
    private final FeatureFlagRepositoryPort featureFlagRepositoryPort;
    private final ConfigurationValueGuard valueGuard = new ConfigurationValueGuard();

    public ConfigurationApplicationService(
            ConfigurationDefinitionRepositoryPort definitionRepositoryPort,
            ConfigurationValueRepositoryPort valueRepositoryPort,
            FeatureFlagRepositoryPort featureFlagRepositoryPort
    ) {
        this.definitionRepositoryPort = Objects.requireNonNull(definitionRepositoryPort, "Configuration definition repository port must not be null.");
        this.valueRepositoryPort = Objects.requireNonNull(valueRepositoryPort, "Configuration value repository port must not be null.");
        this.featureFlagRepositoryPort = Objects.requireNonNull(featureFlagRepositoryPort, "Feature flag repository port must not be null.");
    }

    @Override
    public ConfigurationDefinitionSummaryDto createConfigurationDefinition(CreateConfigurationDefinitionCommand command) {
        Objects.requireNonNull(command, "Create configuration definition command must not be null.");
        valueGuard.ensureAllowedDefinition(command.key());
        Instant now = Instant.now();
        ConfigurationDefinition definition = new ConfigurationDefinition(
                ConfigurationId.newId().value(),
                command.namespaceId(),
                command.key(),
                command.displayNameFr(),
                command.displayNameAr(),
                command.displayNameEn(),
                command.valueType(),
                command.sensitivity(),
                ConfigurationDefinitionStatus.DRAFT,
                command.scoped(),
                command.requiresApproval(),
                command.defaultValue(),
                command.description(),
                now,
                now
        );
        return ConfigurationApplicationMapper.toSummary(definitionRepositoryPort.save(definition));
    }

    @Override
    public ConfigurationValueSummaryDto setConfigurationValue(SetConfigurationValueCommand command) {
        Objects.requireNonNull(command, "Set configuration value command must not be null.");
        valueGuard.ensureNoSecretMaterial(command.rawValue());
        Instant now = Instant.now();
        ConfigurationValue value = new ConfigurationValue(
                ConfigurationId.newId().value(),
                command.definitionId(),
                command.definitionVersionId(),
                command.environment(),
                command.rawValue(),
                command.jsonValue(),
                command.secretReference(),
                ConfigurationValueStatus.ACTIVE,
                command.effectiveFrom(),
                command.effectiveTo(),
                command.createdByActorId(),
                now,
                now
        );
        return ConfigurationApplicationMapper.toSummary(valueRepositoryPort.save(value));
    }

    @Override
    public FeatureFlagSummaryDto createFeatureFlag(CreateFeatureFlagCommand command) {
        Objects.requireNonNull(command, "Create feature flag command must not be null.");
        Instant now = Instant.now();
        FeatureFlag featureFlag = new FeatureFlag(
                ConfigurationId.newId().value(),
                command.code(),
                command.nameFr(),
                command.nameAr(),
                command.nameEn(),
                command.owningModule(),
                FeatureFlagStatus.DRAFT,
                command.evaluationStrategy(),
                command.defaultEnabled(),
                command.description(),
                now,
                now
        );
        return ConfigurationApplicationMapper.toSummary(featureFlagRepositoryPort.save(featureFlag));
    }
}
