/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JdbcMonitoringRuleRevisionAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-13
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : monitoring
 * @Package     : dz.sh.hidra.modules.monitoring.infrastructure.persistence.adapter
 *
 * @Description : Serializes Monitoring rule versions and immutable activation records.
 *
 */
package dz.sh.hidra.modules.monitoring.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.monitoring.application.port.out.MonitoringRuleRevisionPort;
import dz.sh.hidra.modules.monitoring.domain.model.MonitoringRuleRevision;
import dz.sh.hidra.modules.monitoring.domain.value.MonitoringRuleType;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Objects;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
public final class JdbcMonitoringRuleRevisionAdapter implements MonitoringRuleRevisionPort {
    private static final String VIEW = """
        select v.rule_id,v.revision,v.company_party_id,v.asset_type,v.asset_id,v.telemetry_point_id,
        v.rule_type,v.unit_id,v.evidence_policy_code,v.valid_from,v.valid_to,v.created_by_actor_id,
        v.created_at,a.effective_from,a.effective_until,a.activated_at,a.activated_by_actor_id
        from public.hidra_monitoring_rule_revision v
        left join public.hidra_monitoring_rule_activation a
          on a.rule_id=v.rule_id and a.revision=v.revision
        """;
    private final JdbcTemplate jdbc;
    public JdbcMonitoringRuleRevisionAdapter(JdbcTemplate jdbc){this.jdbc=Objects.requireNonNull(jdbc);}

