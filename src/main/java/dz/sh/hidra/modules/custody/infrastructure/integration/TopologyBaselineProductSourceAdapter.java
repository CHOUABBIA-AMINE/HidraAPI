/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyBaselineProductSourceAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-03
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.integration
 *
 * @Description : Reads exact immutable product publications in the admitted baseline transaction.
 *
 */
package dz.sh.hidra.modules.custody.infrastructure.integration;


import dz.sh.hidra.modules.custody.application.contract.topology.TopologyBaselineProductSourceContract;
import dz.sh.hidra.modules.custody.infrastructure.persistence.adapter.ProductRevisionPublicationCodec;
import dz.sh.hidra.modules.custody.infrastructure.persistence.adapter.SuppliedFluidPropertyRevisionCodec;
import dz.sh.hidra.modules.custody.domain.model.ProductRevisionPublication;
import dz.sh.hidra.platform.persistence.ProtectedRelationFence;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.security.access.AccessDeniedException;

@Component
public final class TopologyBaselineProductSourceAdapter implements TopologyBaselineProductSourceContract {
    private static final String PREFIX="public.hidra_custody_product_publication";
    private static final List<String> TABLES=List.of(PREFIX,PREFIX+"_head",PREFIX+"_event",
        "public.hidra_custody_catalog_entry");
    private final JdbcTemplate jdbc;
    private final dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor executor;
    private final ProductRevisionPublicationCodec codec=new ProductRevisionPublicationCodec();
    public TopologyBaselineProductSourceAdapter(JdbcTemplate jdbc,dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor executor){this.jdbc=Objects.requireNonNull(jdbc);this.executor=Objects.requireNonNull(executor);}
    @Override public Admission acquire(Request request,Original origin) {
        caller();Objects.requireNonNull(request);Objects.requireNonNull(origin);
        executor.requireEngineeringBaselineSourceLock();
        jdbc.queryForObject("SELECT public.hidra_custody_baseline_lock_product_source()",Boolean.class);
        var t=new Ticket(this,request,origin,holder());t.facts=observe(t);return t;
    }
    @Override public Facts read(Admission admission){var t=ticket(admission);return t.facts;}
    @Override public void requireCurrent(Admission admission){
        var t=ticket(admission);if(!t.facts.equals(observe(t)))throw denied();
    }
    @Override public List<ProtectedRelationFence> finalFences(Admission admission){
        requireCurrent(admission);
        return TABLES.stream().map(x->new ProtectedRelationFence(x,ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE)).toList();
    }
    private Facts observe(Ticket t) {
        physical(t);
        var q=t.request;
        var rows=jdbc.query("SELECT canonical_payload,sha256,payload_format FROM "+PREFIX
            +" WHERE company_party_id=? AND product_draft_id=? AND catalog_product_id=? AND publication_id=?",
            (r,n)->{
                byte[] b=r.getBytes(1);
                if(b==null||b.length>4*1024*1024||!q.sha256().equals(r.getString(2))
                    ||!ProductRevisionPublication.hash(b).equals(q.sha256())
                    ||!ProductRevisionPublicationCodec.FORMAT.equals(r.getString(3)))throw denied();
                return codec.publication(b);
            },q.companyPartyId(),q.draftId(),q.catalogProductId(),q.publicationId());
        if(rows.size()!=1)throw denied();
        var p=rows.getFirst();var envelope=p.envelope();var scope=envelope.scope();
        if(!scope.companyPartyId().equals(q.companyPartyId())||!scope.productDraftId().equals(q.draftId())
            ||!scope.catalogProductId().equals(q.catalogProductId())||!p.publicationId().equals(q.publicationId())
            ||!p.approval().digest().equals(ProductRevisionPublication.hash(codec.encode(envelope))))throw denied();
        var from=jdbc.queryForObject("SELECT min(effective_at) FROM "+PREFIX
            +"_event WHERE company_party_id=? AND product_draft_id=? AND publication_id=? AND action='PUBLISH'",
            Timestamp.class,q.companyPartyId(),q.draftId(),q.publicationId());
        if(from==null)throw denied();
        var until=jdbc.queryForObject("SELECT min(effective_at) FROM "+PREFIX
            +"_event WHERE company_party_id=? AND product_draft_id=? AND effective_at>? AND (publication_id<>? OR state<>'ACTIVE')",
            Timestamp.class,q.companyPartyId(),q.draftId(),from,q.publicationId());
        boolean eligible=Boolean.TRUE.equals(jdbc.queryForObject("SELECT EXISTS(SELECT 1 FROM "+PREFIX
            +"_head WHERE company_party_id=? AND product_draft_id=? AND catalog_product_id=? AND publication_id=? AND state='ACTIVE')",
            Boolean.class,q.companyPartyId(),q.draftId(),q.catalogProductId(),q.publicationId()));
        var facts=new SuppliedFluidPropertyRevisionCodec().decode(envelope.supplied().bytes());
        // Exact source owner diagnostics preserve missing methods, applicability and provenance.
        boolean methods=facts.methods()!=null&&!facts.methods().isEmpty()&&facts.diagnostics().stream().noneMatch(x->x.contains("METHOD"));
        boolean complete=facts.diagnostics().isEmpty()&&envelope.diagnostics().isEmpty();
        physical(t);
        return new Facts(from.toInstant(),until==null?null:until.toInstant(),eligible,methods,complete,true);
    }
    private ConnectionHolder holder(){
        if(jdbc.getDataSource()==null||!TransactionSynchronizationManager.isActualTransactionActive()
            ||!TransactionSynchronizationManager.isSynchronizationActive()
            ||TransactionSynchronizationManager.isCurrentTransactionReadOnly()
            ||!(TransactionSynchronizationManager.getResource(jdbc.getDataSource()) instanceof ConnectionHolder h)
            ||h.isRollbackOnly())throw denied();
        return h;
    }
    private Ticket ticket(Admission a){
        caller();
        if(!(a instanceof Ticket t)||t.owner!=this||t.thread!=Thread.currentThread()
            ||holder()!=t.holder||t.connection!=t.holder.getConnection())throw denied();
        physical(t);return t;
    }
    private void physical(Ticket t){
        if(!Boolean.TRUE.equals(jdbc.queryForObject(
            "SELECT pg_backend_pid()=? AND txid_current()=? AND current_user=? AND session_user=? AND clock_timestamp()>=? AND clock_timestamp()<?",
            Boolean.class,t.origin.backendPid(),t.origin.transactionId(),t.origin.databaseRole(),t.origin.databaseRole(),
            Timestamp.from(t.origin.sampledAt()),Timestamp.from(t.origin.validUntil()))))throw denied();
    }
    private static void caller(){
        if(!StackWalker.getInstance().walk(fs->fs.anyMatch(f->f.getClassName().equals(
            "dz.sh.hidra.modules.topology.infrastructure.integration.EngineeringBaselineSourceAdapter"))))throw denied();
    }
    private static AccessDeniedException denied(){return new AccessDeniedException("Baseline product source denied");}
    private static final class Ticket implements Admission {
        final TopologyBaselineProductSourceAdapter owner;final Request request;final Original origin;
        final ConnectionHolder holder;final java.sql.Connection connection;
        final Thread thread=Thread.currentThread();Facts facts;
        Ticket(TopologyBaselineProductSourceAdapter owner,Request request,Original origin,ConnectionHolder holder){
            this.owner=owner;this.request=request;this.origin=origin;this.holder=holder;this.connection=holder.getConnection();
        }
    }
}
