/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentRelatedIncidentRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.application.port.out
 *
 * @Description : Repository port for IncidentRelatedIncident.
 *
 */
package dz.sh.hidra.modules.incident.application.port.out;

import dz.sh.hidra.modules.incident.domain.model.IncidentRelatedIncident;

import java.util.Optional;

/**
 * Repository port for IncidentRelatedIncident.
 */
public interface IncidentRelatedIncidentRepositoryPort {

    IncidentRelatedIncident save(IncidentRelatedIncident model);

    Optional<IncidentRelatedIncident> findById(String id);
}
