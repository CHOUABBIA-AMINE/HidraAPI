/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineSegmentRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Persistence adapter implementing PipelineSegmentRepositoryPort.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import java.util.Objects;
import java.util.Optional;
import java.util.List;
import java.util.Locale;

import dz.sh.hidra.kernel.application.pagination.PageRequest;
import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.modules.topology.application.port.out.PipelineSegmentRepositoryPort;
import dz.sh.hidra.modules.topology.application.query.ListPipelineSegmentsQuery;
import dz.sh.hidra.modules.topology.domain.model.PipelineSegment;
import dz.sh.hidra.modules.topology.domain.value.PipelineSegmentId;
import dz.sh.hidra.modules.topology.domain.value.TopologyCode;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.PipelineSegmentJpaEntity;
import dz.sh.hidra.modules.topology.infrastructure.persistence.mapper.TopologyPersistenceMapper;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.PipelineSegmentJpaRepository;

/**
 * Persistence adapter implementing PipelineSegmentRepositoryPort.
 *
 * <p>Business role:
 * Persists and retrieves topology pipelineSegment domain models through Spring Data JPA.
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
public final class PipelineSegmentRepositoryAdapter implements PipelineSegmentRepositoryPort {

    private final PipelineSegmentJpaRepository jpaRepository;
    private final TopologyPersistenceMapper mapper;

    public PipelineSegmentRepositoryAdapter(
            PipelineSegmentJpaRepository jpaRepository,
            TopologyPersistenceMapper mapper) {

        this.jpaRepository = Objects.requireNonNull(jpaRepository, "PipelineSegmentJpaRepository must not be null.");
        this.mapper = Objects.requireNonNull(mapper, "Topology persistence mapper must not be null.");
    }

    @Override
    public PipelineSegment save(PipelineSegment pipelineSegment) {
        Objects.requireNonNull(pipelineSegment, "PipelineSegment must not be null.");
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(pipelineSegment)));
    }

    @Override
    public Optional<PipelineSegment> findById(PipelineSegmentId id) {
        Objects.requireNonNull(id, "PipelineSegmentId must not be null.");
        return jpaRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public Optional<PipelineSegment> findByCode(TopologyCode code) {
        Objects.requireNonNull(code, "Topology code must not be null.");
        return jpaRepository.findByCode(code.value()).map(mapper::toDomain);
    }

    @Override
    public boolean existsByCode(TopologyCode code) {
        Objects.requireNonNull(code, "Topology code must not be null.");
        return jpaRepository.existsByCode(code.value());
    }

    @Override
    public PageResult<PipelineSegment> findAll(ListPipelineSegmentsQuery query) {
        Objects.requireNonNull(query, "List query must not be null.");

        List<PipelineSegment> filteredItems = jpaRepository.findAll().stream()
                .filter(entity -> matchesSearchText(entity, query.searchText()))
                .filter(entity -> query.pipelineId() == null || entity.getPipelineId().equals(query.pipelineId().value()))
                .filter(entity -> query.fromNodeId() == null || entity.getFromNodeId().equals(query.fromNodeId().value()))
                .filter(entity -> query.toNodeId() == null || entity.getToNodeId().equals(query.toNodeId().value()))
                .filter(entity -> query.status() == null || entity.getStatus().equals(query.status().name()))
                .map(mapper::toDomain)
                .toList();

        return paginate(filteredItems, query.pageRequest());
    }

    private static boolean matchesSearchText(PipelineSegmentJpaEntity entity, String searchText) {
        if (searchText == null) {
            return true;
        }

        return containsIgnoreCase(entity.getCode(), searchText)
                || containsIgnoreCase(entity.getName(), searchText);
    }

    private static PageResult<PipelineSegment> paginate(List<PipelineSegment> items, PageRequest pageRequest) {
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
