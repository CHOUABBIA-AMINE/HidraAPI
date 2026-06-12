/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryQuarantineRecordRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.port.out
 *
 * @Description : Repository port for TelemetryQuarantineRecord.
 *
 */
package dz.sh.hidra.modules.telemetry.application.port.out;

import dz.sh.hidra.modules.telemetry.domain.model.TelemetryQuarantineRecord;

import java.util.Optional;

/**
 * Repository port for TelemetryQuarantineRecord.
 */
public interface TelemetryQuarantineRecordRepositoryPort {

    TelemetryQuarantineRecord save(TelemetryQuarantineRecord model);

    Optional<TelemetryQuarantineRecord> findById(String id);
}
