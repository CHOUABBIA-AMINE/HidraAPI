/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyAdministrationUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-04
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.application.port.in
 *
 * @Description : Defines the bounded Party administration and discovery contract.
 *
 */
package dz.sh.hidra.modules.party.application.port.in;

import dz.sh.hidra.modules.party.domain.model.Party;
import dz.sh.hidra.modules.party.application.command.RegisterPartyCommand;
import dz.sh.hidra.modules.party.application.command.AssignPartyRoleCommand;
import java.time.Instant;
import java.util.List;
public interface PartyAdministrationUseCase {
    record Page(List<Party> items,long total,int offset,int limit) { public Page { items=List.copyOf(items); } }
    // Transport-neutral application projection: REST never imports the Party aggregate.
    record View(String id,String code,String partyTypeId,String legalName,String tradeName,
            String shortName,String countryCode,String jurisdictionCode,String status,
            String primaryRoleCodeSnapshot,Instant createdAt,Instant updatedAt) {
        public static View from(Party party) {
            return new View(party.id(),party.code(),party.partyTypeId(),party.legalName(),
                    party.tradeName(),party.shortName(),party.countryCode(),party.jurisdictionCode(),
                    party.status().name(),party.primaryRoleCodeSnapshot(),party.createdAt(),party.updatedAt());
        }
    }
    record ViewPage(List<View> items,long total,int offset,int limit) {
        public ViewPage { items=List.copyOf(items); }
    }
    record Choice(String id,String code,String label,boolean qualificationRequired) { }
    record History(String id,String oldStatus,String newStatus,String reason,String details,String actor,Instant at) { }
    Party register(RegisterPartyCommand input);
    String assign(AssignPartyRoleCommand input);
    Party get(String id);
    Page search(String query,String typeId,String roleId,boolean eligibleOnly,int offset,int limit);
    List<Choice> types(String language);
    List<Choice> roles(String language);
    List<History> history(String id,int offset,int limit);
    Party transition(String id,Instant expected,boolean activate,String reason);
    Party edit(String id,Instant expected,String legalName,String tradeName,String shortName,String reason);
    default View view(String id) { return View.from(get(id)); }
    default ViewPage searchViews(String query,String typeId,String roleId,boolean eligibleOnly,int offset,int limit) {
        Page page=search(query,typeId,roleId,eligibleOnly,offset,limit);
        return new ViewPage(page.items().stream().map(View::from).toList(),page.total(),page.offset(),page.limit());
    }
    default View transitionView(String id,Instant expected,boolean activate,String reason) {
        return View.from(transition(id,expected,activate,reason));
    }
    default View editView(String id,Instant expected,String legalName,String tradeName,String shortName,String reason) {
        return View.from(edit(id,expected,legalName,tradeName,shortName,reason));
    }
}
