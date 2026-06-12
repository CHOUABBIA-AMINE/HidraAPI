/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentAccessGrantRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.application.port.out
 *
 * @Description : Repository port for DocumentAccessGrant.
 *
 */
package dz.sh.hidra.modules.documents.application.port.out;

import dz.sh.hidra.modules.documents.domain.model.DocumentAccessGrant;

import java.util.Optional;

/**
 * Repository port for DocumentAccessGrant.
 */
public interface DocumentAccessGrantRepositoryPort {

    DocumentAccessGrant save(DocumentAccessGrant model);

    Optional<DocumentAccessGrant> findById(String id);
}
