/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IncidentController
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
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

import dz.sh.hidra.modules.incident.api.rest.request.OpenIncidentRequest;
import dz.sh.hidra.modules.incident.api.rest.request.RecordIncidentResponseActionRequest;
import dz.sh.hidra.modules.incident.api.rest.response.IncidentResponse;

/**
 * Framework-neutral incident controller contract.
 */
public interface IncidentController {

    IncidentResponse openIncident(OpenIncidentRequest request);

    String recordResponseAction(RecordIncidentResponseActionRequest request);
}
