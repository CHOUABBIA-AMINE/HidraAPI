/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PartyAdministrationApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-04
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.application.service
 *
 * @Description : Coordinates current administration, owner locking and Party operations.
 *
 */
package dz.sh.hidra.modules.party.application.service;

import dz.sh.hidra.modules.party.application.port.in.PartyAdministrationUseCase;
import dz.sh.hidra.modules.party.application.port.out.PartyAdministrationPersistencePort;
import dz.sh.hidra.modules.party.application.port.out.PartyAdministrationAuthorizationPort;
import dz.sh.hidra.modules.party.application.command.RegisterPartyCommand;
import dz.sh.hidra.modules.party.application.command.AssignPartyRoleCommand;
import dz.sh.hidra.modules.party.domain.model.Party;
import dz.sh.hidra.modules.party.domain.model.PartyRoleAssignment;
import dz.sh.hidra.modules.party.domain.value.*;
import dz.sh.hidra.modules.party.domain.service.PartyLifecyclePolicy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Isolation;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.Objects;
@Service
@Transactional(readOnly=true)
public class PartyAdministrationApplicationService implements PartyAdministrationUseCase {
    private final PartyAdministrationPersistencePort store;
    private final PartyAdministrationAuthorizationPort authority;
    public PartyAdministrationApplicationService(PartyAdministrationPersistencePort store,PartyAdministrationAuthorizationPort authority) {
        this.store=Objects.requireNonNull(store);this.authority=Objects.requireNonNull(authority);
    }
    private String writer() { authority.administrator();store.lockWrites();return authority.administrator(); }
    private void page(int offset,int limit) { if(offset<0||offset>100000||limit<1||limit>100)throw new IllegalArgumentException("Invalid Party page."); }
    private String language(String language) { if(language==null||!language.matches("[a-z]{2}(?:-[A-Z]{2})?"))throw new IllegalArgumentException("Invalid catalog language.");return language; }
    @Override public Party get(String id) { authority.administrator();return store.get(id); }
    @Override public Page search(String query,String typeId,String roleId,boolean eligibleOnly,int offset,int limit) {
        authority.administrator();page(offset,limit);if(query!=null&&query.length()>200)throw new IllegalArgumentException("Query too long.");
        return store.search(query,typeId,roleId,eligibleOnly,offset,limit);
    }
    @Override public List<Choice> types(String language) { authority.administrator();return store.choices(false,language(language)); }
    @Override public List<Choice> roles(String language) { authority.administrator();return store.choices(true,language(language)); }
    @Override public List<History> history(String id,int offset,int limit) { authority.administrator();page(offset,limit);store.get(id);return store.history(id,offset,limit); }
    @Override @Transactional(isolation=Isolation.READ_COMMITTED,rollbackFor=Exception.class)
    public Party register(RegisterPartyCommand c) {
        String actor=writer();Objects.requireNonNull(c);PartyLifecyclePolicy.metadata(c.legalName(),c.tradeName(),c.shortName());
        PartyLifecyclePolicy.registration(c.code(),c.partyTypeId(),c.countryCode(),c.jurisdictionCode(),c.primaryRoleCodeSnapshot());
        Instant now=Instant.now();var p=new Party(UUID.randomUUID().toString(),c.code(),c.partyTypeId(),c.legalName(),c.tradeName(),c.shortName(),c.countryCode(),c.jurisdictionCode(),PartyStatus.DRAFT,c.primaryRoleCodeSnapshot(),now,now);
        Party saved=store.create(p,actor);authority.administrator();return saved;
    }
    @Override @Transactional(isolation=Isolation.READ_COMMITTED,rollbackFor=Exception.class)
    public String assign(AssignPartyRoleCommand c) {
        String actor=writer();Objects.requireNonNull(c);Instant now=Instant.now();
        var a=new PartyRoleAssignment(UUID.randomUUID().toString(),c.partyId(),c.roleId(),c.validFrom()==null?now:c.validFrom(),c.validTo(),PartyRoleAssignmentStatus.ACTIVE,c.qualificationRequired(),now,now);
        String id=store.assign(a,actor);authority.administrator();return id;
    }
    @Override @Transactional(isolation=Isolation.READ_COMMITTED,rollbackFor=Exception.class)
    public Party transition(String id,Instant expected,boolean activate,String reason) {
        String actor=writer();PartyLifecyclePolicy.reason(reason);Party p=store.transition(id,expected,activate,reason.strip(),actor);authority.administrator();return p;
    }
    @Override @Transactional(isolation=Isolation.READ_COMMITTED,rollbackFor=Exception.class)
    public Party edit(String id,Instant expected,String legal,String trade,String shortName,String reason) {
        String actor=writer();PartyLifecyclePolicy.reason(reason);PartyLifecyclePolicy.metadata(legal,trade,shortName);
        Party p=store.edit(id,expected,legal.strip(),trade,shortName,reason.strip(),actor);authority.administrator();return p;
    }
    // REST projection entry points need their own proxied write transaction.
    // A default interface wrapper self-calls transition/edit and bypasses their advice.
    @Override @Transactional(isolation=Isolation.READ_COMMITTED,rollbackFor=Exception.class)
    public View transitionView(String id,Instant expected,boolean activate,String reason) {
        return View.from(transition(id,expected,activate,reason));
    }
    @Override @Transactional(isolation=Isolation.READ_COMMITTED,rollbackFor=Exception.class)
    public View editView(String id,Instant expected,String legalName,String tradeName,String shortName,String reason) {
        return View.from(edit(id,expected,legalName,tradeName,shortName,reason));
    }
}
