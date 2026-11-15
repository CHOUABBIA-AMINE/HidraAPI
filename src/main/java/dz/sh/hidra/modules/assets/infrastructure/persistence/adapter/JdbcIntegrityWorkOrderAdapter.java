/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : JdbcIntegrityWorkOrderAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-15
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : assets
 * @Package     : dz.sh.hidra.modules.assets.infrastructure.persistence.adapter
 * @Description : T18 source-locked owner-private immutable maintenance evidence adapter.
 */
package dz.sh.hidra.modules.assets.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.assets.application.port.out.IntegrityWorkOrderStorePort;
import dz.sh.hidra.modules.assets.application.port.in.IntegrityWorkOrderUseCase;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class JdbcIntegrityWorkOrderAdapter implements IntegrityWorkOrderStorePort {
    private final JdbcTemplate jdbc;
    public JdbcIntegrityWorkOrderAdapter(JdbcTemplate jdbc){this.jdbc=Objects.requireNonNull(jdbc);}
    private static final RowMapper<State> STATES=(rs,n)->new State(
            rs.getString("work_order_id"),rs.getString("approval_id"),rs.getString("finding_id"),
            rs.getString("recommendation_id"),rs.getString("company_id"),rs.getString("asset_type"),
            rs.getString("asset_id"),rs.getString("maintainable_asset_id"),rs.getString("status"),
            rs.getLong("version"),rs.getString("assigned_actor_id"),rs.getString("completion_id"));
    private static final RowMapper<Event> EVENTS=(rs,n)->new Event(
            rs.getString("work_order_id"),rs.getString("id"),rs.getString("command_id"),
            rs.getString("command_sha256"),rs.getString("operation"),rs.getString("status"),
            rs.getLong("version"),rs.getString("actor_id"),rs.getString("execution_id"),
            rs.getString("verdict"),rs.getString("summary"),rs.getTimestamp("recorded_at").toInstant());
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public State lock(String id){
        var rows=jdbc.query("""
            select * from public.hidra_asset_integrity_work_order where work_order_id=? for update
            """,STATES,id);
        if(rows.size()!=1)throw new NoSuchElementException("Protected maintenance order unavailable.");
        return rows.getFirst();
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Optional<State> read(String id){
        var rows=jdbc.query("""
            select * from public.hidra_asset_integrity_work_order where work_order_id=?
            """,STATES,id);
        return rows.stream().findFirst();
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Optional<Event> replay(String commandId,String digest){
        var rows=jdbc.query("""
            select * from public.hidra_asset_integrity_work_order_event where command_id=?
            """,EVENTS,commandId);
        if(rows.isEmpty())return Optional.empty();
        Event e=rows.getFirst();
        if(!e.digest().equals(digest))throw new IllegalStateException("Maintenance command key reused with a different payload.");
        return Optional.of(e);
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public boolean hasExistingRecommendation(String id){
        Integer n=jdbc.queryForObject("""
            select count(*) from public.hidra_asset_maintenance_work_order
            where source_recommendation_id=?
            """,Integer.class,id);
        return n!=null&&n>0;
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public void createState(State s){
        jdbc.update("""
            insert into public.hidra_asset_integrity_work_order
               (work_order_id,approval_id,finding_id,recommendation_id,company_id,asset_type,asset_id,
                maintainable_asset_id,status,version,assigned_actor_id,completion_id,created_at)
            values (?,?,?,?,?,?,?,?,?,0,null,null,now())
            """,s.workOrderId(),s.approvalId(),s.findingId(),s.recommendationId(),
                s.companyId(),s.assetType(),s.assetId(),s.maintainableAssetId(),"DRAFT");
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Event append(State s,String key,String digest,String op,String status,String actor,
            String assignee,String completionId,String verdict,String summary,Instant at){
        String id=UUID.randomUUID().toString();
        long version="CREATE".equals(op)?0:s.version()+1;
        String actualCompletion="EXECUTE".equals(op)?id:completionId;
        String executionId="EXECUTE".equals(op)?id:null;
        if(!"CREATE".equals(op)){
            int changed=jdbc.update("""
                update public.hidra_asset_integrity_work_order
                   set status=?,version=?,assigned_actor_id=?,completion_id=?
                where work_order_id=? and version=?
                """,status,version,
                "ASSIGN".equals(op)?assignee:s.assignedActorId(),
                actualCompletion,s.workOrderId(),s.version());
            if(changed!=1)throw new IllegalStateException("Stale maintenance state.");
        }
        jdbc.update("""
            insert into public.hidra_asset_integrity_work_order_event
              (id,work_order_id,command_id,command_sha256,operation,status,version,actor_id,
               execution_id,verdict,summary,recorded_at)
            values (?,?,?,?,?,?,?,?,?,?,?,?)
            """,id,s.workOrderId(),key,digest,op,status,version,actor,executionId,verdict,
                summary,Timestamp.from(at));
        if("EXECUTE".equals(op)){
            jdbc.update("""
                insert into public.hidra_asset_maintenance_execution_record
                    (id,work_order_id,execution_result,performed_by_actor_id,executed_at,
                     result_summary,created_at)
                values (?,?,'SUCCESS',?,?,?,?)
                """,executionId,s.workOrderId(),actor,Timestamp.from(at),summary,Timestamp.from(Instant.now()));
        }
        return new Event(s.workOrderId(),id,key,digest,op,status,version,actor,executionId,verdict,summary,at);
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public List<IntegrityWorkOrderUseCase.Event> history(String id){
        List<Event> rows=jdbc.query("""
            select * from public.hidra_asset_integrity_work_order_event
            where work_order_id=? order by version,id limit 101
            """,EVENTS,id);
        if(rows.size()>100)throw new IllegalStateException("Maintenance history exceeds bounded read.");
        return rows.stream().map(e->new IntegrityWorkOrderUseCase.Event(e.eventId(),e.operation(),
                e.status(),e.version(),e.actorId(),e.executionId(),e.verdict(),
                e.summary(),e.recordedAt())).toList();
    }
}
