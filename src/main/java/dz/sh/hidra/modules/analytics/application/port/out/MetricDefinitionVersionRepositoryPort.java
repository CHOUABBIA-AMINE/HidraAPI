/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MetricDefinitionVersionRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.application.port.out
 *
 * @Description : Repository port for MetricDefinitionVersion.
 *
 */
package dz.sh.hidra.modules.analytics.application.port.out;

import dz.sh.hidra.modules.analytics.domain.model.MetricDefinitionVersion;

import java.util.Optional;

/**
 * Repository port for MetricDefinitionVersion.
 */
public interface MetricDefinitionVersionRepositoryPort {

    MetricDefinitionVersion save(MetricDefinitionVersion model);

    Optional<MetricDefinitionVersion> findById(String id);
}
