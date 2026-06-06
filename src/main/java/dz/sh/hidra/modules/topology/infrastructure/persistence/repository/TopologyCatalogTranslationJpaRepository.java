/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyCatalogTranslationJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.repository
 *
 * @Description : JPA-backed repository for topology catalog translation rows.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.repository;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.TopologyCatalogTranslationJpaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

/**
 * JPA-backed repository for topology catalog translation rows.
 *
 * <p>Business role:
 * Reads localized labels and descriptions from the additive V003 catalog translation tables.
 *
 * <p>Architecture role:
 * This repository is infrastructure-only. It uses native queries because V003 uses one translation
 * table per topology catalog family.
 *
 * <p>Validation:
 * Catalog table and foreign-key column names are selected only from fixed in-memory maps.
 * Caller-provided catalog names are never interpolated directly into SQL.
 *
 * <p>Usage:
 * Use from TopologyCatalogRepositoryAdapter only.
 */
public final class TopologyCatalogTranslationJpaRepository {

    private static final Map<String, String> TRANSLATION_TABLES = Map.of(
            "PRODUCT_TYPE", "hidra_topology_product_type_translation",
            "FACILITY_TYPE", "hidra_topology_facility_type_translation",
            "NODE_TYPE", "hidra_topology_node_type_translation",
            "PIPELINE_APPURTENANCE_TYPE", "hidra_topology_pipeline_appurtenance_type_translation",
            "VALVE_TYPE", "hidra_topology_valve_type_translation",
            "EQUIPMENT_TYPE", "hidra_topology_equipment_type_translation",
            "CONNECTION_TYPE", "hidra_topology_connection_type_translation");

    private static final Map<String, String> TYPE_ID_COLUMNS = Map.of(
            "PRODUCT_TYPE", "product_type_id",
            "FACILITY_TYPE", "facility_type_id",
            "NODE_TYPE", "node_type_id",
            "PIPELINE_APPURTENANCE_TYPE", "pipeline_appurtenance_type_id",
            "VALVE_TYPE", "valve_type_id",
            "EQUIPMENT_TYPE", "equipment_type_id",
            "CONNECTION_TYPE", "connection_type_id");

    private final EntityManager entityManager;

    public TopologyCatalogTranslationJpaRepository(EntityManager entityManager) {
        this.entityManager = Objects.requireNonNull(entityManager, "EntityManager must not be null.");
    }

    public List<TopologyCatalogTranslationJpaEntity> findByCatalogNameAndTypeId(String catalogName, String typeId) {
        Objects.requireNonNull(typeId, "Catalog type id must not be null.");
        Query query = entityManager.createNativeQuery("""
                select id, %s, locale, name, description, created_at, updated_at
                from %s
                where %s = :typeId
                order by locale asc
                """.formatted(typeIdColumnFor(catalogName), tableFor(catalogName), typeIdColumnFor(catalogName)));
        query.setParameter("typeId", typeId);
        return query.getResultList().stream()
                .map(row -> toEntity((Object[]) row))
                .toList();
    }

    private static TopologyCatalogTranslationJpaEntity toEntity(Object[] row) {
        TopologyCatalogTranslationJpaEntity entity = new TopologyCatalogTranslationJpaEntity();
        entity.setId((String) row[0]);
        entity.setTypeId((String) row[1]);
        entity.setLocale((String) row[2]);
        entity.setName((String) row[3]);
        entity.setDescription((String) row[4]);
        entity.setCreatedAt(toInstant(row[5]));
        entity.setUpdatedAt(toInstant(row[6]));
        return entity;
    }

    private static String tableFor(String catalogName) {
        String normalized = normalizeCatalogName(catalogName);
        String tableName = TRANSLATION_TABLES.get(normalized);
        if (tableName == null) {
            throw new IllegalArgumentException("Unsupported topology catalog name: " + catalogName);
        }
        return tableName;
    }

    private static String typeIdColumnFor(String catalogName) {
        String normalized = normalizeCatalogName(catalogName);
        String columnName = TYPE_ID_COLUMNS.get(normalized);
        if (columnName == null) {
            throw new IllegalArgumentException("Unsupported topology catalog name: " + catalogName);
        }
        return columnName;
    }

    private static String normalizeCatalogName(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Topology catalog name must not be null or blank.");
        }
        return value.trim().replace('-', '_').replace(' ', '_').toUpperCase(Locale.ROOT);
    }

    private static Instant toInstant(Object value) {
        if (value instanceof Instant instant) {
            return instant;
        }
        if (value instanceof Timestamp timestamp) {
            return timestamp.toInstant();
        }
        if (value instanceof OffsetDateTime offsetDateTime) {
            return offsetDateTime.toInstant();
        }
        if (value instanceof ZonedDateTime zonedDateTime) {
            return zonedDateTime.toInstant();
        }
        throw new IllegalArgumentException("Unsupported timestamp value type: " + value.getClass().getName());
    }
}
