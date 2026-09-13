/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SpringDocumentsController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-13
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
import dz.sh.hidra.modules.documents.api.rest.request.LinkDocumentToTargetRequest;
import dz.sh.hidra.modules.documents.api.rest.request.RegisterDocumentRequest;
import dz.sh.hidra.modules.documents.api.rest.request.UploadDocumentBinaryVersionRequest;
import dz.sh.hidra.modules.documents.api.rest.request.UploadDocumentVersionRequest;
import dz.sh.hidra.modules.documents.api.rest.response.DocumentResponse;
import dz.sh.hidra.modules.documents.api.rest.response.DocumentTargetLinkResponse;
import dz.sh.hidra.modules.documents.api.rest.response.DocumentVersionResponse;
import dz.sh.hidra.modules.documents.application.command.UploadDocumentBinaryVersionCommand;
import dz.sh.hidra.modules.documents.application.dto.DocumentVersionContentDto;
import dz.sh.hidra.modules.documents.application.port.in.DownloadDocumentVersionContentUseCase;
import dz.sh.hidra.modules.documents.application.port.in.LinkDocumentToTargetUseCase;
import dz.sh.hidra.modules.documents.application.port.in.RegisterDocumentUseCase;
import dz.sh.hidra.modules.documents.application.port.in.UploadDocumentBinaryVersionUseCase;
import dz.sh.hidra.modules.documents.application.port.in.UploadDocumentVersionUseCase;
import dz.sh.hidra.modules.documents.domain.exception.DocumentContentTransferException;
import jakarta.validation.Valid;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Spring MVC adapter exposing documents REST endpoints.
 */
@RestController
@Validated
@RequestMapping("/api/v1/documents")
public class SpringDocumentsController implements DocumentsController {

    private final LinkDocumentToTargetUseCase linkDocumentToTargetUseCase;
    private final RegisterDocumentUseCase registerDocumentUseCase;
    private final UploadDocumentVersionUseCase uploadDocumentVersionUseCase;
    private final UploadDocumentBinaryVersionUseCase uploadDocumentBinaryVersionUseCase;
    private final DownloadDocumentVersionContentUseCase downloadDocumentVersionContentUseCase;
    private final long maxUploadBytes;

    public SpringDocumentsController(
            LinkDocumentToTargetUseCase linkDocumentToTargetUseCase,
            RegisterDocumentUseCase registerDocumentUseCase,
            UploadDocumentVersionUseCase uploadDocumentVersionUseCase,
            UploadDocumentBinaryVersionUseCase uploadDocumentBinaryVersionUseCase,
            DownloadDocumentVersionContentUseCase downloadDocumentVersionContentUseCase,
            @Value("${hidra.documents.upload.max-bytes:52428800}") long maxUploadBytes
    ) {
        this.linkDocumentToTargetUseCase = Objects.requireNonNull(linkDocumentToTargetUseCase, "LinkDocumentToTargetUseCase must not be null.");
        this.registerDocumentUseCase = Objects.requireNonNull(registerDocumentUseCase, "RegisterDocumentUseCase must not be null.");
        this.uploadDocumentVersionUseCase = Objects.requireNonNull(uploadDocumentVersionUseCase, "UploadDocumentVersionUseCase must not be null.");
        this.uploadDocumentBinaryVersionUseCase = Objects.requireNonNull(uploadDocumentBinaryVersionUseCase, "UploadDocumentBinaryVersionUseCase must not be null.");
        this.downloadDocumentVersionContentUseCase = Objects.requireNonNull(downloadDocumentVersionContentUseCase, "DownloadDocumentVersionContentUseCase must not be null.");
        if (maxUploadBytes <= 0L) {
            throw new IllegalArgumentException("Document upload max bytes must be positive.");
        }
        this.maxUploadBytes = maxUploadBytes;
    }

    @GetMapping("/capabilities")
    public Map<String, Object> capabilities() {
        return Map.of(
                "module", "documents",
                "mission", "Manage operational documents and link controlled document evidence to business targets.",
                "objectives", List.of(
                        "Register controlled documents.",
                        "Upload document versions.",
                        "Stream authorized document-version content.",
                        "Link documents to topology, integrity, risk, HSE, or workflow targets."
                ),
                "operations", List.of(
                        "linkDocumentToTarget",
                        "registerDocument",
                        "uploadDocumentVersion",
                        "uploadDocumentVersionContent",
                        "downloadDocumentVersionContent"
                ),
                "resourceEndpoints", List.of(
                        "POST /api/v1/documents/target-links",
                        "POST /api/v1/documents/documents",
                        "POST /api/v1/documents/document-versions",
                        "POST /api/v1/documents/document-versions/upload",
                        "GET /api/v1/documents/document-versions/{versionId}/content"
                ),
                "contentTransfer", Map.of(
                        "multipartMetadataPart", "metadata",
                        "multipartFilePart", "file",
                        "maxUploadBytes", maxUploadBytes,
                        "checksumAlgorithm", "SHA-256",
                        "rangeRequestsSupported", false,
                        "storageObjectIdOwnership", "backend"
                )
        );
    }

