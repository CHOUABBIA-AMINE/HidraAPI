/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ListTelemetryDevicesQuery
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-05-30
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.query
 *
 * @Description : Query to list telemetry devices.
 *
 */
package dz.sh.hidra.modules.telemetry.application.query;

import dz.sh.hidra.kernel.application.pagination.PageRequest;
import dz.sh.hidra.kernel.application.query.Query;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryDeviceStatus;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetryDeviceTypeReference;
import dz.sh.hidra.modules.telemetry.domain.value.TelemetrySourceId;
import java.util.Objects;

/**
 * Query to list telemetry devices.
 *
 * <p>Architecture role:
 * Application-layer query contract for the telemetry module. It carries validated
 * domain value objects and catalog references only. It must not depend on persistence, REST, Spring,
 * JPA, topology implementation classes, flow, risk, analytics, workflow, reporting, or notification.
 */
public record ListTelemetryDevicesQuery(
        String searchText,
        TelemetrySourceId sourceId,
        TelemetryDeviceTypeReference deviceType,
        TelemetryDeviceStatus status,
        PageRequest pageRequest) implements Query {

    public ListTelemetryDevicesQuery {
        pageRequest = Objects.requireNonNull(pageRequest, "ListTelemetryDevicesQuery pageRequest must not be null.");
    }
}
