/**
 * HidraAPI — authorized, immutable installed-station configuration HTTP responses.
 */
package dz.sh.hidra.modules.topology.api.rest.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.List;

public final class StationInstalledConfigurationResponses {
    private StationInstalledConfigurationResponses(){}
    @Schema(name="StationConfigurationPositionResponse")
    public record Position(String positionId,String disposition,String installedUnitId,
        String installationId,long installationRevision,Instant installationFrom,
        Instant installationUntil,String installationEvidenceReference){}
    @Schema(name="StationConfigurationHeaderResponse")
    public record Header(String id,String kind,String portId){}
    @Schema(name="StationConfigurationUnitResponse")
    public record Unit(String equipmentId,String facilityId,String inletPortId,String outletPortId){}
    @Schema(name="StationConfigurationEdgeResponse")
    public record Edge(String id,String fromPortId,String toPortId){}
    @Schema(name="StationConfigurationGraphResponse")
    public record Graph(String facilityId,String revisionId,String status,
        List<Header> headers,List<Unit> units,List<Edge> edges){
        public Graph {headers=List.copyOf(headers);units=List.copyOf(units);edges=List.copyOf(edges);}
    }
    @Schema(name="StationConfigurationGraphRevisionResponse")
    public record GraphRevision(String facilityId,String revisionId,Graph graph,
        String sourceClassification,Instant recordedAt,Instant effectiveFrom,
        Instant effectiveUntil,String evidenceReference,String expectedPredecessorRevisionId,
        String qualification){}
    @Schema(name="StationConfigurationSnapshotResponse")
    public record Snapshot(String companyPartyId,String facilityId,String revisionId,String requestId,
        long expectedEpoch,long selectedEpoch,long graphHeadEpoch,GraphRevision pinnedGraph,
        String graphPayloadFormat,String graphSha256,Instant effectiveFrom,Instant effectiveUntil,
        Instant recordedAt,String sourceReference,String actorBindingId,String actorSessionId,
        String actorUserId,List<Position> positions,List<String> activeEdgeIds,
        List<String> structuralDiagnostics,List<String> seriesPairs,List<String> parallelPairs) {
        public Snapshot {
            positions=List.copyOf(positions);activeEdgeIds=List.copyOf(activeEdgeIds);
            structuralDiagnostics=List.copyOf(structuralDiagnostics);
            seriesPairs=List.copyOf(seriesPairs);parallelPairs=List.copyOf(parallelPairs);
        }
    }
    @Schema(name="StationConfigurationOperationResponse")
    public record Configuration(Snapshot snapshot,List<Snapshot> history,
        String nextCursor,boolean replayed,boolean engineeringQualified,boolean operatingStateValidated) {
        public Configuration {history=List.copyOf(history);
            if(engineeringQualified || operatingStateValidated)
                throw new IllegalArgumentException("Structural configuration is not engineering acceptance");
        }
    }
}
