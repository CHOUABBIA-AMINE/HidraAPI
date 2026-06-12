/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : CustodyPartyReference
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Domain
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.domain.value
 *
 * @Description : Neutral external party snapshot.
 *
 */
package dz.sh.hidra.modules.custody.domain.value;

/**
 * Neutral external party snapshot.
 *
 * @param partyId party identifier
 * @param partyCodeSnapshot party code snapshot
 * @param partyNameSnapshot party name snapshot
 * @param partyRoleCodeSnapshot party role code snapshot
 */
public record CustodyPartyReference(
        String partyId,
        String partyCodeSnapshot,
        String partyNameSnapshot,
        String partyRoleCodeSnapshot
) {
}
