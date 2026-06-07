/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryReadingRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.port.out
 *
 * @Description : Outbound port for telemetry reading persistence.
 *
 */
package dz.sh.hidra.modules.telemetry.application.port.out;

import dz.sh.hidra.kernel.application.pagination.PageRequest;
import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryReading;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryIngestionBatchId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryPointId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryQualityCodeReference;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryReadingId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryReadingState;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryTimestamp;
import java.util.Optional;

/**
 * Outbound port for telemetry reading persistence.
 *
 * <p>Architecture role:
 * Outbound application port for telemetry. Infrastructure adapters implement this interface.
 * Application and domain layers must not depend on JPA, REST, SQL, Spring Data, or external module
 * implementation classes.
 */
public interface TelemetryReadingRepositoryPort {

    TelemetryReading save(TelemetryReading reading);

    Optional<TelemetryReading> findById(TelemetryReadingId id);

    Optional<TelemetryReading> findLatestByPointId(TelemetryPointId pointId);

    PageResult<TelemetryReading> findAll(
            TelemetryPointId pointId,
            TelemetryQualityCodeReference qualityCode,
            TelemetryReadingState state,
            TelemetryTimestamp fromSourceTimestamp,
            TelemetryTimestamp toSourceTimestamp,
            TelemetryIngestionBatchId ingestionBatchId,
            PageRequest pageRequest);

    boolean existsByPointIdAndSourceTimestamp(TelemetryPointId pointId, TelemetryTimestamp sourceTimestamp);

}
