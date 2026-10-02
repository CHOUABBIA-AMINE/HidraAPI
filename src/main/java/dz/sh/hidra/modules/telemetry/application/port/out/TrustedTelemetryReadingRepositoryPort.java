/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TrustedTelemetryReadingRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.port.out
 *
 * @Description : Repository port for TrustedTelemetryReading.
 *
 */
package dz.sh.hidra.modules.telemetry.application.port.out;

import dz.sh.hidra.modules.telemetry.domain.model.TrustedTelemetryReading;

import java.util.Optional;

/**
 * Repository port for TrustedTelemetryReading.
 */
public interface TrustedTelemetryReadingRepositoryPort {

    TrustedTelemetryReading save(TrustedTelemetryReading model);

    Optional<TrustedTelemetryReading> findById(String id);
}
