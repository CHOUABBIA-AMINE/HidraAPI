/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : GetTopologyNodeUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.port.in
 *
 * @Description : Inbound port for retrieving a topology node.
 *
 */
package dz.sh.hidra.modules.topology.application.port.in;

import dz.sh.hidra.modules.topology.application.dto.TopologyNodeDto;
import dz.sh.hidra.modules.topology.application.query.GetTopologyNodeByIdQuery;

/**
 * Inbound port for retrieving a topology node.
 *
 * <p>Business role:
 * This inbound port exposes a topology read use case without exposing domain aggregates,
 * persistence entities, REST DTOs, identity implementation, organization implementation,
 * measurement, flow, risk, workflow, or infrastructure code.
 *
 * <p>Architecture role:
 * This is an application-layer inbound port. API controllers and other callers may depend on this
 * interface; implementation belongs to application services in a later task.
 *
 * <p>Validation:
 * Input validation is expected before or inside the use-case implementation.
 *
 * <p>Usage:
 * Depend on this interface from the API layer for get-by-id operations.
 */
public interface GetTopologyNodeUseCase {

    /**
     * Retrieves a topology node.
     *
     * @param query use-case input
     * @return a topology node DTO
     */
    TopologyNodeDto getTopologyNode(GetTopologyNodeByIdQuery query);
}
