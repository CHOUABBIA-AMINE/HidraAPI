/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ListTelemetryCatalogTypesUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.port.in
 *
 * @Description : Inbound port for listing telemetry catalog entries.
 *
 */
package dz.sh.hidra.modules.telemetry.application.port.in;

import dz.sh.hidra.modules.telemetry.application.dto.TelemetryCatalogDto;
import dz.sh.hidra.modules.telemetry.application.dto.TelemetryPageDto;
import dz.sh.hidra.modules.telemetry.application.query.ListTelemetryCatalogTypesQuery;

/**
 * Inbound port for listing telemetry catalog entries.
 *
 * <p>Architecture role:
 * Inbound application port for the telemetry module. It is implemented by application services and
 * called by adapters such as REST controllers or scheduled ingestion entry points.
 */
public interface ListTelemetryCatalogTypesUseCase {

    /**
     * Executes the use case.
     *
     * @param query use case input
     * @return use case result
     */
    TelemetryPageDto<TelemetryCatalogDto> listTelemetryCatalogTypes(ListTelemetryCatalogTypesQuery query);
}
