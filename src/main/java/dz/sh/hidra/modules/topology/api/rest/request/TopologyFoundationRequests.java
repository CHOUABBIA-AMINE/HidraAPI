package dz.sh.hidra.modules.topology.api.rest.request;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import java.time.Instant;
import java.util.UUID;
public final class TopologyFoundationRequests {
    private TopologyFoundationRequests(){}
    @io.swagger.v3.oas.annotations.media.Schema(name="TopologyFoundationCreateSystem")
    public record CreateSystem(String companyPartyId,UUID requestId,String id,String code,String name,String systemTypeId,String reason) {
        @JsonAnySetter public void unknown(String key,Object value){throw new IllegalArgumentException("Unsupported setup field");}
    }
    @io.swagger.v3.oas.annotations.media.Schema(name="TopologyFoundationRegisterFacility")
    public record RegisterFacility(String companyPartyId,UUID requestId,String id,String code,String name,String facilityTypeId,String facilityKind,String reason) {
        @JsonAnySetter public void unknown(String key,Object value){throw new IllegalArgumentException("Unsupported setup field");}
    }
    @io.swagger.v3.oas.annotations.media.Schema(name="TopologyFoundationActivateFacility")
    public record ActivateFacility(String companyPartyId,UUID requestId,Instant expectedUpdatedAt,String reason) {
        @JsonAnySetter public void unknown(String key,Object value){throw new IllegalArgumentException("Unsupported setup field");}
    }
    @io.swagger.v3.oas.annotations.media.Schema(name="TopologyFoundationRegisterPosition")
    public record RegisterPosition(String companyPartyId,UUID requestId,String id,String code,String name,String equipmentKindId,String reason) {
        @JsonAnySetter public void unknown(String key,Object value){throw new IllegalArgumentException("Unsupported setup field");}
    }
}
