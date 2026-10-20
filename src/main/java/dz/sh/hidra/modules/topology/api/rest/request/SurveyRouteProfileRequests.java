/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SurveyRouteProfileRequests
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-20
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.request
 *
 * @Description : Defines strict survey transport with explicit decimal strings and no domain types.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.request;

import java.util.*;
import java.math.BigDecimal;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.ValueDeserializer;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JsonNode;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DatabindException;
import io.swagger.v3.oas.annotations.media.Schema;
public final class SurveyRouteProfileRequests {
    private SurveyRouteProfileRequests(){}

    @Schema(name="SurveyRouteProfileAxisRequest")
    public record Axis(String name,String unitIdentifier) {}
    @Schema(name="SurveyRouteProfileCrsRequest")
    public record Crs(String authority,String code,List<Axis> axes) {public Crs{axes=axes==null?null:List.copyOf(axes);}}
    @Schema(name="SurveyRouteProfileVerticalReferenceRequest")
    public record VerticalReference(String datumAuthority,String datumCode,String unitIdentifier) {}
    @Schema(name="SurveyRouteProfilePointRequest")
    public record Point(String id,String firstCoordinate,String secondCoordinate,String chainageMetres,String elevation,
        String coordinatePrecision,String chainagePrecisionMetres,String elevationPrecision) {}
    @Schema(name="SurveyRouteProfileSectionRequest")
    public record Section(String id,String startChainageMetres,String endChainageMetres,String evidenceReference) {}
    @Schema(name="SurveyRouteProfileProfileRequest")
    public record Profile(String branchId,Crs horizontalCrs,VerticalReference verticalReference,String chainageOriginReference,
        String direction,String evidenceReference,List<Point> points,List<Section> sections) {
        public Profile{points=points==null?null:List.copyOf(points);sections=sections==null?null:List.copyOf(sections);}
    }

    @JsonDeserialize(using=StrictUpdate.class)
    @Schema(name="SurveyRouteProfileUpdateRequest")
    public record Update(Long expectedVersion,Profile profile) {}
    public static BigDecimal decimal(String value){
        if(value==null || value.isEmpty() || value.length()>4096 || !value.equals(value.strip()))throw new IllegalArgumentException("Explicit bounded decimal string required");
        return new BigDecimal(value);
    }
    public static final class StrictUpdate extends ValueDeserializer<Update> {
        private static void object(JsonParser p,JsonNode node,String... fields){
            if(node==null || !node.isObject())throw DatabindException.from(p,"Survey object required");
            var allowed=Set.of(fields);for(String key:node.propertyNames())if(!allowed.contains(key))throw DatabindException.from(p,"Unknown survey field");
        }
        private static String string(JsonParser p,JsonNode node,String field,boolean required){
            var v=node.get(field);if(v==null || v.isNull()){if(required)throw DatabindException.from(p,"Required survey string");return null;}
            if(!v.isString())throw DatabindException.from(p,"Survey string required");return v.stringValue();
        }
        private static String decimal(JsonParser p,JsonNode node,String field,boolean required){
            var s=string(p,node,field,required);if(s!=null)SurveyRouteProfileRequests.decimal(s);return s;
        }
        private static JsonNode array(JsonParser p,JsonNode node,String field,int max){
            var v=node.get(field);if(v==null || !v.isArray() || v.size()>max)throw DatabindException.from(p,"Bounded survey array required");return v;
        }
        @Override public Update deserialize(JsonParser p,DeserializationContext c){
            JsonNode root=c.readTree(p);object(p,root,"expectedVersion","profile");var v=root.get("expectedVersion");
            if(v==null || !v.isIntegralNumber())throw DatabindException.from(p,"Integral survey version required");
            long version;try{version=v.bigIntegerValue().longValueExact();}catch(ArithmeticException e){throw DatabindException.from(p,"Survey version range",e);}
            if(version<1)throw DatabindException.from(p,"Positive survey version required");
            var profile=root.get("profile");object(p,profile,"branchId","horizontalCrs","verticalReference","chainageOriginReference","direction","evidenceReference","points","sections");
            var crs=profile.get("horizontalCrs");object(p,crs,"authority","code","axes");var axes=new ArrayList<Axis>();
            for(var a:array(p,crs,"axes",2)){object(p,a,"name","unitIdentifier");axes.add(new Axis(string(p,a,"name",true),string(p,a,"unitIdentifier",true)));}
            VerticalReference vertical=null;var vr=profile.get("verticalReference");if(vr!=null && !vr.isNull()){
                object(p,vr,"datumAuthority","datumCode","unitIdentifier");vertical=new VerticalReference(string(p,vr,"datumAuthority",true),string(p,vr,"datumCode",true),string(p,vr,"unitIdentifier",true));
            }
            var points=new ArrayList<Point>();for(var a:array(p,profile,"points",10000)){
                object(p,a,"id","firstCoordinate","secondCoordinate","chainageMetres","elevation","coordinatePrecision","chainagePrecisionMetres","elevationPrecision");
                points.add(new Point(string(p,a,"id",true),decimal(p,a,"firstCoordinate",true),decimal(p,a,"secondCoordinate",true),decimal(p,a,"chainageMetres",true),decimal(p,a,"elevation",false),decimal(p,a,"coordinatePrecision",true),decimal(p,a,"chainagePrecisionMetres",true),decimal(p,a,"elevationPrecision",false)));
            }
            var sections=new ArrayList<Section>();for(var a:array(p,profile,"sections",10000)){
                object(p,a,"id","startChainageMetres","endChainageMetres","evidenceReference");sections.add(new Section(string(p,a,"id",true),decimal(p,a,"startChainageMetres",true),decimal(p,a,"endChainageMetres",true),string(p,a,"evidenceReference",true)));
            }
            return new Update(version,new Profile(string(p,profile,"branchId",true),new Crs(string(p,crs,"authority",true),string(p,crs,"code",true),axes),vertical,string(p,profile,"chainageOriginReference",true),string(p,profile,"direction",true),string(p,profile,"evidenceReference",true),points,sections));
        }
    }
}
