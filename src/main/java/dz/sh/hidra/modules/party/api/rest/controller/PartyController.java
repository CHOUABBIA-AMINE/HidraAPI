/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyController
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-13
 *
 * @Type        : Interface
 * @Layer       : API
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.api.rest.controller
 *
 * @Description : Framework-neutral party controller contract.
 *
 */
package dz.sh.hidra.modules.party.api.rest.controller;
import dz.sh.hidra.modules.party.api.rest.request.*;
import dz.sh.hidra.modules.party.api.rest.response.*;

/**
 * Framework-neutral party controller contract.
 */
public interface PartyController {
    String assignRole(AssignPartyRoleRequest request);
    PartyResponse registerParty(RegisterPartyRequest request);
}
