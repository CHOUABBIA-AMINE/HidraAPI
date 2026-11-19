/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : HseCapaLifecycleController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Class
 * @Layer       : API
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.api.rest.controller
 * @Description : Thirteen native signed company-and-FACILITY HSE CAPA lifecycle operations.
 */
package dz.sh.hidra.modules.hse.api.rest.controller;

import dz.sh.hidra.modules.hse.api.rest.mapper.HseCapaLifecycleRestMapper;
import dz.sh.hidra.modules.hse.api.rest.request.HseCapaLifecycleRequest;
import dz.sh.hidra.modules.hse.api.rest.response.HseCapaLifecycleResponse;
import dz.sh.hidra.modules.hse.application.port.in.HseCapaLifecycleUseCase;
import dz.sh.hidra.platform.permissions.NativeProtectedOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.NoSuchElementException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/hse/companies/{companyId}/facilities/{facilityId}/cases")
@Tag(name="HSE scoped corrective actions")
public final class HseCapaLifecycleController {
    private final HseCapaLifecycleUseCase service;
    public HseCapaLifecycleController(HseCapaLifecycleUseCase service){this.service=service;}
    @NativeProtectedOperation
    @Operation(operationId="recordScopedHseCaseImpactAssessment")
    @PostMapping("/{caseId}/impact-assessments")
    public ResponseEntity<HseCapaLifecycleResponse.HseCaseImpactAssessmentReceipt> assessImpact(
            @PathVariable String companyId, @PathVariable String facilityId, @PathVariable String caseId,
            @Valid @RequestBody HseCapaLifecycleRequest.HseCaseImpactAssessmentRequest request) {
        var result = service.assessImpact(companyId, facilityId, caseId,
                HseCapaLifecycleRestMapper.impact(request));
        return ResponseEntity.status(result.replayed() ? HttpStatus.OK : HttpStatus.CREATED)
                .body(HseCapaLifecycleRestMapper.impact(result));
    }

