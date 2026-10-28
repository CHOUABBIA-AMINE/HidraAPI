/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : ProductPublicationSourceQueryAdapter
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-10-27
 *
 * @Type        : Class
 * @Layer       : Infrastructure
 * @Module      : custody
 * @Package     : dz.sh.hidra.modules.custody.infrastructure.integration
 *
 * @Description : Provides strict protected supplied-fluid draft HTTP contracts.
 *
 */
package dz.sh.hidra.modules.custody.infrastructure.integration;

import dz.sh.hidra.modules.custody.application.contract.publication.ProductPublicationSourceContract;
import dz.sh.hidra.modules.custody.application.contract.publication.ProductPublicationSourceContract.*;
import dz.sh.hidra.modules.custody.application.port.in.ProductRevisionPublicationUseCase;
import dz.sh.hidra.modules.custody.application.port.in.ProductRevisionPublicationUseCase.Kind;
import dz.sh.hidra.modules.custody.infrastructure.persistence.adapter.ProductEngineeringDraftMetadataCodec;
import dz.sh.hidra.modules.custody.infrastructure.persistence.adapter.SuppliedFluidPropertyRevisionCodec;
import java.util.*;
import org.springframework.stereotype.Component;

@Component
public class ProductPublicationSourceQueryAdapter implements ProductPublicationSourceContract {
    private final ProductRevisionPublicationUseCase publications;
    public ProductPublicationSourceQueryAdapter(ProductRevisionPublicationUseCase publications){this.publications=Objects.requireNonNull(publications);}
    @Override public Optional<Source> read(Query q){Objects.requireNonNull(q);var kind=switch(q.mode()){case EXACT_HISTORICAL->Kind.READ_EXACT;case ELIGIBLE_CURRENT->Kind.ELIGIBLE_CURRENT;case ELIGIBLE_EFFECTIVE->Kind.ELIGIBLE_EFFECTIVE;};
        var r=publications.execute(new ProductRevisionPublicationUseCase.Command(kind,q.productDraftId(),q.companyPartyId(),null,0,0,null,q.publicationId(),0,null,null,q.asOf(),0,null));
        if(r.publication()==null || q.mode()!=Mode.EXACT_HISTORICAL && !r.consumerEligible())return Optional.empty();
        var p=r.publication();var e=p.envelope();var metadata=new ProductEngineeringDraftMetadataCodec().decode(e.metadata().bytes());var facts=new SuppliedFluidPropertyRevisionCodec().decode(e.supplied().bytes());var scalars=new ArrayList<ScalarFact>();flatten("supplied",facts,scalars);
        return Optional.of(new Source(envelope(e),p.publicationId(),approval(p.approval()),head(r.observedHead()),head(r.selectedHead()),q.mode()!=Mode.EXACT_HISTORICAL&&r.consumerEligible(),new Metadata(metadata.name(),metadata.description(),metadata.evidenceReference(),e.classification()),scalars,false));
    }
    private static void flatten(String path,Object value,List<ScalarFact> out){
        if(value==null){out.add(new ScalarFact(path,null));return;}
        if(value instanceof dz.sh.hidra.modules.custody.domain.model.SuppliedFluidPropertyRevision.Decimal d){out.add(new ScalarFact(path,d.lexeme()));return;}
        if(value instanceof String s){out.add(new ScalarFact(path,s));return;}
        if(value instanceof Enum<?> e){out.add(new ScalarFact(path,e.name()));return;}
        if(value instanceof List<?> xs){out.add(new ScalarFact(path+".size",Integer.toString(xs.size())));for(int i=0;i<xs.size();i++)flatten(path+"["+i+"]",xs.get(i),out);return;}
        if(value.getClass().isRecord())try{for(var c:value.getClass().getRecordComponents())flatten(path+"."+c.getName(),c.getAccessor().invoke(value),out);return;}catch(ReflectiveOperationException error){throw new IllegalStateException("Publication facts unavailable");}
        throw new IllegalStateException("Unsupported authored source scalar");
    }
    private static Scope scope(dz.sh.hidra.modules.custody.domain.model.ProductRevisionPublication.Scope s){return new Scope(s.productDraftId(),s.companyPartyId(),s.catalogProductId());}
    private static Component component(dz.sh.hidra.modules.custody.domain.model.ProductRevisionPublication.Component s){return new Component(s.version(),s.format(),s.sha256(),s.base64());}
    private static Envelope envelope(dz.sh.hidra.modules.custody.domain.model.ProductRevisionPublication.Envelope s){if(s==null)return null;var p=s.policy();return new Envelope(scope(s.scope()),component(s.metadata()),component(s.supplied()),s.suppliedMetadataVersion(),s.classification(),s.diagnostics(),s.units().stream().map(u->new Unit(u.unitId(),u.role(),u.code(),u.symbol(),u.dimension(),u.updatedAt())).toList(),new Policy(p.definitionId(),p.definitionVersion(),p.targetTypeId(),p.purposeId()),false);}
    private static Approval approval(dz.sh.hidra.modules.custody.domain.model.ProductRevisionPublication.Approval s){return s==null?null:new Approval(s.digest(),s.instanceId(),s.taskId(),s.actionId(),s.historyId(),s.actorId(),s.actorDisplayName(),s.actedAt());}
    private static Head head(dz.sh.hidra.modules.custody.domain.model.ProductRevisionPublication.Head s){return s==null?null:new Head(scope(s.scope()),s.publicationId(),s.epoch(),s.state().name(),s.eventId(),s.effectiveAt());}
    private static Event event(dz.sh.hidra.modules.custody.domain.model.ProductRevisionPublication.Event s){return s==null?null:new Event(s.eventId(),scope(s.scope()),s.epoch(),s.action().name(),s.priorPublicationId(),s.publicationId(),s.state().name(),s.requestId(),s.commandSha256(),s.effectiveAt(),approval(s.approval()));}
}
