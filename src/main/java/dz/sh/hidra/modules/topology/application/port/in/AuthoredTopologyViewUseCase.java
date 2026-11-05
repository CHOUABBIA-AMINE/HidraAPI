/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : AuthoredTopologyViewUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-04
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.port.in
 *
 * @Description : Owns the bounded canonical Pipeline and persisted graph association contract.
 *
 */
package dz.sh.hidra.modules.topology.application.port.in;

import java.util.*;
import dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft;
public interface AuthoredTopologyViewUseCase {
    record PipelineView(String id,long revision,String companyPartyId,String pipelineSystemId,String code,String pipelineTypeId,String nameAr,String nameFr,String nameEn) {}
    record Binding(String branchId,PipelineView pipeline,String segmentId) {}
    record View(String companyPartyId,String pipelineSystemId,EngineeringGraphDraftUseCase.Result graph,List<Binding> bindings,List<String> diagnostics,String geometryStatus) {
        public View {Objects.requireNonNull(graph);bindings=List.copyOf(bindings);diagnostics=List.copyOf(diagnostics);if(!"NOT_SUPPLIED".equals(geometryStatus))throw new IllegalArgumentException("Only persisted schematic projection is admitted");}
    }
    record Choice(String id,String code,String name,String kind) {}
    record Discovery(String companyPartyId,String pipelineSystemId,List<PipelineView> pipelines,long totalPipelines,int offset,int limit,List<Choice> facilities,List<Choice> pipelineTypes,List<Choice> segments,List<Choice> equipmentKinds,List<Choice> installedUnits,List<String> candidateInstalledUnitIds,Integer nextOffset) {
        public Discovery(String companyPartyId,String pipelineSystemId,List<PipelineView> pipelines,long totalPipelines,int offset,int limit,List<Choice> facilities,List<Choice> pipelineTypes,List<Choice> segments,List<Choice> equipmentKinds,List<Choice> installedUnits,List<String> candidates){this(companyPartyId,pipelineSystemId,pipelines,totalPipelines,offset,limit,facilities,pipelineTypes,segments,equipmentKinds,installedUnits,candidates,offset+limit<=100000 && (offset+pipelines.size()<totalPipelines || facilities.size()==limit || pipelineTypes.size()==limit || segments.size()==limit || equipmentKinds.size()==limit || candidates.size()==limit)?offset+limit:null);}
        public Discovery {pipelines=List.copyOf(pipelines);facilities=List.copyOf(facilities);pipelineTypes=List.copyOf(pipelineTypes);segments=List.copyOf(segments);equipmentKinds=List.copyOf(equipmentKinds);installedUnits=List.copyOf(installedUnits);candidateInstalledUnitIds=List.copyOf(candidateInstalledUnitIds);}
    }
    void requireGlobalMapAccess();
    View view(PipelineAuthoringUseCase.Scope scope,Long version);
    Discovery discover(PipelineAuthoringUseCase.Scope scope,int offset,int limit);
}
