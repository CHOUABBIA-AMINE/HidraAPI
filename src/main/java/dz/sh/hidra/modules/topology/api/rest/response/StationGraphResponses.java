package dz.sh.hidra.modules.topology.api.rest.response;
import java.time.Instant;
import java.util.List;
public final class StationGraphResponses {
    private StationGraphResponses(){}
    @io.swagger.v3.oas.annotations.media.Schema(name="ProtectedStationGraphHead")
    public record Head(String facilityId,String revisionId,long epoch,String state){}
    @io.swagger.v3.oas.annotations.media.Schema(name="ProtectedStationGraphEvent")
    public record Event(String kind,Long epoch,String revisionId,Instant recordedAt){}
    @io.swagger.v3.oas.annotations.media.Schema(name="ProtectedStationGraphView")
    public record View(Head head,StationInstalledConfigurationResponses.GraphRevision revision,String payloadFormat,String canonicalSha256,List<Event> history,boolean observedCommit) {
        public View {history=List.copyOf(history);}
    }
}
