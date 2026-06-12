/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuditExportRequestResponse
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.api.rest.response
 *
 * @Description : REST response for audit export request.
 *
 */
package dz.sh.hidra.modules.audit.api.rest.response;

import dz.sh.hidra.modules.audit.domain.value.AuditExportStatus;

import java.time.Instant;

/**
 * REST response for audit export request.
 */
public record AuditExportRequestResponse(
        String id,
        String requestedByActorId,
        String purposeId,
        String format,
        AuditExportStatus status,
        Integer recordCount,
        Instant requestedAt,
        Instant completedAt
) {
}
