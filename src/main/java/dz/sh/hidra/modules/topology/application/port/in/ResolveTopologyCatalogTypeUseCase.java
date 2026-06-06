/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ResolveTopologyCatalogTypeUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.port.in
 *
 * @Description : Inbound port for resolving topology catalog types by catalog name and code.
 *
 */
package dz.sh.hidra.modules.topology.application.port.in;

import dz.sh.hidra.modules.topology.application.dto.TopologyCatalogDto;
import dz.sh.hidra.modules.topology.application.query.ResolveTopologyCatalogTypeQuery;

/**
 * Inbound port for resolving topology catalog types by catalog name and code.
 *
 * <p>Business role:
 * Resolves language-neutral topology type codes into catalog entries with localized labels.
 *
 * <p>Architecture role:
 * This port supports the transition away from enum-based asset type fields while keeping API and
 * application callers decoupled from persistence implementation.
 *
 * <p>Validation:
 * Implementations reject inactive catalog entries when the query requires active values for create or
 * update workflows.
 *
 * <p>Usage:
 * Use this interface before create/update commands that need a validated topology type reference.
 */
public interface ResolveTopologyCatalogTypeUseCase {

    /**
     * Resolves one topology catalog type by catalog name and code.
     *
     * @param query use-case input
     * @return catalog DTO
     */
    TopologyCatalogDto resolveTopologyCatalogType(ResolveTopologyCatalogTypeQuery query);
}
