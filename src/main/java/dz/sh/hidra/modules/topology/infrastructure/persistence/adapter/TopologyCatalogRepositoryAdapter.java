/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyCatalogRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Persistence adapter implementing TopologyCatalogRepositoryPort.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

import dz.sh.hidra.kernel.application.pagination.PageRequest;
import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.modules.topology.application.port.out.TopologyCatalogRepositoryPort;
import dz.sh.hidra.modules.topology.application.query.ListTopologyCatalogTypesQuery;
import dz.sh.hidra.modules.topology.domain.model.TopologyTypeCatalog;
import dz.sh.hidra.modules.topology.domain.value.TopologyCode;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.TopologyCatalogJpaEntity;
import dz.sh.hidra.modules.topology.infrastructure.persistence.mapper.TopologyCatalogPersistenceMapper;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.TopologyCatalogJpaRepository;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.TopologyCatalogTranslationJpaRepository;

/**
 * Persistence adapter implementing TopologyCatalogRepositoryPort.
 *
 * <p>Business role:
 * Provides application services with multilingual configurable topology catalog entries from V003
 * topology catalog tables.
 *
 * <p>Architecture role:
 * This adapter hides the physical one-table-per-catalog-family design behind one application outbound
 * port. Application services depend on TopologyCatalogRepositoryPort, not on JPA, SQL, or table names.
 *
 * <p>Validation:
 * Catalog names are normalized and supported only when V003 has a corresponding table. Domain models
 * are restored through TopologyCatalogPersistenceMapper so value-object validation is re-applied.
 *
 * <p>Usage:
 * Wire this adapter from topology infrastructure configuration.
 */
public final class TopologyCatalogRepositoryAdapter implements TopologyCatalogRepositoryPort {

    private final TopologyCatalogJpaRepository catalogRepository;
    private final TopologyCatalogTranslationJpaRepository translationRepository;
    private final TopologyCatalogPersistenceMapper mapper;

    public TopologyCatalogRepositoryAdapter(
            TopologyCatalogJpaRepository catalogRepository,
            TopologyCatalogTranslationJpaRepository translationRepository,
            TopologyCatalogPersistenceMapper mapper) {

        this.catalogRepository = Objects.requireNonNull(catalogRepository, "Topology catalog repository must not be null.");
        this.translationRepository = Objects.requireNonNull(translationRepository, "Topology catalog translation repository must not be null.");
        this.mapper = Objects.requireNonNull(mapper, "Topology catalog persistence mapper must not be null.");
    }

    @Override
    public Optional<TopologyTypeCatalog> findById(String id) {
        Objects.requireNonNull(id, "Topology catalog id must not be null.");
        return supportedCatalogNames().stream()
                .map(catalogName -> catalogRepository.findById(catalogName, id)
                        .map(entity -> toDomain(catalogName, entity)))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();
    }

    @Override
    public Optional<TopologyTypeCatalog> findByCatalogNameAndCode(String catalogName, TopologyCode code) {
        Objects.requireNonNull(code, "Topology catalog code must not be null.");
        String normalizedCatalogName = normalizeCatalogName(catalogName);
        ensureSupported(normalizedCatalogName);
        return catalogRepository.findByCode(normalizedCatalogName, code.value())
                .map(entity -> toDomain(normalizedCatalogName, entity));
    }

    @Override
    public PageResult<TopologyTypeCatalog> findAll(ListTopologyCatalogTypesQuery query) {
        Objects.requireNonNull(query, "List topology catalog types query must not be null.");
        String normalizedCatalogName = normalizeCatalogName(query.catalogName());
        ensureSupported(normalizedCatalogName);

        List<TopologyTypeCatalog> filteredItems = catalogRepository.findAll(normalizedCatalogName).stream()
                .filter(entity -> query.status() == null || entity.getStatus().equals(query.status().name()))
                .map(entity -> toDomain(normalizedCatalogName, entity))
                .toList();

        return paginate(filteredItems, query.pageRequest());
    }

    private TopologyTypeCatalog toDomain(String catalogName, TopologyCatalogJpaEntity entity) {
        return mapper.toDomain(
                entity,
                translationRepository.findByCatalogNameAndTypeId(catalogName, entity.getId()));
    }

    private void ensureSupported(String catalogName) {
        if (!catalogRepository.supports(catalogName)) {
            throw new IllegalArgumentException("Unsupported topology catalog name: " + catalogName);
        }
    }

    private static PageResult<TopologyTypeCatalog> paginate(List<TopologyTypeCatalog> items, PageRequest pageRequest) {
        Objects.requireNonNull(pageRequest, "Page request must not be null.");
        int fromIndex = Math.min(pageRequest.page() * pageRequest.size(), items.size());
        int toIndex = Math.min(fromIndex + pageRequest.size(), items.size());
        return PageResult.of(items.subList(fromIndex, toIndex), pageRequest.page(), pageRequest.size(), items.size());
    }

    private static List<String> supportedCatalogNames() {
        return List.of(
                "PRODUCT_TYPE",
                "FACILITY_TYPE",
                "NODE_TYPE",
                "PIPELINE_APPURTENANCE_TYPE",
                "VALVE_TYPE",
                "EQUIPMENT_TYPE",
                "CONNECTION_TYPE");
    }

    private static String normalizeCatalogName(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Topology catalog name must not be null or blank.");
        }
        return value.trim().replace('-', '_').replace(' ', '_').toUpperCase(Locale.ROOT);
    }
}
