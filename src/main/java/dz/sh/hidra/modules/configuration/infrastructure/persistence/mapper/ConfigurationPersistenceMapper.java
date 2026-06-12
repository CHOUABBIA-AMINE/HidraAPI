/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationPersistenceMapper
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
 * @Layer       : Infrastructure
 * @Module      : configuration
 * @Package     : dz.sh.hidra.modules.configuration.infrastructure.persistence.mapper
 *
 * @Description : Maps configuration domain models to JPA entities.
 *
 */
package dz.sh.hidra.modules.configuration.infrastructure.persistence.mapper;

import dz.sh.hidra.modules.configuration.domain.model.*;
import dz.sh.hidra.modules.configuration.infrastructure.persistence.entity.*;

/**
 * Maps configuration domain models to JPA entities.
 */
public final class ConfigurationPersistenceMapper {

    private ConfigurationPersistenceMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }


        public static ConfigurationNamespaceJpaEntity toEntity(ConfigurationNamespace model) {
            return new ConfigurationNamespaceJpaEntity(
                        model.id(),
                        model.code(),
                        model.nameFr(),
                        model.nameAr(),
                        model.nameEn(),
                        model.ownerModule(),
                        model.ownerTeam(),
                        model.status(),
                        model.description(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static ConfigurationNamespace toDomain(ConfigurationNamespaceJpaEntity entity) {
            return new ConfigurationNamespace(
                        entity.id(),
                        entity.code(),
                        entity.nameFr(),
                        entity.nameAr(),
                        entity.nameEn(),
                        entity.ownerModule(),
                        entity.ownerTeam(),
                        entity.status(),
                        entity.description(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static ConfigurationDefinitionJpaEntity toEntity(ConfigurationDefinition model) {
            return new ConfigurationDefinitionJpaEntity(
                        model.id(),
                        model.namespaceId(),
                        model.key(),
                        model.displayNameFr(),
                        model.displayNameAr(),
                        model.displayNameEn(),
                        model.valueType(),
                        model.sensitivity(),
                        model.status(),
                        model.scoped(),
                        model.requiresApproval(),
                        model.defaultValue(),
                        model.description(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static ConfigurationDefinition toDomain(ConfigurationDefinitionJpaEntity entity) {
            return new ConfigurationDefinition(
                        entity.id(),
                        entity.namespaceId(),
                        entity.key(),
                        entity.displayNameFr(),
                        entity.displayNameAr(),
                        entity.displayNameEn(),
                        entity.valueType(),
                        entity.sensitivity(),
                        entity.status(),
                        entity.scoped(),
                        entity.requiresApproval(),
                        entity.defaultValue(),
                        entity.description(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static ConfigurationDefinitionVersionJpaEntity toEntity(ConfigurationDefinitionVersion model) {
            return new ConfigurationDefinitionVersionJpaEntity(
                        model.id(),
                        model.definitionId(),
                        model.versionNumber(),
                        model.schemaJson(),
                        model.defaultValue(),
                        model.validationSummary(),
                        model.createdByActorId(),
                        model.createdAt(),
                        model.active()
            );
        }

        public static ConfigurationDefinitionVersion toDomain(ConfigurationDefinitionVersionJpaEntity entity) {
            return new ConfigurationDefinitionVersion(
                        entity.id(),
                        entity.definitionId(),
                        entity.versionNumber(),
                        entity.schemaJson(),
                        entity.defaultValue(),
                        entity.validationSummary(),
                        entity.createdByActorId(),
                        entity.createdAt(),
                        entity.active()
            );
        }

        public static ConfigurationValueJpaEntity toEntity(ConfigurationValue model) {
            return new ConfigurationValueJpaEntity(
                        model.id(),
                        model.definitionId(),
                        model.definitionVersionId(),
                        model.environment(),
                        model.rawValue(),
                        model.jsonValue(),
                        model.secretReference(),
                        model.status(),
                        model.effectiveFrom(),
                        model.effectiveTo(),
                        model.createdByActorId(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static ConfigurationValue toDomain(ConfigurationValueJpaEntity entity) {
            return new ConfigurationValue(
                        entity.id(),
                        entity.definitionId(),
                        entity.definitionVersionId(),
                        entity.environment(),
                        entity.rawValue(),
                        entity.jsonValue(),
                        entity.secretReference(),
                        entity.status(),
                        entity.effectiveFrom(),
                        entity.effectiveTo(),
                        entity.createdByActorId(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static ScopedConfigurationOverrideJpaEntity toEntity(ScopedConfigurationOverride model) {
            return new ScopedConfigurationOverrideJpaEntity(
                        model.id(),
                        model.configurationValueId(),
                        model.definitionId(),
                        model.scopeType(),
                        model.scopeId(),
                        model.moduleName(),
                        model.organizationUnitId(),
                        model.overrideValue(),
                        model.overrideJsonValue(),
                        model.status(),
                        model.effectiveFrom(),
                        model.effectiveTo(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static ScopedConfigurationOverride toDomain(ScopedConfigurationOverrideJpaEntity entity) {
            return new ScopedConfigurationOverride(
                        entity.id(),
                        entity.configurationValueId(),
                        entity.definitionId(),
                        entity.scopeType(),
                        entity.scopeId(),
                        entity.moduleName(),
                        entity.organizationUnitId(),
                        entity.overrideValue(),
                        entity.overrideJsonValue(),
                        entity.status(),
                        entity.effectiveFrom(),
                        entity.effectiveTo(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static ConfigurationProfileJpaEntity toEntity(ConfigurationProfile model) {
            return new ConfigurationProfileJpaEntity(
                        model.id(),
                        model.code(),
                        model.nameFr(),
                        model.nameAr(),
                        model.nameEn(),
                        model.environment(),
                        model.status(),
                        model.description(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static ConfigurationProfile toDomain(ConfigurationProfileJpaEntity entity) {
            return new ConfigurationProfile(
                        entity.id(),
                        entity.code(),
                        entity.nameFr(),
                        entity.nameAr(),
                        entity.nameEn(),
                        entity.environment(),
                        entity.status(),
                        entity.description(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static ConfigurationProfileEntryJpaEntity toEntity(ConfigurationProfileEntry model) {
            return new ConfigurationProfileEntryJpaEntity(
                        model.id(),
                        model.profileId(),
                        model.definitionId(),
                        model.configurationValueId(),
                        model.scopedOverrideId(),
                        model.priorityOrder(),
                        model.active(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static ConfigurationProfileEntry toDomain(ConfigurationProfileEntryJpaEntity entity) {
            return new ConfigurationProfileEntry(
                        entity.id(),
                        entity.profileId(),
                        entity.definitionId(),
                        entity.configurationValueId(),
                        entity.scopedOverrideId(),
                        entity.priorityOrder(),
                        entity.active(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static FeatureFlagJpaEntity toEntity(FeatureFlag model) {
            return new FeatureFlagJpaEntity(
                        model.id(),
                        model.code(),
                        model.nameFr(),
                        model.nameAr(),
                        model.nameEn(),
                        model.owningModule(),
                        model.status(),
                        model.evaluationStrategy(),
                        model.defaultEnabled(),
                        model.description(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static FeatureFlag toDomain(FeatureFlagJpaEntity entity) {
            return new FeatureFlag(
                        entity.id(),
                        entity.code(),
                        entity.nameFr(),
                        entity.nameAr(),
                        entity.nameEn(),
                        entity.owningModule(),
                        entity.status(),
                        entity.evaluationStrategy(),
                        entity.defaultEnabled(),
                        entity.description(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static FeatureFlagRuleJpaEntity toEntity(FeatureFlagRule model) {
            return new FeatureFlagRuleJpaEntity(
                        model.id(),
                        model.featureFlagId(),
                        model.ruleName(),
                        model.scopeType(),
                        model.scopeId(),
                        model.conditionExpression(),
                        model.percentage(),
                        model.enabled(),
                        model.priorityOrder(),
                        model.active(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static FeatureFlagRule toDomain(FeatureFlagRuleJpaEntity entity) {
            return new FeatureFlagRule(
                        entity.id(),
                        entity.featureFlagId(),
                        entity.ruleName(),
                        entity.scopeType(),
                        entity.scopeId(),
                        entity.conditionExpression(),
                        entity.percentage(),
                        entity.enabled(),
                        entity.priorityOrder(),
                        entity.active(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static ConfigurationValidationRuleJpaEntity toEntity(ConfigurationValidationRule model) {
            return new ConfigurationValidationRuleJpaEntity(
                        model.id(),
                        model.definitionId(),
                        model.ruleCode(),
                        model.ruleTypeId(),
                        model.expression(),
                        model.configurationJson(),
                        model.status(),
                        model.failureMessage(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static ConfigurationValidationRule toDomain(ConfigurationValidationRuleJpaEntity entity) {
            return new ConfigurationValidationRule(
                        entity.id(),
                        entity.definitionId(),
                        entity.ruleCode(),
                        entity.ruleTypeId(),
                        entity.expression(),
                        entity.configurationJson(),
                        entity.status(),
                        entity.failureMessage(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static ConfigurationChangeRequestJpaEntity toEntity(ConfigurationChangeRequest model) {
            return new ConfigurationChangeRequestJpaEntity(
                        model.id(),
                        model.requestNumber(),
                        model.definitionId(),
                        model.profileId(),
                        model.featureFlagId(),
                        model.requestedValue(),
                        model.requestedJsonValue(),
                        model.status(),
                        model.reason(),
                        model.requestedByActorId(),
                        model.requestedAt(),
                        model.workflowInstanceId(),
                        model.approvedByActorId(),
                        model.approvedAt(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static ConfigurationChangeRequest toDomain(ConfigurationChangeRequestJpaEntity entity) {
            return new ConfigurationChangeRequest(
                        entity.id(),
                        entity.requestNumber(),
                        entity.definitionId(),
                        entity.profileId(),
                        entity.featureFlagId(),
                        entity.requestedValue(),
                        entity.requestedJsonValue(),
                        entity.status(),
                        entity.reason(),
                        entity.requestedByActorId(),
                        entity.requestedAt(),
                        entity.workflowInstanceId(),
                        entity.approvedByActorId(),
                        entity.approvedAt(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static ConfigurationDeploymentJpaEntity toEntity(ConfigurationDeployment model) {
            return new ConfigurationDeploymentJpaEntity(
                        model.id(),
                        model.deploymentNumber(),
                        model.changeRequestId(),
                        model.profileId(),
                        model.environment(),
                        model.status(),
                        model.deployedByActorId(),
                        model.startedAt(),
                        model.completedAt(),
                        model.rollbackDeploymentId(),
                        model.failureReason(),
                        model.createdAt()
            );
        }

        public static ConfigurationDeployment toDomain(ConfigurationDeploymentJpaEntity entity) {
            return new ConfigurationDeployment(
                        entity.id(),
                        entity.deploymentNumber(),
                        entity.changeRequestId(),
                        entity.profileId(),
                        entity.environment(),
                        entity.status(),
                        entity.deployedByActorId(),
                        entity.startedAt(),
                        entity.completedAt(),
                        entity.rollbackDeploymentId(),
                        entity.failureReason(),
                        entity.createdAt()
            );
        }

        public static ResolvedConfigurationSnapshotJpaEntity toEntity(ResolvedConfigurationSnapshot model) {
            return new ResolvedConfigurationSnapshotJpaEntity(
                        model.id(),
                        model.snapshotNumber(),
                        model.profileId(),
                        model.namespaceId(),
                        model.targetModule(),
                        model.scopeType(),
                        model.scopeId(),
                        model.environment(),
                        model.resolvedValuesJson(),
                        model.hashValue(),
                        model.status(),
                        model.resolvedAt(),
                        model.expiresAt()
            );
        }

        public static ResolvedConfigurationSnapshot toDomain(ResolvedConfigurationSnapshotJpaEntity entity) {
            return new ResolvedConfigurationSnapshot(
                        entity.id(),
                        entity.snapshotNumber(),
                        entity.profileId(),
                        entity.namespaceId(),
                        entity.targetModule(),
                        entity.scopeType(),
                        entity.scopeId(),
                        entity.environment(),
                        entity.resolvedValuesJson(),
                        entity.hashValue(),
                        entity.status(),
                        entity.resolvedAt(),
                        entity.expiresAt()
            );
        }

        public static ConfigurationExternalReferenceJpaEntity toEntity(ConfigurationExternalReference model) {
            return new ConfigurationExternalReferenceJpaEntity(
                        model.id(),
                        model.targetType(),
                        model.targetId(),
                        model.targetCodeSnapshot(),
                        model.targetLabelSnapshot(),
                        model.referenceModule(),
                        model.referenceType(),
                        model.referenceId(),
                        model.referenceCodeSnapshot(),
                        model.referenceLabelSnapshot(),
                        model.active(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static ConfigurationExternalReference toDomain(ConfigurationExternalReferenceJpaEntity entity) {
            return new ConfigurationExternalReference(
                        entity.id(),
                        entity.targetType(),
                        entity.targetId(),
                        entity.targetCodeSnapshot(),
                        entity.targetLabelSnapshot(),
                        entity.referenceModule(),
                        entity.referenceType(),
                        entity.referenceId(),
                        entity.referenceCodeSnapshot(),
                        entity.referenceLabelSnapshot(),
                        entity.active(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static ConfigurationCatalogEntryJpaEntity toEntity(ConfigurationCatalogEntry model) {
            return new ConfigurationCatalogEntryJpaEntity(
                        model.id(),
                        model.catalogName(),
                        model.code(),
                        model.active(),
                        model.sortOrder(),
                        model.systemDefined(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static ConfigurationCatalogEntry toDomain(ConfigurationCatalogEntryJpaEntity entity) {
            return new ConfigurationCatalogEntry(
                        entity.id(),
                        entity.catalogName(),
                        entity.code(),
                        entity.active(),
                        entity.sortOrder(),
                        entity.systemDefined(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

        public static ConfigurationCatalogTranslationJpaEntity toEntity(ConfigurationCatalogTranslation model) {
            return new ConfigurationCatalogTranslationJpaEntity(
                        model.id(),
                        model.catalogEntryId(),
                        model.locale(),
                        model.name(),
                        model.description(),
                        model.createdAt(),
                        model.updatedAt()
            );
        }

        public static ConfigurationCatalogTranslation toDomain(ConfigurationCatalogTranslationJpaEntity entity) {
            return new ConfigurationCatalogTranslation(
                        entity.id(),
                        entity.catalogEntryId(),
                        entity.locale(),
                        entity.name(),
                        entity.description(),
                        entity.createdAt(),
                        entity.updatedAt()
            );
        }

}
