package dz.sh.hidra.modules.topology.api.rest.response;
import dz.sh.hidra.modules.topology.application.port.in.TopologyFoundationUseCase;
import java.time.Instant;
import java.util.List;
public final class TopologyFoundationResponses {
    private TopologyFoundationResponses(){}
    @io.swagger.v3.oas.annotations.media.Schema(name="TopologyFoundationItem")
    public record Item(String id,String code,String name,String kind,String status,Instant updatedAt){}
    @io.swagger.v3.oas.annotations.media.Schema(name="TopologyFoundationInstallation")
    public record Installation(String unitId,String installationId,String positionId,String facilityId,long revision,Instant effectiveFrom,Instant effectiveUntil){}
    @io.swagger.v3.oas.annotations.media.Schema(name="TopologyFoundationPage")
    public record Page(List<Item> items,List<Installation> installations,String nextCursor,boolean replayed) {
        public Page {items=List.copyOf(items);installations=List.copyOf(installations);}
        public static Page from(TopologyFoundationUseCase.Result r){return new Page(r.items().stream().map(i->new Item(i.id(),i.code(),i.name(),i.kind(),i.status(),i.updatedAt())).toList(),r.installations().stream().map(i->new Installation(i.unitId(),i.installationId(),i.positionId(),i.facilityId(),i.revision(),i.effectiveFrom(),i.effectiveUntil())).toList(),r.nextOffset()==null?null:dz.sh.hidra.modules.topology.api.rest.mapper.TopologyDiscoveryCursor.encode(r.nextOffset()),r.replayed());}
    }
}
