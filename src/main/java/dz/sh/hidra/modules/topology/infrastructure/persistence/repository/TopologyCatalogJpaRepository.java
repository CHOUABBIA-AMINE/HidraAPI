/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyCatalogJpaRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.repository
 *
 * @Description : JPA-backed repository for topology type catalog rows.
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
import java.util.Optional;

import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.TopologyCatalogJpaEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

/**
 * JPA-backed repository for topology type catalog rows.
 *
 * <p>Business role:
 * Reads multilingual topology catalog base entries from the additive V003 catalog tables.
 *
 * <p>Architecture role:
 * This repository is infrastructure-only. It uses native queries because V003 deliberately stores
 * each catalog family in its own physical table while the application port exposes one generic
 * topology catalog boundary.
 *
 * <p>Validation:
 * Catalog table names are selected only from a fixed in-memory map. Caller-provided catalog names are
 * never interpolated directly into SQL.
 *
 * <p>Usage:
 * Use from TopologyCatalogRepositoryAdapter only.
 */
public final class TopologyCatalogJpaRepository {

    private static final Map<String, String> CATALOG_TABLES = Map.of(
            "PRODUCT_TYPE", "hidra_topology_product_type",
            "FACILITY_TYPE", "hidra_topology_facility_type",
            "NODE_TYPE", "hidra_topology_node_type",
            "PIPELINE_APPURTENANCE_TYPE", "hidra_topology_pipeline_appurtenance_type",
            "VALVE_TYPE", "hidra_topology_valve_type",
            "EQUIPMENT_TYPE", "hidra_topology_equipment_type",
            "CONNECTION_TYPE", "hidra_topology_connection_type");

    private final EntityManager entityManager;

    public TopologyCatalogJpaRepository(EntityManager entityManager) {
        this.entityManager = Objects.requireNonNull(entityManager, "EntityManager must not be null.");
    }

    public Optional<TopologyCatalogJpaEntity> findById(String catalogName, String id) {
        Objects.requireNonNull(id, "Catalog id must not be null.");
        Query query = entityManager.createNativeQuery("""
                select id, code, status, sort_order, system_defined, created_at, updated_at
                from %s
                where id = :id
                """.formatted(tableFor(catalogName)));
        query.setParameter("id", id);
        return singleResult(query.getResultList(), catalogName);
    }

    public Optional<TopologyCatalogJpaEntity> findByCode(String catalogName, String code) {
        Objects.requireNonNull(code, "Catalog code must not be null.");
        Query query = entityManager.createNativeQuery("""
                select id, code, status, sort_order, system_defined, created_at, updated_at
                from %s
                where code = :code
                """.formatted(tableFor(catalogName)));
        query.setParameter("code", code);
        return singleResult(query.getResultList(), catalogName);
    }

    public List<TopologyCatalogJpaEntity> findAll(String catalogName) {
        Query query = entityManager.createNativeQuery("""
                select id, code, status, sort_order, system_defined, created_at, updated_at
                from %s
                order by sort_order asc, code asc
                """.formatted(tableFor(catalogName)));
        return query.getResultList().stream()
                .map(row -> toEntity(catalogName, (Object[]) row))
                .toList();
    }

    public boolean supports(String catalogName) {
        return CATALOG_TABLES.containsKey(normalizeCatalogName(catalogName));
    }

    private static Optional<TopologyCatalogJpaEntity> singleResult(List<?> rows, String catalogName) {
        if (rows.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(toEntity(catalogName, (Object[]) rows.get(0)));
    }

    private static TopologyCatalogJpaEntity toEntity(String catalogName, Object[] row) {
        TopologyCatalogJpaEntity entity = new TopologyCatalogJpaEntity();
        entity.setCatalogName(normalizeCatalogName(catalogName));
        entity.setId((String) row[0]);
        entity.setCode((String) row[1]);
        entity.setStatus((String) row[2]);
        entity.setSortOrder(((Number) row[3]).intValue());
        entity.setSystemDefined((Boolean) row[4]);
        entity.setCreatedAt(toInstant(row[5]));
        entity.setUpdatedAt(toInstant(row[6]));
        return entity;
    }

    private static String tableFor(String catalogName) {
        String normalized = normalizeCatalogName(catalogName);
        String tableName = CATALOG_TABLES.get(normalized);
        if (tableName == null) {
            throw new IllegalArgumentException("Unsupported topology catalog name: " + catalogName);
        }
        return tableName;
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
