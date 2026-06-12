/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyBankReferenceRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.application.port.out
 *
 * @Description : Repository port for PartyBankReference.
 *
 */
package dz.sh.hidra.modules.party.application.port.out;

import dz.sh.hidra.modules.party.domain.model.PartyBankReference;

import java.util.Optional;

/**
 * Repository port for PartyBankReference.
 */
public interface PartyBankReferenceRepositoryPort {

    PartyBankReference save(PartyBankReference model);

    Optional<PartyBankReference> findById(String id);
}
