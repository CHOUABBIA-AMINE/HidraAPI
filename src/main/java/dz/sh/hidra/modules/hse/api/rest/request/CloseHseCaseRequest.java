/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CloseHseCaseRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.api.rest.request
 *
 * @Description : REST request for close hse case.
 *
 */
package dz.sh.hidra.modules.hse.api.rest.request;

/**
 * REST request for close hse case.
 */
public record CloseHseCaseRequest(
        String hseCaseId,
        String closureSummary,
        boolean impactAssessed,
        boolean capaCompleted,
        boolean evidenceReviewed,
        boolean regulatoryReviewed,
        String closedByActorId,
        String closedByDisplayNameSnapshot,
        String workflowInstanceId
) {
}
