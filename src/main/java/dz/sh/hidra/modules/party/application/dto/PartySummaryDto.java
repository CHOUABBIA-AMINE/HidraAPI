/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartySummaryDto
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.application.dto
 *
 * @Description : Party summary DTO.
 *
 */
package dz.sh.hidra.modules.party.application.dto;

import dz.sh.hidra.modules.party.domain.value.PartyStatus;

/**
 * Party summary DTO.
 */
public record PartySummaryDto(
        String id,
        String code,
        String legalName,
        String tradeName,
        String shortName,
        String countryCode,
        PartyStatus status,
        String primaryRoleCodeSnapshot
) {
}
