/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.api.rest.mapper
 *
 * @Description : Maps party REST models to application models.
 *
 */
package dz.sh.hidra.modules.party.api.rest.mapper;

import dz.sh.hidra.modules.party.api.rest.request.RegisterPartyRequest;
import dz.sh.hidra.modules.party.api.rest.response.PartyResponse;
import dz.sh.hidra.modules.party.application.command.RegisterPartyCommand;
import dz.sh.hidra.modules.party.application.dto.PartySummaryDto;

/**
 * Maps party REST models to application models.
 */
public final class PartyRestMapper {

    private PartyRestMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static RegisterPartyCommand toCommand(RegisterPartyRequest request) {
        return new RegisterPartyCommand(
                request.code(),
                request.partyTypeId(),
                request.legalName(),
                request.tradeName(),
                request.shortName(),
                request.countryCode(),
                request.jurisdictionCode(),
                request.primaryRoleCodeSnapshot()
        );
    }

    public static PartyResponse toResponse(PartySummaryDto dto) {
        return new PartyResponse(
                dto.id(),
                dto.code(),
                dto.legalName(),
                dto.tradeName(),
                dto.shortName(),
                dto.countryCode(),
                dto.status(),
                dto.primaryRoleCodeSnapshot()
        );
    }
}
