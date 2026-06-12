/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RegisterPartyCommand
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.application.command
 *
 * @Description : Command to register a party.
 *
 */
package dz.sh.hidra.modules.party.application.command;

/**
 * Command to register a party.
 */
public record RegisterPartyCommand(
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
