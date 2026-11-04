/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JpaPartyRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-06-11
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence.adapter
 *
 * @Description : Database-backed adapter for Party.
 *
 */
package dz.sh.hidra.modules.party.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.party.application.port.out.PartyRepositoryPort;
import dz.sh.hidra.modules.party.domain.model.Party;
import dz.sh.hidra.modules.party.infrastructure.persistence.mapper.PartyPersistenceMapper;
import dz.sh.hidra.modules.party.infrastructure.persistence.repository.PartyJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

/**
 * Database-backed repository adapter for Party.
 */
@Component
public class JpaPartyRepositoryAdapter implements PartyRepositoryPort, dz.sh.hidra.modules.party.application.port.out.PartyAdministrationPersistencePort {

    private final PartyJpaRepository repository;
    private org.springframework.jdbc.core.JdbcTemplate jdbc;

    public JpaPartyRepositoryAdapter(PartyJpaRepository repository) {
        this.repository = Objects.requireNonNull(repository, "PartyJpaRepository must not be null.");
    }

    @org.springframework.beans.factory.annotation.Autowired
    public JpaPartyRepositoryAdapter(PartyJpaRepository repository,org.springframework.jdbc.core.JdbcTemplate jdbc) {
        this(repository);this.jdbc=Objects.requireNonNull(jdbc);
    }

    @Override public void lockWrites() {
        if(jdbc==null || !org.springframework.transaction.support.TransactionSynchronizationManager.isActualTransactionActive()
                || org.springframework.transaction.support.TransactionSynchronizationManager.isCurrentTransactionReadOnly())
            throw new IllegalStateException("Party owner write transaction required.");
        jdbc.execute("LOCK TABLE public.hidra_party_party IN SHARE ROW EXCLUSIVE MODE");
        jdbc.execute("LOCK TABLE public.hidra_party_type,public.hidra_party_role,public.hidra_party_role_assignment IN SHARE MODE");
    }

