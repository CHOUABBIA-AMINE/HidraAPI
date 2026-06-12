/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsDatasetLineageRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.application.port.out
 *
 * @Description : Repository port for AnalyticsDatasetLineage.
 *
 */
package dz.sh.hidra.modules.analytics.application.port.out;

import dz.sh.hidra.modules.analytics.domain.model.AnalyticsDatasetLineage;

import java.util.Optional;

/**
 * Repository port for AnalyticsDatasetLineage.
 */
public interface AnalyticsDatasetLineageRepositoryPort {

    AnalyticsDatasetLineage save(AnalyticsDatasetLineage model);

    Optional<AnalyticsDatasetLineage> findById(String id);
}
