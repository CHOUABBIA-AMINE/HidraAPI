/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
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

/**
 * Maps risk REST models to application models.
 */
public final class RiskRestMapper {

    private RiskRestMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static AddRiskEvidenceCommand toCommand(AddRiskEvidenceRequest request) {
        return new AddRiskEvidenceCommand(
                request.riskAssessmentId(),
                request.evidenceModule(),
                request.evidenceType(),
                request.evidenceId(),
                request.evidenceCodeSnapshot(),
                request.evidenceLabelSnapshot(),
                request.evidenceTimestamp(),
                request.evidenceHash(),
                request.evidenceSummary()
        );
    }

    public static CreateRiskAssessmentCommand toCommand(CreateRiskAssessmentRequest request) {
        return new CreateRiskAssessmentCommand(
                request.riskRegisterId(),
                request.assessmentNumber(),
                request.title(),
                request.description(),
                request.assessmentTypeId(),
                request.methodologyId(),
                request.scopeId(),
                request.riskScenarioId(),
                request.assessmentDate(),
                request.validFrom(),
                request.validTo(),
                request.assessedByActorId(),
                request.assessedByDisplayNameSnapshot()
        );
    }

    public static CreateRiskRegisterCommand toCommand(CreateRiskRegisterRequest request) {
        return new CreateRiskRegisterCommand(
                request.code(),
                request.nameAr(),
                request.nameFr(),
                request.nameEn(),
                request.description(),
                request.registerTypeId(),
                request.ownerOrganizationUnitId(),
                request.ownerOrganizationUnitNameSnapshot(),
                request.scopeType(),
                request.scopeId(),
                request.scopeCodeSnapshot(),
                request.scopeLabelSnapshot(),
                request.reviewFrequencyId(),
                request.effectiveFrom(),
                request.effectiveTo(),
                request.createdByActorId(),
                request.createdByDisplayNameSnapshot()
        );
    }

    public static RiskAssessmentResponse toResponse(RiskAssessmentSummaryDto dto) {
        return new RiskAssessmentResponse(
                dto.id(),
                dto.riskRegisterId(),
                dto.assessmentNumber(),
                dto.title(),
                dto.riskScenarioId(),
                dto.status(),
                dto.inherentScore(),
                dto.inherentRatingId(),
                dto.residualScore(),
                dto.residualRatingId(),
                dto.assessmentDate()
        );
    }

    public static RiskRegisterResponse toResponse(RiskRegisterSummaryDto dto) {
        return new RiskRegisterResponse(
                dto.id(),
                dto.code(),
                dto.nameFr(),
                dto.registerTypeId(),
                dto.scopeType(),
                dto.scopeId(),
                dto.status()
        );
    }
}
