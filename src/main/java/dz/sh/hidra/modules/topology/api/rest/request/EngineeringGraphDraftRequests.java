/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringGraphDraftRequests
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-19
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.request
 *
 * @Description : Defines bounded, strict HTTP graph draft inputs without persistence models or equipment measurements.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.request;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonMappingException;
import java.io.IOException;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.Objects;

public final class EngineeringGraphDraftRequests {
    private EngineeringGraphDraftRequests() { }

    /** Reject scalar coercions that Jackson's default String deserializer might accept. */
    public static final class StrictText extends JsonDeserializer<String> {
        @Override public String deserialize(JsonParser parser, DeserializationContext context) throws IOException {
            if (parser.currentToken() != JsonToken.VALUE_STRING)
                throw JsonMappingException.from(parser, "Graph text must be a JSON string");
            return parser.getText();
        }
    }
    /** Do not turn a JSON float, boolean or numeric string into an expectedVersion. */
    public static final class StrictVersion extends JsonDeserializer<Long> {
        @Override public Long deserialize(JsonParser parser, DeserializationContext context) throws IOException {
            if (parser.currentToken() != JsonToken.VALUE_NUMBER_INT)
                throw JsonMappingException.from(parser, "Expected version must be a JSON integer");
            try { return parser.getBigIntegerValue().longValueExact(); }
            catch (ArithmeticException outOfRange) {
                throw JsonMappingException.from(parser, "Version exceeds signed 64-bit range", outOfRange);
            }
        }
    }
    public record Node(@JsonDeserialize(using=StrictText.class) String id,
                       @JsonDeserialize(using=StrictText.class) String kind,
                       @JsonDeserialize(using=StrictText.class) String facilityId) {
        @JsonAnySetter public void unknown(String name, Object value) {
            throw new IllegalArgumentException("Unsupported graph node field");
        }
    }
    public record Port(@JsonDeserialize(using=StrictText.class) String id,
                       @JsonDeserialize(using=StrictText.class) String nodeId,
                       @JsonDeserialize(using=StrictText.class) String direction) {
        @JsonAnySetter public void unknown(String name, Object value) {
            throw new IllegalArgumentException("Unsupported graph port field");
        }
    }
    public record Branch(@JsonDeserialize(using=StrictText.class) String id,
                         @JsonDeserialize(using=StrictText.class) String kind,
                         @JsonDeserialize(using=StrictText.class) String fromPortId,
                         @JsonDeserialize(using=StrictText.class) String toPortId) {
        @JsonAnySetter public void unknown(String name, Object value) {
            throw new IllegalArgumentException("Unsupported graph branch field");
        }
    }
    @JsonDeserialize(using=StrictUpdate.class)
    @Schema(name="EngineeringGraphDraftUpdateRequest")
    public record Update(@JsonDeserialize(using=StrictVersion.class) Long expectedVersion,
                         List<Node> nodes, List<Port> ports, List<Branch> branches) {
        @JsonAnySetter public void unknown(String name, Object value) {
            throw new IllegalArgumentException("Unsupported graph field");
        }
        /** Only transport validation belongs here; domain conversion belongs to the controller boundary. */
        public void validate() {
            if (expectedVersion == null || expectedVersion < 1)
                throw new IllegalArgumentException("Positive expected version required");
            if (nodes == null || ports == null || branches == null
                    || nodes.stream().anyMatch(Objects::isNull)
                    || ports.stream().anyMatch(Objects::isNull)
                    || branches.stream().anyMatch(Objects::isNull))
                throw new IllegalArgumentException("Invalid graph arrays");
        }
    }

    /** Read the JSON syntax before Jackson's permissive record/scalar coercion can occur. */
    public static final class StrictUpdate extends JsonDeserializer<Update> {
        private static void object(JsonParser p, com.fasterxml.jackson.databind.JsonNode value,
                                   String... allowed) throws JsonMappingException {
            if (value == null || !value.isObject())
                throw JsonMappingException.from(p, "Graph object required");
            var permitted = java.util.Set.of(allowed);
            var keys = value.fieldNames();
            while (keys.hasNext())
                if (!permitted.contains(keys.next()))
                    throw JsonMappingException.from(p, "Unsupported graph property");
        }
        private static String string(JsonParser p, com.fasterxml.jackson.databind.JsonNode value,
                                     String field, boolean required) throws JsonMappingException {
            var node = value.get(field);
            if (node == null || node.isNull()) {
                if (required) throw JsonMappingException.from(p, "Required graph string missing");
                return null;
            }
            if (!node.isTextual()) throw JsonMappingException.from(p, "Graph values must be JSON strings");
            return node.textValue();
        }
        private static com.fasterxml.jackson.databind.JsonNode array(JsonParser p,
                com.fasterxml.jackson.databind.JsonNode root, String field) throws JsonMappingException {
            var node = root.get(field);
            if (node == null || !node.isArray())
                throw JsonMappingException.from(p, "Required graph array missing");
            return node;
        }
        @Override public Update deserialize(JsonParser p, DeserializationContext context) throws IOException {
            com.fasterxml.jackson.databind.JsonNode root = p.getCodec().readTree(p);
            object(p,root,"expectedVersion","nodes","ports","branches");
            var versionNode=root.get("expectedVersion");
            if (versionNode == null || !versionNode.isIntegralNumber())
                throw JsonMappingException.from(p, "Expected version must be an integer");
            final long version;
            try { version=versionNode.bigIntegerValue().longValueExact(); }
            catch (ArithmeticException range) {
                throw JsonMappingException.from(p,"Expected version exceeds 64-bit range",range);
            }
            if(version < 1) throw JsonMappingException.from(p,"Expected version must be positive");
            var nodes = new java.util.ArrayList<Node>();
            for(var item:array(p,root,"nodes")) {
                object(p,item,"id","kind","facilityId");
                nodes.add(new Node(string(p,item,"id",true),string(p,item,"kind",true),
                        string(p,item,"facilityId",false)));
            }
            var ports = new java.util.ArrayList<Port>();
            for(var item:array(p,root,"ports")) {
                object(p,item,"id","nodeId","direction");
                ports.add(new Port(string(p,item,"id",true),string(p,item,"nodeId",true),
                        string(p,item,"direction",true)));
            }
            var branches = new java.util.ArrayList<Branch>();
            for(var item:array(p,root,"branches")) {
                object(p,item,"id","kind","fromPortId","toPortId");
                branches.add(new Branch(string(p,item,"id",true),string(p,item,"kind",true),
                        string(p,item,"fromPortId",true),string(p,item,"toPortId",true)));
            }
            return new Update(version,List.copyOf(nodes),List.copyOf(ports),List.copyOf(branches));
        }
    }
}
