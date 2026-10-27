/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SuppliedFluidPropertyRevisionRequests
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-27
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.api.rest.request
 *
 * @Description : Provides strict protected supplied-fluid draft HTTP contracts.
 *
 */
package dz.sh.hidra.modules.custody.api.rest.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import tools.jackson.databind.JsonNode;
import dz.sh.hidra.modules.custody.domain.model.SuppliedFluidPropertyRevision;
import java.util.*;
import java.util.function.Function;

/** Explicit wire types. Decimal values are exact strings; no coercion or conversion. */
public final class SuppliedFluidPropertyRevisionRequests {
    private SuppliedFluidPropertyRevisionRequests() {}
    @Schema(name="SuppliedFluidSourceRequest")
    public record Source(@Schema(requiredMode=Schema.RequiredMode.REQUIRED,allowableValues={"DECLARED_PARAMETER","SYNTHETIC"}) String origin,
            @Schema(requiredMode=Schema.RequiredMode.REQUIRED,maxLength=80) String reference,@Schema(nullable=true,maxLength=1000) String evidenceReference) {}
    @Schema(name="SuppliedFluidMethodKeyRequest")
    public record MethodKey(@Schema(requiredMode=Schema.RequiredMode.REQUIRED,maxLength=80) String reference,@Schema(requiredMode=Schema.RequiredMode.REQUIRED,maxLength=80) String revision) {}
    @Schema(name="SuppliedFluidComponentRequest")
    public record Component(@Schema(requiredMode=Schema.RequiredMode.REQUIRED,maxLength=80) String reference,
            @Schema(requiredMode=Schema.RequiredMode.REQUIRED,description="Positive canonical decimal string, at most one. Exact fractions sum to one.",pattern="(0|[1-9][0-9]{0,33})(\\.[0-9]{1,18})?") String fraction) {}
    @Schema(name="SuppliedFluidCompositionRequest")
    public record Composition(@Schema(requiredMode=Schema.RequiredMode.REQUIRED,allowableValues={"MOLE_FRACTION","MASS_FRACTION"}) String basis,
            @Schema(requiredMode=Schema.RequiredMode.REQUIRED) List<Component> components,@Schema(nullable=true) MethodKey method,@Schema(nullable=true) Source source) {}
    @Schema(name="SuppliedFluidConditionRequest")
    public record Condition(@Schema(requiredMode=Schema.RequiredMode.REQUIRED,description="Positive exact decimal string; absolute reference condition, no default.") String value,
            @Schema(requiredMode=Schema.RequiredMode.REQUIRED,maxLength=80) String unitId) {}
    @Schema(name="SuppliedFluidRangeRequest")
    public record Range(@Schema(requiredMode=Schema.RequiredMode.REQUIRED) String minimum,@Schema(requiredMode=Schema.RequiredMode.REQUIRED) String maximum,
            @Schema(requiredMode=Schema.RequiredMode.REQUIRED,maxLength=80) String unitId) {}
    @Schema(name="SuppliedFluidObservationRequest")
    public record Observation(@Schema(requiredMode=Schema.RequiredMode.REQUIRED,maxLength=80) String observationId,@Schema(nullable=true,maxLength=240) String name,
            @Schema(requiredMode=Schema.RequiredMode.REQUIRED,allowableValues={"DENSITY","DYNAMIC_VISCOSITY","COMPRESSIBILITY_FACTOR"}) String property,
            @Schema(requiredMode=Schema.RequiredMode.REQUIRED,description="Positive exact decimal string; no numeric JSON coercion.") String value,
            @Schema(requiredMode=Schema.RequiredMode.REQUIRED,maxLength=80) String unitId,@Schema(requiredMode=Schema.RequiredMode.REQUIRED) Condition absolutePressure,
            @Schema(requiredMode=Schema.RequiredMode.REQUIRED) Condition absoluteTemperature,@Schema(nullable=true) MethodKey method,@Schema(nullable=true) Source source) {}
    @Schema(name="SuppliedFluidMethodRequest")
    public record Method(@Schema(requiredMode=Schema.RequiredMode.REQUIRED) MethodKey key,
            @Schema(requiredMode=Schema.RequiredMode.REQUIRED,allowableValues={"DECLARED_PARAMETER","SYNTHETIC"}) String origin,
            @Schema(requiredMode=Schema.RequiredMode.REQUIRED,allowableValues={"MOLE_FRACTION","MASS_FRACTION"}) String basis,
            @Schema(requiredMode=Schema.RequiredMode.REQUIRED) List<String> components,@Schema(requiredMode=Schema.RequiredMode.REQUIRED) List<String> properties,
            @Schema(requiredMode=Schema.RequiredMode.REQUIRED) Range absolutePressure,@Schema(requiredMode=Schema.RequiredMode.REQUIRED) Range absoluteTemperature,
            @Schema(nullable=true,maxLength=1000) String evidenceReference) {}
    @Schema(name="SuppliedFluidPropertyWriteRequest",description="Authored declarations only; exact current metadata and property versions required. No actor or qualification fields.")
    public record Write(@Schema(requiredMode=Schema.RequiredMode.REQUIRED,format="uuid") String requestId,
            @Schema(requiredMode=Schema.RequiredMode.REQUIRED,minimum="0",format="int64") Long expectedVersion,
            @Schema(requiredMode=Schema.RequiredMode.REQUIRED,minimum="1",format="int64") Long expectedProductMetadataVersion,
            @Schema(nullable=true) Composition composition,@Schema(nullable=true) List<Observation> properties,@Schema(nullable=true) List<Method> methods,
            @Schema(nullable=true) Source source,@Schema(nullable=true,maxLength=4000) String description) {
        public Write {
            ProductEngineeringDraftRequests.uuid(requestId);
            if(expectedVersion==null || expectedVersion<0 || expectedVersion==Long.MAX_VALUE || expectedProductMetadataVersion==null || expectedProductMetadataVersion<=0)throw invalid();
            properties=bounded(properties,32);methods=bounded(methods,16);
            // Domain validation is authoritative for declaration invariants; eligibility stays in owners.
            SuppliedFluidPropertyRevisionRequests.facts(composition,properties,methods,source,description);
        }
        public SuppliedFluidPropertyRevision.Facts facts(){return SuppliedFluidPropertyRevisionRequests.facts(composition,properties,methods,source,description);}
        @JsonCreator(mode=JsonCreator.Mode.DELEGATING)
        public static Write from(JsonNode n) {
            object(n,Set.of("requestId","expectedVersion","expectedProductMetadataVersion","composition","properties","methods","source","description"));
            return new Write(text(n,"requestId"),integer(n,"expectedVersion"),integer(n,"expectedProductMetadataVersion"),optional(n,"composition",SuppliedFluidPropertyRevisionRequests::composition),
                array(n,"properties",32,SuppliedFluidPropertyRevisionRequests::observation),array(n,"methods",16,SuppliedFluidPropertyRevisionRequests::method),optional(n,"source",SuppliedFluidPropertyRevisionRequests::source),text(n,"description"));
        }
    }
    private static SuppliedFluidPropertyRevision.Facts facts(Composition c,List<Observation> p,List<Method> m,Source s,String description) {
        try {
            return new SuppliedFluidPropertyRevision.Facts(c==null?null:new SuppliedFluidPropertyRevision.Composition(SuppliedFluidPropertyRevision.Basis.valueOf(c.basis()),
                map(c.components(),x->new SuppliedFluidPropertyRevision.Component(x.reference(),new SuppliedFluidPropertyRevision.Decimal(x.fraction()))),key(c.method()),source(c.source())),
                map(p,x->new SuppliedFluidPropertyRevision.Observation(x.observationId(),x.name(),SuppliedFluidPropertyRevision.Property.valueOf(x.property()),new SuppliedFluidPropertyRevision.Decimal(x.value()),x.unitId(),condition(x.absolutePressure()),condition(x.absoluteTemperature()),key(x.method()),source(x.source()))),
                map(m,x->new SuppliedFluidPropertyRevision.Method(key(x.key()),SuppliedFluidPropertyRevision.Origin.valueOf(x.origin()),SuppliedFluidPropertyRevision.Basis.valueOf(x.basis()),x.components(),map(x.properties(),SuppliedFluidPropertyRevision.Property::valueOf),range(x.absolutePressure()),range(x.absoluteTemperature()),x.evidenceReference())),source(s),description);
        }catch(NullPointerException error){throw invalid();}
    }
    private static SuppliedFluidPropertyRevision.Source source(Source s){return s==null?null:new SuppliedFluidPropertyRevision.Source(SuppliedFluidPropertyRevision.Origin.valueOf(s.origin()),s.reference(),s.evidenceReference());}
    private static SuppliedFluidPropertyRevision.MethodKey key(MethodKey k){return k==null?null:new SuppliedFluidPropertyRevision.MethodKey(k.reference(),k.revision());}
    private static SuppliedFluidPropertyRevision.Condition condition(Condition c){return new SuppliedFluidPropertyRevision.Condition(new SuppliedFluidPropertyRevision.Decimal(c.value()),c.unitId());}
    private static SuppliedFluidPropertyRevision.Range range(Range c){return new SuppliedFluidPropertyRevision.Range(new SuppliedFluidPropertyRevision.Decimal(c.minimum()),new SuppliedFluidPropertyRevision.Decimal(c.maximum()),c.unitId());}
    private static <T,R> List<R> map(List<T> xs,Function<T,R> fn){return xs==null?null:xs.stream().map(fn).toList();}
    private static <T> List<T> bounded(List<T> xs,int max){if(xs==null)return null;if(xs.size()>max || xs.stream().anyMatch(Objects::isNull))throw invalid();return List.copyOf(xs);}
    private static void object(JsonNode n,Set<String> names){if(n==null || !n.isObject())throw invalid();for(String name:n.propertyNames())if(!names.contains(name))throw invalid();}
    private static String text(JsonNode n,String key){var x=n.get(key);if(x==null || x.isNull())return null;if(!x.isTextual())throw invalid();return x.asText();}
    private static Long integer(JsonNode n,String key){var x=n.get(key);if(x==null || !x.isIntegralNumber() || !x.canConvertToLong())throw invalid();return x.longValue();}
    private static <T> T optional(JsonNode n,String key,Function<JsonNode,T> fn){var x=n.get(key);return x==null || x.isNull()?null:fn.apply(x);}
    private static <T> List<T> array(JsonNode n,String key,int max,Function<JsonNode,T> fn){var x=n.get(key);if(x==null || x.isNull())return null;if(!x.isArray() || x.size()>max)throw invalid();var a=new ArrayList<T>();for(var child:x){if(child==null || child.isNull())throw invalid();a.add(fn.apply(child));}return List.copyOf(a);}
    private static String scalar(JsonNode n){if(n==null || !n.isTextual())throw invalid();return n.asText();}
    private static Source source(JsonNode n){object(n,Set.of("origin","reference","evidenceReference"));return new Source(text(n,"origin"),text(n,"reference"),text(n,"evidenceReference"));}
    private static MethodKey key(JsonNode n){object(n,Set.of("reference","revision"));return new MethodKey(text(n,"reference"),text(n,"revision"));}
    private static Composition composition(JsonNode n){object(n,Set.of("basis","components","method","source"));return new Composition(text(n,"basis"),array(n,"components",64,x->{object(x,Set.of("reference","fraction"));return new Component(text(x,"reference"),text(x,"fraction"));}),optional(n,"method",SuppliedFluidPropertyRevisionRequests::key),optional(n,"source",SuppliedFluidPropertyRevisionRequests::source));}
    private static Condition condition(JsonNode n){object(n,Set.of("value","unitId"));return new Condition(text(n,"value"),text(n,"unitId"));}
    private static Range range(JsonNode n){object(n,Set.of("minimum","maximum","unitId"));return new Range(text(n,"minimum"),text(n,"maximum"),text(n,"unitId"));}
    private static Observation observation(JsonNode n){object(n,Set.of("observationId","name","property","value","unitId","absolutePressure","absoluteTemperature","method","source"));return new Observation(text(n,"observationId"),text(n,"name"),text(n,"property"),text(n,"value"),text(n,"unitId"),optional(n,"absolutePressure",SuppliedFluidPropertyRevisionRequests::condition),optional(n,"absoluteTemperature",SuppliedFluidPropertyRevisionRequests::condition),optional(n,"method",SuppliedFluidPropertyRevisionRequests::key),optional(n,"source",SuppliedFluidPropertyRevisionRequests::source));}
    private static Method method(JsonNode n){object(n,Set.of("key","origin","basis","components","properties","absolutePressure","absoluteTemperature","evidenceReference"));return new Method(optional(n,"key",SuppliedFluidPropertyRevisionRequests::key),text(n,"origin"),text(n,"basis"),array(n,"components",64,SuppliedFluidPropertyRevisionRequests::scalar),array(n,"properties",3,SuppliedFluidPropertyRevisionRequests::scalar),optional(n,"absolutePressure",SuppliedFluidPropertyRevisionRequests::range),optional(n,"absoluteTemperature",SuppliedFluidPropertyRevisionRequests::range),text(n,"evidenceReference"));}
    private static IllegalArgumentException invalid(){return new IllegalArgumentException("Invalid supplied fluid request");}
}
