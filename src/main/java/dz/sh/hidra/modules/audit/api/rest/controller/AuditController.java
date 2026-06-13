/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
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
import dz.sh.hidra.modules.audit.api.rest.request.*;
import dz.sh.hidra.modules.audit.api.rest.response.*;

/**
 * Framework-neutral audit controller contract.
 */
public interface AuditController {
    AuditAccessRecordResponse recordAuditAccess(RecordAuditAccessRequest request);
    AuditEventResponse recordAuditEvent(RecordAuditEventRequest request);
    AuditExportRequestResponse requestAuditExport(RequestAuditExportRequest request);
}
