/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsDatasetVersionRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.application.port.out
 *
 * @Description : Repository port for AnalyticsDatasetVersion.
 *
 */
package dz.sh.hidra.modules.analytics.application.port.out;

import dz.sh.hidra.modules.analytics.domain.model.AnalyticsDatasetVersion;

import java.util.Optional;

/**
 * Repository port for AnalyticsDatasetVersion.
 */
public interface AnalyticsDatasetVersionRepositoryPort {

    AnalyticsDatasetVersion save(AnalyticsDatasetVersion model);

    Optional<AnalyticsDatasetVersion> findById(String id);
}
