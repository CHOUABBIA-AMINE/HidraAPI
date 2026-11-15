/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : IntegrityWorkOrderController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 * @Type        : Class
 * @Layer       : API
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.api.rest.controller
 * @Description : T18 native-scoped Integrity/Assets governed work-order operations.
 */
package dz.sh.hidra.modules.assets.api.rest.controller;

import dz.sh.hidra.modules.assets.api.rest.request.IntegrityWorkOrderRequest;
import dz.sh.hidra.modules.assets.api.rest.response.IntegrityWorkOrderResponse;
import dz.sh.hidra.modules.assets.application.port.in.IntegrityWorkOrderUseCase;
import dz.sh.hidra.platform.permissions.NativeProtectedOperation;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.NoSuchElementException;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/assets/maintenance-work-orders")
public final class IntegrityWorkOrderController {
    private final IntegrityWorkOrderUseCase useCase;
    public IntegrityWorkOrderController(IntegrityWorkOrderUseCase useCase){
        this.useCase=Objects.requireNonNull(useCase);
    }
    @NativeProtectedOperation @Operation(operationId="handOffApprovedIntegrityFindingToAssets")
    @PostMapping("/from-integrity")
    public IntegrityWorkOrderResponse.Receipt handoff(@Valid @RequestBody IntegrityWorkOrderRequest.Create r){
        return IntegrityWorkOrderResponse.Receipt.from(useCase.handoff(
                new IntegrityWorkOrderUseCase.Create(r.commandId(),r.approvalId(),r.maintainableAssetId(),
                        r.workOrderNumber(),r.workOrderTypeId(),r.priorityId(),r.title(),r.description())));
    }
    private IntegrityWorkOrderResponse.Receipt transition(String id,IntegrityWorkOrderUseCase.Action action){
        return IntegrityWorkOrderResponse.Receipt.from(useCase.transition(id,action));
    }
    @NativeProtectedOperation @Operation(operationId="assignApprovedIntegrityWorkOrder")
    @PostMapping("/{id}/assignment")
    public IntegrityWorkOrderResponse.Receipt assign(@PathVariable String id,
            @Valid @RequestBody IntegrityWorkOrderRequest.Assign r){
        return transition(id,new IntegrityWorkOrderUseCase.Action(r.commandId(),r.expectedVersion(),
                "ASSIGN",r.assigneeId(),null,null,null,r.reason(),null));
    }
    @NativeProtectedOperation @Operation(operationId="startApprovedIntegrityWorkOrder")
    @PostMapping("/{id}/start")
    public IntegrityWorkOrderResponse.Receipt start(@PathVariable String id,
            @Valid @RequestBody IntegrityWorkOrderRequest.Start r){
        return transition(id,new IntegrityWorkOrderUseCase.Action(r.commandId(),r.expectedVersion(),
                "START",null,null,null,null,r.reason(),null));
    }
    @NativeProtectedOperation @Operation(operationId="completeApprovedIntegrityWorkOrder")
    @PostMapping("/{id}/execution")
    public IntegrityWorkOrderResponse.Receipt execution(@PathVariable String id,
            @Valid @RequestBody IntegrityWorkOrderRequest.Execute r){
        return transition(id,new IntegrityWorkOrderUseCase.Action(r.commandId(),r.expectedVersion(),
                "EXECUTE",null,r.result(),null,null,r.summary(),r.occurredAt()));
    }
    @NativeProtectedOperation @Operation(operationId="verifyApprovedIntegrityWorkOrder")
    @PostMapping("/{id}/verification")
    public IntegrityWorkOrderResponse.Receipt verification(@PathVariable String id,
            @Valid @RequestBody IntegrityWorkOrderRequest.Verify r){
        return transition(id,new IntegrityWorkOrderUseCase.Action(r.commandId(),r.expectedVersion(),
                "VERIFY",null,null,r.completionId(),r.verdict(),r.evidenceSummary(),null));
    }
    @NativeProtectedOperation @Operation(operationId="readApprovedIntegrityWorkOrderHistory")
    @GetMapping("/{id}/integrity-history")
    public IntegrityWorkOrderResponse.History history(@PathVariable String id){
        return IntegrityWorkOrderResponse.History.from(useCase.history(id));
    }
    @ExceptionHandler(NoSuchElementException.class)
    ProblemDetail hidden(NoSuchElementException ignored){
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,"Protected maintenance order unavailable.");
    }
    @ExceptionHandler(IllegalStateException.class)
    ProblemDetail conflict(IllegalStateException ignored){
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,"Maintenance state or command conflict.");
    }
    @ExceptionHandler(IllegalArgumentException.class)
    ProblemDetail invalid(IllegalArgumentException ignored){
        return ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY,"Invalid maintenance declaration.");
    }
}
