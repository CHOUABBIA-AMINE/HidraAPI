/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyTypeTranslationRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.application.port.out
 *
 * @Description : Repository port for PartyTypeTranslation.
 *
 */
package dz.sh.hidra.modules.party.application.port.out;

import dz.sh.hidra.modules.party.domain.model.PartyTypeTranslation;

import java.util.Optional;

/**
 * Repository port for PartyTypeTranslation.
 */
public interface PartyTypeTranslationRepositoryPort {

    PartyTypeTranslation save(PartyTypeTranslation model);

    Optional<PartyTypeTranslation> findById(String id);
}
