/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : UploadDocumentBinaryVersionUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-13
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.application.port.in
 *
 * @Description : Application contract for authoritative multipart document-version upload.
 *
 */
package dz.sh.hidra.modules.documents.application.port.in;

import dz.sh.hidra.modules.documents.application.command.UploadDocumentBinaryVersionCommand;
import dz.sh.hidra.modules.documents.application.dto.DocumentVersionSummaryDto;

public interface UploadDocumentBinaryVersionUseCase {
    DocumentVersionSummaryDto uploadDocumentBinaryVersion(UploadDocumentBinaryVersionCommand command);
}
