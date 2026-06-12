/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RegisterExternalSystemUseCase
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.application.port.in
 *
 * @Description : Use case for registering external systems.
 *
 */
package dz.sh.hidra.modules.integration.application.port.in;

import dz.sh.hidra.modules.integration.application.command.RegisterExternalSystemCommand;
import dz.sh.hidra.modules.integration.application.dto.ExternalSystemSummaryDto;

/**
 * Use case for registering external systems.
 */
public interface RegisterExternalSystemUseCase {

    ExternalSystemSummaryDto registerExternalSystem(RegisterExternalSystemCommand command);
}
