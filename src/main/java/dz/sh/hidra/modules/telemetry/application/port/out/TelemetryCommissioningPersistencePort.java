/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryCommissioningPersistencePort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-12
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.port.out
 *
 * @Description : Owns transactional Telemetry commissioning persistence.
 *
 */
package dz.sh.hidra.modules.telemetry.application.port.out;

import dz.sh.hidra.modules.telemetry.application.port.in.TelemetryCommissioningUseCase.*;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface TelemetryCommissioningPersistencePort {
    Snapshot enroll(Enroll command);
    Snapshot rebind(Rebind command);
    Optional<Snapshot> effective(String pointId, Instant at);
    List<Snapshot> history(String pointId, int limit);
}
