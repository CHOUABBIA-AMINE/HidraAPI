/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyGeneratedRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
 *
 * @Type        : Interface
 * @Layer       : API
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.api.rest.mapper
 *
 * @Description : Generates exact party API/application boundary mappings at compile time.
 *
 */
package dz.sh.hidra.modules.party.api.rest.mapper;

import dz.sh.hidra.modules.party.api.rest.request.AssignPartyRoleRequest;
import dz.sh.hidra.modules.party.api.rest.request.RegisterPartyRequest;
import dz.sh.hidra.modules.party.api.rest.response.PartyResponse;
import dz.sh.hidra.modules.party.application.command.AssignPartyRoleCommand;
import dz.sh.hidra.modules.party.application.command.RegisterPartyCommand;
import dz.sh.hidra.modules.party.application.dto.PartySummaryDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * Generates only the HRA-070-approved exact party boundary mappings.
 */
@Mapper(
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface PartyGeneratedRestMapper {

    PartyGeneratedRestMapper INSTANCE = Mappers.getMapper(PartyGeneratedRestMapper.class);

    AssignPartyRoleCommand toCommand(AssignPartyRoleRequest request);

    RegisterPartyCommand toCommand(RegisterPartyRequest request);

    PartyResponse toResponse(PartySummaryDto dto);
}
