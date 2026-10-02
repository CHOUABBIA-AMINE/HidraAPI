/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : LeakdetectionGeneratedRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Interface
 * @Layer       : API
 * @Module      : leakdetection
 * @Package     : dz.sh.hidra.modules.leakdetection.api.rest.mapper
 *
 * @Description : Generates exact leakdetection API/application boundary mappings at compile time.
 *
 */
package dz.sh.hidra.modules.leakdetection.api.rest.mapper;

import dz.sh.hidra.modules.leakdetection.api.rest.request.CreateLeakCandidateRequest;
import dz.sh.hidra.modules.leakdetection.api.rest.request.EscalateLeakCaseRequest;
import dz.sh.hidra.modules.leakdetection.api.rest.request.OpenLeakCaseRequest;
import dz.sh.hidra.modules.leakdetection.api.rest.response.LeakCandidateResponse;
import dz.sh.hidra.modules.leakdetection.api.rest.response.LeakCaseResponse;
import dz.sh.hidra.modules.leakdetection.application.command.CreateLeakCandidateCommand;
import dz.sh.hidra.modules.leakdetection.application.command.EscalateLeakCaseCommand;
import dz.sh.hidra.modules.leakdetection.application.command.OpenLeakCaseCommand;
import dz.sh.hidra.modules.leakdetection.application.dto.LeakCandidateSummaryDto;
import dz.sh.hidra.modules.leakdetection.application.dto.LeakCaseSummaryDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * Generates only the HRA-070-approved exact leakdetection boundary mappings.
 */
@Mapper(
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface LeakdetectionGeneratedRestMapper {

    LeakdetectionGeneratedRestMapper INSTANCE = Mappers.getMapper(LeakdetectionGeneratedRestMapper.class);

    CreateLeakCandidateCommand toCommand(CreateLeakCandidateRequest request);

    EscalateLeakCaseCommand toCommand(EscalateLeakCaseRequest request);

    OpenLeakCaseCommand toCommand(OpenLeakCaseRequest request);

    LeakCandidateResponse toResponse(LeakCandidateSummaryDto dto);

    LeakCaseResponse toResponse(LeakCaseSummaryDto dto);
}
