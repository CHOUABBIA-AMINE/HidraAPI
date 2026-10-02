/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : IdentityGeneratedRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Interface
 * @Layer       : API
 * @Module      : identity
 * @Package     : dz.sh.hidra.modules.identity.api.rest.mapper
 *
 * @Description : Generates exact identity API/application boundary mappings at compile time.
 *
 */
package dz.sh.hidra.modules.identity.api.rest.mapper;

import dz.sh.hidra.modules.identity.api.rest.request.CreateUserRequest;
import dz.sh.hidra.modules.identity.api.rest.response.UserResponse;
import dz.sh.hidra.modules.identity.application.command.CreateUserCommand;
import dz.sh.hidra.modules.identity.application.dto.UserSummaryDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * Generates only the HRA-070-approved exact identity boundary mappings.
 */
@Mapper(
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface IdentityGeneratedRestMapper {

    IdentityGeneratedRestMapper INSTANCE = Mappers.getMapper(IdentityGeneratedRestMapper.class);

    CreateUserCommand toCommand(CreateUserRequest request);

    UserResponse toResponse(UserSummaryDto dto);
}
