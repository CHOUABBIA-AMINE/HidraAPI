/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : CustodyTransferLifecycleController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-26
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
import dz.sh.hidra.modules.custody.api.rest.response.CustodyDiscoveryResponses;
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

    @NativeProtectedOperation @Operation(operationId="custodyT63DiscoverTransferPoints",summary="Discover authorized custody transfer points")
    @GetMapping("/companies/{companyId}/facilities/{facilityId}/transfer-points")
    public ResponseEntity<CustodyDiscoveryResponses.TransferPointPage> discoverTransferPoints(
            @PathVariable String companyId,@PathVariable String facilityId,
            @RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="50") int size){
        return ResponseEntity.ok().cacheControl(CacheControl.noStore()).body(
            CustodyDiscoveryResponses.TransferPointPage.from(useCase.discoverTransferPoints(companyId,facilityId,page,size)));
    }
    @NativeProtectedOperation @Operation(operationId="custodyT63DiscoverPeriods",summary="Discover authorized enrolled custody periods")
    @GetMapping("/companies/{companyId}/transfer-points/{transferPointId}/measurement-periods")
    public ResponseEntity<CustodyDiscoveryResponses.PeriodPage> discoverPeriods(
            @PathVariable String companyId,@PathVariable String transferPointId,
            @RequestParam(required=false) String periodCode,
            @RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="50") int size){
        return ResponseEntity.ok().cacheControl(CacheControl.noStore()).body(
            CustodyDiscoveryResponses.PeriodPage.from(useCase.discoverPeriods(companyId,transferPointId,periodCode,page,size)));
    }
    @NativeProtectedOperation @Operation(operationId="custodyT63ListTickets",summary="List authorized immutable software custody tickets")
    @GetMapping("/measurement-periods/{periodId}/transfer-tickets")
    public ResponseEntity<CustodyDiscoveryResponses.TicketPage> listTickets(
            @PathVariable String periodId,@RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="50") int size){
        return ResponseEntity.ok().cacheControl(CacheControl.noStore()).body(
            CustodyDiscoveryResponses.TicketPage.from(useCase.listTickets(periodId,page,size)));
    }

    @NativeProtectedOperation @Operation(operationId="custodyT64DiscoverAdmissionAgreements",summary="Discover authorized agreements for period admission")
    @GetMapping("/companies/{companyId}/transfer-points/{transferPointId}/agreements")
    public ResponseEntity<CustodyDiscoveryResponses.AdmissionAgreementPage> discoverAdmissionAgreements(
            @PathVariable String companyId,@PathVariable String transferPointId,
            @RequestParam(required=false) String agreementNumber,
            @RequestParam(defaultValue="0") int page,@RequestParam(defaultValue="50") int size){
        return ResponseEntity.ok().cacheControl(CacheControl.noStore()).body(
            CustodyDiscoveryResponses.AdmissionAgreementPage.from(
                useCase.discoverAdmissionAgreements(companyId,transferPointId,agreementNumber,page,size)));
    }
    @NativeProtectedOperation @Operation(operationId="custodyT64OpenPeriod",summary="Admit a protected pre-capture Custody period")
    @PostMapping("/companies/{companyId}/transfer-points/{transferPointId}/measurement-periods")
    public ResponseEntity<CustodyDiscoveryResponses.PeriodAdmissionReceipt> openPeriod(
            @PathVariable String companyId,@PathVariable String transferPointId,
            @Valid @RequestBody CustodyTransferLifecycleRequests.OpenPeriod request){
        return ResponseEntity.ok().cacheControl(CacheControl.noStore()).body(
            CustodyDiscoveryResponses.PeriodAdmissionReceipt.from(useCase.openPeriod(companyId,transferPointId,
                new CustodyTransferLifecycleUseCase.OpenPeriodCommand(request.commandId(),request.agreementId(),
                    request.periodCode(),request.periodStart(),request.periodEnd()))));
    }
}
