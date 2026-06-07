/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryCatalogRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.port.out
 *
 * @Description : Outbound port for telemetry catalog persistence.
 *
 */
package dz.sh.hidra.modules.telemetry.application.port.out;

import dz.sh.hidra.kernel.application.pagination.PageRequest;
import dz.sh.hidra.kernel.application.pagination.PageResult;
import dz.sh.hidra.modules.telemetry.domain.model.TelemetryTypeCatalog;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryCode;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryTypeCatalogId;
import java.util.Optional;

/**
 * Outbound port for telemetry catalog persistence.
 *
 * <p>Architecture role:
 * Outbound application port for telemetry. Infrastructure adapters implement this interface.
 * Application and domain layers must not depend on JPA, REST, SQL, Spring Data, or external module
 * implementation classes.
 */
public interface TelemetryCatalogRepositoryPort {

    TelemetryTypeCatalog save(TelemetryTypeCatalog catalog);

    Optional<TelemetryTypeCatalog> findById(TelemetryTypeCatalogId id);

    Optional<TelemetryTypeCatalog> findByCatalogNameAndCode(String catalogName, TelemetryCode code);

    PageResult<TelemetryTypeCatalog> findAll(String catalogName, Boolean active, PageRequest pageRequest);

    boolean existsByCatalogNameAndCode(String catalogName, TelemetryCode code);

}
