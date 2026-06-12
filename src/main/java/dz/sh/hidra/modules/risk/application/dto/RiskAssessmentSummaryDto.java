/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskAssessmentSummaryDto
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.application.dto
 *
 * @Description : Risk assessment summary DTO.
 *
 */
package dz.sh.hidra.modules.risk.application.dto;

import dz.sh.hidra.modules.risk.domain.value.RiskAssessmentStatus;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Risk assessment summary DTO.
 */
public record RiskAssessmentSummaryDto(
        String id,
        String riskRegisterId,
        String assessmentNumber,
        String title,
        String riskScenarioId,
        RiskAssessmentStatus status,
        BigDecimal inherentScore,
        String inherentRatingId,
        BigDecimal residualScore,
        String residualRatingId,
        Instant assessmentDate
) {
}
