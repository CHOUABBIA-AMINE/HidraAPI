/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringLocalExchangeCodecPort
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-25
 *
 * @Type        : Interface
 * @Layer       : Application
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.application.port.out
 *
 * @Description : Implements bounded local editor exchange through existing protected owners.
 *
 */
package dz.sh.hidra.modules.topology.application.port.out;

import dz.sh.hidra.modules.topology.domain.model.*;
import dz.sh.hidra.modules.topology.domain.model.EngineeringLocalExchange.Editor;
import java.util.*;

/** A format boundary only; contains no persistence or authority operation. */
public interface EngineeringLocalExchangeCodecPort {
    record Origin(Editor editor,String companyPartyId,String pipelineSystemId,UUID contextId,UUID draftId,
                  long sourceVersion,long componentVersion,String branchId,Long boundGraphVersion,String boundGraphSha256) { }
    record Content(EngineeringNetworkDraft.Metadata metadata,EngineeringGraphDraft graph,SurveyRouteProfile survey) {
        public Content {if((metadata==null?0:1)+(graph==null?0:1)+(survey==null?0:1)!=1)
            throw new IllegalArgumentException("Exactly one editor required");}
        public Editor editor(){return metadata!=null?Editor.METADATA:graph!=null?Editor.GRAPH:Editor.SURVEY;}
    }
    EngineeringLocalExchange encode(Origin origin,Content content);
    Content decode(EngineeringLocalExchange envelope);
}
