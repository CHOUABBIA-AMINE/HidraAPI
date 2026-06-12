/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentExtractionRecordRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.application.port.out
 *
 * @Description : Repository port for DocumentExtractionRecord.
 *
 */
package dz.sh.hidra.modules.documents.application.port.out;

import dz.sh.hidra.modules.documents.domain.model.DocumentExtractionRecord;

import java.util.Optional;

/**
 * Repository port for DocumentExtractionRecord.
 */
public interface DocumentExtractionRecordRepositoryPort {

    DocumentExtractionRecord save(DocumentExtractionRecord model);

    Optional<DocumentExtractionRecord> findById(String id);
}
