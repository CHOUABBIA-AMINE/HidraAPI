/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : HseGeneratedRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Interface
 * @Layer       : API
 * @Module      : hse
 * @Package     : dz.sh.hidra.modules.hse.api.rest.mapper
 *
 * @Description : Generates exact hse API/application boundary mappings at compile time.
 *
 */
package dz.sh.hidra.modules.hse.api.rest.mapper;

import dz.sh.hidra.modules.hse.api.rest.request.CloseHseCaseRequest;
import dz.sh.hidra.modules.hse.api.rest.request.CreateHseCapaRequest;
import dz.sh.hidra.modules.hse.api.rest.request.OpenHseCaseRequest;
import dz.sh.hidra.modules.hse.api.rest.response.HseCapaResponse;
import dz.sh.hidra.modules.hse.api.rest.response.HseCaseResponse;
import dz.sh.hidra.modules.hse.application.command.CloseHseCaseCommand;
import dz.sh.hidra.modules.hse.application.command.CreateHseCapaCommand;
import dz.sh.hidra.modules.hse.application.command.OpenHseCaseCommand;
import dz.sh.hidra.modules.hse.application.dto.HseCapaSummaryDto;
import dz.sh.hidra.modules.hse.application.dto.HseCaseSummaryDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * Generates only the HRA-070-approved exact hse boundary mappings.
 */
@Mapper(
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface HseGeneratedRestMapper {

    HseGeneratedRestMapper INSTANCE = Mappers.getMapper(HseGeneratedRestMapper.class);

    CloseHseCaseCommand toCommand(CloseHseCaseRequest request);

    CreateHseCapaCommand toCommand(CreateHseCapaRequest request);

    OpenHseCaseCommand toCommand(OpenHseCaseRequest request);

    HseCapaResponse toResponse(HseCapaSummaryDto dto);

    HseCaseResponse toResponse(HseCaseSummaryDto dto);
}
