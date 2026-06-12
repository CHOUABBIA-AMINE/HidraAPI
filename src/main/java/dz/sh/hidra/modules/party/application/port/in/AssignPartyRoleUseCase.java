/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AssignPartyRoleUseCase
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.application.port.in
 *
 * @Description : Use case for assigning party role.
 *
 */
package dz.sh.hidra.modules.party.application.port.in;

import dz.sh.hidra.modules.party.application.command.AssignPartyRoleCommand;

/**
 * Use case for assigning party role.
 */
public interface AssignPartyRoleUseCase {

    String assignRole(AssignPartyRoleCommand command);
}
