/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AnalyticsCatalogTranslationRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : analytics
 * @Package     : dz.sh.hidra.modules.analytics.application.port.out
 *
 * @Description : Repository port for AnalyticsCatalogTranslation.
 *
 */
package dz.sh.hidra.modules.analytics.application.port.out;

import dz.sh.hidra.modules.analytics.domain.model.AnalyticsCatalogTranslation;

import java.util.Optional;

/**
 * Repository port for AnalyticsCatalogTranslation.
 */
public interface AnalyticsCatalogTranslationRepositoryPort {

    AnalyticsCatalogTranslation save(AnalyticsCatalogTranslation model);

    Optional<AnalyticsCatalogTranslation> findById(String id);
}
