/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : GetTelemetryDeviceByIdQuery
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.query
 *
 * @Description : Query to get a telemetry device by identifier.
 *
 */
package dz.sh.hidra.modules.telemetry.application.query;

import java.util.Objects;

import dz.sh.hidra.kernel.application.query.Query;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryDeviceId;

/**
 * Query to get a telemetry device by identifier.
 *
 * <p>Architecture role:
 * Application-layer query contract for the telemetry module. It carries validated
 * domain value objects and catalog references only. It must not depend on persistence, REST, Spring,
 * JPA, topology implementation classes, flow, risk, analytics, workflow, reporting, or notification.
 */
public record GetTelemetryDeviceByIdQuery(
        TelemetryDeviceId deviceId) implements Query {

    public GetTelemetryDeviceByIdQuery {
        deviceId = Objects.requireNonNull(deviceId, "GetTelemetryDeviceByIdQuery deviceId must not be null.");
    }
}
