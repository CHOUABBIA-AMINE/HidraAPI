/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : OpenCustodyMeasurementPeriodUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.application.port.in
 *
 * @Description : Use case for opening custody measurement periods.
 *
 */
package dz.sh.hidra.modules.custody.application.port.in;

import dz.sh.hidra.modules.custody.application.command.OpenCustodyMeasurementPeriodCommand;
import dz.sh.hidra.modules.custody.application.dto.CustodyMeasurementPeriodSummaryDto;

/**
 * Use case for opening custody measurement periods.
 */
public interface OpenCustodyMeasurementPeriodUseCase {

    CustodyMeasurementPeriodSummaryDto openMeasurementPeriod(OpenCustodyMeasurementPeriodCommand command);
}
