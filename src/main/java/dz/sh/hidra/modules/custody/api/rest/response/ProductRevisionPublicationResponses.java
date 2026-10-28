/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProductRevisionPublicationResponses
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-27
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.api.rest.response
 *
 * @Description : Provides strict protected supplied-fluid draft HTTP contracts.
 *
 */
package dz.sh.hidra.modules.custody.api.rest.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.*;

public final class ProductRevisionPublicationResponses {
    private ProductRevisionPublicationResponses() {}
    @JsonInclude(JsonInclude.Include.ALWAYS) @Schema(name="ProductPublicationScopeResponse")
    public record Scope(UUID productDraftId,String companyPartyId,String catalogProductId) {}
    @JsonInclude(JsonInclude.Include.ALWAYS) @Schema(name="ProductPublicationComponentResponse")
    public record Component(long version,String format,String sha256,String base64) {}
    @JsonInclude(JsonInclude.Include.ALWAYS) @Schema(name="ProductPublicationPolicyResponse")
    public record Policy(String definitionId,int definitionVersion,String targetTypeId,String purposeId) {}
    @JsonInclude(JsonInclude.Include.ALWAYS) @Schema(name="ProductPublicationUnitResponse")
    public record Unit(String unitId,String role,String code,String symbol,String dimension,Instant updatedAt) {}
    @JsonInclude(JsonInclude.Include.ALWAYS) @Schema(name="ProductPublicationEnvelopeResponse")
    public record Envelope(Scope scope,Component metadata,Component supplied,long suppliedMetadataVersion,String classification,List<String> diagnostics,List<Unit> units,Policy policy,boolean engineeringQualified) {
        public Envelope {diagnostics=List.copyOf(diagnostics);units=List.copyOf(units);if(engineeringQualified)throw new IllegalArgumentException("Unqualified source required");}
    }
    @JsonInclude(JsonInclude.Include.ALWAYS) @Schema(name="ProductPublicationApprovalResponse")
    public record Approval(String digest,String instanceId,String taskId,String actionId,String historyId,String actorId,String actorDisplayName,Instant actedAt) {}
    @JsonInclude(JsonInclude.Include.ALWAYS) @Schema(name="ProductPublicationPublicationResponse")
    public record Publication(UUID publicationId,Envelope envelope,Approval approval,boolean engineeringQualified) {}
    @JsonInclude(JsonInclude.Include.ALWAYS) @Schema(name="ProductPublicationHeadResponse")
    public record Head(Scope scope,UUID publicationId,long epoch,String state,UUID eventId,Instant effectiveAt) {}
    @JsonInclude(JsonInclude.Include.ALWAYS) @Schema(name="ProductPublicationEventResponse")
    public record Event(UUID eventId,Scope scope,long epoch,String action,UUID priorPublicationId,UUID publicationId,String state,UUID requestId,String commandSha256,Instant effectiveAt,Approval approval) {}
    @JsonInclude(JsonInclude.Include.ALWAYS) @Schema(name="ProductPublicationReceiptResponse")
    public record Receipt(Scope scope,String actorId,UUID requestId,String commandSha256,String kind,String candidateDigest,Event event,Instant recordedAt) {}
    @JsonInclude(JsonInclude.Include.ALWAYS) @Schema(name="ProductPublicationResultResponse",description="Immutable unqualified source bytes and historical head facts. Current HTTP state is not an eligibility certificate.")
    public record Result(Envelope candidate,String candidateDigest,Publication publication,Head observedHead,Head selectedHead,Receipt receipt,List<Event> history,String nextCursor,boolean replayed) {public Result {history=List.copyOf(history);}}
}
