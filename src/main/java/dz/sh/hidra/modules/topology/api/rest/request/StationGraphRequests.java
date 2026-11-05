package dz.sh.hidra.modules.topology.api.rest.request;
import com.fasterxml.jackson.annotation.JsonAnySetter;
public final class StationGraphRequests {
    private StationGraphRequests(){}
    @io.swagger.v3.oas.annotations.media.Schema(name="ProtectedStationGraphAuthor")
    public record Author(String companyPartyId,StationInstalledConfigurationRequests.GraphRevision revision){
        @JsonAnySetter public void unknown(String key,Object value){throw new IllegalArgumentException("Unsupported station graph field");}
    }
    @io.swagger.v3.oas.annotations.media.Schema(name="ProtectedStationGraphPublish")
    public record Publish(String companyPartyId,String revisionId,Long expectedEpoch,String expectedRevisionId){
        @JsonAnySetter public void unknown(String key,Object value){throw new IllegalArgumentException("Unsupported station graph field");}
    }
}
