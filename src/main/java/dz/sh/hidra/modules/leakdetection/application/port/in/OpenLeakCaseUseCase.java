/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OpenLeakCaseUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.application.port.in
 *
 * @Description : Use case for opening leak cases.
 *
 */
package dz.sh.hidra.modules.leakdetection.application.port.in;

import dz.sh.hidra.modules.leakdetection.application.command.OpenLeakCaseCommand;
import dz.sh.hidra.modules.leakdetection.application.dto.LeakCaseSummaryDto;

/**
 * Use case for opening leak cases.
 */
public interface OpenLeakCaseUseCase {

    LeakCaseSummaryDto openLeakCase(OpenLeakCaseCommand command);
}
