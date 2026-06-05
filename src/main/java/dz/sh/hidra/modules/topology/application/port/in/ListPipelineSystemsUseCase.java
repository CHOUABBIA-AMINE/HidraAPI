/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ListPipelineSystemsUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.port.in
 *
 * @Description : Inbound port for listing pipeline systems.
 *
 */
package dz.sh.hidra.modules.topology.application.port.in;

import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.modules.topology.application.dto.PipelineSystemDto;
import dz.sh.hidra.modules.topology.application.query.ListPipelineSystemsQuery;

/**
 * Inbound port for listing pipeline systems.
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
public interface ListPipelineSystemsUseCase {

    /**
     * Lists pipeline systems.
     *
     * @param query use-case input
     * @return paged pipeline systems DTO result
     */
    PageResult<PipelineSystemDto> listPipelineSystems(ListPipelineSystemsQuery query);
}
