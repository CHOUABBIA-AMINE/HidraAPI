/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringBaselineSourceAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-03
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.integration
 *
 * @Description : Captures exact Topology and public owner source evidence without nested execution.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.integration;


import dz.sh.hidra.modules.topology.application.port.out.EngineeringBaselineSourcePort;
import dz.sh.hidra.modules.topology.domain.model.EngineeringBaselineManifest;
import dz.sh.hidra.modules.topology.domain.model.EngineeringBaselineManifest.Pin;
import dz.sh.hidra.modules.topology.domain.model.EngineeringBaselineManifest.Finding;
import dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft;
import dz.sh.hidra.modules.topology.domain.model.EngineeringRevisionPublication;
import dz.sh.hidra.modules.topology.domain.model.InstalledCharacteristicLink;
import dz.sh.hidra.modules.topology.domain.model.StationInstalledConfiguration;
import dz.sh.hidra.modules.topology.domain.service.EngineeringBaselineCompletenessPolicy.*;
import dz.sh.hidra.modules.topology.domain.service.InstalledCharacteristicLinkApprovalTargetDigester;
import dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.EngineeringRevisionPublicationCodec;
import dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.EngineeringGraphDraftCodec;
import dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.StationInstalledConfigurationCodec;
import dz.sh.hidra.modules.custody.application.contract.topology.TopologyBaselineProductSourceContract;
import dz.sh.hidra.modules.simulation.application.contract.topology.TopologyManufacturerCharacteristicReferenceContract;
import dz.sh.hidra.platform.persistence.ProtectedRelationFence;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.function.Consumer;
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.ConnectionHolder;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Component
public final class EngineeringBaselineSourceAdapter implements EngineeringBaselineSourcePort {
    private static final List<String> TABLES=List.of(
        "public.hidra_topology_engineering_context","public.hidra_topology_engineering_publication_snapshot",
        "public.hidra_topology_engineering_publication_head","public.hidra_topology_engineering_publication_event",
        "public.hidra_topology_facility","public.hidra_topology_station_configuration_revision",
        "public.hidra_topology_station_configuration_head","public.hidra_topology_installed_unit",
        "public.hidra_topology_installed_unit_installation","public.hidra_topology_installed_unit_installation_event",
        "public.hidra_topology_installed_characteristic_revision","public.hidra_topology_installed_characteristic_approval",
        "public.hidra_simulation_manufacturer_characteristic_revision");
    private final JdbcTemplate jdbc;
    private final dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor executor;
    private final TopologyBaselineProductSourceContract products;
    private final TopologyManufacturerCharacteristicReferenceContract manufacturer;
    public EngineeringBaselineSourceAdapter(dz.sh.hidra.platform.persistence.ProtectedTransactionExecutor executor,JdbcTemplate jdbc,TopologyBaselineProductSourceContract products,
            TopologyManufacturerCharacteristicReferenceContract manufacturer){
        this.executor=Objects.requireNonNull(executor);this.jdbc=Objects.requireNonNull(jdbc);this.products=Objects.requireNonNull(products);
        this.manufacturer=Objects.requireNonNull(manufacturer);
    }
    @Override public Admission capture(EngineeringBaselineManifest manifest,Original origin,Consumer<Read> authorizer){
        var t=new Ticket(this,manifest,origin,holder(),authorizer);
        executor.requireEngineeringBaselineSourceLock();
        jdbc.queryForObject("SELECT public.hidra_topology_baseline_lock_sources()",Boolean.class);
        t.facts=observe(t,true);return t;
    }
    @Override public List<SourceFacts> facts(Admission a){return ticket(a).facts;}
    @Override public void requireCurrent(Admission a){
        var t=ticket(a);
        if(!t.facts.equals(observe(t,false)))throw denied();
        for(var p:t.products)products.requireCurrent(p);
        for(var m:t.manufacturers)manufacturer.requireCurrent(m);
    }
    @Override public List<ProtectedRelationFence> finalFences(Admission a){
        var t=ticket(a);requireCurrent(t);
        var result=new ArrayList<ProtectedRelationFence>();
        TABLES.forEach(x->result.add(new ProtectedRelationFence(x,ProtectedRelationFence.Mode.SHARE_ROW_EXCLUSIVE)));
        t.products.forEach(x->result.addAll(products.finalFences(x)));
        t.manufacturers.forEach(x->result.addAll(manufacturer.finalFences(x)));
        return List.copyOf(result);
    }
    private List<SourceFacts> observe(Ticket t,boolean first){
        physical(t);t.checkPermissions=first;var result=new ArrayList<SourceFacts>();
        var installations=new HashMap<String,StationInstalledConfiguration.Position>();
        // Station ownership/membership is captured before any unit link can be consumed.
        for(Pin pin:t.manifest.pins())if(pin.kind()==EngineeringBaselineManifest.Kind.STATION_CONFIGURATION)
            result.add(station(t,pin,installations));
        int productIndex=0;
        for(Pin pin:t.manifest.pins()){
            switch(pin.kind()){
                case NETWORK_PUBLICATION->result.add(network(t,pin));
                case STATION_CONFIGURATION->{}
                case CHARACTERISTIC_LINK->result.add(link(t,pin,installations,first));
                case PRODUCT_PUBLICATION->{
                    authorize(t,new Read(Kind.PRODUCT_PUBLICATION,pin.sourceId()));
                    TopologyBaselineProductSourceContract.Admission admitted;
                    if(first){
                        admitted=products.acquire(new TopologyBaselineProductSourceContract.Request(
                            t.manifest.companyPartyId(),UUID.fromString(pin.ownerId()),pin.sourceId(),
                            UUID.fromString(pin.revisionId()),pin.sha256()),
                            new TopologyBaselineProductSourceContract.Original(t.origin.backendPid(),t.origin.transactionId(),
                                t.origin.databaseRole(),t.origin.sampledAt(),t.origin.validUntil()));
                        t.products.add(admitted);
                    }else admitted=t.products.get(productIndex);
                    productIndex++;
                    var facts=products.read(admitted);var findings=new ArrayList<Finding>();
                    if(!facts.currentlyEligible())findings.add(Finding.SOURCE_NOT_CURRENTLY_ELIGIBLE);
                    if(!facts.methodSupplied())findings.add(Finding.METHOD_NOT_SUPPLIED);
                    if(!facts.inputsComplete())findings.add(Finding.SOURCE_DATA_INCOMPLETE);
                    if(!facts.approved())findings.add(Finding.APPROVAL_NOT_ESTABLISHED);
                    result.add(new SourceFacts(pin,facts.effectiveFrom(),facts.effectiveUntil(),Set.of(),findings));
                }
            }
        }
        physical(t);
        result.sort(Comparator.comparing(f->f.pin().kind().name()+":"+f.pin().ownerId()+":"+f.pin().sourceId()));
        return List.copyOf(result);
    }
    private SourceFacts network(Ticket t,Pin pin){
        if(!pin.ownerId().equals(pin.sourceId()))throw denied();
        authorize(t,new Read(Kind.NETWORK_PUBLICATION,t.manifest.pipelineSystemId()));
        var context=UUID.fromString(pin.ownerId());var revision=UUID.fromString(pin.revisionId());
        var scope=new EngineeringRevisionPublication.Scope(context,t.manifest.companyPartyId(),t.manifest.pipelineSystemId());
        var rows=jdbc.query("SELECT canonical_payload,sha256,payload_format FROM public.hidra_topology_engineering_publication_snapshot"
            +" WHERE context_id=? AND revision_id=? AND EXISTS(SELECT 1 FROM public.hidra_topology_engineering_context"
            +" WHERE id=? AND company_party_id=? AND pipeline_system_id=?)",
            (r,n)->{
                byte[] b=r.getBytes(1);
                if(b==null||!EngineeringRevisionPublication.hash(b).equals(pin.sha256())
                    ||!pin.sha256().equals(r.getString(2))
                    ||!EngineeringRevisionPublicationCodec.FORMAT.equals(r.getString(3)))throw denied();
                return new EngineeringRevisionPublicationCodec().decode(b,scope);
            },context,revision,context,scope.companyPartyId(),scope.pipelineSystemId());
        if(rows.size()!=1)throw denied();var snapshot=rows.getFirst();
        var required=new HashSet<Requirement>();var findings=new HashSet<Finding>();
        if(snapshot.graph()==null)findings.add(Finding.GRAPH_NOT_AUTHORED);
        else{
            var graph=new EngineeringGraphDraftCodec().decode(Base64.getDecoder().decode(snapshot.graph().base64()));
            for(var node:graph.nodes())if(node.kind()==EngineeringGraphDraft.NodeKind.STATION)
                required.add(new Requirement(EngineeringBaselineManifest.Kind.STATION_CONFIGURATION,node.facilityId()));
            if(graph.diagnostics().contains("GRAPH_DISCONNECTED"))findings.add(Finding.GRAPH_INCOMPLETE);
            if(graph.branches().stream().noneMatch(b->b.kind()==EngineeringGraphDraft.BranchKind.PIPE))findings.add(Finding.SECTIONS_NOT_SUPPLIED);
            for(var survey:snapshot.surveys())if(survey.source()!=null){
                var profile=new dz.sh.hidra.modules.topology.infrastructure.persistence.adapter.SurveyRouteProfileCodec()
                    .decode(Base64.getDecoder().decode(survey.source().base64()));
                if(profile.sections().isEmpty())findings.add(Finding.SECTIONS_NOT_SUPPLIED);
            }
            if(snapshot.surveys().size()!=graph.branches().stream().filter(b->b.kind()==EngineeringGraphDraft.BranchKind.PIPE).count()
                ||snapshot.surveys().stream().anyMatch(x->x.source()==null||!x.diagnostics().isEmpty()))
                findings.add(Finding.ROUTE_PROFILE_NOT_SUPPLIED);
        }
        var from=jdbc.queryForObject("SELECT min(effective_at) FROM public.hidra_topology_engineering_publication_event"
            +" WHERE context_id=? AND revision_id=? AND operation='PUBLISH'",Timestamp.class,context,revision);
        if(from==null)throw denied();
        var until=jdbc.queryForObject("SELECT min(effective_at) FROM public.hidra_topology_engineering_publication_event"
            +" WHERE context_id=? AND effective_at>? AND (revision_id<>? OR resulting_state<>'ACTIVE')",
            Timestamp.class,context,from,revision);
        boolean eligible=Boolean.TRUE.equals(jdbc.queryForObject("SELECT EXISTS(SELECT 1 FROM public.hidra_topology_engineering_publication_head"
            +" WHERE context_id=? AND revision_id=? AND state='ACTIVE')",Boolean.class,context,revision));
        if(!eligible)findings.add(Finding.SOURCE_NOT_CURRENTLY_ELIGIBLE);
        return new SourceFacts(pin,from.toInstant(),until==null?null:until.toInstant(),required,
            findings.stream().sorted().toList());
    }
    private SourceFacts station(Ticket t,Pin pin,Map<String,StationInstalledConfiguration.Position> installations){
        if(!pin.ownerId().equals(pin.sourceId()))throw denied();
        authorize(t,new Read(Kind.STATION_CONFIGURATION,pin.ownerId()));
        var rows=jdbc.query("SELECT canonical_payload,canonical_sha256,payload_format FROM public.hidra_topology_station_configuration_revision"
            +" WHERE company_party_id=? AND facility_id=? AND revision_id=?",
            (r,n)->{
                byte[] b=r.getBytes(1);var codec=new StationInstalledConfigurationCodec();
                if(b==null||!codec.sha256(b).equals(pin.sha256())||!pin.sha256().equals(r.getString(2))
                    ||!StationInstalledConfigurationCodec.FORMAT.equals(r.getString(3)))throw denied();
                return codec.decode(b);
            },t.manifest.companyPartyId(),pin.ownerId(),pin.revisionId());
        if(rows.size()!=1)throw denied();var value=rows.getFirst();
        if(!value.companyPartyId().equals(t.manifest.companyPartyId())||!value.facilityId().equals(pin.ownerId())
            ||!value.revisionId().equals(pin.revisionId()))throw denied();
        var units=value.positions().stream().filter(x->x.installedUnitId()!=null)
            .map(StationInstalledConfiguration.Position::installedUnitId).distinct().sorted().toList();
        authorize(t,new Read(Kind.STATION_CONFIGURATION,pin.ownerId(),units));
        if(!Boolean.TRUE.equals(jdbc.queryForObject("SELECT EXISTS(SELECT 1 FROM public.hidra_topology_facility"
            +" WHERE id=? AND owner_party_id=?)",Boolean.class,pin.ownerId(),t.manifest.companyPartyId())))throw denied();
        var requirements=new HashSet<Requirement>();
        for(var position:value.positions())if(position.installedUnitId()!=null){
            if(installations.putIfAbsent(position.installedUnitId(),position)!=null)throw denied();
            if(!Boolean.TRUE.equals(jdbc.queryForObject("SELECT EXISTS(SELECT 1 FROM public.hidra_topology_installed_unit"
                +" WHERE id=? AND company_party_id=?)",Boolean.class,position.installedUnitId(),t.manifest.companyPartyId())))throw denied();
            if(!Boolean.TRUE.equals(jdbc.queryForObject("SELECT EXISTS(SELECT 1 FROM public.hidra_topology_installed_unit_installation_event"
                +" WHERE installation_id=? AND revision=? AND unit_id=? AND position_id=? AND company_party_id=?)",
                Boolean.class,position.installationId(),position.installationRevision(),position.installedUnitId(),
                position.positionId(),t.manifest.companyPartyId())))throw denied();
            requirements.add(new Requirement(EngineeringBaselineManifest.Kind.CHARACTERISTIC_LINK,position.installedUnitId()));
        }
        var missing=new HashSet<Finding>();
        if(!Boolean.TRUE.equals(jdbc.queryForObject("SELECT EXISTS(SELECT 1 FROM public.hidra_topology_facility WHERE id=? AND status='ACTIVE')",Boolean.class,pin.ownerId())))
            missing.add(Finding.SOURCE_NOT_CURRENTLY_ELIGIBLE);
        if(!Boolean.TRUE.equals(jdbc.queryForObject("SELECT EXISTS(SELECT 1 FROM public.hidra_topology_station_configuration_head WHERE company_party_id=? AND facility_id=? AND revision_id=?)",Boolean.class,t.manifest.companyPartyId(),pin.ownerId(),pin.revisionId())))
            missing.add(Finding.SOURCE_NOT_CURRENTLY_ELIGIBLE);
        for(var position:value.positions())if(position.installedUnitId()!=null){
            var periods=jdbc.query("SELECT effective_from,effective_until FROM public.hidra_topology_installed_unit_installation WHERE installation_id=? AND unit_id=? AND position_id=? AND company_party_id=?",
                (r,n)->t.manifest.coveredBy(r.getTimestamp(1).toInstant(),instant(r.getTimestamp(2))),position.installationId(),position.installedUnitId(),position.positionId(),t.manifest.companyPartyId());
            if(periods.size()!=1||!periods.getFirst())missing.add(Finding.SOURCE_INTERVAL_NOT_COVERED);
        }
        return new SourceFacts(pin,value.effectiveFrom(),value.effectiveUntil(),requirements,missing.stream().sorted().toList());
    }
    private SourceFacts link(Ticket t,Pin pin,Map<String,StationInstalledConfiguration.Position> installations,boolean first){
        authorize(t,new Read(Kind.CHARACTERISTIC_LINK,pin.sourceId(),List.of(pin.ownerId())));
        var rows=jdbc.query("SELECT * FROM public.hidra_topology_installed_characteristic_revision"
            +" WHERE company_party_id=? AND installed_unit_id=? AND link_id=? AND link_revision=?",
            (r,n)->new InstalledCharacteristicLink(r.getString("link_id"),r.getLong("link_revision"),
                r.getString("company_party_id"),r.getString("installed_unit_id"),r.getString("source_id"),
                r.getString("source_revision_id"),r.getString("source_sha256"),InstalledCharacteristicLink.Kind.valueOf(r.getString("source_kind")),
                r.getTimestamp("effective_from").toInstant(),instant(r.getTimestamp("effective_until")),
                r.getTimestamp("recorded_at").toInstant(),r.getString("evidence_reference"),r.getString("installation_id")),
            t.manifest.companyPartyId(),pin.ownerId(),pin.sourceId(),Long.parseLong(pin.revisionId()));
        if(rows.size()!=1)throw denied();var value=rows.getFirst();
        if(!InstalledCharacteristicLinkApprovalTargetDigester.digest(value).equals(pin.sha256()))throw denied();
        var position=installations.get(value.installedUnitId());
        if(position==null||!position.installationId().equals(value.installationId()))throw denied();
        String source=manufacturerScope(value.companyPartyId(),value.sourceId(),value.sourceRevisionId());
        authorize(t,new Read(Kind.MANUFACTURER_CHARACTERISTIC,source));
        if(first){
            var admitted=manufacturer.acquire(new TopologyManufacturerCharacteristicReferenceContract.Requested(
                value.companyPartyId(),value.sourceId(),value.sourceRevisionId(),value.sourceSha256(),
                value.kind().name(),value.effectiveFrom(),value.effectiveUntil()),
                new TopologyManufacturerCharacteristicReferenceContract.Original(value.companyPartyId(),
                    t.origin.bindingId(),t.origin.userId(),t.origin.backendPid(),t.origin.transactionId(),
                    t.origin.databaseRole(),t.origin.sampledAt(),t.origin.validUntil()));
            manufacturer.verify(admitted);t.manufacturers.add(admitted);
        }
        boolean approved=Boolean.TRUE.equals(jdbc.queryForObject("SELECT EXISTS(SELECT 1 FROM public.hidra_topology_installed_characteristic_approval"
            +" WHERE company_party_id=? AND installed_unit_id=? AND link_id=? AND link_revision=? AND target_sha256=?)",
            Boolean.class,value.companyPartyId(),value.installedUnitId(),value.linkId(),value.linkRevision(),pin.sha256()));
        return new SourceFacts(pin,value.effectiveFrom(),value.effectiveUntil(),Set.of(),
            approved?List.of():List.of(Finding.APPROVAL_NOT_ESTABLISHED));
    }
    private static String manufacturerScope(String company,String source,String revision){
        String s="HIDRA_MANUFACTURER_NATIVE_SCOPE_V1|"+company.length()+":"+company+"|"+source.length()+":"+source+"|"+revision.length()+":"+revision;
        try{return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(s.getBytes(StandardCharsets.UTF_8)));}
        catch(java.security.NoSuchAlgorithmException impossible){throw new IllegalStateException(impossible);}
    }
    private static void authorize(Ticket t,Read r){if(t.checkPermissions)t.authorizer.accept(r);}
    private static Instant instant(Timestamp value){return value==null?null:value.toInstant();}
    private ConnectionHolder holder(){
        if(jdbc.getDataSource()==null||!TransactionSynchronizationManager.isActualTransactionActive()
            ||!TransactionSynchronizationManager.isSynchronizationActive()||TransactionSynchronizationManager.isCurrentTransactionReadOnly()
            ||!(TransactionSynchronizationManager.getResource(jdbc.getDataSource()) instanceof ConnectionHolder h)||h.isRollbackOnly())throw denied();
        return h;
    }
    private Ticket ticket(Admission a){
        if(!(a instanceof Ticket t)||t.owner!=this||t.thread!=Thread.currentThread()
            ||holder()!=t.holder||t.connection!=t.holder.getConnection())throw denied();
        physical(t);return t;
    }
    private void physical(Ticket t){
        if(!Boolean.TRUE.equals(jdbc.queryForObject("SELECT pg_backend_pid()=? AND txid_current()=? AND current_user=? AND session_user=?"
            +" AND clock_timestamp()>=? AND clock_timestamp()<?",Boolean.class,t.origin.backendPid(),t.origin.transactionId(),
            t.origin.databaseRole(),t.origin.databaseRole(),Timestamp.from(t.origin.sampledAt()),Timestamp.from(t.origin.validUntil()))))throw denied();
    }
    private static AccessDeniedException denied(){return new AccessDeniedException("Engineering baseline source denied");}
    private static final class Ticket implements Admission {
        final EngineeringBaselineSourceAdapter owner;final EngineeringBaselineManifest manifest;final Original origin;
        final ConnectionHolder holder;final java.sql.Connection connection;final Thread thread=Thread.currentThread();
        final Consumer<Read> authorizer;final List<TopologyBaselineProductSourceContract.Admission> products=new ArrayList<>();
        final List<TopologyManufacturerCharacteristicReferenceContract.Admission> manufacturers=new ArrayList<>();
        List<SourceFacts> facts;boolean checkPermissions=true;
        Ticket(EngineeringBaselineSourceAdapter owner,EngineeringBaselineManifest manifest,Original origin,ConnectionHolder holder,Consumer<Read> authorizer){
            this.owner=owner;this.manifest=manifest;this.origin=origin;this.holder=holder;this.connection=holder.getConnection();this.authorizer=authorizer;
        }
    }
}