    @NativeProtectedOperation @Operation(operationId="enrollIncidentHseCase")
    @PostMapping("/from-incident")
    public ResponseEntity<HseCapaLifecycleResponse.CaseReceipt> enroll(@PathVariable String companyId,
            @PathVariable String facilityId,@Valid @RequestBody HseCapaLifecycleRequest.Enroll r){
        var out=service.enroll(companyId,facilityId,HseCapaLifecycleRestMapper.enroll(r));
        return ResponseEntity.status(out.replayed()?HttpStatus.OK:HttpStatus.CREATED)
                .body(HseCapaLifecycleResponse.CaseReceipt.from(out));
    }
    @NativeProtectedOperation @Operation(operationId="discoverScopedHseCases")
    @GetMapping
    public HseCapaLifecycleResponse.ScopedHsePage<HseCapaLifecycleResponse.CaseView> cases(
            @PathVariable String companyId,@PathVariable String facilityId,
            @RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="25") int size,
            @RequestParam(required=false) String caseNumber){
        return HseCapaLifecycleRestMapper.cases(service.cases(companyId,facilityId,page,size,caseNumber));
    }
    @NativeProtectedOperation @Operation(operationId="readScopedHseCase")
    @GetMapping("/{caseId}")
    public HseCapaLifecycleResponse.CaseView caseDetail(@PathVariable String companyId,
            @PathVariable String facilityId,@PathVariable String caseId){
        return HseCapaLifecycleResponse.CaseView.from(
                service.caseDetail(companyId,facilityId,caseId));
    }
    @NativeProtectedOperation @Operation(operationId="createScopedHseCapa")
    @PostMapping("/{caseId}/capas")
    public ResponseEntity<HseCapaLifecycleResponse.CapaReceipt> propose(@PathVariable String companyId,
            @PathVariable String facilityId,@PathVariable String caseId,
            @Valid @RequestBody HseCapaLifecycleRequest.Propose r){
        var out=service.propose(companyId,facilityId,caseId,HseCapaLifecycleRestMapper.propose(r));
        return ResponseEntity.status(out.replayed()?HttpStatus.OK:HttpStatus.CREATED)
                .body(HseCapaLifecycleResponse.CapaReceipt.from(out));
    }
    @NativeProtectedOperation @Operation(operationId="listScopedHseCapas")
    @GetMapping("/{caseId}/capas")
    public HseCapaLifecycleResponse.ScopedHsePage<HseCapaLifecycleResponse.ScopedHseCapaView> capas(
            @PathVariable String companyId,@PathVariable String facilityId,
            @PathVariable String caseId,@RequestParam(defaultValue="0") int page,
            @RequestParam(defaultValue="25") int size,
            @RequestParam(required=false) String actionNumber){
        return HseCapaLifecycleRestMapper.capas(service.capas(companyId,facilityId,caseId,page,size,actionNumber));
    }
    @NativeProtectedOperation @Operation(operationId="readScopedHseCapa")
    @GetMapping("/{caseId}/capas/{capaId}")
    public HseCapaLifecycleResponse.ScopedHseCapaView capaDetail(@PathVariable String companyId,
            @PathVariable String facilityId,@PathVariable String caseId,@PathVariable String capaId){
        return HseCapaLifecycleResponse.ScopedHseCapaView.from(
                service.capaDetail(companyId,facilityId,caseId,capaId));
    }
    @NativeProtectedOperation @Operation(operationId="approveScopedHseCapa")
    @PostMapping("/{caseId}/capas/{capaId}/approve")
    public HseCapaLifecycleResponse.CapaReceipt approve(@PathVariable String companyId,
            @PathVariable String facilityId,@PathVariable String caseId,@PathVariable String capaId,
            @Valid @RequestBody HseCapaLifecycleRequest.ScopedHseCapaAction r){
        return HseCapaLifecycleResponse.CapaReceipt.from(service.approve(
                companyId,facilityId,caseId,capaId,HseCapaLifecycleRestMapper.action(r)));
    }
    @NativeProtectedOperation @Operation(operationId="assignScopedHseCapa")
    @PostMapping("/{caseId}/capas/{capaId}/assign")
    public HseCapaLifecycleResponse.CapaReceipt assign(@PathVariable String companyId,
            @PathVariable String facilityId,@PathVariable String caseId,@PathVariable String capaId,
            @Valid @RequestBody HseCapaLifecycleRequest.ScopedHseCapaAction r){
        return HseCapaLifecycleResponse.CapaReceipt.from(service.assign(
                companyId,facilityId,caseId,capaId,HseCapaLifecycleRestMapper.action(r)));
    }
    @NativeProtectedOperation @Operation(operationId="attachScopedHseCapaEvidence")
    @PostMapping("/{caseId}/capas/{capaId}/evidence")
    public HseCapaLifecycleResponse.CapaReceipt evidence(@PathVariable String companyId,
            @PathVariable String facilityId,@PathVariable String caseId,@PathVariable String capaId,
            @Valid @RequestBody HseCapaLifecycleRequest.ScopedHseCapaAction r){
        return HseCapaLifecycleResponse.CapaReceipt.from(service.evidence(
                companyId,facilityId,caseId,capaId,HseCapaLifecycleRestMapper.action(r)));
    }
    @NativeProtectedOperation @Operation(operationId="completeScopedHseCapa")
    @PostMapping("/{caseId}/capas/{capaId}/complete")
    public HseCapaLifecycleResponse.CapaReceipt complete(@PathVariable String companyId,
            @PathVariable String facilityId,@PathVariable String caseId,@PathVariable String capaId,
            @Valid @RequestBody HseCapaLifecycleRequest.ScopedHseCapaAction r){
        return HseCapaLifecycleResponse.CapaReceipt.from(service.complete(
                companyId,facilityId,caseId,capaId,HseCapaLifecycleRestMapper.action(r)));
    }
    @NativeProtectedOperation @Operation(operationId="verifyScopedHseCapa")
    @PostMapping("/{caseId}/capas/{capaId}/verify")
    public HseCapaLifecycleResponse.CapaReceipt verify(@PathVariable String companyId,
            @PathVariable String facilityId,@PathVariable String caseId,@PathVariable String capaId,
            @Valid @RequestBody HseCapaLifecycleRequest.ScopedHseCapaAction r){
        return HseCapaLifecycleResponse.CapaReceipt.from(service.verify(
                companyId,facilityId,caseId,capaId,HseCapaLifecycleRestMapper.action(r)));
    }
    @NativeProtectedOperation @Operation(operationId="closeScopedHseCase")
    @PostMapping("/{caseId}/close")
    public HseCapaLifecycleResponse.CaseReceipt close(@PathVariable String companyId,
            @PathVariable String facilityId,@PathVariable String caseId,
            @Valid @RequestBody HseCapaLifecycleRequest.ScopedHseCapaAction r){
        return HseCapaLifecycleResponse.CaseReceipt.from(service.close(
                companyId,facilityId,caseId,HseCapaLifecycleRestMapper.action(r)));
    }
    @NativeProtectedOperation @Operation(operationId="readScopedHseCaseHistory")
    @GetMapping("/{caseId}/history")
    public HseCapaLifecycleResponse.ScopedHsePage<HseCapaLifecycleResponse.EventView> history(
            @PathVariable String companyId,@PathVariable String facilityId,@PathVariable String caseId,
            @RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="25") int size){
        return HseCapaLifecycleRestMapper.events(service.history(companyId,facilityId,caseId,page,size));
    }
    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<ProblemDetail> denied(SecurityException ignored){
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN,"Protected HSE action denied."));
    }
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ProblemDetail> hidden(NoSuchElementException ignored){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,"Protected HSE resource unavailable."));
    }
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ProblemDetail> conflict(IllegalStateException ignored){
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
                ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,"HSE case/CAPA revision conflict."));
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ProblemDetail> invalid(IllegalArgumentException ignored){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,"Invalid HSE CAPA request."));
    }
}
