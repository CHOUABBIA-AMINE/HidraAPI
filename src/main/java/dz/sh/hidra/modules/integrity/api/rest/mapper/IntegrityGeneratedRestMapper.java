/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IntegrityGeneratedRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Interface
 * @Layer       : API
 * @Module      : integrity
 * @Package     : dz.sh.hidra.modules.integrity.api.rest.mapper
 *
 * @Description : Generates exact integrity API/application boundary mappings at compile time.
 *
 */
package dz.sh.hidra.modules.integrity.api.rest.mapper;

import dz.sh.hidra.modules.integrity.api.rest.request.CreateIntegrityAssessmentRequest;
import dz.sh.hidra.modules.integrity.api.rest.request.CreateIntegrityProgramRequest;
import dz.sh.hidra.modules.integrity.api.rest.request.OpenIntegrityCaseRequest;
import dz.sh.hidra.modules.integrity.api.rest.response.IntegrityAssessmentResponse;
import dz.sh.hidra.modules.integrity.api.rest.response.IntegrityCaseResponse;
import dz.sh.hidra.modules.integrity.api.rest.response.IntegrityProgramResponse;
import dz.sh.hidra.modules.integrity.application.command.CreateIntegrityAssessmentCommand;
import dz.sh.hidra.modules.integrity.application.command.CreateIntegrityProgramCommand;
import dz.sh.hidra.modules.integrity.application.command.OpenIntegrityCaseCommand;
import dz.sh.hidra.modules.integrity.application.dto.IntegrityAssessmentSummaryDto;
import dz.sh.hidra.modules.integrity.application.dto.IntegrityCaseSummaryDto;
import dz.sh.hidra.modules.integrity.application.dto.IntegrityProgramSummaryDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * Generates only the HRA-070-approved exact integrity boundary mappings.
 */
@Mapper(
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface IntegrityGeneratedRestMapper {

    IntegrityGeneratedRestMapper INSTANCE = Mappers.getMapper(IntegrityGeneratedRestMapper.class);

    CreateIntegrityAssessmentCommand toCommand(CreateIntegrityAssessmentRequest request);

    CreateIntegrityProgramCommand toCommand(CreateIntegrityProgramRequest request);

    OpenIntegrityCaseCommand toCommand(OpenIntegrityCaseRequest request);

    IntegrityAssessmentResponse toResponse(IntegrityAssessmentSummaryDto dto);

    IntegrityCaseResponse toResponse(IntegrityCaseSummaryDto dto);

    IntegrityProgramResponse toResponse(IntegrityProgramSummaryDto dto);
}
