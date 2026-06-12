/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsProjectionDefinitionRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.application.port.out
 *
 * @Description : Repository port for AnalyticsProjectionDefinition.
 *
 */
package dz.sh.hidra.modules.analytics.application.port.out;

import dz.sh.hidra.modules.analytics.domain.model.AnalyticsProjectionDefinition;

import java.util.Optional;

/**
 * Repository port for AnalyticsProjectionDefinition.
 */
public interface AnalyticsProjectionDefinitionRepositoryPort {

    AnalyticsProjectionDefinition save(AnalyticsProjectionDefinition model);

    Optional<AnalyticsProjectionDefinition> findById(String id);
}
