/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyComplianceStatusRepositoryPort
 * @CreatedOn   : 2026-06-11
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.application.port.out
 *
 * @Description : Repository port for PartyComplianceStatus.
 *
 */
package dz.sh.hidra.modules.party.application.port.out;

import dz.sh.hidra.modules.party.domain.model.PartyComplianceStatus;

import java.util.Optional;

/**
 * Repository port for PartyComplianceStatus.
 */
public interface PartyComplianceStatusRepositoryPort {

    PartyComplianceStatus save(PartyComplianceStatus model);

    Optional<PartyComplianceStatus> findById(String id);
}
