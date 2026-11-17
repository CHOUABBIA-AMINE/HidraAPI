/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DeviationMetricReceiptPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-17
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.application.port.out
 *
 * @Description : Transactional replay, version eligibility and immutable receipt storage.
 *
 */
package dz.sh.hidra.modules.analytics.application.port.out;

import dz.sh.hidra.modules.analytics.application.port.in.DeviationMetricRunUseCase.Result;
import dz.sh.hidra.modules.analytics.application.port.in.DeviationMetricRunUseCase.Source;
import java.time.Instant;
import java.util.Optional;

public interface DeviationMetricReceiptPort {
    record Configuration(String definitionId, String unitId) { }
    void lock(String companyId, String facilityId, String definitionVersionId, String evaluationId);
    Optional<Result> byKey(String companyId, String facilityId, String definitionVersionId, String evaluationId);
    Optional<Result> byRun(String companyId, String facilityId, String runId);
    Configuration eligible(String versionId, String unitId, Instant evaluatedAt);
    void seal(String runId, String valueId, String companyId, String facilityId,
              String versionId, String evaluationId, String actorId, Source source);
}
