/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : FacilityRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Persistence adapter implementing FacilityRepositoryPort.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import java.util.Objects;
import java.util.Optional;
import java.util.List;
import java.util.Locale;

import dz.sh.hidra.kernel.application.pagination.PageRequest;
import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.modules.topology.application.port.out.FacilityRepositoryPort;
import dz.sh.hidra.modules.topology.application.query.ListFacilitiesQuery;
import dz.sh.hidra.modules.topology.domain.model.Facility;
import dz.sh.hidra.modules.topology.domain.value.FacilityId;
import dz.sh.hidra.modules.topology.domain.value.TopologyCode;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.FacilityJpaEntity;
import dz.sh.hidra.modules.topology.infrastructure.persistence.mapper.TopologyPersistenceMapper;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.FacilityJpaRepository;

/**
 * Persistence adapter implementing FacilityRepositoryPort.
 *
 * <p>Business role:
 * Persists and retrieves topology facility domain models through Spring Data JPA.
 *
 * <p>Architecture role:
 * This class adapts an application outbound port to infrastructure persistence. Application services
 * depend on the port, not on this adapter or Spring Data repository.
 *
 * <p>Validation:
 * Domain validation happens before persistence. Mapping restores domain value objects and model
 * invariants.
 *
 * <p>Usage:
 * Wire this adapter from topology infrastructure configuration.
 */
public final class FacilityRepositoryAdapter implements FacilityRepositoryPort {

    private final FacilityJpaRepository jpaRepository;
    private final TopologyPersistenceMapper mapper;

    public FacilityRepositoryAdapter(
            FacilityJpaRepository jpaRepository,
            TopologyPersistenceMapper mapper) {

        this.jpaRepository = Objects.requireNonNull(jpaRepository, "FacilityJpaRepository must not be null.");
        this.mapper = Objects.requireNonNull(mapper, "Topology persistence mapper must not be null.");
    }

    @Override
    public Facility save(Facility facility) {
        Objects.requireNonNull(facility, "Facility must not be null.");
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(facility)));
    }

    @Override
    public Optional<Facility> findById(FacilityId id) {
        Objects.requireNonNull(id, "FacilityId must not be null.");
        return jpaRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public Optional<Facility> findByCode(TopologyCode code) {
        Objects.requireNonNull(code, "Topology code must not be null.");
        return jpaRepository.findByCode(code.value()).map(mapper::toDomain);
    }

    @Override
    public boolean existsByCode(TopologyCode code) {
        Objects.requireNonNull(code, "Topology code must not be null.");
        return jpaRepository.existsByCode(code.value());
    }

    @Override
    public PageResult<Facility> findAll(ListFacilitiesQuery query) {
        Objects.requireNonNull(query, "List query must not be null.");

        List<Facility> filteredItems = jpaRepository.findAll().stream()
                .filter(entity -> matchesSearchText(entity, query.searchText()))
                .filter(entity -> query.facilityType() == null || entity.getFacilityType().equals(query.facilityType().name()))
                .filter(entity -> query.productType() == null || entity.getProductType().equals(query.productType().name()))
                .filter(entity -> query.status() == null || entity.getStatus().equals(query.status().name()))
                .map(mapper::toDomain)
                .toList();

        return paginate(filteredItems, query.pageRequest());
    }

    private static boolean matchesSearchText(FacilityJpaEntity entity, String searchText) {
        if (searchText == null) {
            return true;
        }

        return containsIgnoreCase(entity.getCode(), searchText)
                || containsIgnoreCase(entity.getName(), searchText);
    }

    private static PageResult<Facility> paginate(List<Facility> items, PageRequest pageRequest) {
        Objects.requireNonNull(pageRequest, "Page request must not be null.");

        int fromIndex = Math.min(pageRequest.page() * pageRequest.size(), items.size());
        int toIndex = Math.min(fromIndex + pageRequest.size(), items.size());
        return PageResult.of(items.subList(fromIndex, toIndex), pageRequest.page(), pageRequest.size(), items.size());
    }

    private static boolean containsIgnoreCase(String value, String searchText) {
        return value != null
                && value.toLowerCase(Locale.ROOT).contains(searchText.toLowerCase(Locale.ROOT));
    }
}
