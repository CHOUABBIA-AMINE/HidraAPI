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
import dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft;
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
        EngineeringGraphDraft.Node toDomain() {
            if (kind == null) throw new IllegalArgumentException("Node kind required");
            return new EngineeringGraphDraft.Node(id, EngineeringGraphDraft.NodeKind.valueOf(kind), facilityId);
        }
    }
    public record Port(@JsonDeserialize(using=StrictText.class) String id,
                       @JsonDeserialize(using=StrictText.class) String nodeId,
                       @JsonDeserialize(using=StrictText.class) String direction) {
        @JsonAnySetter public void unknown(String name, Object value) {
            throw new IllegalArgumentException("Unsupported graph port field");
        }
        EngineeringGraphDraft.Port toDomain() {
            if (direction == null) throw new IllegalArgumentException("Port direction required");
            return new EngineeringGraphDraft.Port(id, nodeId, EngineeringGraphDraft.PortDirection.valueOf(direction));
        }
    }
    public record Branch(@JsonDeserialize(using=StrictText.class) String id,
                         @JsonDeserialize(using=StrictText.class) String kind,
                         @JsonDeserialize(using=StrictText.class) String fromPortId,
                         @JsonDeserialize(using=StrictText.class) String toPortId) {
        @JsonAnySetter public void unknown(String name, Object value) {
            throw new IllegalArgumentException("Unsupported graph branch field");
        }
        EngineeringGraphDraft.Branch toDomain() {
            if (kind == null) throw new IllegalArgumentException("Branch kind required");
            return new EngineeringGraphDraft.Branch(id, EngineeringGraphDraft.BranchKind.valueOf(kind), fromPortId, toPortId);
        }
    }
    @Schema(name="EngineeringGraphDraftUpdateRequest")
    public record Update(@JsonDeserialize(using=StrictVersion.class) Long expectedVersion,
                         List<Node> nodes, List<Port> ports, List<Branch> branches) {
        @JsonAnySetter public void unknown(String name, Object value) {
            throw new IllegalArgumentException("Unsupported graph field");
        }
        public EngineeringGraphDraft graph() {
            if (expectedVersion == null || expectedVersion < 1)
                throw new IllegalArgumentException("Positive expected version required");
            Objects.requireNonNull(nodes, "Nodes array required");
            Objects.requireNonNull(ports, "Ports array required");
            Objects.requireNonNull(branches, "Branches array required");
            if (nodes.stream().anyMatch(Objects::isNull)
                    || ports.stream().anyMatch(Objects::isNull)
                    || branches.stream().anyMatch(Objects::isNull))
                throw new IllegalArgumentException("Null graph element");
            return new EngineeringGraphDraft(nodes.stream().map(Node::toDomain).toList(),
                    ports.stream().map(Port::toDomain).toList(),
                    branches.stream().map(Branch::toDomain).toList());
        }
    }
}
