/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthoredTopologyResponses
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-04
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.response
 *
 * @Description : Owns the bounded canonical Pipeline and persisted graph association contract.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.response;

import dz.sh.hidra.modules.topology.application.port.in.AuthoredTopologyViewUseCase;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
public final class AuthoredTopologyResponses {
    private AuthoredTopologyResponses() {}
    @Schema(name="AuthoredTopologyPipeline")
    public record Pipeline(String id,long revision,String companyPartyId,String pipelineSystemId,String code,String pipelineTypeId,String nameAr,String nameFr,String nameEn) {
        public static Pipeline from(AuthoredTopologyViewUseCase.PipelineView p){return new Pipeline(p.id(),p.revision(),p.companyPartyId(),p.pipelineSystemId(),p.code(),p.pipelineTypeId(),p.nameAr(),p.nameFr(),p.nameEn());}
    }
    @Schema(name="AuthoredTopologyBinding")
    public record Binding(String branchId,Pipeline pipeline,String segmentId) {}
    @Schema(name="AuthoredTopologyView")
    public record View(String companyPartyId,String pipelineSystemId,EngineeringGraphDraftResponses.Graph graph,List<Binding> bindings,List<String> diagnostics,String geometryStatus) {
        public static View from(AuthoredTopologyViewUseCase.View v){return new View(v.companyPartyId(),v.pipelineSystemId(),EngineeringGraphDraftResponses.Graph.from(v.graph()),v.bindings().stream().map(b->new Binding(b.branchId(),Pipeline.from(b.pipeline()),b.segmentId())).toList(),v.diagnostics(),v.geometryStatus());}
    }
    @Schema(name="AuthoredTopologyChoice")
    public record Choice(String id,String code,String name,String kind) {public static Choice from(AuthoredTopologyViewUseCase.Choice c){return new Choice(c.id(),c.code(),c.name(),c.kind());}}
    @Schema(name="AuthoredTopologyDiscovery")
    public record Discovery(String companyPartyId,String pipelineSystemId,List<Pipeline> pipelines,long totalPipelines,int offset,int limit,List<Choice> facilities,List<Choice> pipelineTypes,List<Choice> segments) {
        public static Discovery from(AuthoredTopologyViewUseCase.Discovery d){return new Discovery(d.companyPartyId(),d.pipelineSystemId(),d.pipelines().stream().map(Pipeline::from).toList(),d.totalPipelines(),d.offset(),d.limit(),d.facilities().stream().map(Choice::from).toList(),d.pipelineTypes().stream().map(Choice::from).toList(),d.segments().stream().map(Choice::from).toList());}
    }
}
