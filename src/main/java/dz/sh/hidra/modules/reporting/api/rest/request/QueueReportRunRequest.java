/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : QueueReportRunRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.api.rest.request
 *
 * @Description : REST request for queue report run.
 *
 */
package dz.sh.hidra.modules.reporting.api.rest.request;

import dz.sh.hidra.modules.reporting.domain.value.ReportRunMode;

/**
 * REST request for queue report run.
 */
public record QueueReportRunRequest(
        String reportRequestId,
        String reportDefinitionId,
        String templateVersionId,
        ReportRunMode runMode,
        String correlationId
) {
}
