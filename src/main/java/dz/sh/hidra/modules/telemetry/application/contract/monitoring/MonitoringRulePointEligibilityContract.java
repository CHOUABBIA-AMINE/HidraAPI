/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringRulePointEligibilityContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-13
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : telemetry
 * @Package     : dz.sh.hidra.modules.telemetry.application.contract.monitoring
 *
 * @Description : Exports current bound point and unit eligibility for Monitoring.
 *
 */
package dz.sh.hidra.modules.telemetry.application.contract.monitoring;

import java.time.Instant;

/** Owner facts only; this is not an Identity grant or an engineering unit conversion. */
public interface MonitoringRulePointEligibilityContract {
    void requireEligible(String pointId,String assetType,String assetId,String unitId,Instant at);
}
