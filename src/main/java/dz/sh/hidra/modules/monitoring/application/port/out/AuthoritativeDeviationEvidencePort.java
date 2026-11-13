/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthoritativeDeviationEvidencePort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-13
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.application.port.out
 *
 * @Description : Resolves owner-qualified scalar evidence for a rule.
 *
 */
package dz.sh.hidra.modules.monitoring.application.port.out;

import dz.sh.hidra.modules.monitoring.domain.model.AuthoritativeDeviationEvaluation.Observation;
import dz.sh.hidra.modules.monitoring.domain.model.AuthoritativeDeviationEvaluation.Target;
import dz.sh.hidra.modules.monitoring.domain.model.MonitoringRuleRevision;
import java.time.Instant;
import java.util.Optional;

public interface AuthoritativeDeviationEvidencePort {
    record Expected(Target provenance, String pointId, String unitId) { }
    record Actual(Observation provenance, String unitId, boolean qualified) { }

    Optional<Expected> target(String id, MonitoringRuleRevision rule, Instant at);
    Optional<Actual> observation(String id, MonitoringRuleRevision rule);
}
