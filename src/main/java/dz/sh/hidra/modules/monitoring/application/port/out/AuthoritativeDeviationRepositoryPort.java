/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthoritativeDeviationRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-13
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.application.port.out
 *
 * @Description : Persists immutable evaluation receipts and resolves pinned freshness policy.
 *
 */
package dz.sh.hidra.modules.monitoring.application.port.out;

import dz.sh.hidra.modules.monitoring.domain.model.AuthoritativeDeviationEvaluation;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;

public interface AuthoritativeDeviationRepositoryPort {
    record Key(String ruleId, String targetId, String trustedReadingId, Instant evaluatedAt) { }

    OptionalLong maximumAgeSeconds(String ruleId, long revision);
    Optional<AuthoritativeDeviationEvaluation> byRequest(Key key);
    AuthoritativeDeviationEvaluation save(Key key, AuthoritativeDeviationEvaluation evaluation);
    /** Internal owner lookup only; callers must subsequently authorize through the Monitoring use case. */
    Optional<String> ruleIdByEvaluationId(String id);
    Optional<AuthoritativeDeviationEvaluation> find(String ruleId, String id);
    List<AuthoritativeDeviationEvaluation> history(String ruleId, int limit);
}
