/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : JdbcSimulationManufacturerEvidenceAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-30
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : party
 * @Package     : dz.sh.hidra.modules.party.infrastructure.persistence.adapter
 *
 * @Description : Party-owned exact manufacturer-company-model authorization with original-transaction locks.
 *
 */
package dz.sh.hidra.modules.party.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.party.application.contract.simulation.SimulationManufacturerEvidenceContract;
import dz.sh.hidra.platform.persistence.ProtectedRelationFence;
import java.sql.Connection;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/** Neither a manufacturer role nor an unrelated Party relationship implies a company/model grant. */
@Repository
public class JdbcSimulationManufacturerEvidenceAdapter implements SimulationManufacturerEvidenceContract {
    private static final List<String> RELATIONS=List.of(
        "public.hidra_party_party",
        "public.hidra_party_eligibility_generation",
        "public.hidra_party_role",
        "public.hidra_party_role_assignment",
        "public.hidra_party_manufacturer_profile",
        "public.hidra_party_manufacturer_source_authorization");
    private static final String LOCK="public.hidra_party_manufacturer_authorization_lock()";
    private static final String EVIDENCE="""
        SELECT a.authorization_id,a.company_party_id,a.manufacturer_party_id,
               a.manufacturer_model_reference,pg.generation AS manufacturer_generation,
               cg.generation AS company_generation,
               a.valid_from AS authorization_from,a.valid_until AS authorization_until,
               ra.valid_from AS role_from,ra.valid_to AS role_until,
               pg_catalog.clock_timestamp() AS observed_at
          FROM public.hidra_party_manufacturer_source_authorization a
          JOIN public.hidra_party_party company ON company.id=a.company_party_id AND company.status='ACTIVE'
          JOIN public.hidra_party_eligibility_generation cg ON cg.party_id=company.id AND cg.present AND cg.generation>0
          JOIN public.hidra_party_party m ON m.id=a.manufacturer_party_id AND m.status='ACTIVE'
          JOIN public.hidra_party_eligibility_generation pg ON pg.party_id=m.id AND pg.present AND pg.generation>0
          JOIN public.hidra_party_role_assignment ra ON ra.party_id=m.id AND ra.status='ACTIVE'
          JOIN public.hidra_party_role role ON role.id=ra.role_id AND role.code='MANUFACTURER' AND role.status='ACTIVE'
          JOIN public.hidra_party_manufacturer_profile profile ON profile.party_id=m.id
             AND profile.qualification_status='QUALIFIED'
             AND profile.capability_type IN ('EQUIPMENT','INSTRUMENTATION')
         WHERE a.company_party_id=? AND a.manufacturer_party_id=? AND a.manufacturer_model_reference=?
           AND a.authorization_status='ACTIVE' AND a.revoked_at IS NULL
           AND a.valid_from<=pg_catalog.clock_timestamp()
           AND (a.valid_until IS NULL OR a.valid_until>pg_catalog.clock_timestamp())
           AND ra.valid_from<=pg_catalog.clock_timestamp()
           AND (ra.valid_to IS NULL OR ra.valid_to>pg_catalog.clock_timestamp())
        """;
    private final JdbcTemplate jdbc;
    private final DataSource dataSource;
    public JdbcSimulationManufacturerEvidenceAdapter(JdbcTemplate jdbc) {
        this.jdbc=Objects.requireNonNull(jdbc);
        this.dataSource=Objects.requireNonNull(jdbc.getDataSource());
    }

    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Admission acquire(Requirement requirement) {
        try {
            Objects.requireNonNull(requirement);
            var holder=holder();
            if(!Boolean.TRUE.equals(jdbc.queryForObject("""
                    SELECT p.prosecdef AND p.provolatile='v' AND NOT p.proleakproof
                       AND pg_catalog.has_function_privilege(current_user,p.oid,'EXECUTE')
                       AND NOT EXISTS (
                           SELECT 1 FROM pg_catalog.aclexplode(COALESCE(p.proacl,
                               pg_catalog.acldefault('f',p.proowner))) a
                           WHERE a.grantee=0 AND a.privilege_type='EXECUTE')
                    FROM pg_catalog.pg_proc p
                    WHERE p.oid='public.hidra_party_manufacturer_authorization_lock()'::regprocedure
                    """,Boolean.class))) throw denied();
            // The function locks a reviewed, literal owner relation set and cannot accept caller SQL.
            jdbc.queryForObject("SELECT "+LOCK,(row,index)->Boolean.TRUE);
            String sql="SELECT pg_catalog.pg_backend_pid(),pg_catalog.txid_current(),current_user";
            Ticket ticket=jdbc.queryForObject(sql,(r,n)->new Ticket(this,requirement,holder,
                    holder.getConnection(),r.getInt(1),r.getLong(2),r.getString(3)));
            if(ticket==null)throw denied();
            TransactionSynchronizationManager.registerSynchronization(ticket);
            return ticket;
        }catch(RuntimeException failure){rollback();throw denied();}
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Contribution read(Admission admission) {
        try {
            Ticket ticket=current(admission);
            if(!ticket.used.compareAndSet(false,true))throw denied();
            var r=ticket.requirement;
            List<Contribution> rows=jdbc.query(EVIDENCE,(x,n)-> {
                Instant sampled=x.getObject("observed_at",OffsetDateTime.class).toInstant();
                Instant start=x.getObject("authorization_from",OffsetDateTime.class).toInstant();
                Instant roleStart=x.getObject("role_from",OffsetDateTime.class).toInstant();
                if(sampled.isBefore(start) || sampled.isBefore(roleStart)
                        || x.getLong("manufacturer_generation")<=0 || x.getLong("company_generation")<=0)throw denied();
                var authUntil=x.getObject("authorization_until",OffsetDateTime.class);
                var roleUntil=x.getObject("role_until",OffsetDateTime.class);
                Instant until=authUntil==null?null:authUntil.toInstant();
                if(roleUntil!=null && (until==null || roleUntil.toInstant().isBefore(until)))until=roleUntil.toInstant();
                if(until!=null && !sampled.isBefore(until))throw denied();
                return new Contribution(x.getString("authorization_id"),x.getString("company_party_id"),
                        x.getString("manufacturer_party_id"),x.getString("manufacturer_model_reference"),
                        x.getLong("manufacturer_generation"),sampled,until,ticket.pid,ticket.xid,ticket.role);
            },r.companyPartyId(),r.manufacturerPartyId(),r.manufacturerModelReference());
            if(rows.size()!=1)throw denied();
            ticket.contribution=rows.getFirst();
            current(ticket);
            return ticket.contribution;
        }catch(RuntimeException fault){rollback();throw denied();}
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public void requireCurrent(Admission admission) {
        try {
            Ticket ticket=current(admission);
            if(ticket.contribution==null)throw denied();
            var r=ticket.requirement;
            var rows=jdbc.query(EVIDENCE,(x,n)-> {
                Instant sampled=x.getObject("observed_at",OffsetDateTime.class).toInstant();
                Instant from=x.getObject("authorization_from",OffsetDateTime.class).toInstant();
                Instant roleFrom=x.getObject("role_from",OffsetDateTime.class).toInstant();
                if(sampled.isBefore(from)||sampled.isBefore(roleFrom))throw denied();
                OffsetDateTime a=x.getObject("authorization_until",OffsetDateTime.class);
                OffsetDateTime b=x.getObject("role_until",OffsetDateTime.class);
                Instant until=a==null?null:a.toInstant();
                if(b!=null && (until==null||b.toInstant().isBefore(until)))until=b.toInstant();
                return new Contribution(x.getString("authorization_id"),x.getString("company_party_id"),
                    x.getString("manufacturer_party_id"),x.getString("manufacturer_model_reference"),
                    x.getLong("manufacturer_generation"),sampled,until,ticket.pid,ticket.xid,ticket.role);
            },r.companyPartyId(),r.manufacturerPartyId(),r.manufacturerModelReference());
            if(rows.size()!=1)throw denied();
            Contribution latest=rows.getFirst(),prior=ticket.contribution;
            if(!prior.authorizationId().equals(latest.authorizationId())
                    || prior.partyGeneration()!=latest.partyGeneration()
                    || !Objects.equals(prior.validUntil(),latest.validUntil())
                    || !prior.companyPartyId().equals(latest.companyPartyId())
                    || !prior.manufacturerPartyId().equals(latest.manufacturerPartyId())
                    || !prior.manufacturerModelReference().equals(latest.manufacturerModelReference())
                    || (latest.validUntil()!=null && !latest.observedAt().isBefore(latest.validUntil())))
                throw denied();
            current(ticket);
        }catch(RuntimeException fault){rollback();throw denied();}
    }
    @Override
    public List<ProtectedRelationFence> finalFences(Admission admission) {
        current(admission);
        return RELATIONS.stream().map(x->new ProtectedRelationFence(
                x,ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE)).toList();
    }

    private Ticket current(Admission admission) {
        if(!(admission instanceof Ticket t)||t.owner!=this||!t.active||t.thread!=Thread.currentThread()
                ||t.holder!=holder()||t.holder.getConnection()!=t.connection)throw denied();
        boolean same=Boolean.TRUE.equals(jdbc.queryForObject(
                "SELECT pg_catalog.pg_backend_pid()=? AND pg_catalog.txid_current()=? AND current_user=?"
                +" AND pg_catalog.current_setting('transaction_isolation')='read committed'"
                +" AND pg_catalog.current_setting('transaction_read_only')='off'",
                Boolean.class,t.pid,t.xid,t.role));
        if(!same)throw denied();
        return t;
    }
    private ConnectionHolder holder() {
        if(!TransactionSynchronizationManager.isActualTransactionActive()
                || !TransactionSynchronizationManager.isSynchronizationActive()
                || TransactionSynchronizationManager.isCurrentTransactionReadOnly()
                || !(TransactionSynchronizationManager.getResource(dataSource) instanceof ConnectionHolder h)
                || h.isRollbackOnly())throw denied();
        return h;
    }
    private void rollback() {
        Object x=TransactionSynchronizationManager.getResource(dataSource);
        if(x instanceof ConnectionHolder h)h.setRollbackOnly();
    }
    private static IllegalStateException denied() {return new IllegalStateException("Manufacturer Party evidence unavailable");}
    private static final class Ticket implements Admission,TransactionSynchronization {
        private final JdbcSimulationManufacturerEvidenceAdapter owner;
        private final Requirement requirement;
        private final ConnectionHolder holder;
        private final Connection connection;
        private final Thread thread=Thread.currentThread();
        private final AtomicBoolean used=new AtomicBoolean();
        private final int pid;private final long xid;private final String role;
        private volatile boolean active=true;
        private Contribution contribution;
        Ticket(JdbcSimulationManufacturerEvidenceAdapter owner,Requirement requirement,ConnectionHolder holder,
                Connection connection,int pid,long xid,String role) {
            this.owner=owner;this.requirement=requirement;this.holder=holder;
            this.connection=connection;this.pid=pid;this.xid=xid;this.role=role;
        }
        @Override public void beforeCommit(boolean readOnly) {
            if(!active||readOnly||holder.isRollbackOnly()||contribution==null)throw denied();
        }
        @Override public void afterCompletion(int status) {active=false;}
        @Override public void suspend() {active=false;}
        @Override public String toString(){return "ManufacturerPartyAdmission[opaque]";}
    }
}
