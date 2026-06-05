/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyNodeRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.port.out
 *
 * @Description : Outbound persistence port for topologyNode.
 *
 */
package dz.sh.hidra.modules.topology.application.port.out;

import java.util.Optional;

import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.modules.topology.application.query.ListTopologyNodesQuery;
import dz.sh.hidra.modules.topology.domain.model.TopologyNode;
import dz.sh.hidra.modules.topology.domain.value.TopologyNodeId;
import dz.sh.hidra.modules.topology.domain.value.TopologyCode;

/**
 * Outbound persistence port for topologyNode.
 *
 * <p>Business role:
 * This port defines persistence operations required by topology application services without binding
 * them to JPA, Spring Data, SQL, REST DTOs, or infrastructure implementation details.
 *
 * <p>Architecture role:
 * This is an application-layer outbound port. Infrastructure adapters implement this interface in a
 * later task. Application services depend on this interface, not on repositories or entities.
 *
 * <p>Validation:
 * Domain validation is performed before calling this port. Implementations must preserve domain
 * invariants when mapping to persistence.
 *
 * <p>Usage:
 * Implement this interface in the topology infrastructure persistence adapter package.
 */
public interface TopologyNodeRepositoryPort {

    /**
     * Saves topologyNode.
     *
     * @param topologyNode domain model
     * @return saved domain model
     */
    TopologyNode save(TopologyNode topologyNode);

    /**
     * Finds topologyNode by identifier.
     *
     * @param id identifier
     * @return optional domain model
     */
    Optional<TopologyNode> findById(TopologyNodeId id);

    /**
     * Finds topologyNode by business code.
     *
     * @param code business code
     * @return optional domain model
     */
    Optional<TopologyNode> findByCode(TopologyCode code);

    /**
     * Checks whether topologyNode exists with the supplied business code.
     *
     * @param code business code
     * @return true when a matching model exists
     */
    boolean existsByCode(TopologyCode code);

    /**
     * Lists topologyNode according to application filtering and pagination criteria.
     *
     * @param query list query
     * @return paged domain model result
     */
    PageResult<TopologyNode> findAll(ListTopologyNodesQuery query);

}
