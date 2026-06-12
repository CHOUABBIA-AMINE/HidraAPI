/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateTelemetrySourceUseCase
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.port.in
 *
 * @Description : Use case for creating telemetry sources.
 *
 */
package dz.sh.hidra.modules.telemetry.application.port.in;

import dz.sh.hidra.modules.telemetry.application.command.CreateTelemetrySourceCommand;
import dz.sh.hidra.modules.telemetry.application.dto.TelemetrySourceSummaryDto;

/**
 * Use case for creating telemetry sources.
 */
public interface CreateTelemetrySourceUseCase {

    TelemetrySourceSummaryDto createTelemetrySource(CreateTelemetrySourceCommand command);
}
