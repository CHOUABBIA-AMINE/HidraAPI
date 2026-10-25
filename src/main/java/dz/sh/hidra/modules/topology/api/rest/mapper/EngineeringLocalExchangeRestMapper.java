/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringLocalExchangeRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-25
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.mapper
 *
 * @Description : Implements bounded local editor exchange through existing protected owners.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.mapper;

import dz.sh.hidra.modules.topology.api.rest.request.EngineeringLocalExchangeRequests;
import dz.sh.hidra.modules.topology.api.rest.response.EngineeringLocalExchangeResponses;
import dz.sh.hidra.modules.topology.application.port.in.EngineeringLocalExchangeUseCase;
import dz.sh.hidra.modules.topology.application.port.in.EngineeringLocalExchangeUseCase.*;
import dz.sh.hidra.modules.topology.domain.model.EngineeringLocalExchange;
import dz.sh.hidra.modules.topology.domain.model.EngineeringLocalExchange.Editor;
import dz.sh.hidra.modules.topology.domain.model.EngineeringNetworkDraft;
import java.util.*;

public final class EngineeringLocalExchangeRestMapper {
    private EngineeringLocalExchangeRestMapper() { }
    public static Command command(Operation op,String editor,String company,String network,String draft,String branch,
            Long version,EngineeringLocalExchangeRequests.Submission body){
        EngineeringLocalExchange pack=null;
        if(body!=null){var p=Objects.requireNonNull(body.bundle());
            pack=new EngineeringLocalExchange(p.format(),Editor.valueOf(p.editor()),p.companyPartyId(),p.pipelineSystemId(),
                EngineeringNetworkDraft.uuid(p.contextId()),EngineeringNetworkDraft.uuid(p.draftId()),Objects.requireNonNull(p.sourceVersion()),
                Objects.requireNonNull(p.componentVersion()),p.componentFormat(),p.sha256(),p.payload(),p.branchId(),p.boundGraphVersion(),p.boundGraphSha256());}
        return new Command(op,Editor.valueOf(editor),company,network,EngineeringNetworkDraft.uuid(draft),branch,version,
                body==null?null:body.expectedVersion(),pack);
    }
    public static EngineeringLocalExchangeResponses.Result response(EngineeringLocalExchangeUseCase.Result r){
        EngineeringLocalExchangeResponses.Envelope p=null;
        if(r.bundle()!=null){var e=r.bundle();p=new EngineeringLocalExchangeResponses.Envelope(e.format(),e.editor().name(),
            e.companyPartyId(),e.pipelineSystemId(),e.contextId().toString(),e.draftId().toString(),e.sourceVersion(),e.componentVersion(),
            e.componentFormat(),e.sha256(),e.payload(),e.branchId(),e.boundGraphVersion(),e.boundGraphSha256());}
        return new EngineeringLocalExchangeResponses.Result(r.operation().name(),r.editor().name(),r.observedVersion(),r.targetVersion(),p,
            r.diagnostics(),r.state(),false,r.operation()==Operation.IMPORT?"CHECKED_BY_EXISTING_OWNER":"NOT_EVALUATED",
            List.of("EXPORT","DRY_RUN","IMPORT","CAPABILITIES"),r.editor().maximumBytes(),r.boundGraphVersion(),r.boundGraphSha256());
    }
}
