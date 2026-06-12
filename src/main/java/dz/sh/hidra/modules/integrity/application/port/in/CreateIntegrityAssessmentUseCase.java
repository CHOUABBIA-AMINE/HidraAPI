/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateIntegrityAssessmentUseCase
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.application.port.in
 *
 * @Description : Use case for creating integrity assessments.
 *
 */
package dz.sh.hidra.modules.integrity.application.port.in;

import dz.sh.hidra.modules.integrity.application.command.CreateIntegrityAssessmentCommand;
import dz.sh.hidra.modules.integrity.application.dto.IntegrityAssessmentSummaryDto;

/**
 * Use case for creating integrity assessments.
 */
public interface CreateIntegrityAssessmentUseCase {

    IntegrityAssessmentSummaryDto createIntegrityAssessment(CreateIntegrityAssessmentCommand command);
}
