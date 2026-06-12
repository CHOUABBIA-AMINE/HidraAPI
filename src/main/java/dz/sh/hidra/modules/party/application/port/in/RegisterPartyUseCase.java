/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : RegisterPartyUseCase
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.application.port.in
 *
 * @Description : Use case for registering party.
 *
 */
package dz.sh.hidra.modules.party.application.port.in;

import dz.sh.hidra.modules.party.application.command.RegisterPartyCommand;
import dz.sh.hidra.modules.party.application.dto.PartySummaryDto;

/**
 * Use case for registering parties.
 */
public interface RegisterPartyUseCase {

    PartySummaryDto registerParty(RegisterPartyCommand command);
}
