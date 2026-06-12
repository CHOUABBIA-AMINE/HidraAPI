/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyReferenceSnapshot
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.domain.value
 *
 * @Description : Neutral party reference snapshot.
 *
 */
package dz.sh.hidra.modules.assets.domain.value;

/**
 * Neutral party reference snapshot.
 *
 * @param partyId party identifier
 * @param partyCodeSnapshot party code snapshot
 * @param partyNameSnapshot party name snapshot
 * @param partyRoleCodeSnapshot party role code snapshot
 */
public record PartyReferenceSnapshot(
        String partyId,
        String partyCodeSnapshot,
        String partyNameSnapshot,
        String partyRoleCodeSnapshot
) {
}
