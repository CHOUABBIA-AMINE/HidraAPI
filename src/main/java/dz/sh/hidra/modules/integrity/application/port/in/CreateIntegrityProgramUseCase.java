/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateIntegrityProgramUseCase
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.application.port.in
 *
 * @Description : Use case for creating integrity programs.
 *
 */
package dz.sh.hidra.modules.integrity.application.port.in;

import dz.sh.hidra.modules.integrity.application.command.CreateIntegrityProgramCommand;
import dz.sh.hidra.modules.integrity.application.dto.IntegrityProgramSummaryDto;

/**
 * Use case for creating integrity programs.
 */
public interface CreateIntegrityProgramUseCase {

    IntegrityProgramSummaryDto createIntegrityProgram(CreateIntegrityProgramCommand command);
}
