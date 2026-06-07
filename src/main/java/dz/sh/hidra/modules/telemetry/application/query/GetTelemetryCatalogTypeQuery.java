/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : GetTelemetryCatalogTypeQuery
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.query
 *
 * @Description : Query to get a telemetry catalog entry by identifier.
 *
 */
package dz.sh.hidra.modules.telemetry.application.query;

import dz.sh.hidra.kernel.application.pagination.PageRequest;
import dz.sh.hidra.kernel.application.query.Query;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryTypeCatalogId;
import java.util.Objects;

/**
 * Query to get a telemetry catalog entry by identifier.
 *
 * <p>Architecture role:
 * Application-layer query contract for the telemetry module. It carries validated
 * domain value objects and catalog references only. It must not depend on persistence, REST, Spring,
 * JPA, topology implementation classes, flow, risk, analytics, workflow, reporting, or notification.
 */
public record GetTelemetryCatalogTypeQuery(
        TelemetryTypeCatalogId typeId,
        String locale) implements Query {

    public GetTelemetryCatalogTypeQuery {
        typeId = Objects.requireNonNull(typeId, "GetTelemetryCatalogTypeQuery typeId must not be null.");
    }
}
