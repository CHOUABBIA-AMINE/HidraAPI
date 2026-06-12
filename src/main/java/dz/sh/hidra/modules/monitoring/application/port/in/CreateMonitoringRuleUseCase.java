/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CreateMonitoringRuleUseCase
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.application.port.in
 *
 * @Description : Use case for creating monitoring rules.
 *
 */
package dz.sh.hidra.modules.monitoring.application.port.in;

import dz.sh.hidra.modules.monitoring.application.command.CreateMonitoringRuleCommand;
import dz.sh.hidra.modules.monitoring.application.dto.MonitoringRuleSummaryDto;

/**
 * Use case for creating monitoring rules.
 */
public interface CreateMonitoringRuleUseCase {

    MonitoringRuleSummaryDto createMonitoringRule(CreateMonitoringRuleCommand command);
}
