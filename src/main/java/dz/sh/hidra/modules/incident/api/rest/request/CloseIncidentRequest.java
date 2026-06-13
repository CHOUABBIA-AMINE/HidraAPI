/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CloseIncidentRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : incident
 * @Package     : dz.sh.hidra.modules.incident.api.rest.request
 *
 * @Description : REST request for close incident.
 *
 */
package dz.sh.hidra.modules.incident.api.rest.request;

/**
 * REST request for close incident.
 */
public record CloseIncidentRequest(
        String incidentId,
        String closureSummary,
        boolean resolutionVerified,
        boolean evidenceReviewed,
        boolean rootCauseReviewed,
        boolean followUpActionsCreated,
        String closedByActorId,
        String closedByActorNameSnapshot,
        String workflowInstanceId
) {
}
