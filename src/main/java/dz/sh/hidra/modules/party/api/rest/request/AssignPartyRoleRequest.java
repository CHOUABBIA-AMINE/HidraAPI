/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssignPartyRoleRequest
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Record
 * @Layer       : API
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.api.rest.request
 *
 * @Description : REST request for assign party role.
 *
 */
package dz.sh.hidra.modules.party.api.rest.request;

import java.time.Instant;

/**
 * REST request for assign party role.
 */
public record AssignPartyRoleRequest(
        String partyId,
        String roleId,
        Instant validFrom,
        Instant validTo,
        boolean qualificationRequired
) {
}
