/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringBaselineReadResponses
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-12
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.response
 *
 * @Description : Exposes exact historical baseline lineage and bounded comparison results.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.response;

import dz.sh.hidra.modules.topology.api.rest.response.EngineeringBaselinePublicationResponses.Published;
import java.util.List;

public final class EngineeringBaselineReadResponses {
    private EngineeringBaselineReadResponses() {}

    public record History(List<Published> items, String nextCursor, boolean hasMore) {
        public History { items = List.copyOf(items); }
    }
    public record ChangedPin(String kind, String ownerId, String sourceId,
                             String leftRevisionId, String leftSha256,
                             String rightRevisionId, String rightSha256) {}
    public record Comparison(Published left, Published right, boolean sameCanonicalSha256,
                             List<ChangedPin> changedPins) {
        public Comparison { changedPins = List.copyOf(changedPins); }
    }
}
