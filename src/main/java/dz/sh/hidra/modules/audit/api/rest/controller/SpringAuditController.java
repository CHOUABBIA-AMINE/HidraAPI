/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SpringAuditController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : audit
 * @Package     : dz.sh.hidra.modules.audit.api.rest.controller
 *
 * @Description : Spring MVC adapter exposing audit REST endpoints.
 *
 */
package dz.sh.hidra.modules.audit.api.rest.controller;
import dz.sh.hidra.modules.audit.api.rest.mapper.AuditRestMapper;
import dz.sh.hidra.modules.audit.api.rest.request.RecordAuditAccessRequest;
import dz.sh.hidra.modules.audit.api.rest.request.RecordAuditEventRequest;
import dz.sh.hidra.modules.audit.api.rest.request.RequestAuditExportRequest;
import dz.sh.hidra.modules.audit.api.rest.response.AuditAccessRecordResponse;
import dz.sh.hidra.modules.audit.api.rest.response.AuditEventResponse;
import dz.sh.hidra.modules.audit.api.rest.response.AuditExportRequestResponse;
import dz.sh.hidra.modules.audit.application.port.in.RecordAuditAccessUseCase;
import dz.sh.hidra.modules.audit.application.port.in.RecordAuditEventUseCase;
import dz.sh.hidra.modules.audit.application.port.in.RequestAuditExportUseCase;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Spring MVC adapter exposing audit REST endpoints.
 */
@RestController
@Validated
@RequestMapping("/api/v1/audit")
public class SpringAuditController implements AuditController {

    private final RecordAuditAccessUseCase recordAuditAccessUseCase;
    private final RecordAuditEventUseCase recordAuditEventUseCase;
    private final RequestAuditExportUseCase requestAuditExportUseCase;

    public SpringAuditController(
            RecordAuditAccessUseCase recordAuditAccessUseCase,
            RecordAuditEventUseCase recordAuditEventUseCase,
            RequestAuditExportUseCase requestAuditExportUseCase
    ) {
        this.recordAuditAccessUseCase = Objects.requireNonNull(recordAuditAccessUseCase, "RecordAuditAccessUseCase must not be null.");
        this.recordAuditEventUseCase = Objects.requireNonNull(recordAuditEventUseCase, "RecordAuditEventUseCase must not be null.");
        this.requestAuditExportUseCase = Objects.requireNonNull(requestAuditExportUseCase, "RequestAuditExportUseCase must not be null.");
    }

    @GetMapping("/capabilities")
    public Map<String, Object> capabilities() {
        return Map.of(
                "module", "audit",
                "mission", "Preserve accountability, traceability, and audit evidence across Hidra operations.",
                "objectives", List.of(
                "Record business audit events.",
                "Record access traces for sensitive operational data.",
                "Request audit exports for compliance and investigation."
        ),
                "operations", List.of(
                "recordAuditAccess",
                "recordAuditEvent",
                "requestAuditExport"
        ),
                "resourceEndpoints", List.of(
                "POST /api/v1/audit/access-records",
                "POST /api/v1/audit/events",
                "POST /api/v1/audit/exports"
        )
        );
    }

    @Override
    @PostMapping({"/record-audit-access", "/access-records"})
    public AuditAccessRecordResponse recordAuditAccess(@Valid @RequestBody RecordAuditAccessRequest request) {
        Objects.requireNonNull(request, "RecordAuditAccessRequest must not be null.");
        return AuditRestMapper.toResponse(recordAuditAccessUseCase.recordAuditAccess(AuditRestMapper.toCommand(request)));
    }

    @Override
    @PostMapping({"/record-audit-event", "/events"})
    public AuditEventResponse recordAuditEvent(@Valid @RequestBody RecordAuditEventRequest request) {
        Objects.requireNonNull(request, "RecordAuditEventRequest must not be null.");
        return AuditRestMapper.toResponse(recordAuditEventUseCase.recordAuditEvent(AuditRestMapper.toCommand(request)));
    }

    @Override
    @PostMapping({"/request-audit-export", "/exports"})
    public AuditExportRequestResponse requestAuditExport(@Valid @RequestBody RequestAuditExportRequest request) {
        Objects.requireNonNull(request, "RequestAuditExportRequest must not be null.");
        return AuditRestMapper.toResponse(requestAuditExportUseCase.requestAuditExport(AuditRestMapper.toCommand(request)));
    }

}
