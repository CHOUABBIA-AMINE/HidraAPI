/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyReferenceProjection
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.projection
 *
 * @Description : Party reference projection for external modules.
 *
 */
package dz.sh.hidra.modules.party.infrastructure.projection;

import dz.sh.hidra.modules.party.domain.value.PartyStatus;

/**
 * Party reference projection for external modules.
 */
public record PartyReferenceProjection(
        String partyId,
        String partyCodeSnapshot,
        String partyNameSnapshot,
        String partyRoleCodeSnapshot,
        PartyStatus partyStatusSnapshot
) {
}
