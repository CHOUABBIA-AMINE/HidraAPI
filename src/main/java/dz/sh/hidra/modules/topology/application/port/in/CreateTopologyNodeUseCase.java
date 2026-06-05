/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateTopologyNodeUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.port.in
 *
 * @Description : Inbound port for a topology node creation.
 *
 */
package dz.sh.hidra.modules.topology.application.port.in;

import dz.sh.hidra.modules.topology.application.command.CreateTopologyNodeCommand;
import dz.sh.hidra.modules.topology.application.dto.TopologyNodeDto;

/**
 * Inbound port for a topology node creation.
 *
 * <p>Business role:
 * This inbound port exposes a topology use case to callers without exposing persistence entities,
 * REST DTOs, identity implementation classes, organization implementation classes, measurement,
 * flow, risk, workflow, or infrastructure code.
 *
 * <p>Architecture role:
 * This is an application-layer inbound port. API controllers and other callers may depend on this
 * interface; implementation belongs to application services in a later task.
 *
 * <p>Validation:
 * Input validation is expected before or inside the use-case implementation. Domain invariants
 * remain protected by topology value objects, models, policies, and domain services.
 *
 * <p>Usage:
 * Depend on this interface from the API layer. Do not implement business logic in controllers.
 */
public interface CreateTopologyNodeUseCase {

    /**
     * Creates a topology node.
     *
     * @param command use-case input
     * @return created a topology node DTO
     */
    TopologyNodeDto createTopologyNode(CreateTopologyNodeCommand command);
}
