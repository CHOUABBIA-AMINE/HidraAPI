/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyCatalogRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.port.out
 *
 * @Description : Outbound persistence port for topology type catalogs.
 *
 */
package dz.sh.hidra.modules.topology.application.port.out;

import java.util.Optional;

import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.modules.topology.application.query.ListTopologyCatalogTypesQuery;
import dz.sh.hidra.modules.topology.domain.model.TopologyTypeCatalog;
import dz.sh.hidra.modules.topology.domain.value.TopologyCode;

/**
 * Outbound persistence port for topology type catalogs.
 *
 * <p>Business role:
 * Provides application services with access to multilingual configurable topology type catalog
 * entries without coupling them to SQL, JPA, or Spring Data.
 *
 * <p>Architecture role:
 * Infrastructure adapters implement this interface in a later task. Application services depend on
 * this port only.
 *
 * <p>Validation:
 * Implementations must preserve domain validation by returning restored TopologyTypeCatalog domain
 * models, not persistence entities.
 *
 * <p>Usage:
 * Implement this port from topology catalog persistence adapters in COR-008.
 */
public interface TopologyCatalogRepositoryPort {

    /**
     * Finds a catalog entry by identifier.
     *
     * @param id catalog entry identifier
     * @return optional catalog entry
     */
    Optional<TopologyTypeCatalog> findById(String id);

    /**
     * Finds a catalog entry by catalog name and code.
     *
     * @param catalogName catalog name, for example FACILITY_TYPE
     * @param code language-neutral catalog code
     * @return optional catalog entry
     */
    Optional<TopologyTypeCatalog> findByCatalogNameAndCode(String catalogName, TopologyCode code);

    /**
     * Lists catalog entries according to application filtering and pagination criteria.
     *
     * @param query list query
     * @return paged catalog result
     */
    PageResult<TopologyTypeCatalog> findAll(ListTopologyCatalogTypesQuery query);
}
