/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentsController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Interface
 * @Layer       : API
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.api.rest.controller
 *
 * @Description : Framework-neutral documents controller contract.
 *
 */
package dz.sh.hidra.modules.documents.api.rest.controller;
import dz.sh.hidra.modules.documents.api.rest.request.*;
import dz.sh.hidra.modules.documents.api.rest.response.*;

/**
 * Framework-neutral documents controller contract.
 */
public interface DocumentsController {
    DocumentTargetLinkResponse linkDocumentToTarget(LinkDocumentToTargetRequest request);
    DocumentResponse registerDocument(RegisterDocumentRequest request);
    DocumentVersionResponse uploadDocumentVersion(UploadDocumentVersionRequest request);
}
