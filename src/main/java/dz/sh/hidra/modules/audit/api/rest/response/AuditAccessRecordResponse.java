/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditAccessRecordResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.api.rest.response
 *
 * @Description : REST response for audit access record.
 *
 */
package dz.sh.hidra.modules.audit.api.rest.response;

import dz.sh.hidra.modules.audit.domain.value.AuditAccessType;
import java.time.Instant;

/**
 * REST response for audit access record.
 */
public record AuditAccessRecordResponse(
        String id,
        String actorId,
        AuditAccessType accessType,
        String auditEventId,
        String exportRequestId,
        Integer resultCount,
        Instant accessedAt
) {
}
