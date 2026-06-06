/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ListTopologyCatalogTypesUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.port.in
 *
 * @Description : Inbound port for listing topology catalog types.
 *
 */
package dz.sh.hidra.modules.topology.application.port.in;

import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.modules.topology.application.dto.TopologyCatalogDto;
import dz.sh.hidra.modules.topology.application.query.ListTopologyCatalogTypesQuery;

/**
 * Inbound port for listing topology catalog types.
 *
 * <p>Business role:
 * Exposes paged lookup of configurable topology controlled vocabulary entries by catalog name and
 * preferred locale.
 *
 * <p>Architecture role:
 * This application-layer port is consumed by API or other application clients and implemented by the
 * topology catalog application service.
 *
 * <p>Validation:
 * The implementation resolves localized labels and falls back to the default locale when needed.
 *
 * <p>Usage:
 * Use this interface to populate selectable topology type lists for multilingual clients.
 */
public interface ListTopologyCatalogTypesUseCase {

    /**
     * Lists topology catalog types.
     *
     * @param query use-case input
     * @return paged catalog DTO result
     */
    PageResult<TopologyCatalogDto> listTopologyCatalogTypes(ListTopologyCatalogTypesQuery query);
}
