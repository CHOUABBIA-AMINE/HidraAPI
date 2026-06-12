/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsInsightEvidenceRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.application.port.out
 *
 * @Description : Repository port for AnalyticsInsightEvidence.
 *
 */
package dz.sh.hidra.modules.analytics.application.port.out;

import dz.sh.hidra.modules.analytics.domain.model.AnalyticsInsightEvidence;

import java.util.Optional;

/**
 * Repository port for AnalyticsInsightEvidence.
 */
public interface AnalyticsInsightEvidenceRepositoryPort {

    AnalyticsInsightEvidence save(AnalyticsInsightEvidence model);

    Optional<AnalyticsInsightEvidence> findById(String id);
}
