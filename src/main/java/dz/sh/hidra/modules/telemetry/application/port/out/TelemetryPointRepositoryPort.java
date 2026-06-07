/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryPointRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.port.out
 *
 * @Description : Outbound port for telemetry point persistence.
 *
 */
package dz.sh.hidra.modules.telemetry.application.port.out;

import dz.sh.hidra.kernel.application.pagination.PageRequest;
import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryPoint;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryCode;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryDeviceId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryPointId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryPointStatus;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryPointTypeReference;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetrySignalTypeReference;
import java.util.Optional;

/**
 * Outbound port for telemetry point persistence.
 *
 * <p>Architecture role:
 * Outbound application port for telemetry. Infrastructure adapters implement this interface.
 * Application and domain layers must not depend on JPA, REST, SQL, Spring Data, or external module
 * implementation classes.
 */
public interface TelemetryPointRepositoryPort {

    TelemetryPoint save(TelemetryPoint point);

    Optional<TelemetryPoint> findById(TelemetryPointId id);

    Optional<TelemetryPoint> findByCode(TelemetryCode code);

    boolean existsByCode(TelemetryCode code);

    PageResult<TelemetryPoint> findAll(
            String searchText,
            TelemetryDeviceId deviceId,
            TelemetryPointTypeReference pointType,
            TelemetrySignalTypeReference signalType,
            TelemetryPointStatus status,
            PageRequest pageRequest);

}
