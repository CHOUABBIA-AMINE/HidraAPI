/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : SuppliedFluidPropertyRevisionUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-27
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.application.port.in
 *
 * @Description : Preserves exact authored fluid facts independently of numerical qualification.
 *
 */
package dz.sh.hidra.modules.custody.application.port.in;
import dz.sh.hidra.modules.custody.domain.model.SuppliedFluidPropertyRevision;
import dz.sh.hidra.modules.custody.domain.model.SuppliedFluidPropertyRevision.Facts;
import java.util.*;
public interface SuppliedFluidPropertyRevisionUseCase {
    enum Kind { READ, WRITE }
    record Command(Kind kind,String companyPartyId,UUID productDraftId,UUID requestId,Long expectedVersion,Long expectedProductMetadataVersion,Facts facts) {
        public Command {Objects.requireNonNull(kind);SuppliedFluidPropertyRevision.text(companyPartyId,80,true);Objects.requireNonNull(productDraftId);
            if(kind==Kind.READ){if(requestId!=null || expectedVersion!=null || expectedProductMetadataVersion!=null || facts!=null)throw new IllegalArgumentException("Invalid read shape");}
            else if(requestId==null || expectedVersion==null || expectedVersion<0 || expectedVersion==Long.MAX_VALUE || expectedProductMetadataVersion==null || expectedProductMetadataVersion<=0 || facts==null)throw new IllegalArgumentException("Explicit expected versions and facts required");}
    }
    record Result(SuppliedFluidPropertyRevision draft,boolean replayed,boolean metadataBindingCurrent) {
        public Result {Objects.requireNonNull(draft);}
        public List<String> diagnostics(){var d=new ArrayList<>(draft.facts().diagnostics());if(!metadataBindingCurrent)d.add("METADATA_BINDING_STALE");return List.copyOf(d);}
    }
    enum Reason { INVALID_REQUEST, CONFLICT, DENIED, UNAVAILABLE, INDETERMINATE }
    final class Failure extends RuntimeException {
        private final Reason reason;
        public Failure(Reason r){super("Supplied fluid draft "+Objects.requireNonNull(r));reason=r;}
        public Failure(Reason r,Throwable cause){super("Supplied fluid draft "+Objects.requireNonNull(r),cause);reason=r;}
        public Reason reason(){return reason;}
    }
    Result execute(Command command);
}
