/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CloseHseCaseUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.application.port.in
 *
 * @Description : Use case for closing HSE cases.
 *
 */
package dz.sh.hidra.modules.hse.application.port.in;

import dz.sh.hidra.modules.hse.application.command.CloseHseCaseCommand;

/**
 * Use case for closing HSE cases.
 */
public interface CloseHseCaseUseCase {

    String closeHseCase(CloseHseCaseCommand command);
}
