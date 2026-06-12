/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ShiftAssignmentRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : organization
 * @Package     : dz.sh.hidra.modules.organization.application.port.out
 *
 * @Description : Repository port for ShiftAssignment.
 *
 */
package dz.sh.hidra.modules.organization.application.port.out;

import dz.sh.hidra.modules.organization.domain.model.ShiftAssignment;

import java.util.Optional;

/**
 * Repository port for ShiftAssignment.
 */
public interface ShiftAssignmentRepositoryPort {

    ShiftAssignment save(ShiftAssignment model);

    Optional<ShiftAssignment> findById(String id);
}
