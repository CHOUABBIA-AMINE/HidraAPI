/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : TopologyManufacturerCharacteristicReferenceAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-01
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : simulation
 * @Package     : dz.sh.hidra.modules.simulation.infrastructure.integration
 *
 * @Description : Verifies immutable manufacturer source and Party eligibility within Topology's original transaction.
 *
 */
package dz.sh.hidra.modules.simulation.infrastructure.integration;

import dz.sh.hidra.modules.simulation.application.contract.topology.TopologyManufacturerCharacteristicReferenceContract;
import dz.sh.hidra.modules.simulation.application.contract.topology.TopologyManufacturerCharacteristicReferenceContract.*;
import dz.sh.hidra.modules.simulation.domain.model.ManufacturerCharacteristicRevision;
import dz.sh.hidra.modules.simulation.infrastructure.persistence.adapter.ManufacturerCharacteristicRevisionCodec;
import dz.sh.hidra.modules.party.application.contract.simulation.SimulationManufacturerEvidenceContract;
import dz.sh.hidra.platform.persistence.ProtectedRelationFence;
import java.sql.Connection;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/** Private producer adapter: source bytes never leave Simulation or qualify as Workflow approval. */
@Component
public class TopologyManufacturerCharacteristicReferenceAdapter
        implements TopologyManufacturerCharacteristicReferenceContract {
    private static final String TABLE="public.hidra_simulation_manufacturer_characteristic_revision";
    private static final String SOURCE_SQL="""
        SELECT canonical_payload,payload_sha256,payload_format,manufacturer_party_id,manufacturer_model_reference,
               effective_from,effective_until,source_origin
          FROM public.hidra_simulation_manufacturer_characteristic_revision
         WHERE company_party_id=? AND source_id=? AND revision_id=?
        """;
    private final JdbcTemplate jdbc;
    private final DataSource ds;
    private final SimulationManufacturerEvidenceContract party;
    private final ManufacturerCharacteristicRevisionCodec codec=new ManufacturerCharacteristicRevisionCodec();
    public TopologyManufacturerCharacteristicReferenceAdapter(JdbcTemplate jdbc,
            SimulationManufacturerEvidenceContract party){
        this.jdbc=Objects.requireNonNull(jdbc);this.ds=Objects.requireNonNull(jdbc.getDataSource());
        this.party=Objects.requireNonNull(party);
    }
    @Override @Transactional(propagation=Propagation.MANDATORY)
    public Admission acquire(Requested request,Original original){
        Ticket ticket=null;
        try {
            Objects.requireNonNull(request);Objects.requireNonNull(original);
            if(!request.companyPartyId().equals(original.companyPartyId())
                    ||!caller())throw denied();
            var holder=holder();
            requirePhysical(original);
            if(!Boolean.TRUE.equals(jdbc.queryForObject("""
                    SELECT EXISTS(SELECT 1 FROM pg_catalog.pg_locks
                    WHERE pid=pg_catalog.pg_backend_pid() AND locktype='relation' AND granted
                      AND relation='public.hidra_simulation_manufacturer_characteristic_revision'::regclass
                      AND mode='ShareRowExclusiveLock')
                    """,Boolean.class)))throw denied();
            SourceImage image=sourceImage(request);
            var requirement=new SimulationManufacturerEvidenceContract.Requirement(
                    request.companyPartyId(),image.manufacturerPartyId,image.manufacturerModelReference);
            var partyAdmission=party.acquire(requirement);
            var manufacturer=party.read(partyAdmission);
            if(!request.companyPartyId().equals(manufacturer.companyPartyId())
                    ||!image.manufacturerPartyId.equals(manufacturer.manufacturerPartyId())
                    ||!image.manufacturerModelReference.equals(manufacturer.manufacturerModelReference())
                    ||manufacturer.backendPid()!=original.backendPid()
                    ||manufacturer.transactionId()!=original.transactionId()
                    ||!manufacturer.databaseRole().equals(original.databaseRole()))throw denied();
            ticket=new Ticket(this,request,original,holder,image,partyAdmission,manufacturer);
            TransactionSynchronizationManager.registerSynchronization(ticket);
            return ticket;
        }catch(RuntimeException failure){poison(ticket);throw denied();}
    }
    @Override public Verified verify(Admission admission){
        try {
            Ticket t=ticket(admission);
            if(!t.used.compareAndSet(false,true))throw denied();
            requireCurrent(t);
            return new Verified(t.request.companyPartyId(),t.request.sourceId(),
                    t.request.revisionId(),t.request.sourceSha256(),t.request.kind(),
                    t.original.backendPid(),t.original.transactionId(),t.original.databaseRole());
        }catch(RuntimeException failure){poison(admission);throw denied();}
    }
    @Override public void requireCurrent(Admission admission) {
        try {
            Ticket t=ticket(admission);
            party.requireCurrent(t.partyAdmission);
            if(!t.image.equals(sourceImage(t.request)))throw denied();
            requirePhysical(t.original);
        }catch(RuntimeException failure){poison(admission);throw denied();}
    }
    @Override public List<ProtectedRelationFence> finalFences(Admission admission) {
        requireCurrent(admission);
        var fences=new java.util.ArrayList<ProtectedRelationFence>(party.finalFences(((Ticket)admission).partyAdmission));
        fences.add(new ProtectedRelationFence(TABLE,ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE));
        return List.copyOf(fences);
    }
    private SourceImage sourceImage(Requested r) {
        List<SourceImage> images=jdbc.query(SOURCE_SQL,(rs,i)->{
            byte[] bytes=rs.getBytes("canonical_payload");
            String sha=rs.getString("payload_sha256");
            if(bytes==null||!codec.sha256(bytes).equals(sha)||!sha.equals(r.sourceSha256())
                    ||!ManufacturerCharacteristicRevisionCodec.FORMAT.equals(rs.getString("payload_format")))throw denied();
            ManufacturerCharacteristicRevision source=codec.decode(bytes);
            if(!r.sourceId().equals(source.sourceId())||!r.revisionId().equals(source.revisionId())
                    ||!r.kind().equals(source.family().kind().name())
                    ||r.effectiveFrom().isBefore(source.effectiveFrom())
                    ||source.effectiveUntil()!=null && (r.effectiveUntil()==null
                        ||r.effectiveUntil().isAfter(source.effectiveUntil()))
                    ||!source.effectiveFrom().equals(rs.getTimestamp("effective_from").toInstant())
                    ||!Objects.equals(source.effectiveUntil(),
                        rs.getTimestamp("effective_until")==null?null:rs.getTimestamp("effective_until").toInstant())
                    ||!source.manufacturerPartyId().equals(rs.getString("manufacturer_party_id"))
                    ||!source.manufacturerModelReference().equals(rs.getString("manufacturer_model_reference"))
                    ||!source.origin().name().equals(rs.getString("source_origin")))throw denied();
            return new SourceImage(sha,source.manufacturerPartyId(),source.manufacturerModelReference(),
                    source.family().kind().name(),source.effectiveFrom(),source.effectiveUntil());
        },r.companyPartyId(),r.sourceId(),r.revisionId());
        if(images.size()!=1)throw denied();
        return images.getFirst();
    }
    private boolean caller(){return StackWalker.getInstance().walk(stream->stream.anyMatch(f->
            (f.getClassName().equals("dz.sh.hidra.modules.topology.application.service.ProtectedInstalledCharacteristicLinkAdmissionCoordinator")
            ||f.getClassName().equals("dz.sh.hidra.modules.topology.infrastructure.integration.EngineeringBaselineSourceAdapter"))));}
    private void requirePhysical(Original original){
        if(!Boolean.TRUE.equals(jdbc.queryForObject(
                "SELECT pg_catalog.pg_backend_pid()=? AND pg_catalog.txid_current()=? AND current_user=?"
                +" AND pg_catalog.clock_timestamp()>=? AND pg_catalog.clock_timestamp()<?",
                Boolean.class,original.backendPid(),original.transactionId(),original.databaseRole(),
                Timestamp.from(original.sampledAt()),Timestamp.from(original.validUntil()))))throw denied();
    }
    private ConnectionHolder holder(){
        if(!TransactionSynchronizationManager.isActualTransactionActive()
                ||!TransactionSynchronizationManager.isSynchronizationActive()
                ||TransactionSynchronizationManager.isCurrentTransactionReadOnly()
                ||!(TransactionSynchronizationManager.getResource(ds) instanceof ConnectionHolder h)
                ||h.isRollbackOnly())throw denied();
        return h;
    }
    private Ticket ticket(Admission a){
        if(!(a instanceof Ticket t)||t.owner!=this||!t.active||t.thread!=Thread.currentThread()
                ||t.holder!=holder()||t.connection!=t.holder.getConnection())throw denied();
        return t;
    }
    private void poison(Object candidate) {
        if(candidate instanceof Ticket t){t.active=false;t.holder.setRollbackOnly();}
        Object bound=TransactionSynchronizationManager.getResource(ds);
        if(bound instanceof ConnectionHolder h)h.setRollbackOnly();
    }
    private static IllegalStateException denied(){return new IllegalStateException("Protected manufacturer source unavailable");}
    private record SourceImage(String sha,String manufacturerPartyId,String manufacturerModelReference,String kind,
                               Instant effectiveFrom,Instant effectiveUntil){}
    private static final class Ticket implements Admission,TransactionSynchronization {
        final TopologyManufacturerCharacteristicReferenceAdapter owner;
        final Requested request;final Original original;
        final ConnectionHolder holder;final Connection connection;
        final SourceImage image;
        final SimulationManufacturerEvidenceContract.Admission partyAdmission;
        final SimulationManufacturerEvidenceContract.Contribution manufacturer;
        final Thread thread=Thread.currentThread();
        final AtomicBoolean used=new AtomicBoolean();
        boolean active=true;
        Ticket(TopologyManufacturerCharacteristicReferenceAdapter owner,Requested request,Original original,
                ConnectionHolder holder,SourceImage image,
                SimulationManufacturerEvidenceContract.Admission partyAdmission,
                SimulationManufacturerEvidenceContract.Contribution manufacturer){
            this.owner=owner;this.request=request;this.original=original;this.holder=holder;
            this.connection=holder.getConnection();this.image=image;this.partyAdmission=partyAdmission;
            this.manufacturer=manufacturer;
        }
        @Override public void suspend(){active=false;}
        @Override public void beforeCommit(boolean readOnly){if(!active||!used.get()||holder.isRollbackOnly())throw denied();}
        @Override public void afterCompletion(int code){active=false;}
    }
}
