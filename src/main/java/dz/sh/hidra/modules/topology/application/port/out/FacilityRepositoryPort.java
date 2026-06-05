/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : FacilityRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.port.out
 *
 * @Description : Outbound persistence port for facility.
 *
 */
package dz.sh.hidra.modules.topology.application.port.out;

import java.util.Optional;

import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.modules.topology.application.query.ListFacilitiesQuery;
import dz.sh.hidra.modules.topology.domain.model.Facility;
import dz.sh.hidra.modules.topology.domain.value.FacilityId;
import dz.sh.hidra.modules.topology.domain.value.TopologyCode;

/**
 * Outbound persistence port for facility.
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
public interface FacilityRepositoryPort {

    /**
     * Saves facility.
     *
     * @param facility domain model
     * @return saved domain model
     */
    Facility save(Facility facility);

    /**
     * Finds facility by identifier.
     *
     * @param id identifier
     * @return optional domain model
     */
    Optional<Facility> findById(FacilityId id);

    /**
     * Finds facility by business code.
     *
     * @param code business code
     * @return optional domain model
     */
    Optional<Facility> findByCode(TopologyCode code);

    /**
     * Checks whether facility exists with the supplied business code.
     *
     * @param code business code
     * @return true when a matching model exists
     */
    boolean existsByCode(TopologyCode code);

    /**
     * Lists facility according to application filtering and pagination criteria.
     *
     * @param query list query
     * @return paged domain model result
     */
    PageResult<Facility> findAll(ListFacilitiesQuery query);

}
