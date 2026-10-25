/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringLocalExchangeRequests
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-25
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.request
 *
 * @Description : Implements bounded local editor exchange through existing protected owners.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.*;
import tools.jackson.databind.annotation.JsonDeserialize;
import java.util.*;

public final class EngineeringLocalExchangeRequests {
    private EngineeringLocalExchangeRequests() { }
    @Schema(name="EngineeringLocalExchangeEnvelopeRequest")
    public record Envelope(String format,String editor,String companyPartyId,String pipelineSystemId,String contextId,
            String draftId,Long sourceVersion,Long componentVersion,String componentFormat,String sha256,String payload,
            String branchId,Long boundGraphVersion,String boundGraphSha256) { }
    @Schema(name="EngineeringLocalExchangeSubmission")
    @JsonDeserialize(using=StrictSubmission.class)
    public record Submission(Long expectedVersion,@JsonProperty("package") Envelope bundle) { }
    public static final class StrictSubmission extends ValueDeserializer<Submission> {
        private static JsonNode object(JsonParser p,JsonNode n,Set<String> allowed){
            if(n==null || !n.isObject())throw DatabindException.from(p,"Exchange object required");
            for(String key:n.propertyNames())if(!allowed.contains(key))throw DatabindException.from(p,"Unsupported exchange field");
            return n;
        }
        private static String text(JsonParser p,JsonNode n,String key,boolean required,int maximum){
            var v=n.get(key);if(v==null || v.isNull()){if(required)throw DatabindException.from(p,"Exchange string required");return null;}
            if(!v.isString() || v.stringValue().length()>maximum)throw DatabindException.from(p,"Invalid exchange string");return v.stringValue();
        }
        private static Long number(JsonParser p,JsonNode n,String key,boolean required){
            var v=n.get(key);if(v==null || v.isNull()){if(required)throw DatabindException.from(p,"Exchange version required");return null;}
            if(!v.isIntegralNumber())throw DatabindException.from(p,"Exchange integer required");
            try{return v.bigIntegerValue().longValueExact();}catch(ArithmeticException e){throw DatabindException.from(p,"Exchange integer overflow");}
        }
        @Override public Submission deserialize(JsonParser p,DeserializationContext c){
            var root=object(p,c.readTree(p),Set.of("expectedVersion","package"));
            var n=object(p,root.get("package"),Set.of("format","editor","companyPartyId","pipelineSystemId","contextId","draftId",
                    "sourceVersion","componentVersion","componentFormat","sha256","payload","branchId","boundGraphVersion","boundGraphSha256"));
            return new Submission(number(p,root,"expectedVersion",true),new Envelope(
                text(p,n,"format",true,64),text(p,n,"editor",true,16),text(p,n,"companyPartyId",true,160),text(p,n,"pipelineSystemId",true,160),
                text(p,n,"contextId",true,36),text(p,n,"draftId",true,36),number(p,n,"sourceVersion",true),number(p,n,"componentVersion",true),
                text(p,n,"componentFormat",true,64),text(p,n,"sha256",true,64),text(p,n,"payload",true,22369624),
                text(p,n,"branchId",false,80),number(p,n,"boundGraphVersion",false),text(p,n,"boundGraphSha256",false,64)));
        }
    }
}
