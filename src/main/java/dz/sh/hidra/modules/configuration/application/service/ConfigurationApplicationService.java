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

import org.springframework.stereotype.Service;

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
@Service
@org.springframework.transaction.annotation.Transactional(readOnly=true)
public class ConfigurationApplicationService implements CreateConfigurationDefinitionUseCase, SetConfigurationValueUseCase, CreateFeatureFlagUseCase {

    private final ConfigurationDefinitionRepositoryPort definitionRepositoryPort;
    private final ConfigurationValueRepositoryPort valueRepositoryPort;
    private final FeatureFlagRepositoryPort featureFlagRepositoryPort;
    private final dz.sh.hidra.modules.configuration.application.port.out.ConfigurationAdministrationPersistencePort administration;
    private final dz.sh.hidra.modules.configuration.application.port.out.ConfigurationAdministrationAuthorizationPort authority;
    private final ConfigurationValueGuard valueGuard = new ConfigurationValueGuard();

    public ConfigurationApplicationService(
            ConfigurationDefinitionRepositoryPort definitionRepositoryPort,
            ConfigurationValueRepositoryPort valueRepositoryPort,
            FeatureFlagRepositoryPort featureFlagRepositoryPort,
            dz.sh.hidra.modules.configuration.application.port.out.ConfigurationAdministrationPersistencePort administration,
            dz.sh.hidra.modules.configuration.application.port.out.ConfigurationAdministrationAuthorizationPort authority
    ) {
        this.administration=Objects.requireNonNull(administration);this.authority=Objects.requireNonNull(authority);
        this.definitionRepositoryPort = Objects.requireNonNull(definitionRepositoryPort, "Configuration definition repository port must not be null.");
        this.valueRepositoryPort = Objects.requireNonNull(valueRepositoryPort, "Configuration value repository port must not be null.");
        this.featureFlagRepositoryPort = Objects.requireNonNull(featureFlagRepositoryPort, "Feature flag repository port must not be null.");
    }

    @Override
    @org.springframework.transaction.annotation.Transactional(rollbackFor=Exception.class)
    public ConfigurationDefinitionSummaryDto createConfigurationDefinition(CreateConfigurationDefinitionCommand command) {
        authority.administrator();
        Objects.requireNonNull(command, "Create configuration definition command must not be null.");
        administration.requireNamespace(command.namespaceId());
        dz.sh.hidra.modules.configuration.domain.service.TypedConfigurationPolicy.noSecrets(command.key(),command.defaultValue(),command.description(),command.displayNameFr(),command.displayNameAr(),command.displayNameEn());
        valueGuard.ensureAllowedDefinition(command.key());
        if(command.sensitivity()==dz.sh.hidra.modules.configuration.domain.value.ConfigurationSensitivity.PUBLIC
                ||command.sensitivity()==dz.sh.hidra.modules.configuration.domain.value.ConfigurationSensitivity.INTERNAL)
            dz.sh.hidra.modules.configuration.domain.service.TypedConfigurationPolicy.typed(command.valueType(),command.defaultValue());
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
        if(definition.sensitivity()==dz.sh.hidra.modules.configuration.domain.value.ConfigurationSensitivity.PUBLIC
                ||definition.sensitivity()==dz.sh.hidra.modules.configuration.domain.value.ConfigurationSensitivity.INTERNAL)
            dz.sh.hidra.modules.configuration.domain.service.TypedConfigurationPolicy.supported(definition);
        else if(definition.defaultValue()!=null)throw new IllegalArgumentException("Sensitive definitions accept metadata only.");
        var result=ConfigurationApplicationMapper.toSummary(definitionRepositoryPort.save(definition));
        authority.administrator();return result;
    }

    @Override
    @org.springframework.transaction.annotation.Transactional(rollbackFor=Exception.class)
    public ConfigurationValueSummaryDto setConfigurationValue(SetConfigurationValueCommand command) {
        String actor=authority.administrator();
        Objects.requireNonNull(command, "Set configuration value command must not be null.");
        dz.sh.hidra.modules.configuration.domain.service.TypedConfigurationPolicy.exact(command.definitionId(),80);
        dz.sh.hidra.modules.configuration.domain.service.TypedConfigurationPolicy.environment(command.environment());
        dz.sh.hidra.modules.configuration.domain.service.TypedConfigurationPolicy.window(command.effectiveFrom(),command.effectiveTo());
        dz.sh.hidra.modules.configuration.domain.service.TypedConfigurationPolicy.noSecrets(command.rawValue(),command.jsonValue(),command.secretReference());
        if(command.jsonValue()!=null||command.secretReference()!=null)throw new IllegalArgumentException("Only typed non-sensitive scalar values are admitted.");
        if(command.createdByActorId()!=null&&!actor.equals(command.createdByActorId()))throw new IllegalArgumentException("Actor impersonation is not permitted.");
        var state=administration.definition(command.definitionId(),true);
        dz.sh.hidra.modules.configuration.domain.service.TypedConfigurationPolicy.active(state.definition());
        administration.requireNamespace(state.definition().namespaceId());administration.requireSupportedRules(command.definitionId());
        if(state.activeVersionId()==null||!state.activeVersionId().equals(command.definitionVersionId()))throw new IllegalStateException("Exact active definition version required.");
        String typed=dz.sh.hidra.modules.configuration.domain.service.TypedConfigurationPolicy.typed(state.definition().valueType(),command.rawValue());
        if(typed==null)throw new IllegalArgumentException("Typed override value required.");
        administration.requireNoOverlap(command.definitionId(),command.environment(),command.effectiveFrom(),command.effectiveTo());
        Instant now = Instant.now();
        ConfigurationValue value = new ConfigurationValue(
                ConfigurationId.newId().value(),
                command.definitionId(),
                command.definitionVersionId(),
                command.environment(),
                typed,
                command.jsonValue(),
                command.secretReference(),
                ConfigurationValueStatus.ACTIVE,
                command.effectiveFrom(),
                command.effectiveTo(),
                actor,
                now,
                now
        );
        var result=ConfigurationApplicationMapper.toSummary(valueRepositoryPort.save(value));
        authority.administrator();return result;
    }

    @Override
    @org.springframework.transaction.annotation.Transactional(rollbackFor=Exception.class)
    public FeatureFlagSummaryDto createFeatureFlag(CreateFeatureFlagCommand command) {
        authority.administrator();
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
        dz.sh.hidra.modules.configuration.domain.service.TypedConfigurationPolicy.booleanFlag(featureFlag);
        var result=ConfigurationApplicationMapper.toSummary(featureFlagRepositoryPort.save(featureFlag));
        authority.administrator();return result;
    }
}

