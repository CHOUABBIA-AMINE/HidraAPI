/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyCatalogPersistenceMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.mapper
 *
 * @Description : Maps topology catalog persistence rows to domain catalog models.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.mapper;

import java.util.List;
import java.util.Objects;

import dz.sh.hidra.modules.topology.domain.model.TopologyTypeCatalog;
import dz.sh.hidra.modules.topology.domain.model.TopologyTypeTranslation;
import dz.sh.hidra.modules.topology.domain.value.TopologyCode;
import dz.sh.hidra.modules.topology.domain.value.TopologyName;
import dz.sh.hidra.modules.topology.domain.value.TopologyStatus;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.TopologyCatalogJpaEntity;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.TopologyCatalogTranslationJpaEntity;

/**
 * Maps topology catalog persistence rows to domain catalog models.
 *
 * <p>Business role:
 * Restores multilingual configurable topology type catalog entries from V003 persistence rows.
 *
 * <p>Architecture role:
 * This mapper belongs to topology infrastructure only. It does not depend on REST, application
 * services, identity, organization implementation, measurement, flow, risk, or workflow.
 *
 * <p>Validation:
 * Domain factory methods re-validate catalog codes, statuses, translation names, locale tags, and
 * timestamps during restoration.
 *
 * <p>Usage:
 * Use from TopologyCatalogRepositoryAdapter only.
 */
public final class TopologyCatalogPersistenceMapper {

    public TopologyTypeCatalog toDomain(
            TopologyCatalogJpaEntity entity,
            List<TopologyCatalogTranslationJpaEntity> translationEntities) {

        Objects.requireNonNull(entity, "Topology catalog persistence entity must not be null.");
        List<TopologyTypeTranslation> translations = translationEntities == null
                ? List.of()
                : translationEntities.stream()
                        .map(this::toDomain)
                        .toList();

        return TopologyTypeCatalog.restore(
                entity.getId(),
                entity.getCatalogName(),
                TopologyCode.of(entity.getCode()),
                TopologyStatus.valueOf(entity.getStatus()),
                entity.getSortOrder(),
                entity.getSystemDefined(),
                translations,
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }

    private TopologyTypeTranslation toDomain(TopologyCatalogTranslationJpaEntity entity) {
        Objects.requireNonNull(entity, "Topology catalog translation persistence entity must not be null.");
        return TopologyTypeTranslation.restore(
                entity.getId(),
                entity.getTypeId(),
                entity.getLocale(),
                TopologyName.of(entity.getName()),
                entity.getDescription(),
                entity.getCreatedAt(),
                entity.getUpdatedAt());
    }
}
