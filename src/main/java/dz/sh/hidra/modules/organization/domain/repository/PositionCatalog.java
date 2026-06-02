/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PositionCatalog
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Domain
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.domain.repository
 *
 * @Description : Domain contract for looking up organizational positions.
 *
 */
package dz.sh.hidra.modules.organization.domain.repository;

import java.util.List;
import java.util.Optional;

import dz.sh.hidra.modules.organization.domain.model.Position;
import dz.sh.hidra.modules.organization.domain.value.PositionCode;
import dz.sh.hidra.modules.organization.domain.value.PositionId;

/**
 * Defines the domain lookup contract for organizational positions.
 *
 * <p>Business role:
 * The catalog allows domain and application logic to look up positions such as Station Team
 * Leader, Station Boss, Region Director, Gas Flux Director, or Department Chief without knowing
 * how positions are persisted.
 *
 * <p>Architecture role:
 * This is a domain repository contract. It is not a Spring Data repository and must not import
 * JPA, Spring, REST DTOs, identity, topology, or platform infrastructure.
 *
 * <p>Validation:
 * Implementations must preserve uniqueness of position codes and must not return persistence
 * entities to domain callers.
 *
 * <p>Usage:
 * Implement this contract later in infrastructure or bridge it through application ports when
 * position lookup is required by domain-oriented services.
 */
public interface PositionCatalog {

    /**
     * Finds a position by its stable identifier.
     *
     * @param id position identifier
     * @return matching position when present
     */
    Optional<Position> findById(PositionId id);

    /**
     * Finds a position by its unique business code.
     *
     * @param code position code
     * @return matching position when present
     */
    Optional<Position> findByCode(PositionCode code);

    /**
     * Checks whether a position exists for the given business code.
     *
     * @param code position code
     * @return true when a position exists for the code
     */
    boolean existsByCode(PositionCode code);

    /**
     * Lists active positions available for operational assignment.
     *
     * @return active positions
     */
    List<Position> findActivePositions();
}
