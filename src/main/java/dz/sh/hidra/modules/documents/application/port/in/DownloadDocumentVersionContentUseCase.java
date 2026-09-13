/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DownloadDocumentVersionContentUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-13
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.application.port.in
 *
 * @Description : Application contract for retrieving document-version binary content.
 *
 */
package dz.sh.hidra.modules.documents.application.port.in;

import dz.sh.hidra.modules.documents.application.dto.DocumentVersionContentDto;

public interface DownloadDocumentVersionContentUseCase {
    DocumentVersionContentDto downloadDocumentVersionContent(String versionId);
}
