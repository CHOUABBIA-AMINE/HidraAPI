/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RecordAuditAccessRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.api.rest.request
 *
 * @Description : REST request for record audit access.
 *
 */
package dz.sh.hidra.modules.audit.api.rest.request;

import dz.sh.hidra.modules.audit.domain.value.AuditAccessType;

/**
 * REST request for record audit access.
 */
public record RecordAuditAccessRequest(
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
