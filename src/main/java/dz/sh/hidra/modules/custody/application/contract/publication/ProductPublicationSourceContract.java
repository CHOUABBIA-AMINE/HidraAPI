/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProductPublicationSourceContract
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-27
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.application.contract.publication
 *
 * @Description : Provides strict protected supplied-fluid draft HTTP contracts.
 *
 */
package dz.sh.hidra.modules.custody.application.contract.publication;

import java.time.Instant;
import java.util.*;

/** Original authoring facts; publication is never engineering qualification. No importer authority input. */
public interface ProductPublicationSourceContract {
    enum Mode { EXACT_HISTORICAL, ELIGIBLE_CURRENT, ELIGIBLE_EFFECTIVE }
    record Query(Mode mode,String companyPartyId,UUID productDraftId,UUID publicationId,Instant asOf) {
        public Query {Objects.requireNonNull(mode);Objects.requireNonNull(productDraftId);if(companyPartyId==null || companyPartyId.isBlank() || companyPartyId.codePointCount(0,companyPartyId.length())>80 || !companyPartyId.equals(companyPartyId.strip()))throw new IllegalArgumentException("Invalid publication scope");
            if(mode==Mode.EXACT_HISTORICAL && publicationId==null || mode!=Mode.EXACT_HISTORICAL && publicationId!=null || mode==Mode.ELIGIBLE_EFFECTIVE && (asOf==null || asOf.getNano()%1000!=0) || mode!=Mode.ELIGIBLE_EFFECTIVE && asOf!=null)throw new IllegalArgumentException("Invalid publication selection");}
    }
    public record Scope(UUID productDraftId,String companyPartyId,String catalogProductId) {}
    public record Component(long version,String format,String sha256,String base64) {}
    public record Policy(String definitionId,int definitionVersion,String targetTypeId,String purposeId) {}
    public record Unit(String unitId,String role,String code,String symbol,String dimension,Instant updatedAt) {}
    public record Envelope(Scope scope,Component metadata,Component supplied,long suppliedMetadataVersion,String classification,List<String> diagnostics,List<Unit> units,Policy policy,boolean engineeringQualified) {
        public Envelope {diagnostics=List.copyOf(diagnostics);units=List.copyOf(units);if(engineeringQualified)throw new IllegalArgumentException("Unqualified source required");}
    }
    public record Approval(String digest,String instanceId,String taskId,String actionId,String historyId,String actorId,String actorDisplayName,Instant actedAt) {}
    public record Publication(UUID publicationId,Envelope envelope,Approval approval,boolean engineeringQualified) {}
    public record Head(Scope scope,UUID publicationId,long epoch,String state,UUID eventId,Instant effectiveAt) {}
    public record Event(UUID eventId,Scope scope,long epoch,String action,UUID priorPublicationId,UUID publicationId,String state,UUID requestId,String commandSha256,Instant effectiveAt,Approval approval) {}
    public record Receipt(Scope scope,String actorId,UUID requestId,String commandSha256,String kind,String candidateDigest,Event event,Instant recordedAt) {}
    record Metadata(String name,String description,String evidenceReference,String classification) {}
    record ScalarFact(String path,String value) {public ScalarFact {Objects.requireNonNull(path);}}
    record Source(Envelope envelope,UUID publicationId,Approval approval,Head observedHead,Head selectedHead,boolean currentlyEligible,Metadata authoredMetadata,List<ScalarFact> authoredFacts,boolean engineeringQualified) {
        public Source {Objects.requireNonNull(envelope);authoredFacts=List.copyOf(authoredFacts);if(engineeringQualified)throw new IllegalArgumentException("Unqualified source required");}
    }
    Optional<Source> read(Query query);
}
