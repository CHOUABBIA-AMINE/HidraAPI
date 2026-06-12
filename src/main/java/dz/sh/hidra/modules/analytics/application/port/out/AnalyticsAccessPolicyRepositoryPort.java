/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsAccessPolicyRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.application.port.out
 *
 * @Description : Repository port for AnalyticsAccessPolicy.
 *
 */
package dz.sh.hidra.modules.analytics.application.port.out;

import dz.sh.hidra.modules.analytics.domain.model.AnalyticsAccessPolicy;

import java.util.Optional;

/**
 * Repository port for AnalyticsAccessPolicy.
 */
public interface AnalyticsAccessPolicyRepositoryPort {

    AnalyticsAccessPolicy save(AnalyticsAccessPolicy model);

    Optional<AnalyticsAccessPolicy> findById(String id);
}
