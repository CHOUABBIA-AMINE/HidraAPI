/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentTimelineEntryRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.application.port.out
 *
 * @Description : Repository port for IncidentTimelineEntry.
 *
 */
package dz.sh.hidra.modules.incident.application.port.out;

import dz.sh.hidra.modules.incident.domain.model.IncidentTimelineEntry;

import java.util.Optional;

/**
 * Repository port for IncidentTimelineEntry.
 */
public interface IncidentTimelineEntryRepositoryPort {

    IncidentTimelineEntry save(IncidentTimelineEntry model);

    Optional<IncidentTimelineEntry> findById(String id);
}
