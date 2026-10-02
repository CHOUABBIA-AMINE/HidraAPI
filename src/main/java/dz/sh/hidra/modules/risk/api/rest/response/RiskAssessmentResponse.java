/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskAssessmentResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.api.rest.response
 *
 * @Description : REST response for risk assessment.
 *
 */
package dz.sh.hidra.modules.risk.api.rest.response;

import dz.sh.hidra.modules.risk.domain.value.RiskAssessmentStatus;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * REST response for risk assessment.
 */
public record RiskAssessmentResponse(
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
