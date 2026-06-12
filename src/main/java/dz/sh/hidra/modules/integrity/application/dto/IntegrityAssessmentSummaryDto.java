/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityAssessmentSummaryDto
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.application.dto
 *
 * @Description : Integrity assessment summary DTO.
 *
 */
package dz.sh.hidra.modules.integrity.application.dto;

import dz.sh.hidra.modules.integrity.domain.value.IntegrityAssessmentStatus;

import java.time.Instant;

/**
 * Integrity assessment summary DTO.
 */
public record IntegrityAssessmentSummaryDto(
        String id,
        String programId,
        String assessmentNumber,
        String title,
        String assessmentTypeId,
        IntegrityAssessmentStatus status,
        Instant assessmentDate
) {
}
