/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RecordAuditAccessCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.application.command
 *
 * @Description : Command to record access to audit evidence.
 *
 */
package dz.sh.hidra.modules.audit.application.command;

import dz.sh.hidra.modules.audit.domain.value.AuditAccessType;

/**
 * Command to record access to audit evidence.
 */
public record RecordAuditAccessCommand(
        String actorId,
        String actorDisplayNameSnapshot,
        AuditAccessType accessType,
        String auditEventId,
        String searchFilterHash,
        String exportRequestId,
        Integer resultCount,
        String purposeText,
        String correlationId
) {
}