    @Override
    @PostMapping({"/link-document-to-target", "/target-links"})
    public DocumentTargetLinkResponse linkDocumentToTarget(@Valid @RequestBody LinkDocumentToTargetRequest request) {
        Objects.requireNonNull(request, "LinkDocumentToTargetRequest must not be null.");
        return DocumentsRestMapper.toResponse(linkDocumentToTargetUseCase.linkDocumentToTarget(DocumentsRestMapper.toCommand(request)));
    }

    @Override
    @PostMapping({"/register-document", "/documents"})
    public DocumentResponse registerDocument(@Valid @RequestBody RegisterDocumentRequest request) {
        Objects.requireNonNull(request, "RegisterDocumentRequest must not be null.");
        return DocumentsRestMapper.toResponse(registerDocumentUseCase.registerDocument(DocumentsRestMapper.toCommand(request)));
    }

    @Override
    @PostMapping({"/upload-document-version", "/document-versions"})
    public DocumentVersionResponse uploadDocumentVersion(@Valid @RequestBody UploadDocumentVersionRequest request) {
        Objects.requireNonNull(request, "UploadDocumentVersionRequest must not be null.");
        return DocumentsRestMapper.toResponse(uploadDocumentVersionUseCase.uploadDocumentVersion(DocumentsRestMapper.toCommand(request)));
    }

    @PostMapping(value = "/document-versions/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public DocumentVersionResponse uploadDocumentVersionContent(
            @Valid @RequestPart("metadata") UploadDocumentBinaryVersionRequest metadata,
            @RequestPart("file") MultipartFile file
    ) {
        Objects.requireNonNull(metadata, "UploadDocumentBinaryVersionRequest must not be null.");
        if (file == null || file.isEmpty()) {
            throw invalid("Document file must not be empty.");
        }
        if (file.getSize() > maxUploadBytes) {
            throw invalid("Document file exceeds the configured upload limit of " + maxUploadBytes + " bytes.");
        }
        try {
            return DocumentsRestMapper.toResponse(uploadDocumentBinaryVersionUseCase.uploadDocumentBinaryVersion(
                    new UploadDocumentBinaryVersionCommand(
                            metadata.documentId(),
                            metadata.versionNumber(),
                            metadata.versionLabel(),
                            metadata.titleAr(),
                            metadata.titleFr(),
                            metadata.titleEn(),
                            metadata.description(),
                            metadata.languageCode(),
                            metadata.documentDate(),
                            metadata.effectiveFrom(),
                            metadata.effectiveTo(),
                            metadata.uploadedByActorId(),
                            metadata.uploadedByDisplayNameSnapshot(),
                            file.getOriginalFilename(),
                            file.getContentType(),
                            file.getInputStream()
                    )
            ));
        } catch (IOException exception) {
            throw new DocumentContentTransferException(
                    DocumentContentTransferException.Reason.STORAGE_FAILURE,
                    "Document upload stream could not be opened.",
                    exception
            );
        }
    }

    @GetMapping("/document-versions/{versionId}/content")
    public ResponseEntity<InputStreamResource> downloadDocumentVersionContent(@PathVariable String versionId) {
        DocumentVersionContentDto content = downloadDocumentVersionContentUseCase.downloadDocumentVersionContent(versionId);
        MediaType mediaType;
        try {
            mediaType = MediaType.parseMediaType(content.contentType());
        } catch (IllegalArgumentException exception) {
            mediaType = MediaType.APPLICATION_OCTET_STREAM;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(mediaType);
        headers.setContentLength(content.contentLengthBytes());
        headers.setContentDisposition(ContentDisposition.attachment()
                .filename(content.originalFilename(), StandardCharsets.UTF_8)
                .build());
        headers.set(HttpHeaders.ACCEPT_RANGES, "none");

        return ResponseEntity.ok()
                .headers(headers)
                .body(new InputStreamResource(content.content()));
    }

    private static DocumentContentTransferException invalid(String message) {
        return new DocumentContentTransferException(DocumentContentTransferException.Reason.INVALID_CONTENT, message);
    }
}
