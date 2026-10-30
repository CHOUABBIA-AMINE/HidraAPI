/**
 * HidraAPI — protected station configuration HTTP request contracts.
 * Transport-only values: no actor identity, session, approval, permission or database evidence.
 */
package dz.sh.hidra.modules.topology.api.rest.request;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.*;
import tools.jackson.databind.annotation.JsonDeserialize;
import java.util.Set;
import java.util.ArrayList;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

/** Unknown fields fail closed, including forged native-provenance inputs. */
public final class StationInstalledConfigurationRequests {
    private StationInstalledConfigurationRequests() {}
    @Schema(name="StationConfigurationPositionRequest")
    public record Position(String positionId,String disposition,String installedUnitId,
            String installationId,Long installationRevision,String installationFrom,
            String installationUntil,String installationEvidenceReference) {
        @JsonAnySetter public void unknown(String key,Object value){throw new IllegalArgumentException("Unsupported station configuration field");}
    }
    @Schema(name="StationConfigurationHeaderRequest")
    public record Header(String id,String kind,String portId) {
        @JsonAnySetter public void unknown(String key,Object value){throw new IllegalArgumentException("Unsupported station graph field");}
    }
    @Schema(name="StationConfigurationUnitRequest")
    public record Unit(String equipmentId,String facilityId,String inletPortId,String outletPortId) {
        @JsonAnySetter public void unknown(String key,Object value){throw new IllegalArgumentException("Unsupported station graph field");}
    }
    @Schema(name="StationConfigurationEdgeRequest")
    public record Edge(String id,String fromPortId,String toPortId) {
        @JsonAnySetter public void unknown(String key,Object value){throw new IllegalArgumentException("Unsupported station graph field");}
    }
    @Schema(name="StationConfigurationGraphRequest")
    public record Graph(String facilityId,String revisionId,String status,
            List<Header> headers,List<Unit> units,List<Edge> edges) {
        @JsonAnySetter public void unknown(String key,Object value){throw new IllegalArgumentException("Unsupported station graph field");}
    }
    @Schema(name="StationConfigurationGraphRevisionRequest")
    public record GraphRevision(String facilityId,String revisionId,Graph graph,
            String sourceClassification,String recordedAt,String effectiveFrom,
            String effectiveUntil,String evidenceReference,String expectedPredecessorRevisionId,
            String qualification) {
        @JsonAnySetter public void unknown(String key,Object value){throw new IllegalArgumentException("Unsupported graph revision field");}
    }
    @Schema(name="StationConfigurationSaveRequest")
    @JsonDeserialize(using=SaveDeserializer.class)
    public record Save(String requestId,String companyPartyId,String revisionId,
            Long expectedEpoch,Long graphHeadEpoch,GraphRevision pinnedGraph,
            String graphPayloadFormat,String graphSha256,String effectiveFrom,
            String effectiveUntil,String sourceReference,List<Position> positions,
            List<String> activeEdgeIds) {
        @JsonAnySetter public void unknown(String key,Object value){throw new IllegalArgumentException("Unsupported station configuration field");}
    }

