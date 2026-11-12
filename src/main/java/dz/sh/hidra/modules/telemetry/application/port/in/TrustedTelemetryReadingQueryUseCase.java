/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TrustedTelemetryReadingQueryUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-12
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.port.in
 *
 * @Description : Publishes and reads immutable trusted observations with current owner authorization.
 *
 */
package dz.sh.hidra.modules.telemetry.application.port.in;

import dz.sh.hidra.modules.telemetry.application.port.out.TelemetryTrustedReadPort.Published;
import java.util.List;

public interface TrustedTelemetryReadingQueryUseCase {
    Published publish(String companyPartyId, String pointId, String readingId);
    Published find(String companyPartyId, String pointId, String trustedId);
    List<Published> history(String companyPartyId, String pointId, int limit);
}
