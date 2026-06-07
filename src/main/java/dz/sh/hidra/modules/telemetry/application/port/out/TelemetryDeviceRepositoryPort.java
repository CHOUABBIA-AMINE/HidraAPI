/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryDeviceRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.port.out
 *
 * @Description : Outbound port for telemetry device persistence.
 *
 */
package dz.sh.hidra.modules.telemetry.application.port.out;

import dz.sh.hidra.kernel.application.pagination.PageRequest;
import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryDevice;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryCode;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryDeviceId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryDeviceStatus;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryDeviceTypeReference;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetrySourceId;
import java.util.Optional;

/**
 * Outbound port for telemetry device persistence.
 *
 * <p>Architecture role:
 * Outbound application port for telemetry. Infrastructure adapters implement this interface.
 * Application and domain layers must not depend on JPA, REST, SQL, Spring Data, or external module
 * implementation classes.
 */
public interface TelemetryDeviceRepositoryPort {

    TelemetryDevice save(TelemetryDevice device);

    Optional<TelemetryDevice> findById(TelemetryDeviceId id);

    Optional<TelemetryDevice> findByCode(TelemetryCode code);

    boolean existsByCode(TelemetryCode code);

    PageResult<TelemetryDevice> findAll(
            String searchText,
            TelemetrySourceId sourceId,
            TelemetryDeviceTypeReference deviceType,
            TelemetryDeviceStatus status,
            PageRequest pageRequest);

}