    /** Strict recursive parser prevents coerced epochs, invented authority, and unknown nested fields. */
    public static final class SaveDeserializer extends ValueDeserializer<Save> {
        private static JsonNode object(JsonParser p,JsonNode n,Set<String> keys){
            if(n==null || !n.isObject())throw DatabindException.from(p,"Station configuration object required");
            for(String key:n.propertyNames())if(!keys.contains(key))
                throw DatabindException.from(p,"Unsupported station configuration field");
            return n;
        }
        private static String string(JsonParser p,JsonNode n,String key,boolean required,int max){
            JsonNode v=n.get(key);
            if(v==null || v.isNull()){
                if(required)throw DatabindException.from(p,"Required station configuration text absent");
                return null;
            }
            if(!v.isString())throw DatabindException.from(p,"Station configuration text expected");
            String s=v.stringValue();
            if(s.isBlank() || !s.equals(s.strip()) || s.codePointCount(0,s.length())>max
                || s.codePoints().anyMatch(Character::isISOControl))
                throw DatabindException.from(p,"Invalid station configuration text");
            return s;
        }
        private static Long number(JsonParser p,JsonNode n,String key,boolean required){
            JsonNode v=n.get(key);
            if(v==null || v.isNull()){
                if(required)throw DatabindException.from(p,"Station configuration integer absent");
                return null;
            }
            if(!v.isIntegralNumber())throw DatabindException.from(p,"Exact integer required");
            try{return v.bigIntegerValue().longValueExact();}
            catch(ArithmeticException invalid){throw DatabindException.from(p,"Integer out of range");}
        }
        private static JsonNode array(JsonParser p,JsonNode n,String field,int max){
            JsonNode v=n.get(field);
            if(v==null || !v.isArray() || v.size()>max)
                throw DatabindException.from(p,"Bounded station configuration array required");
            return v;
        }
        private static Header header(JsonParser p,JsonNode n){
            object(p,n,Set.of("id","kind","portId"));
            return new Header(string(p,n,"id",true,80),string(p,n,"kind",true,24),
                string(p,n,"portId",true,80));
        }
        private static Unit unit(JsonParser p,JsonNode n){
            object(p,n,Set.of("equipmentId","facilityId","inletPortId","outletPortId"));
            return new Unit(string(p,n,"equipmentId",true,80),
                string(p,n,"facilityId",true,80),string(p,n,"inletPortId",true,80),
                string(p,n,"outletPortId",true,80));
        }
        private static Edge edge(JsonParser p,JsonNode n){
            object(p,n,Set.of("id","fromPortId","toPortId"));
            return new Edge(string(p,n,"id",true,80),string(p,n,"fromPortId",true,80),
                string(p,n,"toPortId",true,80));
        }
        private static Graph graph(JsonParser p,JsonNode n){
            object(p,n,Set.of("facilityId","revisionId","status","headers","units","edges"));
            var headers=new ArrayList<Header>();
            for(JsonNode h:array(p,n,"headers",128))headers.add(header(p,h));
            var units=new ArrayList<Unit>();
            for(JsonNode u:array(p,n,"units",128))units.add(unit(p,u));
            var edges=new ArrayList<Edge>();
            for(JsonNode e:array(p,n,"edges",1024))edges.add(edge(p,e));
            return new Graph(string(p,n,"facilityId",true,80),string(p,n,"revisionId",true,80),
                string(p,n,"status",true,20),List.copyOf(headers),List.copyOf(units),List.copyOf(edges));
        }
        private static GraphRevision revision(JsonParser p,JsonNode n){
            object(p,n,Set.of("facilityId","revisionId","graph","sourceClassification",
                "recordedAt","effectiveFrom","effectiveUntil","evidenceReference",
                "expectedPredecessorRevisionId","qualification"));
            return new GraphRevision(string(p,n,"facilityId",true,80),
                string(p,n,"revisionId",true,80),graph(p,n.get("graph")),
                string(p,n,"sourceClassification",true,32),string(p,n,"recordedAt",true,40),
                string(p,n,"effectiveFrom",true,40),string(p,n,"effectiveUntil",false,40),
                string(p,n,"evidenceReference",true,65536),
                string(p,n,"expectedPredecessorRevisionId",false,80),
                string(p,n,"qualification",true,32));
        }
        private static Position position(JsonParser p,JsonNode n){
            object(p,n,Set.of("positionId","disposition","installedUnitId","installationId",
                "installationRevision","installationFrom","installationUntil",
                "installationEvidenceReference"));
            return new Position(string(p,n,"positionId",true,80),
                string(p,n,"disposition",true,24),string(p,n,"installedUnitId",false,80),
                string(p,n,"installationId",false,80),number(p,n,"installationRevision",false),
                string(p,n,"installationFrom",false,40),
                string(p,n,"installationUntil",false,40),
                string(p,n,"installationEvidenceReference",false,65536));
        }
        @Override public Save deserialize(JsonParser parser,DeserializationContext context){
            var n=object(parser,context.readTree(parser),
                Set.of("requestId","companyPartyId","revisionId","expectedEpoch",
                    "graphHeadEpoch","pinnedGraph","graphPayloadFormat","graphSha256",
                    "effectiveFrom","effectiveUntil","sourceReference","positions",
                    "activeEdgeIds"));
            var positions=new ArrayList<Position>();
            for(JsonNode p:array(parser,n,"positions",128))positions.add(position(parser,p));
            var edges=new ArrayList<String>();
            for(JsonNode e:array(parser,n,"activeEdgeIds",1024)){
                if(!e.isString())throw DatabindException.from(parser,"Active edge must be text");
                String value=e.stringValue();
                if(value.isBlank() || !value.equals(value.strip()) || value.codePointCount(0,value.length())>80)
                    throw DatabindException.from(parser,"Invalid active edge identity");
                edges.add(value);
            }
            return new Save(string(parser,n,"requestId",true,80),
                string(parser,n,"companyPartyId",true,80),
                string(parser,n,"revisionId",true,80),
                number(parser,n,"expectedEpoch",true),
                number(parser,n,"graphHeadEpoch",true),
                revision(parser,n.get("pinnedGraph")),
                string(parser,n,"graphPayloadFormat",true,80),
                string(parser,n,"graphSha256",true,64),
                string(parser,n,"effectiveFrom",true,40),
                string(parser,n,"effectiveUntil",false,40),
                string(parser,n,"sourceReference",true,65536),
                List.copyOf(positions),List.copyOf(edges));
        }
    }
}
