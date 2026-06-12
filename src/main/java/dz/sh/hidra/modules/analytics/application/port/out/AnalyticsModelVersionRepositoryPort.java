/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsModelVersionRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.application.port.out
 *
 * @Description : Repository port for AnalyticsModelVersion.
 *
 */
package dz.sh.hidra.modules.analytics.application.port.out;

import dz.sh.hidra.modules.analytics.domain.model.AnalyticsModelVersion;

import java.util.Optional;

/**
 * Repository port for AnalyticsModelVersion.
 */
public interface AnalyticsModelVersionRepositoryPort {

    AnalyticsModelVersion save(AnalyticsModelVersion model);

    Optional<AnalyticsModelVersion> findById(String id);
}
