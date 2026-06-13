/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SpringDocumentsController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.api.rest.controller
 *
 * @Description : Spring MVC adapter exposing documents REST endpoints.
 *
 */
package dz.sh.hidra.modules.documents.api.rest.controller;

import dz.sh.hidra.modules.documents.api.rest.mapper.DocumentsRestMapper;
import dz.sh.hidra.modules.documents.api.rest.request.RegisterDocumentRequest;
import dz.sh.hidra.modules.documents.api.rest.request.UploadDocumentVersionRequest;
import dz.sh.hidra.modules.documents.api.rest.response.DocumentResponse;
import dz.sh.hidra.modules.documents.api.rest.response.DocumentVersionResponse;
import dz.sh.hidra.modules.documents.application.port.in.RegisterDocumentUseCase;
import dz.sh.hidra.modules.documents.application.port.in.UploadDocumentVersionUseCase;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Objects;

/**
 * Spring MVC adapter exposing documents REST endpoints.
 */
@RestController
@Validated
@RequestMapping("/api/v1/documents")
public class SpringDocumentsController implements DocumentsController {

    private final RegisterDocumentUseCase registerDocumentUseCase;
    private final UploadDocumentVersionUseCase uploadDocumentVersionUseCase;

    public SpringDocumentsController(
            RegisterDocumentUseCase registerDocumentUseCase,
            UploadDocumentVersionUseCase uploadDocumentVersionUseCase
    ) {
        this.registerDocumentUseCase = Objects.requireNonNull(registerDocumentUseCase, "RegisterDocumentUseCase must not be null.");
        this.uploadDocumentVersionUseCase = Objects.requireNonNull(uploadDocumentVersionUseCase, "UploadDocumentVersionUseCase must not be null.");
    }


    @Override
    @PostMapping("/register-document")
    public DocumentResponse registerDocument(@Valid @RequestBody RegisterDocumentRequest request) {
        Objects.requireNonNull(request, "RegisterDocumentRequest must not be null.");
        return DocumentsRestMapper.toResponse(registerDocumentUseCase.registerDocument(DocumentsRestMapper.toCommand(request)));
    }

    @Override
    @PostMapping("/upload-document-version")
    public DocumentVersionResponse uploadDocumentVersion(@Valid @RequestBody UploadDocumentVersionRequest request) {
        Objects.requireNonNull(request, "UploadDocumentVersionRequest must not be null.");
        return DocumentsRestMapper.toResponse(uploadDocumentVersionUseCase.uploadDocumentVersion(DocumentsRestMapper.toCommand(request)));
    }

}
