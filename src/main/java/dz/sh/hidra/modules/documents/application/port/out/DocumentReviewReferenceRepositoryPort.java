/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentReviewReferenceRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.application.port.out
 *
 * @Description : Repository port for DocumentReviewReference.
 *
 */
package dz.sh.hidra.modules.documents.application.port.out;

import dz.sh.hidra.modules.documents.domain.model.DocumentReviewReference;

import java.util.Optional;

/**
 * Repository port for DocumentReviewReference.
 */
public interface DocumentReviewReferenceRepositoryPort {

    DocumentReviewReference save(DocumentReviewReference model);

    Optional<DocumentReviewReference> findById(String id);
}
