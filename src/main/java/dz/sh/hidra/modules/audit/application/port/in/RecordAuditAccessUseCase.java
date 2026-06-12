/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RecordAuditAccessUseCase
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.application.port.in
 *
 * @Description : Use case for recording audit access.
 *
 */
package dz.sh.hidra.modules.audit.application.port.in;

import dz.sh.hidra.modules.audit.application.command.RecordAuditAccessCommand;
import dz.sh.hidra.modules.audit.application.dto.AuditAccessRecordSummaryDto;

/**
 * Use case for recording audit access.
 */
public interface RecordAuditAccessUseCase {

    AuditAccessRecordSummaryDto recordAuditAccess(RecordAuditAccessCommand command);
}
