/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RecordIncidentResponseActionUseCase
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.application.port.in
 *
 * @Description : Use case for recording incident response actions.
 *
 */
package dz.sh.hidra.modules.incident.application.port.in;

import dz.sh.hidra.modules.incident.application.command.RecordIncidentResponseActionCommand;

/**
 * Use case for recording incident response actions.
 */
public interface RecordIncidentResponseActionUseCase {

    String recordResponseAction(RecordIncidentResponseActionCommand command);
}
