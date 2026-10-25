/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringLocalExchangeCodec
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-25
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.infrastructure.persistence.adapter
 *
 * @Description : Implements bounded local editor exchange through existing protected owners.
 *
 */
package dz.sh.hidra.modules.topology.infrastructure.persistence.adapter;

import dz.sh.hidra.modules.topology.domain.model.*;
import dz.sh.hidra.modules.topology.application.port.out.EngineeringLocalExchangeCodecPort;
import java.util.*;
import org.springframework.stereotype.Component;

@Component
public final class EngineeringLocalExchangeCodec implements EngineeringLocalExchangeCodecPort {
    private final EngineeringNetworkDraftCodec metadata=new EngineeringNetworkDraftCodec();
    private final EngineeringGraphDraftCodec graph=new EngineeringGraphDraftCodec();
    private final SurveyRouteProfileCodec survey=new SurveyRouteProfileCodec();
    private byte[] encodeContent(Content content){return switch(content.editor()){
        case METADATA -> metadata.encode(content.metadata());case GRAPH -> graph.encode(content.graph());case SURVEY -> survey.encode(content.survey());};}
    @Override public EngineeringLocalExchange encode(Origin o,Content content){
        Objects.requireNonNull(o);Objects.requireNonNull(content);
        if(o.editor()!=content.editor())throw new IllegalArgumentException("Mixed editors");
        byte[] bytes=encodeContent(content);
        return new EngineeringLocalExchange(EngineeringLocalExchange.FORMAT,o.editor(),o.companyPartyId(),o.pipelineSystemId(),
                o.contextId(),o.draftId(),o.sourceVersion(),o.componentVersion(),o.editor().componentFormat(),
                EngineeringLocalExchange.digest(bytes),Base64.getEncoder().encodeToString(bytes),o.branchId(),o.boundGraphVersion(),o.boundGraphSha256());
    }
    @Override public Content decode(EngineeringLocalExchange envelope){
        Objects.requireNonNull(envelope);byte[] bytes=envelope.bytes();
        Content content=switch(envelope.editor()){
            case METADATA -> new Content(metadata.decode(bytes),null,null);
            case GRAPH -> new Content(null,graph.decode(bytes),null);
            case SURVEY -> new Content(null,null,survey.decode(bytes));};
        if(!Arrays.equals(bytes,encodeContent(content)) || content.survey()!=null && !content.survey().branchId().equals(envelope.branchId()))
            throw new IllegalArgumentException("Noncanonical payload or branch substitution");
        return content;
    }
}
