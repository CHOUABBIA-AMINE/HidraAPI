/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : FindPartyByIdQuery
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.application.query
 *
 * @Description : Query to find party by ID.
 *
 */
package dz.sh.hidra.modules.party.application.query;

/**
 * Query to find a party by ID.
 */
public record FindPartyByIdQuery(String partyId) {
}
