/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RequestAuditExportUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.application.port.in
 *
 * @Description : Use case for requesting audit exports.
 *
 */
package dz.sh.hidra.modules.audit.application.port.in;

import dz.sh.hidra.modules.audit.application.command.RequestAuditExportCommand;
import dz.sh.hidra.modules.audit.application.dto.AuditExportRequestSummaryDto;

/**
 * Use case for requesting audit exports.
 */
public interface RequestAuditExportUseCase {

    AuditExportRequestSummaryDto requestAuditExport(RequestAuditExportCommand command);
}
