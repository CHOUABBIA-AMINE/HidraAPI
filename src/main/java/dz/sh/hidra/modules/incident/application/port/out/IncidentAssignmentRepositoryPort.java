/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentAssignmentRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.application.port.out
 *
 * @Description : Repository port for IncidentAssignment.
 *
 */
package dz.sh.hidra.modules.incident.application.port.out;

import dz.sh.hidra.modules.incident.domain.model.IncidentAssignment;

import java.util.Optional;

/**
 * Repository port for IncidentAssignment.
 */
public interface IncidentAssignmentRepositoryPort {

    IncidentAssignment save(IncidentAssignment model);

    Optional<IncidentAssignment> findById(String id);
}
