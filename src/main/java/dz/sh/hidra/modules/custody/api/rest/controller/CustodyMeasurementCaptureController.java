/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : CustodyMeasurementCaptureController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 * @Type        : Class
 * @Layer       : REST Adapter
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.api.rest.controller
 * @Description : Exposes two exact native protected measurement operations.
 */
package dz.sh.hidra.modules.custody.api.rest.controller;

import dz.sh.hidra.modules.custody.api.rest.request.CustodyMeasurementCaptureRequest;
import dz.sh.hidra.modules.custody.api.rest.response.CustodyMeasurementCaptureResponse;
import dz.sh.hidra.modules.custody.application.port.in.CustodyMeasurementCaptureUseCase;
import dz.sh.hidra.modules.custody.domain.service.CustodyMeasurementCapturePolicy;
import dz.sh.hidra.platform.permissions.NativeProtectedOperation;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.NoSuchElementException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/v1/custody/measurement-periods/{periodId}/measurements")
public class CustodyMeasurementCaptureController {
    private final CustodyMeasurementCaptureUseCase useCase;
    public CustodyMeasurementCaptureController(CustodyMeasurementCaptureUseCase useCase){this.useCase=useCase;}
    @NativeProtectedOperation
    @Operation(operationId="captureQualifiedCustodyMeasurement",summary="Capture one qualified observed gas interval without fiscal acceptance")
    @PostMapping
    public ResponseEntity<CustodyMeasurementCaptureResponse> capture(@PathVariable String periodId,
            @Valid @RequestBody CustodyMeasurementCaptureRequest request){
        var result=useCase.capture(periodId,new CustodyMeasurementCaptureUseCase.Command(request.commandId(),
            request.meterRunSnapshotId(),request.measurementTypeId(),request.productPublicationId(),
            request.agreementPartyId(),request.unitId(),request.intervalEvidenceId()));
        return ResponseEntity.status(result.replay()?HttpStatus.OK:HttpStatus.CREATED)
            .body(CustodyMeasurementCaptureResponse.from(result));
    }
    @NativeProtectedOperation
    @Operation(operationId="readQualifiedCustodyMeasurement",summary="Read an immutable scoped observed interval receipt")
    @GetMapping("/{measurementId}")
    public CustodyMeasurementCaptureResponse read(@PathVariable String periodId,@PathVariable String measurementId){
        return CustodyMeasurementCaptureResponse.from(useCase.read(periodId,measurementId));
    }
    @ExceptionHandler(NoSuchElementException.class)
    ProblemDetail unavailable(){return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,"Custody measurement unavailable.");}
    @ExceptionHandler(CustodyMeasurementCapturePolicy.Invalid.class)
    ProblemDetail invalid(CustodyMeasurementCapturePolicy.Invalid failure){
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,failure.getMessage());
    }
    @ExceptionHandler(CustodyMeasurementCapturePolicy.Conflict.class)
    ProblemDetail conflict(CustodyMeasurementCapturePolicy.Conflict failure){
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,failure.getMessage());
    }
}
