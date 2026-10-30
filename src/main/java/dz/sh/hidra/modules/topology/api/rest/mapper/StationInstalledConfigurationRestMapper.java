/**
 * HidraAPI — strict transport/domain mapping for protected station configuration.
 * No caller-supplied actor, role, transaction, privilege or approval fields.
 */
package dz.sh.hidra.modules.topology.api.rest.mapper;

import dz.sh.hidra.modules.topology.api.rest.request.StationInstalledConfigurationRequests;
import dz.sh.hidra.modules.topology.api.rest.request.StationInstalledConfigurationRequests.*;
import dz.sh.hidra.modules.topology.api.rest.response.StationInstalledConfigurationResponses;
import dz.sh.hidra.modules.topology.api.rest.response.StationInstalledConfigurationResponses.*;
import dz.sh.hidra.modules.topology.application.port.in.StationInstalledConfigurationUseCase;
import dz.sh.hidra.modules.topology.application.port.in.StationInstalledConfigurationUseCase.*;
import dz.sh.hidra.modules.topology.domain.model.StationInstalledConfiguration;
import dz.sh.hidra.modules.topology.domain.model.StationInternalGraph;
import dz.sh.hidra.modules.topology.domain.model.StationInternalGraphRevision;
import java.time.Instant;
import java.time.DateTimeException;
import java.util.*;

