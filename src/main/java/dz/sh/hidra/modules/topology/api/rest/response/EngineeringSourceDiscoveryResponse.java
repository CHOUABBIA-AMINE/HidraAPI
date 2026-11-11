/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringSourceDiscoveryResponse
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-11
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.response
 *
 * @Description : Maps authoritative engineering source selection into a compact frontend DTO.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.response;

import dz.sh.hidra.modules.topology.application.port.in.EngineeringSourceDiscoveryUseCase;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public final class EngineeringSourceDiscoveryResponse {
    private EngineeringSourceDiscoveryResponse() {}
    @Schema(name="EngineeringSourceSelection")
    public record Item(String kind,UUID ownerId,UUID sourceId,UUID revisionId,long authoredVersion,
                       String metadataSha256,String state,Instant effectiveAt,
                       boolean currentlyEligible,boolean engineeringQualified) {
        public static Item from(EngineeringSourceDiscoveryUseCase.Source source) {
            return new Item(source.kind().name(),source.ownerId(),source.sourceId(),source.revisionId(),
                    source.authoredVersion(),source.metadataSha256(),source.state(),source.effectiveAt(),
                    source.currentlyEligible(),source.engineeringQualified());
        }
    }
    @Schema(name="EngineeringSourceDiscoveryPage")
    public record Page(List<Item> items,String nextCursor) {
        public Page {items=List.copyOf(items);}
    }
}
