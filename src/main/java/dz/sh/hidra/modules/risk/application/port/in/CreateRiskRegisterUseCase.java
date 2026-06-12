/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateRiskRegisterUseCase
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.application.port.in
 *
 * @Description : Use case for creating risk registers.
 *
 */
package dz.sh.hidra.modules.risk.application.port.in;

import dz.sh.hidra.modules.risk.application.command.CreateRiskRegisterCommand;
import dz.sh.hidra.modules.risk.application.dto.RiskRegisterSummaryDto;

/**
 * Use case for creating risk registers.
 */
public interface CreateRiskRegisterUseCase {

    RiskRegisterSummaryDto createRiskRegister(CreateRiskRegisterCommand command);
}
