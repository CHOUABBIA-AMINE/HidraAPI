/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : LeakEvaluationTriggerPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-14
 * @Type        : Class
 * @Layer       : Application
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.application.port.out
 * @Description : T13 governed source-linked suspected leak candidate delivery.
 */
package dz.sh.hidra.modules.leakdetection.application.port.out;

import dz.sh.hidra.modules.leakdetection.domain.model.LeakEvaluationTrigger;
import java.util.Optional;

public interface LeakEvaluationTriggerPort {
    /** PostgreSQL transaction-scoped synchronization precedes all read/write attempts. */
    void serialize(String evaluationId);
    Optional<LeakEvaluationTrigger> byEvaluation(String evaluationId);
    Optional<LeakEvaluationTrigger> byCandidate(String candidateId);
    void save(LeakEvaluationTrigger trigger);
}
