/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportRequestResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.api.rest.response
 *
 * @Description : REST response for report request.
 *
 */
package dz.sh.hidra.modules.reporting.api.rest.response;

import dz.sh.hidra.modules.reporting.domain.value.ReportRequestStatus;

import java.time.Instant;

/**
 * REST response for report request.
 */
public record ReportRequestResponse(
        String id,
        String reportDefinitionId,
        String requestedByActorId,
        String requestedByDisplayNameSnapshot,
        String organizationUnitId,
        Instant requestedAt,
        ReportRequestStatus status,
        String correlationId,
        String workflowReferenceId
) {
}
