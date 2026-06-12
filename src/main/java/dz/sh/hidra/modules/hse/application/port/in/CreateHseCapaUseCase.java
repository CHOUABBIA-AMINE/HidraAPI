/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateHseCapaUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.application.port.in
 *
 * @Description : Use case for creating HSE CAPA.
 *
 */
package dz.sh.hidra.modules.hse.application.port.in;

import dz.sh.hidra.modules.hse.application.command.CreateHseCapaCommand;
import dz.sh.hidra.modules.hse.application.dto.HseCapaSummaryDto;

/**
 * Use case for creating HSE CAPA.
 */
public interface CreateHseCapaUseCase {

    HseCapaSummaryDto createHseCapa(CreateHseCapaCommand command);
}
