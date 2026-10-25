/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringLocalExchangeApplicationService
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-25
 *
 * @Type        : Class
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.service
 *
 * @Description : Implements bounded local editor exchange through existing protected owners.
 *
 */
package dz.sh.hidra.modules.topology.application.service;

import dz.sh.hidra.modules.topology.domain.model.*;
import dz.sh.hidra.modules.topology.domain.model.EngineeringLocalExchange.Editor;
import dz.sh.hidra.modules.topology.application.port.in.*;
import dz.sh.hidra.modules.topology.application.port.out.EngineeringLocalExchangeCodecPort;
import dz.sh.hidra.modules.topology.application.port.out.EngineeringLocalExchangeCodecPort.*;
import dz.sh.hidra.modules.topology.application.model.EngineeringLocalExchangeFailure;
import dz.sh.hidra.modules.topology.application.model.EngineeringLocalExchangeFailure.Reason;
import java.util.*;
import org.springframework.stereotype.Service;

/** No outer transaction or new persistence owner: only one existing UPDATE may write. */
@Service
public final class EngineeringLocalExchangeApplicationService implements EngineeringLocalExchangeUseCase {
    private final EngineeringNetworkDraftUseCase metadata;
    private final EngineeringGraphDraftUseCase graphs;
    private final SurveyRouteProfileUseCase surveys;
    private final EngineeringLocalExchangeCodecPort codec;
    public EngineeringLocalExchangeApplicationService(EngineeringNetworkDraftUseCase metadata,
            EngineeringGraphDraftUseCase graphs,SurveyRouteProfileUseCase surveys,EngineeringLocalExchangeCodecPort codec){
        this.metadata=Objects.requireNonNull(metadata);this.graphs=Objects.requireNonNull(graphs);
        this.surveys=Objects.requireNonNull(surveys);this.codec=Objects.requireNonNull(codec);
    }
    @Override public Result execute(Command c){
        Objects.requireNonNull(c);
        Content proposed=c.bundle()==null?null:codec.decode(c.bundle());
        var base=metadata.execute(new EngineeringNetworkDraftUseCase.Command(EngineeringNetworkDraftUseCase.Kind.READ,
                c.companyPartyId(),c.pipelineSystemId(),c.draftId(),null,null,null)).draft();
        long observed=base.version();
        if(!base.companyPartyId().equals(c.companyPartyId()) || !base.pipelineSystemId().equals(c.pipelineSystemId())
                || !base.draftId().equals(c.draftId()))throw conflict();
        if(c.expectedVersion()!=null && c.expectedVersion()!=observed)throw conflict();
        if(c.bundle()!=null){var p=c.bundle();
            if(!p.companyPartyId().equals(c.companyPartyId()) || !p.pipelineSystemId().equals(c.pipelineSystemId())
                    || !p.contextId().equals(base.contextId()) || !p.draftId().equals(c.draftId())
                    || !Objects.equals(p.branchId(),c.branchId()) || p.sourceVersion()>observed)throw conflict();
        }
        Long selection=c.operation()==Operation.EXPORT?c.requestedVersion():null;
        EngineeringGraphDraftUseCase.Result graph=null;SurveyRouteProfileUseCase.Result survey=null;
        if(c.editor()!=Editor.METADATA){
            graph=graphs.executeGraph(new EngineeringGraphDraftUseCase.Command(EngineeringGraphDraftUseCase.Kind.READ,
                    c.companyPartyId(),c.pipelineSystemId(),c.draftId(),null,selection,null));
            if(graph.version()!=(selection==null?observed:selection))throw conflict();
        }
        if(c.editor()==Editor.SURVEY){
            if(c.operation()==Operation.EXPORT || c.operation()==Operation.CAPABILITIES)
            survey=surveys.executeSurvey(new SurveyRouteProfileUseCase.Command(SurveyRouteProfileUseCase.Kind.READ,
                    c.companyPartyId(),c.pipelineSystemId(),c.draftId(),c.branchId(),null,selection,null));
            if(survey!=null && (survey.version()!=graph.version() || survey.profile()!=null &&
                    (!Objects.equals(survey.boundGraphVersion(),graph.graphVersion())
                    || !Objects.equals(survey.boundGraphSha256(),graph.graphSha256()))))throw conflict();
            if(c.bundle()!=null && (graph.graph()==null || !Objects.equals(c.bundle().boundGraphSha256(),graph.graphSha256())
                    || graph.graph().branches().stream().noneMatch(b->b.id().equals(c.branchId()) && b.kind()==EngineeringGraphDraft.BranchKind.PIPE)))throw conflict();
        }
        boolean authored=c.editor()==Editor.METADATA || c.editor()==Editor.GRAPH && graph.graph()!=null
                || c.editor()==Editor.SURVEY && survey!=null && survey.profile()!=null;
        List<String> diagnostics=switch(c.editor()){
            case METADATA -> metadataDiagnostics(base.metadata());case GRAPH -> graph.diagnostics();case SURVEY -> survey==null?List.of("PHYSICAL_QUALIFICATION_NOT_ESTABLISHED"):survey.diagnostics();};
        Long bound=survey==null?(c.editor()==Editor.SURVEY?graph.graphVersion():null):survey.boundGraphVersion();
        String boundSha=survey==null?(c.editor()==Editor.SURVEY?graph.graphSha256():null):survey.boundGraphSha256();
        if(c.operation()==Operation.CAPABILITIES)
            return new Result(c.operation(),c.editor(),observed,null,null,diagnostics,authored?"AUTHORED":"NOT_AUTHORED",bound,boundSha);
        if(c.operation()==Operation.EXPORT){
            if(!authored)throw new EngineeringLocalExchangeFailure(Reason.NOT_AUTHORED);
            long version=c.editor()==Editor.METADATA?observed:graph.version();
            long component=c.editor()==Editor.METADATA?observed:c.editor()==Editor.GRAPH?graph.graphVersion():survey.surveyVersion();
            Content content=switch(c.editor()){
                case METADATA -> new Content(base.metadata(),null,null);case GRAPH -> new Content(null,graph.graph(),null);
                case SURVEY -> new Content(null,null,survey.profile());};
            var pack=codec.encode(new Origin(c.editor(),c.companyPartyId(),c.pipelineSystemId(),base.contextId(),c.draftId(),
                    version,component,c.branchId(),bound,boundSha),content);
            return new Result(c.operation(),c.editor(),version,null,pack,diagnostics,"AUTHORED",bound,boundSha);
        }
        List<String> proposedDiagnostics=switch(c.editor()){
            case METADATA -> metadataDiagnostics(proposed.metadata());case GRAPH -> proposed.graph().diagnostics();
            case SURVEY -> List.of("PHYSICAL_QUALIFICATION_NOT_ESTABLISHED");};
        if(c.operation()==Operation.DRY_RUN){
            var advisory=new ArrayList<>(proposedDiagnostics);advisory.add("DRY_RUN_ADVISORY");advisory.add("WRITE_AUTHORIZATION_NOT_EVALUATED");
            if(c.editor()==Editor.GRAPH)advisory.add("PROPOSED_STATION_ELIGIBILITY_RECHECKED_ON_IMPORT");
            return new Result(c.operation(),c.editor(),observed,null,null,advisory,"STRUCTURALLY_VALID",bound,boundSha);
        }
        long target;
        switch(c.editor()){
            case METADATA -> target=metadata.execute(new EngineeringNetworkDraftUseCase.Command(EngineeringNetworkDraftUseCase.Kind.UPDATE,
                    c.companyPartyId(),c.pipelineSystemId(),c.draftId(),null,c.expectedVersion(),proposed.metadata())).draft().version();
            case GRAPH -> target=graphs.executeGraph(new EngineeringGraphDraftUseCase.Command(EngineeringGraphDraftUseCase.Kind.UPDATE,
                    c.companyPartyId(),c.pipelineSystemId(),c.draftId(),c.expectedVersion(),null,proposed.graph())).version();
            case SURVEY -> {
                var updated=surveys.executeSurvey(new SurveyRouteProfileUseCase.Command(SurveyRouteProfileUseCase.Kind.UPDATE,
                        c.companyPartyId(),c.pipelineSystemId(),c.draftId(),c.branchId(),c.expectedVersion(),null,proposed.survey()));
                target=updated.version();bound=updated.boundGraphVersion();boundSha=updated.boundGraphSha256();
            }
            default -> throw new IllegalArgumentException("Unsupported editor");
        }
        return new Result(c.operation(),c.editor(),observed,target,c.bundle(),proposedDiagnostics,"IMPORTED",bound,boundSha);
    }
    private static List<String> metadataDiagnostics(EngineeringNetworkDraft.Metadata m){
        var d=new ArrayList<String>();if(m.title()==null)d.add("TITLE_NOT_SUPPLIED");
        if(m.evidenceReference()==null)d.add("EVIDENCE_NOT_SUPPLIED");return List.copyOf(d);
    }
    private static EngineeringLocalExchangeFailure conflict(){return new EngineeringLocalExchangeFailure(Reason.CONFLICT);}
}
