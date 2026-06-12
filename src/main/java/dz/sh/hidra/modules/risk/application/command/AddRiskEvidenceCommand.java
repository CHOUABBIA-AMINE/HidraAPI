/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AddRiskEvidenceCommand
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.application.command
 *
 * @Description : Command to link evidence to a risk assessment.
 *
 */
package dz.sh.hidra.modules.risk.application.command;

import java.time.Instant;

/**
 * Command to link evidence to a risk assessment.
 */
public record AddRiskEvidenceCommand(
        String riskAssessmentId,
        String evidenceModule,
        String evidenceType,
        String evidenceId,
        String evidenceCodeSnapshot,
        String evidenceLabelSnapshot,
        Instant evidenceTimestamp,
        String evidenceHash,
        String evidenceSummary
) {
}
