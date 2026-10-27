/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProductEngineeringDraftUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-27
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.application.port.in
 *
 * @Description : Provides company-owned catalog-linked product metadata drafts.
 *
 */
package dz.sh.hidra.modules.custody.application.port.in;

import dz.sh.hidra.modules.custody.domain.model.ProductEngineeringDraft;
import dz.sh.hidra.modules.custody.domain.model.ProductEngineeringDraft.Metadata;
import java.util.*;

public interface ProductEngineeringDraftUseCase {
    enum Kind { CREATE, READ, UPDATE }
    record Command(Kind kind,String companyPartyId,UUID draftId,String localCode,String catalogProductId,
            UUID creationRequestId,Long expectedVersion,Metadata metadata) {
        public Command {
            Objects.requireNonNull(kind); ProductEngineeringDraft.text(companyPartyId,80,true);
            if(kind==Kind.CREATE) {
                ProductEngineeringDraft.text(localCode,80,true); ProductEngineeringDraft.text(catalogProductId,80,true);
                Objects.requireNonNull(creationRequestId); Objects.requireNonNull(metadata);
                if(draftId!=null || expectedVersion!=null) throw new IllegalArgumentException("Invalid create shape");
            } else {
                Objects.requireNonNull(draftId);
                if(localCode!=null || catalogProductId!=null || creationRequestId!=null)
                    throw new IllegalArgumentException("Immutable identity cannot be supplied");
                if(kind==Kind.READ) {
                    if(expectedVersion!=null || metadata!=null) throw new IllegalArgumentException("Invalid read shape");
                } else if(expectedVersion==null || expectedVersion<=0 || metadata==null)
                    throw new IllegalArgumentException("Expected version and metadata required");
            }
        }
    }
    record Result(ProductEngineeringDraft draft,boolean replayed) {
        public Result {Objects.requireNonNull(draft);}
        public List<String> diagnostics(){return draft.metadata().diagnostics();}
    }
    enum Reason { CONFLICT, DENIED, UNAVAILABLE, INDETERMINATE }
    final class Failure extends RuntimeException {
        private final Reason reason;
        public Failure(Reason reason){super("Product draft "+Objects.requireNonNull(reason));this.reason=reason;}
        public Failure(Reason reason,Throwable cause){super("Product draft "+Objects.requireNonNull(reason),cause);this.reason=reason;}
        public Reason reason(){return reason;}
    }
    Result execute(Command command);
}
