/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringNetworkDraftRequests
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-19
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.request
 *
 * @Description : Implements the bounded company-scoped engineering draft contract.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.request;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import io.swagger.v3.oas.annotations.media.Schema;
public final class EngineeringNetworkDraftRequests {
    private EngineeringNetworkDraftRequests(){}
    @Schema(name="EngineeringDraftCreateRequest")
    public record Create(String companyPartyId,String pipelineSystemId,String creationRequestId,
            String title,String description,String evidenceReference){
        @JsonAnySetter public void unknown(String name,Object value){throw new IllegalArgumentException("Unsupported draft field");}
    }
    @Schema(name="EngineeringDraftUpdateRequest")
    public record Update(Long expectedVersion,String title,String description,String evidenceReference){
        @JsonAnySetter public void unknown(String name,Object value){throw new IllegalArgumentException("Unsupported draft field");}
    }
    @Schema(name="EngineeringDraftValidateRequest")
    public record Validate(Long expectedVersion){
        @JsonAnySetter public void unknown(String name,Object value){throw new IllegalArgumentException("Unsupported draft field");}
    }
}
