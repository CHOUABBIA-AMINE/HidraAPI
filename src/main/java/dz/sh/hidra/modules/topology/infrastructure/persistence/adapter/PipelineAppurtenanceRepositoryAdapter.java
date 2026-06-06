/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineAppurtenanceRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Persistence adapter implementing PipelineAppurtenanceRepositoryPort.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

import dz.sh.hidra.kernel.application.pagination.PageRequest;
import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.modules.topology.application.port.out.PipelineAppurtenanceRepositoryPort;
import dz.sh.hidra.modules.topology.application.query.ListPipelineAppurtenancesQuery;
import dz.sh.hidra.modules.topology.domain.model.PipelineAppurtenance;
import dz.sh.hidra.modules.topology.domain.value.PipelineAppurtenanceId;
import dz.sh.hidra.modules.topology.domain.value.TopologyCode;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.PipelineAppurtenanceJpaEntity;
import dz.sh.hidra.modules.topology.infrastructure.persistence.mapper.TopologyPersistenceMapper;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.PipelineAppurtenanceJpaRepository;

/**
 * Persistence adapter implementing PipelineAppurtenanceRepositoryPort.
 */
public final class PipelineAppurtenanceRepositoryAdapter implements PipelineAppurtenanceRepositoryPort {

    private final PipelineAppurtenanceJpaRepository jpaRepository;
    private final TopologyPersistenceMapper mapper;

    public PipelineAppurtenanceRepositoryAdapter(PipelineAppurtenanceJpaRepository jpaRepository, TopologyPersistenceMapper mapper) {
        this.jpaRepository = Objects.requireNonNull(jpaRepository, "PipelineAppurtenanceJpaRepository must not be null.");
        this.mapper = Objects.requireNonNull(mapper, "Topology persistence mapper must not be null.");
    }

    @Override
    public PipelineAppurtenance save(PipelineAppurtenance pipelineAppurtenance) {
        Objects.requireNonNull(pipelineAppurtenance, "PipelineAppurtenance must not be null.");
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(pipelineAppurtenance)));
    }

    @Override
    public Optional<PipelineAppurtenance> findById(PipelineAppurtenanceId id) {
        Objects.requireNonNull(id, "PipelineAppurtenanceId must not be null.");
        return jpaRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public Optional<PipelineAppurtenance> findByCode(TopologyCode code) {
        Objects.requireNonNull(code, "Topology code must not be null.");
        return jpaRepository.findByCode(code.value()).map(mapper::toDomain);
    }

    @Override
    public boolean existsByCode(TopologyCode code) {
        Objects.requireNonNull(code, "Topology code must not be null.");
        return jpaRepository.existsByCode(code.value());
    }

    @Override
    public PageResult<PipelineAppurtenance> findAll(ListPipelineAppurtenancesQuery query) {
        Objects.requireNonNull(query, "List query must not be null.");

        List<PipelineAppurtenance> filteredItems = jpaRepository.findAll().stream()
                .filter(entity -> matchesSearchText(entity, query.searchText()))
                .filter(entity -> query.pipelineId() == null || query.pipelineId().value().equals(entity.getPipelineId()))
                .filter(entity -> query.appurtenanceType() == null || query.appurtenanceType().id().equals(entity.getAppurtenanceTypeId()))
                .filter(entity -> query.valveType() == null || query.valveType().id().equals(entity.getValveTypeId()))
                .filter(entity -> query.status() == null || entity.getStatus().equals(query.status().name()))
                .map(mapper::toDomain)
                .toList();

        return paginate(filteredItems, query.pageRequest());
    }

    private static boolean matchesSearchText(PipelineAppurtenanceJpaEntity entity, String searchText) {
        if (searchText == null) {
            return true;
        }
        return containsIgnoreCase(entity.getCode(), searchText) || containsIgnoreCase(entity.getName(), searchText);
    }

    private static PageResult<PipelineAppurtenance> paginate(List<PipelineAppurtenance> items, PageRequest pageRequest) {
        Objects.requireNonNull(pageRequest, "Page request must not be null.");
        int fromIndex = Math.min(pageRequest.page() * pageRequest.size(), items.size());
        int toIndex = Math.min(fromIndex + pageRequest.size(), items.size());
        return PageResult.of(items.subList(fromIndex, toIndex), pageRequest.page(), pageRequest.size(), items.size());
    }

    private static boolean containsIgnoreCase(String value, String searchText) {
        return value != null && value.toLowerCase(Locale.ROOT).contains(searchText.toLowerCase(Locale.ROOT));
    }
}
