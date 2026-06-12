/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityAssessmentResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.api.rest.response
 *
 * @Description : REST response for integrity assessment.
 *
 */
package dz.sh.hidra.modules.integrity.api.rest.response;

import dz.sh.hidra.modules.integrity.domain.value.IntegrityAssessmentStatus;

import java.time.Instant;

/**
 * REST response for integrity assessment.
 */
public record IntegrityAssessmentResponse(
        String id,
        String programId,
        String assessmentNumber,
        String title,
        String assessmentTypeId,
        IntegrityAssessmentStatus status,
        Instant assessmentDate
) {
}
