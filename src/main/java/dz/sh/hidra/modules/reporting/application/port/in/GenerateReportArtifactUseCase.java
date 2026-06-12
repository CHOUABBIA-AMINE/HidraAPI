/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : GenerateReportArtifactUseCase
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.application.port.in
 *
 * @Description : Use case for registering generated report artifacts.
 *
 */
package dz.sh.hidra.modules.reporting.application.port.in;

import dz.sh.hidra.modules.reporting.application.command.GenerateReportArtifactCommand;
import dz.sh.hidra.modules.reporting.application.dto.ReportOutputArtifactSummaryDto;

/**
 * Use case for registering generated report artifacts.
 */
public interface GenerateReportArtifactUseCase {

    ReportOutputArtifactSummaryDto generateReportArtifact(GenerateReportArtifactCommand command);
}
