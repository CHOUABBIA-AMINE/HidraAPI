/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProductEngineeringDraftResponses
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-27
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.api.rest.response
 *
 * @Description : Provides company-owned catalog-linked product metadata drafts.
 *
 */
package dz.sh.hidra.modules.custody.api.rest.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import dz.sh.hidra.modules.custody.application.port.in.ProductEngineeringDraftUseCase.Result;
import java.util.*;

public final class ProductEngineeringDraftResponses {
    private ProductEngineeringDraftResponses() {}
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name="ProductEngineeringDraftMetadataResponse")
    public record Metadata(@Schema(nullable=true) String name,@Schema(nullable=true) String description,
            @Schema(nullable=true) String evidenceReference,
            @Schema(nullable=true,allowableValues={"GAS","LIQUID"},description="Authored label only. Null means unspecified; no physical qualification is implied.") String classification) {}
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name="ProductEngineeringDraftResponse",description="Current company-local metadata draft. No property computation, publication or engineering qualification.")
    public record Draft(UUID draftId,String companyPartyId,String localCode,String catalogProductId,long version,
            Metadata metadata,String state,boolean metadataComplete,boolean replayed,List<String> diagnostics) {
        public Draft {Objects.requireNonNull(metadata);diagnostics=List.copyOf(diagnostics);}
    }
    public static Draft from(Result result) {
        var draft=result.draft();var metadata=draft.metadata();
        return new Draft(draft.id(),draft.companyPartyId(),draft.localCode(),draft.catalogProductId(),draft.version(),
                new Metadata(metadata.name(),metadata.description(),metadata.evidenceReference(),metadata.classification()==null?null:metadata.classification().name()),
                "DRAFT",result.diagnostics().isEmpty(),result.replayed(),result.diagnostics());
    }
}
