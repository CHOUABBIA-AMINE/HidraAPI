/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityCatalogTranslationRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.application.port.out
 *
 * @Description : Repository port for IntegrityCatalogTranslation.
 *
 */
package dz.sh.hidra.modules.integrity.application.port.out;

import dz.sh.hidra.modules.integrity.domain.model.IntegrityCatalogTranslation;

import java.util.Optional;

/**
 * Repository port for IntegrityCatalogTranslation.
 */
public interface IntegrityCatalogTranslationRepositoryPort {

    IntegrityCatalogTranslation save(IntegrityCatalogTranslation model);

    Optional<IntegrityCatalogTranslation> findById(String id);
}
