/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : ScopedReportsController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-18
 * @Type        : Class
 * @Layer       : Api
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.api.rest.controller
 * @Description : Scoped, versioned T25 report delivery.
 */
package dz.sh.hidra.modules.reporting.api.rest.controller;
import dz.sh.hidra.modules.reporting.api.rest.mapper.ScopedReportRestMapper;
import dz.sh.hidra.modules.reporting.api.rest.request.ScopedReportRequests.T25SubmitReport;
import dz.sh.hidra.modules.reporting.api.rest.request.ScopedReportRequests.T25RenderReport;
import dz.sh.hidra.modules.reporting.api.rest.request.ScopedReportRequests.T25SealReport;
import dz.sh.hidra.modules.reporting.api.rest.response.ScopedReportResponses.T25ReportReceipt;
import dz.sh.hidra.modules.reporting.application.port.in.ScopedReportUseCase;
import dz.sh.hidra.modules.reporting.domain.model.ScopedReportRun.Scope;
import dz.sh.hidra.platform.permissions.NativeProtectedOperation;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reporting/companies/{companyId}/facilities/{facilityId}")
public class ScopedReportsController {
    private final ScopedReportUseCase reports;
    public ScopedReportsController(ScopedReportUseCase reports) { this.reports=reports; }
    @PostMapping("/report-requests") @NativeProtectedOperation
    @Operation(operationId="submitT25ScopedReport")
    public ResponseEntity<T25ReportReceipt> submit(@PathVariable String companyId,
            @PathVariable String facilityId,@Valid @RequestBody T25SubmitReport request) {
        var receipt=reports.submit(new Scope(companyId,facilityId),ScopedReportRestMapper.command(request));
        return ResponseEntity.status(receipt.replayed()?200:201).body(ScopedReportRestMapper.receipt(receipt));
    }
    @PostMapping("/report-requests/{requestId}/render") @NativeProtectedOperation
    @Operation(operationId="renderT25ScopedReport")
    public T25ReportReceipt render(@PathVariable String companyId,@PathVariable String facilityId,
            @PathVariable String requestId,@Valid @RequestBody T25RenderReport request) {
        return ScopedReportRestMapper.receipt(reports.render(new Scope(companyId,facilityId),requestId,
                ScopedReportRestMapper.command(request)));
    }
    @GetMapping("/report-runs/{runId}") @NativeProtectedOperation
    @Operation(operationId="readT25ScopedReport")
    public T25ReportReceipt read(@PathVariable String companyId,@PathVariable String facilityId,@PathVariable String runId) {
        return ScopedReportRestMapper.receipt(reports.read(new Scope(companyId,facilityId),runId));
    }
    @PostMapping("/report-runs/{runId}/seal") @NativeProtectedOperation
    @Operation(operationId="sealT25ScopedReport")
    public T25ReportReceipt seal(@PathVariable String companyId,@PathVariable String facilityId,
            @PathVariable String runId,@Valid @RequestBody T25SealReport request) {
        return ScopedReportRestMapper.receipt(reports.seal(new Scope(companyId,facilityId),runId,
                ScopedReportRestMapper.command(request)));
    }
    @GetMapping(value="/report-runs/{runId}/content",produces="application/json") @NativeProtectedOperation
    @Operation(operationId="downloadT25ScopedReport")
    public ResponseEntity<byte[]> content(@PathVariable String companyId,@PathVariable String facilityId,
            @PathVariable String runId) {
        return ScopedReportRestMapper.content(reports.content(new Scope(companyId,facilityId),runId));
    }
}
