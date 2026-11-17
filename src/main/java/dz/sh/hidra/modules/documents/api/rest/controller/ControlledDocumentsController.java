/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : ControlledDocumentsController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-17
 * @Type        : Class
 * @Layer       : API
 * @Module      : documents
 * @Package     : dz.sh.hidra.modules.documents.api.rest.controller
 * @Description : Nine exact native signed scoped Documents HTTP endpoints.
 */
package dz.sh.hidra.modules.documents.api.rest.controller;

import dz.sh.hidra.modules.documents.api.rest.mapper.DocumentsRestMapper;
import dz.sh.hidra.modules.documents.api.rest.request.ControlledDocumentRequests;
import dz.sh.hidra.modules.documents.api.rest.request.UploadDocumentBinaryVersionRequest;
import dz.sh.hidra.modules.documents.api.rest.response.ControlledDocumentResponses;
import dz.sh.hidra.modules.documents.api.rest.response.DocumentVersionResponse;
import dz.sh.hidra.modules.documents.application.command.UploadDocumentBinaryVersionCommand;
import dz.sh.hidra.modules.documents.application.dto.DocumentVersionContentDto;
import dz.sh.hidra.modules.documents.application.port.in.ControlledDocumentsUseCase;
import dz.sh.hidra.modules.documents.domain.exception.DocumentContentTransferException;
import dz.sh.hidra.platform.permissions.NativeProtectedOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/documents/companies/{companyId}/facilities/{facilityId}/controlled-documents")
@Tag(name="Controlled facility documents")
public final class ControlledDocumentsController {
    private final ControlledDocumentsUseCase documents;
    private final long maxBytes;
    public ControlledDocumentsController(ControlledDocumentsUseCase documents,
            @Value("${hidra.documents.upload.max-bytes:52428800}") long maxBytes){
        this.documents=Objects.requireNonNull(documents);
        if(maxBytes<1)throw new IllegalArgumentException("Upload limit must be positive.");
        this.maxBytes=maxBytes;
    }

