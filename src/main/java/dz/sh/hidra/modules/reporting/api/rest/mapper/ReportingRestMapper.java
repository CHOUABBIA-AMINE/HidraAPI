/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ReportingRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : reporting
 * @Package     : dz.sh.hidra.modules.reporting.api.rest.mapper
 *
 * @Description : Maps reporting REST models to application models.
 *
 */
package dz.sh.hidra.modules.reporting.api.rest.mapper;
import dz.sh.hidra.modules.reporting.api.rest.request.CreateReportDefinitionRequest;
import dz.sh.hidra.modules.reporting.api.rest.request.GenerateReportArtifactRequest;
import dz.sh.hidra.modules.reporting.api.rest.request.QueueReportRunRequest;
import dz.sh.hidra.modules.reporting.api.rest.request.RequestReportRequest;
import dz.sh.hidra.modules.reporting.api.rest.response.ReportDefinitionResponse;
import dz.sh.hidra.modules.reporting.api.rest.response.ReportOutputArtifactResponse;
import dz.sh.hidra.modules.reporting.api.rest.response.ReportRequestResponse;
import dz.sh.hidra.modules.reporting.api.rest.response.ReportRunResponse;
import dz.sh.hidra.modules.reporting.application.command.CreateReportDefinitionCommand;
import dz.sh.hidra.modules.reporting.application.command.GenerateReportArtifactCommand;
import dz.sh.hidra.modules.reporting.application.command.QueueReportRunCommand;
import dz.sh.hidra.modules.reporting.application.command.RequestReportCommand;
import dz.sh.hidra.modules.reporting.application.dto.ReportDefinitionSummaryDto;
import dz.sh.hidra.modules.reporting.application.dto.ReportOutputArtifactSummaryDto;
import dz.sh.hidra.modules.reporting.application.dto.ReportRequestSummaryDto;
import dz.sh.hidra.modules.reporting.application.dto.ReportRunSummaryDto;
import java.util.Objects;

/**
 * Maps reporting REST models to application models.
 */
public final class ReportingRestMapper {

    private static final ReportingGeneratedRestMapper GENERATED = ReportingGeneratedRestMapper.INSTANCE;

    private ReportingRestMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static CreateReportDefinitionCommand toCommand(CreateReportDefinitionRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "CreateReportDefinitionRequest must not be null."));
    }

    public static GenerateReportArtifactCommand toCommand(GenerateReportArtifactRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "GenerateReportArtifactRequest must not be null."));
    }

    public static QueueReportRunCommand toCommand(QueueReportRunRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "QueueReportRunRequest must not be null."));
    }

    public static RequestReportCommand toCommand(RequestReportRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "RequestReportRequest must not be null."));
    }

    public static ReportDefinitionResponse toResponse(ReportDefinitionSummaryDto dto) {
        return GENERATED.toResponse(Objects.requireNonNull(dto, "ReportDefinitionSummaryDto must not be null."));
    }

    public static ReportOutputArtifactResponse toResponse(ReportOutputArtifactSummaryDto dto) {
        return GENERATED.toResponse(Objects.requireNonNull(dto, "ReportOutputArtifactSummaryDto must not be null."));
    }

    public static ReportRunResponse toResponse(ReportRunSummaryDto dto) {
        return GENERATED.toResponse(Objects.requireNonNull(dto, "ReportRunSummaryDto must not be null."));
    }

    public static ReportRequestResponse toResponse(ReportRequestSummaryDto dto) {
        return GENERATED.toResponse(Objects.requireNonNull(dto, "ReportRequestSummaryDto must not be null."));
    }
}
