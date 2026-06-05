/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyConnectionRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.port.out
 *
 * @Description : Outbound persistence port for topologyConnection.
 *
 */
package dz.sh.hidra.modules.topology.application.port.out;

import java.util.Optional;

import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.modules.topology.application.query.ListTopologyConnectionsQuery;
import dz.sh.hidra.modules.topology.domain.model.TopologyConnection;
import dz.sh.hidra.modules.topology.domain.value.TopologyConnectionId;
import dz.sh.hidra.modules.topology.domain.value.TopologyCode;

/**
 * Outbound persistence port for topologyConnection.
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
public interface TopologyConnectionRepositoryPort {

    /**
     * Saves topologyConnection.
     *
     * @param topologyConnection domain model
     * @return saved domain model
     */
    TopologyConnection save(TopologyConnection topologyConnection);

    /**
     * Finds topologyConnection by identifier.
     *
     * @param id identifier
     * @return optional domain model
     */
    Optional<TopologyConnection> findById(TopologyConnectionId id);

    /**
     * Finds topologyConnection by business code.
     *
     * @param code business code
     * @return optional domain model
     */
    Optional<TopologyConnection> findByCode(TopologyCode code);

    /**
     * Checks whether topologyConnection exists with the supplied business code.
     *
     * @param code business code
     * @return true when a matching model exists
     */
    boolean existsByCode(TopologyCode code);

    /**
     * Lists topologyConnection according to application filtering and pagination criteria.
     *
     * @param query list query
     * @return paged domain model result
     */
    PageResult<TopologyConnection> findAll(ListTopologyConnectionsQuery query);

}
