/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SuppliedFluidPropertyRevisionResponses
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-27
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.api.rest.response
 *
 * @Description : Provides strict protected supplied-fluid draft HTTP contracts.
 *
 */
package dz.sh.hidra.modules.custody.api.rest.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.*;

/** Explicit wire mappings preserve exact strings and null/empty without exposing persistence. */
public final class SuppliedFluidPropertyRevisionResponses {
    private SuppliedFluidPropertyRevisionResponses() {}
    @JsonInclude(JsonInclude.Include.ALWAYS) @Schema(name="SuppliedFluidSourceResponse")
    public record Source(String origin,String reference,@Schema(nullable=true) String evidenceReference) {}
    @JsonInclude(JsonInclude.Include.ALWAYS) @Schema(name="SuppliedFluidMethodKeyResponse")
    public record MethodKey(String reference,String revision) {}
    @JsonInclude(JsonInclude.Include.ALWAYS) @Schema(name="SuppliedFluidComponentResponse")
    public record Component(String reference,String fraction) {}
    @JsonInclude(JsonInclude.Include.ALWAYS) @Schema(name="SuppliedFluidCompositionResponse")
    public record Composition(String basis,List<Component> components,@Schema(nullable=true) MethodKey method,@Schema(nullable=true) Source source) {}
    @JsonInclude(JsonInclude.Include.ALWAYS) @Schema(name="SuppliedFluidConditionResponse")
    public record Condition(String value,String unitId) {}
    @JsonInclude(JsonInclude.Include.ALWAYS) @Schema(name="SuppliedFluidRangeResponse")
    public record Range(String minimum,String maximum,String unitId) {}
    @JsonInclude(JsonInclude.Include.ALWAYS) @Schema(name="SuppliedFluidObservationResponse")
    public record Observation(String observationId,@Schema(nullable=true) String name,String property,String value,String unitId,
            Condition absolutePressure,Condition absoluteTemperature,@Schema(nullable=true) MethodKey method,@Schema(nullable=true) Source source) {}
    @JsonInclude(JsonInclude.Include.ALWAYS) @Schema(name="SuppliedFluidMethodResponse")
    public record Method(MethodKey key,String origin,String basis,List<String> components,List<String> properties,
            Range absolutePressure,Range absoluteTemperature,@Schema(nullable=true) String evidenceReference) {}
    @JsonInclude(JsonInclude.Include.ALWAYS) @Schema(name="SuppliedFluidFactsResponse")
    public record Facts(@Schema(nullable=true) Composition composition,@Schema(nullable=true) List<Observation> properties,
            @Schema(nullable=true) List<Method> methods,@Schema(nullable=true) Source source,@Schema(nullable=true) String description) {}
    @JsonInclude(JsonInclude.Include.ALWAYS)
    @Schema(name="SuppliedFluidPropertyDraftResponse",description="Current supplied authoring revision only. ProductDraftId plus version identifies the supplied revision. Completeness is not engineering or numerical qualification.")
    public record Draft(UUID productDraftId,String companyPartyId,String localCode,String catalogProductId,
            @Schema(description="Independent supplied-property version, not the metadata version.") long version,
            @Schema(description="Exact authored product metadata version.") long productMetadataVersion,
            @Schema(description="Authored GAS/LIQUID label; retained even when metadata binding becomes stale.") String classification,
            Facts facts,String state,boolean authoringComplete,boolean replayed,boolean metadataBindingCurrent,List<String> diagnostics) {
        public Draft {Objects.requireNonNull(facts);diagnostics=List.copyOf(diagnostics);}
    }
}
