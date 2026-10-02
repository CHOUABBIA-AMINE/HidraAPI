/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CloseIncidentUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.application.port.in
 *
 * @Description : Use case for closing incidents.
 *
 */
package dz.sh.hidra.modules.incident.application.port.in;

import dz.sh.hidra.modules.incident.application.command.CloseIncidentCommand;

/**
 * Use case for closing incidents.
 */
public interface CloseIncidentUseCase {

    String closeIncident(CloseIncidentCommand command);
}
