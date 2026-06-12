/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RequestReportRequest
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.api.rest.request
 *
 * @Description : REST request to request report generation.
 *
 */
package dz.sh.hidra.modules.reporting.api.rest.request;

/**
 * REST request to request report generation.
 */
public record RequestReportRequest(
        String reportDefinitionId,
        String requestedByActorId,
        String requestedByUsernameSnapshot,
        String requestedByDisplayNameSnapshot,
        String requestedByRoleCodeSnapshot,
        String organizationUnitId,
        String organizationUnitNameSnapshot,
        String purpose,
        String correlationId,
        String workflowReferenceId
) {
}
