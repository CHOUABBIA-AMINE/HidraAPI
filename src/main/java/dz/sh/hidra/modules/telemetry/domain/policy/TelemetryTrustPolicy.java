/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TelemetryTrustPolicy
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-06
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.domain.policy
 *
 * @Description : Defines the accepted downstream trust levels without upgrading assessment evidence.
 *
 */
package dz.sh.hidra.modules.telemetry.domain.policy;

import dz.sh.hidra.modules.telemetry.domain.value.TrustLevel;

public final class TelemetryTrustPolicy {
    private TelemetryTrustPolicy() { }
    public static boolean accepts(TrustLevel level) {
        return level == TrustLevel.MEDIUM || level == TrustLevel.HIGH || level == TrustLevel.CERTIFIED;
    }
}
