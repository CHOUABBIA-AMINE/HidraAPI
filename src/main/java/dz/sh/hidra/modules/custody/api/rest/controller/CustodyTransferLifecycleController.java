/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : CustodyTransferLifecycleController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-17
 * @Type        : Class
 * @Layer       : REST Adapter
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.api.rest.controller
 * @Description : Exposes exact native-protected T21 software lifecycle routes.
 */
package dz.sh.hidra.modules.custody.api.rest.controller;
import dz.sh.hidra.modules.custody.application.port.in.CustodyTransferLifecycleUseCase;
import dz.sh.hidra.modules.custody.api.rest.request.CustodyTransferLifecycleRequests;
import dz.sh.hidra.modules.custody.api.rest.response.CustodyTransferLifecycleResponses;
import dz.sh.hidra.platform.permissions.NativeProtectedOperation;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/v1/custody")
public final class CustodyTransferLifecycleController {
    private final CustodyTransferLifecycleUseCase useCase;
    public CustodyTransferLifecycleController(CustodyTransferLifecycleUseCase useCase){
        this.useCase=Objects.requireNonNull(useCase);
    }
    @NativeProtectedOperation @Operation(operationId="custodyT21ReadPeriod",summary="Read a scoped custody measurement period")
    @GetMapping("/measurement-periods/{periodId}")
    public CustodyTransferLifecycleUseCase.Period period(@PathVariable String periodId){
        return useCase.period(periodId);
    }
    @NativeProtectedOperation @Operation(operationId="custodyT21ListReconciliations",summary="List authorized immutable period reconciliations")
    @GetMapping("/measurement-periods/{periodId}/reconciliations")
    public List<CustodyTransferLifecycleResponses.ReconciliationView> reconciliations(@PathVariable String periodId){
        return useCase.reconciliations(periodId).stream().map(CustodyTransferLifecycleResponses.ReconciliationView::from).toList();
    }
    @NativeProtectedOperation @Operation(operationId="custodyT21Reconcile",summary="Freeze qualified observed receipt reconciliation")
    @PostMapping("/measurement-periods/{periodId}/reconciliations")
    public CustodyTransferLifecycleResponses.ReconciliationView reconcile(@PathVariable String periodId,
            @Valid @RequestBody CustodyTransferLifecycleRequests.Reconcile request){
        return CustodyTransferLifecycleResponses.ReconciliationView.from(useCase.reconcile(periodId,
            new CustodyTransferLifecycleUseCase.ReconcileCommand(request.commandId(),request.ticketQuantity())));
    }
    @NativeProtectedOperation @Operation(operationId="custodyT21ReadReconciliation",summary="Read immutable reconciliation evidence")
    @GetMapping("/reconciliations/{reconciliationId}")
    public CustodyTransferLifecycleResponses.ReconciliationView reconciliation(@PathVariable String reconciliationId){
        return CustodyTransferLifecycleResponses.ReconciliationView.from(useCase.reconciliation(reconciliationId));
    }
    @NativeProtectedOperation @Operation(operationId="custodyT21ReadTicket",summary="Read a scoped software custody transfer ticket")
    @GetMapping("/transfer-tickets/{ticketId}")
    public CustodyTransferLifecycleResponses.TicketView ticket(@PathVariable String ticketId){
        return CustodyTransferLifecycleResponses.TicketView.from(useCase.ticket(ticketId));
    }
    @NativeProtectedOperation @Operation(operationId="custodyT21TicketHistory",summary="Read full immutable ticket correction lineage")
    @GetMapping("/transfer-tickets/{ticketId}/history")
    public List<CustodyTransferLifecycleResponses.TicketView> history(@PathVariable String ticketId){
        return useCase.history(ticketId).stream().map(CustodyTransferLifecycleResponses.TicketView::from).toList();
    }
    @NativeProtectedOperation @Operation(operationId="custodyT21FinalizeTicket",summary="Software-finalize only with independent Workflow decision")
    @PostMapping("/transfer-tickets/{ticketId}/finalizations")
    public CustodyTransferLifecycleResponses.TicketView finalizeTicket(@PathVariable String ticketId,
            @Valid @RequestBody CustodyTransferLifecycleRequests.Finalize request){
        return CustodyTransferLifecycleResponses.TicketView.from(useCase.finalizeTicket(ticketId,
            new CustodyTransferLifecycleUseCase.FinalizeCommand(request.commandId(),
                request.expectedVersion(),request.workflowInstanceId(),request.workflowTaskId(),request.workflowActionId())));
    }
    @NativeProtectedOperation @Operation(operationId="custodyT21CorrectTicket",summary="Append a pending superseding ticket")
    @PostMapping("/transfer-tickets/{ticketId}/corrections")
    public CustodyTransferLifecycleResponses.TicketView correctTicket(@PathVariable String ticketId,
            @Valid @RequestBody CustodyTransferLifecycleRequests.Correct request){
        return CustodyTransferLifecycleResponses.TicketView.from(useCase.correctTicket(ticketId,
            new CustodyTransferLifecycleUseCase.CorrectCommand(request.commandId(),request.expectedVersion(),
                request.revisedQuantity(),request.reason(),request.evidenceReference())));
    }
    @NativeProtectedOperation @Operation(operationId="custodyT21ListDiscrepancies",summary="Read discrepancy status for one reconciliation")
    @GetMapping("/reconciliations/{reconciliationId}/discrepancies")
    public List<CustodyTransferLifecycleResponses.DiscrepancyView> discrepancies(@PathVariable String reconciliationId){
        return useCase.discrepancies(reconciliationId).stream().map(CustodyTransferLifecycleResponses.DiscrepancyView::from).toList();
    }
    @NativeProtectedOperation @Operation(operationId="custodyT21ReadDiscrepancy",summary="Read one scoped discrepancy")
    @GetMapping("/discrepancies/{discrepancyId}")
    public CustodyTransferLifecycleResponses.DiscrepancyView discrepancy(@PathVariable String discrepancyId){
        return CustodyTransferLifecycleResponses.DiscrepancyView.from(useCase.discrepancy(discrepancyId));
    }
    @NativeProtectedOperation @Operation(operationId="custodyT21DisposeDiscrepancy",summary="Append an authorized explicit discrepancy disposition")
    @PostMapping("/discrepancies/{discrepancyId}/dispositions")
    public CustodyTransferLifecycleResponses.DiscrepancyView dispose(@PathVariable String discrepancyId,
            @Valid @RequestBody CustodyTransferLifecycleRequests.Disposition request){
        return CustodyTransferLifecycleResponses.DiscrepancyView.from(useCase.dispose(discrepancyId,
            new CustodyTransferLifecycleUseCase.DispositionCommand(request.commandId(),request.disposition(),
                request.reason(),request.evidenceReference())));
    }
    @ExceptionHandler(NoSuchElementException.class)
    ProblemDetail unavailable(){return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,"Custody transfer unavailable.");}
    @ExceptionHandler({IllegalStateException.class,DataIntegrityViolationException.class})
    ProblemDetail conflict(){return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,"Custody lifecycle state or command conflict.");}
    @ExceptionHandler(IllegalArgumentException.class)
    ProblemDetail invalid(){return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,"Invalid custody lifecycle command.");}
}
