/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyApplicationMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.application.mapper
 *
 * @Description : Maps party domain models to DTOs.
 *
 */
package dz.sh.hidra.modules.party.application.mapper;

import dz.sh.hidra.modules.party.application.dto.PartySummaryDto;
import dz.sh.hidra.modules.party.domain.model.Party;

/**
 * Maps party domain models to DTOs.
 */
public final class PartyApplicationMapper {

    private PartyApplicationMapper() {
        throw new UnsupportedOperationException("Utility class must not be instantiated.");
    }

    public static PartySummaryDto toSummary(Party party) {
        return new PartySummaryDto(
                party.id(),
                party.code(),
                party.legalName(),
                party.tradeName(),
                party.shortName(),
                party.countryCode(),
                party.status(),
                party.primaryRoleCodeSnapshot()
        );
    }
}
