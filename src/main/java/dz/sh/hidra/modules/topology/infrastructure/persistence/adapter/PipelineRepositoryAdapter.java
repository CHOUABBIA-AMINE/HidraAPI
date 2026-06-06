/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Persistence adapter implementing PipelineRepositoryPort.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

import dz.sh.hidra.kernel.application.pagination.PageRequest;
import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.modules.topology.application.port.out.PipelineRepositoryPort;
import dz.sh.hidra.modules.topology.application.query.ListPipelinesQuery;
import dz.sh.hidra.modules.topology.domain.model.Pipeline;
import dz.sh.hidra.modules.topology.domain.value.PipelineId;
import dz.sh.hidra.modules.topology.domain.value.TopologyCode;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.PipelineJpaEntity;
import dz.sh.hidra.modules.topology.infrastructure.persistence.mapper.TopologyPersistenceMapper;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.PipelineJpaRepository;

/**
 * Persistence adapter implementing PipelineRepositoryPort.
 */
public final class PipelineRepositoryAdapter implements PipelineRepositoryPort {

    private final PipelineJpaRepository jpaRepository;
    private final TopologyPersistenceMapper mapper;

    public PipelineRepositoryAdapter(PipelineJpaRepository jpaRepository, TopologyPersistenceMapper mapper) {
        this.jpaRepository = Objects.requireNonNull(jpaRepository, "PipelineJpaRepository must not be null.");
        this.mapper = Objects.requireNonNull(mapper, "Topology persistence mapper must not be null.");
    }

    @Override
    public Pipeline save(Pipeline pipeline) {
        Objects.requireNonNull(pipeline, "Pipeline must not be null.");
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(pipeline)));
    }

    @Override
    public Optional<Pipeline> findById(PipelineId id) {
        Objects.requireNonNull(id, "PipelineId must not be null.");
        return jpaRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public Optional<Pipeline> findByCode(TopologyCode code) {
        Objects.requireNonNull(code, "Topology code must not be null.");
        return jpaRepository.findByCode(code.value()).map(mapper::toDomain);
    }

    @Override
    public boolean existsByCode(TopologyCode code) {
        Objects.requireNonNull(code, "Topology code must not be null.");
        return jpaRepository.existsByCode(code.value());
    }

    @Override
    public PageResult<Pipeline> findAll(ListPipelinesQuery query) {
        Objects.requireNonNull(query, "List query must not be null.");

        List<Pipeline> filteredItems = jpaRepository.findAll().stream()
                .filter(entity -> matchesSearchText(entity, query.searchText()))
                .filter(entity -> query.pipelineSystemId() == null || query.pipelineSystemId().value().equals(entity.getPipelineSystemId()))
                .filter(entity -> query.productType() == null || query.productType().id().equals(entity.getProductTypeId()))
                .filter(entity -> query.status() == null || entity.getStatus().equals(query.status().name()))
                .map(mapper::toDomain)
                .toList();

        return paginate(filteredItems, query.pageRequest());
    }

    private static boolean matchesSearchText(PipelineJpaEntity entity, String searchText) {
        if (searchText == null) {
            return true;
        }
        return containsIgnoreCase(entity.getCode(), searchText) || containsIgnoreCase(entity.getName(), searchText);
    }

    private static PageResult<Pipeline> paginate(List<Pipeline> items, PageRequest pageRequest) {
        Objects.requireNonNull(pageRequest, "Page request must not be null.");
        int fromIndex = Math.min(pageRequest.page() * pageRequest.size(), items.size());
        int toIndex = Math.min(fromIndex + pageRequest.size(), items.size());
        return PageResult.of(items.subList(fromIndex, toIndex), pageRequest.page(), pageRequest.size(), items.size());
    }

    private static boolean containsIgnoreCase(String value, String searchText) {
        return value != null && value.toLowerCase(Locale.ROOT).contains(searchText.toLowerCase(Locale.ROOT));
    }
}
