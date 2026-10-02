/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Interface
 * @Layer       : API
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.api.rest.controller
 *
 * @Description : Framework-neutral incident controller contract.
 *
 */
package dz.sh.hidra.modules.incident.api.rest.controller;
import dz.sh.hidra.modules.incident.api.rest.request.*;
import dz.sh.hidra.modules.incident.api.rest.response.*;

/**
 * Framework-neutral incident controller contract.
 */
public interface IncidentController {
    String closeIncident(CloseIncidentRequest request);
    IncidentResponse openIncident(OpenIncidentRequest request);
    String recordResponseAction(RecordIncidentResponseActionRequest request);
}
