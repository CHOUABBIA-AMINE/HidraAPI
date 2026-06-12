/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentCatalogTranslationRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.application.port.out
 *
 * @Description : Repository port for DocumentCatalogTranslation.
 *
 */
package dz.sh.hidra.modules.documents.application.port.out;

import dz.sh.hidra.modules.documents.domain.model.DocumentCatalogTranslation;

import java.util.Optional;

/**
 * Repository port for DocumentCatalogTranslation.
 */
public interface DocumentCatalogTranslationRepositoryPort {

    DocumentCatalogTranslation save(DocumentCatalogTranslation model);

    Optional<DocumentCatalogTranslation> findById(String id);
}
