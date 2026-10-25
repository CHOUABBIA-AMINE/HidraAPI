/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringLocalExchangeUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-25
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.port.in
 *
 * @Description : Implements bounded local editor exchange through existing protected owners.
 *
 */
package dz.sh.hidra.modules.topology.application.port.in;

import dz.sh.hidra.modules.topology.domain.model.*;
import dz.sh.hidra.modules.topology.domain.model.EngineeringLocalExchange.Editor;
import java.util.*;

public interface EngineeringLocalExchangeUseCase {
    enum Operation { EXPORT, DRY_RUN, IMPORT, CAPABILITIES }
    record Command(Operation operation,Editor editor,String companyPartyId,String pipelineSystemId,UUID draftId,
                   String branchId,Long requestedVersion,Long expectedVersion,EngineeringLocalExchange bundle) {
        public Command {
            Objects.requireNonNull(operation);Objects.requireNonNull(editor);Objects.requireNonNull(draftId);
            EngineeringNetworkDraft.identity(companyPartyId);EngineeringNetworkDraft.identity(pipelineSystemId);
            if(editor==Editor.SURVEY)EngineeringGraphDraft.identity(branchId);
            else if(branchId!=null)throw new IllegalArgumentException("Unexpected branch");
            boolean post=operation==Operation.DRY_RUN || operation==Operation.IMPORT;
            if(post){
                if(expectedVersion==null || expectedVersion<1 || expectedVersion==Long.MAX_VALUE || bundle==null
                        || bundle.editor()!=editor || requestedVersion!=null)throw new IllegalArgumentException("Invalid import selectors");
            }else if(expectedVersion!=null || bundle!=null)throw new IllegalArgumentException("Unexpected body");
            if(requestedVersion!=null && (operation!=Operation.EXPORT || editor==Editor.METADATA || requestedVersion<1))
                throw new IllegalArgumentException("Unsupported historical selection");
        }
    }
    record Result(Operation operation,Editor editor,long observedVersion,Long targetVersion,
                  EngineeringLocalExchange bundle,List<String> diagnostics,String state,
                  Long boundGraphVersion,String boundGraphSha256) {
        public Result {Objects.requireNonNull(operation);Objects.requireNonNull(editor);
            if(observedVersion<1 || targetVersion!=null && targetVersion<1)throw new IllegalArgumentException("Invalid result version");
            diagnostics=List.copyOf(diagnostics);Objects.requireNonNull(state);}
    }
    Result execute(Command command);
}
