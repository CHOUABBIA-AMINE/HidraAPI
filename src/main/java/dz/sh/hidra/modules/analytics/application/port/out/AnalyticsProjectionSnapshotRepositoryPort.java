/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsProjectionSnapshotRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.application.port.out
 *
 * @Description : Repository port for AnalyticsProjectionSnapshot.
 *
 */
package dz.sh.hidra.modules.analytics.application.port.out;

import dz.sh.hidra.modules.analytics.domain.model.AnalyticsProjectionSnapshot;

import java.util.Optional;

/**
 * Repository port for AnalyticsProjectionSnapshot.
 */
public interface AnalyticsProjectionSnapshotRepositoryPort {

    AnalyticsProjectionSnapshot save(AnalyticsProjectionSnapshot model);

    Optional<AnalyticsProjectionSnapshot> findById(String id);
}
