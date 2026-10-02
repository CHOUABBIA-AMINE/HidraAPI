/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RegisterPartyRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.api.rest.request
 *
 * @Description : REST request to register party.
 *
 */
package dz.sh.hidra.modules.party.api.rest.request;

/**
 * REST request to register party.
 */
public record RegisterPartyRequest(
        String code,
        String partyTypeId,
        String legalName,
        String tradeName,
        String shortName,
        String countryCode,
        String jurisdictionCode,
        String primaryRoleCodeSnapshot
) {
}
