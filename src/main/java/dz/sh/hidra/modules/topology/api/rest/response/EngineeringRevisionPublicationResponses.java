/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringRevisionPublicationResponses
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-20
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.response
 *
 * @Description : Maps governed ledger results to immutable transport-owned unqualified snapshot responses.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.response;

import dz.sh.hidra.modules.topology.application.port.in.EngineeringRevisionPublicationUseCase;
import java.time.Instant;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.UUID;

public final class EngineeringRevisionPublicationResponses {
    private EngineeringRevisionPublicationResponses(){}
    @Schema(name="EngineeringPublicationComponentResponse")
    public record Component(long version,String format,String sha256,String base64) {}
    @Schema(name="EngineeringPublicationSurveyResponse")
    public record Survey(String branchId,Component source,Long graphVersion,String graphSha256,List<String> diagnostics) {
        public Survey {diagnostics=List.copyOf(diagnostics);}
    }
    @Schema(name="EngineeringPublicationSnapshotResponse")
    public record Snapshot(UUID revisionId,UUID contextId,String companyPartyId,String pipelineSystemId,
            UUID draftId,long version,String format,boolean engineeringQualified,Component metadata,Component graph,
            List<Survey> surveys,List<String> diagnostics) {
        public Snapshot {surveys=List.copyOf(surveys);diagnostics=List.copyOf(diagnostics);if(engineeringQualified)throw new IllegalArgumentException();}
    }
    @Schema(name="EngineeringPublicationHeadResponse")
    public record Head(UUID revisionId,long epoch,String state,UUID eventId,Instant effectiveAt) {}
    @Schema(name="EngineeringPublicationEventResponse")
    public record Event(UUID eventId,long epoch,String action,UUID priorRevisionId,UUID revisionId,String state,
            UUID requestId,String commandSha256,Instant effectiveAt) {}
    @Schema(name="EngineeringPublicationReceiptResponse")
    public record Receipt(UUID requestId,String commandSha256,Event outcome) {}
    @Schema(name="EngineeringPublicationPublicationResponse")
    public record Publication(Head head,Snapshot snapshot,List<Event> events,Receipt receipt,String nextCursor,boolean replayed) {
        public Publication {events=List.copyOf(events);}
    }
    private static Component component(dz.sh.hidra.modules.topology.domain.model.EngineeringRevisionPublication.Component value) {
        return value==null?null:new Component(value.version(),value.format(),value.sha256(),value.base64());
    }
    private static Event event(dz.sh.hidra.modules.topology.domain.model.EngineeringRevisionPublication.Event value) {
        return new Event(value.eventId(),value.epoch(),value.action().name(),value.priorRevisionId(),value.revisionId(),
                value.state().name(),value.requestId(),value.commandSha256(),value.effectiveAt());
    }
    public static Publication from(EngineeringRevisionPublicationUseCase.Result result) {
        var h=result.observedHead();var p=result.publication();var r=result.receipt();
        Head head=h==null?null:new Head(h.revisionId(),h.epoch(),h.state().name(),h.eventId(),h.effectiveAt());
        Snapshot snapshot=null;
        if(p!=null) {
            var s=p.snapshot();var scope=s.scope();
            snapshot=new Snapshot(p.revisionId(),scope.contextId(),scope.companyPartyId(),scope.pipelineSystemId(),
                s.draftId(),s.version(),"HIDRA_ENGINEERING_PUBLICATION_V1",false,component(s.metadata()),component(s.graph()),
                s.surveys().stream().map(v->new Survey(v.branchId(),component(v.source()),v.graphVersion(),v.graphSha256(),v.diagnostics())).toList(),s.diagnostics());
        }
        return new Publication(head,snapshot,result.events().stream().map(EngineeringRevisionPublicationResponses::event).toList(),
                r==null?null:new Receipt(r.requestId(),r.commandSha256(),event(r.outcome())),result.nextCursor(),result.replayed());
    }
}
