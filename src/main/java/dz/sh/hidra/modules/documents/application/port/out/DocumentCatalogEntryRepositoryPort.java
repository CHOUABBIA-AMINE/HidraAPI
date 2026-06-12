/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentCatalogEntryRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.application.port.out
 *
 * @Description : Repository port for DocumentCatalogEntry.
 *
 */
package dz.sh.hidra.modules.documents.application.port.out;

import dz.sh.hidra.modules.documents.domain.model.DocumentCatalogEntry;

import java.util.Optional;

/**
 * Repository port for DocumentCatalogEntry.
 */
public interface DocumentCatalogEntryRepositoryPort {

    DocumentCatalogEntry save(DocumentCatalogEntry model);

    Optional<DocumentCatalogEntry> findById(String id);
}
