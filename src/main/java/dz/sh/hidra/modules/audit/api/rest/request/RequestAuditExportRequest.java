/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RequestAuditExportRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.api.rest.request
 *
 * @Description : REST request to request audit export.
 *
 */
package dz.sh.hidra.modules.audit.api.rest.request;

/**
 * REST request to request audit export.
 */
public record RequestAuditExportRequest(
        String requestedByActorId,
        String requestedByDisplayNameSnapshot,
        String purposeId,
        String filterJson,
        String format,
        String workflowInstanceId
) {
}
