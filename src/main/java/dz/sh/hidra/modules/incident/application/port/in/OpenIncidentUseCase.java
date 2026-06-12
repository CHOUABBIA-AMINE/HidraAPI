/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OpenIncidentUseCase
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.application.port.in
 *
 * @Description : Use case for opening incidents.
 *
 */
package dz.sh.hidra.modules.incident.application.port.in;

import dz.sh.hidra.modules.incident.application.command.OpenIncidentCommand;
import dz.sh.hidra.modules.incident.application.dto.IncidentSummaryDto;

/**
 * Use case for opening incidents.
 */
public interface OpenIncidentUseCase {

    IncidentSummaryDto openIncident(OpenIncidentCommand command);
}
