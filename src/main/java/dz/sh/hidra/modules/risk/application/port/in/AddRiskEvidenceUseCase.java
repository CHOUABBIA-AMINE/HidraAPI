/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AddRiskEvidenceUseCase
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.application.port.in
 *
 * @Description : Use case for linking risk evidence.
 *
 */
package dz.sh.hidra.modules.risk.application.port.in;

import dz.sh.hidra.modules.risk.application.command.AddRiskEvidenceCommand;

/**
 * Use case for linking risk evidence.
 */
public interface AddRiskEvidenceUseCase {

    String addRiskEvidence(AddRiskEvidenceCommand command);
}
