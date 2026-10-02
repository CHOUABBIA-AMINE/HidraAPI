/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartySelectionService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Domain
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.domain.service
 *
 * @Description : Validates whether a party can be selected for new references.
 *
 */
package dz.sh.hidra.modules.party.domain.service;

import dz.sh.hidra.modules.party.domain.exception.BlockedPartySelectionException;
import dz.sh.hidra.modules.party.domain.model.Party;

/**
 * Validates whether a party can be selected for new operational references.
 */
public class PartySelectionService {

    public void ensureSelectable(Party party, boolean authorizedOverride) {
        if (party == null) {
            throw new BlockedPartySelectionException("Party must not be null.");
        }
        if (!party.selectableForNewReference() && !authorizedOverride) {
            throw new BlockedPartySelectionException("Party is not selectable for new references without authorized override.");
        }
    }
}
