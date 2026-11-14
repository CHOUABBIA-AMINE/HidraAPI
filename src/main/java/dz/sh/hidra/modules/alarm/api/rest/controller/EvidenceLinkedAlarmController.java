/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EvidenceLinkedAlarmController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-14
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : alarm
 * @Package     : dz.sh.hidra.modules.alarm.api.rest.controller
 *
 * @Description : Exposes two native-protected Alarm evaluation endpoints.
 *
 */
package dz.sh.hidra.modules.alarm.api.rest.controller;

import dz.sh.hidra.modules.alarm.api.rest.request.EvidenceLinkedAlarmRequest;
import dz.sh.hidra.modules.alarm.api.rest.response.EvidenceLinkedAlarmResponse;
import dz.sh.hidra.modules.alarm.application.port.in.EvidenceLinkedAlarmUseCase;
import dz.sh.hidra.platform.permissions.NativeProtectedOperation;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.Objects;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/alarm/alarms")
public final class EvidenceLinkedAlarmController {
    private final EvidenceLinkedAlarmUseCase alarms;

    public EvidenceLinkedAlarmController(EvidenceLinkedAlarmUseCase alarms) {
        this.alarms = Objects.requireNonNull(alarms);
    }

    @Operation(operationId = "raiseAlarmFromAuthorizedMonitoringEvaluation")
    @PostMapping("/from-evaluation")
    @NativeProtectedOperation
    public ResponseEntity<EvidenceLinkedAlarmResponse> create(
            @Valid @RequestBody EvidenceLinkedAlarmRequest request) {
        var result = alarms.raise(new EvidenceLinkedAlarmUseCase.Command(
                request.evaluationId(), request.alarmTypeId(), request.severityId(), request.titleFr()));
        return ResponseEntity.status(result.created() ? HttpStatus.CREATED : HttpStatus.OK)
                .body(EvidenceLinkedAlarmResponse.of(result));
    }

    @Operation(operationId = "readAlarmAuthorizedMonitoringEvidence")
    @GetMapping("/{id}/evidence")
    @NativeProtectedOperation
    public EvidenceLinkedAlarmResponse evidence(@PathVariable String id) {
        return EvidenceLinkedAlarmResponse.of(alarms.evidence(id));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ProblemDetail invalid(IllegalArgumentException ignored) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY,
                "Invalid or non-qualifying Monitoring evaluation or Alarm catalog request.");
    }
}
