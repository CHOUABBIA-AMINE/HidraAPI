/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ListTopologyConnectionsUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.port.in
 *
 * @Description : Inbound port for listing topology connections.
 *
 */
package dz.sh.hidra.modules.topology.application.port.in;

import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.modules.topology.application.dto.TopologyConnectionDto;
import dz.sh.hidra.modules.topology.application.query.ListTopologyConnectionsQuery;

/**
 * Inbound port for listing topology connections.
 *
 * <p>Business role:
 * This inbound port exposes a topology list use case with filtering and pagination.
 *
 * <p>Architecture role:
 * This is an application-layer inbound port. API controllers and other callers may depend on this
 * interface; implementation belongs to application services in a later task.
 *
 * <p>Validation:
 * Input validation is expected before or inside the use-case implementation.
 *
 * <p>Usage:
 * Depend on this interface from the API layer for list operations.
 */
public interface ListTopologyConnectionsUseCase {

    /**
     * Lists topology connections.
     *
     * @param query use-case input
     * @return paged topology connections DTO result
     */
    PageResult<TopologyConnectionDto> listTopologyConnections(ListTopologyConnectionsQuery query);
}
