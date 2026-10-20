/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringRevisionPublicationRequests
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-20
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.request
 *
 * @Description : Defines strict publication mutation bodies without caller authority or effective-time overrides.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.request;

import tools.jackson.core.JsonParser;
import tools.jackson.databind.*;
import tools.jackson.databind.annotation.JsonDeserialize;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;

public final class EngineeringRevisionPublicationRequests {
    private EngineeringRevisionPublicationRequests(){}
    @JsonDeserialize(using=StrictPublish.class)
    @Schema(name="EngineeringPublicationPublishRequest")
    public record Publish(String requestId,Long draftVersion,Long expectedEpoch,String expectedRevisionId) {}
    @JsonDeserialize(using=StrictTransition.class)
    @Schema(name="EngineeringPublicationTransitionRequest")
    public record Transition(String requestId,Long expectedEpoch,String expectedRevisionId) {}
    private static JsonNode object(JsonParser parser,DeserializationContext context,Set<String> allowed) {
        JsonNode root=context.readTree(parser);
        if(root==null || !root.isObject())throw DatabindException.from(parser,"Publication object required");
        for(String key:root.propertyNames())if(!allowed.contains(key))throw DatabindException.from(parser,"Unsupported publication field");
        return root;
    }
    private static String string(JsonParser parser,JsonNode root,String key,boolean required) {
        JsonNode value=root.get(key);
        if(value==null || value.isNull()){
            if(required)throw DatabindException.from(parser,"Publication identity required");return null;
        }
        if(!value.isString() || value.stringValue().length()>36)throw DatabindException.from(parser,"Publication UUID must be a string");
        return value.stringValue();
    }
    private static long integer(JsonParser parser,JsonNode root,String key,long minimum) {
        JsonNode value=root.get(key);
        if(value==null || !value.isIntegralNumber())throw DatabindException.from(parser,"Publication integer required");
        try {long result=value.bigIntegerValue().longValueExact();if(result<minimum)throw new ArithmeticException();return result;}
        catch(ArithmeticException invalid){throw DatabindException.from(parser,"Publication integer outside bounds");}
    }
    public static final class StrictPublish extends ValueDeserializer<Publish> {
        @Override public Publish deserialize(JsonParser parser,DeserializationContext context) {
            var root=object(parser,context,Set.of("requestId","draftVersion","expectedEpoch","expectedRevisionId"));
            return new Publish(string(parser,root,"requestId",true),integer(parser,root,"draftVersion",1),
                    integer(parser,root,"expectedEpoch",0),string(parser,root,"expectedRevisionId",false));
        }
    }
    public static final class StrictTransition extends ValueDeserializer<Transition> {
        @Override public Transition deserialize(JsonParser parser,DeserializationContext context) {
            var root=object(parser,context,Set.of("requestId","expectedEpoch","expectedRevisionId"));
            return new Transition(string(parser,root,"requestId",true),integer(parser,root,"expectedEpoch",1),
                    string(parser,root,"expectedRevisionId",true));
        }
    }
}
