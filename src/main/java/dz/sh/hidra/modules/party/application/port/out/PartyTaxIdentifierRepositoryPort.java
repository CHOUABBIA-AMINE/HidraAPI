/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyTaxIdentifierRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.application.port.out
 *
 * @Description : Repository port for PartyTaxIdentifier.
 *
 */
package dz.sh.hidra.modules.party.application.port.out;

import dz.sh.hidra.modules.party.domain.model.PartyTaxIdentifier;

import java.util.Optional;

/**
 * Repository port for PartyTaxIdentifier.
 */
public interface PartyTaxIdentifierRepositoryPort {

    PartyTaxIdentifier save(PartyTaxIdentifier model);

    Optional<PartyTaxIdentifier> findById(String id);
}
