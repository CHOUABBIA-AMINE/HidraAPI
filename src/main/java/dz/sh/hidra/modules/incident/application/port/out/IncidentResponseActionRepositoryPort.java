/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentResponseActionRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.application.port.out
 *
 * @Description : Repository port for IncidentResponseAction.
 *
 */
package dz.sh.hidra.modules.incident.application.port.out;

import dz.sh.hidra.modules.incident.domain.model.IncidentResponseAction;

import java.util.Optional;

/**
 * Repository port for IncidentResponseAction.
 */
public interface IncidentResponseActionRepositoryPort {

    IncidentResponseAction save(IncidentResponseAction model);

    Optional<IncidentResponseAction> findById(String id);
}
