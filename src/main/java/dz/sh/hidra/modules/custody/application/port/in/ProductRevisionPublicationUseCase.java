/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProductRevisionPublicationUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-28
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.application.port.in
 *
 * @Description : Preserves governed product publication evidence under exact owner admission.
 *
 */
package dz.sh.hidra.modules.custody.application.port.in;
import dz.sh.hidra.modules.custody.domain.model.ProductRevisionPublication.*;
import dz.sh.hidra.modules.custody.domain.model.ProductRevisionPublication;
import java.time.Instant;
import java.util.*;
public interface ProductRevisionPublicationUseCase {
    enum Kind { PREPARE,PUBLISH,WITHDRAW,REACTIVATE,READ_CANDIDATE,READ_CANDIDATE_FOR_WORKFLOW,READ_EXACT,READ_CURRENT,READ_EFFECTIVE,READ_HISTORY,READ_RECEIPT,ELIGIBLE_CURRENT,ELIGIBLE_EFFECTIVE }
    record Selection(String instanceId,String taskId,String actionId) {
        public Selection {ProductRevisionPublication.identity(instanceId);ProductRevisionPublication.identity(taskId);ProductRevisionPublication.identity(actionId);}
    }
    record Command(Kind kind,UUID productDraftId,String companyPartyId,UUID requestId,long metadataVersion,long suppliedVersion,
            String candidateDigest,UUID publicationId,long expectedEpoch,UUID expectedPublicationId,Selection approval,
            Instant asOf,int limit,String cursor) {
        public Command {
            Objects.requireNonNull(kind);boolean lookup=kind==Kind.READ_CANDIDATE_FOR_WORKFLOW;
            if(lookup){if(productDraftId!=null || companyPartyId!=null)throw new Failure(Reason.INVALID_REQUEST);}
            else {Objects.requireNonNull(productDraftId);ProductRevisionPublication.identity(companyPartyId);}
            if(Set.of(Kind.PREPARE,Kind.PUBLISH,Kind.WITHDRAW,Kind.REACTIVATE,Kind.READ_RECEIPT).contains(kind))Objects.requireNonNull(requestId);
            if(kind==Kind.PREPARE && (metadataVersion<1 || suppliedVersion<1))throw new Failure(Reason.INVALID_REQUEST);
            if(Set.of(Kind.PUBLISH,Kind.READ_CANDIDATE,Kind.READ_CANDIDATE_FOR_WORKFLOW).contains(kind))ProductRevisionPublication.digest(candidateDigest);
            if(Set.of(Kind.WITHDRAW,Kind.REACTIVATE,Kind.READ_EXACT).contains(kind))Objects.requireNonNull(publicationId);
            if(Set.of(Kind.PUBLISH,Kind.REACTIVATE).contains(kind))Objects.requireNonNull(approval);
            if(Set.of(Kind.PUBLISH,Kind.WITHDRAW,Kind.REACTIVATE).contains(kind) && (expectedEpoch<0 || (expectedEpoch==0)!=(expectedPublicationId==null)))throw new Failure(Reason.INVALID_REQUEST);
            if(Set.of(Kind.READ_EFFECTIVE,Kind.ELIGIBLE_EFFECTIVE,Kind.READ_HISTORY).contains(kind))ProductRevisionPublication.microseconds(asOf);
            if(kind==Kind.READ_HISTORY && (limit<1 || limit>100 || cursor!=null && cursor.length()>4096))throw new Failure(Reason.INVALID_REQUEST);
        }
        public boolean mutating(){return Set.of(Kind.PREPARE,Kind.PUBLISH,Kind.WITHDRAW,Kind.REACTIVATE).contains(kind);}
        public static Command workflow(String digest){return new Command(Kind.READ_CANDIDATE_FOR_WORKFLOW,null,null,null,0,0,digest,null,0,null,null,null,0,null);}
    }
    record Result(Envelope candidate,String candidateDigest,ProductRevisionPublication publication,Head observedHead,
            Head selectedHead,Receipt receipt,List<Event> history,String nextCursor,boolean replayed,boolean consumerEligible) {
        public Result {history=List.copyOf(history);}
    }
    enum Reason { INVALID_REQUEST,DENIED,CONFLICT,UNAVAILABLE,INDETERMINATE }
    final class Failure extends RuntimeException {
        private final Reason reason;
        public Failure(Reason reason){super("Product publication "+reason);this.reason=reason;}
        public Failure(Reason reason,Throwable cause){super("Product publication "+reason,cause);this.reason=reason;}
        public Reason reason(){return reason;}
    }
    Result execute(Command command);
    /** Suspends ordinary Workflow work only after rejecting active protected execution. */
    Optional<String> candidateForWorkflow(String digest);
}
