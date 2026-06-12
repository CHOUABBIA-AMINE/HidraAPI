/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ResidualRiskAssessmentRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.application.port.out
 *
 * @Description : Repository port for ResidualRiskAssessment.
 *
 */
package dz.sh.hidra.modules.risk.application.port.out;

import dz.sh.hidra.modules.risk.domain.model.ResidualRiskAssessment;

import java.util.Optional;

/**
 * Repository port for ResidualRiskAssessment.
 */
public interface ResidualRiskAssessmentRepositoryPort {

    ResidualRiskAssessment save(ResidualRiskAssessment model);

    Optional<ResidualRiskAssessment> findById(String id);
}
