/**
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 * @Name        : JdbcWorkflowInboxRepositoryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-19
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : notification
 * @Package     : dz.sh.hidra.modules.notification.infrastructure.persistence.adapter
 * @Description : PostgreSQL recipient-scoped keyset, idempotent assignment delivery and immutable acknowledgements.
 */
package dz.sh.hidra.modules.notification.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.notification.application.port.out.WorkflowInboxRepositoryPort;
import dz.sh.hidra.modules.notification.domain.model.WorkflowInboxItem;
import dz.sh.hidra.modules.notification.domain.model.WorkflowInboxItem.Scope;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(propagation=Propagation.MANDATORY)
public class JdbcWorkflowInboxRepositoryAdapter implements WorkflowInboxRepositoryPort {
    private static final String ITEMS="public.hidra_notification_t27_inbox_item";
    private static final String ACK="public.hidra_notification_t27_inbox_acknowledgement";
    private static final String COLUMNS="SELECT i.*,a.acknowledged_at FROM "+ITEMS
            +" i LEFT JOIN "+ACK+" a ON a.notification_id=i.id AND a.actor_id=i.recipient_actor_id ";
    private final JdbcTemplate jdbc;
    public JdbcWorkflowInboxRepositoryAdapter(JdbcTemplate jdbc) { this.jdbc=Objects.requireNonNull(jdbc); }
    @Override public WorkflowInboxItem deliver(WorkflowInboxItem value) {
        jdbc.update("INSERT INTO "+ITEMS
                +" (id,event_code,message_code,channel,delivery_status,company_id,facility_id,"
                +" recipient_actor_id,workflow_task_id,assigned_at,delivered_at)"
                +" VALUES (?,?,?,?,?,?,?,?,?,?,?) ON CONFLICT (event_code,workflow_task_id) DO NOTHING",
                value.id(),value.eventCode(),value.messageCode(),value.channel().name(),value.deliveryStatus(),
                value.companyId(),value.facilityId(),value.recipientActorId(),value.workflowTaskId(),
                java.sql.Timestamp.from(value.assignedAt()),java.sql.Timestamp.from(value.deliveredAt()));
        List<WorkflowInboxItem> found=jdbc.query(COLUMNS
                +" WHERE i.event_code=? AND i.workflow_task_id=?",JdbcWorkflowInboxRepositoryAdapter::row,
                value.eventCode(),value.workflowTaskId());
        if (found.size()!=1) throw new IllegalStateException("Missing inbox assignment receipt.");
        WorkflowInboxItem existing=found.getFirst();
        if (!existing.companyId().equals(value.companyId())
                || !existing.facilityId().equals(value.facilityId())
                || !existing.recipientActorId().equals(value.recipientActorId())
                || !existing.assignedAt().equals(value.assignedAt())
                || !existing.messageCode().equals(value.messageCode()))
            throw new IllegalStateException("Conflicting inbox event replay.");
        return existing;
    }
    @Override public List<WorkflowInboxItem> list(Scope scope,String actor,int max,Cursor before) {
        String predicate=" WHERE i.company_id=? AND i.facility_id=? AND i.recipient_actor_id=?";
        if (before==null) {
            return jdbc.query(COLUMNS+predicate+" ORDER BY i.delivered_at DESC,i.id DESC LIMIT ?",
                    JdbcWorkflowInboxRepositoryAdapter::row,
                    scope.companyId(),scope.facilityId(),actor,max);
        }
        return jdbc.query(COLUMNS+predicate
                    +" AND (i.delivered_at<? OR (i.delivered_at=? AND i.id<?))"
                    +" ORDER BY i.delivered_at DESC,i.id DESC LIMIT ?",
                JdbcWorkflowInboxRepositoryAdapter::row,
                scope.companyId(),scope.facilityId(),actor,
                java.sql.Timestamp.from(before.deliveredAt()),
                java.sql.Timestamp.from(before.deliveredAt()),before.id(),max);
    }
    @Override public Optional<WorkflowInboxItem> detail(
            Scope scope,String actor,String id,boolean lock) {
        return jdbc.query(COLUMNS+" WHERE i.company_id=? AND i.facility_id=?"
                        +" AND i.recipient_actor_id=? AND i.id=?"+(lock?" FOR UPDATE OF i":""),
                JdbcWorkflowInboxRepositoryAdapter::row,
                scope.companyId(),scope.facilityId(),actor,id).stream().findFirst();
    }
    @Override public Instant acknowledge(Scope scope,String actor,String id) {
        int count=jdbc.update("INSERT INTO "+ACK
                        +" (notification_id,actor_id,acknowledged_at)"
                        +" SELECT i.id,i.recipient_actor_id,GREATEST(clock_timestamp(),i.delivered_at)"
                        +" FROM "+ITEMS+" i WHERE i.company_id=? AND i.facility_id=?"
                        +" AND i.recipient_actor_id=? AND i.id=?"
                        +" ON CONFLICT (notification_id) DO NOTHING",
                scope.companyId(),scope.facilityId(),actor,id);
        if (count<0 || count>1) throw new IllegalStateException("Unexpected inbox receipt count.");
        List<Instant> times=jdbc.query("SELECT a.acknowledged_at FROM "+ACK+" a JOIN "+ITEMS
                        +" i ON i.id=a.notification_id AND i.recipient_actor_id=a.actor_id"
                        +" WHERE i.company_id=? AND i.facility_id=? AND i.recipient_actor_id=? AND i.id=?",
                (r,n)->r.getTimestamp(1).toInstant(),scope.companyId(),scope.facilityId(),actor,id);
        if (times.size()!=1) throw new NoSuchElementException("Protected inbox unavailable.");
        return times.getFirst();
    }
    private static WorkflowInboxItem row(ResultSet r,int n) throws SQLException {
        if (!"WEB".equals(r.getString("channel")) || !"DELIVERED".equals(r.getString("delivery_status")))
            throw new IllegalStateException("Unsupported inbox delivery state.");
        var receipt=r.getTimestamp("acknowledged_at");
        return new WorkflowInboxItem(r.getString("id"),r.getString("event_code"),r.getString("message_code"),
                r.getString("company_id"),r.getString("facility_id"),
                r.getString("recipient_actor_id"),r.getString("workflow_task_id"),
                r.getTimestamp("assigned_at").toInstant(),r.getTimestamp("delivered_at").toInstant(),
                receipt==null?null:receipt.toInstant());
    }
}
