/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SurveyRouteProfileResponses
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-20
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.response
 *
 * @Description : Describes draft-only survey output with explicit snapshot provenance.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.response;

import java.util.List;
import java.util.UUID;
import io.swagger.v3.oas.annotations.media.Schema;
public final class SurveyRouteProfileResponses {
    private SurveyRouteProfileResponses(){}

    @Schema(name="SurveyRouteProfileAxisResponse")
    public record Axis(String name,String unitIdentifier) {}
    @Schema(name="SurveyRouteProfileCrsResponse")
    public record Crs(String authority,String code,List<Axis> axes) {public Crs{axes=axes==null?null:List.copyOf(axes);}}
    @Schema(name="SurveyRouteProfileVerticalReferenceResponse")
    public record VerticalReference(String datumAuthority,String datumCode,String unitIdentifier) {}
    @Schema(name="SurveyRouteProfilePointResponse")
    public record Point(String id,String firstCoordinate,String secondCoordinate,String chainageMetres,String elevation,
        String coordinatePrecision,String chainagePrecisionMetres,String elevationPrecision) {}
    @Schema(name="SurveyRouteProfileSectionResponse")
    public record Section(String id,String startChainageMetres,String endChainageMetres,String evidenceReference) {}
    @Schema(name="SurveyRouteProfileProfileResponse")
    public record Profile(String branchId,Crs horizontalCrs,VerticalReference verticalReference,String chainageOriginReference,
        String direction,String evidenceReference,List<Point> points,List<Section> sections) {
        public Profile{points=points==null?null:List.copyOf(points);sections=sections==null?null:List.copyOf(sections);}
    }

    @Schema(name="SurveyRouteProfileResponse")
    public record Survey(UUID draftId,String branchId,long version,Long surveyVersion,String surveySha256,
        Long boundGraphVersion,String boundGraphSha256,Profile profile,String state,boolean engineeringComplete,List<String> diagnostics){
        public Survey{diagnostics=List.copyOf(diagnostics);}
    }
}
