/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditController
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : API
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.api.rest.controller
 *
 * @Description : Framework-neutral audit controller contract.
 *
 */
package dz.sh.hidra.modules.audit.api.rest.controller;

import dz.sh.hidra.modules.audit.api.rest.request.RecordAuditEventRequest;
import dz.sh.hidra.modules.audit.api.rest.request.RequestAuditExportRequest;
import dz.sh.hidra.modules.audit.api.rest.response.AuditEventResponse;
import dz.sh.hidra.modules.audit.api.rest.response.AuditExportRequestResponse;

/**
 * Framework-neutral audit controller contract.
 */
public interface AuditController {

    AuditEventResponse recordAuditEvent(RecordAuditEventRequest request);

    AuditExportRequestResponse requestAuditExport(RequestAuditExportRequest request);
}
