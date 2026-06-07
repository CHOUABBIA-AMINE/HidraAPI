/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryIngestionBatchRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.port.out
 *
 * @Description : Outbound port for telemetry ingestion batch persistence.
 *
 */
package dz.sh.hidra.modules.telemetry.application.port.out;

import dz.sh.hidra.kernel.application.pagination.PageRequest;
import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryIngestionBatch;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryIngestionBatchId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryIngestionBatchStatus;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetrySourceId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryTimestamp;
import java.util.Optional;

/**
 * Outbound port for telemetry ingestion batch persistence.
 *
 * <p>Architecture role:
 * Outbound application port for telemetry. Infrastructure adapters implement this interface.
 * Application and domain layers must not depend on JPA, REST, SQL, Spring Data, or external module
 * implementation classes.
 */
public interface TelemetryIngestionBatchRepositoryPort {

    TelemetryIngestionBatch save(TelemetryIngestionBatch batch);

    Optional<TelemetryIngestionBatch> findById(TelemetryIngestionBatchId id);

    PageResult<TelemetryIngestionBatch> findAll(
            TelemetrySourceId sourceId,
            TelemetryIngestionBatchStatus status,
            TelemetryTimestamp startedFrom,
            TelemetryTimestamp startedTo,
            PageRequest pageRequest);

}
