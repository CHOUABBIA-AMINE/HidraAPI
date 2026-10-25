/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringLocalExchangeResponses
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-25
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.response
 *
 * @Description : Implements bounded local editor exchange through existing protected owners.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public final class EngineeringLocalExchangeResponses {
    private EngineeringLocalExchangeResponses() { }
    @Schema(name="EngineeringLocalExchangeEnvelopeResponse")
    public record Envelope(String format,String editor,String companyPartyId,String pipelineSystemId,String contextId,
            String draftId,long sourceVersion,long componentVersion,String componentFormat,String sha256,String payload,
            String branchId,Long boundGraphVersion,String boundGraphSha256) { }
    @Schema(name="EngineeringLocalExchangeResult")
    public record Result(String operation,String editor,long observedVersion,Long targetVersion,
            @JsonProperty("package") Envelope bundle,List<String> diagnostics,String state,boolean engineeringQualified,
            String writeAuthorization,List<String> supportedOperations,int maximumDecodedBytes,
            Long boundGraphVersion,String boundGraphSha256) {
        public Result {diagnostics=List.copyOf(diagnostics);supportedOperations=List.copyOf(supportedOperations);}
    }
}
