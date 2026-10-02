/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Interface
 * @Layer       : API
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.api.rest.controller
 *
 * @Description : Framework-neutral monitoring controller contract.
 *
 */
package dz.sh.hidra.modules.monitoring.api.rest.controller;
import dz.sh.hidra.modules.monitoring.api.rest.request.*;
import dz.sh.hidra.modules.monitoring.api.rest.response.*;

/**
 * Framework-neutral monitoring controller contract.
 */
public interface MonitoringController {
    MonitoringRuleResponse createMonitoringRule(CreateMonitoringRuleRequest request);
    DeviationResponse recordDeviation(RecordDeviationRequest request);
}
