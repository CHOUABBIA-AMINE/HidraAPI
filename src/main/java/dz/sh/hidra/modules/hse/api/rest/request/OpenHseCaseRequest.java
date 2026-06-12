/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OpenHseCaseRequest
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.api.rest.request
 *
 * @Description : REST request to open HSE case.
 *
 */
package dz.sh.hidra.modules.hse.api.rest.request;

import dz.sh.hidra.modules.hse.domain.value.HseCaseSourceType;

import java.time.Instant;

/**
 * REST request to open HSE case.
 */
public record OpenHseCaseRequest(
        String caseNumber,
        String title,
        String description,
        String caseTypeId,
        String severityId,
        String priorityId,
        HseCaseSourceType sourceType,
        String incidentReferenceId,
        String incidentCodeSnapshot,
        String incidentTitleSnapshot,
        String targetModule,
        String targetTypeCode,
        String targetId,
        String targetCodeSnapshot,
        String targetLabelSnapshot,
        Instant occurredAt,
        String reportedByActorId,
        String reportedByDisplayNameSnapshot,
        String responsibleOrganizationUnitId,
        String responsibleOrganizationUnitNameSnapshot,
        String workflowInstanceId
) {
}
