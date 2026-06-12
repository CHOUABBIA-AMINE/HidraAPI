/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyResponse
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.api.rest.response
 *
 * @Description : REST response for party.
 *
 */
package dz.sh.hidra.modules.party.api.rest.response;

import dz.sh.hidra.modules.party.domain.value.PartyStatus;

/**
 * REST response for party.
 */
public record PartyResponse(
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
