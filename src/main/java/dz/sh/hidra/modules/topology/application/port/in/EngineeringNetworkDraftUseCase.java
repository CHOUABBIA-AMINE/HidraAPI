/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringNetworkDraftUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-20
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.port.in
 *
 * @Description : Defines validated metadata graph and survey draft commands.
 *
 */
package dz.sh.hidra.modules.topology.application.port.in;

import dz.sh.hidra.modules.topology.domain.model.EngineeringNetworkDraft;
import dz.sh.hidra.modules.topology.domain.model.EngineeringGraphDraft;
import dz.sh.hidra.modules.topology.domain.model.EngineeringNetworkDraft.Metadata;
import java.util.*;
public interface EngineeringNetworkDraftUseCase {
    enum Kind { CREATE, READ, UPDATE, VALIDATE, GRAPH_READ, GRAPH_UPDATE, SURVEY_READ, SURVEY_UPDATE }
    record Command(Kind kind,String companyPartyId,String pipelineSystemId,UUID draftId,
            UUID creationRequestId,Long expectedVersion,Metadata metadata,
            Long requestedVersion, EngineeringGraphDraft graph, String branchId,
            dz.sh.hidra.modules.topology.domain.model.SurveyRouteProfile survey) {
        public Command(Kind kind,String companyPartyId,String pipelineSystemId,UUID draftId,UUID creationRequestId,Long expectedVersion,Metadata metadata,Long requestedVersion,EngineeringGraphDraft graph){
            this(kind,companyPartyId,pipelineSystemId,draftId,creationRequestId,expectedVersion,metadata,requestedVersion,graph,null,null);
        }
        public Command(Kind kind,String companyPartyId,String pipelineSystemId,UUID draftId,
                UUID creationRequestId,Long expectedVersion,Metadata metadata){
            this(kind,companyPartyId,pipelineSystemId,draftId,creationRequestId,expectedVersion,metadata,null,null,null,null);
        }
        public Command {
            Objects.requireNonNull(kind);
            EngineeringNetworkDraft.identity(companyPartyId);
            EngineeringNetworkDraft.identity(pipelineSystemId);
            if(kind==Kind.SURVEY_READ || kind==Kind.SURVEY_UPDATE){
                new SurveyRouteProfileUseCase.Command(kind==Kind.SURVEY_READ?SurveyRouteProfileUseCase.Kind.READ:SurveyRouteProfileUseCase.Kind.UPDATE,
                    companyPartyId,pipelineSystemId,draftId,branchId,expectedVersion,requestedVersion,survey);
                if(metadata!=null || graph!=null || creationRequestId!=null)throw new IllegalArgumentException("Invalid survey fields");
            }else if(branchId!=null || survey!=null)throw new IllegalArgumentException("Survey fields on other operation");
            if(kind==Kind.CREATE){
                if(creationRequestId==null || metadata==null || draftId!=null || expectedVersion!=null
                    || requestedVersion!=null || graph!=null)throw new IllegalArgumentException("Invalid create");
            }else{
                if(draftId==null || creationRequestId!=null)throw new IllegalArgumentException("Draft identity required");
            }
            if((kind==Kind.UPDATE || kind==Kind.VALIDATE || kind==Kind.GRAPH_UPDATE || kind==Kind.SURVEY_UPDATE)
                && (expectedVersion==null || expectedVersion<1))throw new IllegalArgumentException("Expected version required");
            if(kind==Kind.UPDATE && metadata==null)throw new IllegalArgumentException("Metadata required");
            if(kind==Kind.GRAPH_UPDATE && (metadata!=null || requestedVersion!=null || graph==null))
                throw new IllegalArgumentException("Graph update fields invalid");
            if(kind==Kind.GRAPH_READ && (metadata!=null || graph!=null || expectedVersion!=null
                || requestedVersion!=null && requestedVersion<1))
                throw new IllegalArgumentException("Graph read fields invalid");
            if(kind!=Kind.GRAPH_READ && kind!=Kind.GRAPH_UPDATE && kind!=Kind.SURVEY_READ && kind!=Kind.SURVEY_UPDATE && (graph!=null || requestedVersion!=null))
                throw new IllegalArgumentException("Graph fields on metadata command");
            if((kind==Kind.READ || kind==Kind.VALIDATE) && metadata!=null)
                throw new IllegalArgumentException("Read/validate metadata not allowed");
        }
    }
    record Result(EngineeringNetworkDraft draft,boolean replayed,List<String> diagnostics){
        public Result {Objects.requireNonNull(draft);diagnostics=List.copyOf(diagnostics);}
    }
    Result execute(Command command);
}
