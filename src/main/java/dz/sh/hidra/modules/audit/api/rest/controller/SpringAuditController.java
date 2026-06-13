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
import dz.sh.hidra.modules.audit.api.rest.request.RecordAuditEventRequest;
import dz.sh.hidra.modules.audit.api.rest.request.RequestAuditExportRequest;
import dz.sh.hidra.modules.audit.api.rest.response.AuditEventResponse;
import dz.sh.hidra.modules.audit.api.rest.response.AuditExportRequestResponse;
import dz.sh.hidra.modules.audit.application.port.in.RecordAuditEventUseCase;
import dz.sh.hidra.modules.audit.application.port.in.RequestAuditExportUseCase;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Objects;

/**
 * Spring MVC adapter exposing audit REST endpoints.
 */
@RestController
@Validated
@RequestMapping("/api/v1/audit")
public final class SpringAuditController implements AuditController {

    private final RecordAuditEventUseCase recordAuditEventUseCase;
    private final RequestAuditExportUseCase requestAuditExportUseCase;

    public SpringAuditController(
            RecordAuditEventUseCase recordAuditEventUseCase,
            RequestAuditExportUseCase requestAuditExportUseCase
    ) {
        this.recordAuditEventUseCase = Objects.requireNonNull(recordAuditEventUseCase, "RecordAuditEventUseCase must not be null.");
        this.requestAuditExportUseCase = Objects.requireNonNull(requestAuditExportUseCase, "RequestAuditExportUseCase must not be null.");
    }


    @Override
    @PostMapping("/record-audit-event")
    public AuditEventResponse recordAuditEvent(@Valid @RequestBody RecordAuditEventRequest request) {
        Objects.requireNonNull(request, "RecordAuditEventRequest must not be null.");
        return AuditRestMapper.toResponse(recordAuditEventUseCase.recordAuditEvent(AuditRestMapper.toCommand(request)));
    }

    @Override
    @PostMapping("/request-audit-export")
    public AuditExportRequestResponse requestAuditExport(@Valid @RequestBody RequestAuditExportRequest request) {
        Objects.requireNonNull(request, "RequestAuditExportRequest must not be null.");
        return AuditRestMapper.toResponse(requestAuditExportUseCase.requestAuditExport(AuditRestMapper.toCommand(request)));
    }

}