    @Override @Transactional(propagation=Propagation.MANDATORY)
    public void register(String ruleId,String company,String type,String asset,String actor){
        jdbc.update("""
            insert into public.hidra_monitoring_rule_scope
            (rule_id,company_party_id,asset_type,asset_id,created_by_actor_id,created_at)
            values (?,?,?,?,?,now())
            """,ruleId,company,type,asset,actor);
        jdbc.update("""
            insert into public.hidra_monitoring_rule_head
            (rule_id,last_revision,active_revision,effective_from,effective_until)
            values (?,0,0,null,null)
            """,ruleId);
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Optional<RuleScope> scope(String ruleId){
        return jdbc.query("""
            select rule_id,company_party_id,asset_type,asset_id
            from public.hidra_monitoring_rule_scope where rule_id=?
            """,(rs,n)->new RuleScope(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)),
            ruleId).stream().findFirst();
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public RuleContext ruleContext(String ruleId){
        return jdbc.query("""
            select telemetry_point_id,rule_type,status from public.hidra_monitoring_rule where id=?
            """,(rs,n)->new RuleContext(rs.getString(1),
                    MonitoringRuleType.valueOf(rs.getString(2)),
                    dz.sh.hidra.modules.monitoring.domain.value.MonitoringLifecycleStatus.valueOf(rs.getString(3))),
            ruleId).stream().findFirst()
            .orElseThrow(()->new IllegalArgumentException("Rule context unavailable."));
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public boolean policyAvailable(String code){
        if(!"TRUSTED_PLAN_COMPARISON".equals(code))return false;
        Integer n=jdbc.queryForObject("""
            select count(*) from public.hidra_monitoring_catalog_entry
            where catalog_name='EVIDENCE_POLICY' and code=? and active=true
            """,Integer.class,code);
        return n!=null&&n==1;
    }
    private void lock(String ruleId){
        jdbc.queryForObject("select id from public.hidra_monitoring_rule where id=? for update",
                String.class,ruleId);
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public MonitoringRuleRevision create(MonitoringRuleRevision candidate){
        lock(candidate.ruleId());
        Long previous=jdbc.queryForObject("""
            select last_revision from public.hidra_monitoring_rule_head where rule_id=? for update
            """,Long.class,candidate.ruleId());
        if(previous==null)throw new IllegalStateException("Rule head unavailable.");
        long revision=Math.addExact(previous,1);
        jdbc.update("""
            insert into public.hidra_monitoring_rule_revision
            (rule_id,revision,company_party_id,asset_type,asset_id,telemetry_point_id,
             rule_type,unit_id,evidence_policy_code,valid_from,valid_to,
             created_by_actor_id,created_at)
            values (?,?,?,?,?,?,?,?,?,?,?,?,?)
            """,candidate.ruleId(),revision,candidate.companyPartyId(),candidate.assetType(),
            candidate.assetId(),candidate.telemetryPointId(),candidate.ruleType().name(),
            candidate.unitId(),candidate.evidencePolicyCode(),Timestamp.from(candidate.validFrom()),
            Timestamp.from(candidate.validTo()),candidate.createdByActorId(),
            Timestamp.from(candidate.createdAt()));
        jdbc.update("""
            update public.hidra_monitoring_rule_head set last_revision=? where rule_id=?
            """,revision,candidate.ruleId());
        return exact(candidate.ruleId(),revision).orElseThrow();
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public MonitoringRuleRevision activate(String ruleId,long revision,long expectedHead,
            Instant from,Instant until,String actor){
        lock(ruleId);
        var head=jdbc.query("""
            select active_revision,effective_from,effective_until
            from public.hidra_monitoring_rule_head where rule_id=? for update
            """,(rs,n)->new Head(rs.getLong(1),stamp(rs,"effective_from"),
                    stamp(rs,"effective_until")),ruleId).stream().findFirst()
            .orElseThrow(()->new IllegalStateException("Rule head unavailable."));
        if(head.activeRevision()!=expectedHead){
            if(head.activeRevision()==revision){
                var existing=exact(ruleId,revision).orElseThrow();
                if(from.equals(existing.effectiveFrom())&&until.equals(existing.effectiveUntil())
                        &&actor.equals(existing.activatedByActorId()))return existing;
            }
            throw new IllegalStateException("Stale expected rule head.");
        }
        if(head.effectiveUntil()!=null&&from.isBefore(head.effectiveUntil()))
            throw new IllegalStateException("Overlapping effective rule intervals.");
        var candidate=exact(ruleId,revision).orElseThrow(
                ()->new IllegalArgumentException("Unknown candidate revision."));
        if(candidate.activatedAt()!=null||from.isBefore(candidate.validFrom())
                ||until.isAfter(candidate.validTo())||!from.isBefore(until))
            throw new IllegalStateException("Invalid or already activated rule.");
        jdbc.update("""
            insert into public.hidra_monitoring_rule_activation
            (rule_id,revision,effective_from,effective_until,activated_by_actor_id,activated_at)
            values (?,?,?,?,?,now())
            """,ruleId,revision,Timestamp.from(from),Timestamp.from(until),actor);
        jdbc.update("""
            update public.hidra_monitoring_rule_head
            set active_revision=?,effective_from=?,effective_until=? where rule_id=?
            """,revision,Timestamp.from(from),Timestamp.from(until),ruleId);
        jdbc.update("""
            update public.hidra_monitoring_rule set status='ACTIVE',updated_at=now() where id=?
            """,ruleId);
        return exact(ruleId,revision).orElseThrow();
    }
    private record Head(long activeRevision,Instant effectiveFrom,Instant effectiveUntil){ }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Optional<MonitoringRuleRevision> exact(String ruleId,long revision){
        return jdbc.query(VIEW+" where v.rule_id=? and v.revision=?",MAPPER,
                ruleId,revision).stream().findFirst();
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Optional<MonitoringRuleRevision> effective(String ruleId,Instant at){
        return jdbc.query(VIEW+"""
            where v.rule_id=? and a.effective_from <= ? and a.effective_until > ?
            order by a.effective_from desc limit 1
            """,MAPPER,ruleId,Timestamp.from(at),Timestamp.from(at)).stream().findFirst();
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public List<MonitoringRuleRevision> history(String ruleId,int limit){
        return List.copyOf(jdbc.query(VIEW+" where v.rule_id=? order by v.revision desc limit ?",
                MAPPER,ruleId,limit));
    }
    private static Instant stamp(ResultSet rs,String name)throws SQLException{
        Timestamp t=rs.getTimestamp(name);return t==null?null:t.toInstant();
    }
    private static final RowMapper<MonitoringRuleRevision> MAPPER=(rs,n)->new MonitoringRuleRevision(
            rs.getString("rule_id"),rs.getLong("revision"),rs.getString("company_party_id"),
            rs.getString("asset_type"),rs.getString("asset_id"),rs.getString("telemetry_point_id"),
            MonitoringRuleType.valueOf(rs.getString("rule_type")),rs.getString("unit_id"),
            rs.getString("evidence_policy_code"),stamp(rs,"valid_from"),stamp(rs,"valid_to"),
            rs.getString("created_by_actor_id"),stamp(rs,"created_at"),
            stamp(rs,"effective_from"),stamp(rs,"effective_until"),stamp(rs,"activated_at"),
            rs.getString("activated_by_actor_id"));
}
