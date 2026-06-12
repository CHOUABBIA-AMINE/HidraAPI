/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsDataSourceReferenceRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.application.port.out
 *
 * @Description : Repository port for AnalyticsDataSourceReference.
 *
 */
package dz.sh.hidra.modules.analytics.application.port.out;

import dz.sh.hidra.modules.analytics.domain.model.AnalyticsDataSourceReference;

import java.util.Optional;

/**
 * Repository port for AnalyticsDataSourceReference.
 */
public interface AnalyticsDataSourceReferenceRepositoryPort {

    AnalyticsDataSourceReference save(AnalyticsDataSourceReference model);

    Optional<AnalyticsDataSourceReference> findById(String id);
}
