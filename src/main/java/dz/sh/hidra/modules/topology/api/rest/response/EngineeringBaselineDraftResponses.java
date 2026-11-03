/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringBaselineDraftResponses
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-03
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.response
 *
 * @Description : Exposes exact authorized draft data without qualification or freeze claims.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.response;


import java.time.Instant;
import java.util.List;
import java.util.UUID;
public final class EngineeringBaselineDraftResponses {
    private EngineeringBaselineDraftResponses(){}
    public record SourcePin(String kind,String ownerId,String sourceId,String revisionId,String sha256) {}
    public record Finding(String finding,String kind,String ownerId) {}
    public record Completeness(boolean complete,List<Finding> findings) {
        public Completeness {findings=List.copyOf(findings);}
    }
    public record Draft(UUID manifestId,String companyPartyId,String pipelineSystemId,long version,
                        Instant effectiveFrom,Instant effectiveUntil,List<SourcePin> pins,String canonicalSha256,
                        Completeness capturedCompleteness,Completeness currentCompleteness,
                        boolean replayed,boolean engineeringQualified,boolean authoritative,boolean frozen){
        public Draft {pins=List.copyOf(pins);}
    }
}
