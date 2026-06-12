/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentVersionRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.application.port.out
 *
 * @Description : Repository port for DocumentVersion.
 *
 */
package dz.sh.hidra.modules.documents.application.port.out;

import dz.sh.hidra.modules.documents.domain.model.DocumentVersion;

import java.util.Optional;

/**
 * Repository port for DocumentVersion.
 */
public interface DocumentVersionRepositoryPort {

    DocumentVersion save(DocumentVersion model);

    Optional<DocumentVersion> findById(String id);
}
