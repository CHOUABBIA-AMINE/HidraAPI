/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RiskGeneratedRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Interface
 * @Layer       : API
 * @Module      : risk
 * @Package     : dz.sh.hidra.modules.risk.api.rest.mapper
 *
 * @Description : Generates exact risk API/application boundary mappings at compile time.
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
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * Generates only the HRA-070-approved exact risk boundary mappings.
 */
@Mapper(
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface RiskGeneratedRestMapper {

    RiskGeneratedRestMapper INSTANCE = Mappers.getMapper(RiskGeneratedRestMapper.class);

    AddRiskEvidenceCommand toCommand(AddRiskEvidenceRequest request);

    CreateRiskAssessmentCommand toCommand(CreateRiskAssessmentRequest request);

    CreateRiskRegisterCommand toCommand(CreateRiskRegisterRequest request);

    RiskAssessmentResponse toResponse(RiskAssessmentSummaryDto dto);

    RiskRegisterResponse toResponse(RiskRegisterSummaryDto dto);
}
