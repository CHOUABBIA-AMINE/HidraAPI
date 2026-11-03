/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringBaselineDraftUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-03
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.port.in
 *
 * @Description : Coordinates versioned unqualified engineering baseline drafts.
 *
 */
package dz.sh.hidra.modules.topology.application.port.in;


import dz.sh.hidra.modules.topology.domain.model.EngineeringBaselineManifest;
import dz.sh.hidra.modules.topology.domain.model.EngineeringBaselineManifest.*;
import java.time.Instant;
import java.util.*;

public interface EngineeringBaselineDraftUseCase {
    enum Action { CREATE, UPDATE, READ, VALIDATE }
    record Command(Action action, UUID manifestId, String companyPartyId, String pipelineSystemId,
                   Long revision, long expectedVersion, UUID requestId, Instant effectiveFrom,
                   Instant effectiveUntil, List<Pin> pins) {
        public Command {
            Objects.requireNonNull(action); Objects.requireNonNull(manifestId);
            companyPartyId=EngineeringBaselineManifest.identity(companyPartyId);
            pipelineSystemId=EngineeringBaselineManifest.identity(pipelineSystemId);
            if(revision!=null && revision<1)throw new IllegalArgumentException("Positive revision required");
            boolean write=action==Action.CREATE||action==Action.UPDATE;
            if(write){
                if(revision!=null||requestId==null||expectedVersion<0||expectedVersion==Long.MAX_VALUE
                    ||(action==Action.CREATE)!=(expectedVersion==0))throw new IllegalArgumentException("Invalid draft writer command");
                new EngineeringBaselineManifest(manifestId,companyPartyId,pipelineSystemId,
                    expectedVersion+1,effectiveFrom,effectiveUntil,pins);
                pins=List.copyOf(pins);
            }else if(expectedVersion!=0||requestId!=null||effectiveFrom!=null||effectiveUntil!=null||pins!=null)
                throw new IllegalArgumentException("Read cannot contain draft mutation data");
        }
        public boolean mutating(){return action==Action.CREATE||action==Action.UPDATE;}
        public EngineeringBaselineManifest candidate(){
            if(!mutating())throw new IllegalStateException("Read has no candidate");
            return new EngineeringBaselineManifest(manifestId,companyPartyId,pipelineSystemId,
                expectedVersion+1,effectiveFrom,effectiveUntil,pins);
        }
        @Override public String toString(){return "BaselineDraftCommand[redacted]";}
    }
    record Result(EngineeringBaselineManifest manifest, String canonicalSha256,
                  Completeness capturedCompleteness, Completeness currentCompleteness, boolean replayed) {
        public Result {Objects.requireNonNull(manifest);Objects.requireNonNull(canonicalSha256);
            Objects.requireNonNull(capturedCompleteness);Objects.requireNonNull(currentCompleteness);}
    }
    Result execute(Command command);
}
