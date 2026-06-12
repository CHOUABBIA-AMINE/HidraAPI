/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrationCatalogTranslationRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : integration
 * @Package     : dz.sh.hidra.modules.integration.application.port.out
 *
 * @Description : Repository port for IntegrationCatalogTranslation.
 *
 */
package dz.sh.hidra.modules.integration.application.port.out;

import dz.sh.hidra.modules.integration.domain.model.IntegrationCatalogTranslation;

import java.util.Optional;

/**
 * Repository port for IntegrationCatalogTranslation.
 */
public interface IntegrationCatalogTranslationRepositoryPort {

    IntegrationCatalogTranslation save(IntegrationCatalogTranslation model);

    Optional<IntegrationCatalogTranslation> findById(String id);
}
