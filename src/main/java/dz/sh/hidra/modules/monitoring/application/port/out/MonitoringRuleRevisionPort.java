/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringRuleRevisionPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-13
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.application.port.out
 *
 * @Description : Durably stores governed immutable revisions and serialized active heads.
 *
 */
package dz.sh.hidra.modules.monitoring.application.port.out;

import dz.sh.hidra.modules.monitoring.domain.model.MonitoringRuleRevision;
import dz.sh.hidra.modules.monitoring.domain.value.MonitoringRuleType;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface MonitoringRuleRevisionPort {
    record RuleScope(String ruleId,String company,String assetType,String assetId){ }
    record RuleContext(String telemetryPointId,MonitoringRuleType ruleType,
                       dz.sh.hidra.modules.monitoring.domain.value.MonitoringLifecycleStatus status){ }
    void register(String ruleId,String company,String assetType,String assetId,String actor);
    Optional<RuleScope> scope(String ruleId);
    RuleContext ruleContext(String ruleId);
    boolean policyAvailable(String code);
    MonitoringRuleRevision create(MonitoringRuleRevision revision);
    MonitoringRuleRevision activate(String ruleId,long revision,long expectedHead,
                                    Instant from,Instant until,String actor);
    Optional<MonitoringRuleRevision> exact(String ruleId,long revision);
    Optional<MonitoringRuleRevision> effective(String ruleId,Instant at);
    List<MonitoringRuleRevision> history(String ruleId,int limit);
}
