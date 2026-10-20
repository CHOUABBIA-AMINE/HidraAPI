/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringGraphDraftRequests
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-20
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
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.ValueDeserializer;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DatabindException;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.Objects;

public final class EngineeringGraphDraftRequests {
    private EngineeringGraphDraftRequests() { }

    public record Node(String id,
                       String kind,
                       String facilityId) {
        @JsonAnySetter public void unknown(String name, Object value) {
            throw new IllegalArgumentException("Unsupported graph node field");
        }
    }
    public record Port(String id,
                       String nodeId,
                       String direction) {
        @JsonAnySetter public void unknown(String name, Object value) {
            throw new IllegalArgumentException("Unsupported graph port field");
        }
    }
    public record Branch(String id,
                         String kind,
                         String fromPortId,
                         String toPortId) {
        @JsonAnySetter public void unknown(String name, Object value) {
            throw new IllegalArgumentException("Unsupported graph branch field");
        }
    }
    @JsonDeserialize(using=StrictUpdate.class)
    @Schema(name="EngineeringGraphDraftUpdateRequest")
    public record Update(Long expectedVersion,
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
    public static final class StrictUpdate extends ValueDeserializer<Update> {
        private static void object(JsonParser p, tools.jackson.databind.JsonNode value,
                                   String... allowed) throws DatabindException {
            if (value == null || !value.isObject())
                throw DatabindException.from(p, "Graph object required");
            var permitted = java.util.Set.of(allowed);
            for (String key : value.propertyNames())
                if (!permitted.contains(key))
                    throw DatabindException.from(p, "Unsupported graph property");
        }
        private static String string(JsonParser p, tools.jackson.databind.JsonNode value,
                                     String field, boolean required) throws DatabindException {
            var node = value.get(field);
            if (node == null || node.isNull()) {
                if (required) throw DatabindException.from(p, "Required graph string missing");
                return null;
            }
            if (!node.isString()) throw DatabindException.from(p, "Graph values must be JSON strings");
            return node.stringValue();
        }
        private static tools.jackson.databind.JsonNode array(JsonParser p,
                tools.jackson.databind.JsonNode root, String field) throws DatabindException {
            var node = root.get(field);
            if (node == null || !node.isArray())
                throw DatabindException.from(p, "Required graph array missing");
            return node;
        }
        @Override public Update deserialize(JsonParser p, DeserializationContext context) {
            tools.jackson.databind.JsonNode root = context.readTree(p);
            object(p,root,"expectedVersion","nodes","ports","branches");
            var versionNode=root.get("expectedVersion");
            if (versionNode == null || !versionNode.isIntegralNumber())
                throw DatabindException.from(p, "Expected version must be an integer");
            final long version;
            try { version=versionNode.bigIntegerValue().longValueExact(); }
            catch (ArithmeticException range) {
                throw DatabindException.from(p,"Expected version exceeds 64-bit range",range);
            }
            if(version < 1) throw DatabindException.from(p,"Expected version must be positive");
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
