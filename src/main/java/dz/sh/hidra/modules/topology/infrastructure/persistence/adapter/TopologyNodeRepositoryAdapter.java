/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyNodeRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Persistence adapter implementing TopologyNodeRepositoryPort.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

import dz.sh.hidra.kernel.application.pagination.PageRequest;
import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.modules.topology.application.port.out.TopologyNodeRepositoryPort;
import dz.sh.hidra.modules.topology.application.query.ListTopologyNodesQuery;
import dz.sh.hidra.modules.topology.domain.model.TopologyNode;
import dz.sh.hidra.modules.topology.domain.value.TopologyCode;
import dz.sh.hidra.modules.topology.domain.value.TopologyNodeId;
import dz.sh.hidra.modules.topology.infrastructure.persistence.entity.TopologyNodeJpaEntity;
import dz.sh.hidra.modules.topology.infrastructure.persistence.mapper.TopologyPersistenceMapper;
import dz.sh.hidra.modules.topology.infrastructure.persistence.repository.TopologyNodeJpaRepository;

/**
 * Persistence adapter implementing TopologyNodeRepositoryPort.
 */
public final class TopologyNodeRepositoryAdapter implements TopologyNodeRepositoryPort {

    private final TopologyNodeJpaRepository jpaRepository;
    private final TopologyPersistenceMapper mapper;

    public TopologyNodeRepositoryAdapter(TopologyNodeJpaRepository jpaRepository, TopologyPersistenceMapper mapper) {
        this.jpaRepository = Objects.requireNonNull(jpaRepository, "TopologyNodeJpaRepository must not be null.");
        this.mapper = Objects.requireNonNull(mapper, "Topology persistence mapper must not be null.");
    }

    @Override
    public TopologyNode save(TopologyNode topologyNode) {
        Objects.requireNonNull(topologyNode, "TopologyNode must not be null.");
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(topologyNode)));
    }

    @Override
    public Optional<TopologyNode> findById(TopologyNodeId id) {
        Objects.requireNonNull(id, "TopologyNodeId must not be null.");
        return jpaRepository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public Optional<TopologyNode> findByCode(TopologyCode code) {
        Objects.requireNonNull(code, "Topology code must not be null.");
        return jpaRepository.findByCode(code.value()).map(mapper::toDomain);
    }

    @Override
    public boolean existsByCode(TopologyCode code) {
        Objects.requireNonNull(code, "Topology code must not be null.");
        return jpaRepository.existsByCode(code.value());
    }

    @Override
    public PageResult<TopologyNode> findAll(ListTopologyNodesQuery query) {
        Objects.requireNonNull(query, "List query must not be null.");

        List<TopologyNode> filteredItems = jpaRepository.findAll().stream()
                .filter(entity -> matchesSearchText(entity, query.searchText()))
                .filter(entity -> query.nodeType() == null || query.nodeType().id().equals(entity.getNodeTypeId()))
                .filter(entity -> query.facilityId() == null || query.facilityId().value().equals(entity.getFacilityId()))
                .filter(entity -> query.pipelineAppurtenanceId() == null || query.pipelineAppurtenanceId().value().equals(entity.getPipelineAppurtenanceId()))
                .filter(entity -> query.status() == null || entity.getStatus().equals(query.status().name()))
                .map(mapper::toDomain)
                .toList();

        return paginate(filteredItems, query.pageRequest());
    }

    private static boolean matchesSearchText(TopologyNodeJpaEntity entity, String searchText) {
        if (searchText == null) {
            return true;
        }
        return containsIgnoreCase(entity.getCode(), searchText) || containsIgnoreCase(entity.getName(), searchText);
    }

    private static PageResult<TopologyNode> paginate(List<TopologyNode> items, PageRequest pageRequest) {
        Objects.requireNonNull(pageRequest, "Page request must not be null.");
        int fromIndex = Math.min(pageRequest.page() * pageRequest.size(), items.size());
        int toIndex = Math.min(fromIndex + pageRequest.size(), items.size());
        return PageResult.of(items.subList(fromIndex, toIndex), pageRequest.page(), pageRequest.size(), items.size());
    }

    private static boolean containsIgnoreCase(String value, String searchText) {
        return value != null && value.toLowerCase(Locale.ROOT).contains(searchText.toLowerCase(Locale.ROOT));
    }
}