    private Party row(java.sql.ResultSet rs,int index) throws java.sql.SQLException {
        return new Party(rs.getString("id"),rs.getString("code"),rs.getString("party_type_id"),rs.getString("legal_name"),rs.getString("trade_name"),rs.getString("short_name"),rs.getString("country_code"),rs.getString("jurisdiction_code"),dz.sh.hidra.modules.party.domain.value.PartyStatus.valueOf(rs.getString("status")),rs.getString("primary_role_code_snapshot"),rs.getTimestamp("created_at").toInstant(),rs.getTimestamp("updated_at").toInstant());
    }
    @Override public Party get(String id) {
        if(id==null||id.isBlank()||id.length()>80)throw new IllegalArgumentException("Invalid Party id.");
        var rows=jdbc.query("SELECT * FROM public.hidra_party_party WHERE id=?",this::row,id);
        if(rows.size()!=1)throw new java.util.NoSuchElementException("Party unavailable.");
        return rows.getFirst();
    }
    @Override public dz.sh.hidra.modules.party.application.port.in.PartyAdministrationUseCase.Page search(String query,String typeId,String roleId,boolean eligibleOnly,int offset,int limit) {
        String where=" FROM public.hidra_party_party p JOIN public.hidra_party_type t ON t.id=p.party_type_id"
                +" WHERE (?::text IS NULL OR p.code ILIKE '%'||?||'%' OR p.legal_name ILIKE '%'||?||'%')"
                +" AND (?::text IS NULL OR p.party_type_id=?)"
                +" AND (NOT ? OR (p.status='ACTIVE' AND t.status='ACTIVE'))"
                +" AND (?::text IS NULL OR EXISTS (SELECT 1 FROM public.hidra_party_role_assignment a"
                +" JOIN public.hidra_party_role r ON r.id=a.role_id WHERE a.party_id=p.id AND a.role_id=?"
                +" AND a.status='ACTIVE' AND r.status='ACTIVE' AND a.valid_from<=clock_timestamp()"
                +" AND (a.valid_to IS NULL OR clock_timestamp()<a.valid_to)))";
        Object[] args={query,query,query,typeId,typeId,eligibleOnly,roleId,roleId};
        Long total=jdbc.queryForObject("SELECT count(*)"+where,Long.class,args);
        var parameters=new java.util.ArrayList<Object>(java.util.Arrays.asList(args));parameters.add(limit);parameters.add(offset);
        var items=jdbc.query("SELECT p.*"+where+" ORDER BY p.code,p.id LIMIT ? OFFSET ?",this::row,parameters.toArray());
        return new dz.sh.hidra.modules.party.application.port.in.PartyAdministrationUseCase.Page(items,total==null?0:total,offset,limit);
    }
    @Override public java.util.List<dz.sh.hidra.modules.party.application.port.in.PartyAdministrationUseCase.Choice> choices(boolean roles,String language) {
        String table=roles?"role":"type",foreign=roles?"party_role_id":"party_type_id";
        String qualification=roles?"p.qualification_required_by_default":"false";
        return jdbc.query("SELECT p.id,p.code,COALESCE(t.label,p.code),"+qualification
                +" FROM public.hidra_party_"+table+" p LEFT JOIN public.hidra_party_"+table+"_translation t"
                +" ON t."+foreign+"=p.id AND t.language_code=? WHERE p.status='ACTIVE' ORDER BY p.code,p.id LIMIT 1000",
                (rs,n)->new dz.sh.hidra.modules.party.application.port.in.PartyAdministrationUseCase.Choice(rs.getString(1),rs.getString(2),rs.getString(3),rs.getBoolean(4)),language);
    }
    @Override public java.util.List<dz.sh.hidra.modules.party.application.port.in.PartyAdministrationUseCase.History> history(String id,int offset,int limit) {
        return jdbc.query("SELECT id,old_status,new_status,reason,reason_message,changed_by_actor_id,changed_at"
                +" FROM public.hidra_party_status_history WHERE party_id=? ORDER BY changed_at,id LIMIT ? OFFSET ?",
                (rs,n)->new dz.sh.hidra.modules.party.application.port.in.PartyAdministrationUseCase.History(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getTimestamp(7).toInstant()),id,limit,offset);
    }
    private void activeType(String id) {
        if(!Boolean.TRUE.equals(jdbc.queryForObject("SELECT EXISTS(SELECT 1 FROM public.hidra_party_type WHERE id=? AND status='ACTIVE')",Boolean.class,id)))
            throw new IllegalArgumentException("Active Party type required.");
    }
    private String snapshot(String id) {
        return jdbc.queryForObject("SELECT row_to_json(p)::text FROM public.hidra_party_party p WHERE id=?",String.class,id);
    }
    private void audit(String id,String oldStatus,String newStatus,String reason,String message,String before,String actor) {
        jdbc.update("INSERT INTO public.hidra_party_status_history(id,party_id,old_status,new_status,reason,reason_message,changed_by_actor_id,changed_at)"
                +" VALUES (?,?,?,?,?,jsonb_build_object('reason',?::text,'before',?::jsonb,'after',?::jsonb)::text,?,clock_timestamp())",
                java.util.UUID.randomUUID().toString(),id,oldStatus,newStatus,reason,message,before,snapshot(id),actor);
    }
    @Override public Party create(Party p,String actor) {
        activeType(p.partyTypeId());
        if(Boolean.TRUE.equals(jdbc.queryForObject("SELECT EXISTS(SELECT 1 FROM public.hidra_party_party WHERE code=?)",Boolean.class,p.code())))
            throw new dz.sh.hidra.modules.party.domain.exception.InvalidPartyValueException("Party code must be unique.");
        if(p.primaryRoleCodeSnapshot()!=null && !Boolean.TRUE.equals(jdbc.queryForObject("SELECT EXISTS(SELECT 1 FROM public.hidra_party_role WHERE code=? AND status='ACTIVE')",Boolean.class,p.primaryRoleCodeSnapshot())))
            throw new IllegalArgumentException("Active Party role required.");
        jdbc.update("INSERT INTO public.hidra_party_party(id,code,party_type_id,legal_name,trade_name,short_name,country_code,jurisdiction_code,status,primary_role_code_snapshot,created_at,updated_at)"
                +" VALUES (?,?,?,?,?,?,?,?,'DRAFT',?,clock_timestamp(),clock_timestamp())",p.id(),p.code(),p.partyTypeId(),p.legalName(),p.tradeName(),p.shortName(),p.countryCode(),p.jurisdictionCode(),p.primaryRoleCodeSnapshot());
        audit(p.id(),null,"DRAFT","CREATED","Party registered",null,actor);return get(p.id());
    }
    @Override public String assign(dz.sh.hidra.modules.party.domain.model.PartyRoleAssignment a,String actor) {
        Party p=get(a.partyId());if(!p.selectableForNewReference())throw new IllegalStateException("Active Party required.");activeType(p.partyTypeId());
        var roles=jdbc.queryForList("SELECT qualification_required_by_default FROM public.hidra_party_role WHERE id=? AND status='ACTIVE'",a.roleId());
        if(roles.size()!=1)throw new IllegalArgumentException("Active Party role required.");
        if(Boolean.TRUE.equals(jdbc.queryForObject("SELECT EXISTS(SELECT 1 FROM public.hidra_party_role_assignment WHERE party_id=? AND role_id=? AND status='ACTIVE')",Boolean.class,a.partyId(),a.roleId())))
            throw new dz.sh.hidra.modules.party.domain.exception.InvalidPartyValueException("Party already has an ACTIVE assignment for the requested role.");
        String before=snapshot(p.id());
        jdbc.update("INSERT INTO public.hidra_party_role_assignment(id,party_id,role_id,valid_from,valid_to,status,qualification_required,created_at,updated_at)"
                +" VALUES (?,?,?,?,?,'ACTIVE',?,clock_timestamp(),clock_timestamp())",a.id(),a.partyId(),a.roleId(),java.sql.Timestamp.from(a.validFrom()),a.validTo()==null?null:java.sql.Timestamp.from(a.validTo()),a.qualificationRequired()||Boolean.TRUE.equals(roles.getFirst().get("qualification_required_by_default")));
        audit(p.id(),p.status().name(),p.status().name(),"CORRECTED","Assigned role "+a.roleId(),before,actor);return a.id();
    }
    @Override public Party transition(String id,java.time.Instant expected,boolean activate,String reason,String actor) {
        Party before=get(id);dz.sh.hidra.modules.party.domain.service.PartyLifecyclePolicy.expected(expected,before.updatedAt());
        dz.sh.hidra.modules.party.domain.service.PartyLifecyclePolicy.transition(before.status(),activate);if(activate)activeType(before.partyTypeId());
        String original=snapshot(id),status=activate?"ACTIVE":"SUSPENDED";
        jdbc.update("UPDATE public.hidra_party_party SET status=?,updated_at=GREATEST(clock_timestamp(),updated_at+interval '1 microsecond') WHERE id=?",status,id);
        audit(id,before.status().name(),status,activate?"ACTIVATED":"SUSPENDED",reason,original,actor);return get(id);
    }
    @Override public Party edit(String id,java.time.Instant expected,String legal,String trade,String shortName,String reason,String actor) {
        Party before=get(id);dz.sh.hidra.modules.party.domain.service.PartyLifecyclePolicy.expected(expected,before.updatedAt());
        if(before.status()==dz.sh.hidra.modules.party.domain.value.PartyStatus.BLOCKED||before.status()==dz.sh.hidra.modules.party.domain.value.PartyStatus.RETIRED)
            throw new IllegalStateException("Party metadata is frozen.");
        String original=snapshot(id);
        jdbc.update("UPDATE public.hidra_party_party SET legal_name=?,trade_name=?,short_name=?,updated_at=GREATEST(clock_timestamp(),updated_at+interval '1 microsecond') WHERE id=?",legal,trade,shortName,id);
        audit(id,before.status().name(),before.status().name(),"CORRECTED",reason,original,actor);return get(id);
    }

    @Override
    public Party save(Party model) {
        return PartyPersistenceMapper.toDomain(
                repository.save(PartyPersistenceMapper.toEntity(model))
        );
    }

    @Override
    public Optional<Party> findById(String id) {
        return repository.findById(id).map(PartyPersistenceMapper::toDomain);
    }

    @Override
    public boolean existsByCode(String code) {
        return repository.existsByCode(code);
    }
}
