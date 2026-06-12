/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentRetentionRecordRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.application.port.out
 *
 * @Description : Repository port for DocumentRetentionRecord.
 *
 */
package dz.sh.hidra.modules.documents.application.port.out;

import dz.sh.hidra.modules.documents.domain.model.DocumentRetentionRecord;

import java.util.Optional;

/**
 * Repository port for DocumentRetentionRecord.
 */
public interface DocumentRetentionRecordRepositoryPort {

    DocumentRetentionRecord save(DocumentRetentionRecord model);

    Optional<DocumentRetentionRecord> findById(String id);
}
