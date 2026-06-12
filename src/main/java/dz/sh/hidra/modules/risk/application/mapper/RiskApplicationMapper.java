/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskApplicationMapper
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Utility
 * @Layer       : Application
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.application.mapper
 *
 * @Description : Maps risk domain models to DTOs.
 *
 */
package dz.sh.hidra.modules.risk.application.mapper;

import dz.sh.hidra.modules.risk.application.dto.RiskAssessmentSummaryDto;
import dz.sh.hidra.modules.risk.application.dto.RiskRegisterSummaryDto;
import dz.sh.hidra.modules.risk.domain.model.RiskAssessment;
import dz.sh.hidra.modules.risk.domain.model.RiskRegister;

/**
 * Maps risk domain models to DTOs.
 */
public final class RiskApplicationMapper {

    private RiskApplicationMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static RiskRegisterSummaryDto toSummary(RiskRegister register) {
        return new RiskRegisterSummaryDto(register.id(), register.code(), register.nameFr(), register.registerTypeId(), register.scopeType(), register.scopeId(), register.status());
    }

    public static RiskAssessmentSummaryDto toSummary(RiskAssessment assessment) {
        return new RiskAssessmentSummaryDto(
                assessment.id(),
                assessment.riskRegisterId(),
                assessment.assessmentNumber(),
                assessment.title(),
                assessment.riskScenarioId(),
                assessment.status(),
                assessment.inherentScore(),
                assessment.inherentRatingId(),
                assessment.residualScore(),
                assessment.residualRatingId(),
                assessment.assessmentDate()
        );
    }
}
