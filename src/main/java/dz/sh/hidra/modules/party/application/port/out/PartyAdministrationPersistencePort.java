/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyAdministrationPersistencePort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-04
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.application.port.out
 *
 * @Description : Separates Party owner persistence from application coordination.
 *
 */
package dz.sh.hidra.modules.party.application.port.out;

import dz.sh.hidra.modules.party.application.port.in.PartyAdministrationUseCase.*;
import dz.sh.hidra.modules.party.domain.model.Party;
import dz.sh.hidra.modules.party.domain.model.PartyRoleAssignment;
import java.time.Instant;
import java.util.List;
public interface PartyAdministrationPersistencePort {
    void lockWrites();
    Party get(String id);
    Page search(String query,String typeId,String roleId,boolean eligibleOnly,int offset,int limit);
    List<Choice> choices(boolean roles,String language);
    List<History> history(String id,int offset,int limit);
    Party create(Party party,String actor);
    String assign(PartyRoleAssignment assignment,String actor);
    Party transition(String id,Instant expected,boolean activate,String reason,String actor);
    Party edit(String id,Instant expected,String legalName,String tradeName,String shortName,String reason,String actor);
}
