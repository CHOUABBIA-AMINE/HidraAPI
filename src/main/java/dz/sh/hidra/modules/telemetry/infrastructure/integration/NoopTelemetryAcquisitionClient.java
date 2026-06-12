/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : NoopTelemetryAcquisitionClient
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.integration
 *
 * @Description : No-op telemetry acquisition client.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.integration;

import java.util.List;
import java.util.Map;

/**
 * No-op telemetry acquisition client.
 */
public class NoopTelemetryAcquisitionClient implements TelemetryAcquisitionClient {

    @Override
    public List<Map<String, String>> fetchPayloads(String sourceCode, String endpointCode) {
        return List.of();
    }
}