public final class StationInstalledConfigurationRestMapper {
    private StationInstalledConfigurationRestMapper(){}
    private static String exact(String s){
        if(s==null || s.isBlank() || !s.equals(s.strip()) ||
           s.codePointCount(0,s.length())>80 ||
           s.codePoints().anyMatch(Character::isISOControl))
           throw new IllegalArgumentException("Exact bounded identity required");
        return s;
    }
    public static Instant instant(String text){
        if(text==null)return null;
        try {
            Instant instant=Instant.parse(text);
            if(!instant.toString().equals(text) || instant.getNano()%1000!=0)
                throw new IllegalArgumentException("Canonical UTC instant with microsecond precision required");
            return instant;
        }catch(DateTimeException ex){throw new IllegalArgumentException("Invalid instant",ex);}
    }
    private static Instant requiredInstant(String text){
        var value=instant(text);
        if(value==null)throw new IllegalArgumentException("Required canonical UTC instant");
        return value;
    }
    private static <T> List<T> list(List<T> values,int max){
        if(values==null || values.size()>max || values.stream().anyMatch(Objects::isNull))
            throw new IllegalArgumentException("Bounded non-null collection required");
        return List.copyOf(values);
    }
    private static StationInternalGraphRevision graph(StationInstalledConfigurationRequests.GraphRevision value){
        if(value==null)throw new IllegalArgumentException("Exact graph revision required");
        var graph=value.graph();
        if(graph==null)throw new IllegalArgumentException("Pinned graph required");
        return new StationInternalGraphRevision(exact(value.facilityId()),exact(value.revisionId()),
            new StationInternalGraph(exact(graph.facilityId()),exact(graph.revisionId()),
                StationInternalGraph.Status.valueOf(exact(graph.status())),
                list(graph.headers(),128).stream().map(h->new StationInternalGraph.Header(
                    exact(h.id()),StationInternalGraph.HeaderKind.valueOf(exact(h.kind())),exact(h.portId()))).toList(),
                list(graph.units(),128).stream().map(u->new StationInternalGraph.Unit(
                    exact(u.equipmentId()),exact(u.facilityId()),exact(u.inletPortId()),exact(u.outletPortId()))).toList(),
                list(graph.edges(),1024).stream().map(e->new StationInternalGraph.Edge(
                    exact(e.id()),exact(e.fromPortId()),exact(e.toPortId()))).toList()),
            StationInternalGraphRevision.SourceClassification.valueOf(exact(value.sourceClassification())),
            requiredInstant(value.recordedAt()),
            requiredInstant(value.effectiveFrom()),instant(value.effectiveUntil()),
            value.evidenceReference(),value.expectedPredecessorRevisionId(),
            StationInternalGraphRevision.Qualification.valueOf(exact(value.qualification())));
    }
    public static Command save(String facilityId,Save body){
        if(body==null)throw new IllegalArgumentException("Save body required");
        String facility=exact(facilityId);
        var revision=graph(body.pinnedGraph());
        if(!facility.equals(revision.facilityId()))throw new IllegalArgumentException("Station graph scope mismatch");
        if(body.expectedEpoch()==null || body.expectedEpoch()<0 ||
           body.expectedEpoch()==Long.MAX_VALUE || body.graphHeadEpoch()==null ||
           body.graphHeadEpoch()<1)throw new IllegalArgumentException("Invalid expected epoch");
        // The protected B3 owner re-verifies canonical graph bytes against the
        // native selected graph source under original PostgreSQL locks.
        // A REST mapper must never depend on, duplicate or impersonate its storage codec.
        if(!"HIDRA_TOPOLOGY_STATION_GRAPH_REVISION_V1".equals(body.graphPayloadFormat())
          || body.graphSha256()==null || !body.graphSha256().matches("[0-9a-f]{64}"))
            throw new IllegalArgumentException("Invalid pinned graph declaration");
        String digest=body.graphSha256();
        var positions=list(body.positions(),StationInstalledConfiguration.MAX_POSITIONS).stream().map(p->
            new StationInstalledConfiguration.Position(exact(p.positionId()),
                StationInstalledConfiguration.Disposition.valueOf(exact(p.disposition())),
                p.installedUnitId(),p.installationId(),
                p.installationRevision()==null?0:p.installationRevision(),
                instant(p.installationFrom()),instant(p.installationUntil()),
                p.installationEvidenceReference())).toList();
        var selected=body.expectedEpoch()+1;
        // Placeholder provenance is never used as a permission or recorded actor:
        // the protected JDBC owner replaces all native identity fields and recordedAt.
        var declaration=new StationInstalledConfiguration(exact(body.companyPartyId()),facility,
            exact(body.revisionId()),exact(body.requestId()),body.expectedEpoch(),selected,
            body.graphHeadEpoch(),revision,body.graphPayloadFormat(),digest,
            requiredInstant(body.effectiveFrom()),instant(body.effectiveUntil()),
            requiredInstant(body.effectiveFrom()),body.sourceReference(),
            "server-attributed","server-attributed","server-attributed",
            positions,list(body.activeEdgeIds(),1024));
        return new Command(Operation.SAVE,declaration.companyPartyId(),facility,
            declaration.revisionId(),declaration,0);
    }
    public static Command current(String facilityId,String company){
        return new Command(Operation.READ_CURRENT,exact(company),exact(facilityId),null,null,0);
    }
    public static Command revision(String facilityId,String company,String revisionId){
        return new Command(Operation.READ_REVISION,exact(company),exact(facilityId),exact(revisionId),null,0);
    }
    public static Command history(String facilityId,String company,int limit,String cursor){
        Long before=null;
        if(cursor!=null) {
            if(cursor.length()>19 || cursor.isBlank() || cursor.startsWith("+") || cursor.startsWith("0"))
                throw new IllegalArgumentException("Invalid exclusive history cursor");
            try {
                before=Long.valueOf(cursor);
                if(before<1 || !before.toString().equals(cursor))
                    throw new IllegalArgumentException("Invalid exclusive history cursor");
            }catch(NumberFormatException invalid){
                throw new IllegalArgumentException("Invalid exclusive history cursor",invalid);
            }
        }
        return new Command(Operation.LIST_HISTORY,exact(company),exact(facilityId),null,null,limit,before);
    }
    public static Snapshot snapshot(StationInstalledConfiguration c){
        if(c==null)return null;
        var g=c.pinnedGraph();
        var graph=g.graph();
        var report=c.activeStructure();
        return new Snapshot(c.companyPartyId(),c.facilityId(),c.revisionId(),c.requestId(),
            c.expectedEpoch(),c.selectedEpoch(),c.graphHeadEpoch(),
            new StationInstalledConfigurationResponses.GraphRevision(g.facilityId(),g.revisionId(),
                new StationInstalledConfigurationResponses.Graph(graph.facilityId(),graph.revisionId(),
                    graph.status().name(),graph.headers().stream().map(h->
                        new StationInstalledConfigurationResponses.Header(h.id(),h.kind().name(),h.portId())).toList(),
                    graph.units().stream().map(u->new StationInstalledConfigurationResponses.Unit(
                        u.equipmentId(),u.facilityId(),u.inletPortId(),u.outletPortId())).toList(),
                    graph.edges().stream().map(e->new StationInstalledConfigurationResponses.Edge(
                        e.id(),e.fromPortId(),e.toPortId())).toList()),
                g.sourceClassification().name(),g.recordedAt(),g.effectiveFrom(),g.effectiveUntil(),
                g.evidenceReference(),g.expectedPredecessorRevisionId(),g.qualification().name()),
            c.graphPayloadFormat(),c.graphSha256(),c.effectiveFrom(),c.effectiveUntil(),
            c.recordedAt(),c.sourceReference(),c.actorBindingId(),c.actorSessionId(),c.actorUserId(),
            c.positions().stream().map(p->new StationInstalledConfigurationResponses.Position(
                p.positionId(),p.disposition().name(),p.installedUnitId(),p.installationId(),
                p.installationRevision(),p.installationFrom(),p.installationUntil(),
                p.installationEvidenceReference())).toList(),
            c.activeEdgeIds(),report.diagnostics(),report.seriesPairs(),report.parallelPairs());
    }
    public static Configuration from(Result result){
        return from(result,0);
    }
    /** Application returns one additional fully-authorized history row for exclusive pagination. */
    public static Configuration from(Result result,int requestedHistoryLimit){
        Objects.requireNonNull(result);
        if(requestedHistoryLimit<0 || requestedHistoryLimit>100)
            throw new IllegalArgumentException("Invalid history window");
        var history=result.history();
        int count=requestedHistoryLimit==0?history.size():Math.min(requestedHistoryLimit,history.size());
        String cursor=requestedHistoryLimit>0 && history.size()>requestedHistoryLimit && count>0
            ?Long.toString(history.get(count-1).selectedEpoch()):null;
        return new Configuration(snapshot(result.declaration()),
            history.subList(0,count).stream().map(StationInstalledConfigurationRestMapper::snapshot).toList(),
            cursor,result.replayed(),false,false);
    }
}
