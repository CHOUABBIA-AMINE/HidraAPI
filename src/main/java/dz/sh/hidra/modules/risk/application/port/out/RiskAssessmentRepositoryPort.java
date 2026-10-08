/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskAssessmentRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-08
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.application.port.out
 *
 * @Description : Repository port for RiskAssessment.
 *
 */
package dz.sh.hidra.modules.risk.application.port.out;

import dz.sh.hidra.modules.risk.domain.model.RiskAssessment;

import java.util.Optional;

/**
 * Repository port for RiskAssessment.
 */
public interface RiskAssessmentRepositoryPort {

    default RiskAssessment create(RiskAssessment model,
            java.util.List<dz.sh.hidra.modules.risk.application.command.RiskAssessmentScopeInput> scopes) {
        throw new UnsupportedOperationException("Governed creation required.");
    }
    default RiskAssessment score(dz.sh.hidra.modules.risk.application.command.ScoreRiskAssessmentCommand command) {
        throw new UnsupportedOperationException("Governed scoring required.");
    }
    default RiskAssessment submit(String id) { throw new UnsupportedOperationException("Governed submission required."); }
    default RiskAssessment approve(dz.sh.hidra.modules.risk.application.command.ApproveRiskAssessmentCommand command) {
        throw new UnsupportedOperationException("Governed approval required.");
    }
    RiskAssessment save(RiskAssessment model);

    Optional<RiskAssessment> findById(String id);
}
