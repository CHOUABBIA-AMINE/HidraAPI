/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-09-28
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
import dz.sh.hidra.modules.party.api.rest.request.AssignPartyRoleRequest;
import dz.sh.hidra.modules.party.api.rest.request.RegisterPartyRequest;
import dz.sh.hidra.modules.party.api.rest.response.PartyResponse;
import dz.sh.hidra.modules.party.application.command.AssignPartyRoleCommand;
import dz.sh.hidra.modules.party.application.command.RegisterPartyCommand;
import dz.sh.hidra.modules.party.application.dto.PartySummaryDto;
import java.util.Objects;

/**
 * Maps party REST models to application models.
 */
public final class PartyRestMapper {

    private static final PartyGeneratedRestMapper GENERATED = PartyGeneratedRestMapper.INSTANCE;

    private PartyRestMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static AssignPartyRoleCommand toCommand(AssignPartyRoleRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "AssignPartyRoleRequest must not be null."));
    }

    public static RegisterPartyCommand toCommand(RegisterPartyRequest request) {
        return GENERATED.toCommand(Objects.requireNonNull(request, "RegisterPartyRequest must not be null."));
    }

    public static PartyResponse toResponse(PartySummaryDto dto) {
        return GENERATED.toResponse(Objects.requireNonNull(dto, "PartySummaryDto must not be null."));
    }
}
