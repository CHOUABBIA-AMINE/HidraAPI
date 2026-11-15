/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : IntegrityMaintenanceApprovalController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 * @Type        : Class
 * @Layer       : API
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.api.rest.controller
 * @Description : T18 native-scoped Integrity/Assets governed work-order operations.
 */
package dz.sh.hidra.modules.integrity.api.rest.controller;

import dz.sh.hidra.modules.integrity.api.rest.request.IntegrityMaintenanceApprovalRequest;
import dz.sh.hidra.modules.integrity.api.rest.response.IntegrityMaintenanceApprovalResponse;
import dz.sh.hidra.modules.integrity.application.port.in.IntegrityMaintenanceApprovalUseCase;
import dz.sh.hidra.platform.permissions.NativeProtectedOperation;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.NoSuchElementException;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/integrity/findings/{findingId}")
public final class IntegrityMaintenanceApprovalController {
    private final IntegrityMaintenanceApprovalUseCase useCase;
    public IntegrityMaintenanceApprovalController(IntegrityMaintenanceApprovalUseCase useCase){
        this.useCase=Objects.requireNonNull(useCase);
    }
    @NativeProtectedOperation @Operation(operationId="approveIntegrityFindingForMaintenance")
    @PostMapping("/maintenance-approvals")
    public IntegrityMaintenanceApprovalResponse approve(@PathVariable String findingId,
            @Valid @RequestBody IntegrityMaintenanceApprovalRequest r){
        return IntegrityMaintenanceApprovalResponse.from(useCase.approve(findingId,
                new IntegrityMaintenanceApprovalUseCase.Command(r.commandId(),r.recommendationId(),
                        r.caseId(),r.reason())));
    }
    @NativeProtectedOperation @Operation(operationId="readIntegrityFindingMaintenanceHandoff")
    @GetMapping("/maintenance-handoffs/{approvalId}")
    public IntegrityMaintenanceApprovalResponse read(@PathVariable String findingId,
            @PathVariable String approvalId){
        return IntegrityMaintenanceApprovalResponse.from(useCase.read(findingId,approvalId));
    }
    @ExceptionHandler(NoSuchElementException.class)
    ProblemDetail hidden(NoSuchElementException ignored){
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,"Protected Integrity maintenance unavailable.");
    }
    @ExceptionHandler(IllegalStateException.class)
    ProblemDetail conflict(IllegalStateException ignored){
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,"Integrity maintenance source or command conflict.");
    }
    @ExceptionHandler(IllegalArgumentException.class)
    ProblemDetail invalid(IllegalArgumentException ignored){
        return ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY,"Invalid Integrity maintenance request.");
    }
}
