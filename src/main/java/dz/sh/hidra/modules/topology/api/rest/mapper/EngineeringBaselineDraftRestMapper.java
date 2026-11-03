/**
 *
 * @Project     : HidraAPI
 * @Product     : Hidra - Hydrocarbon Intelligence for Data, Risk, and Analytics
 * @Author      : Abir MEDJERAB
 * @Owner       : Sonatrach / TRC : Digitalization Initiative
 *
 * @Name        : EngineeringBaselineDraftRestMapper
 * @CreatedOn   : 2025-06-26
 * @UpdatedOn   : 2026-11-03
 *
 * @Type        : Class
 * @Layer       : API
 * @Module      : topology
 * @Package     : dz.sh.hidra.modules.topology.api.rest.mapper
 *
 * @Description : Maps explicit baseline draft transport contracts to immutable application data.
 *
 */
package dz.sh.hidra.modules.topology.api.rest.mapper;


import dz.sh.hidra.modules.topology.api.rest.request.EngineeringBaselineDraftRequests.*;
import dz.sh.hidra.modules.topology.api.rest.response.EngineeringBaselineDraftResponses;
import dz.sh.hidra.modules.topology.application.port.in.EngineeringBaselineDraftUseCase.*;
import dz.sh.hidra.modules.topology.domain.model.EngineeringBaselineManifest;
import java.util.*;

public final class EngineeringBaselineDraftRestMapper {
    private EngineeringBaselineDraftRestMapper(){}
    private static List<EngineeringBaselineManifest.Pin> pins(List<SourcePin> values){
        Objects.requireNonNull(values);
        if(values.size()>1024)throw new IllegalArgumentException("Baseline source bound exceeded");
        return values.stream().map(p->new EngineeringBaselineManifest.Pin(
            EngineeringBaselineManifest.Kind.valueOf(p.kind()),p.ownerId(),p.sourceId(),p.revisionId(),p.sha256())).toList();
    }
    public static Command create(Create b){
        Objects.requireNonNull(b);
        return new Command(Action.CREATE,b.manifestId(),b.companyPartyId(),b.pipelineSystemId(),null,0,b.requestId(),
            b.effectiveFrom(),b.effectiveUntil(),pins(b.pins()));
    }
    public static Command update(UUID id,Update b){
        Objects.requireNonNull(b);
        return new Command(Action.UPDATE,id,b.companyPartyId(),b.pipelineSystemId(),null,b.expectedVersion(),b.requestId(),
            b.effectiveFrom(),b.effectiveUntil(),pins(b.pins()));
    }
    public static Command read(UUID id,String company,String pipeline,Long version,boolean validate){
        return new Command(validate?Action.VALIDATE:Action.READ,id,company,pipeline,version,0,null,null,null,null);
    }
    private static EngineeringBaselineDraftResponses.Completeness completeness(EngineeringBaselineManifest.Completeness v){
        return new EngineeringBaselineDraftResponses.Completeness(v.complete(),v.diagnostics().stream()
            .map(d->new EngineeringBaselineDraftResponses.Finding(d.finding().name(),d.kind()==null?null:d.kind().name(),d.ownerId())).toList());
    }
    public static EngineeringBaselineDraftResponses.Draft response(Result r){
        var v=r.manifest();
        return new EngineeringBaselineDraftResponses.Draft(v.manifestId(),v.companyPartyId(),v.pipelineSystemId(),v.version(),
            v.effectiveFrom(),v.effectiveUntil(),v.pins().stream().map(p->new EngineeringBaselineDraftResponses.SourcePin(
                p.kind().name(),p.ownerId(),p.sourceId(),p.revisionId(),p.sha256())).toList(),
            r.canonicalSha256(),completeness(r.capturedCompleteness()),completeness(r.currentCompleteness()),
            r.replayed(),false,false,false);
    }
}
