/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyRiskSnapshotRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.application.port.out
 *
 * @Description : Repository port for PartyRiskSnapshot.
 *
 */
package dz.sh.hidra.modules.party.application.port.out;

import dz.sh.hidra.modules.party.domain.model.PartyRiskSnapshot;

import java.util.Optional;

/**
 * Repository port for PartyRiskSnapshot.
 */
public interface PartyRiskSnapshotRepositoryPort {

    PartyRiskSnapshot save(PartyRiskSnapshot model);

    Optional<PartyRiskSnapshot> findById(String id);
}
