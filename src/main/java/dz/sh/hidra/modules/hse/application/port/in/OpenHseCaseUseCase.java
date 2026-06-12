/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OpenHseCaseUseCase
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.application.port.in
 *
 * @Description : Use case for opening HSE cases.
 *
 */
package dz.sh.hidra.modules.hse.application.port.in;

import dz.sh.hidra.modules.hse.application.command.OpenHseCaseCommand;
import dz.sh.hidra.modules.hse.application.dto.HseCaseSummaryDto;

/**
 * Use case for opening HSE cases.
 */
public interface OpenHseCaseUseCase {

    HseCaseSummaryDto openHseCase(OpenHseCaseCommand command);
}