    @NativeProtectedOperation @Operation(operationId="createControlledDocument")
    @PostMapping
    public ResponseEntity<ControlledDocumentResponses.Created> create(
            @PathVariable String companyId,@PathVariable String facilityId,
            @Valid @RequestBody ControlledDocumentRequests.Create request){
        var result=documents.create(companyId,facilityId,new ControlledDocumentsUseCase.Create(
                request.requestId(),request.code(),request.titleFr(),
                request.documentTypeId(),request.classificationId()));
        return ResponseEntity.status(result.replayed()?HttpStatus.OK:HttpStatus.CREATED)
                .body(ControlledDocumentResponses.Created.from(result));
    }
    @NativeProtectedOperation @Operation(operationId="discoverControlledDocuments")
    @GetMapping
    public ControlledDocumentResponses.Discovery discover(@PathVariable String companyId,
            @PathVariable String facilityId,@RequestParam(defaultValue="0") Integer page,
            @RequestParam(defaultValue="25") Integer size,
            @RequestParam(required=false) String code){
        return ControlledDocumentResponses.Discovery.from(
                documents.discover(companyId,facilityId,page,size,code));
    }
    @NativeProtectedOperation @Operation(operationId="readControlledDocument")
    @GetMapping("/{documentId}")
    public ControlledDocumentResponses.Detail detail(@PathVariable String companyId,
            @PathVariable String facilityId,@PathVariable String documentId){
        return ControlledDocumentResponses.Detail.from(documents.detail(companyId,facilityId,documentId));
    }
    @NativeProtectedOperation @Operation(operationId="listControlledDocumentVersions")
    @GetMapping("/{documentId}/versions")
    public ControlledDocumentResponses.Versions versions(@PathVariable String companyId,
            @PathVariable String facilityId,@PathVariable String documentId,
            @RequestParam(defaultValue="0") Integer page,@RequestParam(defaultValue="25") Integer size){
        return ControlledDocumentResponses.Versions.from(documents.versions(
                companyId,facilityId,documentId,page,size));
    }
    @NativeProtectedOperation @Operation(operationId="listControlledDocumentLinks")
    @GetMapping("/{documentId}/links")
    public ControlledDocumentResponses.Links links(@PathVariable String companyId,
            @PathVariable String facilityId,@PathVariable String documentId,
            @RequestParam(defaultValue="0") Integer page,@RequestParam(defaultValue="25") Integer size){
        return ControlledDocumentResponses.Links.from(documents.links(
                companyId,facilityId,documentId,page,size));
    }
    @NativeProtectedOperation @Operation(operationId="uploadControlledDocumentVersion")
    @PostMapping(value="/{documentId}/versions/upload",consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
    public DocumentVersionResponse upload(@PathVariable String companyId,@PathVariable String facilityId,
            @PathVariable String documentId,
            @Valid @RequestPart("metadata") UploadDocumentBinaryVersionRequest metadata,
            @RequestPart("file") MultipartFile file){
        if(file==null||file.isEmpty()||file.getSize()>maxBytes)
            throw new IllegalArgumentException("Invalid document binary size.");
        if(!documentId.equals(metadata.documentId()))
            throw new NoSuchElementException("Protected Documents resource unavailable.");
        try {
            return DocumentsRestMapper.toResponse(documents.upload(companyId,facilityId,documentId,
                    new UploadDocumentBinaryVersionCommand(metadata.documentId(),metadata.versionNumber(),
                            metadata.versionLabel(),metadata.titleAr(),metadata.titleFr(),
                            metadata.titleEn(),metadata.description(),metadata.languageCode(),
                            metadata.documentDate(),metadata.effectiveFrom(),metadata.effectiveTo(),
                            metadata.uploadedByActorId(),metadata.uploadedByDisplayNameSnapshot(),
                            file.getOriginalFilename(),file.getContentType(),file.getInputStream())));
        }catch(IOException e){throw new DocumentContentTransferException(
                DocumentContentTransferException.Reason.STORAGE_FAILURE,
                "Controlled document stream could not be opened.",e);}
    }
    @NativeProtectedOperation @Operation(operationId="downloadControlledDocumentVersion")
    @GetMapping("/{documentId}/versions/{versionId}/content")
    public ResponseEntity<InputStreamResource> content(@PathVariable String companyId,
            @PathVariable String facilityId,@PathVariable String documentId,@PathVariable String versionId){
        DocumentVersionContentDto content=documents.content(companyId,facilityId,documentId,versionId);
        MediaType mime;
        try {mime=MediaType.parseMediaType(content.contentType());}
        catch(IllegalArgumentException e){mime=MediaType.APPLICATION_OCTET_STREAM;}
        var headers=new HttpHeaders();
        headers.setContentType(mime);
        headers.setContentLength(content.contentLengthBytes());
        headers.setContentDisposition(ContentDisposition.attachment()
                .filename(content.originalFilename(),StandardCharsets.UTF_8).build());
        headers.set(HttpHeaders.ACCEPT_RANGES,"none");
        headers.setETag("\""+content.checksumValue()+"\"");
        return ResponseEntity.ok().headers(headers).body(new InputStreamResource(content.content()));
    }
    @NativeProtectedOperation @Operation(operationId="acceptControlledDocumentVersion")
    @PostMapping("/{documentId}/versions/{versionId}/accept")
    public ResponseEntity<ControlledDocumentResponses.Decision> accept(@PathVariable String companyId,
            @PathVariable String facilityId,@PathVariable String documentId,
            @PathVariable String versionId,@Valid @RequestBody ControlledDocumentRequests.Accept request){
        var result=documents.accept(companyId,facilityId,documentId,versionId,
                new ControlledDocumentsUseCase.Accept(request.requestId(),request.reason(),request.linkRoleId()));
        return ResponseEntity.status(result.replayed()?HttpStatus.OK:HttpStatus.CREATED)
                .body(ControlledDocumentResponses.Decision.from(result));
    }
    @NativeProtectedOperation @Operation(operationId="supersedeControlledDocumentVersion")
    @PostMapping("/{documentId}/versions/{versionId}/supersede")
    public ResponseEntity<ControlledDocumentResponses.Decision> supersede(@PathVariable String companyId,
            @PathVariable String facilityId,@PathVariable String documentId,@PathVariable String versionId,
            @Valid @RequestBody ControlledDocumentRequests.Supersede request){
        var result=documents.supersede(companyId,facilityId,documentId,versionId,
                new ControlledDocumentsUseCase.Supersede(request.requestId(),request.reason(),
                        request.linkRoleId(),request.expectedCurrentVersionId()));
        return ResponseEntity.status(result.replayed()?HttpStatus.OK:HttpStatus.CREATED)
                .body(ControlledDocumentResponses.Decision.from(result));
    }
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ProblemDetail> hidden(NoSuchElementException ignored){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,"Protected Documents resource unavailable."));
    }
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ProblemDetail> conflict(IllegalStateException ignored){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT,"Controlled Documents state or content conflict."));
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ProblemDetail> invalid(IllegalArgumentException ignored){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,"Invalid controlled Documents request."));
    }
    @ExceptionHandler(DocumentContentTransferException.class)
    public ResponseEntity<ProblemDetail> contentFailure(DocumentContentTransferException error){
        var status=switch(error.reason()){
            case INVALID_CONTENT->HttpStatus.BAD_REQUEST;
            case NOT_FOUND->HttpStatus.NOT_FOUND;
            case STORAGE_FAILURE->HttpStatus.SERVICE_UNAVAILABLE;
        };
        return ResponseEntity.status(status).body(ProblemDetail.forStatusAndDetail(status,
                "Controlled Documents content unavailable."));
    }
}
