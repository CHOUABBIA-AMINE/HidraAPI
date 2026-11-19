/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ScopedAuditEvidenceController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-19
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.api.rest.controller
 *
 * @Description : Transactional governed scoped Audit search, export and access evidence.
 *
 */
package dz.sh.hidra.modules.audit.api.rest.controller;

import dz.sh.hidra.modules.audit.api.rest.request.ScopedAuditEvidenceRequests.T28CreateAuditExport;
import dz.sh.hidra.modules.audit.api.rest.response.ScopedAuditEvidenceResponses;
import dz.sh.hidra.modules.audit.api.rest.response.ScopedAuditEvidenceResponses.*;
import dz.sh.hidra.modules.audit.application.port.in.ScopedAuditEvidenceUseCase;
import dz.sh.hidra.modules.audit.domain.model.ScopedAuditEvidence;
import dz.sh.hidra.modules.audit.domain.model.ScopedAuditEvidence.*;
import dz.sh.hidra.platform.permissions.NativeProtectedOperation;
import io.swagger.v3.oas.annotations.Operation;
import java.time.Instant;
import java.util.Base64;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/audit/companies/{companyId}/facilities/{facilityId}")
public final class ScopedAuditEvidenceController {
    private final ScopedAuditEvidenceUseCase audit;
    public ScopedAuditEvidenceController(ScopedAuditEvidenceUseCase audit){
        this.audit=Objects.requireNonNull(audit);
    }
    @GetMapping("/events")
    @NativeProtectedOperation @Operation(operationId="searchT28AuditEvents")
    public T28AuditEventPage search(@PathVariable String companyId,@PathVariable String facilityId,
            @RequestParam String eventType,@RequestParam Instant from,@RequestParam Instant to,
            @RequestParam(required=false) Integer limit,@RequestParam(required=false) String cursor) {
        var page=audit.search(new Scope(companyId,facilityId),new Window(eventType,from,to),limit,cursor);
        return new T28AuditEventPage(page.items().stream()
                .map(ScopedAuditEvidenceResponses::item).toList(),page.nextCursor());
    }
    @PostMapping("/exports")
    @NativeProtectedOperation @Operation(operationId="createT28AuditExport")
    public ResponseEntity<T28AuditExportManifest> createExport(@PathVariable String companyId,
            @PathVariable String facilityId,@RequestBody T28CreateAuditExport request) {
        if(request==null)throw new IllegalArgumentException("Audit export criteria required.");
        var result=audit.createExport(new Criteria(new Scope(companyId,facilityId),
                new Window(request.eventType(),request.from(),request.to()),
                request.requestKey(),request.purpose()));
        return ResponseEntity.status(result.created()?HttpStatus.CREATED:HttpStatus.OK)
                .cacheControl(CacheControl.noStore()).body(ScopedAuditEvidenceResponses.manifest(result.manifest()));
    }
    @GetMapping("/exports/{exportId}")
    @NativeProtectedOperation @Operation(operationId="readT28AuditExportManifest")
    public T28AuditExportManifest exportManifest(@PathVariable String companyId,
            @PathVariable String facilityId,@PathVariable UUID exportId) {
        return ScopedAuditEvidenceResponses.manifest(
                audit.exportManifest(new Scope(companyId,facilityId),exportId));
    }
    @GetMapping("/exports/{exportId}/content")
    @NativeProtectedOperation @Operation(operationId="downloadT28AuditExportContent")
    public ResponseEntity<byte[]> download(@PathVariable String companyId,
            @PathVariable String facilityId,@PathVariable UUID exportId) {
        ContentResult content=audit.download(new Scope(companyId,facilityId),exportId);
        if(content.status()!=200)return ResponseEntity.status(content.status())
                .cacheControl(CacheControl.noStore()).build();
        byte[] bytes=content.artifact().bytes();
        String digest=Base64.getEncoder().encodeToString(
                java.util.HexFormat.of().parseHex(content.artifact().manifest().sha256()));
        return ResponseEntity.ok().cacheControl(CacheControl.noStore())
                .contentType(MediaType.APPLICATION_JSON).contentLength(bytes.length)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"gz2-audit-"+exportId+".json\"")
                .header("Content-Digest","sha-256=:"+digest+":").body(bytes);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String,String>> invalid(IllegalArgumentException error) {
        return safe(400,"INVALID_REQUEST");
    }
    @ExceptionHandler(ScopedAuditEvidence.Conflict.class)
    public ResponseEntity<Map<String,String>> conflict(ScopedAuditEvidence.Conflict error) {
        return safe(409,"CONFLICT");
    }
    @ExceptionHandler(ScopedAuditEvidence.LimitExceeded.class)
    public ResponseEntity<Map<String,String>> limit(ScopedAuditEvidence.LimitExceeded error) {
        return safe(422,"EXPORT_LIMIT_EXCEEDED");
    }
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Map<String,String>> hidden(NoSuchElementException error) {
        return safe(404,"NOT_FOUND");
    }
    @ExceptionHandler(org.springframework.dao.DataAccessException.class)
    public ResponseEntity<Map<String,String>> unavailable(org.springframework.dao.DataAccessException error){
        return safe(503,"UNAVAILABLE");
    }
    private ResponseEntity<Map<String,String>> safe(int code,String reason) {
        return ResponseEntity.status(code).cacheControl(CacheControl.noStore())
                .body(Map.of("code",reason));
    }
}
