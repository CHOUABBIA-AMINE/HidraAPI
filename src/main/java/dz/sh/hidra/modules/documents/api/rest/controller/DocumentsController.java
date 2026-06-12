/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : DocumentsController
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
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

import dz.sh.hidra.modules.documents.api.rest.request.RegisterDocumentRequest;
import dz.sh.hidra.modules.documents.api.rest.request.UploadDocumentVersionRequest;
import dz.sh.hidra.modules.documents.api.rest.response.DocumentResponse;
import dz.sh.hidra.modules.documents.api.rest.response.DocumentVersionResponse;

/**
 * Framework-neutral documents controller contract.
 */
public interface DocumentsController {

    DocumentResponse registerDocument(RegisterDocumentRequest request);

    DocumentVersionResponse uploadDocumentVersion(UploadDocumentVersionRequest request);
}
