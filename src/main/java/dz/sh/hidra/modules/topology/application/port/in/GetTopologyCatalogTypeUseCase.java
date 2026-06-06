/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : GetTopologyCatalogTypeUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.port.in
 *
 * @Description : Inbound port for retrieving one topology catalog type.
 *
 */
package dz.sh.hidra.modules.topology.application.port.in;

import dz.sh.hidra.modules.topology.application.dto.TopologyCatalogDto;
import dz.sh.hidra.modules.topology.application.query.GetTopologyCatalogTypeQuery;

/**
 * Inbound port for retrieving one topology catalog type.
 *
 * <p>Business role:
 * Exposes lookup of one configurable topology controlled vocabulary entry by identifier.
 *
 * <p>Architecture role:
 * API controllers and other callers depend on this interface, not on application service
 * implementation, domain models, repositories, JPA, or SQL.
 *
 * <p>Validation:
 * The implementation resolves localized labels with fallback behavior.
 *
 * <p>Usage:
 * Use this interface when a caller already knows the catalog entry identifier.
 */
public interface GetTopologyCatalogTypeUseCase {

    /**
     * Retrieves one topology catalog type.
     *
     * @param query use-case input
     * @return catalog DTO
     */
    TopologyCatalogDto getTopologyCatalogType(GetTopologyCatalogTypeQuery query);
}
