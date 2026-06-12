/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryAcquisitionClient
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Infrastructure
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.infrastructure.integration
 *
 * @Description : External acquisition client contract.
 *
 */
package dz.sh.hidra.modules.telemetry.infrastructure.integration;

import java.util.List;
import java.util.Map;

/**
 * External acquisition client contract.
 */
public interface TelemetryAcquisitionClient {

    List<Map<String, String>> fetchPayloads(String sourceCode, String endpointCode);
}
