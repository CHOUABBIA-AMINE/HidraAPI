/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyRoleRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.application.port.out
 *
 * @Description : Repository port for PartyRole.
 *
 */
package dz.sh.hidra.modules.party.application.port.out;

import dz.sh.hidra.modules.party.domain.model.PartyRole;

import java.util.Optional;

/**
 * Repository port for PartyRole.
 */
public interface PartyRoleRepositoryPort {

    PartyRole save(PartyRole model);

    Optional<PartyRole> findById(String id);
}
