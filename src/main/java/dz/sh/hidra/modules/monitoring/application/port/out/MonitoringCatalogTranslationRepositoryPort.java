/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : MonitoringCatalogTranslationRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.application.port.out
 *
 * @Description : Repository port for MonitoringCatalogTranslation.
 *
 */
package dz.sh.hidra.modules.monitoring.application.port.out;

import dz.sh.hidra.modules.monitoring.domain.model.MonitoringCatalogTranslation;

import java.util.Optional;

/**
 * Repository port for MonitoringCatalogTranslation.
 */
public interface MonitoringCatalogTranslationRepositoryPort {

    MonitoringCatalogTranslation save(MonitoringCatalogTranslation model);

    Optional<MonitoringCatalogTranslation> findById(String id);
}
