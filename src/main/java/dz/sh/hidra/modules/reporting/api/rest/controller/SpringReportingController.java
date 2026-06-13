/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SpringReportingController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.api.rest.controller
 *
 * @Description : Spring MVC adapter exposing reporting REST endpoints.
 *
 */
package dz.sh.hidra.modules.reporting.api.rest.controller;
import dz.sh.hidra.modules.reporting.api.rest.mapper.ReportingRestMapper;
import dz.sh.hidra.modules.reporting.api.rest.request.CreateReportDefinitionRequest;
import dz.sh.hidra.modules.reporting.api.rest.request.GenerateReportArtifactRequest;
import dz.sh.hidra.modules.reporting.api.rest.request.QueueReportRunRequest;
import dz.sh.hidra.modules.reporting.api.rest.request.RequestReportRequest;
import dz.sh.hidra.modules.reporting.api.rest.response.ReportDefinitionResponse;
import dz.sh.hidra.modules.reporting.api.rest.response.ReportOutputArtifactResponse;
import dz.sh.hidra.modules.reporting.api.rest.response.ReportRequestResponse;
import dz.sh.hidra.modules.reporting.api.rest.response.ReportRunResponse;
import dz.sh.hidra.modules.reporting.application.port.in.CreateReportDefinitionUseCase;
import dz.sh.hidra.modules.reporting.application.port.in.GenerateReportArtifactUseCase;
import dz.sh.hidra.modules.reporting.application.port.in.QueueReportRunUseCase;
import dz.sh.hidra.modules.reporting.application.port.in.RequestReportUseCase;
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
 * Spring MVC adapter exposing reporting REST endpoints.
 */
@RestController
@Validated
@RequestMapping("/api/v1/reporting")
public class SpringReportingController implements ReportingController {

    private final CreateReportDefinitionUseCase createReportDefinitionUseCase;
    private final GenerateReportArtifactUseCase generateReportArtifactUseCase;
    private final QueueReportRunUseCase queueReportRunUseCase;
    private final RequestReportUseCase requestReportUseCase;

    public SpringReportingController(
            CreateReportDefinitionUseCase createReportDefinitionUseCase,
            GenerateReportArtifactUseCase generateReportArtifactUseCase,
            QueueReportRunUseCase queueReportRunUseCase,
            RequestReportUseCase requestReportUseCase
    ) {
        this.createReportDefinitionUseCase = Objects.requireNonNull(createReportDefinitionUseCase, "CreateReportDefinitionUseCase must not be null.");
        this.generateReportArtifactUseCase = Objects.requireNonNull(generateReportArtifactUseCase, "GenerateReportArtifactUseCase must not be null.");
        this.queueReportRunUseCase = Objects.requireNonNull(queueReportRunUseCase, "QueueReportRunUseCase must not be null.");
        this.requestReportUseCase = Objects.requireNonNull(requestReportUseCase, "RequestReportUseCase must not be null.");
    }

    @GetMapping("/capabilities")
    public Map<String, Object> capabilities() {
        return Map.of(
                "module", "reporting",
                "mission", "Produce controlled reports and artifacts for operational, risk, and analytics decisions.",
                "objectives", List.of(
                "Create report definitions.",
                "Request reports.",
                "Queue report runs and generate report artifacts."
        ),
                "operations", List.of(
                "createReportDefinition",
                "generateReportArtifact",
                "queueReportRun",
                "requestReport"
        ),
                "resourceEndpoints", List.of(
                "POST /api/v1/reporting/definitions",
                "POST /api/v1/reporting/artifacts",
                "POST /api/v1/reporting/runs",
                "POST /api/v1/reporting/requests"
        )
        );
    }

    @Override
    @PostMapping({"/create-report-definition", "/definitions"})
    public ReportDefinitionResponse createReportDefinition(@Valid @RequestBody CreateReportDefinitionRequest request) {
        Objects.requireNonNull(request, "CreateReportDefinitionRequest must not be null.");
        return ReportingRestMapper.toResponse(createReportDefinitionUseCase.createReportDefinition(ReportingRestMapper.toCommand(request)));
    }

    @Override
    @PostMapping({"/generate-report-artifact", "/artifacts"})
    public ReportOutputArtifactResponse generateReportArtifact(@Valid @RequestBody GenerateReportArtifactRequest request) {
        Objects.requireNonNull(request, "GenerateReportArtifactRequest must not be null.");
        return ReportingRestMapper.toResponse(generateReportArtifactUseCase.generateReportArtifact(ReportingRestMapper.toCommand(request)));
    }

    @Override
    @PostMapping({"/queue-report-run", "/runs"})
    public ReportRunResponse queueReportRun(@Valid @RequestBody QueueReportRunRequest request) {
        Objects.requireNonNull(request, "QueueReportRunRequest must not be null.");
        return ReportingRestMapper.toResponse(queueReportRunUseCase.queueReportRun(ReportingRestMapper.toCommand(request)));
    }

    @Override
    @PostMapping({"/request-report", "/requests"})
    public ReportRequestResponse requestReport(@Valid @RequestBody RequestReportRequest request) {
        Objects.requireNonNull(request, "RequestReportRequest must not be null.");
        return ReportingRestMapper.toResponse(requestReportUseCase.requestReport(ReportingRestMapper.toCommand(request)));
    }

}
