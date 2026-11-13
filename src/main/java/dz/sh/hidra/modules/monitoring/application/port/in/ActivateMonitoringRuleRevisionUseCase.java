/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ActivateMonitoringRuleRevisionUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-13
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.application.port.in
 *
 * @Description : Provides authorized Monitoring rule version lifecycle and readback.
 *
 */
package dz.sh.hidra.modules.monitoring.application.port.in;

import dz.sh.hidra.modules.monitoring.domain.model.MonitoringRuleRevision;
import java.time.Instant;
import java.util.List;

public interface ActivateMonitoringRuleRevisionUseCase {
    record Draft(String unitId,String evidencePolicyCode,Instant validFrom,Instant validTo){ }
    record Activation(long expectedHeadRevision,Instant effectiveFrom,Instant effectiveUntil){ }
    MonitoringRuleRevision revise(String ruleId,Draft draft);
    MonitoringRuleRevision activate(String ruleId,long revision,Activation activation);
    MonitoringRuleRevision exact(String ruleId,long revision);
    MonitoringRuleRevision effective(String ruleId,Instant at);
    List<MonitoringRuleRevision> history(String ruleId,int limit);
}
