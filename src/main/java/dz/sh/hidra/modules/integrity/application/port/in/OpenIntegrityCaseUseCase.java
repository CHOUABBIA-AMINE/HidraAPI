/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OpenIntegrityCaseUseCase
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.application.port.in
 *
 * @Description : Use case for opening integrity cases.
 *
 */
package dz.sh.hidra.modules.integrity.application.port.in;

import dz.sh.hidra.modules.integrity.application.command.OpenIntegrityCaseCommand;
import dz.sh.hidra.modules.integrity.application.dto.IntegrityCaseSummaryDto;

/**
 * Use case for opening integrity cases.
 */
public interface OpenIntegrityCaseUseCase {

    IntegrityCaseSummaryDto openIntegrityCase(OpenIntegrityCaseCommand command);
}
