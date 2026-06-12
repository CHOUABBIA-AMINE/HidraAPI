/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssignPartyRoleCommand
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Record
 * @Layer       : Application
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.application.command
 *
 * @Description : Command to assign a party role.
 *
 */
package dz.sh.hidra.modules.party.application.command;

import java.time.Instant;

/**
 * Command to assign a party role.
 */
public record AssignPartyRoleCommand(
        String partyId,
        String roleId,
        Instant validFrom,
        Instant validTo,
        boolean qualificationRequired
) {
}
