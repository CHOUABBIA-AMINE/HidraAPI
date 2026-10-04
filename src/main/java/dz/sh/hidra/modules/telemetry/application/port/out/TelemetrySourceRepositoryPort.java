/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetrySourceRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-04
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.port.out
 *
 * @Description : Repository port for TelemetrySource and source-owned catalog eligibility.
 *
 */
package dz.sh.hidra.modules.telemetry.application.port.out;

import dz.sh.hidra.modules.telemetry.domain.model.TelemetrySource;
import java.util.Optional;

/**
 * Repository port for TelemetrySource.
 */
public interface TelemetrySourceRepositoryPort {

    TelemetrySource save(TelemetrySource model);

    Optional<TelemetrySource> findById(String id);

    boolean existsByCode(String code);

    boolean activeCatalogEntryExists(String id, String catalogName);
}
