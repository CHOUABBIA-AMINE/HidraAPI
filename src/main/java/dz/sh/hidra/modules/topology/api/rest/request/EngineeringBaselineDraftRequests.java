/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringBaselineDraftRequests
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-03
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.request
 *
 * @Description : Defines bounded baseline draft transport input independently of persistence.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.request;


import java.time.Instant;
import java.util.List;
import java.util.UUID;
public final class EngineeringBaselineDraftRequests {
    private EngineeringBaselineDraftRequests(){}
    public record SourcePin(String kind,String ownerId,String sourceId,String revisionId,String sha256) {}
    public record Create(UUID manifestId,String companyPartyId,String pipelineSystemId,UUID requestId,
                         Instant effectiveFrom,Instant effectiveUntil,List<SourcePin> pins) {}
    public record Update(String companyPartyId,String pipelineSystemId,long expectedVersion,UUID requestId,
                         Instant effectiveFrom,Instant effectiveUntil,List<SourcePin> pins) {}
}
