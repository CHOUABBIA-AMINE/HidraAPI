/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.api.rest.mapper
 *
 * @Description : Maps risk REST models to application models.
 *
 */
package dz.sh.hidra.modules.risk.api.rest.mapper;
import dz.sh.hidra.modules.risk.api.rest.request.AddRiskEvidenceRequest;
import dz.sh.hidra.modules.risk.api.rest.request.CreateRiskAssessmentRequest;
import dz.sh.hidra.modules.risk.api.rest.request.CreateRiskRegisterRequest;
import dz.sh.hidra.modules.risk.api.rest.response.RiskAssessmentResponse;
import dz.sh.hidra.modules.risk.api.rest.response.RiskRegisterResponse;
import dz.sh.hidra.modules.risk.application.command.AddRiskEvidenceCommand;
import dz.sh.hidra.modules.risk.application.command.CreateRiskAssessmentCommand;
import dz.sh.hidra.modules.risk.application.command.CreateRiskRegisterCommand;
import dz.sh.hidra.modules.risk.application.dto.RiskAssessmentSummaryDto;
import dz.sh.hidra.modules.risk.application.dto.RiskRegisterSummaryDto;
import java.util.Objects;

/**
 * Maps risk REST models to application models.
 */
public final class RiskRestMapper {

    private static final RiskGeneratedRestMapper GENERATED = RiskGeneratedRestMapper.INSTANCE;

    private RiskRestMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static AddRiskEvidenceCommand toCommand(AddRiskEvidenceRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "AddRiskEvidenceRequest must not be null."));
    }

    public static CreateRiskAssessmentCommand toCommand(CreateRiskAssessmentRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "CreateRiskAssessmentRequest must not be null."));
    }

    public static CreateRiskRegisterCommand toCommand(CreateRiskRegisterRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "CreateRiskRegisterRequest must not be null."));
    }

    public static RiskAssessmentResponse toResponse(RiskAssessmentSummaryDto dto) {
        return GENERATED.toResponse(Objects.requireNonNull(dto, "RiskAssessmentSummaryDto must not be null."));
    }

    public static RiskRegisterResponse toResponse(RiskRegisterSummaryDto dto) {
        return GENERATED.toResponse(Objects.requireNonNull(dto, "RiskRegisterSummaryDto must not be null."));
    }
}
