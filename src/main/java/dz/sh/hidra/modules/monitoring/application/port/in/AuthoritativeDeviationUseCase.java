/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthoritativeDeviationUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-13
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.application.port.in
 *
 * @Description : Evaluates and reads protected Monitoring deviation receipts.
 *
 */
package dz.sh.hidra.modules.monitoring.application.port.in;

import dz.sh.hidra.modules.monitoring.domain.model.AuthoritativeDeviationEvaluation;
import java.time.Instant;
import java.util.List;

public interface AuthoritativeDeviationUseCase {
    record Request(String targetId, String trustedReadingId, Instant evaluatedAt) { }

    AuthoritativeDeviationEvaluation evaluate(String ruleId, Request request);
    AuthoritativeDeviationEvaluation find(String ruleId, String evaluationId);
    List<AuthoritativeDeviationEvaluation> history(String ruleId, int limit);
}
