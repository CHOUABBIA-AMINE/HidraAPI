/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyRoleAssignmentRepositoryPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-05
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.application.port.out
 *
 * @Description : Repository port for PartyRoleAssignment.
 *
 */
package dz.sh.hidra.modules.party.application.port.out;

import dz.sh.hidra.modules.party.domain.model.PartyRoleAssignment;

import java.util.Optional;

/**
 * Repository port for PartyRoleAssignment.
 */
public interface PartyRoleAssignmentRepositoryPort {

    PartyRoleAssignment save(PartyRoleAssignment model);

    Optional<PartyRoleAssignment> findById(String id);

    boolean existsActiveByPartyIdAndRoleId(String partyId, String roleId);
}
