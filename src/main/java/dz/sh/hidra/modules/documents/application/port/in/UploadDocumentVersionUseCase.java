/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : UploadDocumentVersionUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.application.port.in
 *
 * @Description : Use case for uploading document version metadata.
 *
 */
package dz.sh.hidra.modules.documents.application.port.in;

import dz.sh.hidra.modules.documents.application.command.UploadDocumentVersionCommand;
import dz.sh.hidra.modules.documents.application.dto.DocumentVersionSummaryDto;

/**
 * Use case for uploading document version metadata.
 */
public interface UploadDocumentVersionUseCase {

    DocumentVersionSummaryDto uploadDocumentVersion(UploadDocumentVersionCommand command);
}
