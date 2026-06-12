/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateRiskAssessmentCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.application.command
 *
 * @Description : Command to create risk assessment.
 *
 */
package dz.sh.hidra.modules.risk.application.command;

import java.time.Instant;

/**
 * Command to create risk assessment.
 */
public record CreateRiskAssessmentCommand(
        String riskRegisterId,
        String assessmentNumber,
        String title,
        String description,
        String assessmentTypeId,
        String methodologyId,
        String scopeId,
        String riskScenarioId,
        Instant assessmentDate,
        Instant validFrom,
        Instant validTo,
        String assessedByActorId,
        String assessedByDisplayNameSnapshot
) {
}
