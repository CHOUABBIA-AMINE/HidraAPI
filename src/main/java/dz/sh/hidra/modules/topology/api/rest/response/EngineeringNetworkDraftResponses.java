/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringNetworkDraftResponses
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-19
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.response
 *
 * @Description : Implements the bounded company-scoped engineering draft contract.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.response;

import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import dz.sh.hidra.modules.topology.application.port.in.EngineeringNetworkDraftUseCase.Result;
public final class EngineeringNetworkDraftResponses {
    private EngineeringNetworkDraftResponses(){}
    @Schema(name="EngineeringDraftMetadataResponse")
    public record Metadata(String title,String description,String evidenceReference){}
    @Schema(name="EngineeringNetworkDraftResponse")
    public record Draft(UUID contextId,String companyPartyId,String pipelineSystemId,UUID draftId,long version,
        Metadata metadata,String state,boolean engineeringComplete,boolean replayed,String validationStatus,List<String> diagnostics){}
    public static Draft from(Result r){var d=r.draft();var m=d.metadata();return new Draft(d.contextId(),d.companyPartyId(),d.pipelineSystemId(),d.draftId(),d.version(),
        new Metadata(m.title(),m.description(),m.evidenceReference()),"DRAFT",false,r.replayed(),"INCOMPLETE",r.diagnostics());}
}
