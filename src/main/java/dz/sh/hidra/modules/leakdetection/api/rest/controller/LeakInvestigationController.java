/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : LeakInvestigationController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 * @Type        : Class
 * @Layer       : API
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.api.rest.controller
 * @Description : Native-scoped Leak suspected-case investigation and independent documentary review.
 */
package dz.sh.hidra.modules.leakdetection.api.rest.controller;

import dz.sh.hidra.modules.leakdetection.api.rest.request.LeakInvestigationRequest;
import dz.sh.hidra.modules.leakdetection.api.rest.response.LeakInvestigationResponse;
import dz.sh.hidra.modules.leakdetection.application.port.in.LeakInvestigationUseCase;
import dz.sh.hidra.platform.permissions.NativeProtectedOperation;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/leakdetection/cases")
public final class LeakInvestigationController {
    private final LeakInvestigationUseCase service;
    public LeakInvestigationController(LeakInvestigationUseCase service) {this.service=Objects.requireNonNull(service);}
    @NativeProtectedOperation @Operation(operationId="openGovernedLeakCaseFromAuthorizedCandidate")
    @PostMapping("/from-candidate")
    public ResponseEntity<LeakInvestigationResponse.Receipt> open(
            @Valid @RequestBody LeakInvestigationRequest.Open r) {
        var result=service.open(r.candidateId(),r.requestId());
        return ResponseEntity.status(result.replayed()?HttpStatus.OK:HttpStatus.CREATED)
                .body(LeakInvestigationResponse.Receipt.from(result));
    }
    @NativeProtectedOperation @Operation(operationId="readProtectedLeakInvestigationHistory")
    @GetMapping("/{id}/investigation")
    public LeakInvestigationResponse.View history(@PathVariable String id) {
        return LeakInvestigationResponse.View.from(service.history(id));
    }
    private LeakInvestigationResponse.Receipt execute(String id,LeakInvestigationUseCase.Command cmd) {
        return LeakInvestigationResponse.Receipt.from(service.execute(id,cmd));
    }
    @NativeProtectedOperation @Operation(operationId="assignLeakCaseInvestigator")
    @PostMapping("/{id}/investigation/assign")
    public LeakInvestigationResponse.Receipt assign(@PathVariable String id,
            @Valid @RequestBody LeakInvestigationRequest.Assign r) {
        return execute(id,new LeakInvestigationUseCase.Command(r.requestId(),r.expectedVersion(),
                "ASSIGN",null,null,null,r.reason(),null));
    }
    @NativeProtectedOperation @Operation(operationId="recordLeakCaseContradictoryOrSupportingEvidence")
    @PostMapping("/{id}/investigation/evidence")
    public LeakInvestigationResponse.Receipt evidence(@PathVariable String id,
            @Valid @RequestBody LeakInvestigationRequest.Evidence r) {
        return execute(id,new LeakInvestigationUseCase.Command(r.requestId(),r.expectedVersion(),
                "EVIDENCE","MONITORING_DEVIATION",r.evaluationId(),r.direction(),r.explanation(),null));
    }
    @NativeProtectedOperation @Operation(operationId="recordLeakCaseHumanVerificationAction")
    @PostMapping("/{id}/investigation/actions")
    public LeakInvestigationResponse.Receipt action(@PathVariable String id,
            @Valid @RequestBody LeakInvestigationRequest.Action r) {
        return execute(id,new LeakInvestigationUseCase.Command(r.requestId(),r.expectedVersion(),
                "ACTION",r.actionType(),null,null,r.result(),null));
    }
    @NativeProtectedOperation @Operation(operationId="submitLeakCaseDocumentaryDisposition")
    @PostMapping("/{id}/investigation/disposition")
    public LeakInvestigationResponse.Receipt disposition(@PathVariable String id,
            @Valid @RequestBody LeakInvestigationRequest.Disposition r) {
        return execute(id,new LeakInvestigationUseCase.Command(r.requestId(),r.expectedVersion(),
                "SUBMIT",null,null,null,r.explanation(),r.disposition()));
    }
    @NativeProtectedOperation @Operation(operationId="independentlyReviewLeakCaseDisposition")
    @PostMapping("/{id}/investigation/review")
    public LeakInvestigationResponse.Receipt review(@PathVariable String id,
            @Valid @RequestBody LeakInvestigationRequest.Review r) {
        return execute(id,new LeakInvestigationUseCase.Command(r.requestId(),r.expectedVersion(),
                "REVIEW",null,null,r.verdict(),r.rationale(),null));
    }
    @ExceptionHandler(java.util.NoSuchElementException.class)
    ProblemDetail hidden(java.util.NoSuchElementException ignored) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,"Protected Leak case unavailable.");
    }
    @ExceptionHandler(IllegalStateException.class)
    ProblemDetail conflict(IllegalStateException ignored) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,"Conflicting Leak investigation revision.");
    }
    @ExceptionHandler(IllegalArgumentException.class)
    ProblemDetail invalid(IllegalArgumentException ignored) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY,"Invalid Leak investigation declaration.");
    }
}
