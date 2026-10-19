/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringNetworkDraftUseCase
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-19
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.port.in
 *
 * @Description : Implements the bounded company-scoped engineering draft contract.
 *
 */
package dz.sh.hidra.modules.topology.application.port.in;

import dz.sh.hidra.modules.topology.domain.model.EngineeringNetworkDraft;
import dz.sh.hidra.modules.topology.domain.model.EngineeringNetworkDraft.Metadata;
import java.util.*;
public interface EngineeringNetworkDraftUseCase {
    enum Kind { CREATE, READ, UPDATE, VALIDATE }
    record Command(Kind kind,String companyPartyId,String pipelineSystemId,UUID draftId,
            UUID creationRequestId,Long expectedVersion,Metadata metadata){
        public Command {if(kind==null)throw new IllegalArgumentException("Kind required");EngineeringNetworkDraft.identity(companyPartyId);EngineeringNetworkDraft.identity(pipelineSystemId);
            if(kind==Kind.CREATE){if(creationRequestId==null || metadata==null)throw new IllegalArgumentException("Create fields required");if(draftId!=null || expectedVersion!=null)throw new IllegalArgumentException("Invalid create");}
            else {if(draftId==null)throw new IllegalArgumentException("Draft identity required");if(creationRequestId!=null)throw new IllegalArgumentException("Invalid request");}
            if(kind==Kind.UPDATE || kind==Kind.VALIDATE){if(expectedVersion==null || expectedVersion<1)throw new IllegalArgumentException("Expected version required");}
            if(kind==Kind.UPDATE && metadata==null)throw new IllegalArgumentException("Metadata replacement required");
        }
    }
    record Result(EngineeringNetworkDraft draft,boolean replayed,List<String> diagnostics){
        public Result {Objects.requireNonNull(draft);diagnostics=List.copyOf(diagnostics);}
    }
    Result execute(Command command);
}
