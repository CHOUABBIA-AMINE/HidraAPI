/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetrySourceRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.port.out
 *
 * @Description : Outbound port for telemetry source persistence.
 *
 */
package dz.sh.hidra.modules.telemetry.application.port.out;

import dz.sh.hidra.kernel.application.pagination.PageRequest;
import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetrySource;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryCode;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryProtocolReference;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetrySourceId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetrySourceStatus;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetrySourceTypeReference;
import java.util.Optional;

/**
 * Outbound port for telemetry source persistence.
 *
 * <p>Architecture role:
 * Outbound application port for telemetry. Infrastructure adapters implement this interface.
 * Application and domain layers must not depend on JPA, REST, SQL, Spring Data, or external module
 * implementation classes.
 */
public interface TelemetrySourceRepositoryPort {

    TelemetrySource save(TelemetrySource source);

    Optional<TelemetrySource> findById(TelemetrySourceId id);

    Optional<TelemetrySource> findByCode(TelemetryCode code);

    boolean existsByCode(TelemetryCode code);

    PageResult<TelemetrySource> findAll(
            String searchText,
            TelemetrySourceTypeReference sourceType,
            TelemetryProtocolReference protocol,
            TelemetrySourceStatus status,
            PageRequest pageRequest);

}
