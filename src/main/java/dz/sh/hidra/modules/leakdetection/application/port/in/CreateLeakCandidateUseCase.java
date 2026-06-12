/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateLeakCandidateUseCase
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.application.port.in
 *
 * @Description : Use case for creating leak candidates.
 *
 */
package dz.sh.hidra.modules.leakdetection.application.port.in;

import dz.sh.hidra.modules.leakdetection.application.command.CreateLeakCandidateCommand;
import dz.sh.hidra.modules.leakdetection.application.dto.LeakCandidateSummaryDto;

/**
 * Use case for creating leak candidates.
 */
public interface CreateLeakCandidateUseCase {

    LeakCandidateSummaryDto createLeakCandidate(CreateLeakCandidateCommand command);
}
