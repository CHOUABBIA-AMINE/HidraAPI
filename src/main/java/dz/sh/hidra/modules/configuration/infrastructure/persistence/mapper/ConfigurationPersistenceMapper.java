/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ConfigurationPersistenceMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Class
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
}
