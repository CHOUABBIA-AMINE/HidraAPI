/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyOwnershipLinkRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.application.port.out
 *
 * @Description : Repository port for PartyOwnershipLink.
 *
 */
package dz.sh.hidra.modules.party.application.port.out;

import dz.sh.hidra.modules.party.domain.model.PartyOwnershipLink;

import java.util.Optional;

/**
 * Repository port for PartyOwnershipLink.
 */
public interface PartyOwnershipLinkRepositoryPort {

    PartyOwnershipLink save(PartyOwnershipLink model);

    Optional<PartyOwnershipLink> findById(String id);
}
