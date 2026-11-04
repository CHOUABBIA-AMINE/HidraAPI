/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : PipelineAuthoringUseCase
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

import dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft;
import dz.sh.hidra.modules.topology.domain.model.EngineeringNetworkDraft;
import java.util.*;
public interface PipelineAuthoringUseCase {
    record Scope(String companyPartyId,String pipelineSystemId,UUID draftId) {
        public Scope {EngineeringNetworkDraft.identity(companyPartyId);EngineeringNetworkDraft.identity(pipelineSystemId);Objects.requireNonNull(draftId);}
    }
    record PipelineEdit(String id,long expectedRevision,String code,String pipelineTypeId,String nameAr,String nameFr,String nameEn) {
        public PipelineEdit {
            EngineeringGraphDraft.identity(id);EngineeringGraphDraft.identity(pipelineTypeId);
            if(expectedRevision<0||expectedRevision==Long.MAX_VALUE||code==null||code.isBlank()||code.length()>120||!code.equals(code.strip()))throw new IllegalArgumentException("Exact Pipeline identity and expected revision required");
            for(var name:new String[]{nameAr,nameFr,nameEn})if(name!=null&&name.length()>255)throw new IllegalArgumentException("Pipeline label limit");
        }
    }
    record Write(Scope scope,long expectedVersion,UUID requestId,PipelineEdit pipeline,EngineeringGraphDraft graph,List<EngineeringGraphDraft.PipelineReference> references) {
        public Write {Objects.requireNonNull(scope);Objects.requireNonNull(requestId);Objects.requireNonNull(graph);if(expectedVersion<1||expectedVersion==Long.MAX_VALUE)throw new IllegalArgumentException("Expected draft version required");references=graph.pipelineReferences(references);}
    }
    AuthoredTopologyViewUseCase.View write(Write command);
}
