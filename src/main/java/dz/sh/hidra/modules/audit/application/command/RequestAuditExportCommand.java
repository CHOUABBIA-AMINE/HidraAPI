/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RequestAuditExportCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.application.command
 *
 * @Description : Command to request an audit export.
 *
 */
package dz.sh.hidra.modules.audit.application.command;

/**
 * Command to request an audit export.
 */
public record RequestAuditExportCommand(
        String requestedByActorId,
        String requestedByDisplayNameSnapshot,
        String purposeId,
        String filterJson,
        String format,
        String workflowInstanceId
) {
}
