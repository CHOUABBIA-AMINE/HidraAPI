/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyReferenceSnapshot
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.domain.value
 *
 * @Description : Neutral party reference snapshot for other modules.
 *
 */
package dz.sh.hidra.modules.party.domain.value;

/**
 * Neutral party reference snapshot used by other modules.
 *
 * @param partyId party identifier
 * @param partyCodeSnapshot party code snapshot
 * @param partyNameSnapshot party name snapshot
 * @param partyRoleCodeSnapshot role code snapshot
 * @param partyStatusSnapshot party status snapshot
 */
public record PartyReferenceSnapshot(
        String partyId,
        String partyCodeSnapshot,
        String partyNameSnapshot,
        String partyRoleCodeSnapshot,
        String partyStatusSnapshot
) {
}
