/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateIntegrityAssessmentRequest
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.api.rest.request
 *
 * @Description : REST request to create integrity assessment.
 *
 */
package dz.sh.hidra.modules.integrity.api.rest.request;

import java.time.Instant;

/**
 * REST request to create integrity assessment.
 */
public record CreateIntegrityAssessmentRequest(
        String programId,
        String assessmentNumber,
        String title,
        String description,
        String assessmentTypeId,
        String methodologyId,
        Instant assessmentDate,
        String assessedByActorId,
        String workflowInstanceId
) {
}
