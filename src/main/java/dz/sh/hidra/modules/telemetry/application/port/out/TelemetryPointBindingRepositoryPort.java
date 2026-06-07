/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryPointBindingRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.port.out
 *
 * @Description : Outbound port for telemetry point topology binding persistence.
 *
 */
package dz.sh.hidra.modules.telemetry.application.port.out;

import dz.sh.hidra.kernel.application.pagination.PageRequest;
import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryPointBinding;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryBindingRoleReference;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryCode;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryPointBindingId;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryPointId;
import java.util.List;
import java.util.Optional;

/**
 * Outbound port for telemetry point topology binding persistence.
 *
 * <p>Architecture role:
 * Outbound application port for telemetry. Infrastructure adapters implement this interface.
 * Application and domain layers must not depend on JPA, REST, SQL, Spring Data, or external module
 * implementation classes.
 */
public interface TelemetryPointBindingRepositoryPort {

    TelemetryPointBinding save(TelemetryPointBinding binding);

    Optional<TelemetryPointBinding> findById(TelemetryPointBindingId id);

    List<TelemetryPointBinding> findActiveByPointId(TelemetryPointId pointId);

    PageResult<TelemetryPointBinding> findAll(
            TelemetryPointId pointId,
            TelemetryCode assetTypeCode,
            String assetId,
            TelemetryBindingRoleReference bindingRole,
            Boolean active,
            PageRequest pageRequest);

}
