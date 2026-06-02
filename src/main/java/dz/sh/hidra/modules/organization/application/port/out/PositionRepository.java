/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PositionRepository
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.port.out
 *
 * @Description : Outbound position persistence port.
 *
 */
package dz.sh.hidra.modules.organization.application.port.out;

import java.util.List;
import java.util.Optional;

import dz.sh.hidra.modules.organization.domain.model.Position;
import dz.sh.hidra.modules.organization.domain.value.PositionCode;
import dz.sh.hidra.modules.organization.domain.value.PositionId;

/**
 * Defines the outbound persistence contract for positions.
 *
 * <p>Business role:
 * This port persists and retrieves operational positions/functions such as Station Team Leader,
 * Station Boss, Region Director, Gas Flux Director, or Department Chief. These are not identity roles.
 *
 * <p>Architecture role:
 * This is an application outbound port implemented later by infrastructure. It must not depend on
 * Spring Data, JPA entities, REST DTOs, identity domain objects, or topology domain objects.
 *
 * <p>Validation:
 * Implementations must preserve position code uniqueness and return domain models, not persistence
 * entities.
 *
 * <p>Usage:
 * Application services depend on this interface when loading or saving positions.
 */
public interface PositionRepository {

    /**
     * Saves a position.
     *
     * @param position position to save
     * @return saved position
     */
    Position save(Position position);

    /**
     * Finds a position by identifier.
     *
     * @param id position identifier
     * @return position when found
     */
    Optional<Position> findById(PositionId id);

    /**
     * Finds a position by business code.
     *
     * @param code position code
     * @return position when found
     */
    Optional<Position> findByCode(PositionCode code);

    /**
     * Checks whether a position code already exists.
     *
     * @param code position code
     * @return true when the code exists
     */
    boolean existsByCode(PositionCode code);

    /**
     * Lists active positions available for assignment.
     *
     * @return active positions
     */
    List<Position> findActivePositions();
}
