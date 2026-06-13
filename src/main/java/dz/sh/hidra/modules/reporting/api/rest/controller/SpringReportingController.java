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
import dz.sh.hidra.modules.reporting.api.rest.request.RequestReportRequest;
import dz.sh.hidra.modules.reporting.api.rest.response.ReportDefinitionResponse;
import dz.sh.hidra.modules.reporting.api.rest.response.ReportRequestResponse;
import dz.sh.hidra.modules.reporting.application.port.in.CreateReportDefinitionUseCase;
import dz.sh.hidra.modules.reporting.application.port.in.RequestReportUseCase;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Objects;

/**
 * Spring MVC adapter exposing reporting REST endpoints.
 */
@RestController
@Validated
@RequestMapping("/api/v1/reporting")
public final class SpringReportingController implements ReportingController {

    private final CreateReportDefinitionUseCase createReportDefinitionUseCase;
    private final RequestReportUseCase requestReportUseCase;

    public SpringReportingController(
            CreateReportDefinitionUseCase createReportDefinitionUseCase,
            RequestReportUseCase requestReportUseCase
    ) {
        this.createReportDefinitionUseCase = Objects.requireNonNull(createReportDefinitionUseCase, "CreateReportDefinitionUseCase must not be null.");
        this.requestReportUseCase = Objects.requireNonNull(requestReportUseCase, "RequestReportUseCase must not be null.");
    }


    @Override
    @PostMapping("/create-report-definition")
    public ReportDefinitionResponse createReportDefinition(@Valid @RequestBody CreateReportDefinitionRequest request) {
        Objects.requireNonNull(request, "CreateReportDefinitionRequest must not be null.");
        return ReportingRestMapper.toResponse(createReportDefinitionUseCase.createReportDefinition(ReportingRestMapper.toCommand(request)));
    }

    @Override
    @PostMapping("/request-report")
    public ReportRequestResponse requestReport(@Valid @RequestBody RequestReportRequest request) {
        Objects.requireNonNull(request, "RequestReportRequest must not be null.");
        return ReportingRestMapper.toResponse(requestReportUseCase.requestReport(ReportingRestMapper.toCommand(request)));
    }

}
