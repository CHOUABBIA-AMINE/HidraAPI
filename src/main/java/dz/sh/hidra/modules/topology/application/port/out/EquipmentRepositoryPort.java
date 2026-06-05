/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EquipmentRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.port.out
 *
 * @Description : Outbound persistence port for topology equipment.
 *
 */
package dz.sh.hidra.modules.topology.application.port.out;

import java.util.Optional;

import dz.sh.hidra.modules.topology.domain.model.Equipment;
import dz.sh.hidra.modules.topology.domain.value.EquipmentId;
import dz.sh.hidra.modules.topology.domain.value.TopologyCode;

/**
 * Outbound persistence port for topology equipment.
 *
 * <p>Business role:
 * This port defines persistence operations required for topology equipment/component references.
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
public interface EquipmentRepositoryPort {

    /**
     * Saves topology equipment.
     *
     * @param equipment domain model
     * @return saved domain model
     */
    Equipment save(Equipment equipment);

    /**
     * Finds topology equipment by identifier.
     *
     * @param id equipment identifier
     * @return optional domain model
     */
    Optional<Equipment> findById(EquipmentId id);

    /**
     * Finds topology equipment by business code.
     *
     * @param code business code
     * @return optional domain model
     */
    Optional<Equipment> findByCode(TopologyCode code);

    /**
     * Checks whether topology equipment exists with the supplied business code.
     *
     * @param code business code
     * @return true when a matching model exists
     */
    boolean existsByCode(TopologyCode code);
}
