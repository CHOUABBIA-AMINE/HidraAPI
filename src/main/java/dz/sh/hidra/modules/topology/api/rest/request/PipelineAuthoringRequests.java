/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineAuthoringRequests
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-04
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.request
 *
 * @Description : Owns the bounded canonical Pipeline and persisted graph association contract.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.request;

import java.util.*;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import io.swagger.v3.oas.annotations.media.Schema;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.ValueDeserializer;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DatabindException;
public final class PipelineAuthoringRequests {
    private PipelineAuthoringRequests() {}
    @Schema(name="PipelineAuthoringPipelineRequest") @JsonDeserialize(using=StrictPipeline.class)
    public record Pipeline(String id,Long expectedRevision,String code,String pipelineTypeId,String nameAr,String nameFr,String nameEn) {
        @JsonAnySetter public void unknown(String name,Object value){throw new IllegalArgumentException("Unsupported Pipeline property");}
    }
    @Schema(name="PipelineAuthoringReferenceRequest") @JsonDeserialize(using=StrictReference.class)
    public record Reference(String branchId,String pipelineId,String segmentId) {
        @JsonAnySetter public void unknown(String name,Object value){throw new IllegalArgumentException("Unsupported Pipeline reference property");}
    }
    @Schema(name="PipelineAuthoringWriteRequest")
    public record Write(UUID requestId,Pipeline pipeline,EngineeringGraphDraftRequests.Update graph,List<Reference> references) {
        @JsonAnySetter public void unknown(String name,Object value){throw new IllegalArgumentException("Unsupported Pipeline authoring property");}
    }
    public static final class StrictReference extends ValueDeserializer<Reference> {
        @Override public Reference deserialize(JsonParser parser,DeserializationContext context){
            var root=context.readTree(parser);if(!root.isObject())throw DatabindException.from(parser,"Reference object required");
            for(String key:root.propertyNames())if(!Set.of("branchId","pipelineId","segmentId").contains(key))throw DatabindException.from(parser,"Unsupported reference field");
            var values=new String[3];int index=0;for(String key:List.of("branchId","pipelineId","segmentId")){
                var value=root.get(key);if(value==null||value.isNull()){if(index<2)throw DatabindException.from(parser,"Reference identity required");}
                else {if(!value.isString())throw DatabindException.from(parser,"Reference identity must be a string");values[index]=value.stringValue();}index++;
            }
            return new Reference(values[0],values[1],values[2]);
        }
    }
    public static final class StrictPipeline extends ValueDeserializer<Pipeline> {
        private String string(JsonParser parser,tools.jackson.databind.JsonNode root,String key,boolean required){var node=root.get(key);if(node==null||node.isNull()){if(required)throw DatabindException.from(parser,"Missing Pipeline field");return null;}if(!node.isString())throw DatabindException.from(parser,"Pipeline fields must be strings");return node.stringValue();}
        @Override public Pipeline deserialize(JsonParser parser,DeserializationContext context){
            var root=context.readTree(parser);if(!root.isObject())throw DatabindException.from(parser,"Pipeline object required");
            var allowed=Set.of("id","expectedRevision","code","pipelineTypeId","nameAr","nameFr","nameEn");for(String key:root.propertyNames())if(!allowed.contains(key))throw DatabindException.from(parser,"Unsupported Pipeline field");
            var revision=root.get("expectedRevision");if(revision==null||!revision.isIntegralNumber())throw DatabindException.from(parser,"Expected Pipeline revision must be an integer");
            final long value;try{value=revision.bigIntegerValue().longValueExact();}catch(ArithmeticException invalid){throw DatabindException.from(parser,"Expected Pipeline revision exceeds supported range",invalid);}
            if(value<0||value==Long.MAX_VALUE)throw DatabindException.from(parser,"Invalid Pipeline revision");
            return new Pipeline(string(parser,root,"id",true),value,string(parser,root,"code",true),string(parser,root,"pipelineTypeId",true),string(parser,root,"nameAr",false),string(parser,root,"nameFr",false),string(parser,root,"nameEn",false));
        }
    }
}
